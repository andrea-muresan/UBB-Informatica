$(document).ready(getDepartureStations);

function getDepartureStations(){
    // const request = new XMLHttpRequest();
    // request.onreadystatechange = function() {
    //     if (request.readyState === 4 && request.status === 200) {
    //         const response = JSON.parse(request.responseText);
    //         console.log(response);
    //
    //         response.forEach((x) => {
    //             $("#departureList").append(
    //                 $("<option></option>")
    //                     .attr("value", x)
    //                     .attr("name", "departure")
    //                     .text(x)
    //             ).attr("selectedIndex", 0);
    //         })
    //     }
    // };
    // request.open("GET", "getDepartureStations.php", true);
    // request.send('');


    $.get("getDepartureStations.php", function(data, status){
        if(status === "success"){
            const response = JSON.parse(data);
            console.log(response);

            response.forEach((x) => {
                $("#departureList").append(
                    $("<option></option>")
                        .attr("value", x)
                        .attr("name", "departure")
                        .text(x)
                );
            })
        }
    });
}

function getArrivalStations(){
    // const request = new XMLHttpRequest();
    // request.onreadystatechange = function() {
    //     if (request.readyState === 4 && request.status === 200) {
    //         const response = JSON.parse(request.responseText);
    //         console.log(response);
    //
    //         $("#arrivalList").empty();
    //         response.forEach((x) => {
    //             $("#arrivalList").append(
    //                 $("<option></option>")
    //                     .attr("value", x)
    //                     .attr("name", "arrival")
    //                     .text(x)
    //             );
    //         })
    //     }
    // };
    // request.open("GET", "getArrivalStations.php?departure=" +
    //     $("#departureList").val(), true);
    // request.send('');


    $.get("getArrivalStations.php?departure=" + $("#departureList").val(),
        function(data, status){
        if(status === "success"){
            const response = JSON.parse(data);
            console.log(response);

            $("#arrivalList").empty();
            response.forEach((x) => {
                $("#arrivalList").append(
                    $("<option></option>")
                        .attr("value", x)
                        .attr("name", "arrival")
                        .text(x)
                );
            })
        }
    });
}