# Weather
Naziv Fakulteta: Fakultet za saobraćaj i komunikacije
Naziv projekta: Aplikacija za vremensku prognozu
Imena učesnika projekta: 
•	Adis Šehović
•	Irfan Hadžibajrić
•	Aldin Emšo
•	Toni Marjanović
Datum izrade: 12.05.2020.g.
Naziv predmeta: Razvoj mobilnih aplikacija
Ime profesora: doc.dr. Damir Omerašević
BAZA PODATAKA
Prije pokretanja same aplikacije potrebno je kreirati bazu podataka na serveru. U nastavku je prikazan kod  baze podataka:
BAZA PODATAKA

-- Database: `users`

-- Table structure for table `user_data`

CREATE TABLE `user_data` (
  `id` int(11) NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `location` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Indexes for table `user_data`

ALTER TABLE `user_data`
  ADD PRIMARY KEY (`id`);

-- AUTO_INCREMENT for table `user_data`
ALTER TABLE `user_data`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

 
KOMUNIKACIJA IZMEĐU BAZE I APLIKACIJE
Komunikacija baze i aplikacije omogućena je pomoću php-a. U našem slučaju koriste se tri php fajla. Sva tri php fajla potrebno je spremiti u HTDOCS folder, ukoliko koristite XAMPP, a u slučaju WAMP-a php-ove je potrebno spremiti u folder WWW. 

Prvi php fajl (conn.php) služi za konektovanje na samu bazu. U njemu je potrebno izvršiti promjene dvije varijable:
$mysql_username = "root";
$mysql_password = "";
Ove dvije varijable predstavljaju korisnika kojem su dodijeljene privilegije na phpmyadmin-u.
U php fajlovima register.php i login.php nije potrebno ništa mijenjati.
KODOVI php fajlova

CONN.PHP:
<?php 
$db_name = "users";
$mysql_username = "root";
$mysql_password = "";
$server_name = "localhost";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password,$db_name);
?>
 

LOGIN.PHP:
<?php 
require "conn.php";
$user_name = $_POST["user_name"];
$user_pass = $_POST["password"];
$mysql_qry = "select * from user_data where username like '$user_name' and password like '$user_pass';";

$result = mysqli_query($conn ,$mysql_qry);


if(mysqli_num_rows($result) > 0) {
echo "Login uspjesan! Dobrodosli!";
}
else {
echo "Login neuspjesan! Pokusajte ponovo.";
}
 
?>
 

REGISTER.PHP:
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
 
PROMJENE KOJE JE POTREBNO IZVRŠITI U ANDROID STUDIU
Klasa Backgroundworker:
 
Potrebno je promijeniti IP adresu u adresu Vašeg računara.

WeatherActivity:
Napomena: Redirekcija na web stranicu neće biti u funkciji, jer  neće biti dostavljena uz aplikaciju iz razloga što nije završena.
 
