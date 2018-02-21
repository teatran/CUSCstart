
package test.tea;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gux
 */
public class SessionUtils
{
    public static HttpSession getSession()
    {
        return (HttpSession) FacesContext.getCurrentInstance()
            .getExternalContext().getSession(false);
    }
    
    public static HttpServletRequest getRequest()
    {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
            .getExternalContext().getRequest();
    }    
}
