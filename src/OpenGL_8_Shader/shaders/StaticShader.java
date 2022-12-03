package OpenGL_8_Shader.shaders;

public class StaticShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/res/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/res/shaders/fragmentShader.txt";
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "cor");
		super.bindAttribute(2, "normal");
		super.bindAttribute(3, "textuv");
	}
	

}

