# Para ejecutar el programa utilizar el siguiente comando: mvn spring-boot:run
# A la hora de hacer una peticion POST realizarlo sobre esta URL: http://localhost:8080/pdf/generate
# Ejemplo de cuerpo de la peticion
<!-- {
    "docpdf":"PDF en Base64",
    "DocumentosIniciales":"DocsIniciales",
    "datosIniciales": [
                        {"name": "Tablas Datos Inciales", "hash": "sjkfkjsjkjfksjkñldlsjfksjdfksj", "id": "34T"},
                        {"name": "Segundo dato", "hash":"dsjdasssssddsdsdssds", "id": "DS23"},
                        {"name": "Tercer dato", "hash":"sssuuuuuuukkkkkkuuuuuuus", "id":"01Y"}
                    ],
    "DocumentosFirmados":"Documentos firmados",
    "datosFirmados": [
                        {"name": "Tablas Datos Firmados", "hash": 45, "id":"47Y"},
                        {"name": "Abcde", "hash": 23, "id":"4Q"},
                        {"name": "Jklmn", "hash": 67, "id":"76I"},
                        {"name": "Opqrs", "hash": 89, "id":"99L"},
                        {"name": "Opqrs", "hash": 89, "id":"21LL"},
                        {"name": "Opqrs", "hash": 89, "id":"78IO"}
                    ],
    "EvidenciasProceso":"Evidencias del proceso",
    "datosProceso": [
                        {"name_tag":"manuel", "name":"nom_manuel", "date_tag":"fecha", "date":"2024-10-01", "receiver_tag":"receptor", "receiver":"este_receptor","detail_label":"detalle", "detail":"este_detalle"},
                        {"name_tag":"alvaro", "name":"nom_alvaro", "date_tag":"fecha", "date":"2024-10-01", "receiver_tag":"receptor", "receiver":"este_receptor","detail_label":"detalle", "detail":"este_detalle"},
                        {"name_tag":"virginia", "name":"nom_virginia", "date_tag":"fecha", "date":"2024-10-01", "receiver_tag":"receptor", "receiver":"este_receptor","detail_label":"detalle", "detail":"este_detalle"}
                    ]                                                         
} -->
