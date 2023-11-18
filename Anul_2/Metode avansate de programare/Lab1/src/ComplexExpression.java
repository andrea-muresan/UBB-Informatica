public abstract class ComplexExpression {

    private Operation operation;
    protected ComplexNumber[] args;

    public ComplexExpression(Operation operation, ComplexNumber[] args) {
        this.operation = operation;
        this.args = args;
    }

    public abstract ComplexNumber execute();

    protected abstract ComplexNumber executeOneOperation(ComplexNumber arg1, ComplexNumber arg2);

}
