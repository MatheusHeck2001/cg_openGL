package OpenGL_5.obj;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.*;

import OpenGL_5.game.Constantes;
import org.lwjgl.util.glu.*;

public class Esfera3D extends Object3D {
	Sphere sphere = new Sphere();
	public Vector3f cor = new Vector3f();
	Vector3f frente;
	
	
	public Esfera3D(float x, float y, float z, float r) {
		super(x, y, z);
		raio = r;
		massa = r*50;
		frente = new Vector3f(0,0,1);
	}
	
	@Override
	public void DesenhaSe() {
		glPushMatrix();
		
	    glDisable(GL_TEXTURE_2D);
	    glColor3f(cor.x, cor.y, cor.z);
	    
		glTranslatef(x,y,z);
		
		sphere.draw(raio, 16, 16);
		
		glPopMatrix();
	}
	
	@Override
	public void SimulaSe(long diftime) {
		super.SimulaSe(diftime);
		float oldx = x;
		float oldy = y;
		float oldz = z;
		
		x += vx*diftime/1000.0f;
		y += vy*diftime/1000.0f;
		z += vz*diftime/1000.0f;

		//Testar Colisão

		for(int i = 0; i < Constantes.listaDeObjetos.size(); i++) {
			Object3D obj = Constantes.listaDeObjetos.get(i);
			if (this == obj)
				continue;
			float dx = obj.x - x;
			float dy = obj.y - y;
			float dz = obj.z - z;

			float somaraio = raio+obj.raio;
			if(somaraio*somaraio>(dx*dx+dy*dy+dz*dz)) {
				//COLIDIU
				x = oldx;
				y = oldy;
				z = oldz;

//				obj.vx = frente.x*vel*Math.abs(this.massa - obj.massa);
//				obj.vy = frente.y*vel*Math.abs(this.massa - obj.massa);
//				obj.vz = frente.z*vel*Math.abs(this.massa - obj.massa);

				obj.vx = frente.x*(2*massa/(massa + obj.massa))*vel;
				obj.vy = frente.y*(2*massa/(massa + obj.massa))*vel;
				obj.vz = frente.z*(2*massa/(massa + obj.massa))*vel;

				obj.vel = Math.abs(obj.vz);

				vx = frente.x*((massa-obj.massa)/(massa + obj.massa))*vel;
				vy = frente.y*((massa-obj.massa)/(massa + obj.massa))*vel;
				vz = frente.z*((massa-obj.massa)/(massa + obj.massa))*vel;

				vel = Math.abs(vz);
			}

		}
	}
}