package OpenGL_4.Math3D;

public class Vetor3D {
	public double x;
	public double y;
	public double z;
	public double w;
	
	public Vetor3D(double x, double y, double z, double w) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "("+x+","+y+","+z+","+w+")";
	}
	
	public void multiplicaVetor3d(Vetor3D vetor3D){
		x *= vetor3D.x;
		y *= vetor3D.y;
		z *= vetor3D.z;
		w *= vetor3D.w;
	}
}
