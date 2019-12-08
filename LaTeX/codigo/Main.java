
public class Main {

    public static void main(String a[]) {
        FuncaoAtivacao fLimiar = new FuncaoLimiar();
        double dados[][] = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double saida[] = {0, 0, 0, 1};
        Perceptron p = new Perceptron(2);
        p.setFuncaoAtivacao(fLimiar);
        p.setNumeroEpocas(1000);
        p.aprendizado(dados, saida);

        double teste[] = {0, 1};
        double resposta = p.getSaida(teste);
        System.out.println("y = " + resposta);
    }
}
