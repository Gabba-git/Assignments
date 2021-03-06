<?php
 
/*
 * Following code will create a new user
 * All user details are read from HTTP Post or GET Request both will work
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


// check for required fields
if ($db->isValidApiKey($api_key)) {
 	
	$user_id = $db->getUserId($api_key);
    
    $result = $db->signOut ($user_id);
 
    // check if logout or not
    if ($result == 1) {
        // successfully logout into database
        $response["success"] = 1;
        $response["message"] = "User successfully logout.";
 
        // echoing JSON response
        header('Content-type: application/json');
        echo json_encode($response);
    } else{
        // failed to logout
        header('Content-type: application/json');
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
		header('Content-type: application/json');
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
	header('Content-type: application/json');
    echo json_encode($response);

}
?>
