package edu.udea.FinzAPP.controllers;

import edu.udea.FinzAPP.entities.Empresa;
import edu.udea.FinzAPP.services.GestorEmpresas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Biviana Medina Trujillo
 * @author Santiago Medina Trujillo
 *
 */

@RestController
public class EmpresaController {

    //ATRIBUTOS:
    @Autowired
    private GestorEmpresas gestorEmpresas;

    /* ATRIBUTOS - COMENTARIOS:
        1. gestorEmpresa se conecta con la lógica que se encuentra dentro de
           la clase GestorEmpresa
    */


    //Query Params
    @GetMapping("/empresasPostman")
    public ResponseEntity<List<Empresa>> getEmpresas() {
        //Me devuelve todas las empresas existentes
        return new ResponseEntity<>(gestorEmpresas.getEmpresas(), HttpStatus.OK);
    }

    //Path Params
    @GetMapping("/empresa/{id}")
    public ResponseEntity<Object> getEmpresa(@PathVariable int id) {
        try {
            //Me devuelve una empresa en específico
            return new ResponseEntity<>(gestorEmpresas.getEmpresa(id), HttpStatus.OK);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Query Params
//    @PostMapping("/empresa")
//    public ResponseEntity<String>  postEmpresa(@RequestBody Empresa empresaNueva){
//
//       /* Me agrega una empresa*/
//        try {
//            String msg = gestorEmpresas.setEmpresa(empresaNueva);
//            return new ResponseEntity<>( msg, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//
//
//    }

//    @PostMapping("/empresa")
//    public RedirectView createEmpresa(@ModelAttribute Empresa empresa, Model model) throws Exception {
//        model.addAttribute(empresa);
//        this.gestorEmpresas.setEmpresa(empresa);
//        return new RedirectView("/empresas");
////
////    @PostMapping("/empresa")
////    public RedirectView  postEmpresa(@ModelAttribute Empresa empresaNueva, Model model){
//
////        model.addAttribute(empresaNueva);
//
//
//       /* Me agrega una empresa
//        try {
//            String msg = gestorEmpresas.setEmpresa(empresaNueva);
//            return new ResponseEntity<>( msg, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        */
//
//
//    }


    @PatchMapping("/empresa/{id}")
    public  ResponseEntity<String> patchEmpresa(@RequestBody Empresa empresa_update, @PathVariable int id){
        try {
            String msgUpdate = gestorEmpresas.updateEmpresa(empresa_update, id);
            return new ResponseEntity<>(msgUpdate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/empresa/{id}")
    public ResponseEntity<String> deleteEmpresa(@PathVariable int id){

        try {
            String msjDelete = gestorEmpresas.deleteEmpresa(id);
            return new ResponseEntity<>(msjDelete, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>((e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
