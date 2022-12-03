package OpenGL_8_Shader.obj;

import shaders.ShaderProgram;

public class Object3D {
	public float x;
	public float y;
	public float z;
	
	public float vx = 0;
	public float vy = 0;
	public float vz = 0;
	
	float raio = 0; 
	public Object3D pai = null;
	
	public boolean vivo = true;
	public float life = 100;
	
	public Object3D(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void DesenhaSe(ShaderProgram shader) {
		
	};
	
	public void SimulaSe(long diftime) {
		//super.SimulaSe(diftime);
		
		x += vx*diftime/1000.0f;
		y += vy*diftime/1000.0f;
		z += vz*diftime/1000.0f;
	};
	
}
