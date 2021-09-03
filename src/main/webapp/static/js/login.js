window.onload = function(){
    init();
}

function init(){
    login();
}

function login(){

}

function login(){
    let email = document.getElementById("email");
    let pasword = document.getElementById("password")
    let loginButton = document.getElementById("login");
    pasword.addEventListener("input", (e) =>{
        if(pasword.value.length> 0 ){
            password.style.borderColor = "green";
            loginButton.removeAttribute("disabled");
            loginButton.classList.remove("btn-secondary");
            loginButton.classList.add("btn-info");
        }else{
            password.style.borderColor = "red";
        }
    })

}