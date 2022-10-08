<?php

    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");

    $student_id = $_GET['id'];

    $query = "DELETE FROM students WHERE student_id = '$student_id'";

    $exec = mysqli_query($conn, $query);

    if(!mysqli_affected_rows($conn) > 0) {
        $res = "Something went wrong :( :(";
    } else {
        $res = "Query successfully executed :) :)";
    }
    print json_encode($res);

    mysqli_close($conn);
?>