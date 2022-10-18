package edu.udea.FinzAPP.entities;

import edu.udea.FinzAPP.repositories.EmpleadoRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetail implements UserDetails {

    //ATRIBUTOS:________________________________________________________________________________________________________
    private String user;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    private EmpleadoRepository empleadoRepository;

    //CONSTRUCTOR:______________________________________________________________________________________________________
    public MyUserDetail(Empleado empleado) {
        this.user = empleado.getCorreo();
        this.password = empleado.getPassword();
        this.active = empleado.isActivo();



        List<GrantedAuthority> roles = new ArrayList<>();

        for(String rol: empleado.getRol().split(",")){
            roles.add(
                    new SimpleGrantedAuthority(rol)
            );
        }
        this.authorities = roles;
    }

    //MÉTODOS:__________________________________________________________________________________________________________
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities; //ROLES
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;   // -------------------------------> REVISAR
    }
}
