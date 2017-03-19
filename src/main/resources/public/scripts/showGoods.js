/**
 * Created by mr.meng on 17/1/2.
 */
$(document).ready(function () {
    //alert("初始化！");
    getGoodsCategroy();

});

var storeId=1;
var productId;
//解析url获取商品详情
function getGoodsDetail() {

    var url = location.href;
    var tmp1 = url.split("?")[1];
    var tmp2 = tmp1.split("&")[0];
    var tmp3 = tmp2.split("=")[1];
    var tmp4 = tmp3.split("#")[0];
    productId = tmp4;

    //界面上该显示的东西
    var goodsName = document.getElementById("goodsName");
    var goodsImgList=[];
    var goodsCategory;
    var goodsMiddleCategory;
    var goodsBrand;
    var goodsShipAddress = document.getElementById("goodsOrigin");
    var properties=[];
    var propertyOne;
    var propertyTwo;
    var propertyThree;
    var goodsDetailList=[];




    $.ajax({
        url: "http://we.chenhaonee.cn:8443/v1/product/" + productId,
        type: "GET",
        dataType: 'JSON',
        data:{
            storeId:storeId
        },
        async: false,
        success: function (data) {
            goodsName.value=data.data.name;
            goodsShipAddress.value=data.data.origin;
            for(var i =1; i<data.data.productGoodDisplay.length+1;i++){
                var goodsImg = document.getElementById("image"+i);
                goodsImg.src=data.data.productGoodDisplay[i-1].url;
            }
            for(var j =1; j<data.data.productAdvertisingDetails.length+1;j++){
                var adImg = document.getElementById("image1"+j);
                adImg.src=data.data.productAdvertisingDetails[j-1].url;
            }
            for(var a =0; a<data.data.properties.length;a++){
                //alert("a is "+a);
                //alert("properties length is "+data.data.properties.length);
                var addNewProperty = document.getElementById("addNewProperty");
                var newProductPerDiv = document.createElement("div");
                newProductPerDiv.setAttribute("class", "new-product-per-div");
                var inputLeft = document.createElement("input");
                var inputRight = document.createElement("input");
                inputLeft.type = "text";
                inputRight.type = "text";
                inputLeft.setAttribute("class", "new-product-per-input-left");
                inputRight.setAttribute("class", "new-product-per-input");
                inputLeft.placeholder = "属性名称";
                inputLeft.value=data.data.properties[a].pKey;
                inputLeft.id = "inputLeft" + npdNum;
                //alert("inputLeft id is "+inputLeft.id);
                inputRight.placeholder = "属性内容";
                inputRight.value=data.data.properties[a].pValue;
                inputRight.id = "inputRight" + npdNum;
                //alert("inputRight id is "+inputRight.id);
                newProductPerDiv.appendChild(inputLeft);
                newProductPerDiv.appendChild(inputRight);
                //newProductPerDiv.id="npd"+npdNum;
                npdNum++;
                addNewProperty.appendChild(newProductPerDiv);

            }

            //goodsCategory.value=data.data.



        },
    });




    //getGoodsCategroy();
}


//初始化一级分类下拉框
function getGoodsCategroy() {

    var goodsCategorySelect = document.getElementById("goodsCategorySelect");
    var goodsCategorySelectNew = document.getElementById("goodsCategorySelectNew");
    var goodsCategorySelectNewTwo = document.getElementById("goodsCategorySelectNewTwo");
    goodsCategorySelect.innerHTML = "";
    goodsCategorySelectNew.innerHTML = "";
    goodsCategorySelectNewTwo.innerHTML = "";
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/productCategory",
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                if (idx == 0) {
                    getGoodsMiddleCategory(item.id);
                    getGoodsBrand(item.id);
                }
                var perOption = document.createElement("option");
                perOption.value = item.id;
                perOption.innerHTML = item.name;
                var perOptionNew = document.createElement("option");
                perOptionNew.value = item.id;
                perOptionNew.innerHTML = item.name;
                var perOptionNewTwo = document.createElement("option");
                perOptionNewTwo.value = item.id;
                perOptionNewTwo.innerHTML = item.name;
                goodsCategorySelect.appendChild(perOption);
                goodsCategorySelectNew.appendChild(perOptionNew);
                goodsCategorySelectNewTwo.appendChild(perOptionNewTwo);

            });
            //changeShow(categoryId);
        },
    });
}

//初始化二级分类下拉框
function getGoodsMiddleCategory(categoryId) {
    //alert("获取二级分类！"+categoryId);
    var goodsMiddleCategorySelect = document.getElementById("goodsMiddleCategorySelect");
    goodsMiddleCategorySelect.innerHTML = "";
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/productCategory/" + categoryId,
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                var perOption = document.createElement("option");
                perOption.value = item.id;
                perOption.innerHTML = item.name;
                goodsMiddleCategorySelect.appendChild(perOption);
            });


        },
    });
}

