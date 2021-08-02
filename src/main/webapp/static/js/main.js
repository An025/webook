window.onload = function (){
    init()
}

function init(){

    let searchButton = document.getElementById("filter")
    searchButton.addEventListener("click", ()=>{
        check();
        getDataFilteredByCategory();
    });

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

    function getSingleData (datas){
        clearDiv();
        for(data of datas){
            displayProductCards(data.name, data.description, data.defaultPrice);
        }
    }


    function clearDiv() {
        const products = document.querySelector('#products');
        while (products.firstChild) {
            products.removeChild(products.firstChild);
        }
    }

    function displayProductCards(dataName, dataDesc, dataPrice){
        const products = document.querySelector('#products');
        const template = document.querySelector("#productsTemplate");
        const clone = document.importNode(template.content, true);
        clone.querySelector('#product-name').textContent = dataName;
        clone.querySelector('#product-description').textContent = dataDesc;
        clone.querySelector('#product-price').textContent = dataPrice;
        products.appendChild(clone);
    }


}