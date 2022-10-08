<?php

    $cid = $_GET['cid'];
    $sid = $_GET['sid'];
    $date = $_GET['date'];

    $conn = mysqli_connect("localhost", "lucifer","lucifer");

    mysqli_select_db($conn, "att_tracker");

    $query = "SELECT * FROM attendance WHERE (`class_id` = \"$cid\" AND `student_id` = \"$sid\" AND `date` = \"$date\");";

    $exec = mysqli_query($conn, $query);

    $res;

    while($temp_row = mysqli_fetch_assoc($exec)) {
        $res[] = $temp_row;
    }

    if(isset($res)) {
        echo json_encode($res);
    }