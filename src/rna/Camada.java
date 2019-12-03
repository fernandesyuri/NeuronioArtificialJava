package rna;

// Classe para representar uma camada de perceptrons
import java.util.ArrayList;

public class Camada {

    private ArrayList<Perceptron> perceptrons;

    public Camada(int numPerceptrons, int numEntradas) {
        inicializarPerceptrons(numPerceptrons, numEntradas);
    }

    private void inicializarPerceptrons(int numPerceptrons, int numEntradas) {
        perceptrons = new ArrayList<>();
        for (int i = 0; i < numPerceptrons; i++) {
            Perceptron p = new Perceptron(numEntradas);
            perceptrons.add(p);
        }
    }

    public void setFuncaoAtivacao(FuncaoAtivacao fncAtivacao) {
        perceptrons.forEach((p) -> {
            p.setFuncaoAtivacao(fncAtivacao);
        });
    }

    public void setPesos(ArrayList<double[]> pesos) {
        for (int i = 0; i < pesos.size(); i++) {
            perceptrons.get(i).setPesos(pesos.get(i));
        }
    }

    public void setBias(ArrayList<Double> bias) {
        for (int i = 0; i < bias.size(); i++) {
            perceptrons.get(i).setBias(bias.get(i));
        }
    }

    public int getNumPerceptrons() {
        return perceptrons.size();
    }

    public Perceptron getPerceptron(int i) {
        return perceptrons.get(i);
    }
}
