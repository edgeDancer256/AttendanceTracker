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

    $query2 = "SELECT `course_id` FROM classes WHERE (`course_name` = \"$course\" AND `semester` = \"$sem\")";
    $exec1 = mysqli_query($conn, $query2);

    while($temprow = mysqli_fetch_assoc($exec1)) {
        $cid = $temprow['course_id'];

        $query_p = "SELECT count(`status`) as `Present` FROM attendance WHERE (`class_id` = \"$cid\" AND `student_id` = \"$sid\" AND `status` = \"Present\");";

        $query_a = "SELECT count(`status`) as `Absent` FROM attendance WHERE (`class_id` = \"$cid\" AND `student_id` = \"$sid\" AND `status` = \"Absent\");";

        $exec_p = mysqli_query($conn, $query_p);
        $exec_a = mysqli_query($conn, $query_a);

        $res;
        $res_p;
        $res_a;

        while($temp_row = mysqli_fetch_assoc($exec_p)) {
            $res_p = $temp_row['Present'];
        }

        while($temp_row = mysqli_fetch_assoc($exec_a)) {
            $res_a = $temp_row['Absent'];
        }

        if(isset($res_a) and isset($res_p)) {
            $result[] = array("class_id"=>$cid, "Present"=>$res_p, "Absent"=>$res_a);
        }

    }

    if(isset($result)) {
        echo json_encode($result);
    }