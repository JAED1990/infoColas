<?php

    //require_once 'sesion.controlador.php';
/*AUTOR: ROA SÁNCHEZ, ROY*/
    require_once '../negocio/Lugar.php';
    require_once '../util/Funciones.clase.php';
    $id_lugar = $_POST["id_lugar"];

    try {
    	$obj = new Lugar();

        if($id_lugar != "")
            $resultado = $obj->listarLugarID($id_lugar);
        else
            $resultado = $obj->listarLugares();
    	Funciones::imprimeJSON(200, "", $resultado);
	
    } catch (Exception $exc) {
	   Funciones::imprimeJSON(500, $exc->getMessage(), "");
	
    }

