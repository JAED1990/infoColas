<?php
	require_once ("../negocio/TiempoTranscurrido.php");

	$date = '2015-12-06 01:28:04';
	$hoy = '2015-12-06 03:28:04';
	echo $hoy;
	$unix = strtotime($date);

	$res = TiempoTranscurrido::timeago(strtotime($date));

	echo $res;
	echo "<br>";
	//echo date_diff($hoy,$date);
	//echo date("Y-m-d H:i:s")->diff($date);

	//$datetime1->diff($datetime2);

?>