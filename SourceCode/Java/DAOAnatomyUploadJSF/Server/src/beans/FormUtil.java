package beans;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/*
 * utility class for forms. This class contains commonly used request parameter processing and
 * validation logic which are been refactored in single static methods.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jspservlet.html
 */
public final class FormUtil {

    // Constructors -------------------------------------------------------------------------------
    private FormUtil() {
        // utility class, hide constructor.
    }

    // Request parameter processing ---------------------------------------------------------------
    /*
     * Returns the form field value from the given request associated with the given field name.
     * It returns null if the form field value is null or is empty after trimming all whitespace.
     */
    public static String getFieldValue(HttpServletRequest request, String fieldName) {

    	String value = request.getParameter(fieldName);
        return isEmpty(value) ? null : value;
    }

    // Validation ---------------------------------------------------------------------------------
    /*
     * Returns true if the given value is null or is empty.
     */
    public static boolean isEmpty(Object value) {

    	if (value == null) {
            return true;
        }
    	else if (value instanceof String) {
            return ((String) value).trim().length() == 0;
        }
    	else if (value instanceof Object[]) {
            return ((Object[]) value).length == 0;
        }
    	else if (value instanceof Collection<?>) {
            return ((Collection<?>) value).size() == 0;
        }
    	else if (value instanceof Map<?, ?>) {
            return ((Map<?, ?>) value).size() == 0;
        }
    	else {
            return value.toString() == null || value.toString().trim().length() == 0;
        }
    }

    /*
     * Returns true if the given old value does not equals the given new value.
     */
    public static boolean isChanged(Object oldValue, Object newValue) {

    	return oldValue == null ? newValue != null : !oldValue.equals(newValue);
    }

    /*
     * Returns true if the given string is a valid email address.
     */
    public static boolean isEmail(String string) {

    	return string.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");
    }

    /*
     * Returns true if the given string is a valid positive number.
     */
    public static boolean isNumber(String string) {

    	return string.matches("^\\d+$");
    }
}
