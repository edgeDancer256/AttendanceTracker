<?php

    $class_id = $_GET['cid'];
    $student_id = $_GET['sid'];
    $date = $_GET['date'];
    $time = $_GET['time'];
    $status = $_GET['status'];

    $conn = mysqli_connect("localhost", "lucifer","lucifer");

    mysqli_select_db($conn, "att_tracker");

    $query = "INSERT INTO attendance(`id`, `class_id`, `student_id`, `date`, `time`, `status`) VALUES (0, '$class_id', '$student_id', '$date', '$time', '$status')";

    $exec = mysqli_query($conn, $query);

    if(!$exec) {
        $res = "Something went wrong :( :( " . mysqli_error($conn);
    } else {
        $res = "Query successfully executed :) :)";
    }
    print json_encode($res);

    mysqli_close($conn);
?>