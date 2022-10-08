<?php

    $uid = $_GET['uid'];
    $email = $_GET['email'];
    $phoneNumber = $_GET['phoneNumber'];
    $password = $_GET['password'];
    $displayName = $_GET['displayName'];


    require __DIR__.'/vendor/autoload.php';

    use Kreait\Firebase\Factory;
    use Kreait\Firebase\Auth\UserRecord;

    try {
        $factory = (new Factory)->withServiceAccount('attendance-tracker-402d7-firebase-adminsdk-wj229-1905106c56.json');

        $auth = $factory->createAuth();

        $userProps = [
            'uid'=>$uid,
            'email'=>$email,
            'emailVerified'=>false,
            'phoneNumber'=>'+91'.$phoneNumber,
            'password'=>$password,
            'displayName'=>$displayName,
            'disabled'=>false,
        ];
        try {
            $createdUser = $auth->createUser($userProps);
        } catch(FirebaseException $e) {
            echo $e->getMessage();
        }
        

        if($createdUser) {
            echo "Created User successfully";
        }

    } catch (FirebaseException $e) {
        echo "Something went wrong";
    }

?>