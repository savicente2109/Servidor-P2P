package concurrencia;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor extends LectorEscritor {
	final private Lock lock;
	private final Condition okread ;
	final private Condition okwrite;

	int nr;
	int nw;

	public Monitor() {
		lock = new ReentrantLock(true);
    	okread = lock.newCondition();
    	okwrite = lock.newCondition();
		nr = 0;
		nw = 0;
	}
	
	public void requestRead() throws InterruptedException {
		lock.lock();
		while(nw>0) { /*Si hay escritores espero a que deje de haberlos*/
			okread.await();
		}
		
		nr = nr + 1;
		lock.unlock();
	}
	
	public void releaseRead() {
		lock.lock();
		nr = nr -1;
		if(nr == 0) okwrite.signal();
		lock.unlock();
	}
	
	public void requestWrite() throws InterruptedException {
		lock.lock();
		while(nr>0 || nw >0) { /*Si hay lectores u otro escritor espero*/
			okwrite.await();
		}
		
		nw = nw + 1;
		lock.unlock();
	}
	
	public void releaseWrite() {
		lock.lock();
		nw = nw - 1;
		okwrite.signal();
		okread.signalAll();
		lock.unlock();
	}

}
