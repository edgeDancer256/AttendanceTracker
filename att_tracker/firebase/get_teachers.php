<?php

    require __DIR__.'/vendor/autoload.php';

    use Kreait\Firebase\Factory;
    use Kreait\Firebase\Auth\UserRecord;

    try {
        $factory = (new Factory)->withServiceAccount('attendance-tracker-402d7-firebase-adminsdk-wj229-1905106c56.json');

        $auth = $factory->createAuth();

        $users = $auth->listUsers($defaultMaxResults = 1000, $defaultBatchSize = 1000);

        $res;

        foreach($users as $user) {
            if(str_starts_with($user->uid, "TCH")) {
                $temp_row = $user;
                $res[] = $temp_row;
            }
        }
        print json_encode(($res));
    } catch(FirebaseException $e) {
        echo $e->getMessage();
    }

?>