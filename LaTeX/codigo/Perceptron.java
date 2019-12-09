
public void aprendizado(double dados[][], double saidaDesejada[]) {
        double erroTotal = Double.MAX_VALUE;
        int epoca = 0;

        // Faz enquanto o erro for diferente de 0 ou quando acabar o número de épocas
        while (erroTotal > 0 && numeroEpocas > epoca) {
            erroTotal = 0;

            for (int j = 0; j < dados.length; ++j) {

                // f(v(w))
                double saidaDoPerceptron = getSaida(dados[j]);

                // e = (y - f(v(w)))
                double erro = saidaDesejada[j] - saidaDoPerceptron;

                for (int i = 0; i < pesos.length; ++i) {

                    // Caso erro ou entrada seja 0 não realiza a conta para não dividir por 0 e mantém o mesmo valor de peso da época anterior
                    if (dados[j][i] != 0 && erro != 0) {
                        // x(n+1) =  x(n) + ( f(x(n)) / f'(x(n)) )
                        pesos[i] += (Math.pow(erro, 2)) / (dados[j][i] * erro);
                    }
                }

                // Caso erro seja 0 não realiza a conta para não dividir por 0 e mantém o mesmo valor de bias da época anterior
                if (erro != 0) {
                    // b(n+1) = b(n) + ( f(b(n)) / f'(b(n)) )
                    bias += (Math.pow(erro, 2)) / (erro);
                }
                erroTotal += Math.sqrt(Math.pow(erro, 2));
            }
            epoca++;
            // Exibe o erro total da época (pode ser no máximo igual ao número de entradas)
            exibirEpoca(erroTotal, epoca);
        }
    }
}
