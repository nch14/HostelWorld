/**
 * Created by mr.meng on 17/2/5.
 */

function initAny() {
//        alert("init!!!");
}

$(document).ready(function () {
//        alert("!!!");
    //初始化界面
    initInputs();
    //敲击按键时触发
    limitInput();
});
//限制键盘只能输入数字和一个小数点
function limitInput() {
    $("input").bind("keypress", function (event) {
        var event = event || window.event;
        var getValue = $(this).val();
        //控制第一个不能输入小数点"."
        if (getValue.length == 0 && event.which == 46) {
//                alert(1)
            event.preventDefault();
            return;
        }
        //控制只能输入一个小数点"."
        if (getValue.indexOf('.') != -1 && event.which == 46) {
            event.preventDefault();
//                alert(1)
            return;
        }
        //控制只能输入的值
        if (event.which && (event.which < 48 || event.which > 57) && event.which != 8 && event.which != 46) {
            event.preventDefault();
            return;
        }
    })
    //失去焦点时触发
    $("input").bind("blur", function (event) {
        var value = $(this).val(), reg = /\.$/;
        if (reg.test(value)) {
            value = value.replace(reg, "");
            $(this).val(value);
        }
    })
}

var specNum = 0;  //规格数量，是为了方便将所有规格上交
var goodsId = 1;  //货物Id ，为了提交价格
var storeId = 1;

function initInputs() {

//        alert("initInputs!");

    //读取url中的goodsId
    var url = location.href;
    var tmp1 = url.split("?")[1];
    var tmp2 = tmp1.split("&")[0];
    var tmp3 = tmp2.split("=")[1];
//        var tmp4 = tmp3.split("#")[0];
    goodsId = tmp3;
    //alert("goods id is "+goodsId);
    var allSpecificationList = [];
    var priceInputDiv = document.getElementById("priceInputDiv");
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/product/root/spec/" + goodsId,
        type: "GET",
        data: {
            storeId: storeId
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {


                var perSpecification = item.sendPlace + "X" + item.userDefine + "X" + item.pack;
                //新建规格输入框


                var perPriceDiv = document.createElement("div");
                perPriceDiv.setAttribute("class", "price-per-input");
                var perPriceP = document.createElement("p");
                perPriceP.setAttribute("class", "new-price-per-p");
                perPriceP.id = "h" + specNum;
                perPriceP.innerHTML = perSpecification;
                var realPrice = document.createElement("input");
                realPrice.setAttribute("class", "new-price-per-input");
                realPrice.type = "text";
                realPrice.placeholder = "单价";
                realPrice.onKeyUp = "value=value.replace(/[^\d.]/g,'')";
                realPrice.id = "r" + specNum;
                realPrice.value = item.unitPrice

                var marketPrice = document.createElement("input");
                marketPrice.setAttribute("class", "new-price-per-input");
                marketPrice.type = "text";
                marketPrice.placeholder = "市场价";
                marketPrice.onKeyUp = "value=value.replace(/[^\d.]/g,'')";
                marketPrice.id = "m" + specNum;
                marketPrice.value = item.averagePrice;

                var ifSale = document.createElement("input");
                ifSale.setAttribute("class", "ifSale-input");
                ifSale.id = "s" + specNum;
                ifSale.type = "checkbox";
                ifSale.sid = item.id;
                //alert(item.setAsPromoteProduct);
                if(item.setAsPromoteProduct){
                    ifSale.checked ="checked";
                }

                ifSale.onclick = function () {
//                                alert("select!");
                }
                var saleP = document.createElement("p");
                saleP.setAttribute("class", "new-sale-unit-p");
                saleP.innerHTML = "促销";
                saleP.id = "sp" + specNum;
                var ifKeep = document.createElement("input");
                ifKeep.setAttribute("class", "ifSale-input");
                ifKeep.id = specNum;
                ifKeep.type = "checkbox";
                ifKeep.cid = item.id;
                ifKeep.onclick = function () {
                    disableSpecification(this);
                }
                var ifKeepP = document.createElement("p");
                ifKeepP.setAttribute("class", "new-sale-unit-p");
                ifKeepP.innerHTML = "删除";
                ifKeepP.id = "ik" + specNum;
                var unitPL = document.createElement("p");
                unitPL.setAttribute("class", "new-price-unit-p");
                unitPL.id = "pl" + specNum;
                unitPL.innerHTML = "元";
                var unitPR = document.createElement("p");
                unitPR.setAttribute("class", "new-price-unit-p");
                unitPR.id = "pr" + specNum;
                unitPR.innerHTML = "元";

                perPriceDiv.appendChild(ifKeepP);
                perPriceDiv.appendChild(ifKeep);
                perPriceDiv.appendChild(perPriceP);
                perPriceDiv.appendChild(realPrice);
                perPriceDiv.appendChild(unitPL);
                perPriceDiv.appendChild(marketPrice);
                perPriceDiv.appendChild(unitPR);
                perPriceDiv.appendChild(ifSale);
                perPriceDiv.appendChild(saleP);
                priceInputDiv.appendChild(perPriceDiv);


//                            allSpecificationList.push(perSpecification);
                specNum++;

//                alert("规格列表！" + allSpecificationList);
            });
        },

    });

