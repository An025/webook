window.onload = function(){
    init();
}

function init(){
    submitHandle();
}


function submitHandle(){
    let submitButton = document.getElementById('sign-in');
    submitButton.addEventListener('click', ()=>{
        validation()? window.location = "/newuser": alert("something went wrong");
    })
}
function validation(){
    let validity = true;


    return validity
}

