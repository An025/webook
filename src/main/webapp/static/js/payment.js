window.onload = function (){
    init()
}

function init() {
    console.log("Hello from payment js.")
}

function getTotalPrice() {
    fetch("getTotalPrice")
        .then(response => response.json())
        .then((priceData)=>displayTotalPrice(priceData.price));
}

function displayTotalPrice(price){
    let priceField = document.getElementById("priceContainer");
    let header = document.createElement("h4")
    header.innerText= "Total price: "
    let priceTag = document.createElement("p");
    name.innerText = `${price}`;
    priceField.appendChild(priceTag);
}

function confirmPayment(){
    let checkoutField = document.getElementById("priceContainer");
    let makePayment = document.createElement("button")
    makePayment.type = "button";
    makePayment.addEventListener("click", ()=>{
        window.location = "/confirmation";
    })
    makePayment.innerText = "Proceed to payment";
    makePayment.className = "btn btn-primary"
    checkoutField.appendChild(makePayment);
}



