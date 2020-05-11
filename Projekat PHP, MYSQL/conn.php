<?php 
$db_name = "users";
$mysql_username = "irfan";
$mysql_password = "irfan";
$server_name = "localhost";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password,$db_name);
 

//provjera konekcije
/*
if($conn) {
	echo "konekcija je uspjela";
}
else{
	echo "konekcija nije uslpjela";
}
*/
?>