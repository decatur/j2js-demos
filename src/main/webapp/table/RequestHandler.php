<?php

/**
 * This is a copy of /PowerDashServer/src/php/RequestHandler.php.
 * DO NOT EDIT!
 */  

class RequestHandler {
    
    /*
     * The mime type of the response. Allowed values are text/html (FormHttpRequest),
     * text/javascript (ScriptHttpRequest), and andtext/plain (XMLHttpRequest).
     */
    private $mimeType = null;

    function preProcess() {
        global $logger;
        
        if (!array_key_exists('mimeType', $_GET)) {
            $this->mimeType = 'text/plain';
        } else {
            $this->mimeType = $_GET['mimeType'];
        }
        
        if ($logger != null) $logger->log(J2JS_INFO, 'mimeType: '. $this->mimeType);
        
        if ($this->mimeType == 'image/png') {
            $data = null;
        } else if ($this->mimeType == 'text/plain') {
            if (array_key_exists('HTTP_RAW_POST_DATA', $GLOBALS)) {
                $data = $GLOBALS['HTTP_RAW_POST_DATA'];
            } else {
                $data = null;
            }
        } else {
            if ($this->mimeType == 'text/html') {
                $data = $_POST['data'];
            } else if ($this->mimeType == 'text/javascript') {
                $data = $_GET['data'];
            } else {
                throw new Exception("Illegal mime type: $this->mimeType!");
            }
            
            if (get_magic_quotes_gpc() == 1) $data = stripslashes($data);
        }
        
        if ($logger != null && $data != null) $logger->log(J2JS_INFO, 'Request: '. $data);
        
        if ($data != 'null' && strlen($data) > 0) {
            // json_decode cannot handle null and empty strings.
            $obj = json_decode($data);
            if ($logger != null) $logger->log(J2JS_INFO, var_export($obj, true));
            if ($obj == NULL) throw new Exception("Could not decode message!");
        } else {
            $obj = null;
        }
        
        return $obj;
    }
    
    function postProcess($responseData) {
        global $logger;
        
        if ($this->mimeType == 'image/png') {
            exit(0);
        }
        
        //if (!is_string($responseData)) {
            $responseData = json_encode($responseData);
        //}

        if ($logger != null) $logger->log(J2JS_INFO, 'Response: '. $responseData);
            
        if ($this->mimeType == 'text/plain') {
            echo $responseData;
        } else {
            if ($this->mimeType == 'text/html') {
                echo '<html><head><script>';
            }
            
            echo "top.j2js.onScriptLoad($_GET[requestId], $responseData);";
            
            if ($this->mimeType == 'text/html') {
                echo '</script></head><body></body></html>';
            }
        }

        exit(0);
    }
}

?>