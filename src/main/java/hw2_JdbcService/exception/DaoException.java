package hw2_JdbcService.exception;

public class DaoException extends RuntimeException {
    public DaoException(Throwable throwable) {
        super(throwable);
    }
}
