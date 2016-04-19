<?php
/*
 * Following code will Delete user
 * All user details are read from HTTP POST Request
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
	$user = $db->getUserId($api_key);
	$user_type = $db->getUserById($user)['user_type'];
	$priority = $db->getUserTypePriority($user_type);
	//Check User rights
	if($priority==5){
		$access = 1;
	}
}
 
// check for required fields
if ($access && isset($_POST['id'])) {
 
    $user_id = $_POST['id'];
    $result = $db->deleteUser($user_id);
 
    // check if Delete Successful or not
    if ($result == 1) {
        // successfully deleted into database
        $response["success"] = 1;
        $response["message"] = "User successfully deleted.";
		
		header('Content-type: application/json');
        // echoing JSON response
        echo json_encode($response);
    } 
    else {
        // failed to delete
        $response["success"] = 0;
        $response["message"] = "Incorrect user_id or don't have right to delete";
        $response["data"] = NULL;

		header('Content-type: application/json');
        // echoing JSON response
        echo json_encode($response);
    }
    }
    else {
    	// required field is missing
    	$response["success"] = 0;
    	$response["message"] = "Required field(s) is missing";

		header('Content-type: application/json');
    	// echoing JSON response
    	echo json_encode($response);

	}

?>
