<?php

include("connection.php");

$uid = $_POST['uid'];
$vehicleModel = $_POST['vehicleModel'];
$vehicleNumber = $_POST['vehicleNumber'];
$vehicleColor = $_POST['vehicleColor'];

$sql = " INSERT INTO vehicle(uid, vehicle_number, vehicle_model, vehicle_color) VALUES ('$uid','$vehicleNumber','$vehicleModel','$vehicleColor') ";
if(mysqli_query($con,$sql))
{
	echo "New Vehicle Added";
}
else{
echo "Failed";	
}


?>