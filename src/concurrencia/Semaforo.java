package concurrencia;

import java.util.concurrent.locks.*;

public class Semaforo{
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condicion = lock.newCondition();
    private int value;

    public Semaforo(int permisos) {
        this.value = permisos;
    }
    

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (value == 0) {
                condicion.await();
            }
            value--;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            value++;
            condicion.signal();
        } finally {
            lock.unlock();
        }
    }

}

