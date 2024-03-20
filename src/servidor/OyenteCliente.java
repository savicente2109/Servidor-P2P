package servidor;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;

import cliente.Usuario;
import concurrencia.Monitor;
import concurrencia.TablaConcurrenteCanales;
import concurrencia.TablaConcurrenteUsuarios;
import mensajes.*;
import utils.*;

public class OyenteCliente extends Thread {
	
	private Socket sc;
	private TablaConcurrenteUsuarios tUsr;
	private TablaConcurrenteCanales tCanales;
	private String nomUsr;
	private String ipUsr;
	private boolean escuchando = true;
	
	public OyenteCliente(Socket sc, TablaConcurrenteUsuarios tUsr, TablaConcurrenteCanales tCanales) {
		this.sc = sc;
		this.tUsr = tUsr;
		this.tCanales = tCanales;
	}
	
	public void run() {
		
		try (ObjectInputStream fin = new ObjectInputStream(sc.getInputStream());			
				ObjectOutputStream fout = new ObjectOutputStream(sc.getOutputStream())) {
			// Con el try-with-resources los flujos se cierran solos
			
			while(escuchando) {			
				
				Mensaje m = (Mensaje) fin.readObject(); //Espera a que le llegue algo
				
				switch(m.getTipo()) {
				
				case M_CONEXION:
					mConexion((MensajeConexion) m, fin, fout);
					break;
					
				case M_LISTA_USR:
					mListaUsr(fout);
					break;
			
				case M_PEDIR_FICHERO:
					mPedirFichero((MensajePedirFichero) m);
					break;
		
				case M_PREPARADO_CS:
					mPreparadoCS((MensajePreparadoCS) m);
					break;
					
				case M_FICHERO_DESC:
					mFicheroDescargado((MensajeFicheroDescargado) m, fout);
					break;
					
				case M_CERRAR_CONEXION:
					mCerrarConexion(fout);
					break;
					
				default:
					break;
				}
			}
			sc.close();
			System.out.println("Socket cerrado.");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	private void mConexion(MensajeConexion m, ObjectInputStream fin, ObjectOutputStream fout) throws InterruptedException, IOException {
		nomUsr = m.getNombre();
		Usuario u = tUsr.get(nomUsr);
		if (u == null) { // Nuevo usuario
			u = new Usuario(nomUsr, m.getOrigen(), m.getPeliculasNuevas());
			tUsr.put(nomUsr, u);
		}
		else if (u.getConectado()) { // Ya estaba conectado
			fout.writeObject(new MensajeConfirmacionConexion("localhost", u.getIP(), false, -1, null));
			fout.flush();
			escuchando = false;
			return;
		}
		else { // Ya estaba registrado y no conectado
			tUsr.addPeliculas(nomUsr, m.getPeliculasNuevas());
			tUsr.setConectado(nomUsr, true);
		}
		ipUsr = u.getIP();
		tCanales.put(nomUsr, new Canal(fin, fout));
		fout.writeObject(new MensajeConfirmacionConexion("localhost", ipUsr, true, tUsr.size(), tUsr.getPeliculas(nomUsr)));
		fout.flush();
	}
	
	private void mListaUsr(ObjectOutputStream fout) throws InterruptedException, IOException {
		fout.writeObject(new MensajeConfirmacionListaUsuarios("localhost", ipUsr, tUsr.getInfoString()));
		fout.flush();
	}
	
	private void mPedirFichero(MensajePedirFichero m) throws InterruptedException, IOException {
		String nUsr = m.getNombreUsr();
		// Determinar destino (el que hará de emisor)
		Usuario destino = tUsr.get(nUsr);
		// Obtener su flujo
		ObjectOutputStream foutEmisor = tCanales.get(nUsr).getFout();
		// Enviar mensaje al emisor correcto
		foutEmisor.writeObject(new MensajeEmitirFichero("localhost", destino.getIP(), m.getTitulo(), nomUsr));
		foutEmisor.flush();
	}
	
	private void mPreparadoCS(MensajePreparadoCS m) throws InterruptedException, IOException {
		String nomReceptor = m.getNomReceptor();
		// Determinar receptor
		Usuario receptor = tUsr.get(nomReceptor);
		// Obtener su flujo
		ObjectOutputStream foutReceptor = tCanales.get(nomReceptor).getFout();
		foutReceptor.writeObject(new MensajePreparadoSC("localhost", receptor.getIP(), m.getOrigen(), m.getPuerto()));
		foutReceptor.flush();
	}
	
	private void mFicheroDescargado(MensajeFicheroDescargado m, ObjectOutputStream fout) throws InterruptedException, IOException {
		String titulo = m.getTitulo();
		tUsr.addPelicula(nomUsr, new Pelicula(titulo));
		fout.writeObject(new MensajeConfirmacionFicheroDescargado("localhost", ipUsr, titulo));
		fout.flush();
	}
	
	private void mCerrarConexion(ObjectOutputStream fout) throws InterruptedException, IOException {
		tUsr.setConectado(nomUsr, false);
		fout.writeObject(new MensajeConfirmacionCerrarConexion("localhost", ipUsr));
		fout.flush();
		tCanales.remove(nomUsr);
		escuchando = false;
	}
}
