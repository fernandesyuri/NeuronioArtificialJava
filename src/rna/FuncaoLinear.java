package rna;


public class FuncaoLinear implements FuncaoAtivacao {

    @Override
    public double aplicar(double v) {
        return v;
    }

    @Override
    public double derivada(double fv) {
        return 1;
    }
}
