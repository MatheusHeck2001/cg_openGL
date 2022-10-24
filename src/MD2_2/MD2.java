package MD2_2;

public class MD2 {
    public MD2(){};

    boolean LoadModel(String filename) {
        return false;
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
        m_vertices      = 0;
        m_glcmds        = 0;
        m_lightnormals  = 0;

        num_frames      = 0;
        num_xyz         = 0;
        num_glcmds      = 0;

        m_texid         = 0;
        m_scale         = 1.0f;
        setAnim( 0 );
    }
}
