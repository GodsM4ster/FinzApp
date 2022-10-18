package edu.udea.FinzAPP.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

    //En proceso de desarrollo del controlador

import edu.udea.FinzAPP.entities.Empresa;
import edu.udea.FinzAPP.entities.MovimientoDinero;
import edu.udea.FinzAPP.services.GestorMovimientoDinero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Biviana Medina Trujillo
 * @author Mari Luz Tabares López
 * @author Santiago Medina Trujillo
 * @author Edwin Marin Ballesteros
 * @author Henry Daniel Barreto Forero
 *
 */


@RestController
public class MovimientoDineroController {

    //ATRIBUTOS:
    @Autowired
    private GestorMovimientoDinero gestorMovimientoDinero;


    //GET: _____________________________________________________________________________________________________________
//    @GetMapping("/movimientos")
//    public ResponseEntity<List<MovimientoDinero>> getTransacciones() {
//        //Me devuelve todas las empresas existentes
//        return new ResponseEntity<>(gestorMovimientoDinero.getTransacciones(), HttpStatus.OK);
//    }

    @GetMapping("/empresa/{id}/movimiento")
    public ResponseEntity<Object> getTransaccion(@PathVariable int id) {

        try {
            return new ResponseEntity<>(gestorMovimientoDinero.getTransaccion(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //POST: ____________________________________________________________________________________________________________
    @PostMapping("/empresa/movimiento")
    public ResponseEntity<String> postMovimientoDinero(@RequestBody MovimientoDinero transaccionNueva){
        //Me agrega una transacción
        try {
            String msg = gestorMovimientoDinero.setMovimientoDinero(transaccionNueva);
            return new ResponseEntity<>( msg, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //PATCH: ___________________________________________________________________________________________________________
    @PatchMapping("/empresa/{id}/movimiento")
    public  ResponseEntity<String> patchMovimientoDinero(@RequestBody MovimientoDinero movimiento_update, @PathVariable int id){
        try {
            String msgUpdate = gestorMovimientoDinero.updateMovimientoDinero(movimiento_update, id);
            return new ResponseEntity<>(msgUpdate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    //DELETE: __________________________________________________________________________________________________________
    @DeleteMapping("/empresa/{id}/movimiento")
    public ResponseEntity<String> deleteEmpresaMovimientoDinero(@PathVariable int id){

        try {
            //existe.
            String msgDelete = gestorMovimientoDinero.deleteTransaccion(id);

            return new ResponseEntity<>(msgDelete, HttpStatus.OK);
        } catch (Exception e) {
            //no existe:
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
