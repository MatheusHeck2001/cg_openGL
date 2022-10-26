package OpenGL_5.obj;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.*;

public class Esfera3D extends Object3D {
	Sphere sphere = new Sphere();
	public Vector3f cor = new Vector3f();
	
	
	public Esfera3D(float x, float y, float z, float r) {
		super(x, y, z);
		raio = r;
		massa = r*50;
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
		
		x += vx*diftime/1000.0f;
		y += vy*diftime/1000.0f;
		z += vz*diftime/1000.0f;
	}
}
//- Implementar colisões entre as esferas com troca de energia.
//
//- Modificar a movimentação do avião e possibilitar que ele suba e deça.