package edu.udea.FinzAPP.controllers;

import edu.udea.FinzAPP.entities.Empleado;
import edu.udea.FinzAPP.entities.Empresa;
import edu.udea.FinzAPP.entities.MovimientoDinero;
import edu.udea.FinzAPP.entities.Perfil;
import edu.udea.FinzAPP.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FrontendController {

    //ATRIBUTOS:________________________________________________________________________________________________________
    @Autowired
    private GestorEmpresas gestorEmpresas;
    @Autowired
    private GestorEmpleado gestorEmpleado;
    @Autowired
    private GestorPerfil gestorPerfil;
    @Autowired
    private GestorMovimientoDinero gestorMovimientoDinero;

    @Autowired
    private MyUserDetailService us;


    //REST:_____________________________________________________________________________________________________________

    //INDEX:____________________________________________________________________________________________________________
    @GetMapping("/")
    public String getIndex(){
        return "index";
    }


    //LOGIN:____________________________________________________________________________________________________________
    @GetMapping("/loginF")
    public String getLogin(Model model){

        model.addAttribute("formEmpleado", new Empleado());
        return "loginF";
    }

    @PostMapping("/loginF")
    public String login(@ModelAttribute("formEmpleado") Empleado empleado){
        System.out.println(empleado);

        //UserDetails correo = us.loadUserByUsername(empleado.getCorreo());

        return "redirect:home";
    }


    //HOME:_____________________________________________________________________________________________________________
    @GetMapping("/home")
    public String getHome(Model model){
        model.addAttribute("empleados", gestorEmpleado.getEmpleados());
        return "home";
    }


    
    @GetMapping("/perfil")
    public String getPerfilesF(Model model, @AuthenticationPrincipal OAuth2User user){

        if(user != null){
            Object e = user.getAttributes().get("email");
            String email = e.toString();

            try {
                Empleado empleado = gestorEmpleado.getEmpleadoCorreo(email);
                Perfil perfil = gestorPerfil.getPerfil(empleado.getNumDocumento());
                model.addAttribute("perfil", perfil);
                return "perfil";
            } catch (Exception ex) {
                return "redirect:/error";
            }
        }


        try {
            Empleado empleado = gestorEmpleado.getEmpleadoLoginPropio();
            Perfil perfil = empleado.getPerfil();

            model.addAttribute("perfil", perfil);
            return "perfil";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @PatchMapping("/perfil/frontPatch/{id}")
    public String patchPerfil(@ModelAttribute("perfil") Perfil perfil, @PathVariable int id){

        try {
            System.out.println(perfil);
            gestorPerfil.updatePerfil(perfil, id);

            return "redirect:/home";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }
    //EMPRESA:__________________________________________________________________________________________________________
    @GetMapping("/empresas")
    public String getEmpresasF(Model model){

        List<Empresa> empresas = gestorEmpresas.getEmpresas();
        model.addAttribute("empresas", empresas);

        return "empresas";
    }

    @PostMapping("/empresa")
    public RedirectView createEmpresa(@ModelAttribute Empresa empresa, Model model) throws Exception {
        model.addAttribute(empresa);
        this.gestorEmpresas.setEmpresa(empresa);
        return new RedirectView("/empresas");

    }

    @GetMapping("/agregarEmpresa")
    public String getNewEmpresasF(Model model){

        model.addAttribute("formNewEmpresa", new Empresa());

        return "agregarEmpresa";
    }

    @PostMapping("/agregarEmpresa")
    public String newEmpresa(@ModelAttribute("formNewEmpresa") Empresa empresa){
        System.out.println(empresa);

        return "redirect:/empresas";
    }

    @GetMapping("/empresa/front/{id}")
    public String getEmpresa(@PathVariable int id, Model model){
        try {
            Empresa emp = gestorEmpresas.getEmpresa(id);
            model.addAttribute("formEditEmpresa", gestorEmpresas.getEmpresa(id));
            return "actualizarEmpresa";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @PatchMapping("/empresa/frontPatch/{id}")
    public String patchEmpresa(@ModelAttribute("formEditEmpresa") Empresa empresa,@PathVariable int id){

        try {
            System.out.println(empresa);
            gestorEmpresas.updateEmpresa(empresa, id);
            return "redirect:/empresas";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }

    @DeleteMapping("/empresa/front/{id}")
    public String deleteEmpresa(@PathVariable int id, Model model){
        try {
            gestorEmpresas.deleteEmpresa(id);
            return "redirect:/empresas";
        } catch (Exception e) {
            return  "redirect:/error";
        }
    }

    //EMPLEADO:_________________________________________________________________________________________________________
    @GetMapping("/empleados")
    public String getEmpleadosF(Model model){

        List<Empleado> empleados = gestorEmpleado.getEmpleados();
        model.addAttribute("empleados", empleados);

        return "empleados";
    }


    @GetMapping("/agregarEmpleado")
    public String getNewEmpleado(Model model){

        model.addAttribute("formNewEmpleado", new Empleado());
        List<Empresa> empresas = gestorEmpresas.getEmpresas();

        model.addAttribute("empresas", empresas);

        return "agregarEmpleado";
    }

    @PostMapping("/agregarEmpleado")
    public String newEmpleado(@ModelAttribute("formNewEmpleado") Empleado empleado){
        System.out.println(empleado);

        return "redirect:/empleados";
    }

//    @PostMapping("/empleado/front/{id}")
//    public String postEmpleado(@PathVariable int id, Model model){
//        try {
//            gestorEmpleado.deleteEmpleado(id);
//            return "redirect:/empleados";
//        } catch (Exception e) {
//            return  "redirect:/error";
//        }
//    }

    @GetMapping("/empleado/front/{id}")
    public String getEmpleado(@PathVariable int id, Model model){
        try {
            Empleado empleado = gestorEmpleado.getEmpleado(id);
            model.addAttribute("formEditEmpleado", empleado);
            return "actualizarEmpleado";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }

    @PatchMapping("/empleado/frontPatch/{id}")
    public String patchEmpleado(@ModelAttribute("formEditEmpleado") Empleado empleado,@PathVariable int id){
        try {
            gestorEmpleado.updateEmpleado(empleado,id);
            return "redirect:/empleados";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }

    @DeleteMapping("/empleado/front/{id}")
    public String deleteEmpleado(@PathVariable int id, Model model){
        try {
            gestorEmpleado.deleteEmpleado(id);
            return "redirect:/empleados";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }


    //MOVIMIENTOS:______________________________________________________________________________________________________
    @GetMapping("/movimientos")
    public String getMovimientosF(Model model, @AuthenticationPrincipal OAuth2User user){

        try {

            if(user != null){
                Object e = user.getAttributes().get("email");
                String email = e.toString();
                Empleado empleado = gestorEmpleado.getEmpleadoCorreo(email);
                List<MovimientoDinero> transacciones = gestorMovimientoDinero.getTransaccionesNit(empleado.getEmpresa().getNit());
                double total = gestorMovimientoDinero.getTotalEmpresa(transacciones);

                model.addAttribute("total", total);
                model.addAttribute("transacciones", transacciones);
                return "movimientos";
            }
                List<MovimientoDinero> transacciones = gestorMovimientoDinero.getTransaccionesNit();
                double total = gestorMovimientoDinero.getTotalEmpresa(transacciones);

                model.addAttribute("total", total);
                model.addAttribute("transacciones", transacciones);

            return "movimientos";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }

    @GetMapping("/agregarMovimiento")
    public String getNewMovimientosF(Model model){

        model.addAttribute("formNewMovimiento", new MovimientoDinero());

        return "agregarMovimiento";
    }

    @PostMapping("/agregarMovimiento")
    public RedirectView addMovimiento( Model model, @ModelAttribute MovimientoDinero movimientoDinero, @AuthenticationPrincipal OAuth2User user) throws Exception {
        model.addAttribute(movimientoDinero);
        if(user != null){
            //movimientoDinero.setEmpleado(user.getAttributes().get("email"));

            Empleado empleado = gestorEmpleado.getEmpleadoCorreo(user.getAttributes().get("email").toString());
            movimientoDinero.setEmpleado(empleado);
            this.gestorMovimientoDinero.setMovimientoDineroUser(movimientoDinero);
            return new RedirectView("/movimientos");
        }

        this.gestorMovimientoDinero.setMovimientoDinero(movimientoDinero);
        return new RedirectView("/movimientos");

    }

    @GetMapping("/movimiento/front/{id}")
    public String getMovimiento(@PathVariable int id, Model model){
        try {
            MovimientoDinero mov = gestorMovimientoDinero.getTransaccion(id);
            model.addAttribute("formEditMovimiento", gestorMovimientoDinero.getTransaccion(id));
            return "actualizarMovimiento";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @PatchMapping("/movimiento/frontPut/{id}")
    public String patchMovimientoF(@ModelAttribute("formEditMovimiento") MovimientoDinero movimientoDinero,@PathVariable int id){

        try {
            System.out.println(movimientoDinero);
            gestorMovimientoDinero.updateMovimientoDinero(movimientoDinero, id);
            return "redirect:/movimientos";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }

    @DeleteMapping("/movimiento/front/{id}")
    public String deleteTransaccion(@PathVariable int id, Model model){
        try {
            gestorMovimientoDinero.deleteTransaccion(id);
            return "redirect:/movimientos";
        } catch (Exception e) {
            return  "redirect:/error";
        }
    }

}
