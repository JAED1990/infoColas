<?php
    require_once '../negocio/Usuario.php';
    require_once '../util/Funciones.clase.php';
  
    $user_dni = $_POST["user_dni"];
    $user_clave = $_POST["user_clave"];
    
    if(isset($user_dni)&&isset($user_clave)){
        try {

            $obj = new Usuario();

            $resultado = $obj->login($user_dni,$user_clave);
            if($resultado)
                Funciones::imprimeJSON(200, "Bienvenido",$resultado);
            else
                Funciones::imprimeJSON(200, "Usuario no registrado",false);
        } catch (Exception $exc) {
           Funciones::imprimeJSON(500, $exc->getMessage(), "");
        
        }
    }
    else{
        $error = array('estado' => 500,'mesaje'=> "DNI o CLAVE Incorrecta!", 'datos'=>"");

        echo json_encode($error);
    }


?>