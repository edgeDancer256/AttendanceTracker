<?php
    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");
    
    $tid = $_GET['tid'];

    $query = "SELECT * FROM `classes` WHERE teacher_id = '$tid'";

    $teacher = mysqli_query($conn, $query);
    $result;

    while($temprow = mysqli_fetch_assoc($teacher)) {
        $res[] = $temprow;   
    }

    if(isset($res)) {
        print json_encode($res);
    } else {
        print mysqli_error($conn);
    }

    mysqli_close($conn);
?>