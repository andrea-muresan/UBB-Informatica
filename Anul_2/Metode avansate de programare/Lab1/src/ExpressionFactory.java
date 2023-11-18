public class ExpressionFactory { // singleton
    private static ExpressionFactory instance;

    private ExpressionFactory() {}

    public static ExpressionFactory getInstance() {
        if (instance == null) {
            instance = new ExpressionFactory();
        }
        return instance;
    }

    public ComplexExpression createExpression(Operation operation, ComplexNumber[] args) {
        switch (operation) {
            case ADDITION:
                return new AdditionExpression(args);
            case SUBTRACTION:
                return new SubtractionExpression(args);
            case MULTIPLICATION:
                return new MultiplicationExpression(args);
            case DIVISION:
                return new DivisionExpression(args);
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }
}
