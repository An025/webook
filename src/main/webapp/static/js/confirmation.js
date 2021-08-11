window.onload = function (){
    init()
}

function init() {
    console.log("Hello from confirmation js.");
    logoImgClick();
    handleTryAgainBtn();
    backMainPage();
}



function handleTryAgainBtn() {
    let tryAgainButton = document.querySelector(".try_again");
    tryAgainButton.addEventListener("click", () => window.location = "/payment");
}

function backMainPage() {
    let backBtn = document.querySelector(".back_main_button");
    backBtn.addEventListener("click", () => window.location = "/");
}

function logoImgClick(){
    let logoImg = document.querySelector("#logoImg");
    logoImg.addEventListener("click", () => window.location = "/");
}



