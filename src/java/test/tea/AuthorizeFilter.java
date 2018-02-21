package test.tea;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizeFilter implements Filter
{
    public AuthorizeFilter() 
    {
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, 
        FilterChain filterChain) throws IOException, ServletException
    {
       
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        String requestURI = httpRequest.getRequestURI();
        if (requestURI.contains("login.xhtml")
            || (session != null && session.getAttribute("username") != null)
            || requestURI.contains("/public/")
            || requestURI.contains("javax.faces.resources")) {
            filterChain.doFilter(httpRequest, httpResponse);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/faces/login.xhtml");
        }
    }
    
    @Override
    public void destroy()
    {
    }
}
