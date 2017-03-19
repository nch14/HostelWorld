/**
 * Created by mr.meng on 17/1/23.
 */

$(document).ready(function () {
    getSaleGoods();
    //getGoodsCategroy();
});

var storeId = 1;
var specificationList = [];
var unitPriceList = [];
var avePriceList = [];
var setAsPromoteProductList = [];

var productId;


function getSaleGoods() {
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/promote",
        type: "GET",
        data: {
            storeId: storeId
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            if (data.data == "") {
                alert("目前还没有设置促销商品！");
            } else {
                $.each(data.data, function (idx, item) {
                    //alert(item.name);
                    var addNewProperty = document.getElementById("allDivs");

                    var perIndex = document.createElement("div");
                    perIndex.setAttribute("class", "index-management-per-div");
                    perIndex.id = "dc" + num;
                    perIndex.bid = num;
                    perIndex.cid = item.id;
                    perIndex.sale = "true";
                    perIndex.onclick = function () {
                        showPriceDetail(this);
                    }
                    var indexP = document.createElement("p");
                    indexP.setAttribute("class", "add-goods-head");
                    indexP.innerHTML = "设置首页促销商品";

                    var closeBtn = document.createElement("button");
                    closeBtn.setAttribute("class", "add-goods-close-btn");
                    closeBtn.pid = item.id;
                    closeBtn.sale = "true";
                    closeBtn.onclick = function () {
                        closeThis(this);
                    }
                    closeBtn.id = "c" + num;
                    closeBtn.innerHTML = "X";

                    var goodDiv = document.createElement("div");
                    goodDiv.setAttribute("class", "suit-index-management");

                    var imgInput = document.createElement("img");
                    imgInput.src = item.imgUrl;
                    var goodsName = document.createElement("h3");
                    goodsName.innerHTML = item.name
                    var goodsSellR = document.createElement("h4");
//        var deco = document.createElement("h1");
                    goodsSellR.innerHTML = "件";
                    var goodsSellL = document.createElement("h4");
                    goodsSellL.innerHTML = "共售出";
                    var goodsSellNum = document.createElement("h2");
                    goodsSellNum.innerHTML = item.sales;

                    imgInput.id = "imgInput" + saleNum;
                    goodsName.id = "goodsName" + saleNum;
                    goodsSellNum.id = "goodsSellNum" + saleNum;

//        alert(imgInput.id);
                    goodDiv.appendChild(imgInput);
                    goodDiv.appendChild(goodsName);
//        goodDiv.appendChild(deco);
                    goodDiv.appendChild(goodsSellR);
                    goodDiv.appendChild(goodsSellNum);
                    goodDiv.appendChild(goodsSellL);

                    perIndex.appendChild(indexP);
                    perIndex.appendChild(closeBtn);

                    perIndex.appendChild(goodDiv);

                    addNewProperty.appendChild(perIndex);
                    num++;
                    saleNum++;


                });
            }

            //changeShow(categoryId);
        },
    });
}


//初始化一级分类下拉框
function getGoodsCategroy(saleNum, categorySelect, middleCategorySelect, brandSelect) {

    //var goodsCategorySelect= document.getElementById("goodsCategorySelect");
    //goodsCategorySelect.innerHTML="";

    //alert("saleNum is"+saleNum);
    categorySelect.innerHTML = "";
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/productCategory",
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            if (data.data == "") {
                alert("目前还没有设置类别！");
            } else {
                $.each(data.data, function (idx, item) {
                    if (idx == 0) {
                        getGoodsMiddleCategory(saleNum, item.id, middleCategorySelect, brandSelect);
                    }
                    var perOption = document.createElement("option");
                    perOption.value = item.id;
                    perOption.innerHTML = item.name;
                    //var perOptionNew = document.createElement("option");
                    //perOptionNew.value = item.id;
                    //perOptionNew.innerHTML = item.name;
                    //var perOptionNewTwo = document.createElement("option");
                    //perOptionNewTwo.value = item.id;
                    //perOptionNewTwo.innerHTML = item.name;
                    //goodsCategorySelect.appendChild(perOption);
                    categorySelect.appendChild(perOption);
                    //goodsCategorySelectNew.appendChild(perOptionNew);
                    //goodsCategorySelectNewTwo.appendChild(perOptionNewTwo);

                });
            }

            //changeShow(categoryId);
        },
    });
}

