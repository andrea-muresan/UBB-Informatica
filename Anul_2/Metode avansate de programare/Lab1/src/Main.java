public class Main {
    public static void main(String[] args) {
        try {
            ExpressionParser expressionParser = new ExpressionParser();
            ComplexExpression expression = expressionParser.parse(args);
            ComplexNumber result = expression.execute();

            System.out.println("Result: " +  result);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}