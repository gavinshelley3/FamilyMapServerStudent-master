package Result;

/**
 * ClearResult class
 */
public class ClearResult {
    /**
     * message
     */
    private String message;
    /**
     * success
     */
    private boolean success;

    /**
     * ClearResult constructor
     */

    public ClearResult() {}

    /**
     * ClearResult constructor
     * @param message
     * @param success
     */
    public ClearResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
