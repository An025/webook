window.onload = function (){
    init()

}

function init() {
    getDataForOrderHistory();

}

function getDataForOrderHistory() {
    fetch("orderhistorydata")
        .then(response => response.json())
        .then(displayOrderHistory);
}

function displayOrderHistory(orders){
    let cardsField = document.getElementById("order_history");
    cardsField.innerHTML = "";
    for (let order of orders){
        let card = document.createElement("div");
        card.className = "card";
        let orderDate = document.createElement("div");
        orderDate.innerHTML = "<b>" + "Order date: " + "</b>" + order.orderDate;
        card.appendChild(orderDate);
        let orderStatus = document.createElement("div");
        orderStatus.innerHTML = "<b>" +  "Order status: " + "</b>" + order.orderStatus;
        card.appendChild(orderStatus);
        let totalPrice = document.createElement("div");
        totalPrice.innerHTML = "<b>" + "Order total price: " + "</b>" + 0;
        card.appendChild(totalPrice);
        cardsField.appendChild(card);
        let itemsListHeader = document.createElement("p");
        itemsListHeader.innerHTML = "<b>" + "List of products:" + "<b>" ;
        card.appendChild(itemsListHeader);
        let productCard = document.createElement("table");
        let headerRow = document.createElement("tr");
        let productNameHeader = document.createElement("th");
        productNameHeader.innerText = "product name";
        headerRow.appendChild(productNameHeader)
        productCard.appendChild(headerRow)
        let productPriceHeader = document.createElement("th");
        productPriceHeader.innerText = "product price";
        headerRow.appendChild(productPriceHeader)
        productCard.appendChild(headerRow)
        card.appendChild(productCard)
        for (let product of order.products){
            let productRow = document.createElement("tr");
            let productName = document.createElement("td");
            productName.innerText = product.name;
            productRow.appendChild(productName);
            productCard.appendChild(productRow);
            let priceName = document.createElement("td");
            priceName.innerText = product.defaultPrice;
            productRow.appendChild(priceName);
            // productCard.appendChild(priceRow);
            // let productName = document.createElement("div");
            // productName.innerText = "product name: " + product.name + "product price: " + product.defaultPrice;;
            // productCard.appendChild(productName);
            // let productPrice = document.createElement("div");
            // productPrice.innerText =
            // productCard.appendChild(productPrice);
            // card.appendChild(productCard)
        }
    }
}
