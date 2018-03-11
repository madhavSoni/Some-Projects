<?php
$minimumDonation = 99;

// error_reporting( E_ALL );
// ini_set( 'display_errors', 1 );

$filename = 'ipn.log';
$filelog  = true;
if( !$handle = fopen($filename, 'a') ) $filelog = false;


// STEP 1: Read POST data
 
// reading posted data from directly from $_POST causes serialization 
// issues with array data in POST
// reading raw POST data from input stream instead. 
$raw_post_data  = file_get_contents('php://input');
$raw_post_array = explode('&', $raw_post_data);
$myPost         = array();
foreach ($raw_post_array as $keyval) {
  $keyval = explode ('=', $keyval);
  if (count($keyval) == 2)
     $myPost[$keyval[0]] = urldecode($keyval[1]);
}
// read the post from PayPal system and add 'cmd'
$req = 'cmd=_notify-validate';
if(function_exists('get_magic_quotes_gpc')) {
   $get_magic_quotes_exists = true;
} 
foreach ($myPost as $key => $value) {        
   if($get_magic_quotes_exists == true && get_magic_quotes_gpc() == 1) { 
        $value = urlencode(stripslashes($value)); 
   } else {
        $value = urlencode($value);
   }
   $req .= "&$key=$value";
}


if( $filelog ) fwrite( $handle, date('m/d/Y H:i:s') . ' - ' . $req . "\n" );

// STEP 2: Post IPN data back to paypal to validate
// Currently skipped because CURL is not installed.
/*
$ch = curl_init('https://www.paypal.com/cgi-bin/webscr');
curl_setopt($ch, CURLOPT_HTTP_VERSION, CURL_HTTP_VERSION_1_1);
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_RETURNTRANSFER,1);
curl_setopt($ch, CURLOPT_POSTFIELDS, $req);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 1);
curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 2);
curl_setopt($ch, CURLOPT_FORBID_REUSE, 1);
curl_setopt($ch, CURLOPT_HTTPHEADER, array('Connection: Close'));

if( !($res = curl_exec($ch)) ) {
    // error_log("Got " . curl_error($ch) . " when processing IPN data");
    if( $filelog )
	{
		fwrite( $handle, date('m/d/Y H:i:s') . " - ERROR - Got " . curl_error($ch) . " when processing IPN data" . "\n" );
		fclose($handle);
	}
    curl_close($ch);
    exit;
}
curl_close($ch);
if( $filelog ) fwrite( $handle, date('m/d/Y H:i:s') . ' - ' . $res . "\n" );
*/
$res = 'VERIFIED';

if( $filelog ) fclose($handle);

// STEP 3: Inspect IPN validation result and act accordingly
 
