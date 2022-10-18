package edu.udea.FinzAPP.controllers;

import edu.udea.FinzAPP.entities.Empleado;
import edu.udea.FinzAPP.entities.Perfil;
import edu.udea.FinzAPP.services.GestorEmpleado;
import edu.udea.FinzAPP.services.GestorPerfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PerfilController {

    //ATRIBUTOS:
    @Autowired
    private GestorPerfil gestorPerfil;


    //GET:
    @GetMapping("/perfil/{id}")
    public ResponseEntity<Object> getPerfil(@PathVariable int id){

        try {
            return new ResponseEntity<>(gestorPerfil.getPerfil(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //PATCH:
    @PatchMapping("/perfil/{id}")
    public ResponseEntity<String> patchPerfil(@RequestBody Perfil perfilUpdate, @PathVariable int id){

        try {
            String msg = gestorPerfil.updatePerfil(perfilUpdate, id);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




}
