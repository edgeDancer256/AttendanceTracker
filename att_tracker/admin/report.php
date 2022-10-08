<?php

    $course_name = $_GET['cname'];
    $course_id = $_GET['cid'];
    $semester = $_GET['sem'];

    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");

    $std_list = "SELECT student_id, student_name from students WHERE(student_course = \"$course_name\" AND semester = \"$semester\");";

    $exec_std_list = mysqli_query($conn, $std_list);
    $res;

    while($temp = mysqli_fetch_assoc($exec_std_list)) {
        $sid = $temp['student_id'];
        $sname = $temp['student_name'];

        $att_rep_p = "SELECT count('status') as 'present' FROM attendance WHERE (student_id = \"$sid\" AND class_id = \"$course_id\" AND status = 'Present');";
        $att_rep_a = "SELECT count('status') as 'absent' FROM attendance WHERE (student_id = \"$sid\" AND class_id = \"$course_id\" AND status = 'Absent');";

        $exec_att_rep_p = mysqli_query($conn, $att_rep_p);
        $exec_att_rep_a = mysqli_query($conn, $att_rep_a);

        while($temp = mysqli_fetch_assoc($exec_att_rep_p)) {
            $p = $temp['present'];
        }
        while($temp = mysqli_fetch_assoc($exec_att_rep_a)) {
            $a = $temp['absent'];
        }

        if(isset($sname) and isset($p) and isset($a)) {
            $res[] = array("name"=>$sname, "present"=> $p, "absent"=>$a);
        }
    }

    if(isset($res)) {
        print json_encode($res);
    }
