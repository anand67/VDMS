<?php

include("connection.php");

$vid = $_REQUEST['vid'];

$sql = " SELECT * FROM vehicle WHERE id = '$vid' ";
// echo $sql;
$res = mysqli_query($con,$sql);

if(mysqli_num_rows($res) > 0 )
{
	$row = mysqli_fetch_assoc($res);

	$sql = " SELECT * FROM user WHERE id = '$row[uid]' ";
	// echo $sql;
	$res = mysqli_query($con,$sql);
	$urow = mysqli_fetch_assoc($res);
	
	$data['data'][] = array('owner_name' => $urow['owner_name'],'phone_number' => $urow['phone_number'],'email_id' => $urow['email_id'],'type' => $urow['type'],
							'vehicle_number' => $row['vehicle_number'],'vehicle_model' => $row['vehicle_model']);

	echo json_encode($data);

}
else{
echo "Failed";	
}


?>