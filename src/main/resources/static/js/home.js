
async function obtenerDatos(){

//Obtenemos los datos desde el HTML
    let datos = {};
    datos.fDesde = document.getElementById('txtFDesde').value;
    datos.fHasta= document.getElementById('txtFHasta').value;
    datos.temp= document.getElementById('txtTemp').value;

//Validación de campos vacíos
    if(datos.temp == "" || datos.fDesde == "" || datos.fHasta == ""){
        alert('Por favor verifique haber completado los campos');
        return;
    }

  //aqui se declara la dirección a donde se enviarán los datos y el method
  const request = await fetch('api/vista', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      //Se enviará la variable con los valores de los txt en forma de JSON
      body: JSON.stringify(datos)
    });

//Aqui llega el body de la respuesta
const response = await request.text();

//La respuesta (que viene en formato String) se parsea a JSON
let responseJson = JSON.parse(response);

//Insertamos en el HTML lo solicitado
document.getElementById('datos').innerHTML = '<p>Temperatura minima:'+responseJson.menor+'</p><p>Temperatura maxima:'+responseJson.mayor+'</p><p>Temperatura sobre lo solicitado:'+responseJson.porSobreSegundos+' segundos</p><p>Temperatura sobre lo solicitado:'+responseJson.porSobreHoras+' horas</p>';


}