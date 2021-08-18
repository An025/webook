window.onload = function (){
    init()
}

function init() {
    addToCart();
    initializeAddToCartButtons();
    logoImgClick();

    let clearFilterButton = document.getElementById("clear-filter")
    clearFilterButton.addEventListener("click", ()=> {
        clearCheckboxes(document.getElementsByClassName("check-category"));
        clearCheckboxes(document.getElementsByClassName("check-supplier"));

    });

    function initializeAddToCartButtons() {
        let addToCartButtons = document.querySelectorAll(".add-to-cart")
        for (let button of addToCartButtons) {
            button.addEventListener("click", () => {
                let productID = button.id;
                addToCart(productID);
            });
        }
    }


    function addToCart(productID){
        fetch("/addToCart/" + productID)
            .then(response=> response.json())
            .then(numberOfItemsInCart => updateCartItemCounter(numberOfItemsInCart))
            }

    function updateCartItemCounter(numberOfItemsInCart){
        document.getElementById('counter-box').innerText = numberOfItemsInCart;
    }

    // function getNumberOfItemsInCart(){
    //     fetch("/addToCart/")
    //         .then(response=> response.json())
    //         .then(numberOfItemsInCart => updateCartItemCounter(numberOfItemsInCart))
    // }

    // function check() {
    //     let formCategory = document.getElementsByClassName("check-category");
    //     let allCategories = [];
    //     let checkedCategories = [];
    //     for (checkbox of formCategory) {
    //         allCategories.push(checkbox.value);
    //     }
    // }
    //

    let searchButton = document.getElementById("filter")
    searchButton.addEventListener("click", ()=>{
        let categoriesForFilter = check("check-category");
        let suppliersForFilter = check("check-supplier");
        getData(categoriesForFilter, suppliersForFilter);
    });

    function clearCheckboxes(checkboxes) {
        for(checkbox of checkboxes){
            checkbox.checked = false;
        }
    }

    function check(checkBy) {
        let checkboxElements = document.getElementsByClassName(checkBy);
        let allElements =[];
        let checkedElements =[];
        for(checkbox of checkboxElements){
            allElements.push(checkbox.value);
        }
        for(checkbox of checkboxElements){
            if (checkbox.checked === true){
                checkedElements.push(checkbox.value);
            }
        }
        console.log(allElements);
        console.log(checkedElements);

        if (checkedElements.length == 0){
            return allElements;
        }
        else{
            return checkedElements;
        }

    }

    function filterByLists(categories, suppliers, data){
        clearDiv();
        data
            .filter(filtered => categories.includes(filtered.productCategory.name) && suppliers.includes(filtered.supplier.name))
            // .map(filtered => console.log(filtered));
            .map(filtered => displayProductCards(filtered.name, filtered.description, filtered.defaultPrice, filtered.id, filtered.productCategory.name));
        initializeAddToCartButtons();
    }


    function getData(categories, suppliers){
        let dataByCat;
        fetch("/getdata")
            .then(response=> response.json())
            // .then(data => console.log(data));
            .then(data => {
                filterByLists(categories, suppliers, data);
                // getSingleData(data)
            });
          }

        // function getSingleData(datas) {
        //     clearDiv();
        //     for (data of datas) {
        //         displayProductCards(data.name, data.description, data.defaultPrice, data.id);
        //     }
        //     let addToCartButtons = document.querySelectorAll(".add-to-cart")
        //     for (let button of addToCartButtons) {
        //         button.addEventListener("click", () => {
        //             let productID = button.id;
        //             addToCart(productID);
        //         });
        //     }
        // }


        function clearDiv() {
            const products = document.querySelector('#products');
            while (products.firstChild) {
                products.removeChild(products.firstChild);
            }
        }

        function displayProductCards(dataName, dataDesc, dataPrice, dataID, dataCategory) {
            const products = document.querySelector('#products');
            const template = document.querySelector("#productsTemplate");
            const clone = document.importNode(template.content, true);
            clone.querySelector('#product-name').textContent = dataName;
            clone.querySelector('#product-img').src= `/static/img/${dataCategory}_${dataID}.jpg`;
            clone.querySelector('#product-img').classList.add("book-img");
            clone.querySelector('.add-to-cart').id = dataID;
            clone.querySelector('#product-description').textContent = dataDesc;
            clone.querySelector('#product-price').textContent = dataPrice.toFixed(1) + ' USD';
            products.appendChild(clone);
        }
        }

    function logoImgClick(){
        let logoImg = document.querySelector("#logoImg");
        logoImg.addEventListener("click", () => window.location = "/");
    }