//初始化品牌下拉框
function getGoodsBrand(categoryId) {
    var goodsBrandSelect = document.getElementById("goodsBrandSelect");
    goodsBrandSelect.innerHTML = "";
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/brand/" + categoryId,
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                var perOption = document.createElement("option");
                perOption.value = item.id;
                perOption.innerHTML = item.name;
                goodsBrandSelect.appendChild(perOption);
            });

            getGoodsDetail();
        },
    });
}

//初始化二级分类下拉框
function changeGoodsMiddleCategory(btn) {
    //alert("获取二级分类！"+btn.value);
    var goodsMiddleCategorySelect = document.getElementById("goodsMiddleCategorySelect");
    goodsMiddleCategorySelect.innerHTML = "";
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/productCategory/" + btn.value,
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                var perOption = document.createElement("option");
                perOption.value = item.id;
                perOption.innerHTML = item.name;
                goodsMiddleCategorySelect.appendChild(perOption);
            });


        },
    });
    changeGoodsBrand(btn);
}

//初始化品牌下拉框
function changeGoodsBrand(btn) {

    var goodsBrandSelect = document.getElementById("goodsBrandSelect");
    goodsBrandSelect.innerHTML = "";
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/brand/" + btn.value,
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                var perOption = document.createElement("option");
                perOption.value = item.id;
                perOption.innerHTML = item.name;
                goodsBrandSelect.appendChild(perOption);
            });


        },
    });
}


function subOrigin() {
    for (var i = 1; i < 9; i++) {
        var from = document.getElementById("o" + i);
        var to = document.getElementById(1 + "o" + i);
        to.value = from.value;
    }
}

function subWeight() {
    for (var i = 1; i < 9; i++) {
        var from = document.getElementById("w" + i);
        var to = document.getElementById(1 + "w" + i);
        to.value = from.value;
    }
}

function subPack() {
    for (var i = 1; i < 9; i++) {
        var from = document.getElementById("p" + i);
        var to = document.getElementById(1 + "p" + i);
        to.value = from.value;
    }
}


//以下开始是从界面上拷过来的

function toNewGoods() {
    //alert("come on!");
    uploadNewGoods();
}


function uploadNewGoods() {


    //alert("uploadNewGoods");
    var form = $("form[name=uploadGoodsForm]");
    var options = {
        url: 'https://we.chenhaonee.cn/v1/product',
        type: 'post',
        success: function (data) {
            //alert("success" + data.data.id);
            submitNewProperty(data.data.id);
            //TODO 下面这个而要放在上面的success中
        }
    };
    form.ajaxSubmit(options);


}


var npdNum = 0;
function newProperty() {
    var addNewProperty = document.getElementById("addNewProperty");
    var newProductPerDiv = document.createElement("div");
    newProductPerDiv.setAttribute("class", "new-product-per-div");
    var inputLeft = document.createElement("input");
    var inputRight = document.createElement("input");
    inputLeft.type = "text";
    inputRight.type = "text";
    inputLeft.setAttribute("class", "new-product-per-input-left");
    inputRight.setAttribute("class", "new-product-per-input");
    inputLeft.placeholder = "属性名称";
    inputLeft.id = "inputLeft" + npdNum;
    //alert("inputLeft id is "+inputLeft.id);
    inputRight.placeholder = "属性内容";
    inputRight.id = "inputRight" + npdNum;
    //alert("inputRight id is "+inputRight.id);

    newProductPerDiv.appendChild(inputLeft);
    newProductPerDiv.appendChild(inputRight);
    //newProductPerDiv.id="npd"+npdNum;
    npdNum++;
    addNewProperty.appendChild(newProductPerDiv);

}


var productExtraProperties = new Object();


//提交新增属性
function submitNewProperty(productId) {
    var productExtraPropertiesList = [];
    for (var i = 0; i < npdNum; i++) {
        //alert("get left "+"inputLeft"+i)
        //alert("get right "+"inputRight"+i)
        var newKey = document.getElementById("inputLeft" + i);
        var newValue = document.getElementById("inputRight" + i);
        productExtraProperties.key = newKey.value;
        productExtraProperties.value = newValue.value;
        productExtraProperties.pIndex = (i + 1).toString();
        productExtraPropertiesList.push(productExtraProperties);
        //alert(productExtraProperties.value);
        //alert(productExtraPropertiesList.length);


        $.ajax({
            url: "https://we.chenhaonee.cn/v1/product/op/" + productId,
            type: "POST",
            data: {
                productExtraPropertiesValue: JSON.stringify(productExtraPropertiesList)
            },
            dataType: 'JSON',
            async: false,
            success: function (data) {
                //$.each(data.data, function (idx, item) {
                //    var perOption = document.createElement("option");
                //    perOption.value = item.id;
                //    perOption.innerHTML = item.name;
                //    goodsBrandSelect.appendChild(perOption);
                //});
                toPrice(productId);


            },
        });

    }
}

function toPrice(goodsId) {
    //alert("提交成功！");
    window.location.href = "showGoods-SettingPrice.html?productId=" + goodsId;
}


function newCategory() {
    var modifyCategory = document.getElementById("modifyCategory");
    var newInput = document.createElement("input");
    newInput.type = "text";
    modifyCategory.appendChild(newInput);
}


