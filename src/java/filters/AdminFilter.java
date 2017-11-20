/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;


import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 709317
 */
public class AdminFilter implements Filter {

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpSession session =((HttpServletRequest)request).getSession();

        String username = (String) session.getAttribute("username");

        UserDB userDB = new UserDB();
        User user = null;
        
        try {
            user = userDB.getUser(username);
            
            if(user.getRole().getRoleName().equals("admin") && user.getActive()==true){
            
                  chain.doFilter(request, response);
            }
            else{
                ((HttpServletResponse)response).sendRedirect("home");
            }
            
            
        } catch (NotesDBException ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
  

    }



    @Override
    public void destroy() {        
    }


    @Override
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;

    }



}
