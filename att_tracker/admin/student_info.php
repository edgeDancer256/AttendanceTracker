<?php

    $sid = $_GET['sid'];

    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");

    $query = "SELECT student_name, student_course, semester from `students` WHERE student_id = '$sid'";
    $res;

    try{
        $exec = mysqli_query($conn, $query);

        while($tempRow = mysqli_fetch_assoc($exec)) {
            $res[] = $tempRow;
        }

        if(isset($res)) {
            print json_encode($res);
        } else {
            print "No content";
        }
    } catch(Exception $e) {
        print json_encode($e);
    }

    mysqli_close($conn);
?>