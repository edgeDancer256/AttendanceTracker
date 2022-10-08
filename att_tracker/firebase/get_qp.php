<?php

    $course = $_GET['id'];

    require __DIR__.'/vendor/autoload.php';

    use Kreait\Firebase\Factory;
    use Kreait\Firebase\Auth\UserRecord;

    try {
        $factory = (new Factory)->withServiceAccount('attendance-tracker-402d7-firebase-adminsdk-wj229-1905106c56.json');

        $storage = $factory->createStorage();

        $bucket = $storage->getBucket();
        $options = ['prefix' => 'Question Papers/'.$course . '/'];

        $res;

        foreach($bucket->objects($options) as $obj) {
            $arr = array("name"=> $obj->name(), "token"=>$obj->info()['metadata']['firebaseStorageDownloadTokens']);
            
            $res[] = $arr;
        }

        if(isset($res)) {
            print json_encode($res);
        } else {
            print "No content";
        }

    } catch(FirebaseException $e) {
        print $e->getMessage();
    }