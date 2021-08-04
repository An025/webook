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

    function displayItemsInCart(productsInCart) {
        let cartTableContainer = document.getElementById("cart-table");
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
            let upButton = document.createElement("td");
            let ub = document.createElement("button");
            ub.type = "button";
            ub.innerText = "+";
            ub.className = "btn btn-primary"
            upButton.appendChild(ub)
            quantity.innerText = product[1].quantity;
            let del = document.createElement("td");
            let delB = document.createElement("button");
            delB.type = "button";
            delB.innerText = "Delete";
            delB.className = "btn btn-primary"
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
}
