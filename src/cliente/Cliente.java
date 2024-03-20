package cliente;
import java.io.IOException;
import concurrencia.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import mensajes.MensajeCerrarConexion;
import mensajes.MensajeListaUsuarios;
import mensajes.MensajePedirFichero;
import utils.Pelicula;

public class Cliente {
	
	private Scanner scanner;
	private Usuario usr;
	private Lock l;
	private Socket sc;
	private OyenteServidor os;
	
	public static void main(String args[]) {
		
		try {
			Cliente cliente = new Cliente();
			
			cliente.inicializar();
			cliente.menuUsuario();
			cliente.salir();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void inicializar() throws IOException {
		l = new LockRompeEmpate(2);
		
	
		System.out.println("Nombre de usuario: ");
		scanner = new Scanner(System.in);
		String nombre = scanner.nextLine();
		
		ArrayList<Pelicula> peliculas = pedirPeliculas();
		
		usr = new Usuario(nombre, "localhost", peliculas);
		
		sc = new Socket("localhost", 999);
		
		os = new OyenteServidor(sc, usr, this, l);
		os.start();
	}
	
	private Pelicula pedirPelicula() {
		System.out.println("Título: ");
		return new Pelicula(scanner.nextLine());
	}
	
	private ArrayList<Pelicula> pedirPeliculas() {
		ArrayList<Pelicula> peliculas = new ArrayList<Pelicula> ();
		
		System.out.println("Subir película (s/n): ");
		String op = scanner.nextLine();
		while (op.equals("s")) {
			
			peliculas.add(pedirPelicula());
			
			System.out.println("Subir película (s/n): ");
			op = scanner.nextLine();
		}
		
		return peliculas;
	}
	
	private void menuUsuario() throws IOException {
		int op = 0;
		do {
			l.takeLock(1); // Para sincronizar el bloque de println.
			System.out.println("----------------------");
			System.out.println("         MENU         ");
			System.out.println("----------------------");
			System.out.println("");
			System.out.println("   1. Ver lista de usuarios.");
			System.out.println("   2. Descargar pel�cula.");
			System.out.println("   3. Salir.");	
			l.releaseLock(1);
			
			op = scanner.nextInt();
			scanner.nextLine(); // Eliminamos el \n del scanner tras leer el entero
								// para que no lo lea el siguiente nextLine().
			
			
			if (op == 1) {
				os.getFout().writeObject(new MensajeListaUsuarios(usr.getIP(), "localhost"));
				os.getFout().flush();
			}
			
			else if (op == 2) {
				
				System.out.println("Título de la película: ");
				String titulo = scanner.nextLine();
				System.out.println("Usuario que la comparte: ");
				String nombre = scanner.nextLine();
				os.getFout().writeObject(new MensajePedirFichero(usr.getIP(), "localhost", nombre, titulo));
				os.getFout().flush();
			}
			
		} while (op != 3);
		
		os.getFout().writeObject(new MensajeCerrarConexion(usr.getIP(), "localhost"));
		os.getFout().flush();
		
		
	}
	
	private void salir() throws InterruptedException, IOException {
		os.join();
		sc.close();
		System.out.println("Socket cerrado.");
	}

	public void actualizarUsr(ArrayList<Pelicula> p) throws InterruptedException {
		usr.actualizarPeliculas(p);
	}
}
