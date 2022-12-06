package OpenGL_8_Shader.obj;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.lwjgl.util.glu.*;

import OpenGL_8_Shader.shaders.ShaderProgram;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import OpenGL_8_Shader.Model.VboCube;
import OpenGL_8_Shader.game.Constantes;

public class Esfera3D extends Object3D {

	public Vector3f cor = new Vector3f();
	public VboCube vbocube = null;
	
	FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(16);

	public Esfera3D(float x, float y, float z, float r) {
		super(x, y, z);
		raio = r;
	}
	
	@Override
	public void DesenhaSe(ShaderProgram shader) {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, Constantes.texturaDoGatinho);
		
		Matrix4f modelm = new Matrix4f();
		modelm.setIdentity();

		modelm.translate(new Vector3f(x,y,z));
		modelm.scale(new Vector3f(raio,raio,raio));
		
		int modellocation = glGetUniformLocation(shader.programID, "model");
		
		modelm.storeTranspose(matrixBuffer);
		matrixBuffer.flip();
		glUniformMatrix4fv(modellocation, false, matrixBuffer);	
		
		vbocube.draw();
	}
	
	@Override
	public void SimulaSe(long diftime) {
		super.SimulaSe(diftime);
	}
}
