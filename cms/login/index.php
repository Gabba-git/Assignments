<?php
/*
 * Following code will login user
 * All user details are read from HTTP GET Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_GET['id']) && isset($_GET['pass'])) {
 
    $user_id = $_GET['id'];
    $password = $_GET['pass'];
   
    // include db functions class
    require_once '../include/DbHandler.php';
    
 
    // connecting to db
    $db = new DBHandler();
    $result = $db->CheckLogin($user_id, $password);
 
    // check if Login Successful or not
    if ($result == 1) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "User successfully Login.";
        $response["data"] = $db->getUserById($user_id);

		$result = $db->getApiKeyById($user_id);

 		//header('Content-Type: application/json')
		header('Content-type: application/json');
        header('Authorization:'.$result['api_key'].'');
        // echoing JSON response
        echo json_encode($response);
    } 
    else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Incorrect Password or user ID";
        $response["data"] = NULL;
        // echoing JSON response
        header('Content-type: application/json');
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