//初始化二级分类下拉框
function getGoodsMiddleCategory(saleNum, categoryId, middleCategorySelect, brandSelect) {
    //alert("获取二级分类！"+categoryId);
    //var goodsMiddleCategorySelect = document.getElementById("goodsMiddleCategorySelect");
    //goodsMiddleCategorySelect.innerHTML="";
    var middleCategoryId;
    middleCategorySelect.innerHTML = "";
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/productCategory/" + categoryId,
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            if (data.data == "") {
                alert("该类别下没有详细分类！");
            } else {
                $.each(data.data, function (idx, item) {
                    if (idx == 0) {
                        middleCategoryId = item.id;
                    }
                    var perOption = document.createElement("option");
                    perOption.value = item.id;
                    perOption.innerHTML = item.name;
                    middleCategorySelect.appendChild(perOption);
                });
            }

            getGoodsBrand(saleNum, middleCategoryId, brandSelect);

        },
    });

}

//初始化商品下拉框
function getGoodsBrand(saleNum, middleCategoryId, brandSelect) {
    //var goodsBrandSelect = document.getElementById("goodsBrandSelect");
    //goodsBrandSelect.innerHTML="";
    //alert("到商品这里了");
    var productId;
    var productName;
    var imgUrl;
    var sales;
    brandSelect.innerHTML = "";
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/product/category/" + middleCategoryId,
        type: "GET",
        data: {
            storeId: storeId,
            pageSize:100,
            pageNum:1
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            if (data.data == "") {
                alert("该分类下没有商品！");
                var imgInput = document.getElementById("imgInput" + saleNum);
                var goodsName = document.getElementById("goodsName" + saleNum);
                var goodsSellNum = document.getElementById("goodsSellNum" + saleNum);
                imgInput.src="";
                goodsName.innerHTML="商品名称";
                goodsSellNum.innerHTML="";
            } else {
                var perItem= data.data.listObject;
                for(var i=0;i<data.data.listObject.length;i++){
                    var item=perItem[i];
                    var perOption = document.createElement("option");
                    perOption.value = item.id;
                    perOption.innerHTML = item.name;
                    brandSelect.appendChild(perOption);
                    productId = item.id;
                    productName = item.name;
                    imgUrl = item.imgUrl;
                    sales = item.sales;
                }



                showGoodsDetail(productId, productName, imgUrl, sales, saleNum);

            }


        },
    });
}


//更新二级分类下拉框
function changeGoodsMiddleCategory(btn) {
    var middleCategoryId;
    //alert("获取二级分类！"+btn.bid);
    var goodsMiddleCategorySelect = document.getElementById("goodsMiddleCategorySelect" + btn.bid);
    //alert( goodsMiddleCategorySelect.innerHTML);
    goodsMiddleCategorySelect.innerHTML = "";
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/productCategory/" + btn.value,
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            if (data.data == "") {
                alert("该类别下没有详细分类！");
            } else {
                $.each(data.data, function (idx, item) {
                    var perOption = document.createElement("option");
                    if (idx == 0) {
                        middleCategoryId = item.id;
                    }
                    perOption.value = item.id;
                    perOption.innerHTML = item.name;
                    goodsMiddleCategorySelect.appendChild(perOption);
                });
                changeGoods(btn);

            }


        },
    });
    //changeGoods(btn, middleCategoryId);
}

//更新商品下拉框
function changeGoods(btn) {
    //alert("改商品了");

    var productId;
    var productName;
    var imgUrl;
    var sales;
    alert(btn.value);
    var goodsBrandSelect = document.getElementById("goodsBrandSelect" + btn.bid);
    goodsBrandSelect.innerHTML = "";
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/product/category/" + btn.value,
        type: "GET",
        data: {
            storeId: storeId,
            pageSize:100,
            pageNum:1
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            if (data.data == "") {
                alert("该分类下没有商品！");
                var imgInput = document.getElementById("imgInput" + btn.bid);
                var goodsName = document.getElementById("goodsName" + btn.bid);
                var goodsSellNum = document.getElementById("goodsSellNum" + btn.bid);
                imgInput.src="";
                goodsName.innerHTML="商品名称";
                goodsSellNum.innerHTML="";
            } else {
                var perItem= data.data.listObject;
                for(var i=0;i<data.data.listObject.length;i++){
                    var item=perItem[i];
                    if (i == 0) {
                        productId = item.id;
                        productName = item.name;
                        imgUrl = item.imgUrl;
                        sales = item.sales;

                    }
                    var perOption = document.createElement("option");
                    perOption.value = item.id;
                    perOption.innerHTML = item.name;
                    goodsBrandSelect.appendChild(perOption);
                }

                showGoodsDetail(productId, productName, imgUrl, sales, btn.bid);

            }


        },

    });
}


