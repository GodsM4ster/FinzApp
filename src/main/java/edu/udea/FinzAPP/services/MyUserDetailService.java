package edu.udea.FinzAPP.services;

import edu.udea.FinzAPP.entities.Empleado;
import edu.udea.FinzAPP.entities.MyUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    //ATRIBUTOS - INYECCIONES:__________________________________________________________________________________________
    @Autowired
    private GestorEmpleado gestorEmpleado;


    //MÉTODOS:__________________________________________________________________________________________________________
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            Empleado usuario = gestorEmpleado.getEmpleadoCorreo(username);
            System.out.println(usuario.getCorreo());
            return new MyUserDetail(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException(e.getMessage());
        }



    }
}
