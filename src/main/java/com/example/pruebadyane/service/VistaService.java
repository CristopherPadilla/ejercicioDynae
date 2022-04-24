package com.example.pruebadyane.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class VistaService {
    public Map llamarApi(Map datosIngresados) {

        //Se utiliza RestTemplate para llamar a la API
        RestTemplate restTemplate = new RestTemplate();

        //Se crea la URL con los valores que escribió el usuario
        String url = "http://data-env.eba-pwemrg4q.us-east-1.elasticbeanstalk.com/data/sensorElement/8/measurement?from="+datosIngresados.get("fDesde")+"T00:00:00.000Z&to="+datosIngresados.get("fHasta")+"T00:00:00.000Z&timeUnit=SEC";

        //Se llama a la api, indicando la URL y el tipo de dato que devolerá
        ResponseEntity<List> response = restTemplate.getForEntity(url,  List.class);

        double mayor = 0;
        double menor = 0;
        double porSobreSegundos = 0;
        double horas = 0;

        //Se recorren los datos de la API según lo solicitado
        for (int i = 0; i < response.getBody().size(); i++){

            //Se castean los datos a mapa
            LinkedHashMap datosApi = (LinkedHashMap) response.getBody().get(i);

            //Los valores a utilizar se guardan en variables para la limpieza del código
            double magnitude = Double.valueOf(datosApi.get("magnitude").toString());
            double tempIngresada = Double.valueOf(datosIngresados.get("temp").toString());

            //Se realiza la lógica para los datos solicitados por el usuario

            if (magnitude > mayor){
                mayor = magnitude;
            }
                                     //Esta validación es para la primera vez
            if (magnitude < menor || menor == 0){
                menor = magnitude;
            }

            //las temperaturas llegan cada 10 segundos
            if (magnitude > tempIngresada){
                porSobreSegundos = porSobreSegundos + 10;
            }
        }


        //Se cambia de segundos a horas para tener información adicional
        horas = porSobreSegundos / 3600;
        double porSobreHoras = Math.round(horas * 100) / 100d;

        //Se crea el mapa y se ingresan los datos obtenidos
        Map solucion = new HashMap();

        solucion.put("mayor",mayor);
        solucion.put("menor",menor);
        solucion.put("porSobreSegundos",porSobreSegundos);
        solucion.put("porSobreHoras",porSobreHoras);
        return solucion;
    }
}
