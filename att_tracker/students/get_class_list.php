<?php

    $sid = $_GET['sid'];
    $conn = mysqli_connect("localhost", "lucifer","lucifer");

    mysqli_select_db($conn, "att_tracker");

    $query = "SELECT `student_course`, `semester` FROM students WHERE `student_id`  = \"$sid\";";

    $exec = mysqli_query($conn, $query);

    $result;
    $course;
    $sem;

    while($temprow = mysqli_fetch_assoc($exec)) {
        $course = $temprow['student_course'];
        $sem = $temprow['semester'];
    }

    $query2 = "SELECT * FROM classes WHERE (`course_name` = \"$course\" AND `semester` = $sem)";
    $exec1 = mysqli_query($conn, $query2);

    while($temprow = mysqli_fetch_assoc($exec1)) {
        $result[] = $temprow;
    }

    if(isset($result)) {
        print json_encode($result);
    } else {
        print "No content " . mysqli_error($conn);
    }

    mysqli_close($conn);
?>