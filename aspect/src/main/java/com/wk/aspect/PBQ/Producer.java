package com.wk.aspect.PBQ;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

// TODO: Auto-generated Javadoc
/**
 * The Class Producer.
 */
public class Producer implements Runnable {

	/** The id. */
	private int ID;
	
	/** The quantity. */
	private int quantity;
	
	/** The tiq. */
	private Long TIQ;// time into queue
	
	/** The server start time. */
	private long serverStartTime;
	
	/** The order. */
	private Order order;

	/** The is running. */
	private volatile boolean isRunning;

	/** The queue. */
	private BlockingQueue<Order> queue;

	/** The Constant SLEEPTIME. */
	private static final int SLEEPTIME = 1000;

	/**
	 * Instantiates a new producer.
	 *
	 * @param queue the queue
	 */
	public Producer(BlockingQueue<Order> queue) {

		this.queue = queue;
	}

	/**
	 * Instantiates a new producer.
	 *
	 * @param _queue the queue
	 * @param _isRunning the is running
	 * @param _id the id
	 * @param _quantity the quantity
	 * @param _serverStartTime the server start time
	 */
	public Producer(BlockingQueue<Order> _queue, boolean _isRunning, int _id, int _quantity, long _serverStartTime) {

		this.queue = _queue;
		this.isRunning = _isRunning;
		this.ID = _id;
		this.quantity = _quantity;
		this.serverStartTime = _serverStartTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Order data = null;
		Random r = new Random();
		System.out.println("start producer thread = " + Thread.currentThread().getId());
		try {
			if (isRunning) {

				data = new Order(ID, quantity, serverStartTime);
				System.out.println("Order: " + "{ID:" + ID + "," + "quantity:" + quantity + "} is putted into queue ");

				if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
					System.out.println("faile to  put data:  " + data);
				}
			}
			Thread.sleep(r.nextInt(SLEEPTIME));
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();

		}

	}

	/**
	 * Stop.
	 */
	public void stop() {

		isRunning = false;
	}

}
