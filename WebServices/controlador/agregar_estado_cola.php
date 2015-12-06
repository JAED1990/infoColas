<?php
	require_once '../negocio/Cola.php';
    require_once '../util/Funciones.clase.php';
    
    $id_lugar = $_POST["id_lugar"];
    $c_estado = $_POST["c_estado"];
    $user_dni = $_POST["user_dni"];
    if(isset($id_lugar) && isset($c_estado) && isset($user_dni)){
        try {

            $obj = new Cola();
            $resultado = $obj->registrarEstadoCola($c_estado,$id_lugar,$user_dni);
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