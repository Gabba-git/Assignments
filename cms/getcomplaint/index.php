<?php
 
/*
 * Following code will get complaint by ID
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
if ( $access && isset( $_GET['id'])) {
	
	$user_id = $db->getUserId($api_key);
	$user = $db->getUserById($user_id);
	$priority = $db->getUserTypePriority($user['user_type']);

	$complaint_id = $_GET['id'];
	$result = 1;

	if(!$db->isComplaintExist($complaint_id)){
		$result = 0;
	}
    $complaint = $db->getComplaintById($complaint_id);
	
    // check if have access or not
    if ($result == 1) {
        
        $response["success"] = 1;
        $response["message"] = "Query successfull.";
		$response["data"] = $complaint;
		$response["comments"] = $db->getAllCommentsById($complaint_id);
		$vote = 0;
		$voted = $db->getVoteId($complaint_id, $user_id);
		if($voted!=NULL){
			$vote = $voted['vote'];		
		}
		$response["vote"] = $vote;
 
        // echoing JSON response
        header('Content-type: application/json');
        echo json_encode($response);
	}
    else{
		// failed to access
        $response["success"] = 0;
        $response["message"] = "Don't have access or doesn't exist";
 		$response["data"] = NULL;
		$response["comments"] = NULL;

        // echoing JSON response
		header('Content-type: application/json');
        echo json_encode($response);
	}
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 	$response["data"] = NULL;
	$response["comments"] = NULL;

    // echoing JSON response
	header('Content-type: application/json');
    echo json_encode($response);

}
?>
