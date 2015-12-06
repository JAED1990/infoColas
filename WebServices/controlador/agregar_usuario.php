<?php
    require_once '../negocio/Usuario.php';
    require_once '../util/Funciones.clase.php';
    $user_dni = $_POST["user_dni"];
    $user_correo = $_POST["user_correo"];
    $user_clave = $_POST["user_clave"];
    
    if(isset($user_dni)&&isset($user_correo)&&isset($user_clave)){
        try {

            $obj = new Usuario();

            $resultado = $obj->agregar($user_dni,$user_correo,$user_clave);
            if($resultado)
                Funciones::imprimeJSON(200, "Usuario Registrado Correctamente", $resultado);
            else
                Funciones::imprimeJSON(500, "No se puede registrar intentolo mas tarde", $resultado);
        
        } catch (Exception $exc) {
           Funciones::imprimeJSON(500, $exc->getMessage(), "");
        
        }
    }
    else{
        $error = array('estado' => 500,'mesaje'=> "Error al Ingresar los Datos!", 'datos'=>"");

        echo json_encode($error);
    }


?>