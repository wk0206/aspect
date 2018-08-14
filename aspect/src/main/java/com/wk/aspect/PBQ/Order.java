package com.wk.aspect.PBQ;

// TODO: Auto-generated Javadoc
/**
 * The Class Order.
 */
public final class Order implements Comparable {

	/** The id. */
	private int ID;
	
	/** The quantity. */
	private int quantity;
	
	/** The tiq. */
	private Long TIQ;// time into queue
	
	/** The start. */
	private long start;

	/**
	 * Instantiates a new order.
	 */
	Order() {
		this.ID = 0;
		this.quantity = 0;
		this.TIQ = System.currentTimeMillis();
		this.start = 0;
	}

	/**
	 * Instantiates a new order.
	 *
	 * @param _ID the  id
	 * @param _quatnity the quatnity
	 * @param _startTime the start time
	 */
	Order(int _ID, int _quatnity, long _startTime) {
		this.ID = _ID;
		this.quantity = _quatnity;
		this.TIQ = System.currentTimeMillis() - _startTime;
		this.start = _startTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object o) {
		Order odr = (Order) o;
		if ((this.ID <= 1000) && (odr.ID > 1000)) {
			return -1;
		}

		if ((this.ID > 1000) && (odr.ID <= 1000)) {
			return 1;
		}

		return this.TIQ < odr.TIQ ? -1 : 1;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Sets the id.
	 *
	 * @param iD the new id
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the tiq.
	 *
	 * @return the tiq
	 */
	public Long getTIQ() {
		return TIQ;
	}

	/**
	 * Sets the tiq.
	 *
	 * @param tIQ the new tiq
	 */
	public void setTIQ(Long tIQ) {
		TIQ = tIQ;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{ id= " + ID + ", quantity =" + quantity + ", TIQ =" + TIQ + "}";

	}

}
