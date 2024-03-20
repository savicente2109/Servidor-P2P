package cliente;
import java.util.ArrayList;

import concurrencia.ListaConcurrentePeliculas;
import concurrencia.Semaforo;
import utils.Pelicula;

public class Usuario {
	
	private String id;
	private String ip;
	private boolean conectado;
	private ListaConcurrentePeliculas infoCompartida;
	
	public Usuario(String id, String ip, ArrayList<Pelicula> infoCompartida) {
		this.id = id;
		this.ip = ip;
		this.conectado = true;
		this.infoCompartida = new ListaConcurrentePeliculas(infoCompartida);
	}
	
	public String getId() {
		return id;
	}
	
	public String getIP() {
		return ip;
	}
	
	public Boolean getConectado() {
		return conectado;
	}
	
	public void setConectado(boolean b) {
		conectado = b;
	}
	
	public ArrayList<Pelicula> getInfoCompartida() throws InterruptedException {
		return infoCompartida.getLista();
	}
	
	public void addPeliculas(ArrayList<Pelicula> peliculas) throws InterruptedException {
		infoCompartida.addAll(peliculas);
	}
	
	public void addPelicula(Pelicula pelicula) throws InterruptedException {
		infoCompartida.add(pelicula);
	}
	
	public void actualizarPeliculas(ArrayList<Pelicula> peliculas) throws InterruptedException {
		infoCompartida.setLista(peliculas);
	}
	
	public Pelicula buscarPelicula(String titulo) throws InterruptedException {
		return infoCompartida.find(titulo);
	}
}
