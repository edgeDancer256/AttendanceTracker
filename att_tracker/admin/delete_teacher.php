<?php

    mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);

    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");

    $teacher_id = $_GET['id'];

    $query = "DELETE FROM teachers WHERE teacherID = '$teacher_id'";

    $exec = mysqli_query($conn, $query);
    

    if(mysqli_affected_rows($conn) == 1) {
        $res = "Query successfully executed :) :)";
    } else {
        $res = "Something went wrong :( :(" . mysqli_error($conn);
    }
    print json_encode($res);

    mysqli_close($conn);

?>