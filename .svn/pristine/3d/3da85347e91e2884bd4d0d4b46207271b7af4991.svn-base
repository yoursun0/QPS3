package qpses.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import qpses.business.WAChallengeInfo;


public class WAChallengeFilter implements Filter
{
    
    static Logger logger = Logger.getLogger(AuthorizationFilter.class.getName());
    FilterConfig fc      = null;
    
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
    throws ServletException, IOException
    {
        HttpServletRequest request   = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        
        // get the WAChallenge Session variable
        WAChallengeInfo wac = (WAChallengeInfo) request.getSession().getAttribute("QPSES_WA_CHALLENGE");
        
        if (wac == null || !wac.getChallengeStatus().equals("Y")) // Not going through the WA challenge page at all
        {  
            String url = request.getRequestURI();

            String errorString = "Direct access to this location [" + url + "] is not allowed.";
            request.setAttribute("WA_CHALLENGE_MSG", errorString);
            
            if (url.indexOf("StaffRateValidation") > 0)
                response.sendRedirect("StaffRateValidationSLUser");
            else if (url.indexOf("QualitySubscore") > 0)
                response.sendRedirect("CPSSLUser");
            else
                response.sendRedirect("index.jsp");

            return;
        }
        
        chain.doFilter(request,response);
                
    }
    
    /**
     * Method init.
     * @param config
     * @throws javax.servlet.ServletException
     */
    public void init(FilterConfig filterconfig) throws ServletException
    {
        this.fc = filterconfig;
    }
    
    /**
     * @see javax.servlet.Filter#void ()
     */
    public void destroy()
    {
        this.fc = null;
    }
}
