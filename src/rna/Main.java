package rna;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String a[]) throws FileNotFoundException, IOException {

        FuncaoAtivacao funcaoAtivacao = new FuncaoLinear();

        /*
        Neuronio neuronio = new Neuronio(2);
        neuronio.setFuncaoAtivacao(fLimiar);

        double pesos[] = new double[]{0, -1};
        double bias = 0;

        neuronio.setPesos(pesos);
        neuronio.setBias(bias);

        double entrada[] = new double[]{-1, -1};
        double classe = neuronio.getSaida(entrada);

        System.out.println("Classe = " + classe);
         */
        
        /*
        double dados[][] = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double saida[] = {0, 1, 1, 1};
        Perceptron perceptron = new Perceptron(2);
        perceptron.setFuncaoAtivacao(fLimiar);
        perceptron.setNumeroEpocas(100);
        perceptron.setTaxaAprendizado(0.2);
        perceptron.aprendizado(dados, saida);

        double teste[] = {0, 0};
        double resposta = perceptron.getSaida(teste);
        System.out.println("y = " + resposta);
        */
        
        // Adeline
        /*
        double dados[][] = {{2}, {3}, {4}, {5}, {6}};
        double saida[] = {4, 6, 8, 10, 12};
        Perceptron adeline = new Perceptron(1);
        adeline.setFuncaoAtivacao(new FuncaoLinear());
        adeline.setNumeroEpocas(30);
        adeline.setTaxaAprendizado(0.1);
        adeline.aprendizado(dados, saida);
        
        double teste[] = {7};
        System.out.println(adeline.getSaida(teste));
        */
        
        // Exemplo do XOR (0 0 -> 0; 0 1 -> 1; 1 0 -> 1; 1 1 -> 0)
        int arq[] = {2, 2, 1};
        MLP mlp = new MLP(arq);
        
        double pn1[] = {1, 1};
        double pn2[] = {-1, -1};
        double pn3[] = {1, 1};
        ArrayList<Double> bc1 = new ArrayList<>();
        bc1.add(-0.5);
        bc1.add(1.5);
        ArrayList<Double> bc2 = new ArrayList<>();
        bc2.add(-1.5);
        
        ArrayList<double[]> pesos = new ArrayList<>();
        pesos.add(pn1);
        pesos.add(pn2);
        ArrayList<double[]> pesos2 = new ArrayList<>();
        pesos2.add(pn3);
        
        mlp.getCamada(0).setPesos(pesos);
        mlp.getCamada(0).setBias(bc1);
        mlp.getCamada(1).setPesos(pesos2);
        mlp.getCamada(1).setBias(bc2);
        
        mlp.setFuncaoAtivacao(new FuncaoLimiar());
        
        double entrada[] = {0, 0};
        System.out.println(Arrays.toString(mlp.propagar(entrada)));
        
        // Exemplo com a base de dados IRIS
        /*
        BufferedReader br = new BufferedReader(new FileReader(new File("iris.data")));
        double dados[][] = new double[150][4];
        double saida[] = new double[dados.length];
        
        for (int i = 0; i < 150; i++) {
            String line = br.readLine();
            String[] split = line.split(",");
            dados[i][0] = Double.valueOf(split[0]);
            dados[i][1] = Double.valueOf(split[1]);
            dados[i][2] = Double.valueOf(split[2]);
            dados[i][3] = Double.valueOf(split[3]);
            switch(split[4]) {
                case "Iris-setosa":
                    saida[i] = 0;
                    break;
                case "Iris-versicolor":
                    saida[i] = 1;
                    break;
                case "Iris-virginica":
                    saida[i] = 2;
                    break;
            }
        }
        
        double[] saidaP1 = new double[150];
        double[] saidaP2 = new double[150];
        double[] saidaP3 = new double[150];
        
        for(int i = 0; i < 150; i++) {
            saidaP1[i] = saida[i] == 0 ? 1 : 0;
            saidaP2[i] = saida[i] == 1 ? 1 : 0;
            saidaP3[i] = saida[i] == 2 ? 1 : 0;
        }
        
        Perceptron p1 = new Perceptron(4);
        Perceptron p2 = new Perceptron(4);
        Perceptron p3 = new Perceptron(4);
        p1.setFuncaoAtivacao(funcaoAtivacao);
        p2.setFuncaoAtivacao(funcaoAtivacao);
        p3.setFuncaoAtivacao(funcaoAtivacao);
        p1.setNumeroEpocas(100);
        p2.setNumeroEpocas(100);
        p3.setNumeroEpocas(100);
        p1.setTaxaAprendizado(0.1);
        p2.setTaxaAprendizado(0.2);
        p3.setTaxaAprendizado(0.2);
        
        p1.aprendizado(dados, saidaP1);
        */
        
    }
}
