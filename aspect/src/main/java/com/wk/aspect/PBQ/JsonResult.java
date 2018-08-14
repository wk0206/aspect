package com.wk.aspect.PBQ;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonResult.
 */
public class JsonResult {
	
	/** The code. */
	private String code;
	
	/** The message. */
	private String message;
	
	/** The data. */
	private Object data;

	/**
	 * Instantiates a new json result.
	 */
	public JsonResult() {
		this.setCode(ResultCode.SUCCESS);
		this.setMessage("success");

	}

	/**
	 * Instantiates a new json result.
	 *
	 * @param code the code
	 */
	public JsonResult(ResultCode code) {
		this.setCode(code);
		this.setMessage(code.msg());
	}

	/**
	 * Instantiates a new json result.
	 *
	 * @param code the code
	 * @param message the message
	 */
	public JsonResult(ResultCode code, String message) {
		this.setCode(code);
		this.setMessage(message);
	}

	/**
	 * Instantiates a new json result.
	 *
	 * @param code the code
	 * @param message the message
	 * @param data the data
	 */
	public JsonResult(ResultCode code, String message, Object data) {
		this.setCode(code);
		this.setMessage(message);
		this.setData(data);
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(ResultCode code) {
		this.code = code.val();
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
