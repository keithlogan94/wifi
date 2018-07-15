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

if (get_wifi_count() > 1) {
	if (get_last_wifi()['wifi_name'] !== 'Beck_Net' && $wifi === 'Beck_Net') update_last_location('arrived_home');
	else if (get_last_wifi()['wifi_name'] === 'Beck_Net' && $wifi === 'Beck_Net') update_last_location('home');
	else if (get_last_wifi()['wifi_name'] === 'Beck_Net' && $wifi !== 'Beck_Net') update_last_location('leaving_home');
	else if (get_last_wifi()['wifi_name'] !== 'newhome' && $wifi === 'newhome') update_last_location('arrived_work');
	else if (get_last_wifi()['wifi_name'] === 'newhome' && $wifi === 'newhome') update_last_location('work');
	else if (get_last_wifi()['wifi_name'] === 'newhome' && $wifi !== 'newhome') update_last_location('leaving_work');
	else update_last_location('unknown');
}

query("INSERT INTO wifi (wifi_name) VALUES ('$wifi');");



clean_wifi();

echo "success";
exit();

