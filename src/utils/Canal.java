package utils;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Canal {

	ObjectInputStream fin;
	ObjectOutputStream fout;
	
	public Canal(ObjectInputStream fin, ObjectOutputStream fout) {
		this.fin = fin;
		this.fout = fout;
	}
	
	public ObjectOutputStream getFout() {
		return fout;
	}
	
	public ObjectInputStream getFin() {
		return fin;
	}
	
}
