<?php

    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");

    $teacher_id = $_GET['id'];
    $teacher_name = $_GET['name'];
    $teacher_email = $_GET['email'];
    $teacher_phone = $_GET['phone'];
    $teacher_dept = $_GET['dept'];
    
    $query = "INSERT INTO teachers VALUES ('$teacher_id', '$teacher_name', '$teacher_email', '$teacher_phone', '$teacher_dept')";
    $exec = mysqli_query($conn, $query);

    if(!$exec) {
        $res = "Something went wrong..." . mysqli_error($conn);
    } else {
        $res = "Teacher added successfully!!!";
    }
    print json_encode($res);

    mysqli_close($conn);
?>