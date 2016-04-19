<?php
 
/**
 * Class to handle all db operations
 * This class will have CRUD methods for database tables
 */
class DbHandler {
 
    private $conn;
 
    function __construct() {
        require_once 'DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }
 
    /* ------------- `users` table method ------------------ */
 
    /**
     * Creating new user
     * @param String $user_id User ID
     * @param String $email User login email id
     * @param String $password User login password
     * @param String $user_type User Type
     * @param string $hostel user hostel
     */
    public function createUser($user_id, $email, $password, $hostel, $user_type) {
        require_once 'PassHash.php';
        $response = array();
 
        // First check if user already existed in db
        if (!$this->isUserExists($user_id) && !$this->isEmailExists($email)) {
            // Generating password hash
            $password_hash = PassHash::hash($password);
 			
            // Generating API key
            $api_key = $this->generateApiKey();
 
			if (mysqli_connect_errno()) {
            	echo "Failed to connect to MySQL: " . mysqli_connect_error();
        	}
            
			// insert query
            $stmt = $this->conn->prepare("INSERT INTO users(user_id, password, email, api_key, user_type, hostel) values(?, ?, ?, ?, ?,?)");
            $stmt->bind_param("ssssss", $user_id, $password_hash, $email, $api_key, $user_type, $hostel);
            $result = $stmt->execute();
 
            $stmt->close();
 
            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
        } else {
            // User_id or email already existed
            return USER_ALREADY_EXISTED;
        }
 
        return $response;
    }
    
	/**
     * Deleting  user
     * @param String $user_id User ID
     */
 	public function deleteUser($user_id) {
        $response = array();
 
		if (mysqli_connect_errno()) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }

        // First check if user exist in db
        if ($this->isUserExists($user_id)) {
 
            // insert query
            
            $stmt = $this->conn->prepare("DELETE FROM users WHERE user_id = ?");
            $stmt->bind_param("s", $user_id);
            $result = $stmt->execute();
            $stmt->close();
            // Check for successful Delete
            if ($result) {
                // User successfully Delete
                return USER_DELETED_SUCCESSFULLY;
            } else {
                // Failed to DELETE user
                return USER_DELETE_FAILED;
            }
        } else {
            // User_id doesn't existed
            return USER_NOT_EXIST;
        }
 
        return $response;
    }
    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
    public function checkLogin($user_id, $password) {
        require_once 'PassHash.php';
        // fetching user by email
        $stmt = $this->conn->prepare("SELECT password FROM users WHERE user_id = ?");
 
        $stmt->bind_param("s", $user_id);
 
        $stmt->execute();
 
        $stmt->bind_result($password_hash);
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // Found user with the email
            // Now verify the password
 
            $stmt->fetch();
 
            $stmt->close();
 
            if (PassHash::check_password($password_hash, $password)) {
                // User password is correct
                return TRUE;
            } else {
                // user password is incorrect
                return FALSE;
            }
        } else {
            $stmt->close();
 
            // user not existed with the user_id
            return FALSE;
        }
    }

	// Function for signout
	public function signOut($user_id) {
        	
            // Generating API key
            $api_key = $this->generateApiKey();
 
			if (mysqli_connect_errno()) {
            	echo "Failed to connect to MySQL: " . mysqli_connect_error();
        	}
			// insert query
            $stmt = $this->conn->prepare("UPDATE users SET api_key=? WHERE user_id = ? ");
            $stmt->bind_param("ss", $api_key, $user_id);
            $result = $stmt->execute();
 
            $stmt->close();
 
            // Check for successful insertion
            if ($result) {
                // User successfully logout
                return 1;
            } else {
                // Failed to change
                return 0;
            }
    }

    
	/**
     * Changing User Password
     * @param String $user_id User login 
     * @param String $old User login password old
	 * @param String $new new password
     * @return boolean status success/fail
     */
    public function changePassword($user_id, $old, $new) {
        require_once 'PassHash.php';
		if($this->checkLogin($user_id, $old)){
			$password_hash = PassHash::hash($new);
 			
            // Generating API key
            $api_key = $this->generateApiKey();
 
			if (mysqli_connect_errno()) {
            	echo "Failed to connect to MySQL: " . mysqli_connect_error();
        	}
			// insert query
            $stmt = $this->conn->prepare("UPDATE users SET password=?, api_key=? WHERE user_id = ? ");
            $stmt->bind_param("sss", $password_hash, $api_key, $user_id);
            $result = $stmt->execute();
 
            $stmt->close();
 
            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return 1;
            } else {
                // Failed to change
                return 0;
            }
		}
		else{
			//login failed
			return False;
		}
        
    }

	/**
     * Creating new user
     * @param String $user_id User ID
     * @param String $email User login email id
     * @param String $password User login password
     * @param String $user_type User Type
     * @param string $hostel user hostel
     */
    public function updateProfile($user_id, $email, $hostel, $user_type, $first_name, $last_name, $phone, $address) {
        $response = array();
 
        // First check if user already existed in db
        if ($this->isUserExists($user_id)) {
 
			if (mysqli_connect_errno()) {
            	echo "Failed to connect to MySQL: " . mysqli_connect_error();
        	}
            
			// insert query
            $stmt = $this->conn->prepare("UPDATE users SET email=?, first_name=?, last_name=?, phone=?, address=?, hostel= ?, user_type=? WHERE user_id = ? ");
            $stmt->bind_param("ssssssss", $email, $first_name, $last_name, $phone, $address, $hostel, $user_type, $user_id);
            $result = $stmt->execute();
 
            $stmt->close();
 
            // Check for successful Update
            if ($result) {
                // User successfully updated
                return 1;
            } else {
                // Failed to update user
                return 0;
            }
        } else {
            // User_id doesn't exist;
            return 0;
        }
 
        return $response;
    }

    /**
     * Checking for duplicate email address
     * @param String $email email to check in db
     * @return boolean
     */
    private function isEmailExists($email) {
        if($stmt = $this->conn->prepare("SELECT user_id from users WHERE email = ?")){
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
        }
        else{
             return 0;
        }
    }
 
    /**
     * Checking for duplicate user 
     * @param String $user_id user_id to check in db
     * @return boolean
     */
    public function isUserExists($user_id) {
        if($stmt = $this->conn->prepare("SELECT user_id from users WHERE user_id = ?")){
        	$stmt->bind_param("s", $user_id);
        	$stmt->execute();
        	$stmt->store_result();
        	$num_rows = $stmt->num_rows;
        	$stmt->close();
			return $num_rows > 0;  
        }
        else{
			return 0;
		}
    }
 
    /**
     * Fetching user by email
     * @param String $email User email id
     */
    public function getUserByEmail($email) {
        $stmt = $this->conn->prepare("SELECT user_id FROM users WHERE email = ?");
        $stmt->bind_param("s", $email);
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }

	/**
     * Fetching user details by Id
     * @param String $user_id User id
     */

	public function getUserById($user_id) {
        $stmt = $this->conn->prepare("SELECT user_id, first_name, last_name,email, user_type, hostel, phone, address FROM users WHERE user_id = ?");
        $stmt->bind_param("s", $user_id);
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }
 
    /**
     * Fetching user api key
     * @param String $user_id user id primary key in user table
     */
    public function getApiKeyById($user_id) {
        $stmt = $this->conn->prepare("SELECT user_id, api_key FROM users WHERE user_id = ?");
        $stmt->bind_param("s", $user_id);
        if ($stmt->execute()) {
            $api_key = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $api_key;
        } else {
            return NULL;
        }
    }
 
    /**
     * Fetching user id by api key
     * @param String $api_key user api key
     */
    public function getUserId($api_key) {
        $stmt = $this->conn->prepare("SELECT user_id, api_key FROM users WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        if ($stmt->execute()) {
            $user_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user_id['user_id'];
        } else {
            return NULL;
        }
    }
 
    /**
     * Validating user api key
     * If the api key is there in db, it is a valid key
     * @param String $api_key user api key
     * @return boolean
     */
    public function isValidApiKey($api_key) {
        $stmt = $this->conn->prepare("SELECT user_id from users WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
 
    /**
     * Generating random Unique MD5 String for user Api key
     */
    private function generateApiKey() {
        return md5(uniqid(rand(), true));
    }
 
    
	
	/* ------------- `User_type` table method ------------------ */
 
    /**
     * Getting User tag
     * @param String $name 
     * @param String $user_tag
     */
	public function getUserTypeTag($user_type) {
        $stmt = $this->conn->prepare("SELECT name, user_tag FROM user_type WHERE name = ?");
        $stmt->bind_param("s", $user_type);
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user['user_tag'];
        } else {
            return NULL;
        }
    }
	
	/**
     * Getting User priority
     * @param String $name
     * @param int $user_priority
     */

	public function getUserTypePriority($user_type) {
        $stmt = $this->conn->prepare("SELECT name, user_priority FROM user_type WHERE name = ?");
        $stmt->bind_param("s", $user_type);
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user['user_priority'];
        } else {
            return NULL;
        }
    }


	/* ------------- `Complaints` table method ------------------ */
 
    /**
     * Adding Complaint
     * @param String $user_id 
     * @param String $title
     */

	public function addComplaint($user_id, $title, $body, $tag_category, $tag_hostel, $location) {
        $response = array();
 		
		if(!$this->isValidTitle($title)){
			if (mysqli_connect_errno()) {
            	echo "Failed to connect to MySQL: " . mysqli_connect_error();
        	}

			// insert query
            $stmt = $this->conn->prepare("INSERT INTO complaints(user_id, title, body, tag_category,tag_hostel,location) values(?, ?, ?, ?, ?, ?)");
            $stmt->bind_param("ssssss", $user_id, $title, $body, $tag_category, $tag_hostel,$location);
            $result = $stmt->execute();
 
            $stmt->close();
 
            // Check for successful insertion
            if ($result) {
                // complaint successfully inserted
                return 1;
            } else {
                // Failed to create complaint
                return 0;
            }
 		}
		else{ 
			//title already exist
			return 0;
		}
        return $response;
    }

	/**
     * Validating complaint title
     * If the title is there in db, it is a unvalid complaint
     * @return boolean
     */
    private function isValidTitle($title) {
        $stmt = $this->conn->prepare("SELECT complaint_id from complaints WHERE title = ?");
        $stmt->bind_param("s", $title);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
	
	    /**
     * Fetching complaint id by title
     */
    public function getIdByTitle($title) {
        $stmt = $this->conn->prepare("SELECT complaint_id, title FROM complaints WHERE title = ?");
        $stmt->bind_param("s", $title);
        if ($stmt->execute()) {
            $complaint_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $complaint_id['complaint_id'];
        } else {
            return NULL;
        }
    }

	// Checking existance of complaint
	public function isComplaintExist($complaint_id) {
        $stmt = $this->conn->prepare("SELECT complaint_id, title  FROM complaints WHERE complaint_id = ?");
        $stmt->bind_param("i", $complaint_id);
        if ($stmt->execute()) {
            $complaint = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return !($complaint == NULL);
        } else {
            return 0;
        }
    }

	public function markComplaint($complaint_id, $status) {
        $response = array();
 
 			$complaint = $this->getComplaintById($complaint_id);
			
			
			if (mysqli_connect_errno()) {
            	echo "Failed to connect to MySQL: " . mysqli_connect_error();
        	}
            
			// insert query
            $stmt = $this->conn->prepare("UPDATE complaints SET status=? WHERE complaint_id = ? ");
            $stmt->bind_param("ii", $status, $complaint_id);
            $result = $stmt->execute();
 
            $stmt->close();
 
            // Check for successful Update
            if ($result) {
                // User successfully updated
                return 1;
            } else {
                // Failed to update user
                return 0;
            }
 
        return $response;
    }

	public function updateCount($complaint_id, $count1,$count2) {
        $response = array();
 
 			$complaint = $this->getComplaintById($complaint_id);
			
			
			if (mysqli_connect_errno()) {
            	echo "Failed to connect to MySQL: " . mysqli_connect_error();
        	}
			$result1=0;
			$result2=0;
            if($complaint['upvote']!=$count1){
			// insert query
            	$stmt = $this->conn->prepare("UPDATE complaints SET upvote=? WHERE complaint_id = ? ");
            	$stmt->bind_param("ii", $count1, $complaint_id);
            	$result1 = $stmt->execute();
 				
            	$stmt->close();
 			}
			else{$result1=1;}
			if($complaint['downvote']!=$count2){
			// insert query
            	$stmt = $this->conn->prepare("UPDATE complaints SET downvote=? WHERE complaint_id = ? ");
            	$stmt->bind_param("ii", $count2, $complaint_id);
            	$result2 = $stmt->execute();
 				
            	$stmt->close();
 			}
			else{$result2=1;}
            // Check for successful Update
            if ($result1 && $result2) {
                // User successfully updated
                return 1;
            } else {
                // Failed to update user
                return 0;
            }
 
        return $response;
    }
	/**
     * Fetching complaints details by Id
     * @param int $complaint_id 
     */

	public function getComplaintById($complaint_id) {
        $stmt = $this->conn->prepare("SELECT complaint_id, title, user_id, location, upvote, downvote, status, created_at, body, tag_category, assigned_to  FROM complaints WHERE complaint_id = ?");
        $stmt->bind_param("i", $complaint_id);
        if ($stmt->execute()) {
            $complaint = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $complaint;
        } else {
            return NULL;
        }
    }

	// Get ALL My complaints as required >>>>> 
	public function getListComplaints($req, $category,$user_id,$hostel,$sort1,$status){
		
		$acceptableSortValues = array('created_at ASC', 'created_at DESC', 'upvote ASC', 'upvote DESC', 'downvote ASC','downvote DESC', 'location ASC','location DESC' );
    	$sort = $sort1;
    	if (!in_array($sort, $acceptableSortValues))
    	{
        	$sort = 'created_at DESC';
    	}
		$status1 = $status;
		$status2 = 2;
		if($status==2){
			$status1 = 1;
			$status2 = 0;		
		}
		if($req =='self'){
			$stmt = $this->conn->prepare("SELECT complaint_id, title, user_id, upvote, downvote, status, created_at FROM complaints WHERE 	(user_id = ? AND tag_category = ? AND status in (?,?)) ORDER BY $sort");
        	$stmt->bind_param("ssii", $user_id,$category,$status1,$status2); 
        	if ($stmt->execute()) {
				$complaints = array();
				$stmt->bind_result($complaint_id, $title, $user_id, $upvote, $downvote, $status, $created_at);
    			$stmt->store_result();
    			while($stmt->fetch())
    			{
        			$complaints[] = array("complaint_id" => $complaint_id, "title" => $title, "user_id" => $user_id, "upvote" => $upvote, "downvote" => $downvote,  "status" => $status, "created_at" => $created_at);
    			}
            	
            	$stmt->close();
            	return $complaints;
        	} else {
            	return NULL;
        	}	
		}
		elseif($req == 'all'){
			if($category == 'Hostel'){
				$stmt = $this->conn->prepare("SELECT complaint_id, title, user_id, upvote, downvote, status, created_at FROM complaints WHERE 	(tag_hostel = ? AND status in (?,?)) ORDER BY $sort");
			
        		$stmt->bind_param("sii",$hostel, $status1,$status2); 
        		if ($stmt->execute()) {
					$complaints = array();
					$stmt->bind_result($complaint_id, $title, $user_id, $upvote, $downvote, $status, $created_at);
    				$stmt->store_result();
    				while($stmt->fetch())
    				{
        				$complaints[] = array("complaint_id" => $complaint_id, "title" => $title, "user_id" => $user_id, "upvote" => $upvote, "downvote" => $downvote,  "status" => $status, "created_at" => $created_at);
    				}
            	
            		$stmt->close();
            		return $complaints;
        		} else {
            		return NULL;
        		}	
			}
			elseif($category == 'Institute'){
				$stmt = $this->conn->prepare("SELECT complaint_id, title, user_id, upvote, downvote, status, created_at FROM complaints WHERE 	(tag_category = ? AND status in (?,?)) ORDER BY $sort");
			
        		$stmt->bind_param("sii", $category,$status1,$status2); 
        		if ($stmt->execute()) {
					$complaints = array();
					$stmt->bind_result($complaint_id, $title, $user_id, $upvote, $downvote, $status, $created_at);
    				$stmt->store_result();
    				while($stmt->fetch())
    				{
        				$complaints[] = array("complaint_id" => $complaint_id, "title" => $title, "user_id" => $user_id, "upvote" => $upvote, "downvote" => $downvote,  "status" => $status, "created_at" => $created_at);
    				}
            	
            		$stmt->close();
            		return $complaints;
        		} else {
            		return NULL;
        		}	
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}

	}
	
	//Get all results by given keyword
	public function searchComplaints($req, $category,$user_id,$hostel,$sort1,$status,$search){
		
		$acceptableSortValues = array('created_at ASC', 'created_at DESC', 'upvote ASC', 'upvote DESC', 'downvote ASC','downvote DESC', 'location ASC','location DESC' );
    	$sort = $sort1;
    	if (!in_array($sort, $acceptableSortValues))
    	{
        	$sort = 'created_at DESC';
    	}
		$status1 = $status;
		$status2 = 2;
		if($status==2){
			$status1 = 1;
			$status2 = 0;		
		}
		if($req =='self'){
			
			$stmt = $this->conn->prepare("SELECT complaint_id, title, user_id, upvote, downvote, status, created_at FROM complaints WHERE user_id = ? AND tag_category = ? AND status IN (?,?) AND  MATCH (body) AGAINST ( ? ) ORDER BY $sort");
			
        	$stmt->bind_param("ssiis", $user_id,$category,$status1,$status2 ,$search); 
			
			$t = $stmt->execute();
			die('prepare() failed: ' . htmlspecialchars($stmt->error));
        	if ($t) {
				
				$complaints = array();
				$stmt->bind_result($complaint_id, $title, $user_id, $upvote, $downvote, $status, $created_at);
    			$stmt->store_result();
    			while($stmt->fetch())
    			{
        			$complaints[] = array("complaint_id" => $complaint_id, "title" => $title, "user_id" => $user_id, "upvote" => $upvote, "downvote" => $downvote,  "status" => $status, "created_at" => $created_at);
    			}
            	
            	$stmt->close();
            	return $complaints;
        	} else {
				echo "yes";
            	return NULL;
        	}	
		}
		elseif($req == 'all'){
			if($category == 'Hostel'){
				$stmt = $this->conn->prepare("SELECT complaint_id, title, user_id, upvote, downvote, status, created_at FROM complaints WHERE 	(tag_hostel = ? AND status in (?,?)) ORDER BY $sort");
			
        		$stmt->bind_param("sii",$hostel, $status1,$status2); 
        		if ($stmt->execute()) {
					$complaints = array();
					$stmt->bind_result($complaint_id, $title, $user_id, $upvote, $downvote, $status, $created_at);
    				$stmt->store_result();
    				while($stmt->fetch())
    				{
        				$complaints[] = array("complaint_id" => $complaint_id, "title" => $title, "user_id" => $user_id, "upvote" => $upvote, "downvote" => $downvote,  "status" => $status, "created_at" => $created_at);
    				}
            	
            		$stmt->close();
            		return $complaints;
        		} else {
            		return NULL;
        		}	
			}
			elseif($category == 'Institute'){
				$stmt = $this->conn->prepare("SELECT complaint_id, title, user_id, upvote, downvote, status, created_at FROM complaints WHERE 	(tag_category = ? AND status in (?,?)) ORDER BY $sort");
			
        		$stmt->bind_param("sii", $category,$status1,$status2); 
        		if ($stmt->execute()) {
					$complaints = array();
					$stmt->bind_result($complaint_id, $title, $user_id, $upvote, $downvote, $status, $created_at);
    				$stmt->store_result();
    				while($stmt->fetch())
    				{
        				$complaints[] = array("complaint_id" => $complaint_id, "title" => $title, "user_id" => $user_id, "upvote" => $upvote, "downvote" => $downvote,  "status" => $status, "created_at" => $created_at);
    				}
            	
            		$stmt->close();
            		return $complaints;
        		} else {
            		return NULL;
        		}	
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}

	}
	


	///      Comments Table //////////////////


	public function addComment($user_id, $complaint_id, $body) {
        $response = array();
 		
			if (mysqli_connect_errno()) {
            	echo "Failed to connect to MySQL: " . mysqli_connect_error();
        	}
			// insert query
			$complaint = $this->getComplaintById($complaint_id);
			$check = $this->addNotif($complaint_id, $complaint['user_id'], $user_id, "User ".$user_id." Commented on your complaint ".$complaint['title']." .");

            $stmt = $this->conn->prepare("INSERT INTO comments(user_id,complaint_id, body) values(?, ?, ?)");
            $stmt->bind_param("sis", $user_id, $complaint_id, $body);
            $result = $stmt->execute();
			$id = $stmt->insert_id;
            $stmt->close();
 
            // Check for successful insertion
			
            if ($result and $check) {
				
                // complaint successfully inserted
                return $id;
            } else {
                // Failed to create complaint
                return 0;
            }
        return $response;
    }

	public function getCommentById($comment_id) {
        $stmt = $this->conn->prepare("SELECT comment_id, user_id, complaint_id, created_at, body  FROM comments WHERE comment_id = ?");
        $stmt->bind_param("i", $comment_id);
        if ($stmt->execute()) {
            $comment = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $comment;
        } else {
            return NULL;
        }
    }

	public function getAllCommentsById($complaint_id) {
        $stmt = $this->conn->prepare("SELECT comment_id, user_id, created_at, body  FROM comments WHERE complaint_id = ?");
        $stmt->bind_param("i", $complaint_id);
        if ($stmt->execute()) {
			$comments = array();
			$stmt->bind_result($comment_id, $user_id, $created_at, $body);
    		$stmt->store_result();
    		while($stmt->fetch()){
				$comments[] = array("comment_id" => $comment_id, "user_id" => $user_id, "created_at" => $created_at, "body" => $body);
    		}
            $stmt->close();
            return $comments;
        } else {
            return NULL;
        }
    }

		///////////// Notifications Table method////////////////////////////////
   	public function getNotifications($user_id) {
        $stmt = $this->conn->prepare("SELECT date_created, body  FROM notifications WHERE user_id = ?");
        $stmt->bind_param("s", $user_id);
        if ($stmt->execute()) {
			$notif = array();
			$stmt->bind_result($created_at, $body);
    		$stmt->store_result();
    		while($stmt->fetch()){
				$notif[] = array("created_at" => $created_at, "body" => $body);
    		}
            $stmt->close();
            return $notif;
        } else {
            return NULL;
        }
    }

	// Add notifications to user table
	public function addNotif($complaint_id, $user_id, $user_add,$body) {
        $response = array();
 		
			if (mysqli_connect_errno()) {
            	echo "Failed to connect to MySQL: " . mysqli_connect_error();
        	}

			// insert query
            $stmt = $this->conn->prepare("INSERT INTO notifications(user_id, complaint_id, user_add, body) values(?, ?, ?, ?)");
            $stmt->bind_param("siss", $user_id, $complaint_id, $user_add, $body);
            $result = $stmt->execute();
 
            $stmt->close();
 
            // Check for successful insertion
            if ($result) {
                // successfully inserted
                return 1;
            } else {
                // Failed to create complaint
                return 0;
            }
        return $response;
    }

	

	/// ////////////////// Vote Table methods ////////////////////////////////
	// Checking vote exist or not 
	public function isVoteExist($complaint_id, $user_id) {
        $stmt = $this->conn->prepare("SELECT complaint_id, vote_id, user_id, vote  FROM votes WHERE complaint_id = ? and user_id = ?");

        $stmt->bind_param("is", $complaint_id, $user_id);
        if ($stmt->execute()) {
			echo "yes";
            $vote = $stmt->get_result()->fetch_assoc();
            $stmt->close();
			echo $vote;
            return !($vote == NULL);
        } else {
            return 0;
        }
    }

	// if vote exist then get vote id
	public function getVoteId($complaint_id, $user_id) {
        $stmt = $this->conn->prepare("SELECT vote_id, complaint_id, user_id, vote  FROM votes WHERE complaint_id = ? and user_id = ?");
        $stmt->bind_param("is", $complaint_id,$user_id);
        if ($stmt->execute()) {
            $vote = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $vote;
        } else {
            return NULL;
        }
    }

	// if vote exist then get vote
	public function getVote($vote_id) {
        $stmt = $this->conn->prepare("SELECT vote_id, complaint_id, user_id, vote  FROM votes WHERE vote_id = ?");
        $stmt->bind_param("i", $vote_id);
        if ($stmt->execute()) {
            $vote = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $vote;
        } else {
            return NULL;
        }
    }

	// Adding New Vote
	public function addVote($user_id, $complaint_id, $vote) {
        $response = array();
 		
			if (mysqli_connect_errno()) {
            	echo "Failed to connect to MySQL: " . mysqli_connect_error();
        	}
			// insert query
            $stmt = $this->conn->prepare("INSERT INTO votes(user_id,complaint_id, vote) values(?, ?, ?)");
            $stmt->bind_param("sis", $user_id, $complaint_id, $vote);
            $result = $stmt->execute();
			$id = $stmt->insert_id;
            $stmt->close();
 			
			$count1 = $this->voteCount($complaint_id, 1);
			$count2 = $this->voteCount($complaint_id, 2);
			$check = $this->updateCount($complaint_id,$count1,$count2);
            // Check for successful insertion
            if ($result && $check) {
                // complaint successfully inserted
                return $id;
            } else {
                // Failed to create complaint
                return 0;
            }
        return $response;
    }
	// Updating Vote
	public function updateVote($vote_id, $vote) {
        $response = array();
 
			if (mysqli_connect_errno()) {
            	echo "Failed to connect to MySQL: " . mysqli_connect_error();
        	}
            $complaint_id = $this->getVote($vote_id)['complaint_id'];
			// insert query
            $stmt = $this->conn->prepare("UPDATE votes SET vote=? WHERE vote_id = ? ");
            $stmt->bind_param("ii", $vote, $vote_id );
            $result = $stmt->execute();
 
            $stmt->close();
 			$count1 = $this->voteCount($complaint_id, 1);
			$count2 = $this->voteCount($complaint_id, 2);
			$check = $this->updateCount($complaint_id,$count1,$count2);
            // Check for successful Update
            if ($result && $check) {
                // User successfully updated
                return 1;
            } else {
                // Failed to update user
                return 0;
            }
 
        return $response;
    }

	//Get Vote count
	private function voteCount($complaint_id, $vote) {
        $stmt = $this->conn->prepare("SELECT user_id from votes WHERE complaint_id = ? and vote = ?");
        $stmt->bind_param("ii", $complaint_id, $vote);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows;
    }

	
}
 
?>
