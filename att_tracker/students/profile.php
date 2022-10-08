<?php

    $sid = $_GET['sid'];
    $conn = mysqli_connect("localhost", "lucifer","lucifer");

    mysqli_select_db($conn, "att_tracker");

    $query = "SELECT * FROM students WHERE student_id = \"$sid\";";

    $exec = mysqli_query($conn, $query);
    $res;

    while($temp = mysqli_fetch_assoc($exec)) {
        $res[] = $temp;
    }

    print(json_encode($res));

?>