<?php

function checkIfSolution(): string
{
    global $game;
    $status = "continue";

    if($game[0] == $game[1] && $game[0] == $game[2] && ($game[0] == "X" || $game[0] == "0")) // first row
        $status = $game[0] . " won!";

    else if($game[3] == $game[4] && $game[3] == $game[5] && ($game[3] == "X" || $game[3] == "0")) // second row
        $status = $game[3] . " won!";
    else if($game[6] == $game[7] && $game[6] == $game[8] && ($game[6] == "X" || $game[6] == "0")) // third row
        $status = $game[6] . " won!";

    else if($game[0] == $game[3] && $game[0] == $game[6] && ($game[0] == "X" || $game[0] == "0")) // first column
        $status = $game[0] . " won!";

    else if($game[1] == $game[4] && $game[1] == $game[7] && ($game[1] == "X" || $game[1] == "0")) // second column
        $status = $game[1] . " won!";

    else if($game[2] == $game[5] && $game[2] == $game[8] && ($game[2] == "X" || $game[2] == "0")) // third column
        $status = $game[2] . " won!";

    else if($game[0] == $game[4] && $game[0] == $game[8] && ($game[0] == "X" || $game[0] == "0")) // principal diagonal
        $status = $game[0] . " won!";

    else if($game[2] == $game[4] && $game[2] == $game[6] && ($game[2] == "X" || $game[2] == "0")) // secondary diagonal
        $status = $game[2] . " won!";

    else if(!str_contains($game, "-")) // there is no possible move
        $status = "Tie";

    return $status;
}



$game = $_GET["game"];
$status = checkIfSolution();

if($status == "continue") {
    while(true){
        $i = rand(0, 8);
        if($game[$i] === "-"){
            $game[$i] = "0";
            break;
        }
    }

    $status = checkIfSolution();
}

$response = array("status" => $status, "game" => $game);
echo json_encode($response);
