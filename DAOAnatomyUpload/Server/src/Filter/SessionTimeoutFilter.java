package filter;
/**
* When the session destroyed, MySessionListener will
* do necessary logout operations.
* Later, at the first request of client, 
* this filter will be fired and redirect
* the user to the appropriate timeout page 
* if the session is not valid.
*
* Thanks to hturksoy
*
*/

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;

import beans.LoginUserForm;

public class SessionTimeoutFilter implements Filter {

// This should be your default Home or Login page
// "login.seam" if you use Jboss Seam otherwise "login.jsf" 
// "login.xhtml" or whatever

	private String timeoutPage = "login_user.jsp";
	private FilterConfig config;
	
	public void init(FilterConfig config) throws ServletException {

		this.config = config;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,ServletException {

		if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;

			// is session expire control required for this request?
			if (isSessionControlRequiredForThisResource(httpServletRequest)) {

				// is session invalid?
				if (isSessionInvalid(httpServletRequest)) {
				
					String timeoutUrl = httpServletRequest.getContextPath() + "/" + getTimeoutPage();
					System.out.println("Session is invalid! redirecting to timeoutpage : " + timeoutUrl);
					httpServletResponse.sendRedirect(timeoutUrl);
					return;
				}
			}
		}
		
		filterChain.doFilter(request, response);
	}

	/* session shouldn't be checked for some pages. 
	 * For example: for timeout page..
	 * Since we're redirecting to timeout page from this filter,
	 * if we don't disable session control for it, 
	 * filter will again redirect to it
	 * and this will be result with an infinite loop...
	 * */

	private boolean isSessionControlRequiredForThisResource(HttpServletRequest httpServletRequest) {

		String requestPath = httpServletRequest.getRequestURI();

		boolean controlRequired = !StringUtils.contains(requestPath, getTimeoutPage());

		return controlRequired;
	}

	private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {

		//boolean sessionInValid = (httpServletRequest.getRequestedSessionId() != null) && !httpServletRequest.isRequestedSessionIdValid();
		boolean sessionInValid = (httpServletRequest.getSession().getAttribute(LoginUserForm.AUTH_KEY) != null) && !httpServletRequest.isRequestedSessionIdValid();

		return sessionInValid;
	}

	public void destroy() {

		this.config = null;
	}

	public String getTimeoutPage() {
		return timeoutPage;
	}

	public void setTimeoutPage(String timeoutPage) {
		this.timeoutPage = timeoutPage;
	}
}
