<?php

    $tid = $_GET['tid'];
    $conn = mysqli_connect("localhost", "lucifer","lucifer");

    mysqli_select_db($conn, "att_tracker");

    $query = "SELECT `subject`, `semester`, `course_name`,`course_id` FROM `classes` WHERE teacher_id = \"$tid\";";

    $exec = mysqli_query($conn, $query);

    $result;

    while($temprow = mysqli_fetch_assoc($exec)) {
        $result[] = $temprow;
    }

    if(isset($result)) {
        print json_encode(($result));
    } else {
        print "No content " . mysqli_error($conn);
    }

    mysqli_close($conn);
?>