//        alert("finish!");
}

//    function SpecificationBindPrice(unitPrice,averagePrice,setAsPromoteProduct,specification){
//      this.averagePrice=averagePrice;
//        this.unitPrice = unitPrice;
//        this.setAsPromoteProduct=setAsPromoteProduct;
//        this.specification=specification;
//    }

function subGoodsPrice() {
    var specificationBindPricesList = [];

    for (var i = 1; i < specNum; i++) {
        var ifKeepCheckBox = document.getElementById(i);
        if (ifKeepCheckBox.checked) {
            alert("删掉这个规格");
            $.ajax({
                url: "https://we.chenhaonee.cn/v1/product/specification/" + ifKeepCheckBox.cid,
                type: "GET",

                dataType: 'JSON',
                async: false,
                success: function (data) {
                    alert("删除成功！");
                }
            });
        }
        if (!ifKeepCheckBox.checked) {
//                alert(ifKeepCheckBox.checked);

            var SpecificationBindPrice = new Object();
//            alert("i is " + i);
            var ifSaleCheck = document.getElementById("s" + i);
            var realPrice = document.getElementById("r" + i);
            var marketPrice = document.getElementById("m" + i);
            var specificationP = document.getElementById("h" + i);

            var realPriceValue = realPrice.value;
            var marketPriceValue = marketPrice.value;
            var shipAddressId = realPrice.shipAddressId;
            var specificationId = realPrice.specificationId;
            var packId = realPrice.packId;

            SpecificationBindPrice.unitPrice = realPriceValue;
            SpecificationBindPrice.averagePrice = marketPriceValue;
            SpecificationBindPrice.setAsPromoteProduct = ifSaleCheck.checked;
//            alert(specificationP.innerHTML);
            SpecificationBindPrice.specification = specificationP.innerHTML;


//            alert(SpecificationBindPrice.unitPrice);
//            alert(SpecificationBindPrice.averagePrice);
//            alert(SpecificationBindPrice.setAsPromoteProduct);
//            alert(SpecificationBindPrice.specification);
//            alert(specificationBindPrices.setAsPromoteProduct);
//            specificationBindPricesList[i]=new SpecificationBindPrice(realPriceValue,marketPriceValue,ifSaleCheck.checked,specificationP.innerHTML);
            specificationBindPricesList.push(SpecificationBindPrice);
        }

//            alert("shipAddressId is "+shipAddressId);
//            alert(" is "+realPriceInput.shipAddressId);
//            alert("i is "+i);
    }

//        alert("finish!");

//        alert(specificationBindPricesList[11].unitPrice);
//        alert(specificationBindPricesList[11].averagePrice);
//        alert(specificationBindPricesList[11].setAsPromoteProduct);
//        alert(specificationBindPricesList[11].specification);


    $.ajax({
        url: "https://we.chenhaonee.cn/v1/product/price",
        type: "POST",
        data: {
            storeId: 1,
            productId: goodsId,
//                specificationBindPrices: specificationBindPricesList
            specificationBindPricesValue: JSON.stringify(specificationBindPricesList)
        },
        dataType: 'JSON',
//            contentType: "application/json",
        async: false,
        success: function (data) {
            window.location.href = "../customerIndex.html";
            alert("success");
//                    alert("规格列表！" + allSpecificationList);
        }


    });

//        subSaleGoods();

}
function subSaleGoods() {
    for (var i = 1; i < specNum; i++) {
        //alert("i is " + i);
        var ifSaleCheck = document.getElementById("s" + i);
        if (ifSaleCheck.checked) {
            var realPriceInput = document.getElementById("r" + i);
            var marketPriceInput = document.getElementById("m" + i);
            var realPriceInput = realPriceInput.value;
            var marketPriceInput = marketPriceInput.value;
            var shipAddressId = realPriceInput.shipAddressId;
            var specificationId = realPriceInput.specificationId;
            var packId = realPriceInput.packId;

            $.ajax({
                url: "https://we.chenhaonee.cn/v1/promote/",
                type: "POST",
                data: {
                    storeId: 0,
                    productId: goodsId,
                    shipAddressId: shipAddressId,
                    specificationId: specificationId,
                    packId: packId,
                },
                dataType: 'JSON',
                async: false,
                traditional: true,
                success: function (data) {
                    alert("提交促销！");

//                    alert("规格列表！" + allSpecificationList);

                }


            });

        }


//            alert("shipAddressId is "+shipAddressId);
//            alert(" is "+realPriceInput.shipAddressId);
//
    }
}

