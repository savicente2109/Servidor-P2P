package concurrencia;
public abstract class Lock {
	
	protected int M;
	
	public Lock(int M) {
		this.M = M;
	}
	
	public abstract void takeLock(int id);
	
	public abstract void releaseLock(int id);
}
