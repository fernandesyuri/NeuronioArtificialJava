
import java.util.Random;

// Classe Perceptron representa o neurônio com aprendizado.
public class Perceptron extends Neuronio {

    // Define qual será a taxa de aprendizado do Perceptron;
    private int numeroEpocas;

    private double taxaAprendizado;

    public Perceptron(int numEntradas) {
        super(numEntradas);
        configurarPesosAleatorios();
    }

    public int getNumeroEpocas() {
        return numeroEpocas;
    }

    public void setNumeroEpocas(int numeroEpocas) {
        this.numeroEpocas = numeroEpocas;
    }

    public void setTaxaAprendizado(double ta) {
        this.taxaAprendizado = ta;
    }

    public void aprendizado(double dados[][], double saidaDesejada[]) {
        double erroTotal = Double.MAX_VALUE;
        int epoca = 0;
        while (erroTotal > 0 && numeroEpocas > epoca) {
            erroTotal = 0;
            for (int j = 0; j < dados.length; ++j) {
                double saidaDoPerceptron = getSaida(dados[j]); // f(v(w))
                double erro = saidaDesejada[j] - saidaDoPerceptron;

                for (int i = 0; i < pesos.length; ++i) {
                    if (dados[j][i] != 0) {
                        // pesos[i] += taxaAprendizado * (Math.pow(erro, 2)) / (dados[j][i] * erro); // derivada = 2 x erro
                        pesos[i] += taxaAprendizado * dados[j][i] * erro / 2 * Math.pow(dados[j][i], 2);
                    }
                }
                
                    bias += taxaAprendizado * erro;
                
                erroTotal += Math.sqrt(Math.pow(erro, 2));
            }
            epoca++;
            exibirEpoca(erroTotal, epoca);
            imprimirPesos();
        }
    }

    // Configura aleatoriamente os pesos do Perceptron.
    private void configurarPesosAleatorios() {
        Random r = new Random();
        for (int i = 0; i < pesos.length; ++i) {
            pesos[i] = r.nextDouble();
        }
        bias = r.nextDouble();
        imprimirPesos();
    }

    private void exibirEpoca(double erro, int epoca) {
        System.out.printf("Epoca = %d\n", epoca);
        System.out.printf("Erro Total = %.2f\n", erro);
    }
}
