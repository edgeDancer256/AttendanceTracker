<?php
    $uid = $_GET['uid'];

    require __DIR__.'/vendor/autoload.php';

    use Kreait\Firebase\Factory;
    use Kreait\Firebase\Auth\UserRecord;

    try {
        $factory = (new Factory)->withServiceAccount('attendance-tracker-402d7-firebase-adminsdk-wj229-1905106c56.json');

        $auth = $factory->createAuth();

        try {
            $auth->deleteUser($uid);
        } catch (FirebaseException $e) {
            print json_encode($e);
        }
    } catch (FirebaseException $e) {
        print json_encode($e);
    }
?>