if( strcmp( $res, "VERIFIED" ) == 0 )
{
    // check whether the payment_status is Completed
    // check that txn_id has not been previously processed
    // check that receiver_email is your Primary PayPal email
    // check that payment_amount/payment_currency are correct
    // process payment
 
    // assign posted variables to local variables
    $item_name = $_POST['item_name'];
    $item_number = $_POST['item_number'];
    $payment_status = $_POST['payment_status'];
    $payment_amount = $_POST['mc_gross'];
    $payment_currency = $_POST['mc_currency'] == 'USD' ? '$' : $_POST['mc_currency'];
    $payment_date = $_POST['payment_date'];
    $txn_id = $_POST['txn_id'];
    $receiver_email = $_POST['receiver_email'];
    $payer_email = $_POST['payer_email'];
    $first_name = $_POST['first_name'];
    $last_name = $_POST['last_name'];
	
	$email = "Data received via IPN: \n\n";
	$email .= "item_name: " . $item_name . "\n";
	$email .= "item_number: " . $item_number . "\n";
	$email .= "payment_status: " . $payment_status . "\n";
	$email .= "payment_amount: " . var_export( $payment_amount, true ) . "\n";
	$email .= "txn_id: " . $txn_id . "\n";
	$email .= "receiver_email: " . $receiver_email . "\n";
	$email .= "payer_email: " . $payer_email;

	$body  = "Dear $first_name, \n\n";
	$body .= "Thank you for your donation of $payment_currency $payment_amount! If your company has a matching donation program, we would appreciate if you would request a match for your donation from your employer. \n\n";
	$body .= "Your donation will help promote the unambiguous communication of uncertainty through education, best practices, and open standards. \n\n";
	$body .= "Thanks, \n\n";
	$body .= "Sam Savage \n";
	$body .= "Executive Director \n\n";
	$body .= "----------------------------------------------------------- \n";
	$body .= "RECEIPT \n\n";
	$body .= "Thank you for your donation to Probability Management, Inc. \n";
	$body .= "Donor Name: $first_name $last_name \n";
	$body .= "Donation Amount: $payment_currency $payment_amount \n";
	$body .= "Date: $payment_date \n\n";
	$body .= "Save or print this receipt for your tax records. \n";
	$body .= "------------------------------------------------------------ \n\n";
	$body .= "No goods and services were provided for this contribution. Probability Management, Inc. is a non-profit organization with 501(c)(3) tax-exempt status in the United States. Donations to Probability Management, Inc. are tax-deductible to the extent allowed by law.  \n\n";
	$body .= "Probability Management, Inc. \n";
	$body .= "PO Box 61045 \n";
	$body .= "Palo Alto CA 94306 \n";
	$body .= "http://probabilitymanagement.org \n";
	$body .= "Tax ID: 46-0572110 \n";
	mail( $payer_email, 'Your Donation Receipt for ProbabilityManagement.org', $body, 'From: pminfo@probabilitymanagement.org' );
	
	if( $payer_email && $payment_amount >= $minimumDonation )
	{
		$letters = array( 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p' );
		$letterCount = count( $letters );
		$numbers = array( '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' );
		$numberCount = count( $numbers );
		
		$password = '';
		
		for( $i = 0; $i < 8; $i++ )
		{
			if( $i%2 == 0 )
				$password .= $letters[mt_rand(0,$letterCount-1)];
			else
				$password .= $numbers[mt_rand(0,$numberCount-1)];
		}
		
		// Write user/pass to .htpasswd
		$handle2      = fopen( 'secure/.htpasswd', 'a+' );
		$fileContents = explode( "\n", fread( $handle2, filesize( 'secure/.htpasswd' ) ) );
		$emailAppend  = '';
		/*
		// Add the user with a new username if they exist already.
		foreach( $fileContents as $fileLineData )
		{
			$fileLineData = explode( ':', $fileLineData );
			if( ( $payer_email . $emailAppend ) == $fileLineData[0] )
			{
				// We can't have more than 1 of the same user
				$emailAppend = ( $emailAppend == '' ? 1 : $emailAppend+1 );
			}
		}
		*/
		
		$emailExists  = false;
		foreach( $fileContents as $fileLineData )
		{
			$fileLineData = explode( ':', $fileLineData );
			if( $payer_email == $fileLineData[0] )
			{
				// We can't have more than 1 of the same user
				$emailExists = true;
				break;
			}
		}
		
		if( !$emailExists )
		{
			fwrite( $handle2, $payer_email . $emailAppend . ':' . crypt( $password, substr( $password, 0, 2 ) ) . "\n" );
			fclose( $handle2 );
		
			$body = "Thank you for your support! We have included your login credentials below: \n\n";
			$body .= "Members Portal: http://probabilitymanagement.org/secure/members.htm \n\n";
			$body .= "Username: " . $payer_email . $emailAppend . "\n";
			$body .= "Password: " . $password . "\n\n";
			$body .= "Please keep your login credentials somewhere safe. Thank you.";
		
			mail( $payer_email, 'Your Login for ProbabilityManagement.org', $body, 'From: pminfo@probabilitymanagement.org' );
		}else{
			fclose( $handle2 );
			
			$body = "Thank you for your continued support! You may continue to login using your existing credentials. \n\n";
			$body .= "Members Portal: http://probabilitymanagement.org/secure/members.htm \n\n";
			$body .= "If you need new login credentials, please contact us at pminfo@probabilitymanagement.org. Thank you.";
		
			mail( $payer_email, 'Thank You from ProbabilityManagement.org', $body, 'From: pminfo@probabilitymanagement.org' );
		}
	}
	
} else if (strcmp ($res, "INVALID") == 0) {
    // log for manual investigation
}