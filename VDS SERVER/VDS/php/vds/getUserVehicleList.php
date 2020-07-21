<?php

include("connection.php");

$uid = $_REQUEST['uid'];

$sql = " SELECT * FROM vehicle WHERE uid = '$uid' ";
$res = mysqli_query($con,$sql);

if(mysqli_num_rows($res) > 0 )
{
	while ($row = mysqli_fetch_assoc($res)) {
		$data['data'][] = $row;
	}

	echo json_encode($data);
}
else{
echo "Failed";	
}


?>