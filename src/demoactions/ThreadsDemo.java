

package demoactions;

import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.Buffer;

/**
http://tutorials.jenkov.com/java-concurrency/creating-and-starting-threads.html
https://stackoverflow.com/questions/5853167/runnable-with-a-parameter
https://stackoverflow.com/questions/13327571/in-a-simple-to-understand-explanation-what-is-runnable-in-java
https://docs.oracle.com/javase/tutorial/essential/concurrency/simple.html
*/

public class ThreadsDemo {
	
	/**
	jEdit Help:
	A Buffer represents the contents of an open text file as it is maintained 
	in the computer's memory (as opposed to how it may be stored on a disk).
	In a BeanShell script, you can obtain the current buffer instance from 
	the buffer variable.
	This class does not have a public constructor. Buffers can be opened and 
	closed using methods in the jEdit class.
	This class is partially thread-safe, however you must pay attention to two 
	very important guidelines:
	(1) Operations such as insert() and remove(), undo(), change Buffer data 
	in a writeLock(), and must be called from the AWT thread.
	When accessing the buffer from another thread, you must call readLock() 
	before and readUnLock() after, if you plan on performing more than one read, 
	to ensure that the buffer contents are not changed by the AWT thread for 
	the duration of the lock. Only methods whose descriptions specify thread 
	safety can be invoked from other threads.
	*/

	public Thread aThread;
	public Thread bThread;

	private StringBuffer demoOutput = new StringBuffer();
	/**
	https://coderanch.com/t/462846/certification/Thread-safety-difference-StringBuffer-StringBuilder
	The only difference between StringBuffer and StringBuilder is that 
	StringBuffer is synchronized whereas StringBuilder is not.
	Thus, when a single thread is to be used to run the application it is 
	better to use StringBuilder and if the application is to be accessed 
	from multiple threads, StringBuffer should be used because 
	of its synchronized feature.
	*/
	
	private View currentView = jEdit.getActiveView();
	private Buffer newBuffer = jEdit.newFile(currentView);

	private class OhRunnable implements Runnable {
		int lmt;
		String txt;
		OhRunnable(int llmt, String ttxt) {
			lmt = llmt;
			txt = ttxt;
		}
		public void run() {
			for (int i = 0; i < lmt; i++) {
				demoOutput.append("[thread: " + 
					Thread.currentThread().getName() + "] " + txt + "\n");
			}
		}
	}

	public ThreadsDemo(int limit, String txta, String txtb) {
		aThread = new Thread(new OhRunnable(limit, txta));
		bThread = new Thread(new OhRunnable(limit, txtb));
	}

	public void startDemo() {
		aThread.start();
		bThread.start();
		try {
			aThread.join();
		} catch(InterruptedException ie) {
			System.out.println("a: interrupted execution: " + ie);
		}
		try {
			bThread.join();
		} catch(InterruptedException ie) {
			System.out.println("b: interrupted execution: " + ie);
		}
		newBuffer.insert(0, demoOutput.toString());
		int length = newBuffer.getLength();
		newBuffer.insert(length, "current thread: " + Thread.currentThread().getName());
	}

	/**
	https://stackoverflow.com/questions/15956231/what-does-this-thread-join-code-meanhttps://stackoverflow.com/questions/15956231/what-does-this-thread-join-code-mean
	
	To quote from the Thread.join() method javadocs:
		join() Waits for this thread to die.
	There is a thread that is running your example code which is probably 
	the main thread.
	The main thread creates and starts the t1 and t2 threads. The two threads 
	start running in parallel.
	The main thread calls t1.join() to wait for the t1 thread to finish.
	The t1 thread completes and the t1.join() method returns in the main thread. 
	Note that t1 could already have finished before the join() call is made in 
	which case the join() call will return immediately.
	The main thread calls t2.join() to wait for the t2 thread to finish.
	The t2 thread completes (or it might have completed before the t1 thread did) 
	and the t2.join() method returns in the main thread.
	
	It is important to understand that the t1 and t2 threads have been running 
	in parallel but the main thread that started them needs to wait for them 
	to finish before it can continue. That's a common pattern. 
	Also, t1 and/or t2 could have finished before the main thread calls join() 
	on them. If so then join() will not wait but will return immediately.

		t1.join() means cause t2 to stop until t1 terminates?
	No. The main thread that is calling t1.join() will stop running and wait 
	for the t1 thread to finish. The t2 thread is running in parallel and is not 
	affected by t1 or the t1.join() call at all.

	In terms of the try/catch, the join() throws InterruptedException meaning 
	that the main thread that is calling join() may itself be interrupted by 
	another thread.

	while (true) { ...
	Having the joins in a while loop is a strange pattern. 
	Typically you would do the first join and then the second join handling 
	the InterruptedException appropriately in each case. 
	No need to put them in a loop.
	*/

	// main:
	/*
	public static void main(String[] args) {
		int llimit = 200;
		String stra = "FIRST thread prints : FIRST FIRST FIRST FIRST FIRST";
		String strb = "SECOND thread prints: S E C O N D  S E C O N D SECOND";
		ThreadsDemo td = new ThreadsDemo(llimit, stra, strb);
		td.startDemo();
	}
	*/
}