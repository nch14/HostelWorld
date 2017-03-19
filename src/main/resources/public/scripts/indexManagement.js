/**
 * Created by mr.meng on 17/1/10.
 */

function newCategoryDiv() {
    //alert("newCategory");

    var newCategoryDiv = document.getElementById("newCategoryDiv");

    var salePerPrice = document.createElement("div");
    salePerPrice.setAttribute("class", "sale-per-price");
    var categoryName = document.createElement("input");
    categoryName.setAttribute("class", "sale-per-category-input");
    categoryName.placeholder="规格名称";
    var ifSaleCheckBox = document.createElement("input");
    ifSaleCheckBox.type = "checkbox";
    ifSaleCheckBox.setAttribute("class", "sale-per-price-checkbox");
    ifSaleCheckBox.checked="checked";
    var priceInput = document.createElement("input");
    priceInput.type = "text";
    priceInput.setAttribute("class", "sale-per-price-input");
    priceInput.placeholder="现价";
    priceInput.onKeyUp = "value=value.replace(/[^\d.]/g,'')";
    var marketPriceInput = document.createElement("input");
    marketPriceInput.type = "text";
    marketPriceInput.setAttribute("class", "sale-per-price-input");
    marketPriceInput.placeholder="市场价";
    marketPriceInput.onKeyUp = "value=value.replace(/[^\d.]/g,'')";


    salePerPrice.appendChild(categoryName);
    salePerPrice.appendChild(ifSaleCheckBox);
    salePerPrice.appendChild(priceInput);
    salePerPrice.appendChild(marketPriceInput);

    newCategoryDiv.appendChild(salePerPrice);

}

function closeThis(thisDiv) {
    if (confirm("你确定删除该促销商品吗？")) {
        //DO SOMETHING

        saleNum--;
//        alert("closing..."+thisDiv.id);
        var alldiv = document.getElementById("allDivs");
        var toRemove = document.getElementById("d" + thisDiv.id);
//        alert(toRemove.id);
        alldiv.removeChild(toRemove);

    }
    else {
        //DO NOTHING
    }

}