package com.example.pruebadyane.controllers;

import com.example.pruebadyane.service.VistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VistaController {

    //objeto Vista Service, utilizado para acceder al método de la clase
    @Autowired
    VistaService vistaService;

    //Aqui se declara la Uri a la cual llegará desde el Front End
    @RequestMapping(value = "api/vista", method = RequestMethod.POST)
    public ResponseEntity<Map> obtenerDatos(@RequestBody Map datosIngresados){

        //Se llama al método "llamarApi" y se le envían los datos
        //El método retorna un mapa, por lo que se guardará en una variable de ese tipo
        Map solucion= vistaService.llamarApi(datosIngresados);

        //Se retorna el mapa de vuelta al front
        return ResponseEntity.ok(solucion);
    }

}
