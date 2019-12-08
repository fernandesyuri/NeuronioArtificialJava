public class FuncaoLimiar implements FuncaoAtivacao {

    public double aplicar(double v) {
        if (v >= 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
