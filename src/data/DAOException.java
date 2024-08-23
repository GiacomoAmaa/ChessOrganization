package data;

/**
 * This is a runtime exception we define to wrap all the exceptions coming from
 * the DAO objects we're going to define.
 * This way we won't have `SQLException`s bubbling up in all other functions.
 */
public final class DAOException extends RuntimeException {

 private static final long serialVersionUID = 1L;

 /**
  * the constructor.
  * @param message the message to output in the stack trace
  */
 public DAOException(final String message) {
     super(message);
 }

 /**
  * wrapper for a generic exception.
  * @param cause the exception
  */
 public DAOException(final Throwable cause) {
     super(cause);
 }

 /**
  * mixed constructor.
  * @param message costum message
  * @param cause the exception
  */
 public DAOException(final String message, final Throwable cause) {
     super(message, cause);
 }
}

