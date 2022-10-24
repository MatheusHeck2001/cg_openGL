package MD2;

public class FileHeader {
    public int ident;         // magic number
    public int version;       // md2 version
    public int skinwidth;     // width of the texture
    public int skinheight;    // height of the texture
    public int framesize;     // size of one frame in bytes
    public int num_skins;     // number of textures
    public int num_xyz;       // number of vertices
    public int num_st;        // number of texture coordinates
    public int num_tris;      // number of triangles
    public int num_glcmds;    // number of opengl commands
    public int num_frames;    // total number of frames
    public int ofs_skins;     // offset to skin names - each skin is a MAX_SKINNAME string
    public int ofs_st;        // byte offset from start for stverts
    public int ofs_tris;      // offset for dtriangles
    public int ofs_frames;    // offset for first frame
    public int ofs_glcmds;    // offset to opengl commands
    public int ofs_end;       // offset to end of file
}
