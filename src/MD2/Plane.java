package MD2;

public class Plane implements Cloneable{
    public float a,b,c,d;
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
