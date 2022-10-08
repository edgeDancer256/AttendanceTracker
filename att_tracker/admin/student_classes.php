<?php
    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");

    $course = $_GET['course'];
    $semester = $_GET['semester'];

    $query = "SELECT `subject`, `teacher_id` FROM `classes` WHERE (course_name = '$course' AND semester = $semester)";
    $res;

    $exec = mysqli_query($conn, $query); 
    
    

    if($exec === FALSE) {
        print mysqli_error($conn);
    } else {
        while($temprow = mysqli_fetch_assoc($exec)) {
            $res[] = $temprow;
        }
    }

    if(isset($res)) {
        print json_encode($res);
    } else {
        print "No info matching given parameters";
    }

    mysqli_close($conn);

?>