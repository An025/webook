window.onload = function (){
    init()
}

function init() {
    console.log("Hello from payment js.");
    getTotalPrice();
    handlePayment();
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

function handlePayment(){
    let btnPayment = document.querySelector("#payment-button");
    btnPayment.addEventListener("click", () =>setModalContent()
                                                            .then((paymentMethod) => sendPaymentInfo(paymentMethod))
                                                            .catch((message)=>console.log(message)));
}

function setModalContent(){
    return new Promise((resolve,reject) => {
        let paymentMethod = getPaymentOption();
        let paymentForm = document.querySelector("#payment-details");
        //Clear prev inserted content
        while (paymentForm.firstElementChild) {
            paymentForm.removeChild(paymentForm.firstElementChild)
        }
        let paymentDetailDiv = document.createElement("div");
        let savechangesbtn = document.querySelector("#save-changes");
        //It can be hidden if no payment method was chosen, so it needs a reset.
        savechangesbtn.style.display = "display";
        let content = "";
        switch (paymentMethod) {
            case "none":
                let message = document.createElement("p");
                message.innerText = "Please choose payment method first.";
                paymentForm.appendChild(message);
                savechangesbtn.style.display = "none";
                reject("No payment method was chosen.");
                break;
            case "paypal":
                content = createPayPalMethodDetails();
                paymentDetailDiv.innerHTML = content;
                paymentForm.appendChild(paymentDetailDiv);
                break;
            case "card":
                content = createCreditCardMethodDetails();
                paymentDetailDiv.innerHTML = content;
                paymentForm.appendChild(paymentDetailDiv);
                break;
        }
        resolve(paymentMethod);
    }
)}

function sendPaymentInfo(paymentMethod){
    //Prev listener needs to be removed, so onclick is used instead of addEventListener
    let savechangesbtn = document.querySelector("#save-changes");
    savechangesbtn.onclick = function (){
        readInputValues(paymentMethod)
            .then((data) => sendJSON(data));

    };

}

function readInputValues(paymentMethod){
    return new Promise((resolve) => {
        let data = {
            "isPayPal": false,
            "email": "",
            "password": "",
            "cardNo": "",
            "name": "",
            "expDate": "",
            "cvcCode": ""
        };
        let modalFormInputs = document.querySelectorAll("#payment-details input");
        if (paymentMethod == "paypal") {
        data.isPayPal = true;
        data.email = modalFormInputs[0].value;
        data.password = modalFormInputs[1].value;
        } else if (paymentMethod == "card") {
        data.isPayPal = false;
        data.cardNo = modalFormInputs[0].value;
        data.name = modalFormInputs[1].value;
        data.expDate = `${modalFormInputs[2].value}/${modalFormInputs[3].value}`;
        data.cvcCode = modalFormInputs[4].value;
    }
        resolve(data);
    })
}


function getPaymentOption(){
    let paymentFormInputs = document.querySelectorAll("#payment-method-form input");
    let paymentMethod = "none"; //Will be overridden if payment method is checked
    for (inputelement of paymentFormInputs){
        if (inputelement.checked){
            paymentMethod = inputelement.value;
        }
    }
    return paymentMethod;
}


function createPayPalMethodDetails(){
    let content = '<label for=\"email\">Email</label><br>' +
        '<input type=\"text\" id=\"email\" name=\"lname\" value=\"mockaddress@mockingJay.com\" placeholder=\"mockaddress@mockingJay.com\" style=\"min-width: 80%\"><br>'+
        '<label for=\"password\">Password</label><br>' +
        '<input type=\"password\" id=\"password\" name=\"lname\" value=\"notToKnow\" placeholder=\"notToKnow\" style=\"min-width: 80%\"><br>'
    return content;
}

function createCreditCardMethodDetails(){
    let content = '<label for=\"number\">Card number</label><br>' +
        '<input type=\"text\" id=\"number\" name=\"lname\" value=\"1234567812345678\" placeholder=\"1234567812345678\"><br>'+
        '<label for=\"holder\">Name on card</label><br>' +
        '<input type=\"text\" id=\"holder\" name=\"lname\" value=\"John Doe\" placeholder=\"John Doe\" style=\"min-width: 70%\"><br><br>' +
        '<label for=\"expiry-month\" style=\"margin-right: 5px\">Expiry: </label>' +
        '<input type=\"text\" id=\"expiry-month\" name=\"lname\" value=\"01\" placeholder=\"01\" style=\"max-width: 30px\">' +
        '<label for=\"expiry-year\" style=\"margin-left: 5px; margin-right: 5px\"> / </label>' +
        '<input type=\"text\" id=\"expiry-year\" name=\"lname\" value=\"22\" placeholder=\"22\" style=\"max-width: 30px\"><br>' +
        '<label for=\"cvc-code\"> CVC code: </label><br>' +
        '<input type=\"password\" id=\"cvc-code\" name=\"lname\" value=\"123\" placeholder=\"123\" style=\"width: 40px; text-align: center\"><br>';
    return content;

}

function sendJSON(data){

    fetch('/payment/', {
        method: 'POST', // or 'PUT'
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(data => new Promise((resolve,reject)=>
        {
            console.log(data);

        }))

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



