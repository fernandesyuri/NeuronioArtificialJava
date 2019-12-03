package rna;

// Classe Perceptron representa o neurônio com aprendizado.
import java.util.Random;

public class Perceptron extends Neuronio {

    // Define qual será a taxa de aprendizado do Perceptron
    private double taxaAprendizado;
    private int numeroEpocas;

    public Perceptron(int numEntradas) {
        super(numEntradas);
        configurarPesosAleatorios();
    }

    // Aprendizado por método do gradiente
    public void aprendizado(double dados[][], double saidaDesejada[]) {
        // w(n+1) = w(n) + f'(v(w)) * ta * dados[n] * (saidaDesejada[n] - f(v(w)))
        // v(w) -> saída da função somadora

        int epoca = 0;
        double erroTotal = Double.MAX_VALUE;

        while (erroTotal > 0 && numeroEpocas > epoca) {

            erroTotal = 0;

            for (int j = 0; j < dados.length; j++) {

                double saidaDoPerceptron = getSaida(dados[j]);
                double erro = saidaDesejada[j] - saidaDoPerceptron;
                double derivada = fncAtivacao.derivada(saidaDoPerceptron);

                for (int i = 0; i < pesos.length; i++) {
                    pesos[i] += derivada * taxaAprendizado * dados[j][i];
                }

                bias += derivada * taxaAprendizado * erro * 1;
                erroTotal += Math.sqrt(Math.pow(erro, 2));
            }

            epoca++;
            exibirEpoca(erroTotal, epoca);
        }
    }

    // Aprendizado por método de Newton
    public void aprendizadoNewton(double dados[][], double saidaDesejada[]) {
        // w(n+1) = w(n) - f(w(n)) / f'(w(n))
        
        int epoca = 0;
        double erroTotal = Double.MAX_VALUE;

        while (erroTotal > 0 && numeroEpocas > epoca) {

            erroTotal = 0;

            for (int j = 0; j < dados.length; j++) {

                double saidaDoPerceptron = getSaida(dados[j]);
                double erro = saidaDesejada[j] - saidaDoPerceptron;
                double derivada = fncAtivacao.derivada(saidaDoPerceptron);

                for (int i = 0; i < pesos.length; i++) {
                    pesos[i] += derivada * taxaAprendizado * dados[j][i];
                }

                bias += derivada * taxaAprendizado * erro * 1;
                erroTotal += Math.sqrt(Math.pow(erro, 2));
            }

            epoca++;
            exibirEpoca(erroTotal, epoca);
        }
    }

    // Configura aleatoriamente os pesos do Perceptron
    private void configurarPesosAleatorios() {
        Random r = new Random();
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] = r.nextDouble();
        }
        bias = r.nextDouble();
    }

    private void exibirEpoca(double erro, int epoca) {
        System.out.printf("Época = %d\n", epoca);
        System.out.printf("Erro total = %.2f\n", erro);
    }

    public void setTaxaAprendizado(double taxaAprendizado) {
        this.taxaAprendizado = taxaAprendizado;
    }

    public void setNumeroEpocas(int numeroEpocas) {
        this.numeroEpocas = numeroEpocas;
    }
}
