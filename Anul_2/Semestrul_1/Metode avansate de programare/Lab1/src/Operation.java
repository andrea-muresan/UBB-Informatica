public enum Operation {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/");

    private String symbol;

    private Operation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * @return the name of the enum by its symbol
     */
    public static String getOperationBySymbol(String symbol) {
        for (Operation e: Operation.values()) {
            if ( e.getSymbol().equals(symbol)) {
                return e.name();
            }
        }
        return null;
    }
}
