package MD2;

public class FileFrame {
    public float[] scale     = new float[3];           // multiply byte verts by this
    public float[] translate = new float[3];           // then add this
    public String name;                                // frame name from grabbing
    public FileCompressedVertex[] verts;               // variable sized
}
