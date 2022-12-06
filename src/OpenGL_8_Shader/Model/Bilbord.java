package OpenGL_8_Shader.Model;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
//import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.FloatBuffer;

public class Bilbord extends Model{
	int vbo_vertex_handle;
	int vbo_color_handle;
	int vbo_texture_handle;
	
	int vertices = 6;
	int vertex_size = 3; // X, Y, Z,
	int color_size = 3; // R, G, B,
	int texture_size = 2;
	
	@Override
	public void load() {


		FloatBuffer vertex_data = BufferUtils.createFloatBuffer(vertices * vertex_size);
		float v1[] = { -1f, -1f, -1f, };
		float v2[] = { 1f, -1f, -1f, };
		float v3[] = { 1f,  1f, -1f, };
		float v4[] = { -1f,  1f, -1f, };

		//cor
		float c1[] = { 1f, 0f, 0f, };
		float c2[] = { 0f, 1f, 0f, };
		float c3[] = { 0f,  0f,  1f, };
		float c4[] = { 1f,  0f,  1f, };
		
		//uv
		float uv1[] = { 0f, 0f};
		float uv2[] = { 1f, 0f};
		float uv3[] = { 1f, 1f};
		float uv4[] = { 0f, 1f};	
		
		//front
		vertex_data.put(v1);
		vertex_data.put(v2);
		vertex_data.put(v3);
		
		vertex_data.put(v3);
		vertex_data.put(v4);
		vertex_data.put(v1);
		
		vertex_data.flip();

		FloatBuffer color_data = BufferUtils.createFloatBuffer(vertices * color_size);
		
		color_data.flip();
		
		FloatBuffer texture_data = BufferUtils.createFloatBuffer(vertices * texture_size);
		//front
		texture_data.put(uv1);
		texture_data.put(uv2);
		texture_data.put(uv3);
		
		texture_data.put(uv3);
		texture_data.put(uv4);
		texture_data.put(uv1);
		
		texture_data.flip();		

		vbo_vertex_handle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo_vertex_handle);
		glBufferData(GL_ARRAY_BUFFER, vertex_data, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		vbo_color_handle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo_color_handle);
		glBufferData(GL_ARRAY_BUFFER, color_data, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		vbo_texture_handle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo_texture_handle);
		glBufferData(GL_ARRAY_BUFFER, texture_data, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void draw() {
		glBindBuffer(GL_ARRAY_BUFFER, vbo_vertex_handle);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0,vertex_size,GL_FLOAT,false,0,0);

		glBindBuffer(GL_ARRAY_BUFFER, vbo_color_handle);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1,color_size,GL_FLOAT,false,0,0);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo_texture_handle);
		glEnableVertexAttribArray(3);
		glVertexAttribPointer(3,texture_size,GL_FLOAT,false,0,0);

		glDrawArrays(GL_TRIANGLES, 0, vertices);
	}
}
