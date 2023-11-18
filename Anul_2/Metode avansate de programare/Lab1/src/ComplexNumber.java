public class ComplexNumber {
    private double re;
    private double im;

    public ComplexNumber(double re, double im) {
        this.re = Math.round(re * 100) / 100.0;
        this.im = Math.round(im * 100) / 100.0;
    }

    /**
     * @return the addition of two complex numbers
     */
    public ComplexNumber add(ComplexNumber other) {
        double newRe = this.re + other.re;
        double newIm = this.im + other.im;
        return new ComplexNumber(newRe, newIm);
    }

    /**
     * @return the subtraction for complex numbers
     */
    public ComplexNumber subtract(ComplexNumber other) {
        double newRe = this.re - other.re;
        double newIm = this.im - other.im;
        return new ComplexNumber(newRe, newIm);
    }

    /**
     * @return the multiplication for complex numbers
     */
    public ComplexNumber multiply(ComplexNumber other) {
        double newRe = this.re * other.re - this.im * other.getIm();
        double newIm = this.re * other.im + other.re * this.im;
        return new ComplexNumber(newRe, newIm);
    }

    /**
     * @return the division of two complex numbers
     */
    public ComplexNumber divide(ComplexNumber other) {
        // Division by 0
        if (other.im == 0 && other.re == 0)
            throw new IllegalArgumentException("Division by 0");
        double denominator = other.re * other.re + other.im * other.im;
        double newRe = (this.re * other.re + this.im * other.im) / denominator;
        double newIm = (-this.re * other.im + other.re * this.im) / denominator;

        return new ComplexNumber(newRe, newIm);
    }

    /**
     * @return the conjugate of a complex number
     */
    public ComplexNumber conjugate() {
        return new ComplexNumber(this.re, -this.im);
    }

    @Override
    public String toString() {
        return  re +
                " + " + im +
                "i";
    }

    // Getters and setters
    public double getRe() {
        return re;
    }

    public void setRe(float re) {
        this.re = re;
    }

    public double getIm() {
        return im;
    }

    public void setIm(float im) {
        this.im = im;
    }
}
