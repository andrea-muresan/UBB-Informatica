let matrix = [];
let remainingCells = 0;

function initMatrix(){
    const noRows = document.getElementById("noRows").value;
    const noCols = document.getElementById("noCols").value;
    if(noRows < 1 || noCols < 1){
        alert("Numbers must be > 0!");
        return;
    }
    if(noRows % 2 === 1 && noCols % 2 === 1){
        alert("At least one number must be even!");
        return;
    }

    document.body.innerHTML = "<table id='table'></table><p id='congrats' style='color: green; font-size: large'></p>";

    let nums = [];
    for(let i = 0; i < (noRows * noCols) / 2; i++) {
	   nums.push(i + 1);
        nums.push(i + 1); 
    }

    matrix = [];
    nums = nums.sort(() => Math.random() - 0.5)
    for(let i = 0; i < noRows; i++){
        const row = [];
        for(let j = 0; j < noCols; j++)
            row.push(nums.pop());
        matrix.push(row);
    }
    console.log(matrix);

    const table = document.getElementById("table");
    for(let i = 0; i < noRows; i++){
        const tr = document.createElement("tr");
        let txtHTML = "";
        for(let j = 0; j < noCols; j++){
            txtHTML += "<td onclick='show(this)' class='covered'>" + matrix[i][j] + "</td>";

        }
        tr.innerHTML = txtHTML;
        table.appendChild(tr);
    }
    remainingCells = noRows * noCols;

    
}

let firstUncovered = false, secondUncovered = false;

function show(td){
    if(td.className === "covered"){
        if(!firstUncovered){
            td.className = "uncovered";
            firstUncovered = td;
        }
        else if(!secondUncovered){
            td.className = "uncovered";
            secondUncovered = td;
            if(firstUncovered.textContent !== secondUncovered.textContent) {
                setTimeout(() => {
                    firstUncovered.className = "covered";
                    secondUncovered.className = "covered";
                    firstUncovered = secondUncovered = false;
                }, 1000);
            }
            else{
                firstUncovered = secondUncovered = false;
                remainingCells -= 2;
            }
        }
    }

    if(remainingCells === 0){
        document.getElementById("congrats").innerText = "You won!";
        setTimeout(() =>{
            document.body.innerHTML = "<div style=\"width: 200px;\">\n" +
                "        <label for=\"noRows\">No rows: <input id=\"noRows\" type=\"number\"></label>\n" +
                "        <br><br>\n" +
                "        <label for=\"noCols\">No cols: <input id=\"noCols\" type=\"number\"></label>\n" +
                "        <br><br>\n" +
                "        <input type=\"button\" value=\"Start game!\" onclick=\"initMatrix()\">\n" +
                "    </div>";
        }, 2000);
    }
}
