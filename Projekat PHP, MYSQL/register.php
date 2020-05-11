<?php 
require "conn.php";
$user_name = $_POST["user_name"];
$user_pass = $_POST["password"];
$location = $_POST["location"];

$mysql_qry = "insert into user_data (username, password, location) values ('$user_name', '$user_pass', '$location')";




if($conn->query($mysql_qry) === TRUE) {
echo "Registracija uspjesna! Molimo prijavite se.";
}
else {
echo "Error: " . $mysql_qry . "<br>" . $conn->error;
}
$conn->close();
 
?>