function actGoodsSelect(btn) {
    var productId = btn.value;
    var goodsName;
    var goodsImg;
    var saleNum;
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/product/" + productId,
        type: "GET",
        data: {
            storeId: storeId.toString(),
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            var goodDetail = data.data;
            goodsName = goodDetail.name;
            goodsImg = goodDetail.productGoodDisplay[0].url;
            $.ajax({
                url: "https://we.chenhaonee.cn/v1/product/sales/" + productId,
                type: "GET",
                dataType: 'JSON',
                async: false,
                success: function (data) {
                    saleNum=data.data;
                    showGoodsDetail(productId, goodsName, goodsImg, saleNum, btn.bid);
                }
            });
        }


    });
}

//在每个框框内呈现商品详情(或者更改)
function showGoodsDetail(productId, productName, imgUrl, sales, saleNum) {

    //alert("product id is" +productId);
    //alert("img id is " +"imgInput"+saleNum);
    var closeBtn = document.getElementById("c" + saleNum);
    //alert("c" + saleNum);
    closeBtn.pid = productId;
    var imgInput = document.getElementById("imgInput" + saleNum);
    var goodsName = document.getElementById("goodsName" + saleNum);
    var goodsSellNum = document.getElementById("goodsSellNum" + saleNum);
    //alert(imgInput.type);
    imgInput.src = imgUrl;
    goodsName.innerHTML = productName;
    goodsSellNum.innerHTML = sales;

    var perIndex = document.getElementById("dc" + saleNum);
    perIndex.cid = productId;

}


var specList = [];
var speecList = [];
var speeecList = [];
var newUnitPriceList = [];
var newAvePriceList = [];
var newSetAsPromoteProductList = [];

function showPriceDetail(btn) {
    //alert(btn.sale);
    productId = btn.cid;
    var submitPriceBtn = document.getElementById("submitPriceBtn");
    submitPriceBtn.pid=productId;
    //alert(btn.cid);
    var noticeImg = document.getElementById("noticeImg");
    var noticeName = document.getElementById("noticeName");

    var newCategoryDiv = document.getElementById("newCategoryDiv");
    newCategoryDiv.innerHTML = "";

    specList = [];
    speecList = [];
    speeecList = [];
    newUnitPriceList = [];
    newAvePriceList = [];
    newSetAsPromoteProductList = [];

    var imgInput = document.getElementById("imgInput" + btn.bid);
    var goodsName = document.getElementById("goodsName" + btn.bid);
    noticeImg.src = imgInput.src;

    noticeName.innerHTML = goodsName.innerHTML;

    var priceDiv = document.getElementById("priceDiv");
    priceDiv.innerHTML = "";

    specificationList = [];
    unitPriceList = [];
    avePriceList = [];
    setAsPromoteProductList = [];

    $.ajax({
        url: "https://we.chenhaonee.cn/v1/product/root/spec/" + btn.cid,
        type: "GET",
        data: {
            storeId: storeId
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                //alert(item.sendPlace);
                var salePerPrice = document.createElement("div");
                salePerPrice.setAttribute("class", "sale-per-price");
                var priceP = document.createElement("p");
                priceP.setAttribute("class", "sale-per-price-p");
                priceP.innerHTML = item.sendPlace + "X" + item.userDefine + "X" + item.pack;

                var checkSale = document.createElement("input");
                checkSale.type = "checkbox";
                checkSale.setAttribute("class", "sale-per-price-checkbox");
                if (item.setAsPromoteProduct) {
                    checkSale.checked = "checked";
                }
                var unitPrice = document.createElement("input");
                var avePrice = document.createElement("input");
                unitPrice.setAttribute("class", "sale-per-price-input");
                avePrice.setAttribute("class", "sale-per-price-input");
                unitPrice.type = "text";
                avePrice.type = "text";
                unitPrice.placeholder = "现价";
                unitPrice.onKeyUp = "value=value.replace(/[^\d.]/g,'')";
                avePrice.placeholder = "市场价";
                avePrice.onKeyUp = "value=value.replace(/[^\d.]/g,'')";
                unitPrice.value = item.unitPrice;
                avePrice.value = item.averagePrice;


                salePerPrice.appendChild(priceP);
                salePerPrice.appendChild(checkSale);
                salePerPrice.appendChild(avePrice);
                salePerPrice.appendChild(unitPrice);

                specificationList.push(priceP);
                if (btn.sale == "true") {
                    //alert("是促销！");
                    unitPrice.readOnly = "readOnly";
                    avePrice.readOnly = "readOnly";
                    checkSale.disabled = "disabled";
                    var newSaleCategoryBtn = document.getElementById("newSaleCategoryBtn");
                    var submitPriceBtn = document.getElementById("submitPriceBtn");
                    newSaleCategoryBtn.style.display = "none";
                    submitPriceBtn.style.display = "none";
                } else {
                    var newSaleCategoryBtn = document.getElementById("newSaleCategoryBtn");
                    var submitPriceBtn = document.getElementById("submitPriceBtn");
                    newSaleCategoryBtn.style.display = "";
                    submitPriceBtn.style.display = "";
                    //alert("不是促销");
                }
                unitPriceList.push(unitPrice);
                avePriceList.push(avePrice);
                setAsPromoteProductList.push(checkSale);

                priceDiv.appendChild(salePerPrice);
            });


        },

    });


}

