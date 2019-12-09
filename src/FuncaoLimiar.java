
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
    public double derivada(double v) {
        return 1;
    }

    @Override
    public double derivadaSegunda(double v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double inversa(double v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
