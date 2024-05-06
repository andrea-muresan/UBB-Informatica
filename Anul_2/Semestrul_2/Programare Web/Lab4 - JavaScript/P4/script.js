let arr = [];

let sorted = false;
let col = 0;

window.onload = function() {
    arr.push({nume: "Popescu", prenume: "Ion", varsta: 27, salariu: 6320.20});
    arr.push({nume: "Tiple", prenume: "Andrei", varsta: 45, salariu: 2700.70});
    arr.push({nume: "Osan", prenume: "Maria", varsta: 20, salariu: 15203.60});
    arr.push({nume: "Muresan", prenume: "Andrea", varsta: 20, salariu: 6530.10});
    if(document.title === "Pb4 - horizontally")
        wrapperHorizontally(0);
    else
        wrapperVertical(0);
};

function wrapperHorizontally(colIndex){
    sortByColumn(colIndex)
    displayContentHorizontally()
}

function wrapperVertical(colIndex){
    sortByColumn(colIndex)
    displayContentVertical()
}

function sortByColumn(colIndex){
    const keys = Object.keys(arr[0]);
    arr = arr.sort((a, b) => {
        const x = a[keys[colIndex]];
        const y = b[keys[colIndex]];
        if(typeof x == "string")
            return x.localeCompare(y);
        else if(typeof x == "number")
            return x - y;
        else
            return 0;
    });

    if (colIndex === col) {
        if (sorted) {  // inainte era sortat crescator dupa aceeasi coloana
            arr = arr.reverse();
            sorted = false;
        }
        else{ // inainte era sortat crescator dupa aceeasi coloana
            sorted = true;
        }
    }
    else{ // era sortat dupa alta coloana
        col = colIndex;
        sorted = true;
    }
    console.log(arr);
    console.log("sortedAsc = " + sorted);
    console.log("byCol = " + col);
}

function displayContentHorizontally(){
    const keys = Object.keys(arr[0]);
    let txtHTML = "";
    for(let i = 0; i < keys.length; i++){
        txtHTML += "<tr id='row" + i + "'></tr>";
    }
    document.getElementById("table").innerHTML = txtHTML;

    for(let i = 0; i < keys.length; i++){
        const id = "row" + i;
        const key = keys[i];
        const tr = document.getElementById(id);
        txtHTML = "<th onclick=\"wrapperHorizontally(" + i + ");\">" + key.charAt(0).toUpperCase() + key.slice(1) + "</th>";
        for(let j = 0; j < arr.length; j++){
            txtHTML += "<td>" + arr[j][key] + "</td>";
        }
        tr.innerHTML = txtHTML;
    }
}

function displayContentVertical(){
    const keys = Object.keys(arr[0]);
    let txtHTML = "";

    for(let i=0; i <= arr.length; i++){
        txtHTML += "<tr id='row" + i + "'></tr>";
    }
    document.getElementById("table").innerHTML = txtHTML;

    txtHTML = "";
    for(let i = 0; i < keys.length; i++){
        txtHTML += "<th onclick=\"wrapperVertical(" + i + ");\">" + keys[i].charAt(0).toUpperCase() + keys[i].slice(1) + "</th>";
    }
    document.getElementById("row0").innerHTML = txtHTML;

    for(let i = 0; i < arr.length; i++){
        txtHTML = "";
        for(let j = 0; j < keys.length; j++){
            txtHTML += "<td>" + arr[i][keys[j]]+ "</td>";
        }
        document.getElementById("row" + (i + 1)).innerHTML = txtHTML;
    }
}