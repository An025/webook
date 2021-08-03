window.onload = function (){
    init()
}

function init() {

    let searchButton = document.getElementById("filter")
    searchButton.addEventListener("click", () => {
        check();
        getDataFilteredByCategory();
    });


    function addToCart(productID){
        fetch("/addToCart/" + productID)
            .then(response=> response.json())
            .then(numberOfItemsInCart => updateCartItemCounter(numberOfItemsInCart))
            }

    function updateCartItemCounter(numberOfItemsInCart){
        console.log(numberOfItemsInCart);
        document.getElementById('counter-box').innerText = numberOfItemsInCart;
    }

    function check() {
        let formCategory = document.getElementsByClassName("check-category");
        let allCategories =[];
        let checkedCategories =[];
        for(checkbox of formCategory){
            allCategories.push(checkbox.value);
        }
        for(checkbox of formCategory){
            if (checkbox.checked === true){
                checkedCategories.push(checkbox.value);
            }
        }
        if (checkedCategories.length == 0){
            return allCategories;
        }
        else{
            return checkedCategories;
        }
    }

    function getDataFilteredByCategory(){

        fetch("/getdata")
            .then(response=> response.json())
            // .then(data => console.log(data));
            .then(data => {

                getSingleData(data)
            });
    }

    function getSingleData (datas) {
        clearDiv();
        for (data of datas) {
            displayProductCards(data.name, data.description, data.defaultPrice, data.id);
        }
        let addToCartButtons = document.querySelectorAll("#add-to-cart")
        for (let button of addToCartButtons) {
            button.addEventListener("click", () => {
                let productID = button.className;
                addToCart(productID);
            });
        }
    }


    function clearDiv() {
        const products = document.querySelector('#products');
        while (products.firstChild) {
            products.removeChild(products.firstChild);
        }
    }

    function displayProductCards(dataName, dataDesc, dataPrice, dataID){
        const products = document.querySelector('#products');
        const template = document.querySelector("#productsTemplate");
        const clone = document.importNode(template.content, true);
        clone.querySelector('#product-name').textContent = dataName;
        clone.querySelector('#add-to-cart').className = dataID;
        clone.querySelector('#product-description').textContent = dataDesc;
        clone.querySelector('#product-price').textContent = dataPrice;
        products.appendChild(clone);
        }


}