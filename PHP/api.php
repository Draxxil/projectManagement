<?php

require_once("includes.php");

$LdapManager = new ServerManager();

$action = $LdapManager->request->get("action");

switch ($action) {
    case "login":
        $LdapManager->login();
        break;

    case "getServerInfo":
        $LdapManager->getServerInfo();
        break;
}