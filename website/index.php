<?php
/**
 * Created by PhpStorm.
 * User: kbecker
 * Date: 7/14/2018
 * Time: 4:08 PM
 */


$wifi = $_GET['wifi'];



function query($query)
{
    $connect = mysqli_connect('localhost','id2631306_keithloganbecker','c4hahaven%jkb72rAs','id2631306_wifi');
    $result = mysqli_query($connect, $query);
    $rows = [];
    while ($row = mysqli_fetch_assoc($result)) {
        array_push($rows, $row);
    }
    return $rows;
}

/* NOTE:
* make sure tables exist
*/

query('CREATE TABLE IF NOT EXISTS wifi
(
    wifi_id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    connect_time TIMESTAMP NOT NULL DEFAULT now(),
    wifi_name VARCHAR(140) NOT NULL
);');

query('CREATE TABLE IF NOT EXISTS system
(
    system_id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    connect_time TIMESTAMP NOT NULL DEFAULT now(),
    system_key VARCHAR(140) NOT NULL,
    system_value VARCHAR(140) NOT NULL
);');

/*
* main functions 
*/

function get_wifi_count()
{
    return (int)reset(query("SELECT count(*) AS 'amount' FROM wifi;"))['amount'];
}

function clean_wifi()
{
	$lastWifiTime = get_last_wifi()['connect_time'];
    query("DELETE FROM wifi WHERE connect_time < DATE_SUB(\"$lastWifiTime\", INTERVAL 1 MINUTE);");
}

function update_last_location($location)
{
	query("INSERT INTO system (system_key, system_value) VALUES ('last_location', '$location');");
}

function get_last_wifi()
{
	return reset(query("SELECT * FROM wifi ORDER BY connect_time DESC LIMIT 1;"));
}

function was_recently_connected()
{
	if (get_wifi_count() === 0) {
		return false;
	} else {
		if (empty(query("SELECT * FROM wifi WHERE connect_time > DATE_SUB(now(), INTERVAL 1 MINUTE);"))) {
			return false;
		} else {
			return true;
		}
	}
}

function is_last_wifi($wifi)
{
	$lastWifi = reset(query("SELECT * FROM wifi ORDER BY connect_time DESC LIMIT 1"))['wifi_name'];
	return !empty($lastWifi) && $lastWifi === $wifi;
}

function set_at_location($wifi)
{
	switch ($wifi) {
		case 'Beck_Net':
			update_last_location('home');
		break;
		case 'newhome':
			update_last_location('work');
		break;
		default:
			update_last_location('unknown');
	}
}

function set_arriving_location($wifi)
{
	switch ($wifi) {
		case 'Beck_Net':
			update_last_location('arrived_home');
		break;
		case 'newhome':
			update_last_location('arrived_work');
		break;
		default:
			update_last_location('arrived_unknown');
	}
}

/* NOTE:
*
* here is the main logic for deciding if recently arrived at location
*
*/
if (was_recently_connected() && is_last_wifi($wifi)) {
		set_at_location($wifi);
} else if (!was_recently_connected()) {
	set_arriving_location($wifi);
}


query("INSERT INTO wifi (wifi_name) VALUES ('$wifi');");

clean_wifi();

echo "success";
exit();

