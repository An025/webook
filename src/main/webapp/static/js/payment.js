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
    btnPayment.addEventListener("click", () => {removePrevConfirmationButton();
                                                            setModalContent()
                                                            .then((paymentMethod) => sendPaymentInfo(paymentMethod))
                                                            .catch((message)=>console.log(message))});
}

function removePrevConfirmationButton(){
    //Clear prev inserted Confirmation button for assessing new input first
    let confirmation = document.getElementById("confirmation");
    while(confirmation.firstElementChild){
        confirmation.removeChild(confirmation.firstElementChild)
    }
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
        removePrevConfirmationButton();
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
        '<input type=\"text\" id=\"email\" data-type=\"email\" name=\"lname\" value=\"mockaddress@mockingJay.com\" placeholder=\"mockaddress@mockingJay.com\" style=\"min-width: 80%\"><br>'+
        '<label for=\"password\">Password</label><br>' +
        '<input type=\"password\" id=\"password\" data-type=\"password\" name=\"lname\" value=\"notToKnow\" placeholder=\"notToKnow\" style=\"min-width: 80%\"><br>'
    return content;
}

function createCreditCardMethodDetails(){
    let content = '<label for=\"number\">Card number</label><br>' +
        '<input type=\"text\" id=\"number\" data-type=\"cardNo\" name=\"lname\" value=\"1234567812345678\" placeholder=\"1234567812345678\"><br>'+
        '<label for=\"holder\">Name on card</label><br>' +
        '<input type=\"text\" id=\"holder\" data-type=\"name\" name=\"lname\" value=\"John Doe\" placeholder=\"John Doe\" style=\"min-width: 70%\"><br><br>' +
        '<label for=\"expiry-month\" style=\"margin-right: 5px\">Expiry: </label>' +
        '<input type=\"text\" id=\"expiry-month\" data-type=\"expDate\" name=\"lname\" value=\"01\" placeholder=\"01\" style=\"max-width: 30px\">' +
        '<label for=\"expiry-year\" style=\"margin-left: 5px; margin-right: 5px\"> / </label>' +
        '<input type=\"text\" id=\"expiry-year\" data-type=\"expDate\" name=\"lname\" value=\"22\" placeholder=\"22\" style=\"max-width: 30px\"><br>' +
        '<label for=\"cvc-code\"> CVC code: </label><br>' +
        '<input type=\"password\" id=\"cvc-code\" data-type=\"cvcCode\" name=\"lname\" value=\"123\" placeholder=\"123\" style=\"width: 40px; text-align: center\"><br>';
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
        .then(newdata => new Promise((resolve,reject)=>
        {
            let validity = true;
            for (key in newdata) {
                if (newdata[key] == "error"){
                    validity = false;
                }
            }
            if (validity) {
                resolve();
                displayPaymentDetails(newdata);
                closeModalWindow();
                changeOrigTextForPaymentButton();
                showConfirmPaymentButton();
                showCancelPaymentButton();
            } else {
                highLightErrors(newdata);
                reject("Error in payment details.");
            }

        }))
        .catch((message)=>console.log(message));

}


function displayPaymentDetails(data){
    let content = "";
    let detailsContainer = document.getElementById("payment-input-details");
    if (data.isPayPal) {
        content = `
        <p>Payment method: Paypal</p>
        <p>Account: ${data.email}</p>`;
    } else {
        content = `
        <p>Payment method: credit card</p>
        <p>Name on card: ${data.name}</p>
        <p>Card number: ${data.cardNo}</p>`
    }
    detailsContainer.innerHTML = content;

}

function highLightErrors(data){
    for (key in data){
        if (data[key] == "error"){
            //As for expDate 2 input fields share the same data-type
            let invalidInputs = document.querySelectorAll(`input[data-type=${key}]`);
            for (input of invalidInputs) {
                input.style.borderColor = "red";
                input.style.borderWidth = "2p";
                //Removes prev highlight if correction initiated
                input.addEventListener("input", ()=>{
                    //Again, due to that 2 inputs have expDate data-type
                    for (input of invalidInputs) {
                        input.style.borderColor = "black";
                        input.style.borderWidth = "1px";
                    }
                })
            }
        }
    }
}

function closeModalWindow(){
    let closeButton = document.querySelector("button[data-dismiss='modal']");
    closeButton.click();
}

function changeOrigTextForPaymentButton(){
    let btnPayment = document.querySelector("#payment-button");
    btnPayment.innerText = "Change payment details";
}

function showConfirmPaymentButton(){
    let confirmationField = document.getElementById("confirmation");
    let makePayment = document.createElement("button")
    makePayment.type = "button";
    makePayment.addEventListener("click", ()=>{
        window.location = "/confirmation/true";
    })
    makePayment.innerText = "Confirm payment";
    makePayment.className = "btn btn-success";
    makePayment.style.marginTop = "10px";
    makePayment.style.marginRight = "10px";
    confirmationField.appendChild(makePayment);
}

function showCancelPaymentButton(){
    let confirmationField = document.getElementById("confirmation");
    let cancelPayment = document.createElement("button")
    cancelPayment.type = "button";
    cancelPayment.addEventListener("click", ()=>{
        window.location = "/confirmation/false";
    })
    cancelPayment.innerText = "Cancel payment";
    cancelPayment.className = "btn btn-warning";
    cancelPayment.style.marginTop = "10px";
    confirmationField.appendChild(cancelPayment);
}



