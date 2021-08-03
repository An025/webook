window.onload = function (){
    init()
}

function init(){

    let searchButton = document.getElementById("filter")
    searchButton.addEventListener("click", ()=>{
        let categoriesForFilter = check("check-category");
        let suppliersForFilter = check("check-supplier");
        getData(categoriesForFilter, suppliersForFilter);


    });

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
            .map(filtered => displayProductCards(filtered.name, filtered.description, filtered.defaultPrice));
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

    // function getSingleData (datas){
    //     console.log(datas);
    //     clearDiv();
    //     for(data of datas){
    //         displayProductCards(data.name, data.description, data.defaultPrice);
    //     }
    // }

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