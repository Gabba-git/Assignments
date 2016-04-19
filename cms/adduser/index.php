<?php
 
/*
 * Following code will create a new user
 * All user details are read from HTTP Post Request
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
if ( $access && isset( $_POST['id']) && isset($_POST['pass']) && isset($_POST['email']) && isset($_POST['hostel'])) {
 
    $user_id = $_POST['id'];
    $password = $_POST['pass'];
    $email = $_POST['email'];
    $hostel = $_POST['hostel'];
    $user_type = 'Student';
    if(isset($_POST['type'])){
        if($_POST['type']!=""){
    	$user_type = $_POST['type'];
        }
    }
    $result = $db->createUser($user_id, $email, $password, $hostel, $user_type);
 
    // check if row inserted or not
    if ($result == 1) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "User successfully created.";
		$response["data"] = $db->getUserById($user_id);
 
        // echoing JSON response
        header('Content-type: application/json');
        echo json_encode($response);
    } elseif($result == 0) {
        // failed to insert row
        header('Content-type: application/json');
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
		$response["data"] = NULL;
 
        // echoing JSON response
		header('Content-type: application/json');
        echo json_encode($response);
    }
    else{
		// failed to insert row
        $response["success"] = 0;
        $response["message"] = "User or Email Already exist";
 		$response["data"] = NULL;

        // echoing JSON response
		header('Content-type: application/json');
        echo json_encode($response);
	}
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing or don't have right to add user";
 	$response["data"] = NULL;

    // echoing JSON response
	header('Content-type: application/json');
    echo json_encode($response);

}
?>
