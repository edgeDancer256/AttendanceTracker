<?php

    $course_id = $_GET['cid'];
    $class_id = $_GET['cl_id'];
    $semester = $_GET['sid'];
    $date = $_GET['date'];

    $conn = mysqli_connect("localhost", "lucifer","lucifer");

    mysqli_select_db($conn, "att_tracker");

    $query = "SELECT student_id, student_name from students WHERE(student_course = \"$course_id\" AND semester = \"$semester\");";

    $exec = mysqli_query($conn, $query);
    $res;

    while($temp = mysqli_fetch_assoc($exec)) {
        $s_name = $temp['student_name'];
        $s_id = $temp['student_id'];

        $get_report = "SELECT * FROM attendance WHERE (`class_id` = \"$class_id\" AND `student_id` = \"$s_id\" AND `date` = \"$date\");";

        $exec_get_report = mysqli_query($conn, $get_report);

        while($temp = mysqli_fetch_assoc($exec_get_report)) {
            $status = $temp['status'];
            $time = $temp['time'];

            $res[] = array("s_name"=>$s_name,"time"=>$time, "status"=>$status);

        }

    }

    if(isset($res)) {
        print json_encode($res);
    }
?>