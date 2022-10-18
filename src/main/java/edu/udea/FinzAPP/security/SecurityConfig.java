package edu.udea.FinzAPP.security;

import org.hibernate.query.criteria.internal.expression.SubqueryComparisonModifierExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    SuccessGoogle successGoogle;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }

    @Bean //Va a asociar una función el cual crea un Objeto y lo retorna
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login*").permitAll()
                //.antMatchers("/home*").permitAll() // ------------------> CORREGIR, SOLO ACCEDE ADMIN Y OPERARIO
                .antMatchers("/oauth/**").permitAll()



                .antMatchers("/empresas").permitAll()
                .antMatchers("/agregarEmpresa").permitAll()
                .antMatchers("/actualizarEmpresa").permitAll()
                .antMatchers("/eliminarEmpresa").permitAll()
                .antMatchers("/empleados").permitAll()
                .antMatchers("/agregarEmpleado").permitAll()
                .antMatchers("/actualizarEmpleado").permitAll()
                .antMatchers("/eliminarEmpleado").permitAll()
                .antMatchers("/movimientos").permitAll()
                .antMatchers("/agregarMovimiento").permitAll()
                .antMatchers("/actualizarMovimiento").permitAll()
                .antMatchers("/eliminarMovimiento").permitAll()


//                .antMatchers("/empresas").hasRole("ADMIN")
//                .antMatchers("/agregarEmpresa").hasRole("ADMIN")
//                .antMatchers("/empleados").hasRole("ADMIN")
//                .antMatchers("/agregarEmpleado").hasRole("ADMIN")
//                .antMatchers("/movimientos").hasAnyRole("ADMIN","OPERARIO")
//                .antMatchers("/agregarMovimientos").hasRole("ADMIN")

                .and()
                .formLogin()
                .loginPage("/loginF")
                .usernameParameter("correo")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")

                .and()

                .oauth2Login()
//                .loginPage("loginF")
                .successHandler(successGoogle)

                .and()

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")


                ;


//                .antMatchers("/user").hasAnyRole("ADMIN","USER")
//                .antMatchers("/").permitAll()
//                .antMatchers("/login*").permitAll()


    }
}
