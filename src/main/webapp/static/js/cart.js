window.onload = function (){
    init()
}

function init() {
    getDataForCart();

    function getDataForCart() {
        fetch("getItemsInCart")
            .then(response => response.json())
            .then(displayItemsInCart);
    }

    let checkoutButton = document.getElementById("checkout-button");
    checkoutButton.addEventListener("click", getBillingInformation);

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
        let header = document.createElement("p")
        header.innerText= "Your billing information: "
        let city = document.createElement("p")
        city.innerText= billingInformation.city;
        let name = document.createElement("p");
        name.innerText = billingInformation.name
        let email = document.createElement("p");
        email.innerText = billingInformation.email;
        let phone = document.createElement("p");
        phone.innerText = billingInformation.phone;
        let country = document.createElement("p");
        country.innerText = billingInformation.country
        let zipcode = document.createElement("p");
        zipcode.innerText = billingInformation.zipcode;
        let address = document.createElement("p");
        address.innerText = billingInformation.address;
        checkoutField.appendChild(header);
        checkoutField.appendChild(name);
        checkoutField.appendChild(email);
        checkoutField.appendChild(phone);
        checkoutField.appendChild(city);
        checkoutField.appendChild(zipcode);
        checkoutField.appendChild(address);
        checkoutField.appendChild(country);
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
            .then(displayBillingInformation);
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

}
