package Result;
/**
 * LoadResult class
 */
public class LoadResult {
    /**
     * message
     */
    private String message;
    /**
     * success
     */
    private boolean success;

    /**
     * LoadResult constructor
     */
    public LoadResult() {}

    /**
     * LoadResult constructor
     * @param message
     * @param success
     */
    public LoadResult(String message, boolean success) {
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
