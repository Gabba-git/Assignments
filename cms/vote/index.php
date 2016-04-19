<?php
 
/*
 * Following code will create a new comment
 * All user details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();

//Get api key 
$headers = apache_request_headers();
$api_key =$headers['Authorization'];

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
if ( $access && isset($_POST['id']) && isset($_POST['vote'])) {
 
	$user_id = $db->getUserId($api_key);    
	$complaint_id = $_POST['id'];
    $vote = $_POST['vote'];
    
	$result =0;
	$vote_id = $db->getVoteId($complaint_id, $user_id);
	if(	$vote_id !=NULL){
		
		$result = $db->updateVote($vote_id, $vote);
	}
	else{
		$vote_id = $db->addVote($user_id, $complaint_id,$vote);
		if($vote_id!=0){
			$result =1;
		}	
	}
 
    // check if row inserted or not
    if ($result !=0) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "vote successfully created.";
		$response["data"] = $db->getVote($vote_id);
 
        // echoing JSON response
        header('Content-type: application/json');
        echo json_encode($response);
    }
    else{
		// failed to insert row
        $response["success"] = 0;
        $response["message"] = "vote not added or already exist";
 		$response["data"] = NULL;

        // echoing JSON response
		header('Content-type: application/json');
        echo json_encode($response);
	}
}
else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing ";
 	$response["data"] = NULL;

    // echoing JSON response
	header('Content-type: application/json');
    echo json_encode($response);

}
?>
