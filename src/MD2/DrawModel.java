package MD2;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import OpenGL_4.Math3D.Vetor3D;
import OpenGL_4.obj.Esfera3D;
import OpenGL_4.obj.ObjModel;
import OpenGL_4.obj.Personagem;
import OpenGL_4.obj.Vector3f;
import OpenGL_4.util.TextureLoader;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.vector.Vector4f;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

class ModelDraw {
    ModelDraw() {
        frame_num = 0;
        frame_incr = 0.25f;
        draw = true;
        ambient =  new Vetor3D(0.1f, 0.1f, 0.1f, 1);
        diffuse =  new Vetor3D(0.8f, 0,    0,    1);
        specular = new Vetor3D(0.6f, 0.6f, 0.6f, 1);
        shininess = 64;
    }

    Model mod;
    Frame interp_frame;
    float frame_num;
    float frame_incr;

    Vetor3D ambient;
    Vetor3D diffuse;
    Vetor3D specular;
    float shininess;
    boolean draw;
};

public class DrawModel {

    private ModelDraw m = new ModelDraw();

    private long window;

    int windowW = 1200;
    int windowH = 1000;

    Model mod;

    Frame interp_frame;


    private void initModel() {
        try {
            MD2 teste = new MD2();
            this.mod = MD2.loadMD2("src/MD2/female.md2");
            m = new ModelDraw();
            m.mod = mod;
            m.interp_frame = (Frame) m.mod.f[0].clone();
            m.ambient.multiplicaVetor3d(m.diffuse);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        initModel();

        init();

        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
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

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

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
        while ( !glfwWindowShouldClose(window) ) {
            // draw each triangle
            /*for( int i = 0; i < this.mod.f.length; i++ ) {
                drawFrame(i);
            }*/


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
            //glEnable(GL_LIGHT1);
            glEnable(GL_COLOR_MATERIAL);
            //glColorMaterial(GL_FRONT, GL_DIFFUSE);

            glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

            //float[] mat_ambient = {0.1f, 0.1f, 0.1f, 1.0f};
            //float[] mat_diffuse = { 0.1f, 0.1f, 0.1f, 1.0f };
            //float[] mat_specular = { 0.0f, 0.0f, 0.0f, 0.0f };
            //float[] mat_shininess = {0.0f,0,0,0 };

            //glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
            //glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
            //glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
            //glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);

            //glEnable(GL_BLEND);


            angle++;
            glEnable(GL_DEPTH_TEST);


            glLoadIdentity();

//            glTranslated(-aviao.x,-(aviao.y+0.75f),-(aviao.z+2.0f));
//            //GLU.gluLookAt(posX,posY+0.75f,posZ+2.0f,posX,posY+0.75f,posZ,0.0f,1.0f,0.0f);
//
//            for(int i = 0; i < listaDeEsferas.size();i++) {
//                listaDeEsferas.get(i).DesenhaSe();
//            }
//
//
//
//
//
//
//
//            glEnable(GL_TEXTURE_2D);
//            glBindTexture(GL_TEXTURE_2D, texturaDoDennis);
//
//            aviao.DesenhaSe();

		   glPushMatrix();

		   glTranslated(0, 0.5, 0);
		   glRotatef(90, 1.0f, 0.0f, 0.0f);
		   glScalef(0.005f, 0.005f, 0.005f);

		   glPopMatrix();



            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            frame++;
            difTime = actualTime-System.currentTimeMillis();
            actualTime = System.currentTimeMillis();
            if((lasttime/1000)!=(actualTime/1000)) {
                //System.out.println("FPS "+frame);
                frame=0;
                lasttime = actualTime;
            }

        }
    }

    public static void main(String[] args) {
        DrawModel model = new DrawModel();
        model.run();
    }

}
