package MD2_2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class MD2 {
    boolean LoadModel(String filename) throws IOException {
        Header header = new Header();
        char[] buffer = new char[]{};
        Frame[] frame = new Frame[]{};
        Vector[] vertices = new Vector[]{};
        int[] normals = new int[]{};

        try (RandomAccessFile aFile = new RandomAccessFile(filename, "r");
             FileChannel inChannel = aFile.getChannel();) {

            //Buffer size is 1024
            ByteBuffer byteBuffer = ByteBuffer.allocate(4);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

            while (inChannel.read(byteBuffer) > 0) {
                byteBuffer.flip();
                for (int i = 0; i < byteBuffer.limit(); i++) {
                    System.out.print((char) byteBuffer.get());
                }
                header.ident = byteBuffer.getInt();
                byteBuffer.clear(); // do something with the data and clear/compact it.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    boolean LoadSkin(String filename) {
        return false;
    }

    void DrawModel(float time){}
    void DrawFrame(int frame){}

    void setAnim(int type){};
    void ScaleModel(float s){};

    private void Animate(float time){}
    private void ProcessLightning(){}
    private void Interpolate(Vector v_list){}
    private void RenderFrame(){}

    public static Vector anorms [];
    public static float anorms_dots [][];
    public Animation animlist[];

    private int num_frames;
    private int num_xyz;
    private int num_glcmds;
    private Vector[] m_vertices;
    private int[] m_glcmds;
    private int[] m_lightnormals;
    private int m_texid;
    private AnimationState m_anim;
    private float m_scale;

    public MD2(){
        m_vertices      = new Vector[]{new Vector()};
        m_glcmds        = new int[]{0};
        m_lightnormals  = new int[]{0};

        num_frames      = 0;
        num_xyz         = 0;
        num_glcmds      = 0;

        m_texid         = 0;
        m_scale         = 1.0f;
        setAnim( 0 );
    }
}
