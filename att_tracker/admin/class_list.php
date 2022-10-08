<?php

    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");


    $course = $_GET['course'];

    $query = "SELECT * FROM classes WHERE course_name = '$course'";

    $rows = mysqli_query($conn, $query);

    $result;

    while($temp_row = mysqli_fetch_assoc($rows)) {
        $result[] = $temp_row;
    }

    print json_encode(($result));

    mysqli_close($conn);

?>