var newCategoryNum=0;
var specOneList=[];
var specTwoList=[];
var specThreeList=[];
var unitPriceList=[];
var averagePriceList=[];
var ifSaleList=[];


function newCategoryDiv() {
    //alert("newCategory");
    var newCategoryDiv = document.getElementById("newCategoryDiv");

    var salePerPrice = document.createElement("div");
    salePerPrice.setAttribute("class", "sale-per-price");
    var categoryName = document.createElement("input");
    categoryName.setAttribute("class", "sale-per-category-input");
    categoryName.placeholder = "规格1";
    var categoryNaame = document.createElement("input");
    categoryNaame.setAttribute("class", "sale-per-category-input");
    categoryNaame.placeholder = "规格2";
    var categoryNaaame = document.createElement("input");
    categoryNaaame.setAttribute("class", "sale-per-category-input");
    categoryNaaame.placeholder = "规格3";
    var ifSaleCheckBox = document.createElement("input");
    ifSaleCheckBox.type = "checkbox";
    ifSaleCheckBox.setAttribute("class", "sale-per-price-checkbox");
    ifSaleCheckBox.checked = "checked";
    var priceInput = document.createElement("input");
    priceInput.type = "text";
    priceInput.setAttribute("class", "sale-per-price-input");
    priceInput.placeholder = "现价";
    priceInput.onKeyUp = "value=value.replace(/[^\d.]/g,'')";
    var marketPriceInput = document.createElement("input");
    marketPriceInput.type = "text";
    marketPriceInput.setAttribute("class", "sale-per-price-input");
    marketPriceInput.placeholder = "市场价";
    marketPriceInput.onKeyUp = "value=value.replace(/[^\d.]/g,'')";


    salePerPrice.appendChild(categoryName);
    salePerPrice.appendChild(categoryNaame);
    salePerPrice.appendChild(categoryNaaame);
    salePerPrice.appendChild(ifSaleCheckBox);
    salePerPrice.appendChild(priceInput);
    salePerPrice.appendChild(marketPriceInput);

    specOneList.push(categoryName);
    specTwoList.push(categoryNaame);
    specThreeList.push(categoryNaaame);
    unitPriceList.push(priceInput);
    averagePriceList.push(marketPriceInput);
    ifSaleList.push(ifSaleCheckBox);

    specList.push(categoryName);
    speecList.push(categoryNaame);
    speeecList.push(categoryNaaame);
    newUnitPriceList.push(priceInput);
    newAvePriceList.push(marketPriceInput);
    newSetAsPromoteProductList.push(ifSaleCheckBox);

    newCategoryDiv.appendChild(salePerPrice);

    newCategoryNum++;

}


