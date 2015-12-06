<?php
    require_once '../negocio/Cola.php';
    require_once '../util/Funciones.clase.php';
    $id_lugar = $_POST["id_lugar"];
    
    if(isset($id_lugar)){
        try {

            $obj = new Cola();

            $resultado = $obj->listarEstadoCola($id_lugar);

            Funciones::imprimeJSON(200, "Listado Correcto", $resultado);
        
        } catch (Exception $exc) {
           Funciones::imprimeJSON(500, $exc->getMessage(), "");
        
        }
    }
    else{
        $error = array('estado' => 500,'mesaje'=> "no ha ingresado un ID de un lugar", 'datos'=>"");

        echo json_encode($error);
    }
?>