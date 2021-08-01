window.onload = function (){
    init()
}

function init(){

    let searchButton = document.getElementById("filter");
    searchButton.addEventListener("click", getDataFilteredByCategory);

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
        // let container = document.querySelector('.container');
        // container.remove();
        // let newContainer = document.createElement('div');
        // newContainer.classList.add('container');

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