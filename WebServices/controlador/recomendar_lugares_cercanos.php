<?php
	require_once '../negocio/Lugar.php';
    require_once '../util/Funciones.clase.php';
    $id_categoria = $_POST["id_categoria"];
    $id_lugar = $_POST["id_lugar"];
    if (isset($id_categoria) && isset($id_lugar)) {
	    try {
	    	$obj = new Lugar();
	    	$resultado = $obj->recomendarLugares($id_categoria,$id_lugar);

	    	Funciones::imprimeJSON(200, "", $resultado);
		
	    } catch (Exception $exc) {
		   Funciones::imprimeJSON(500, $exc->getMessage(), "");
		
	    }
	}
	else{
		$error = array('estado' => 500,'mesaje'=> "No ha ingresado un ID para categoría", 'datos'=>"");

        echo json_encode($error);
	}

?>