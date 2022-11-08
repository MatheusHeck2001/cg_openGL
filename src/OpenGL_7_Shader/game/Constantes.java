package OpenGL_7_Shader.game;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Matrix4f;

import OpenGL_7_Shader.obj.Object3D;

public class Constantes {
	
	private static final float FOV = 45f;
	private static final float NEAR_PLANE = 1.0f;
	private static final float FAR_PLANE = 1000.0f;
	private static int windowW = 1200;
	private static int windowH = 1000;
	
	public static Matrix4f projectionMatrix;

    public static void createProjectionMatrix(){
      projectionMatrix = new Matrix4f();
      
      float width = windowW;
      float height = windowH;
      float aspectRatio = width / height;
      float yScale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
      float xScale = yScale / aspectRatio;
      float frustumLength = FAR_PLANE - NEAR_PLANE;

      projectionMatrix = new Matrix4f();
      projectionMatrix.m00 = xScale;
      projectionMatrix.m11 = yScale;
      projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength);
      projectionMatrix.m23 = -1;
      projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength);
      projectionMatrix.m33 = 0;
    }	
	public static ArrayList<Object3D> listaDeObjetos = new ArrayList<Object3D>();
}
