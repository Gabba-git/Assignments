<?php
/*
 * Following code will change password of user
 * All user details are read from HTTP POST Request
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
$user_id ='';
if($db->isValidApiKey($api_key)){
	$user_id = $db-> getUserId($api_key); 
	$access = 1;

}

// array for JSON response
$response = array();
// check for required fields
if ($access && isset($_POST['old']) && isset($_POST['new'])) {
 	
    $old = $_POST['old'];
	$new = $_POST['new'];
	if($db->changePassword($user_id,$old,$new)){
    	$response["success"] = 1;
    	$response["message"] = "changed successfully";

		$result = $db->getApiKeyById($user_id);
		header('Authorization: '.$result['api_key'].'');
 		//header('Content-Type: application/json')
		header('Content-type: application/json');
    	// echoing JSON response
    	echo json_encode($response);
	}
	else{
		$response["success"] = 0;
    	$response["message"] = "Not valid password or wrong old password.";
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
