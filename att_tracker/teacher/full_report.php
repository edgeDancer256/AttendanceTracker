<?php

    $tid = $_GET['tid'];

    $conn = mysqli_connect("localhost", "lucifer","lucifer");

    mysqli_select_db($conn, "att_tracker");

    

    $class_list = "SELECT `subject`, `semester`, `course_name`,`course_id` FROM `classes` WHERE teacher_id = \"$tid\";";

    $exec_class_list = mysqli_query($conn, $class_list);
    while($temp = mysqli_fetch_assoc($exec_class_list)) {
        $course_id = $temp['course_id'];
        $class_name = $temp['subject'];
        $course_name = $temp['course_name'];
        $semester = $temp['semester'];
        
        $std_count = "SELECT count(`student_id`) as 'count' from students WHERE(student_course = \"$course_name\" AND semester = \"$semester\");";
        $att_report_p = "SELECT count(`status`) as 'present'FROM attendance WHERE (`class_id` = \"$course_id\" AND `status` = \"Present\");";
        $att_report_a = "SELECT count(`status`) as `absent` FROM attendance WHERE (`class_id` = \"$course_id\" AND `status` = \"Absent\");";

        $exec_std_count = mysqli_query($conn, $std_count);
        $exec_att_p = mysqli_query($conn, $att_report_p);
        $exec_att_a = mysqli_query($conn, $att_report_a);

        $res;
        $res_p;
        $res_a;
        $count;

        while($temp = mysqli_fetch_assoc($exec_std_count)) {
            $count = $temp['count'];
        }


        while($temp = mysqli_fetch_assoc($exec_att_p)) {
            $res_p = $temp['present'];
        }

        while($temp = mysqli_fetch_assoc($exec_att_a)) {
            $res_a = $temp['absent'];
        }

        if(isset($count) and isset($res_p) and isset($res_a)) {
            $res[] = array("subject"=>$class_name,"count"=>$count, "present"=>$res_p, "absent"=>$res_a);
        }
    }

    if(isset($res)) {
        print json_encode($res);
    }
