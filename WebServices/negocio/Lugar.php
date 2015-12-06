<?php
	/**
	* 
	*/
	require_once ("../datos/Conexion.clase.php");
	class Lugar extends Conexion{
		
		public function listarLugares(){
			try {
				$sql = "select * from lugar where estado='A'";
				$sentencia = $this->dblink->prepare($sql);
				$sentencia->execute();

				$resultado = $sentencia->fetchAll(PDO::FETCH_ASSOC);

				return $resultado;
			} catch (Exception $e) {
				throw $e;
			}
		}
		public function listarLugarID($id_lugar){
			try {
				$sql = "select * from lugar where idlugar=".$id_lugar." and estado='A'";
				$sentencia = $this->dblink->prepare($sql);
				$sentencia->execute();

				$resultado = $sentencia->fetchAll(PDO::FETCH_ASSOC);

				return $resultado;
			} catch (Exception $e) {
				throw $e;
			}
		}
		public function registrarLocal($l_nombre,$l_latitud,$l_longitud,$user_dni,$id_categoria){
			$this->dblink->beginTransaction();
			try {
				$sql = "insert into lugar (nombre,latitud,longitud,dni,idcategoria)";
				$sql .=" values(:l_nombre,:l_latitud,:l_longitud,:user_dni,:id_categoria)";

				$sentencia = $this->dblink->prepare($sql);
				$sentencia->bindParam(":l_nombre",$l_nombre,PDO::PARAM_STR);
				$sentencia->bindParam(":l_latitud",$l_latitud,PDO::PARAM_STR);
				$sentencia->bindParam(":l_longitud",$l_longitud,PDO::PARAM_STR);
				$sentencia->bindParam(":user_dni",$user_dni,PDO::PARAM_STR);
				$sentencia->bindParam(":id_categoria",$id_categoria,PDO::PARAM_INT);

				$sentencia->execute();

				$this->dblink->commit();
				return true;
			} catch (Exception $e) {
				$this->dblink->rollback();
				throw $e;
			}
		}
		
		public function recomendarLugares($id_categoria,$id_lugar){
			try {
				$sql = "select distinct lu.idlugar,lu.nombre,lu.imagen,lu.latitud,lu.longitud,col.estado,lu.idcategoria from lugar lu inner join cola col on lu.idlugar = col.idlugar";
				$sql .=" where lu.idcategoria=:id_categoria and (col.estado = 'B' or col.estado = 'M') and lu.idlugar !=:id_lugar";
				$sql .=" order by col.idcola desc limit 5 ";

				$sentencia = $this->dblink->prepare($sql);
				$sentencia->bindParam(":id_categoria",$id_categoria,PDO::PARAM_INT);
				$sentencia->bindParam(":id_lugar",$id_lugar,PDO::PARAM_INT);

				$sentencia->execute();

				$resultado = $sentencia->fetchAll(PDO::FETCH_ASSOC);

				return $resultado;
			} catch (Exception $e) {
				throw $e;
			}
		}
	}
?>