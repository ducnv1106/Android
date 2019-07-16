<?php
	include 'connection.php';
	if (!isset($_POST['user_name'])
	|| !isset($_POST['password'])
	|| !isset($_POST['name'])) {
		header( 'HTTP/1.0 500 Field is require');
		return;
	}

	$user_name = $_POST['user_name'];
	$password = $_POST['password'];
	$name = $_POST['name'];
	$sql = "INSERT INTO user VALUES('$user_name', '$password', '$name')";
	$result = $conn -> query($sql);
	if ($result) {
		header( 'HTTP/1.0 200 Register success');
	}else{
		header( 'HTTP/1.0 202 Register fail');
	}
?>