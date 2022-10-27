package OpenGL_5.obj;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Matrix4f;

import OpenGL_5.Math3D.Matriz4x4;
import OpenGL_5.Math3D.Vetor3D;
import OpenGL_5.game.Constantes;

public class Personagem extends Object3D {
	Sphere sphere = new Sphere();
	
	ObjModel model = null;
	float vel = 0.5f;
	
	float rotxAngle = 0;
	float rotyAngle = 180;
	float rotzAngle = 0;
	
	Vector3f frente;
	
	float raio = 0.4f;

	float massa = 30;


	public Personagem(float x, float y, float z,ObjModel model) {
		super(x, y, z);
		this.model = model;
		frente = new Vector3f(0,0,1);
	}
	
	@Override
	public void SimulaSe(long diftime) {
		float oldx = x;
		float oldy = y;
		float oldz = z;
		
		x += frente.x*vel*diftime/1000.0f;
		y += frente.y*vel*diftime/1000.0f;
		z += frente.z*vel*diftime/1000.0f;
		
		//Testar Colisão
		
		for(int i = 0; i < Constantes.listaDeObjetos.size();i++) {
			Object3D obj = Constantes.listaDeObjetos.get(i);
			float dx = obj.x - x;
			float dy = obj.y - y;
			float dz = obj.z - z;
			
			float somaraio = raio+obj.raio;
			if(somaraio*somaraio>(dx*dx+dy*dy+dz*dz)) {
				//COLIDIU
				x = oldx;
				y = oldy;
				z = oldz;
				
				obj.vx = frente.x*vel*(this.massa - obj.massa);
				obj.vy = frente.y*vel*(this.massa - obj.massa);
				obj.vz = frente.z*vel*(this.massa - obj.massa);

				/*obj.vx = frente.x*vel*2*this.massa/(this.massa + obj.massa);
				obj.vy = frente.y*vel*2*this.massa/(this.massa + obj.massa);
				obj.vz = frente.z*vel*2*this.massa/(this.massa + obj.massa);*/

			}
			
		}
		
	}
	
	@Override
	public void DesenhaSe() {
		   glPushMatrix();
		   
		   glTranslatef(x, y, z);
		   glRotatef(rotxAngle, 1.0f, 0.0f, 0.0f);
		   glRotatef(rotyAngle, 0.0f, 1.0f, 0.0f);
		   glRotatef(rotzAngle, 0.0f, 0.0f, 1.0f);
		  

		   glColor3f(1.0f,0.0f ,0.0f );
		   sphere.draw(raio, 16, 16);

		   
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
