package rna;


public class FuncaoLimiar implements FuncaoAtivacao {

    @Override
    public double aplicar(double v) {
        if (v >= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public double derivada(double fv) {
        return 1;
    }
}