function showNameNotice() {
//        alert("Show Name Notice!");
    noticeListInit();
    $("#nameNotice").addClass("show-anyway");
}


function showPicNotice() {
//        alert("Show Pic Notice!");
    noticeListInit();
    $("#picNotice").addClass("show-anyway");
}


function showCategoryNotice() {
//        alert("Show Category Notice!");
    noticeListInit();
    $("#categoryNotice").addClass("show-anyway");
}

function showGoodsMiddleCategoryNotice() {
    //alert("Show Middle Category Notice!");
    noticeListInit();
    $("#middleCategoryNotice").addClass("show-anyway");
}

function showBrandNotice() {
//        alert("Show Category Notice!");
    noticeListInit();
    $("#brandNotice").addClass("show-anyway");
}

function showShipAddressNotice() {
//        alert("Show Category Notice!");
    noticeListInit();
    $("#shipAddressNotice").addClass("show-anyway");
}

function showComingNotice() {
//        alert("Show Category Notice!");
    noticeListInit();
    $("#comingNotice").addClass("show-anyway");
}
function showWeightNotice() {
//        alert("Show Category Notice!");
    noticeListInit();
    $("#weightNotice").addClass("show-anyway");
}
function showPackageNotice() {
//        alert("Show Category Notice!");
    noticeListInit();
    $("#packageNotice").addClass("show-anyway");
}
function noticeListInit() {
    var noticeList = [];
    var nameNotice = document.getElementById("nameNotice");
    var picNotice = document.getElementById("picNotice");
    var categoryNotice = document.getElementById("categoryNotice");
    var middleCategoryNotice = document.getElementById("middleCategoryNotice");
    var brandNotice = document.getElementById("brandNotice");
//        var shipAddressNotice = document.getElementById("shipAddressNotice");
//    var weightNotice = document.getElementById("weightNotice");
//    var comingNotice = document.getElementById("comingNotice");
//    var packageNotice = document.getElementById("packageNotice");

    noticeList.push(nameNotice);
    noticeList.push(picNotice);
    noticeList.push(categoryNotice);
    noticeList.push(middleCategoryNotice);
    noticeList.push(brandNotice);
//        noticeList.push(shipAddressNotice);
//    noticeList.push(weightNotice);
//    noticeList.push(comingNotice);
//    noticeList.push(packageNotice);


    for (var i = 0; i < noticeList.length; i++) {
        noticeList[i].setAttribute("class", "product-notice-inside");
    }
}


function goodsNewCategory() {
    var form = $("form[name=goodsNewCategoryForm]");
    var options = {
        url: 'https://we.chenhaonee.cn/v1/productCategory',
        type: 'post',
        success: function (data) {
            alert("添加成功！");
            reLoad();
        }
    };
    form.ajaxSubmit(options);

}

function goodsNewMiddleCategory() {
    var form = $("form[name=goodsNewMiddleCategoryForm]");
    var options = {
        url: 'https://we.chenhaonee.cn/v1/productCategory',
        type: 'post',
        success: function (data) {
            alert("添加成功！");
            reLoad();
        }
    };
    form.ajaxSubmit(options);

}

function goodsNewBrand() {
    //alert("hahha");
    var form = $("form[name=goodsNewBrandForm]");
    var options = {
        url: 'https://we.chenhaonee.cn/v1/brand',
        type: 'post',
        success: function (data) {
            alert("添加成功！");
            reLoad();
        }
    };
    form.ajaxSubmit(options);

}

function reLoad() {
    getGoodsCategroy();
    //清空所有的noticeDiv
    var cateName = document.getElementById("cateName");
    var cateImg = document.getElementById("88");
    var cateImgSrc = document.getElementById("image88");

    var cateMName = document.getElementById("cateMName");
    var cateMImg = document.getElementById("21");
    var cateMImgSrc = document.getElementById("image21");

    var brandName = document.getElementById("brandName");
    var brandImg = document.getElementById("31");
    var brandImgSrc = document.getElementById("image31");

    cateName.value="";
    cateMName.value="";
    brandName.value="";

    cateImg.value="";
    cateMImg.value="";
    brandImg.value="";

    cateImgSrc.src="";
    cateMImgSrc.src="";
    brandImgSrc.src="";
}

function toSpecPrice(){
    //alert("111");
    window.location.href="showGoods-SettingPrice.html?productId="+productId;
}


function deleteGoods(){
    //利用对话框返回的值 （true 或者 false）
    if (confirm("你确定删除该商品吗？")) {
        $.ajax({
            url: "https://we.chenhaonee.cn/v1/product/" + productId,
            type: "DELETE",
            dataType: 'JSON',
            async: false,
            success: function (data) {
                window.location.href="../customerIndex.html";
                //$.each(data.data, function (idx, item) {
                //    var perOption = document.createElement("option");
                //    perOption.value = item.id;
                //    perOption.innerHTML = item.name;
                //    goodsBrandSelect.appendChild(perOption);
                //});
                //toPrice(prodecutId);


            },
        });
    }
    else {
        //DO NOTHING
    }
}

function modifyGoods(){
    //alert("抱歉。。暂时不能修改商品...");
}

//判断页面上是否有input为空
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