<?php

include("connection.php");

$name = $_POST['name'];
$address = $_POST['address'];
$phone = $_POST['phone'];
$email = $_POST['email'];
$username = $_POST['username'];
$password = $_POST['password'];
$type = $_POST['type'];

$sql = " INSERT INTO user(owner_name, owner_address, phone_number, email_id, user_name, password, type) VALUES ('$name','$address','$phone','$email','$username','$password','$type') ";
if(mysqli_query($con,$sql))
{
	echo "Registered";
}
else{
echo "Failed";	
}


?>