function disableSpecification(btn) {
    var i = btn.id;
    var realPriceInput = document.getElementById("r" + i);
    var marketPriceInput = document.getElementById("m" + i);

    var ifSaleCheck = document.getElementById("s" + i);

    var head = document.getElementById("h" + i);
    var pl = document.getElementById("pl" + i);
    var pr = document.getElementById("pr" + i);
    var ik = document.getElementById("ik" + i);
    var sp = document.getElementById("sp" + i);
    if (btn.checked) {


        realPriceInput.disabled = "true";
        marketPriceInput.disabled = "true";

        ifSaleCheck.disabled = "true";

        head.style.color = "darkgrey";
        pl.style.color = "darkgrey";
        pr.style.color = "darkgrey";
        ik.style.color = "darkgrey";
        sp.style.color = "darkgrey";
    } else {
        realPriceInput.removeAttribute("disabled");
        marketPriceInput.removeAttribute("disabled");

        ifSaleCheck.removeAttribute("disabled");

        head.style.color = "black";
        pl.style.color = "black";
        pr.style.color = "black";
        ik.style.color = "black";
        sp.style.color = "black";

    }
}


function disableNewSpecification(btn) {
    var i = btn.id;
    var realPriceInput = document.getElementById("r" + i);
    var marketPriceInput = document.getElementById("m" + i);

    var ifSaleCheck = document.getElementById("s" + i);

    var one = document.getElementById("one" + i);
    var two = document.getElementById("two" + i);
    var three = document.getElementById("three" + i);
    var pl = document.getElementById("pl" + i);
    var pr = document.getElementById("pr" + i);
    var ik = document.getElementById("ik" + i);
    var sp = document.getElementById("sp" + i);
    if (btn.checked) {


        realPriceInput.disabled = "true";
        marketPriceInput.disabled = "true";

        ifSaleCheck.disabled = "true";

        one.style.color = "darkgrey";
        two.style.color = "darkgrey";
        three.style.color = "darkgrey";
        pl.style.color = "darkgrey";
        pr.style.color = "darkgrey";
        ik.style.color = "darkgrey";
        sp.style.color = "darkgrey";
    } else {
        realPriceInput.removeAttribute("disabled");
        marketPriceInput.removeAttribute("disabled");

        ifSaleCheck.removeAttribute("disabled");

        one.style.color = "darkgrey";
        two.style.color = "darkgrey";
        three.style.color = "darkgrey";
        pl.style.color = "black";
        pr.style.color = "black";
        ik.style.color = "black";
        sp.style.color = "black";

    }
}

var addCategoryNum = 0;

var ifKeepList = [];
var specList = [];
var speecList = [];
var speeecList = [];
var newUnitPriceList = [];
var newAvePriceList = [];
var newSetAsPromoteProductList = [];

