package concurrencia;

public class LectorEscritorSemaforos extends LectorEscritor {
	/*Esta clase recoge la funcionalidad asociada a la lectura y escritura con semáforos*/
	// Está implementada con paso de testigo y prioridad para los escritores.
	private Semaforo e;
	private Semaforo r;
	private Semaforo w;
	
	private int nr = 0;
	private int nw = 0;
	private int dr = 0;
	private int dw = 0;
	
	public LectorEscritorSemaforos() {
		e = new Semaforo(1);
    	r = new Semaforo(0);
    	w = new Semaforo(0);
	}
	
	@Override
	public void requestRead() throws InterruptedException {
		try {
			e.acquire();
			if(nw>0 || dw>0) {/*Si hay alg�n escritor escribiendo o esperando, espero*/
				dr++;
				e.release();
				r.acquire();
			}
			
			nr++;
			
			if(dr>0) {/*Despierto al resto de lectores*/
				dr--;
				r.release();
			}
			
			else e.release();
		}catch(InterruptedException e) {}
	}
	@Override
	public void releaseRead() {
		try {
			e.acquire();
			nr--;
			if(nr==0 && dw>0) { /*Despertamos solo a uno*/
				dw--;
				w.release();
			}
			else e.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void requestWrite() throws InterruptedException {
		e.acquire();
		if(nr>0 || nw>0) { 
			dw++; /*Aumenta el contador de escritores en espera*/
			e.release();/*Soltamos el sem�foro de entrada*/
			
			//Aqu� esperan los escritores
			w.acquire(); /*Esperamos a que los escritores puedan entrar*/
		}
		
		nw++;
		 
		e.release(); /*Suelta el sem�foro*/
		
	}
	@Override
	public void releaseWrite() {
		try {
e.acquire();
			
			nw--;
			
			if(dw>0) {/*Si hay alg�n escritor esperando*/
				dw--;
				w.release();/*Despertamos al escritor porque puede entrar*/
			}
			
			else if(dr>0) {/*Si no hay ning�n escritor esperando pero si alg�n lector*/
				dr--;
				r.release(); /*Despertamos al reader*/
			}
			else e.release(); /*Si soy el �ltimo suleto el mutex*/
         } catch (InterruptedException e1) {
				e1.printStackTrace();
		} 
	}

}

