import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpressionParser {
    private ExpressionFactory expressionFactory;

    public ExpressionParser() {
        this.expressionFactory = ExpressionFactory.getInstance();
    }

    public ComplexExpression parse(String[] args) {

        // Check if the number of arguments is ok
        if (args.length % 2 == 0 || args.length < 3) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        try {
            List<ComplexNumber> operands = new ArrayList<>();
            List<Operation> operations = new ArrayList<>();

            for (int i = 0; i < args.length; i++) {
                if (i % 2 == 0) {
                    operands.add(parseComplexNumber(args[i]));
                } else {
                    operations.add(Operation.valueOf(Operation.getOperationBySymbol(args[i])));
                }
            }


            return buildExpression(operands, operations);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid args");
        }
    }

    /**
     * Transform a string into a complex number and return it
     * @return type - ComplexNumber
     */
    private ComplexNumber parseComplexNumber(String arg) {
        String[] parts = arg.split("[+\\-]");

        // If the number starts with + or - remove the empty space obtained by splitting
        if (parts[0].isEmpty()) {
            parts = Arrays.copyOfRange(parts, 1, parts.length);
        }

        double re, im;
        // Get the real part (positive or negative)
        if (arg.charAt(0) == '-') {
            re = -Double.parseDouble(parts[0]);
        } else {
            re = Double.parseDouble(parts[0]);
        }

        // Get the imaginary part (positive or negative)
        if (arg.charAt(arg.length() - 1) != 'i') {
            throw new IllegalArgumentException("Imagining part missing");
        }
        if (arg.charAt(arg.length() - parts[1].length() - 1) == '-') {
            im = -Double.parseDouble(parts[1].replace("i", ""));
        } else {
            im = Double.parseDouble(parts[1].replace("i", ""));
        }

        return new ComplexNumber(re, im);
    }

    /**
     * Perform the operations by their precedence
     * @return the last operation to perform
     */
    private ComplexExpression buildExpression(List<ComplexNumber> operands,List<Operation> operations) {

        // Firstly, perform the MULTIPLICATIONS and DIVISIONS
        for (int i = 0; i < operations.size(); i++) {
            if (operations.get(i).getSymbol().equals("*") || operations.get(i).getSymbol().equals("/")) {
                operands.set(i, expressionFactory.createExpression(operations.get(i), new ComplexNumber[]{operands.get(i), operands.get(i + 1)}).execute());
                operands.remove(i +1);
                operations.remove(i);
                i--;
            }
        }

        ComplexExpression expression = expressionFactory.createExpression(Operation.ADDITION, new ComplexNumber[]{operands.get(0), new ComplexNumber(0, 0)});

        // Perform ADDITION and SUBTRACTION
        for (int i = 0; i < operations.size(); i++) {
            expression = expressionFactory.createExpression(operations.get(i), new ComplexNumber[]{expression.execute(), operands.get(i + 1)});
        }

        // Return the last operation
        return expression;
    }
}
