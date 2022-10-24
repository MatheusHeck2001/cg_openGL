package MD2;

public class PositionNormal implements Cloneable{
    public float x, y, z;
    public float nx, ny, nz;
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
