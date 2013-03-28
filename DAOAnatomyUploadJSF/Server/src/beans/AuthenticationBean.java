package beans;

import javax.faces.context.FacesContext;

public class AuthenticationBean {

    public static final String AUTH_KEY = "app.user.name";

    private String name;
    private String password;
    
    public String getName() { 
    	return name; 
    }
    public String getPassword() { 
    	return password; 
    }

    public void setName(String name) { 
    	this.name = name; 
    }
    public void setPassword(String password) { 
    	this.password = password; 
    }

	public boolean isLoggedIn() {
	
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(AUTH_KEY) != null;
	}
	
	public String login() {
	
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(AUTH_KEY, name);
		return "secret";
	}
	
	public String logout() {
	
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(AUTH_KEY);
		return null;
	}
}