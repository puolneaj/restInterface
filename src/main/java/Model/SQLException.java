package Model;

public class SQLException {
    private String SQLState;

    private String errorCode;

    private String message;

    public String getSQLState() {
        return SQLState;
    }

    public void setSQLState(String SQLState) {
        this.SQLState = SQLState;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ClassPojo [SQLState = " + SQLState + ", errorCode = " + errorCode + ", message = " + message + "]";
    }
}

