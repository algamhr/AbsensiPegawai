<?php 
date_default_timezone_set("Asia/Jakarta");

function generateToken($nidn, $waktu){
    $simple_string = $nidn . "/". $waktu;
    $cipher = "AES-128-CTR"; // untuk simpan method cipher
    $option = 0;
    $encryption_iv = '1234567890123456';
    $encryption_key = '3ced8bhu';
    $encryption = openssl_encrypt($simple_string, $cipher, $encryption_key, $option, $encryption_iv);
  	return $encryption;
}

function sendToken($regId, $aesToken){
    $serverKey = "AAAAWR06dJU:APA91bECLnC-_KQBNu5Wy2dxjl77GTog6W7terbNbAxpmT5JlzkPIcmD0YskBJb71LCCJPVpX4X_95tnQOzVmZmghu-GRso2VvKoQ-9fYxLL6uRW_nfxKTqEqbhQWygg6Mj_W95_vbL8";

    $url = "https://fcm.googleapis.com/fcm/send";            

    $header = [
      'Authorization: key='.$serverKey,
      'Content-Type: application/json',
      'Accept: application/json'
    ];

    $body = "Berhasil Mendapatkan Token Baru";

    $extraNotificationData = [
      "custom_notif" => $body,
      "token_absen"=> $aesToken
    ];

    $fcmNotification = [
      'to'        => $regId,
      'data' => $extraNotificationData
    ];

    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 60);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fcmNotification));
    curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
    $result = curl_exec($ch);    
    curl_close($ch);

    echo json_encode($result);
}


$date = date("Y-m-d H:i:s");
$date = strtotime($date);
$date = strtotime("+30 minute", $date);
$next_30 = date('Y-m-d H:i:s', $date);

$con = mysqli_connect("localhost","simdes_alga","ajaccyf14KG8eDuW","simdes_alga");
if($con){
    $sql = "SELECT usr.nidn, usr.fcm_token
            FROM  user usr
            WHERE usr.fcm_token IS NOT NULL";
    $result = mysqli_query($con, $sql);
    if($result){
      while($row = mysqli_fetch_assoc($result)){
        $tokenAbsen = generateToken($row['nidn'], $next_30);
        $tokenFCM = $row['fcm_token'];
        sendToken($tokenFCM, $tokenAbsen);
        echo "<br>";
      }
    }
} else {
    echo "Database Connection Failed";
}

?>