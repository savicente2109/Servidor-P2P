package concurrencia;

public class Entero {

	public volatile int entero = 0;
	
	public Entero(int n) {
		entero = n;
	}
}
