<?php

    $conn = mysqli_connect("localhost", "lucifer", "lucifer");
    mysqli_select_db($conn, "att_tracker");

    $query = "SELECT * FROM `classes`";

    $rows = mysqli_query($conn, $query);

    $result;

    while($temp_row = mysqli_fetch_array($rows)) {
        $result[] = $temp_row;
        
    }

    print (json_encode($result));

    mysqli_close($conn);

?>