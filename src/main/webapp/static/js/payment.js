window.onload = function (){
    init()
}

function init() {
    console.log("Hello from payment js.");
    getTotalPrice()
}

function getTotalPrice() {
        fetch("getItemsInCart")
            .then(response => response.json())
            .then(data =>{
                console.log(data);
                displayTotalPrice(data)
            });
}

function displayTotalPrice(products){
    let totalprice = 0;
    let totalPriceDiv = document.querySelector("#total-price");
    let totalPriceTag = document.createElement("span");
    for (product of products){
        totalprice += product.defaultPrice * product.quantity;
    }
    totalPriceTag.innerText = `Total price: ${totalprice} USD`;
    totalPriceDiv.appendChild(totalPriceTag);
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



