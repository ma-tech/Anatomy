package form;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class holds all (error) messages of a form and provides methods to return the 'succes', the
 * 'focus' and the 'highlight' of the processed form result.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jspservlet.html
 */
public class Form {

    // Properties ---------------------------------------------------------------------------------

    private Map<String, String> messages = new LinkedHashMap<String, String>();
    private boolean hasError;

    // Getters ------------------------------------------------------------------------------------

    /**
     * Returns all messages of the form.
     * @return All messages of the form.
     */
    public Map<String, String> getMessages() {
        return messages;
    }

    /**
     * Returns true if there are no error messages.
     * @return True if there are no error messages.
     */
    public boolean isSuccess() {
        return !hasError;
    }

    /**
     * Returns the field name to set the first focus on.
     * @return The field name to set the first focus on.
     */
    public String getFocus() {
        return !messages.isEmpty() ? messages.keySet().iterator().next() : null;
    }

    /**
     * Returns the comma separated field names to be highlighted.
     * @return The comma separated field names to be highlighted.
     */
    public String getHighlight() {
        StringBuilder highlight = new StringBuilder();
        for (Iterator<String> fieldNames = messages.keySet().iterator(); fieldNames.hasNext();) {
            highlight.append(fieldNames.next());
            if (fieldNames.hasNext()) {
                highlight.append(",");
            }
        }
        return highlight.toString();
    }

    // Special setters ----------------------------------------------------------------------------

    /**
     * Set the given message on the given field name. This will overwrite any previously set
     * (error) message on the given field name.
     * @param fieldName The field name to set the given message on.
     * @param message The message to be set on the given field name.
     */
    public void setMessage(String fieldName, String message) {
        messages.put(fieldName, message);
    }

    /**
     * Set the given error message on the given field name. This will overwrite any previously set
     * (error) message on the given field name.
     * @param fieldName The field name to set the given error message on.
     * @param error The error message to be set on the given field name.
     */
    public void setError(String fieldName, String error) {
        hasError = true;
        setMessage(fieldName, error);
    }

}
