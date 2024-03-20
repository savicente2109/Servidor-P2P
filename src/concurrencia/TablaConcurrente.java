package concurrencia;

import java.util.HashMap;
import java.util.Set;

// Clase paramétrica que guardará la información de cada tabla
public class TablaConcurrente <T> {

	protected HashMap<String, T> tabla;
	protected LectorEscritor sinc;
	
	public TablaConcurrente() {
		tabla = new HashMap<String, T>();
		sinc = new Monitor();
	}
	
	public T get(String nomUsr) throws InterruptedException {
		T objeto;
		sinc.requestRead();
		objeto = tabla.get(nomUsr);
		sinc.releaseRead();
		return objeto;
	}
	
	public void put(String nomUsr, T objeto) throws InterruptedException {
		sinc.requestWrite();
		tabla.put(nomUsr, objeto);
		sinc.releaseWrite();
	}
	
	public void remove(String nomUsr) throws InterruptedException {
		sinc.requestWrite();
		tabla.remove(nomUsr);
		sinc.releaseWrite();
	}
	
	public Set<HashMap.Entry<String, T>> entrySet() throws InterruptedException {
		Set<HashMap.Entry<String, T>> entrySet;
		sinc.requestRead();
		entrySet = tabla.entrySet();
		sinc.releaseRead();
		return entrySet;
	}
	
	public int size() throws InterruptedException {
		int tam;
		sinc.requestRead();
		tam = tabla.size();
		sinc.releaseRead();
		return tam;
	}
	
}
