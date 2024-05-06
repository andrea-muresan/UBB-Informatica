function move(listId){
    var source, destination;
    if(listId === "leftList"){
        source = document.getElementById("leftList");
        destination = document.getElementById("rightList");
    }
    else{
        source = document.getElementById("rightList");
        destination = document.getElementById("leftList");
    }
    var option = source.options[source.selectedIndex];
    source.remove(source.selectedIndex);
    destination.add(option);
}