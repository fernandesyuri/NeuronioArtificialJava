

public class FuncaoSigmoide implements FuncaoAtivacao {

    // função sigmoide
    public double aplicar(double x) {
        double ex = Math.exp(x);
        return ex / (ex + 1);
    }

    // derivada da função sigmoide
    public double derivada(double x) {
        double ex = Math.exp(x);
        return ex / ((ex + 1) * (ex + 1));
    }

    // derivada segunda da função sigmoide
    public double derivadaSegunda(double x) {
        double ex = Math.exp(x);
        return (ex * (1 - ex)) / ((ex + 1) * (ex + 1) * (ex + 1));
    }

    // inversa da função sigmoide
    public double inversa(double y) {
        return Math.log(y / (1 - y));
    }
}
