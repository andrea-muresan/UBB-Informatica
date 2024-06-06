let game;

$(document).ready(function () {
    game = ["-", "-", "-", "-", "-", "-", "-", "-", "-"];

    if(Math.random() < 0.5)
        sendGame();

    $("td").on("click",putX);
})

function putX() {
    const id = this.id[2];
    if(game[id] === "-"){
        $(this).text("X");
        game[id] = "X";
        setTimeout(sendGame, 600);
    }
}

function sendGame() {
    // const request = new XMLHttpRequest();
    // request.onreadystatechange = function () {
    //     if (request.readyState === 4 && request.status === 200) {
    //         const obj = JSON.parse(request.responseText);
    //         redraw(obj);
    //     }
    // };
    // request.open("GET", "makeMove.php?game=" + game.join(''), true);
    // request.send("");

    $.get("makeMove.php?game=" + game.join(''), function(data, status){
        if(status === "success"){
            const obj = JSON.parse(data);
            redraw(obj);
        }
    });
}

function redraw(obj){
    // redraw table
    game = obj["game"].split("");
    console.log(game);

    for(let i = 0; i < game.length; i++){
        const td = $("#td" + i);
        if(td.text() !== game[i] && game[i] !== "-")
            td.text(game[i]);
    }

    // check game status
    if(obj["status"] !== "continue"){
        $("#result").text(obj["status"]);
        $("td").off("click", putX);
    }
}
