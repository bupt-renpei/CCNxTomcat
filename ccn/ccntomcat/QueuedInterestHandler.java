package ccntomcat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import org.ccnx.ccn.protocol.Interest;


/**
 * 
 * This is the prior interest queue, used to get more interest from CCNNetworkManager, design
 * for the CCNConnector. Has two queue: one for the subsequence interest; one for the first 
 * interest. it will prior process subsequence queue. Queue uses the LinkedList.
 * @author root
 *
 * @param <E>
 */
public abstract class QueuedInterestHandler<E> implements Runnable {
	ExecutorService pool;
	final int NUMOFTHREAD = 100;

	protected Queue m_interestQueue = new Queue();
	protected HashSet<String> receiveInterest = new HashSet<String>();
	
	class Queue {  
		private LinkedList<Interest> list = new LinkedList<Interest>();  
		public void put(Object v) {  
			if(v instanceof Interest){
				list.addFirst((Interest)v);
			}
			else{
				System.out.println("add error");
				return;
			}
		}  
		public Interest get() {  
			return list.removeLast();  
		}  
		public boolean isEmpty() {  
			return list.isEmpty();  
		}  
	} 
	
	protected boolean _isRunning = false;
	
	/**
	 * Add a Interest to the queue for processing.
	 */
	public void addInterest(E e) {
		Interest receive = (Interest)e;
		if(!receiveInterest.contains(receive.toString())){
			receiveInterest.add(receive.toString());
			m_interestQueue.put(e);				
		}else{
			System.out.println("Contained");
		}
			
	}
		
	/**
	 * Asynchronously dequeue and process Interest
	 */
	public void run() {
		while (_isRunning) {
			Interest e = null;		
			if(!m_interestQueue.isEmpty()){
				try{
					e = m_interestQueue.get();
				}catch(Exception ex){
					ex.printStackTrace();
					m_interestQueue = new Queue();
				}				
			}
			if (null == e) {
				continue;
			}
			process(e);
		}
	}
	
	protected void Shutdown() {
		this._isRunning = false;
	}
	
	/**
	 * Process the Interest from a InterestHandler asynchronously
	 */
	protected abstract void process(Interest e);
	
}
