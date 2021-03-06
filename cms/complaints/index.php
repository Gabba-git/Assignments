<?php
 
/*
 * Following code will get complaints as required
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
if ( $access && isset( $_GET['user'])) {
	
	$user_id = $db->getUserId($api_key);
	$user = $db->getUserById($user_id);
	$priority = $db->getUserTypePriority($user['user_type']);
	$hostel = $user['hostel'];
	$category = 'Personal';
	$order_by = 'created_at';
	$order = 'DESC';
    $status = 2;
	$req = $_GET['user'];
	if(isset( $_GET['category'])){
		$category = $_GET['category'];
	}
	if(isset( $_GET['order_by'])){
		$order_by = $_GET['order_by'];
	}
	if(isset( $_GET['order'])){
		$order = $_GET['order'];
	}
	if(isset( $_GET['status'])){
		$status = $_GET['status'];
	}
	$order = $order_by.$order;
	if(($req == 'self')||($req == 'all')){

		
		// get all my complaints
    	$response["success"] = 1;
    	$response["message"] = "Query Successful";
 		$response["data"] = $db->getListComplaints($req, $category,$user_id,$hostel,$order,$status);

    	// echoing JSON response
		header('Content-type: application/json');
    	echo json_encode($response);	


	}
	else{
		// required field is missing
    	$response["success"] = 0;
    	$response["message"] = "Required field(s) is missing or is wrong";
 		$response["data"] = NULL;

    // echoing JSON response
	header('Content-type: application/json');
    echo json_encode($response);	
	}
	
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
