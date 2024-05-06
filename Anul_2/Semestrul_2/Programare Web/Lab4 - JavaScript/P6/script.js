let matrix = [];
let solution = [];

let free = {i: 0, j: 0};
let n;

function initGame(){
    n = document.getElementById("nr").value;
    if(n < 2){
        alert("Nr of rows/cols must be greater than 2!");
        return;
    } 

    if(n > 10){
        alert("Nr of rows/cols must be less than 10!");
        return;
    } 
	

    // generare nr de la 1 la n^2
    const nums = [];
    for(let i = 1; i < n * n; i++)
        nums.push(i);
    nums.push(-1);

    // construirea solutiei
    solution = [];
    for(let i = 0; i < n; i++){
        const line = [];
        for(let j = 0; j < n; j++)
            line.push(nums[i * n + j]);
        solution.push(line);
    }
    console.log("Solution: ", solution);

    // construire joc random
    nums.sort(() => Math.random() - 0.5);
    console.log("Nums after shuffle: ", nums);
    matrix = [];
    for (let i = 0; i < n; i++) {
        const line = [];
        for (let j = 0; j < n; j++)
            line.push(nums.pop());
        matrix.push(line);
    }
    if(checkIfSolution()){
        const temp = matrix[0];
        matrix[0] = matrix[n - 1];
        matrix[n - 1] = temp;
    }
    console.log("Matrix: ", matrix);

    // creare tabel cu nr
    document.body.innerHTML = "<table id='table'></table><p id='msg'></p>";
    for(let i = 0; i < n; i++){
        const tr = document.createElement("tr");
        for(let j = 0; j < n; j++){
            if(matrix[i][j] !== -1)
                tr.innerHTML += "<td id='td" + i + j + "'>" + matrix[i][j] + "</td>";
            else {
                free.i = i;
                free.j = j;
                tr.innerHTML += "<td id='td" + i + j + "'></td>";
            }
        }
        document.getElementById("table").appendChild(tr);
    }

    alert("Starting game...");

    // evenimente cu sageti
    document.body.addEventListener("keydown", pressed);
}

// interschimba celula libera cu alta
function switchCells(temp){
    const tdFree = document.getElementById("td" + free.i + free.j);
    const tdTemp = document.getElementById("td" + temp.i + temp.j);
    tdFree.innerText = tdTemp.innerText;
    tdTemp.innerText = "";
    matrix[free.i][free.j] = matrix[temp.i][temp.j];
    matrix[temp.i][temp.j] = -1;
    free.i = temp.i;
    free.j = temp.j;
}

function pressed(event){
    const key = event.key;
    switch(key){
        case "ArrowLeft":
            if(free.j < n - 1){
                const temp = {i: free.i, j: free.j + 1};
                switchCells(temp);
            }
            break;
        case "ArrowRight":
            if(free.j > 0){
                const temp = {i: free.i, j: free.j - 1};
                switchCells(temp);
            }
            break;
        case "ArrowUp":
            if(free.i < n - 1){
                const temp = {i: free.i + 1, j: free.j};
                switchCells(temp);
            }
            break;
        case "ArrowDown":
            if(free.i > 0){
                const temp = {i: free.i - 1, j: free.j};
                switchCells(temp);
            }
            break;
    }

    if(checkIfSolution()){
        document.getElementById("msg").innerText = "You won!";
        document.body.removeEventListener("keydown", pressed);
        setTimeout(() =>
            document.body.innerHTML = "<input type=\"number\" placeholder=\"no of rows/cols\" id=\"nr\">\n" +
                "    <br><br>\n" +
                "    <input id= \"button\" type=\"button\" value=\"Start game!\" onclick=\"initGame();\">",
            2000);
    }
}

function checkIfSolution(){
    for(let i = 0; i < n; i++)
        for(let j = 0; j < n; j++)
            if(matrix[i][j] !== solution[i][j])
                return false;
    return true;
}