<?php
	include 'connection.php';
	if (!isset($_POST['user_name'])
	|| !isset($_POST['password'])) {
		header( 'HTTP/1.0 500 Field is require');
		return;
	}

	$user_name = $_POST['user_name'];
	$password = $_POST['password'];
	
	$sql = "SELECT * FROM user WHERE user_name = '$user_name' AND password = '$password'";
	$query = $conn -> query($sql);
	$row = $query -> fetch_array();
	if ($row) {
		$data = array(
			'user_name' => $row['user_name'], 
			'password' => $row['password'], 
			'name' => $row['name']
		);
		echo json_encode($data);
	}else{
		header( 'HTTP/1.0 400 User name or password incorrect');
	}
?>