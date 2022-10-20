package OpenGL_4.obj;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.*;

public class Esfera3D extends Object3D {
	Sphere sphere = new Sphere();
	public Vector3f cor = new Vector3f();
	
	public Esfera3D(float x, float y, float z) {
		super(x, y, z);
	}
	
	@Override
	public void DesenhaSe() {
		glPushMatrix();
		
	    glDisable(GL_TEXTURE_2D);
	    glColor3f(cor.x, cor.y, cor.z);
	    
		glTranslatef(x,y,z);
		
		sphere.draw(0.1F, 16, 16);
		
		glPopMatrix();
	}
	
	@Override
	public void SimulaSe(long diftime) {
		super.SimulaSe(diftime);
	}
}
