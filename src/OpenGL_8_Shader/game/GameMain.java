package OpenGL_8_Shader.game;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import OpenGL_8_Shader.Math3D.Matriz4x4;
import OpenGL_8_Shader.Math3D.Vetor3D;
import OpenGL_8_Shader.Model.Bilbord;
import OpenGL_8_Shader.Model.VboCube;
import OpenGL_8_Shader.obj.Esfera3D;
import OpenGL_8_Shader.obj.ObjModel;
import OpenGL_8_Shader.obj.Object3D;
import OpenGL_8_Shader.obj.Personagem;
import OpenGL_8_Shader.shaders.ShaderProgram;
import OpenGL_8_Shader.shaders.StaticShader;
import OpenGL_8_Shader.util.TextureLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.nio.*;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GameMain {

	// The window handle
	private long window;
	ObjModel tankobj = null;

	Random rnd = new Random();

	int windowW = 1200;
	int windowH = 1000;
	
	public static Personagem aviao;

	Vector3f posCameraVector = new Vector3f(0.0f,0.5f,-2.0f);


	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		Constantes.vbocube = new VboCube();
		Constantes.bilbord = new Bilbord();
		
		init();
		
		tankobj = new ObjModel();
		tankobj.loadObj("src/res/x-35_obj.obj");
		tankobj.createTextureBase("./x-35_obj.jpg", 1024, 1024);
		
		aviao = new Personagem(0, -0.75f, -2.0f, tankobj);

		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(windowW, windowH, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
			
			if ( key == GLFW_KEY_W) {
				if(action == GLFW_PRESS) {
					aviao.rotateUp = true;
				}else if(action == GLFW_RELEASE) {
					aviao.rotateUp = false;
				}
			}
			if ( key == GLFW_KEY_S) {
				if(action == GLFW_PRESS) {
					aviao.rotateDown = true;
				}else if(action == GLFW_RELEASE) {
					aviao.rotateDown = false;
				}
			}
			if ( key == GLFW_KEY_A) {
				if(action == GLFW_PRESS) {
					aviao.rotateLeft = true;
				}else if(action == GLFW_RELEASE) {
					aviao.rotateLeft = false;
				}
			}
			if ( key == GLFW_KEY_D) {
				if(action == GLFW_PRESS) {
					aviao.rotateRight = true;
				}else if(action == GLFW_RELEASE) {
					aviao.rotateRight = false;
				}	
			}
			
			if ( key == GLFW_KEY_Q) {
				if(action == GLFW_PRESS) {
					aviao.rotateZplus = true;
				}else if(action == GLFW_RELEASE) {
					aviao.rotateZplus = false;
				}
			}
			if ( key == GLFW_KEY_E) {
				if(action == GLFW_PRESS) {
					aviao.rotateZminus = true;
				}else if(action == GLFW_RELEASE) {
					aviao.rotateZminus = false;
				}	
			}			
			
			if ( key == GLFW_KEY_SPACE) {
				if(action == GLFW_PRESS) {
					aviao.FIRE = true;
				}else if(action == GLFW_RELEASE) {
					aviao.FIRE = false;
				}
			}
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);

		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		
		for(int i = 0; i < 20;i++) {
			for(int j = 0; j < 20;j++) {
				Esfera3D esf = new Esfera3D(i-10,rnd.nextFloat()*4-2,-j-4,rnd.nextFloat()*0.25f+0.01f);
				esf.cor = new Vector3f(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
				esf.vbocube = Constantes.vbocube;

				esf.life = rnd.nextFloat()*300;
				System.out.println(esf.life);
				
				Constantes.listaDeObjetos.add(esf);
			}
		}

	}

	long difTime = 0;
	long actualTime = 0;
	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		
		Constantes.createProjectionMatrix();
		
		ShaderProgram shader = new StaticShader();
		tankobj.load();
		Constantes.vbocube.load();
		Constantes.bilbord.load();

		BufferedImage bfm1 = TextureLoader.loadImage("src/res/nave2.png");
		Constantes.texturaDoGatinho = TextureLoader.loadTexture(bfm1);
		BufferedImage bfm2 = TextureLoader.loadImage("src/res/x35_b.jpg");
		Constantes.texturaDoDennis = TextureLoader.loadTexture(bfm2);
		
		BufferedImage bfm3 = TextureLoader.loadImage("src/res/Tiro01.png");
		Constantes.texturaTiro = TextureLoader.loadTexture(bfm3);

		// Set the clear color
		//glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		//gluPerspective(45, windowH/(float)windowW,-1000.0f,1000f);
		float prop = windowH/(float)windowW;
		 glFrustum(-1f,1f,-1*prop,1*prop,1,1000);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		
		int frame = 0;
		long lasttime  = System.currentTimeMillis();
		
		actualTime= System.currentTimeMillis();
		
		int angle = 0;
		FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(16);
		while ( !glfwWindowShouldClose(window) ) {
			
			aviao.SimulaSe(difTime);
			for(int i = 0; i < Constantes.listaDeObjetos.size();i++) {
				Object3D obj = Constantes.listaDeObjetos.get(i);
				obj.SimulaSe(difTime);
				System.out.println(aviao.life);
				if(!obj.vivo) {
					Constantes.listaDeObjetos.remove(i);
					i--;
				}
				if (!aviao.vivo){
					System.out.println("MORREU");
					return;
				}
			}

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			glEnable(GL_LIGHTING);
			   glShadeModel(GL_SMOOTH);
			   
			   float senoang = (float)Math.sin(Math.toRadians(angle));
			   float cosang = (float)Math.cos(Math.toRadians(angle));

			   float[] lightAmbient = {0.1f, 0.1f, 0.1f, 1.0f}; 
		       float[] lightDiffuse = {0.1f, 0.1f, 0.1f, 1.0f};    
		       float[] lightPosition = {0.0f, senoang*600f, cosang*600f, 1.0f};
		       
		       glLightfv(GL_LIGHT0, GL_AMBIENT, lightAmbient);              
		       glLightfv(GL_LIGHT0, GL_DIFFUSE, lightDiffuse);            
		       glLightfv(GL_LIGHT0, GL_POSITION,lightPosition);
		       
			   float[] lightAmbient2 = {0.0f, 0.0f, 0.0f, 1.0f}; 
		       float[] lightDiffuse2 = {1.0f, 1.0f, 1.0f, 1.0f};    
		       float[] lightPosition2 = {-5f, -5f, 0f, 1.0f};
		       
		       glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbient2);              
		       glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuse2);            
		       glLightfv(GL_LIGHT1, GL_POSITION,lightPosition2);
		       
		       glEnable(GL_LIGHT0);		
			   glEnable(GL_COLOR_MATERIAL);

		       glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
		    
		    shader.start();   

			angle++;
			glEnable(GL_DEPTH_TEST);
		   
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
			
	        glActiveTexture(GL_TEXTURE0);
	        glBindTexture(GL_TEXTURE_2D, Constantes.texturaDoDennis);
	        
	        int loctexture = glGetUniformLocation(shader.programID, "tex");
	        glUniform1i(loctexture, 0);
			
	        // SETA E CARREGA PROJECTION MATRIX
			int projlocation = glGetUniformLocation(shader.programID, "projection");
			
			Constantes.projectionMatrix.storeTranspose(matrixBuffer);
			matrixBuffer.flip();
			glUniformMatrix4fv(projlocation, false, matrixBuffer);
			
			// SETA E CARREGA VIEW MATRIX
			Matriz4x4 mat = new Matriz4x4();
			mat.setIdentity();
			mat.setRotateY(-(aviao.rotyAngle*57.2957f));
			Vetor3D vec = mat.multiplicaVetor(new Vetor3D(posCameraVector.x, posCameraVector.y, posCameraVector.z, 1.0f));			

			Matrix4f view = new Matrix4f();
			view.setIdentity();
			view.rotate(-(aviao.rotyAngle+(float)Math.PI), new Vector3f(0,1,0));
			view.translate(new Vector3f(-(aviao.x+(float)vec.x),-(aviao.y+(float)vec.y),-(aviao.z+(float)vec.z)));

			int viewlocation = glGetUniformLocation(shader.programID, "view");
			
			view.storeTranspose(matrixBuffer);
			matrixBuffer.flip();
			glUniformMatrix4fv(viewlocation, false, matrixBuffer);	
		   
			for(int i = 0; i < Constantes.listaDeObjetos.size();i++) {
				Constantes.listaDeObjetos.get(i).DesenhaSe(shader);
			}
			
	        glActiveTexture(GL_TEXTURE0);
	        glBindTexture(GL_TEXTURE_2D, Constantes.texturaDoDennis);
	        
			aviao.DesenhaSe(shader);

		   shader.stop();
			
			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			frame++;
			difTime = System.currentTimeMillis()-actualTime;
			actualTime = System.currentTimeMillis();
			if((lasttime/1000)!=(actualTime/1000)) {
				System.out.println("FPS "+frame+"  "+Constantes.listaDeObjetos.size());
				frame=0;
				lasttime = actualTime;
			}
		}
	}

	public static void main(String[] args) {
		new GameMain().run();
	}
	
	public static void gluPerspective(float fovy, float aspect, float near, float far) {
	    float bottom = -near * (float) Math.tan(fovy / 2);
	    float top = -bottom;
	    float left = aspect * bottom;
	    float right = -left;
	    glFrustum(left, right, bottom, top, near, far);
	}

}

