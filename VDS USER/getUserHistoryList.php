<?php

include("connection.php");

$uid = $_REQUEST['uid'];

$sql = " select date_time, t.status, v.vehicle_model, o.name from time_details t inner join vehicle v on v.id = t.vehicle inner join organization o on t.organization = o.id WHERE v.uid = '$uid' Order by date_time DESC";
$res = mysqli_query($con,$sql);

if(mysqli_num_rows($res) > 0 )
{
	while($row = mysqli_fetch_assoc($res))
		$data['data'][] = $row;
	echo json_encode($data);
}
else{
echo "Failed";	
}


?>