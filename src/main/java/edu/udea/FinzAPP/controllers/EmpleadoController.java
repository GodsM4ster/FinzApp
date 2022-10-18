package edu.udea.FinzAPP.controllers;

import edu.udea.FinzAPP.entities.Empleado;
import edu.udea.FinzAPP.entities.Empresa;
import edu.udea.FinzAPP.entities.Perfil;
import edu.udea.FinzAPP.services.GestorEmpleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mari Luz Tabares López
 * @author Edwin Marin Ballesteros
 * @author Henry Daniel Barreto Forero
 *
 */

@RestController
public class EmpleadoController {

    //ATRIBUTOS:
    @Autowired
    private GestorEmpleado gestorEmpleado;

    /* ATRIBUTOS - COMENTARIOS:
        1. gestorEmpleado se conecta con la lógica que se encuentra dentro de
           la clase GestorEmpleado
    */


    //Query Params
    @GetMapping("/empleadosPostman")
    public ResponseEntity<List<Empleado>> getEmpleados() {
        //Me devuelve todas las empresas existentes
        return new ResponseEntity<>(gestorEmpleado.getEmpleados(), HttpStatus.OK);
    }

    //Path Params
    @GetMapping("/empleado/{id}")
    public ResponseEntity<Object> getEmpleado(@PathVariable int id) {
        try {
            //Me devuelve una empresa en específico
            return new ResponseEntity<>(gestorEmpleado.getEmpleado(id), HttpStatus.OK);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/empleado")
    public RedirectView createEmpleado(@ModelAttribute Empleado nuevoEmpleado, Model model) throws Exception {

        //Me agrega un empleado
        model.addAttribute(nuevoEmpleado);
        this.gestorEmpleado.setEmpleado(nuevoEmpleado);
        return new RedirectView("/empleados");
//
//        try {
//            String msg = gestorEmpleado.setEmpleado(nuevoEmpleado);
//            return new ResponseEntity<>( msg, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @PatchMapping("/empleado/{id}")
    public  ResponseEntity<String> patchEmpleado(@RequestBody Empleado empleado_update, @PathVariable int id){
        try {
            String msgUpdate = gestorEmpleado.updateEmpleado(empleado_update, id);
            return new ResponseEntity<>(msgUpdate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/empleado/{id}")
    public ResponseEntity<String> deleteEmpleado(@PathVariable int id){

        try {
            String msjDelete = gestorEmpleado.deleteEmpleado(id);
            return new ResponseEntity<>(msjDelete, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>((e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
