<?php

    $course_id = $_GET['cid'];
    $semester = $_GET['sid'];

    $conn = mysqli_connect("localhost", "lucifer","lucifer");

    mysqli_select_db($conn, "att_tracker");

    $query = "SELECT student_id, student_name from students WHERE(student_course = \"$course_id\" AND semester = \"$semester\");";

    $exec = mysqli_query($conn, $query);
    $res;

    while($temp = mysqli_fetch_assoc($exec)) {
        $res[] = $temp;
    }

    print json_encode($res);
?>
