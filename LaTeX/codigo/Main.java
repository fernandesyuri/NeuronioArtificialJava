
public class Main {

    public static void main(String a[]) {
        // Define um Perceptron com 2 entradas
        Perceptron p = new Perceptron(2);
        
        // Define a função de Ativação como Limiar
        FuncaoAtivacao fLimiar = new FuncaoLimiar();
        p.setFuncaoAtivacao(fLimiar);
        
        // Define o número de épocas
        p.setNumeroEpocas(1000);
        
        // Dados de entrada
        double dados[][] = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };

        // Dados de saída
        double saida[] = { 0, 0, 0, 1 };

        // Realiza o aprendizado do nerônio artificial com os dados de entrada e saída
        p.aprendizado(dados, saida);

        // Entrada de teste, a resposta do neuônio nesse caso deve ser 0
        double entrada[] = { 0, 1 };
        double resposta = p.getSaida(entrada);

        // Mostra a saída obtida pelo Neurônio Artificial
        System.out.println("y = " + resposta);
    }
}
