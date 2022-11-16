package math;

public class Vector3f {
	
	public float x, y, z;
	
	public Vector3f() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}
	
	public Vector3f(Vector3f vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static Vector3f ADD(Vector3f vec1, Vector3f vec2) {
		return new Vector3f(vec1.x + vec2.x, vec1.y + vec2.y, vec1.z + vec2.z);
	}
	
	public void add(Vector3f vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
	}
	
	public void multiply(float i) {
		this.x =  this.x * i;
		this.y =  this.y * i;
		this.z =  this.z * i;
	}
}
