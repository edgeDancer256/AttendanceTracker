<?php

    $course_name = $_GET['cname'];
    $course_id = $_GET['cid'];
    $semester = $_GET['sem'];
    $tid = $_GET['tid'];

    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");

    $teachName;
    $classCount;
    $studentCount;


    $tname = "SELECT teacherName FROM teachers WHERE teacherID = \"$tid\";";
    $exec_tname = mysqli_query($conn, $tname);
    while($temp = mysqli_fetch_assoc($exec_tname)) {
        $teachName = $temp['teacherName'];
    }

    $std_count = "SELECT count(*) as 'count' FROM students WHERE (student_course = \"$course_name\" AND semester = \"$semester\");";
    $exec_std_count = mysqli_query($conn, $std_count);
    while($temp = mysqli_fetch_assoc($exec_std_count)) {
        $studentCount = $temp['count'];
    }

    $c_count = "SELECT count(*) as 'count' FROM attendance WHERE class_id = \"$course_id\";";
    $exec_c_count = mysqli_query($conn, $c_count);
    while($temp = mysqli_fetch_assoc($exec_c_count)) {
        $classCount = $temp['count'];
    }

    $res[] = array("tname"=>$teachName, "class"=>$classCount, "std"=>$studentCount);

    if(isset($res)) {
        print json_encode($res);
    }