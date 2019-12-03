package rna;

// Classe para representar o neurônio artificial.

public class Neuronio {

    protected double pesos[]; // Pesos do neurônio.
    protected double bias;    // bias do neurônio

    // Função de ativação do neurônio.
    protected FuncaoAtivacao fncAtivacao;

    public Neuronio(int numEntradas) {
        pesos = new double[numEntradas];
    }

    private double juncaoAditiva(double entrada[]) {
        double somatoria = 0.0;
        for (int i = 0; i < pesos.length; ++i) {
            somatoria += pesos[i] * entrada[i];
        }
        somatoria += bias;
        return somatoria;
    }

    public void setPesos(double pesos[]) {
        this.pesos = pesos;
    }

    public void setFuncaoAtivacao(FuncaoAtivacao fncAtivacao) {
        this.fncAtivacao = fncAtivacao;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public double getSaida(double entrada[]) {
        double v = juncaoAditiva(entrada);
        return fncAtivacao.aplicar(v);
    }

}
