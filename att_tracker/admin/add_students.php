<?php
    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");

    $student_id = $_GET['student_id'];
    $student_name = $_GET['student_name'];
    $student_email = $_GET['student_email'];
    $student_phone = $_GET['student_phone'];
    $student_course = $_GET['student_course'];
    $student_semester = $_GET['student_semester'];

    $query = "INSERT INTO students VALUES('$student_id', '$student_name', '$student_email', '$student_phone', '$student_course', '$student_semester')";

    $exec = mysqli_query($conn, $query);
    $res;

    if(!$exec) {
        $res = "Something went wrong..." . mysqli_error($conn);
    } else {
        $res = "Student added successfully!!!";
    }
    print json_encode($res);

    mysqli_close($conn);
?>