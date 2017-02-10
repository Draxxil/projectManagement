<?php

/**
 * Created by PhpStorm.
 * User: jespe
 * Date: 01-02-2017
 * Time: 09:49
 */

require_once("includes.php");

class Controller
{
    public $request;

    function __construct()
    {
        $this->request = new Request();
    }
}