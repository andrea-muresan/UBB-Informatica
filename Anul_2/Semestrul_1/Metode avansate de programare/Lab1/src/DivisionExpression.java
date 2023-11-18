public class DivisionExpression extends ComplexExpression {
    public DivisionExpression(ComplexNumber[] args) {
        super(Operation.ADDITION, args);
    }

    @Override
    public ComplexNumber execute() {
        ComplexNumber result = args[0];
        for (int i = 1; i < args.length; i++) {
            result = executeOneOperation(result, args[i]);
        }
        return result;
    }

    @Override
    protected ComplexNumber executeOneOperation(ComplexNumber arg1, ComplexNumber arg2) {
        return arg1.divide(arg2);
    }


}
