package concurrencia;

import java.util.ArrayList;
import java.util.HashMap;

import cliente.Usuario;
import utils.Pelicula;

public class TablaConcurrenteUsuarios extends TablaConcurrente<Usuario> {
	
	public void setConectado(String nomUsr, boolean b) throws InterruptedException {
		sinc.requestWrite();
		tabla.get(nomUsr).setConectado(b);;
		sinc.releaseWrite();
	}
	
	public void addPelicula(String nomUsr, Pelicula p) throws InterruptedException {
		sinc.requestWrite();
		tabla.get(nomUsr).addPelicula(p);
		sinc.releaseWrite();
	}
	
	public void addPeliculas(String nomUsr, ArrayList<Pelicula> peliculas) throws InterruptedException {
		sinc.requestWrite();
		tabla.get(nomUsr).addPeliculas(peliculas);
		sinc.releaseWrite();
	}
	
	public ArrayList<Pelicula> getPeliculas(String nomUsr) throws InterruptedException {
		ArrayList<Pelicula> info;
		sinc.requestRead();
		info = tabla.get(nomUsr).getInfoCompartida();
		sinc.releaseRead();
		return info;
	}
	
	public String getInfoString() throws InterruptedException {
		String lista = "\nUSUARIOS CONECTADOS:";
		for (HashMap.Entry<String, Usuario> entry : entrySet()) {
		    String nom = entry.getKey();
		    Usuario usuario = entry.getValue();
		    if (usuario.getConectado()) {
				lista = lista + "\n\n" + nom + "\nPELÍCULAS:\n";
				for (Pelicula p : usuario.getInfoCompartida())
					lista = lista + p.getTitulo() + "\n";
			}
		}
		return lista;
	}
	
}
