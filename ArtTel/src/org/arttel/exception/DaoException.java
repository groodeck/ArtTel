package org.arttel.exception;

public class DaoException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1291766636303838545L;

	public DaoException(final String msg, final Throwable e){
		super(msg, e);
	}
}
