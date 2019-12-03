package rna;

// Multi Layer Perceptron
import java.util.ArrayList;

public class MLP {

    private ArrayList<Camada> camadas;

    public MLP(int[] arquitetura) {
        inicializarMLP(arquitetura);
    }

    private void inicializarMLP(int[] arq) {

        camadas = new ArrayList<>();

        for (int i = 1; i < arq.length; i++) {
            Camada c = new Camada(arq[i], arq[i - 1]);
            camadas.add(c);
        }
    }
    
    public Camada getCamada(int i) {
        return camadas.get(i);
    }
    
    public void setFuncaoAtivacao(FuncaoAtivacao fnc) {
        camadas.forEach((c) -> {
            c.setFuncaoAtivacao(fnc);
        });
    }

    public double[] propagar(double[] entrada) {

        for (int i = 0; i < camadas.size(); i++) {
            Camada c = camadas.get(i);
            double[] proximaEntrada = new double[c.getNumPerceptrons()];
            for (int j = 0; j < c.getNumPerceptrons(); j++) {
                double saida = c.getPerceptron(j).getSaida(entrada);
                proximaEntrada[j] = saida;
            }

            entrada = proximaEntrada;
        }
        
        return entrada; // No final, entrada é o equivalente a saída da última camada
    }
}
