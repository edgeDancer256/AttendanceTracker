<?php

    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");

    $class_id = $_GET['class_id'];

    $query = "DELETE FROM classes WHERE course_id = '$class_id'";

    $exec = mysqli_query($conn, $query);

    if(!mysqli_affected_rows($conn) > 0) {
        $res = "Something went wrong :( :(";
    } else {
        $res = "Query successfully executed :) :)";
    }
    print json_encode($res);

    mysqli_close($conn);

?>
