<?php
	include 'connection.php';
	$sql = "SELECT * FROM music";
	$query = $conn -> query($sql);
	$arr = array();
	while ($row = $query -> fetch_array()) {
		$arr[] = array(
			'id' => $row['id'], 
			'title' => $row['title'], 
			'artist' => $row['artist'], 
			'url' => $row['url']
		);
	}
	$json = json_encode($arr);
	echo $json;
?>