function newCategoryPrice() {
    var priceInputDiv = document.getElementById("priceInputDiv");
    var perSpecification = document.createElement("span");
    perSpecification.setAttribute("class", "new-category-per-p");
    var categoryOne = document.createElement("input");
    var categoryTwo = document.createElement("input");
    var categoryThree = document.createElement("input");
    categoryOne.setAttribute("class", "newCategory-input");
    categoryTwo.setAttribute("class", "newCategory-input");
    categoryThree.setAttribute("class", "newCategory-input");
    categoryOne.id = "one" + specNum;
    categoryTwo.id = "two" + specNum;
    categoryThree.id = "three" + specNum;

    categoryOne.placeholder = "规格1";
    categoryTwo.placeholder = "规格2";
    categoryThree.placeholder = "规格3";
    var firstX = document.createElement("p");
    var secondX = document.createElement("p");
    firstX.setAttribute("class", "new-category-unit-p");
    secondX.setAttribute("class", "new-category-unit-p");
    firstX.innerHTML = "X";
    secondX.innerHTML = "X";

    perSpecification.appendChild(categoryOne);
    perSpecification.appendChild(firstX);
    perSpecification.appendChild(categoryTwo);
    perSpecification.appendChild(secondX);
    perSpecification.appendChild(categoryThree);


    var perPriceDiv = document.createElement("div");
    perPriceDiv.setAttribute("class", "price-per-input");

    var realPrice = document.createElement("input");
    realPrice.setAttribute("class", "new-price-per-input");
    realPrice.type = "text";
    realPrice.placeholder = "单价";
    realPrice.onKeyUp = "value=value.replace(/[^\d.]/g,'')";
    realPrice.id = "r" + specNum;

    var marketPrice = document.createElement("input");
    marketPrice.setAttribute("class", "new-price-per-input");
    marketPrice.type = "text";
    marketPrice.placeholder = "市场价";
    marketPrice.onKeyUp = "value=value.replace(/[^\d.]/g,'')";
    marketPrice.id = "m" + specNum;

    var ifSale = document.createElement("input");
    ifSale.setAttribute("class", "ifSale-input");
    ifSale.id = "s" + specNum;
    ifSale.type = "checkbox";
    ifSale.onclick = function () {
//                                alert("select!");
    }
    var saleP = document.createElement("p");
    saleP.setAttribute("class", "new-sale-unit-p");
    saleP.innerHTML = "促销";
    saleP.id = "sp" + specNum;
    var ifKeep = document.createElement("input");
    ifKeep.setAttribute("class", "ifSale-input");
    ifKeep.id = specNum;
    ifKeep.type = "checkbox";
    ifKeep.onclick = function () {
        disableNewSpecification(this);
    }
    var ifKeepP = document.createElement("p");
    ifKeepP.setAttribute("class", "new-sale-unit-p");
    ifKeepP.innerHTML = "删除";
    ifKeepP.id = "ik" + specNum;
    var unitPL = document.createElement("p");
    unitPL.setAttribute("class", "new-price-unit-p");
    unitPL.id = "pl" + specNum;
    unitPL.innerHTML = "元";
    var unitPR = document.createElement("p");
    unitPR.setAttribute("class", "new-price-unit-p");
    unitPR.id = "pr" + specNum;
    unitPR.innerHTML = "元";

    ifKeepList.push(ifKeep);
    specList.push(categoryOne);
    speecList.push(categoryTwo);
    speeecList.push(categoryThree);
    newUnitPriceList.push(realPrice);
    newAvePriceList.push(marketPrice);
    newSetAsPromoteProductList.push(ifSale);

    perPriceDiv.appendChild(ifKeepP);
    perPriceDiv.appendChild(ifKeep);
    perPriceDiv.appendChild(perSpecification);
    perPriceDiv.appendChild(realPrice);
    perPriceDiv.appendChild(unitPL);
    perPriceDiv.appendChild(marketPrice);
    perPriceDiv.appendChild(unitPR);
    perPriceDiv.appendChild(ifSale);
    perPriceDiv.appendChild(saleP);
    priceInputDiv.appendChild(perPriceDiv);


//                            allSpecificationList.push(perSpecification);
    specNum++;
    addCategoryNum++;

}


function subAddCategory() {
    //ifInputNull();
    if(addCategoryNum>0){
        for (var i = 0; i < addCategoryNum; i++) {
            if (!ifKeepList[i].checked) {
                $.ajax({
                    url: "https://we.chenhaonee.cn/v1/product/" + goodsId + "/addSpecification",
                    type: "POST",
                    data: {
                        sendPlace: specList[i].value,
                        userDefine: speecList[i].value,
                        pack: speeecList[i].value,
                        isPromote: newSetAsPromoteProductList[i].checked,
                        unitPrice: newUnitPriceList[i].value,
                        averagePrice: newAvePriceList[i].value,
                    },
                    dataType: 'JSON',
                    async: false,
                    success: function (data) {
                        alert("新增规格提交成功！");
                        subModifyCategory();
                    }
                });
            } else {
                //DO NOTHING
            }
        }
    }else{
        subModifyCategory();
    }

}

function subModifyCategory() {
    for (var a = 0; a < (specNum - addCategoryNum); a++) {
        var ifKeepSelect = document.getElementById(a);
        if (ifKeepSelect.checked) {//删除商品
            var specId= ifKeepSelect.cid;
            $.ajax({
                url: "https://we.chenhaonee.cn/v1/product/specification/"+specId,
                type: "PUT",
                dataType: 'JSON',
                async: false,
                success: function (data) {
                    alert("删除成功！");
                    //location.reload();
                },
                error: function (err) {
                    alert("删除失败，请重试！");
                }
            });
        }else{
            var ifSaleSelect = document.getElementById("s" + a);
            var salePrice = document.getElementById("r" + a);
            var marketPriceInput = document.getElementById("m" + a);
            //alert("ifsale "+ifSaleSelect.checked);

            $.ajax({
                url: "https://we.chenhaonee.cn/v1/product/"+goodsId+"/update",
                type: "PUT",
                data: {
                    specId:ifKeepSelect.cid,
                    isPromote:ifSaleSelect.checked,
                    unitPrice:salePrice.value,
                    averagePrice:marketPriceInput.value,
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

        }

    }
}

function ifInputNull() {
    var itxt = document.getElementsByTagName("input");
    var b = 0;
    for (h = 0; h < itxt.length; h++) {
        var atype = itxt[h].type;
        if (atype == "text" && itxt[h].value=="") {
            alert('您有尚未填写的价格/规格');
            b=1;
        }

    }
    //alert("b = "+b);
    if(b==0){
        //alert("提交吧！");
        subAddCategory();
    }


    //alert("yes");
}