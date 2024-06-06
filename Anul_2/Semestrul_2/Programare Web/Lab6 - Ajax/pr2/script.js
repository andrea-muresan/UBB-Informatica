let pageNumber = 0;

$(document).ready(function (){
    getPage(0);
});

function previous() {
    pageNumber--;
    getPage()
}

function next() {
    pageNumber++;
    getPage();
}

function getPage(){
    // const request = new XMLHttpRequest();
    // request.onreadystatechange = function () {
    //     if(request.readyState === 4 && request.status === 200) {
    //         const obj = JSON.parse(request.responseText);
    //         console.log(obj["users"]);
    //         constructTable(obj);
    //     }
    // };
    // request.open("GET", "getPage.php?page=" + pageNumber, true);
    // request.send('');

    $.get("getPage.php?page=" + pageNumber, function(data, status){
        if(status === "success"){
            const obj = JSON.parse(data);
            console.log(obj["users"]);
            constructTable(obj);
        }
    });
}

function constructTable(obj){
    if(obj["start"] === 0)
        $("#previous").attr("disabled", true);
    else
        $("#previous").attr("disabled", false);

    if(obj["end"] === 0)
        $("#next").attr("disabled", true);
    else
        $("#next").attr("disabled", false);

    const table = $("#table");
    table.empty().append(
        $("<tr></tr>")
            .append($("<th></th>").text("Firstname"))
            .append($("<th></th>").text("Lastname"))
            .append($("<th></th>").text("PhoneNumber"))
            .append($("<th></th>").text("Email"))
    );
    obj["users"].forEach(x => {
        table.append(
            $("<tr></tr>")
                .append($("<td></td>").text(x["firstname"]))
                .append($("<td></td>").text(x["lastname"]))
                .append($("<td></td>").text(x["phoneNumber"]))
                .append($("<td></td>").text(x["email"]))
        )
    });
}
