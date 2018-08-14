package com.wk.aspect.PBQ;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

// TODO: Auto-generated Javadoc
/**
 * The Class Consumer.
 */
public class Consumer implements Runnable {

	/** The queue. */
	private BlockingQueue<Order> queue;
	
	/** The id. */
	private int ID;
	
	/** The quantity. */
	private int quantity;
	
	/** The res. */
	List<Order> res;
	
	/** The Constant SLEEPTIME. */
	private static final int SLEEPTIME = 10000;

	/**
	 * Instantiates a new consumer.
	 *
	 * @param _queue the queue
	 */
	public Consumer(BlockingQueue<Order> _queue) {
		this.queue = _queue;
		this.ID = 0;
		this.quantity = 0;
	}

	/**
	 * Instantiates a new consumer.
	 *
	 * @param _queue the queue
	 * @param _id the id
	 */
	public Consumer(BlockingQueue<Order> _queue, int _id) {
		this.queue = _queue;
		this.ID = _id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("start consumer thread = " + Thread.currentThread().getId());
		Random r = new Random();

		try {
			Iterator<Order> itr = queue.iterator();

			while (itr.hasNext()) {
				Order anOrder = itr.next();
				if (anOrder.getID() == this.ID) {
					itr.remove();
					System.out.println("Order: " + "{ID:" + anOrder.getID() + "," + "quantity:" + anOrder.getQuantity()
							+ "} is removed from queue ");

					break;
				}
			}
			Thread.sleep(r.nextInt(SLEEPTIME));

		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}

	}

}
