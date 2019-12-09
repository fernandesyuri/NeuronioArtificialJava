
public class Main {
    public static void main(String a[]) {

        FuncaoAtivacao fLimiar = new FuncaoLimiar();
        FuncaoAtivacao fSigmoide = new FuncaoSigmoide();

        double dados[][] = {{1, 1}, {1, 4}, {4, 1}, {4, 4}};
        double saida[] = {0, 0, 1, 1};
        // double dados[][] = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
         //double saida[] = { 0, 0, 0, 1 };

        Perceptron p = new Perceptron(2);
        p.setFuncaoAtivacao(fLimiar);
        p.setNumeroEpocas(100);
        p.setTaxaAprendizado(0.2);
        p.aprendizado(dados, saida);

        double entrada[] = { 4, 4 };
        double resposta = p.getSaida(entrada);
        System.out.println("y = " + resposta);
    }
}
