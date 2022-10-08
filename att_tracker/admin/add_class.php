<?php
    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");

    $course_id = $_GET['course_id'];
    $course_name = $_GET['course_name'];
    $semester = $_GET['semester'];
    $subject = $_GET['subject'];
    $teacher_id = $_GET['teacher_id'];

    $query = "INSERT INTO classes VALUES ('$course_id', '$course_name', $semester, '$subject', '$teacher_id')";

    $exec = mysqli_query($conn, $query);

    if(!$exec) {
        $res = "Something went wrong :( :( " . mysqli_error($conn);
    } else {
        $res = "Query successfully executed :) :)";
    }
    print json_encode($res);

    mysqli_close($conn);
?>
    