function submitPrice(btn) {
    var requestSpecificationBindPrice = new Object();
    var newSpecs = [];

    for (var i = 0; i < specificationList.length; i++) {
        var newSpecsObj = new Object();
        newSpecsObj.averagePrice = avePriceList[i].value;
        newSpecsObj.setAsPromoteProduct = setAsPromoteProductList[i].checked;
        newSpecsObj.specification = specificationList[i].innerHTML;
        newSpecsObj.unitPrice = unitPriceList[i].value;
        newSpecs.push(newSpecsObj);
    }
    for (var j = 0; j < specList.length; j++) {
        var newSpecsObj = new Object();
        newSpecsObj.averagePrice = newAvePriceList[j].value;
        newSpecsObj.setAsPromoteProduct = newSetAsPromoteProductList[j].checked;
        newSpecsObj.specification = specList[j].value + "X" + speecList[j].value + "X" + speeecList[j].value;
        newSpecsObj.unitPrice = newUnitPriceList[j].value;
        newSpecs.push(newSpecsObj);
    }

    //requestSpecificationBindPrice.newSpecs = newSpecs;
    //requestSpecificationBindPrice.productId = productId;
    //requestSpecificationBindPrice.storeId = storeId;

    $.ajax({
        url: "https://we.chenhaonee.cn/v1/promote/",
        type: "POST",
        data: {
            productId: productId,
            specsString: JSON.stringify(newSpecs),
            //requestSpecificationBindPrice: JSON.stringify(requestSpecificationBindPrice)
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            alert("提交成功！");
            location.reload();
        },
        error: function (err) {
            alert("提交失败，请重试！");
        }
    });

    //addSpecfication(btn);
}

//提交新增属性  已弃用  因为上面你的方法可以直接提交
function addSpecfication(btn) {
    //alert(btn.pid);



    for(var i =0;i<newCategoryNum;i++){
        $.ajax({
            url: "https://we.chenhaonee.cn/v1/product/"+btn.pid+"/addSpecification",
            type: "POST",
            data: {
                sendPlace:specOneList[i].value,
                userDefine:specTwoList[i].value,
                pack:specThreeList[i].value,
                isPromote:ifSaleList[i].checked,
                unitPrice:unitPriceList[i].value,
                averagePrice:averagePriceList[i].value,
            },
            dataType: 'JSON',
            async: false,
            success: function (data) {
                alert("提交成功！");
                location.reload();
            }
        });
    }


}
var addGoodsBtn = document.getElementById("add-goods-btn-text");
var addPropertyBtn = document.getElementById("add-property-btn-text");
var num = 2;
var saleNum = 2;

function toNewIndex() {
    //alert("come on!");
    ifEmpty();
}

//检查除了“店铺描述”以外的输入框是否为空
function ifEmpty() {
    var ifEmpty = false;
    var inputList = [];
    var shopBackground = document.getElementById("88");
    var shopHead = document.getElementById("99");
    var shopName = document.getElementById("shopName");
    var shopAddress = document.getElementById("shopAddress");
    var shopTel = document.getElementById("shopTel");
    var shopWechat = document.getElementById("shopWechat");
    inputList.push(shopBackground);
    inputList.push(shopHead);
    inputList.push(shopName);
    inputList.push(shopAddress);
    inputList.push(shopTel);
    inputList.push(shopWechat);
    for (var i = 0; i < inputList.length; i++) {
        if (inputList[i].value == "") {
//                alert("it is empty!");
            ifEmpty = true;
        }
    }
    if (ifEmpty) {
        alert("请填写除了'商店描述'以外的而全部信息");
    } else {
        alert("不错，都填了！");
        //TODO 提交主页信息
    }

}

