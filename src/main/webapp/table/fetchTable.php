<?php

/*
 * PHP servlet which delivers a a simple table (a list of lists).
 */

error_reporting(E_ALL);

require_once("RequestHandler.php");

$requestHandler = new RequestHandler();

$requestData = $requestHandler->preProcess();

$responseData = array(
	array("Marc Zinn", "Barnett Ave 12"),
	array("Ed Ladue",  "Argonne St 8"),
	array("Bar",       "Foo 18"));

$requestHandler->postProcess($responseData);

?>
