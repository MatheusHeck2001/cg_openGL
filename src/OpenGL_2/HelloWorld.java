package OpenGL_2;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import OpenGL_2.obj.ObjModel;

//import com.sun.org.apache.xerces.internal.dom.DeepNodeListImpl;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class HelloWorld {

	// The window handle
	private long window;
	ObjModel tankobj = null;
	float rotxAngle = 0;
	float rotyAngle = 0;
	float rotzAngle = 0;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		
		tankobj = new ObjModel();
		tankobj.loadObj("tank.obj");
		
		
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
		window = glfwCreateWindow(800, 600, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
			
			if ( key == GLFW_KEY_W) {
			}
			if ( key == GLFW_KEY_S) {
			}
			if ( key == GLFW_KEY_A) {
			}
			if ( key == GLFW_KEY_D) {
			}
			if ( key == GLFW_KEY_Q) {
				rotxAngle+=15;
			}
			if ( key == GLFW_KEY_E) {
				rotxAngle-=15;
			}
			if ( key == GLFW_KEY_Z) {
				rotyAngle+=15;
			}
			if ( key == GLFW_KEY_X) {
				rotyAngle-=15;
			}
			if ( key == GLFW_KEY_N) {
				rotzAngle+=15;
			}
			if ( key == GLFW_KEY_M) {
				rotzAngle-=15;
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
	}

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45, 600f/800f,-1.0f,100);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		
		int frame = 0;
		long lasttime  = System.currentTimeMillis();
		
		int angle = 0;
		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			
			
			glEnable(GL_LIGHTING);
			   glShadeModel(GL_SMOOTH);
			   
			   float senoang = (float)Math.sin(Math.toRadians(angle));
			   float cosang = (float)Math.cos(Math.toRadians(angle));

			   float[] lightAmbient = {0.0f, 0.0f, 0.0f, 1.0f}; 
		       float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};    
		       float[] lightPosition = {cosang*5f, senoang*5f, -1f, 1.0f};
		       
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
		       glEnable(GL_LIGHT1);	
			   glEnable(GL_COLOR_MATERIAL);
		       glColorMaterial(GL_FRONT, GL_DIFFUSE);	
		       
		       

			angle++;
			glEnable(GL_DEPTH_TEST);
			
			glLoadIdentity();
			glRotatef(angle, 0.0f, 0.0f, 1.0f);
			
			glBegin(GL_TRIANGLES);
			  glColor3f(1.0f, 0.0f, 0.0f);
			  glVertex3f(-1.0f,-0.25f,0.1f);
			  
			  glColor3f(0.0f, 1.0f, 0.0f);
		      glVertex3f(-0.5f,-0.25f,0.1f);
		      
		      glColor3f(0.0f, 0.0f, 1.0f);
		      glVertex3f(-0.75f,0.25f,0.1f);
		   glEnd();
		   
		   glLoadIdentity();
		   
		   glRotatef(rotxAngle, 1.0f, 0.0f, 0.0f);
		   glRotatef(rotyAngle, 0.0f, 1.0f, 0.0f);
		   glRotatef(rotzAngle, 0.0f, 0.0f, 1.0f);
		   glScalef(0.01f, 0.01f, 0.01f);
		   tankobj.desenhaOPENGL();
		   
		   glLoadIdentity();
		   
		   glRotatef(80, 1.0f, 0.0f, 0.0f);
		      
		   glBegin(GL_TRIANGLES);
		      glColor3f(0.0f, 0.0f, 1.0f);
		      glVertex3f(0.5f,-0.25f,0.0f);
		      glVertex3f(1.0f,-0.25f,0.0f);
		      glVertex3f(0.75f,0.25f,0.0f);
		    glEnd();
			
			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			frame++;
			long actualTime = System.currentTimeMillis();
			if((lasttime/1000)!=(actualTime/1000)) {
				System.out.println("FPS "+frame);
				frame=0;
				lasttime = actualTime;
			}
			
		}
	}

	public static void main(String[] args) {
		new HelloWorld().run();
	}
	
	public static void gluPerspective(float fovy, float aspect, float near, float far) {
	    float bottom = -near * (float) Math.tan(fovy / 2);
	    float top = -bottom;
	    float left = aspect * bottom;
	    float right = -left;
	    glFrustum(left, right, bottom, top, near, far);
	}

}

