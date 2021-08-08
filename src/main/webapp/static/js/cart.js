window.onload = function (){
    init()
}

function init() {
    getDataForCart();
    createCheckOutButton();
    //Set event listener for input event on modal input fields.
    clearPrevHighlight();
}

function createCheckOutButton(){
    let checkoutButton = document.getElementById("checkout-button");
    checkoutButton.addEventListener("click", getBillingInformation);
}


function createChangeDataButton(){
    checkoutButton = document.createElement('button');
    checkoutButton.setAttribute('id', 'checkout-button');
    checkoutButton.className = 'btn btn-primary';
    checkoutButton.innerText = "Change data";
    checkoutButton.dataset.toggle = "modal";
    checkoutButton.dataset.target = "#exampleModal";
    let checkout = document.getElementById('checkout');
    checkout.appendChild(checkoutButton);
    checkoutButton.addEventListener("click", getBillingInformation);
}

function highLightErrors(){
    let displayedData = document.querySelectorAll("#checkout p");
    for (let data of displayedData){
        let idname = data.dataset.type;
        let userDataInput = document.getElementById(idname);
        let userText = data.innerText.split(":")[1].trim();
        if (userText == "error"){
            userDataInput.style.borderColor = "red";
            userDataInput.style.borderWidth = "2px";
        }
    }
}

function clearPrevHighlight(){
    let userInputs = document.querySelectorAll(".modal-body form input");
    console.log(userInputs);
    for (inputField of userInputs){
        inputField.addEventListener("input", (event)=>{
            console.log(inputField);
            event.currentTarget.style.borderColor = "black";
            event.currentTarget.style.borderWidth = "1px";
        })
    }

}

function getDataForCart() {
    fetch("getItemsInCart")
        .then(response => response.json())
        .then(displayItemsInCart);
}


function getBillingInformation(){
    let city = document.getElementById("city");
    let name = document.getElementById("name");
    let email = document.getElementById("email");
    let phone = document.getElementById("phone");
    let country = document.getElementById("country");
    let zipcode = document.getElementById("zipcode");
    let address = document.getElementById("address");
    let saveButton = document.getElementById("save-changes");
    saveButton.addEventListener("click", e=> sendJSON(city, name, email, phone, country, zipcode, address))
}

function displayBillingInformation(billingInformation){
    let checkoutField = document.getElementById("checkout");
    checkoutField.innerText = "";
    let header = document.createElement("h4")
    header.innerText= "Your billing information: "
    let name = document.createElement("p");
    name.innerText = `Name: ${billingInformation.name}`;
    name.dataset.type ="name";
    let email = document.createElement("p");
    email.innerText = `Email: ${billingInformation.email}`;
    email.dataset.type="email";
    let phone = document.createElement("p");
    phone.innerText = `Phone: ${billingInformation.phone}`;
    phone.dataset.type = "phone";
    let city = document.createElement("p")
    city.innerText= `City : ${billingInformation.city}`;
    city.dataset.type = "city";
    let country = document.createElement("p");
    country.innerText = `Country: ${billingInformation.country}`;
    country.dataset.type = "country";
    let zipcode = document.createElement("p");
    zipcode.innerText = `Zipcode: ${billingInformation.zipcode}`;
    zipcode.dataset.type= "zipcode";
    let address = document.createElement("p");
    address.innerText = `Address: ${billingInformation.address}`;
    address.dataset.type= "address";
    checkoutField.appendChild(header);
    checkoutField.appendChild(name);
    checkoutField.appendChild(email);
    checkoutField.appendChild(phone);
    checkoutField.appendChild(city);
    checkoutField.appendChild(zipcode);
    checkoutField.appendChild(address);
    checkoutField.appendChild(country);
}

function proceedToPayment(){
    let checkoutField = document.getElementById("checkout");
    let proceedToPayment = document.createElement("button")
    proceedToPayment.type = "button";
    proceedToPayment.innerText = "Proceed to payment";
    proceedToPayment.className = "btn btn-primary"
    checkoutField.appendChild(proceedToPayment);
}

