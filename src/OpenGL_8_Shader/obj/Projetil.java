package OpenGL_8_Shader.obj;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import OpenGL_8_Shader.Math3D.Matriz4x4;
import OpenGL_8_Shader.Model.Bilbord;
import OpenGL_8_Shader.Model.VboCube;
import OpenGL_8_Shader.game.Constantes;
import OpenGL_8_Shader.game.GameMain;
import OpenGL_8_Shader.shaders.ShaderProgram;

public class Projetil extends Object3D {
	public Bilbord bilbord = null;
	FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(16);
	int timerVida = 0;
	int dano = 50;
	
	public Projetil(float x, float y, float z) {
		super(x, y, z);
		raio = 0.1f;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void DesenhaSe(ShaderProgram shader) {
		
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, Constantes.texturaTiro);
        
		Matrix4f modelm = new Matrix4f();
		modelm.setIdentity();
		
		//System.out.println(""+x+" "+y+" "+z);
		modelm.translate(new Vector3f(x,y,z)); 
		modelm.scale(new Vector3f(raio,raio,raio));
		modelm.rotate(GameMain.aviao.rotyAngle,new Vector3f(0,1,0));
		
		int modellocation = glGetUniformLocation(shader.programID, "model");
		modelm.storeTranspose(matrixBuffer);
		matrixBuffer.flip();
		glUniformMatrix4fv(modellocation, false, matrixBuffer);	
		
		bilbord.draw();
	}
	
	@Override
	public void SimulaSe(long diftime) {
		// TODO Auto-generated method stub
		super.SimulaSe(diftime);
		timerVida+=diftime;
		if(timerVida>10000) {
			vivo = false;
		}
		
		for(int i = 0; i < Constantes.listaDeObjetos.size();i++) {
			Object3D obj = Constantes.listaDeObjetos.get(i);
			if(obj.pai==this||obj==this) {
				continue;
			}
			
			float dx = obj.x - x;
			float dy = obj.y - y;
			float dz = obj.z - z;
			
			float somaraio = raio+obj.raio;
			if(somaraio*somaraio>(dx*dx+dy*dy+dz*dz)) {
				obj.life-=dano;
				if(obj.life<=0) {
					obj.vivo = false;
				}
				vivo = false;
				continue;
			}
			
		}		
	}

}
