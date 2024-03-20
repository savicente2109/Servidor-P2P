package concurrencia;

public abstract class LectorEscritor {
	
	public abstract void requestRead() throws InterruptedException;
	
	public abstract void releaseRead();
	
	public abstract void requestWrite() throws InterruptedException;
	
	public abstract void releaseWrite();

}
