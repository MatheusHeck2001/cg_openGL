package MD2;

public class Frame implements Cloneable{
    public PositionNormal[] pn;  // [pn_index]
    public Plane[] triplane;     // [tri_num]

    public Object clone() {
        Frame res = new Frame();
        res.pn = new PositionNormal[pn.length];
        for (int i = 0; i < pn.length; i++) {
            res.pn[i] = (PositionNormal) pn[i].clone();
        }
        res.triplane = new Plane[triplane.length];
        for (int i = 0; i < triplane.length; i++) {
            res.triplane[i] = (Plane) triplane[i].clone();
        }
        return res;
    }
}
