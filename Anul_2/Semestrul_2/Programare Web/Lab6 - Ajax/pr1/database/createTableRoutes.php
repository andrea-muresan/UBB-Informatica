<?php
// Create connection
$conn = new mysqli("localhost", "root", "", "MyDatabase");
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// sql to create table
$sql = "CREATE TABLE Routes 
        (
        departureStation VARCHAR(255),
        arrivalStation VARCHAR(255)
        )";

if ($conn->query($sql) === TRUE) {
    echo "Table Routes created successfully";
} else {
    echo "Error creating table: " . $conn->error;
}

$conn->close();