function sendJSON(city, name, email, phone, country, zipcode, address){
    const data = {name: name.value, city: city.value, email: email.value, phone: phone.value, country: country.value, zipcode: zipcode.value, address: address.value};
    fetch('/billing/', {
        method: 'POST', // or 'PUT'
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(data => new Promise((resolve,reject)=>
        {
            displayBillingInformation(data);
            let validity = true;
            for (input in data) {
                if (data[input] == "error"){
                    validity = false;
                }
            }
            if (validity) {
                resolve();
                proceedToPayment();
                createChangeDataButton();
            } else {
                highLightErrors();
                reject();
            }
        }))
        .catch(()=>{
            console.log("Rejected");
            createChangeDataButton();
        });
}


function displayItemsInCart(productsInCart) {
    if (productsInCart.length === 0) {
        let cartTableContainer = document.getElementById("cart-table");
        cartTableContainer.innerText = "No items in your cart";
    } else {
        let cartTableContainer = document.getElementById("cart-table");
        cartTableContainer.innerHTML = ""
        let table = document.createElement("table");
        table.className = "table"
        let header = document.createElement("tr");
        let nameOfProduct = document.createElement("th");
        nameOfProduct.scope = "col";
        nameOfProduct.innerText = "Name of product";
        let downButtonHeader = document.createElement("th");
        downButtonHeader.scope = "col"
        let quantityHeader = document.createElement("th");
        quantityHeader.innerText = "quantity";
        quantityHeader.scope = "col"
        let upButtonHeader = document.createElement("th");
        upButtonHeader.scope = "col"
        let unitPriceHeader = document.createElement("th");
        unitPriceHeader.innerText = "Unit price";
        unitPriceHeader.scope = "col"
        let deleteButtonHeader = document.createElement("th");
        deleteButtonHeader.scope = "col"
        deleteButtonHeader.innerText = "Delete";
        header.appendChild(nameOfProduct);
        header.appendChild(downButtonHeader)
        header.appendChild(quantityHeader);
        header.appendChild(upButtonHeader);
        header.appendChild(unitPriceHeader);
        header.appendChild(deleteButtonHeader);
        table.appendChild(header);
        cartTableContainer.appendChild(table);
        for (let product of Object.entries(productsInCart)) {
            console.log(product[1].id);
            let row = document.createElement("tr");
            let name = document.createElement("td");
            name.innerText = product[1].name;
            let price = document.createElement("td");
            price.innerText = product[1].defaultPrice;
            let quantity = document.createElement("td");
            let downButton = document.createElement("td");
            let db = document.createElement("button");
            db.type = "button";
            db.innerText = "-";
            db.className = "btn btn-primary"
            downButton.appendChild(db);
            quantity.appendChild(downButton)
            if (product[1].quantity > 1) {
                db.addEventListener("click", e => changeQuantity(db.innerText, product[1].id))
            }
            let upButton = document.createElement("td");
            let ub = document.createElement("button");
            ub.type = "button";
            ub.innerText = "+";
            ub.className = "btn btn-primary"
            ub.addEventListener("click", e => changeQuantity(ub.innerText, product[1].id))
            upButton.appendChild(ub)
            quantity.innerText = product[1].quantity;
            let del = document.createElement("td");
            let delB = document.createElement("button");
            delB.type = "button";
            delB.innerText = "Delete";
            delB.className = "btn btn-primary"
            delB.addEventListener("click", e => changeQuantity(delB.innerText, product[1].id));
            del.appendChild(delB)
            row.appendChild(name);
            row.appendChild(downButton);
            row.appendChild(quantity);
            row.appendChild(upButton);
            row.appendChild(price);
            row.appendChild(del);
            table.appendChild(row);
        }
        }
    let price = 0;
    let totalPriceContainer = document.getElementById("total-price");
    totalPriceContainer.innerText = "";
    let orderPrice;
    for (let product of Object.entries(productsInCart)) {
        price += (parseInt(product[1].quantity) * parseInt(product[1].defaultPrice));
        orderPrice = document.createElement("p");
        orderPrice.innerText = "TOTAL PRICE: " + price;
    }
    totalPriceContainer.appendChild(orderPrice);
}

function changeQuantity(directionOfChange, productID){
        fetch("/changeQuantity/" + directionOfChange + "/" + productID)
            .then(response => response.json())
            .then(getDataForCart);
    }


