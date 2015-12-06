<?php
	require_once ("../datos/Conexion.clase.php");
	require_once ("TiempoTranscurrido.php");
	class Cola extends Conexion{
		
		public function listarEstadoCola($id_lugar){
			try {
				$sql = "select co.idcola,co.dni,co.fecha,co.estado,onp.nombres from cola co";
				$sql .=" inner join onpe onp on co.dni = onp.dni where co.idlugar=".$id_lugar." order by co.idcola desc limit 5";
				$sentencia = $this->dblink->prepare($sql);
				$sentencia->execute();

				$resultado = $sentencia->fetchAll(PDO::FETCH_ASSOC);
				$array = array();
				$i=0;
				$trans = "";
				foreach ($resultado as $key => $value) {
					$array[$i]["idcola"] = $value["idcola"];
					$array[$i]["dni"]= $value["dni"];
					$array[$i]["estado"] = $value["estado"];
					$array[$i]["nombres"]= $value["nombres"];
					$array[$i]["fecha"] = TiempoTranscurrido::timeago(strtotime($value["fecha"]));
					$i++;
				}
				return $array;

				//return $resultado;
			} catch (Exception $e) {
				throw $e;
			}
		}
		public function registrarEstadoCola($c_estado,$id_lugar,$user_dni){
			$this->dblink->beginTransaction();
			try {
				$sql = "insert into cola(fecha,hora,estado,idlugar,dni)";
				$sql .=" values(NOW(),CURTIME(),:c_estado,:c_idlugar,:c_dni)";

				$sentencia = $this->dblink->prepare($sql);
				$sentencia->bindParam(":c_estado",$c_estado,PDO::PARAM_STR);
				$sentencia->bindParam(":c_idlugar",$id_lugar,PDO::PARAM_INT);
				$sentencia->bindParam(":c_dni",$user_dni,PDO::PARAM_STR);

				$sentencia->execute();

				$this->dblink->commit();
				return true;

			} catch (Exception $e) {
				$this->dblink->rollback();
				throw $e;
			}
		}
	}
?>