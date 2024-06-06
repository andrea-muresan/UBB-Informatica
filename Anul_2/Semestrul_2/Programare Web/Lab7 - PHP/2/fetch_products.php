<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "MyDatabase";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("DB connection failed: " . $conn->connect_error);
}

if (isset($_GET['noProducts'])) {
    $number = intval($_GET['noProducts']);
} else {
    $number = 10;
}

if (isset($_GET['page'])) {
    $page = intval($_GET['page']);
} else {
    $page = 1;
}

$offset = ($page - 1) * $number;

$sql = "SELECT * FROM products LIMIT $number OFFSET $offset";
$result = $conn->query($sql);

$sql_total = "SELECT COUNT(*) as total FROM products";
$total_result = $conn->query($sql_total);
$total_row = $total_result->fetch_assoc();
$total = $total_row['total'];

if ($result->num_rows > 0) {
    echo "<table>";
    echo "<tr><th>Name</th><th>Price</th><th>Reviews</th></tr>";

    while ($row = $result->fetch_assoc()) {
        echo "<tr>";
        echo "<td>" . $row['name'] . "</td>";
        echo "<td>" . $row['price'] . "</td>";
        echo "<td>" . $row['reviews'] . "</td>";
        echo "</tr>";
    }

    echo "</table>";

    echo "<div>";

    if ($page > 1) {
        $previousPage = $page - 1;
        echo "<button><a href='fetch_products.php?noProducts=$number&page=$previousPage'>Previous page</a></button>";
    }

    if (($page * $number) < $total) {
        $nextPage = $page + 1;
        echo "<button><a href='fetch_products.php?noProducts=$number&page=$nextPage'>Next page</a></button>";
    }

    echo "</div>";
} else {
    echo "There is no data to fetch";
}

$conn->close();
?>
