package OpenGL_4.obj;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import OpenGL_4.Math3D.Matriz4x4;
import OpenGL_4.Math3D.Vetor3D;

public class Personagem extends Object3D {
	
	ObjModel model = null;
	float vel = 0.5f;
	
	float rotxAngle = 0;
	float rotyAngle = 180;
	float rotzAngle = 0;
	
	Vector3f frente;

	public Personagem(float x, float y, float z,ObjModel model) {
		super(x, y, z);
		this.model = model;
		frente = new Vector3f(0,0,1);
	}
	
	@Override
	public void SimulaSe(long diftime) {
		x += frente.x*vel*diftime/1000.0f;
		y += frente.y*vel*diftime/1000.0f;
		z += frente.z*vel*diftime/1000.0f;
	}
	
	@Override
	public void DesenhaSe() {
		   glPushMatrix();
		   
		   glTranslatef(x, y, z);
		   glRotatef(rotxAngle, 1.0f, 0.0f, 0.0f);
		   glRotatef(rotyAngle, 0.0f, 1.0f, 0.0f);
		   glRotatef(rotzAngle, 0.0f, 0.0f, 1.0f);
		   glScalef(0.01f, 0.01f, 0.01f);
		   		   
		   model.desenhaOPENGL();
		   
		   glPopMatrix();
	}
	
	public void setRotY(float ang) {
		rotyAngle+=ang;
		
		Matriz4x4 mat = new Matriz4x4();
		mat.setIdentity();
		mat.setRotateY(-ang);
		Vetor3D vec = mat.multiplicaVetor(new Vetor3D(frente.x, frente.y, frente.z, 1.0f));
		frente = new Vector3f((float)vec.x,(float)vec.y,(float)vec.z);
		

	}

}
