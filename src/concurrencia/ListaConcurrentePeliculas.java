package concurrencia;

import java.util.ArrayList;

import utils.Pelicula;

public class ListaConcurrentePeliculas {
	
	private ArrayList<Pelicula> lista;
	private LectorEscritor sinc;
	
	public ListaConcurrentePeliculas() {
		lista = new ArrayList<Pelicula>();
		sinc = new LectorEscritorSemaforos();
	}
	
	public ListaConcurrentePeliculas(ArrayList<Pelicula> peliculas) {
		lista = peliculas;
		sinc = new LectorEscritorSemaforos();
	}
	
	public void add(Pelicula p) throws InterruptedException {
		sinc.requestWrite();
		lista.add(p);
		sinc.releaseWrite();
	}
	
	public void addAll(ArrayList<Pelicula> peliculas) throws InterruptedException {
		sinc.requestWrite();
		lista.addAll(peliculas);
		sinc.releaseWrite();
	}
	
	public Pelicula find(String titulo) throws InterruptedException {
		Pelicula p = null;
		sinc.requestRead();
		for (Pelicula pel : lista) {
			if (pel.getTitulo().equals(titulo)) {
				p = pel;
				break;
			}
		}
		sinc.releaseRead();
		return p;
	}
	
	public ArrayList<Pelicula> getLista() throws InterruptedException {
		ArrayList<Pelicula> lis;
		sinc.requestRead();
		lis = lista;
		sinc.releaseRead();
		return lis;
	}
	
	public void setLista(ArrayList<Pelicula> peliculas) throws InterruptedException {
		sinc.requestWrite();
		lista = peliculas;
		sinc.releaseWrite();
	}
}
