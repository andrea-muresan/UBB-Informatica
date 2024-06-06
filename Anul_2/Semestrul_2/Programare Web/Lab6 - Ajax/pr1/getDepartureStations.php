<?php
$conn = new mysqli("localhost", "root", "", "MyDatabase");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$stmt = $conn->prepare("SELECT DISTINCT(departureStation) FROM Routes;");
$stmt->execute();
$stmt->bind_result($result);

$arr = array();

while($stmt->fetch()) {
    $arr[] = $result;
}
echo json_encode($arr);

$stmt->close();
$conn->close();
