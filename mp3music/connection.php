<?php
	$host = "localhost";
	$user_name = "root";
	$password = "";
	$db_name = "mp3_music";
	$conn = mysqli_connect($host, $user_name, $password, $db_name);

	mysqli_set_charset($conn,"utf8");

?>