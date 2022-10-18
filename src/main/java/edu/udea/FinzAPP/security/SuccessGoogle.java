package edu.udea.FinzAPP.security;

import edu.udea.FinzAPP.services.GestorEmpleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class SuccessGoogle implements AuthenticationSuccessHandler {
    @Autowired
    GestorEmpleado gestorEmpleado;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        DefaultOidcUser user =(DefaultOidcUser) authentication.getPrincipal();
        String correoEmpleado = user.getEmail();
        try {
            gestorEmpleado.getEmpleadoCorreo(correoEmpleado);
            response.sendRedirect("/home");
        } catch (Exception e) {
            //logout
            //response.sendRedirect("/logout");
            throw new RuntimeException(e);
        }




    }
}
