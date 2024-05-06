function validate(){
    const nume = document.getElementById("nume");
    const data = document.getElementById("data");
    const varsta = document.getElementById("varsta");
    const email = document.getElementById("email");

    let error = "";
    if(nume.value.length < 3) {
        error += "Numele trebuie sa fie mai lung de 3 caractere!\n";
        nume.setAttribute("style", "border: 2px solid red");
    }
    else
        nume.setAttribute("style", "border: default");

    if(!data.value.match(/^[0-3]?[0-9]\/[01]?[0-9]\/[0-9]{4}$/) &&  // dd/mm/yyyy
        !data.value.match(/^[0-3]?[0-9]-[01]?[0-9]-[0-9]{4}$/) &&  //  dd-mm-yyyy
        !data.value.match(/^[0-3]?[0-9]\.[01]?[0-9]\.[0-9]{4}$/)){ //  dd.mm.yyyy
        error += "Formatul datei este incorect! (dd/mm/yyyy, dd-mm-yyyy sau dd.mm.yyyy)\n";
        data.setAttribute("style", "border: 2px solid red");
    }
    else
        data.setAttribute("style", "border: default");

    if(isNaN(varsta.value)){
        varsta.setAttribute("style", "border: 2px solid red");
        error += "Varsta trebuie sa fie un numar!\n";
    }
    else if(varsta.value < 1 || varsta.value > 100){
        varsta.setAttribute("style", "border: 2px solid red");
        error += "Varsta trebuie sa fie in intervalul [1, 100]!\n";
    }
    else
        varsta.setAttribute("style", "border: default");

    if(!email.value.match(/^.{3,}@.{3,}\..{2,}$/)){
        error += "Email incorect! (abc@def.gh)\n";
        email.setAttribute("style", "border: 2px solid red");
    }
    else
        email.setAttribute("style", "border: default");

    const p = document.getElementById("err");
    if(error !== ""){
        p.setAttribute("style", "color: red");
        p.innerText = error;
    }
    else{
        p.setAttribute("style", "color: green");
        p.innerText = "Totul este corect!";
    }

}