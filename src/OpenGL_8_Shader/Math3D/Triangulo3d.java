package OpenGL_8_Shader.Math3D;
import java.awt.Graphics2D;

public class Triangulo3d {
	Vetor3D p1;
	Vetor3D p2;
	Vetor3D p3;
	
	public Triangulo3d(Vetor3D p1, Vetor3D p2, Vetor3D p3) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	
	public void desenhaSe(Graphics2D dbg, Matriz4x4 m) {
		
		Vetor3D p1l = m.multiplicaVetor(p1);
		Vetor3D p2l = m.multiplicaVetor(p2);
		Vetor3D p3l = m.multiplicaVetor(p3);
		
		dbg.drawLine((int)p1l.x,(int)p1l.y,(int)p2l.x,(int)p2l.y);
		dbg.drawLine((int)p2l.x,(int)p2l.y,(int)p3l.x,(int)p3l.y);
		dbg.drawLine((int)p3l.x,(int)p3l.y,(int)p1l.x,(int)p1l.y);
	}
	
	public static void desenhaStaticTriangle(Graphics2D dbg, Vetor3D ps1,Vetor3D ps2,Vetor3D ps3) {
	}
}
