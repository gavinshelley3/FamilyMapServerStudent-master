package Result;

/**
 * FillResult class
 */
public class FillResult {
    /**
     * message
     */
    private String message;
    /**
     * success
     */
    private boolean success;

    /**
     *  FillResult constructor
     */
    public FillResult() {

    }

    /**
     * FillResult constructor
     * @param message
     * @param success
     */
    public FillResult(String message, boolean success) {
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
