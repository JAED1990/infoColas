<?php
	require_once '../negocio/Lugar.php';
    require_once '../util/Funciones.clase.php';
    
    $l_nombre = $_POST["l_nombre"];
    $l_latitud = $_POST["l_latitud"];
    $l_longitud = $_POST["l_longitud"];
    $user_dni = $_POST["user_dni"];
    $id_categoria = $_POST["id_categoria"];

    if(isset($l_nombre) && isset($l_latitud) && isset($l_longitud) && isset($user_dni) && isset($id_categoria)){
        try {

            $obj = new Lugar();

            $resultado = $obj->registrarLocal($l_nombre,$l_latitud,$l_longitud,$user_dni,$id_categoria);
            if ($resultado)
            	Funciones::imprimeJSON(200, "Registro estado correcto", $resultado);
            else
            	Funciones::imprimeJSON(500, "Surgió un problema inténlo luego", $resultado);

        } catch (Exception $exc) {
           Funciones::imprimeJSON(500, $exc->getMessage(), "");
        }
    }
    else{
        $error = array('estado' => 500,'mesaje'=> "Error al Ingresar los Datos!", 'datos'=>"");

        echo json_encode($error);
    }
?>