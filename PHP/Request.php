<?php

/**
 * Created by PhpStorm.
 * User: jespe
 * Date: 01-02-2017
 * Time: 09:50
 */

require_once("includes.php");

class Request
{
    public function get($key, $filter = FILTER_DEFAULT)
    {
        return $this->filterVar($_GET[$key], $filter);
    }

    public function getPost($key, $filter = FILTER_DEFAULT)
    {
        return $this->filterVar($_POST[$key], $filter);
    }

    private function filterVar($var, $filter)
    {
        return filter_var($var, $filter);
    }
}