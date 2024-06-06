<?php
$conn = new mysqli("localhost", "root", "", "MyDatabase");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$page = $_GET["page"];
$page = $page * 3;

$stmt = $conn->prepare("SELECT firstname, lastname, phoneNumber, email FROM users LIMIT ?, 3;");
$stmt->bind_param("i", $page);
$stmt->execute();
$stmt->bind_result($firstname, $lastname, $phoneNumber, $email);

$users = array();

while ($stmt->fetch()) {
    $users[] = array("firstname" => $firstname, "lastname" => $lastname, "phoneNumber" => $phoneNumber, "email" => $email);
}

$stmt = $conn->prepare("SELECT COUNT(*) as 'count' FROM users;");
$stmt->execute();
$stmt->bind_result($count);
$stmt->fetch();

$arr = array("start" => $page, "end" => max($count - $page - 3, 0), "users" => $users);
echo json_encode($arr);

$stmt->close();
$conn->close();
