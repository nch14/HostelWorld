/**
 * Created by mr.meng on 17/1/7.
 */


function initAny(){
    //alert("init!!!");
}

$(document).ready(function () {
    //alert("!!!");
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

var specNum = 1;  //规格数量，是为了方便将所有规格上交
var goodsId = 1;  //货物Id ，为了提交价格

function initInputs() {

    //alert("initInputs!");

    //读取url中的goodsId
    var url = location.href;
    var tmp1 = url.split("?")[1];
//        var tmp2 = tmp1.split("&")[0];
//        var tmp3 = tmp2.split("=")[1];
//        var tmp4 = tmp3.split("#")[0];
    goodsId = tmp1;
    //alert("goods id is "+goodsId);
    var allSpecificationList = [];
    var priceInputDiv = document.getElementById("priceInputDiv");
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/product/possibleSpecification/" + goodsId,
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            var shipAddressesList = data.data.shipAddresses;
            var specificationList = data.data.specification;
            var packList = data.data.pack;

            for (var a = 0; a < shipAddressesList.length; a++) {
                for (var b = 0; b < specificationList.length; b++) {
                    for (var c = 0; c < packList.length; c++) {
                        var perSpecification = shipAddressesList[a] + "X" + specificationList[b] + "X" + packList[c];
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
                        realPrice.shipAddressId = shipAddressesList[a].id;

//                            alert("init id is "+realPrice.id);

                        realPrice.specificationId = specificationList[a].id;
                        realPrice.packId = packList[a].id;
                        realPrice.value = "3";

                        var marketPrice = document.createElement("input");
                        marketPrice.setAttribute("class", "new-price-per-input");
                        marketPrice.type = "text";
                        marketPrice.placeholder = "市场价";
                        marketPrice.onKeyUp = "value=value.replace(/[^\d.]/g,'')";
                        marketPrice.id = "m" + specNum;
                        marketPrice.value = "5";

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
                    }
                }
            }
//                alert("规格列表！" + allSpecificationList);

        },
    });

    //alert("finish!");
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
//            alert("shipAddressId is "+shipAddressId);
//            alert(" is "+realPriceInput.shipAddressId);
    }

    //alert(specificationBindPricesList[11].unitPrice);
    //alert(specificationBindPricesList[11].averagePrice);
    //alert(specificationBindPricesList[11].setAsPromoteProduct);
    //alert(specificationBindPricesList[11].specification);

    $.ajax({
        url: "https://we.chenhaonee.cn/v1/product/price",
        type: "POST",
        data: {
            storeId: 0,
            productId: goodsId,
//                specificationBindPrices: specificationBindPricesList
            specificationBindPrices: specificationBindPricesList
        },
        dataType: 'JSON',
//            contentType: "application/json",
        async: false,
        success: function (data) {
            alert("提交成功！");
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
                url: "https://we.chenhaonee.cn/v1/promote",
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
                    //alert("提交促销！");
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

