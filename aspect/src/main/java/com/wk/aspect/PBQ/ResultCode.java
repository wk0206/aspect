package com.wk.aspect.PBQ;

// TODO: Auto-generated Javadoc
/**
 * The Enum ResultCode.
 */
public enum ResultCode {
	
	/**  success. */
	SUCCESS("200", "SUCCESS"),

	/**  fail. */
	EXCEPTION("401", "FAIL"),

	/**  exist. */
	ALREADY_EXIST("400 ", "EXIST"),

	/**  not exist. */
	NOT_EXIST("200", "NOT EXIST"),

	/**  do not support. */
	DO_NOT_SUPPORT("400 ", "DO_NOT_SUPPORT"),

	/**  empty. */
	EMPTY("200", "EMPTY");

	/**
	 * Instantiates a new result code.
	 *
	 * @param value the value
	 * @param msg the msg
	 */
	private ResultCode(String value, String msg) {
		this.val = value;
		this.msg = msg;
	}

	/**
	 * Val.
	 *
	 * @return the string
	 */
	public String val() {
		return val;
	}

	/**
	 * Msg.
	 *
	 * @return the string
	 */
	public String msg() {
		return msg;
	}

	/** The val. */
	private String val;
	
	/** The msg. */
	private String msg;
}
