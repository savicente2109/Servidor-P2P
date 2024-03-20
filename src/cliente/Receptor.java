package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import concurrencia.Monitor;
import mensajes.MensajeFicheroDescargado;
import utils.Pelicula;

public class Receptor extends Thread {
	
	private String ipEmisor;
	private int puerto;
	private Socket sc;
	private ObjectInputStream fin;
	private Usuario usr; // Referencia al usuario
	private ObjectOutputStream foutCli; // Para mandar el mensaje de descarga finalizada
	
	public Receptor(String ipEmisor, int puerto, Usuario usr, ObjectOutputStream foutCli) {
		this.ipEmisor = ipEmisor;
		this.puerto = puerto;
		System.out.println("RECEPTOR EN PUERTO: " + puerto);
		this.usr = usr;
		this.foutCli = foutCli;
	}
	
	public void run() {
		try {
			sc = new Socket(ipEmisor, puerto);
			System.out.println("Socket receptor creado.");
			fin = new ObjectInputStream(sc.getInputStream());
			
			Pelicula p = (Pelicula) fin.readObject();
			
			// Acceso protegido por la implementación de lectores-escritores
			// de la clase ListaConcurrentePeliculas.
			usr.addPelicula(p);
			
			foutCli.writeObject(new MensajeFicheroDescargado(usr.getIP(), "localhost", p.getTitulo()));
			
			sc.close();
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}