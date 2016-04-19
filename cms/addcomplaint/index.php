<?php
 
/*
 * Following code will create a new complaint
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
if ( $access && isset( $_POST['title']) && isset($_POST['location']) && isset($_POST['category']) && isset($_POST['body'])) {
 
	$user_id = $db->getUserId($api_key);    
	$title = $_POST['title'];
    $location = $_POST['location'];
    $body = $_POST['body'];
    $tag_hostel = NULL;
    $tag_category = $_POST['category'];
	if($tag_category == 'Hostel'){
		$tag_hostel = $db->getUserById($user_id)['hostel'];
	}
    
	$result = $db->addComplaint($user_id, $title, $body, $tag_category, $tag_hostel,$location);
 
    // check if row inserted or not
    if ($result == 1) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Complaint successfully created.";
		$complaint_id = $db->getIdByTitle($title);
		$response["data"] = $db->getComplaintById($complaint_id);
 
        // echoing JSON response
        header('Content-type: application/json');
        echo json_encode($response);
    }
    else{
		// failed to insert row
        $response["success"] = 0;
        $response["message"] = "comaplaint not added or already exist";
 		$response["data"] = NULL;

        // echoing JSON response
		header('Content-type: application/json');
        echo json_encode($response);
	}
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing ";
 	$response["data"] = NULL;

    // echoing JSON response
	header('Content-type: application/json');
    echo json_encode($response);

}
?>
