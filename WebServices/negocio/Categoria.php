<?php
	/**
	* 
	*/
	require_once ("../datos/Conexion.clase.php");
	class Categoria extends Conexion{
		
		public function listarCategoriaID($id_categoria){
			try {
				$sql = "select * from categoria where idcategoria=".$p_id_categoria;
				$sentencia = $this->dblink->prepare($sql);
				$sentencia->execute();

				$resultado = $sentencia->fetchAll(PDO::FETCH_CLASS);

				return $resultado;
			} catch (Exception $e) {
				throw $e;
			}
		}
	}
?>