function newSale() {
    var addNewProperty = document.getElementById("allDivs");

    var perIndex = document.createElement("div");
    perIndex.setAttribute("class", "index-management-per-div");
    perIndex.id = "dc" + num;
    perIndex.bid = num;
    perIndex.sale = "false";
    perIndex.onclick = function () {
        showPriceDetail(this);
    }
    var indexP = document.createElement("p");
    indexP.setAttribute("class", "add-goods-head");
    indexP.innerHTML = "设置首页促销商品";

    var closeBtn = document.createElement("button");
    closeBtn.setAttribute("class", "add-goods-close-btn");
    closeBtn.sale = "false";
    closeBtn.onclick = function () {
        closeThis(this);
    }
    closeBtn.id = "c" + saleNum;
    //alert( closeBtn.id);
    closeBtn.innerHTML = "X";

    var goodDiv = document.createElement("div");
    goodDiv.setAttribute("class", "suit-index-management");

    var imgInput = document.createElement("img");
    var goodsName = document.createElement("h3");
    goodsName.innerHTML = "羊奶粉"
    var goodsSellR = document.createElement("h4");
//        var deco = document.createElement("h1");
    goodsSellR.innerHTML = "件";
    var goodsSellL = document.createElement("h4");
    goodsSellL.innerHTML = "共售出";
    var goodsSellNum = document.createElement("h2");
    goodsSellNum.innerHTML = "0";

    imgInput.id = "imgInput" + saleNum;
    goodsName.id = "goodsName" + saleNum;
    goodsSellNum.id = "goodsSellNum" + saleNum;

//        alert(imgInput.id);
    goodDiv.appendChild(imgInput);
    goodDiv.appendChild(goodsName);
//        goodDiv.appendChild(deco);
    goodDiv.appendChild(goodsSellR);
    goodDiv.appendChild(goodsSellNum);
    goodDiv.appendChild(goodsSellL);
    var selectCategory = document.createElement("select");
    selectCategory.setAttribute("class", "index-management-select");
    selectCategory.bid = saleNum;
//        for (var i = 0; i < 10; i++) {
//            var op = document.createElement("option");      // 新建OPTION (op)
//            op.setAttribute("value", i);          // 设置OPTION的 VALUE
//            op.appendChild(document.createTextNode("尿不湿")); // 设置OPTION的 TEXT
//            selectCategory.appendChild(op);           // 为SELECT 新建一 OPTION(op)
//        }
    selectCategory.onchange = function () {
        changeGoodsMiddleCategory(this);
    }

    var selectMiddleCategory = document.createElement("select");
    selectMiddleCategory.setAttribute("class", "index-management-select");
    selectMiddleCategory.id = "goodsMiddleCategorySelect" + saleNum;
    selectMiddleCategory.bid = saleNum;

    selectMiddleCategory.onchange = function () {
        changeGoods(this);
    }

    var selectGoods = document.createElement("select");
    selectGoods.setAttribute("class", "index-management-select");
    selectGoods.id = "goodsBrandSelect" + saleNum;
    selectGoods.bid = saleNum;

    selectGoods.onchange = function () {
        actGoodsSelect(this);
    };
    perIndex.appendChild(indexP);
    perIndex.appendChild(closeBtn);

    perIndex.appendChild(selectCategory);
    perIndex.appendChild(selectMiddleCategory);
    perIndex.appendChild(selectGoods);

    perIndex.appendChild(goodDiv);

    addNewProperty.appendChild(perIndex);
    getGoodsCategroy(saleNum, selectCategory, selectMiddleCategory, selectGoods);
    num++;
    saleNum++;

}

function newCategory() {
    var modifyCategory = document.getElementById("modifyCategory");
    var newInput = document.createElement("input");
    newInput.type = "text";
    modifyCategory.appendChild(newInput);

}

//    function showGoodsDetail() {
//        alert("goods!");
//    }

function closeThis(thisDiv) {
    var productId = thisDiv.pid;
    //alert("hhh");
    //alert("product id is "+thisDiv.pid);
    if (confirm("你确定删除该促销商品吗？")) {
        //DO SOMETHING
        //alert("id to delete " + productId);
        if (thisDiv.sale == "true") {
            $.ajax({
                url: "https://we.chenhaonee.cn/v1/promote/" + productId,
                type: "DELETE",
                //data: {
                //    productId: productId
                //},
                dataType: 'JSON',
                contentType: "application/json; charset=utf-8",
                async: false,
                success: function (data) {
                    //alert("删除成功！");
                    saleNum--;
                    var alldiv = document.getElementById("allDivs");
                    var toRemove = document.getElementById("d" + thisDiv.id);
                    alldiv.removeChild(toRemove);
                },
                error: function (err) {
                    alert("删除失败，请重试！");
                }
            });
        } else {
            saleNum--;
//        alert("closing..."+thisDiv.id);
            var alldiv = document.getElementById("allDivs");
            var toRemove = document.getElementById("d" + thisDiv.id);
//        alert(toRemove.id);
            alldiv.removeChild(toRemove);
        }


    }
    else {
        //DO NOTHING
    }


}


