window.onload = function (){
    init()
}

function init(){

    let searchButton = document.getElementById("filter")
    searchButton.addEventListener("click", ()=>{
        check();
        // getDataFilteredByCategory
    });

    function check() {
        let formCategory = document.getElementsByClassName("check-category");
        console.log(formCategory);
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
        clearContainer();
        for(data of datas){
            displayProductCards(data.name, data.description, data.defaultPrice);
        }
    }


    function clearContainer(){
        const container = document.querySelector('.container');
        while (container.firstChild) {
            container.removeChild(container.firstChild);
            }
    }

    function displayProductCards(dataName, dataDesc, dataPrice){
        const container = document.querySelector('.container');
        const template = document.querySelector("#productsTemplate");
        const clone = document.importNode(template.content, true);
        clone.querySelector('#product-name').textContent = dataName;
        clone.querySelector('#product-description').textContent = dataDesc;
        clone.querySelector('#product-price').textContent = dataPrice;
        container.appendChild(clone);
    }


}