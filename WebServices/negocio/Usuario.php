<?php
	/**
	* 
	*/
	require_once ("../datos/Conexion.clase.php");
	class Usuario extends Conexion{
		
		public function login($user_dni, $user_clave){
			try {
				$sql = "select * from usuario where dni=".$user_dni." and clave=md5('".$user_clave."')";
				$sentencia = $this->dblink->prepare($sql);
				$sentencia->execute();

				$resultado = $sentencia->fetch();

				if($sentencia->rowCount() >= 1)
                   return $resultado["dni"];
                else
                    return false;
			} catch (Exception $e) {
				throw $e;
			}
		}
                
        public function agregar($user_dni,$user_correo,$user_clave) {
        $this->dblink->beginTransaction();
        try {
          
                $sql = "
		    INSERT INTO usuario(dni, correo, clave, estado)
		    VALUES (:user_dni, :user_correo, md5(:user_clave),'A');";
                
                $sentencia = $this->dblink->prepare($sql);
                $sentencia->bindParam(":user_dni",$user_dni,PDO::PARAM_STR);
                $sentencia->bindParam(":user_correo",$user_correo,PDO::PARAM_STR);
                $sentencia->bindParam(":user_clave",$user_clave,PDO::PARAM_STR);
                $sentencia->execute();
               
                $this->dblink->commit();
                return true;
                
            
        } catch (Exception $exc) {
            $this->dblink->rollback();
            throw $exc;
        }
    }
	}
        
        
?>