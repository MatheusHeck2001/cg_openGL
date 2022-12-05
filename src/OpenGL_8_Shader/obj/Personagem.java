package OpenGL_8_Shader.obj;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

//import Math3D.Matriz4x4;
import OpenGL_8_Shader.Math3D.Vetor3D;
import OpenGL_8_Shader.game.Constantes;
import OpenGL_8_Shader.shaders.ShaderProgram;

public class Personagem extends Object3D {
	Sphere sphere = new Sphere();
	
	ObjModel model = null;
	float vel = 2.0f;
	
	public float rotxAngle = 0;
	public float rotyAngle = (float)Math.PI;
	public float rotzAngle = 0;
	
	public Vector3f frente;
	public Vector3f direita;
	public Vector3f up;
	
	float raio = 0.4f;
	FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(16);

	public boolean rotateUp = false;
	public boolean rotateDown = false;
	
	public boolean rotateLeft = false;
	public boolean rotateRight = false;
	
	public boolean rotateZplus = false;
	public boolean rotateZminus = false;
	
	public boolean FIRE = false;
	int timertiro = 0;

	public Personagem(float x, float y, float z,ObjModel model) {
		super(x, y, z);
		this.model = model;
		frente = new Vector3f(0,0,-1);
		direita = new Vector3f(1,0,0);
		up = new Vector3f(0,1,0);
	}
	
	@Override
	public void SimulaSe(long diftime) {
		timertiro+=diftime;
		float oldx = x;
		float oldy = y;
		float oldz = z;
		
		if(rotateLeft) {
			float angrot = 1.5707f*diftime/1000.0f;
			setRotY(angrot);
		}
		if(rotateRight) {
			float angrot = 1.5707f*diftime/1000.0f;
			setRotY(-angrot);
		}

		if(rotateUp) {
			float angrot = 1.5707f*diftime/1000.0f;
			setRotX(angrot);
		}
		if(rotateDown) {
			float angrot = 1.5707f*diftime/1000.0f;
			setRotX(-angrot);
		}
		
		if(rotateZplus) {
			float angrot = 1.5707f*diftime/1000.0f;
			setRotZ(angrot);
		}
		if(rotateZminus) {
			float angrot = 1.5707f*diftime/1000.0f;
			setRotZ(-angrot);
		}
		
		x += frente.x*vel*diftime/1000.0f;
		y += frente.y*vel*diftime/1000.0f;
		z += frente.z*vel*diftime/1000.0f;

		//Testar Colisao
		
		for(int i = 0; i < Constantes.listaDeObjetos.size();i++) {
			Object3D obj = Constantes.listaDeObjetos.get(i);
			if(obj.pai==this) {
				continue;
			}
			
			float dx = obj.x - x;
			float dy = obj.y - y;
			float dz = obj.z - z;
			
			float somaraio = raio+obj.raio;
			if(somaraio*somaraio>(dx*dx+dy*dy+dz*dz)) {
				//COLIDIU
				x = oldx;
				y = oldy;
				z = oldz;
				
				obj.vx = frente.x*vel*1.5f;
				obj.vy = frente.y*vel*1.5f;
				obj.vz = frente.z*vel*1.5f;
			}
		}
		
		if(FIRE&&timertiro>100) {
			Projetil p = new Projetil(x+frente.x+direita.x*0.2f,y+frente.y+direita.y*0.2f,z+frente.z+direita.z*0.2f);
			p.vx = frente.x*12.0f;
			p.vy = frente.y*12.0f;
			p.vz = frente.z*12.0f; 
			p.bilbord = Constantes.bilbord;
			p.pai = this;
			
			Constantes.listaDeObjetos.add(p);
			
			p = new Projetil(x+frente.x-direita.x*0.2f,y+frente.y-direita.y*0.2f,z+frente.z-direita.z*0.2f);
			p.vx = frente.x*12.0f;
			p.vy = frente.y*12.0f;
			p.vz = frente.z*12.0f; 
			p.bilbord = Constantes.bilbord;
			p.pai = this;
			
			Constantes.listaDeObjetos.add(p);
			timertiro = 0;
		}
	}
	
	@Override
	public void DesenhaSe(ShaderProgram shader) {
			Matrix4f modelm = new Matrix4f();
			modelm.setIdentity();

			modelm.translate(new Vector3f(x,y,z));
			
			modelm.rotate(rotxAngle, new Vector3f(1.0f, 0.0f, 0.0f));
			modelm.rotate(rotyAngle, new Vector3f(0.0f, 1.0f, 0.0f));
			modelm.rotate(rotzAngle, new Vector3f(0.0f, 0.0f, 1.0f));
			modelm.scale(new Vector3f(0.005f,0.005f,0.005f));
			
			int modellocation = glGetUniformLocation(shader.programID, "model");
			
			modelm.storeTranspose(matrixBuffer);
			matrixBuffer.flip();
			glUniformMatrix4fv(modellocation, false, matrixBuffer);	
		   		   
		    model.draw();
	}
	
	public void setRotY(float ang) {
		rotyAngle+=ang;
		
		Matrix4f mat = new Matrix4f();
		mat.rotate(ang, up);

		Vector4f vec = new Vector4f();
		Matrix4f.transform(mat, new Vector4f(frente.x, frente.y, frente.z, 1.0f), vec);
		frente = new Vector3f((float)vec.x,(float)vec.y,(float)vec.z);
		
		mat.transform(mat, new Vector4f(direita.x, direita.y, direita.z, 1.0f), vec);
		direita = new Vector3f((float)vec.x,(float)vec.y,(float)vec.z);
	}

	public void setRotX(float ang) {
		rotxAngle+=ang;

		Matrix4f mat = new Matrix4f();
		mat.rotate(ang, direita);

		Vector4f vec = new Vector4f();
		Matrix4f.transform(mat, new Vector4f(frente.x, frente.y, frente.z, 1.0f), vec);
		frente = new Vector3f((float)vec.x,(float)vec.y,(float)vec.z);

		mat.transform(mat, new Vector4f(direita.x, direita.y, direita.z, 1.0f), vec);
		direita = new Vector3f((float)vec.x,(float)vec.y,(float)vec.z);
	}
	
	public void setRotZ(float ang) {
		rotzAngle+=ang;
		
		Matrix4f mat = new Matrix4f();
		mat.rotate(ang, frente);

		Vector4f vec = new Vector4f();
		Matrix4f.transform(mat, new Vector4f(up.x, up.y, up.z, 1.0f), vec);
		up = new Vector3f((float)vec.x,(float)vec.y,(float)vec.z);

		mat.transform(mat, new Vector4f(direita.x, direita.y, direita.z, 1.0f), vec);
		direita = new Vector3f((float)vec.x,(float)vec.y,(float)vec.z);
	}
}
