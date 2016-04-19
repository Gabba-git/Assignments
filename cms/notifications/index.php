<?php
 
/*
 * Following code will get notifications
 * All user and complaint details are read from HTTP GET Request
 */
 
// array for JSON response
$response = array();

//Get api key 
$headers = apache_request_headers();
$api_key = $headers['Authorization'];

// include db functions class
require_once '../include/DbHandler.php';
 
// connecting to db
$db = new DBHandler();

//check validity of api-key
$access = 0;
if($db->isValidApiKey($api_key)){
	
	$access = 1;
}

// check for required fields
if ( $access) {
	
	$user_id = $db->getUserId($api_key);
  
        
        $response["success"] = 1;
        $response["message"] = "Query successfull.";
		$response["data"] = $db->getNotifications($user_id);
 
        // echoing JSON response
        header('Content-type: application/json');
        echo json_encode($response);

} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 	$response["data"] = NULL;

    // echoing JSON response
	header('Content-type: application/json');
    echo json_encode($response);

}
?>
