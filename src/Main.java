
public class Main {

    public static void main(String a[]) {
        FuncaoAtivacao fLimiar = new FuncaoLimiar();
        double dados[][] = {{1, 1}, {1, 4}, {4, 1}, {4, 4}};
        double saida[] = {0, 0, 0, 1};
        Perceptron p = new Perceptron(2);
        p.setFuncaoAtivacao(fLimiar);
        p.setNumeroEpocas(1000);
        p.setTaxaAprendizado(0.2);
        p.aprendizado(dados, saida);

        double teste[] = {4, 4};
        double resposta = p.getSaida(teste);
        System.out.println("y = " + resposta);
    }
}
