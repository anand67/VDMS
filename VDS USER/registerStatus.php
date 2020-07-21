<?php

include("connection.php");

$oid = $_POST['oid'];
$vid = $_POST['vid'];
$status = $_POST['status'];


$sql = " INSERT INTO time_details (organization, vehicle, status) VALUES ('$oid', '$vid', '$status') ";
echo $sql;

if(mysqli_query($con,$sql))
{
	echo "Inserted";
}
else{
echo "Failed";	
}


?>