<?php
 
/*
 * Following code will update user
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
	
	$access = 1;
}

// check for required fields
if ( $access && isset( $_POST['id']) ) {

	$user_id = $_POST['id'];
	$result =0;
	$user = $db->getUserId($api_key);
	$user_req = $db->getUserById($user);
	$priority = $db->getUserTypePriority($user_req['user_type']);

	//Check if have access to edit profile
	if(($db->isUserExists($user_id)) && (($user_id == $user) || ($priority == 5))){
		$user_up = $db->getUserById($user_id);
		$email = $user_up['email'];
		$hostel = $user_up['hostel'];
		$user_type = $user_up['user_type'];
		$first_name = $user_up['first_name'];
		$last_name = $user_up['last_name'];
		$address = $user_up['address'];
		$phone = $user_up['phone'];
		
		if(isset($_POST['email'])){
    			$email = $_POST['email'];
    	}

		if(isset($_POST['first_name'])){
    			$first_name = $_POST['first_name'];
    	}
		if(isset($_POST['last_name'])){
    			$last_name = $_POST['last_name'];
    	}
		if(isset($_POST['address'])){
    			$address = $_POST['address'];
    	}
		if(isset($_POST['phone'])){
    			$phone = $_POST['phone'];
    	}

		if($priority ==5){
			if(isset($_POST['hostel'])){
    			$hostel = $_POST['hostel'];
    		}
			if(isset($_POST['user_type'])){
    			$user_type = $_POST['user_type'];
    		}
		}
    $result = $db->updateProfile($user_id, $email, $hostel, $user_type, $first_name, $last_name, $phone, $address);
 	}
    // check if row updated or not
    if ($result == 1) {
        // successfully updated into database
        $response["success"] = 1;
        $response["message"] = "User successfully updated.";
		$response["data"] = $db->getUserById($user_id);
 
        // echoing JSON response
        header('Content-type: application/json');
        echo json_encode($response);
    } else{
        // failed to update row
        header('Content-type: application/json');
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
		$response["data"] = NULL;
 
        // echoing JSON response
		header('Content-type: application/json');
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing or wrong information";
 	$response["data"] = NULL;

    // echoing JSON response
	header('Content-type: application/json');
    echo json_encode($response);

}
?>
