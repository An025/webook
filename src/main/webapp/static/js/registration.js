window.onload = function(){
    init();
}

function init(){
    getRegistrationInfo();
}


function getRegistrationInfo(){
    let password1 = document.getElementById('password1');
    let password2 = document.getElementById('password2');
    password1.addEventListener("input", (e) =>validPassword(password1))
    password2.addEventListener("input", (e) =>validAndMatchPassword(password1, password2))
}

function validPassword(data){
    if(data.value.length< 8){
        data.style.borderColor = "red";
    }else{
        data.style.borderColor = "green";
    }
}

function validAndMatchPassword(password1, password2){
    let submitButton = document.getElementById("signin");
    if(password1.value == password2.value && password2.value.length >=8){
        password2.style.borderColor = "green";
        submitButton.removeAttribute("disabled");
        submitButton.classList.remove("btn-secondary");
        submitButton.classList.add("btn-info");
    }else{
        password2.style.borderColor = "red";
    }
}
