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
        let quantityHeader = document.createElement("th");
        quantityHeader.innerText = "quantity";
        quantityHeader.scope = "col"
        let unitPriceHeader = document.createElement("th");
        unitPriceHeader.innerText = "Unit price";
        unitPriceHeader.scope = "col"
        let deleteButtonHeader = document.createElement("th");
        deleteButtonHeader.scope = "col"
        deleteButtonHeader.innerText = "Delete";
        header.appendChild(nameOfProduct);
        header.appendChild(quantityHeader);
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
            quantity.innerText = product[1].quantity;
            let del = document.createElement("td");
            del.innerText = "delete";
            row.appendChild(name);
            row.appendChild(quantity);
            row.appendChild(price);
            row.appendChild(del);
            table.appendChild(row);
        }

        // <table style="width:100%">
        //     <tr>
        //         <th>Firstname</th>
        //         <th>Lastname</th>
        //         <th>Age</th>
        //     </tr>
        //     <tr>
        //         <td>Jill</td>
        //         <td>Smith</td>
        //         <td>50</td>
        //     </tr>
        //     <tr>
        //         <td>Eve</td>
        //         <td>Jackson</td>
        //         <td>94</td>
        //     </tr>
        // </table>

    }
}
