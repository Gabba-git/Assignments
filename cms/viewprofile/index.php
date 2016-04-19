<?php
/*
 * Following code will send profile details of user
 * All user details are read from HTTP GET Request
 */

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

// array for JSON response
$response = array();
 
// check for required fields
if ($access && isset($_GET['id'])) {
 	
    $user_id = $_GET['id'];
 	
	if($db->isUserExists($user_id)){
    	$response["success"] = 1;
    	$response["message"] = "Query successfully";
    	$response["data"] = $db->getUserById($user_id);

 		//header('Content-Type: application/json')
		header('Content-type: application/json');
    	// echoing JSON response
    	echo json_encode($response);
	}
	else{
		$response["success"] = 0;
    	$response["message"] = "User Doesn't exist.";
    	$response["data"] = NULL;

 		//header('Content-Type: application/json')
		header('Content-type: application/json');
    	// echoing JSON response
    	echo json_encode($response);
	}
} 
else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 	$response["data"] = NULL;
    // echoing JSON response
    header('Content-type: application/json');
    echo json_encode($response);

}

?>
