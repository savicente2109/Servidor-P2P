package concurrencia;
public class LockRompeEmpate extends Lock  {
	
	private Entero[] last;
	private Entero[] in;
	
	public LockRompeEmpate(int M) {
	    super(M);
	    last = new Entero[M];
	    for(int i = 0; i < M; i++) {
	        last[i] = new Entero(0);
	    }

	    in = new Entero[M];
	    for(int i = 0; i < M; i++) {
	        in[i] = new Entero(0);
	    }
	}
	
	@Override
	public void takeLock(int id) {/*Los id van de 1 a M*/
		for (int j = 1; j <= M; j++) {
			
			last[j-1].entero = id; //el último en llegar a la etapa j es i
			
			in[id-1].entero = j; //id esta en la etapa j

			for (int k = 1; k <= M; k++) {
				if (k == id) continue;
				/*Si soy el último en la etapa y hay alguien por delante espero*/
				while (in[k-1].entero >= in[id-1].entero && last[j-1].entero == id) {}
			}
		}
	}

	@Override
	public void releaseLock(int id) {
		in[id-1].entero = 0;
	}
}