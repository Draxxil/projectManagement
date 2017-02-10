<?php

/**
 * Created by PhpStorm.
 * User: jespe
 * Date: 01-02-2017
 * Time: 09:49
 */

//ini_set("display_errors", 1);

require_once("includes.php");

class ServerManager extends Controller
{
    private $ldap;

    const property_names = [
        "name",
        "status"
    ];

    function __construct()
    {
        parent::__construct();
    }

    public function login()
    {
        $this->ldap = ldap_connect(LDAP_SERVER_IP);

        ldap_set_option($this->ldap, LDAP_OPT_PROTOCOL_VERSION, 3);
        ldap_set_option($this->ldap, LDAP_OPT_REFERRALS, 0);

        $username = $this->request->get("username");
        $password = $this->request->get("password");

        if ($username == "" || $password == "") {
            echo "error";
            die;
        }

        if (ldap_bind($this->ldap, $username, $password)) {
            echo "success";
            die;
        }

        echo "error";
    }

    public function getServerInfo() {
        $username = $this->request->get("username");
        $password = $this->request->get("password");

        $raw_server_info = $this->runServerInfoPs($username, $password);
        $xml_server_info = simplexml_load_string($raw_server_info);

        $xml_return = new SimpleXMLElement("<list/>");

        foreach ($xml_server_info->Object as $server_info) {
            $properties = $server_info->Property;

            $property_index = 0;
            $server_info_holder = $xml_return->addChild("ServerInfo");
            foreach ($properties as $property) {
                $server_info_holder->addChild(self::property_names[$property_index], $property);
                $property_index++;
            }
        }

        header("Content-Type: text/xml;charset=UTF-8");

        echo str_replace("<?xml version=\"1.0\"?>", "", $xml_return->asXML());
    }

    private function runServerInfoPs($username, $password) {
        return shell_exec('powershell.exe C:\inetpub\wwwroot\getServerInfo.ps1 -user ' . $username . ' -pass ' . $password);
    }
}