/**
 * Created by mr.meng on 17/1/2.
 */
$(document).ready(function () {
    //alert("初始化！");
    getGoodsCategroy();
});
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
    //先验证是否有空的情况出现
    var ifThereIsNull = false;
    var ifThereIsAdImg = true;
    var ifThereIsDetailImg = true;
    var ifThereIsOne = true;
    var ifThereIsTwo = true;
    var ifThereIsThree = true;
    var goodsNameInput = document.getElementById("goodsNameInput");
    var goodsCategorySelect = document.getElementById("goodsCategorySelect");
    var goodsMiddleCategorySelect = document.getElementById("goodsMiddleCategorySelect");
    var goodsBrandSelect = document.getElementById("goodsBrandSelect");
    var originInput = document.getElementById("originInput");
    if (goodsNameInput.value == "" || goodsCategorySelect.value == "" || goodsMiddleCategorySelect.value == "" ||
        goodsBrandSelect.value == "" || originInput.value == "") {
        alert("请将基本信息填充完整！");
        //alert("有空的！");
        ifThereIsNull = true;
    }
    for (var i = 1; i < 7; i++) {
        var imageGoods = document.getElementById(i);
        var imageDetail = document.getElementById("1" + i);
        if (imageGoods.value != "") {
            //alert("不是空的！");
            ifThereIsAdImg = false;
        }
        if (imageDetail.value != "") {
            //alert("不是空的！");
            ifThereIsDetailImg = false;
        }
    }
    if (ifThereIsAdImg) {
        alert("请至少添加一张商品图片！");
        ifThereIsNull = true;
    }
    if (ifThereIsDetailImg) {
        alert("请至少添加一张商品详情图片！");
        ifThereIsNull = true;
    }
    //alert("是不是空的"+ifThereIsAdImg+" "+ifThereIsDetailImg);
    for (var j = 1; j < 9; j++) {
        var shipAddresses = document.getElementById("1o" + j);
        var specifications = document.getElementById("1w" + j);
        var packs = document.getElementById("1p" + j);
        //alert(shipAddresses.value);
        if (shipAddresses.value != "") {
            //alert("1不是空的！");
            ifThereIsOne = false;
        }
        //alert(specifications.value);
        if (specifications.value != "") {
            //alert("2不是空的！");
            ifThereIsTwo = false;
        }
        if (packs.value != "") {
            //alert("3不是空的！");
            ifThereIsThree = false;
        }
    }

    if (ifThereIsOne) {
        alert("请至少添加一种来源规格！");
        ifThereIsNull = true;
    }
    if (ifThereIsTwo) {
        alert("请至少添加一种自定义规格！");
        ifThereIsNull = true;
    }
    if (ifThereIsThree) {
        alert("请至少添加一种包装规格！");
        ifThereIsNull = true;
    }

    //submitNewProperty();
    if (!ifThereIsNull) {
        //alert("提交吧！");
        uploadNewGoods();
    }
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


//提交新增属性
function submitNewProperty(prodecutId) {
    //alert("规格");
    var productExtraPropertiesList = [];
    for (var i = 0; i < npdNum; i++) {
        //alert("get left "+"inputLeft"+i)
        //alert("get right "+"inputRight"+i)

        var newKey = document.getElementById("inputLeft" + i);
        var newValue = document.getElementById("inputRight" + i);
        if(newKey.value!=""&&newValue.value!=""){
        var productExtraProperties = new Object();
        productExtraProperties.key = newKey.value;
        productExtraProperties.value = newValue.value;
        productExtraProperties.pIndex = (i + 1).toString();
        //alert(productExtraProperties.value);
        productExtraPropertiesList.push(productExtraProperties);
        //alert(productExtraProperties.value);
        //alert(productExtraPropertiesList.length);

        }else{
            //alert("有空的！");
        }
    }
    for (var j = 0; j < productExtraPropertiesList.length; j++) {
        //alert(productExtraPropertiesList[j].value);
    }
    //alert()
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/product/op/" + prodecutId,
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
            alert("提交成功！ 完成价格设置后商品即可上架！");
            toPrice(prodecutId);


        },
    });


}

function toPrice(goodsId) {
    //alert("提交成功！");
    window.location.href = "newGoods-SettingPrice.html?" + goodsId;
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
    var weightNotice = document.getElementById("weightNotice");
    var comingNotice = document.getElementById("comingNotice");
    var packageNotice = document.getElementById("packageNotice");

    noticeList.push(nameNotice);
    noticeList.push(picNotice);
    noticeList.push(categoryNotice);
    noticeList.push(middleCategoryNotice);
    noticeList.push(brandNotice);
//        noticeList.push(shipAddressNotice);
    noticeList.push(weightNotice);
    noticeList.push(comingNotice);
    noticeList.push(packageNotice);


    for (var i = 0; i < noticeList.length; i++) {
        noticeList[i].setAttribute("class", "product-notice-inside");
    }
}


function goodsNewCategory() {
    var cateName = document.getElementById("cateName");
    var cateImg = document.getElementById("88");
    if(cateName.value==""||cateImg.value==""){
        alert("请填写完整的类别信息！");
    }else{
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



}

function goodsNewMiddleCategory() {
    var cateMName = document.getElementById("cateMName");
    var cateMImg = document.getElementById("21");
    if(cateMName.value==""||cateMImg.value==""){
        alert("请填写完整的类别信息！");
    }else {
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
}

function goodsNewBrand() {
    //alert("hahha");
    var brandName = document.getElementById("brandName");
    var brandImg = document.getElementById("31");
    if(brandName.value==""||brandImg.value==""){
        alert("请填写完整的类别信息！");
    }else {
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

    cateName.value = "";
    cateMName.value = "";
    brandName.value = "";

    cateImg.value = "";
    cateMImg.value = "";
    brandImg.value = "";

    cateImgSrc.src = "";
    cateMImgSrc.src = "";
    brandImgSrc.src = "";
}