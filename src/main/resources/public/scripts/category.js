/**
 * Created by mr.meng on 17/1/2.
 */

var categoryId;  //大种类 请勿随意修改
$(document).ready(function () {
    //alert("get all!");
    getAllCategory();
});

function getAllCategory() {
    var allCategory = document.getElementById("allCategory");
    var categorySelect = document.getElementById("categorySelect");
    var cateNum;
    $.ajax({
        //url: "http://192.168.1.107:8443/v1/productCategory",
        url: "https://we.chenhaonee.cn/v1/productCategory",
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            cateNum = data.data.length;
            $.each(data.data, function (idx, item) {
                if (idx == 0) {
                    categoryId = item.id;
                    //alert(categoryId);
                }
                var perOption = document.createElement("option");
                perOption.value = item.id;
                perOption.innerHTML = item.name;
                categorySelect.appendChild(perOption);
                var perCategory = document.createElement("div");
                perCategory.setAttribute("class", "per-category");

                var arrowUp = document.createElement("i");
                arrowUp.className = "fa-middle fa-arrow-circle-o-up per-category-arrow-up";
                var arrowDown = document.createElement("i");
                arrowDown.className = "fa-middle fa-arrow-circle-o-down per-category-arrow-up";

                arrowUp.cid = item.id;
                arrowUp.upon = "up";
                arrowDown.cid = item.id;
                arrowDown.upon = "down";

                arrowUp.onclick = function () {
                    changeIndex(this);
                }
                arrowDown.onclick = function () {
                    changeIndex(this);
                }


                var cateImg = document.createElement("img");
                cateImg.src = item.imgUrl;
                var cateName = document.createElement("h3");
                cateName.innerHTML = item.name;
                var deleteBtn = document.createElement("i");
                deleteBtn.setAttribute("class", "fa-saler-trash");
                deleteBtn.did = item.id;
                deleteBtn.onclick = function () {
                    deleteCategory(this);
                };

                if (cateNum != 1) {
                    var arrowHolder = document.createElement("span");
                    arrowHolder.className = "per-category-arrow";
                    arrowHolder.appendChild(arrowUp);
                    arrowHolder.appendChild(arrowDown);

                    perCategory.appendChild(arrowHolder);
                }

                perCategory.appendChild(cateImg);
                perCategory.appendChild(cateName);
                perCategory.appendChild(deleteBtn);
                perCategory.cid = item.id;
                perCategory.cName = item.name;
                perCategory.imgUrl = item.imgUrl;

                cateImg.onclick = function () {
                    showModifyCategoryNotice(this);
                };
                cateName.onclick = function () {
                    showModifyCategoryNotice(this);
                };
                allCategory.appendChild(perCategory);
            });
            changeShow(categoryId);
        },
    });
}

function deleteCategory(btn) {
    //alert("delete "+btn.did);
    if (confirm("你确定删除该种类吗？")) {
        //DO SOMETHING
        $.ajax({
            url: "https://we.chenhaonee.cn/v1/productCategory/" + btn.did,
            type: "DELETE",
            dataType: 'JSON',
            async: false,
            success: function (data) {
                alert("删除成功!");
                location.reload();
            },
        });
    }
    else {
        //DO NOTHING
    }

}

var addGoodsBtn = document.getElementById("add-goods-btn-text")
var addPropertyBtn = document.getElementById("add-property-btn-text")

function toPicPro() {
    addPropertyBtn.innerHTML = "+";
    $("#add-property-btn-text").removeClass("text-small");
    $("#add-property-btn-text").addClass("text-big");

}

function toTextPro() {
    $("#add-property-btn-text").removeClass("text-big");
    $("#add-property-btn-text").addClass("text-small");
    addPropertyBtn.innerHTML = "新建属性";
}

function toPic() {
    addGoodsBtn.innerHTML = "下一步";
    $("#add-goods-btn-text").removeClass("text-big");
    $("#add-goods-btn-text").addClass("text-middleBig");

}

function toText() {
    $("#add-goods-btn-text").removeClass("text-middleBig");
    $("#add-goods-btn-text").addClass("text-big");
    addGoodsBtn.innerHTML = "下一步";
}

function toNewGoods() {
    alert("come on!");
    uploadNewGoods();
}

function showAllCategory() {
    noticeListInit();
    $("#allCategoryNotice").addClass("show-anyway");
}

function newAMiddleCategory() {
    var middleCategoryBelongTo = document.getElementById("middleCategoryBelongTo");
    //alert("categoryId is " + categoryId);
    middleCategoryBelongTo.value = categoryId;
    //alert("new a "+middleCategoryBelongTo.value);
    var middleCategoryForm = $("form[name=newMiddleCategory]");
    var MCoptions = {
        url: 'https://we.chenhaonee.cn/v1/productCategory',
        type: 'post',
        success: function (data) {
            alert("添加成功");
            reLoad();
        }
    };
    middleCategoryForm.ajaxSubmit(MCoptions);
}

function newABrand() {
    var middleCategoryBelongTo = document.getElementById("brandBelongTo");
    //alert("categoryId is " + categoryId);
    middleCategoryBelongTo.value = categoryId;
    //alert("new a "+middleCategoryBelongTo.value);
    var middleCategoryForm = $("form[name=newBrand]");
    var MCoptions = {
        url: 'https://we.chenhaonee.cn/v1/brand',
        type: 'post',
        success: function (data) {
            alert("success");
            reLoad();
        }
    };
    middleCategoryForm.ajaxSubmit(MCoptions);
}

function changeShow(value) {
    categoryId = value;
    //alert("value is "+value);
    clearAll();
    getMiddleCategory();
    getBrand();
}

function clearAll() {
    var middleCategoryDetails = document.getElementById("middleCategory");
    var mchilds = middleCategoryDetails.childNodes;
    for (var i = mchilds.length - 1; i >= 0; i--) {
        middleCategoryDetails.removeChild(mchilds[i]);
    }

    var typeDetails = document.getElementById("brandGoods");
    var childs = typeDetails.childNodes;
    for (var i = childs.length - 1; i >= 0; i--) {
        typeDetails.removeChild(childs[i]);
    }
}

function getMiddleCategory() {
    //alert("获取"+categoryId+"的二级分类");
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/productCategory/" + categoryId,
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                var perCategoryDiv = document.createElement("div");
                perCategoryDiv.setAttribute("class", "per-category-div");
                perCategoryDiv.cid = item.id;
                perCategoryDiv.imgUrl = item.imgUrl;
                perCategoryDiv.cName = item.name;

                var perCategoryImg = document.createElement("img");
                perCategoryImg.src = item.imgUrl;
                var perCategoryName = document.createElement("h3");
                perCategoryName.innerHTML = item.name;
                perCategoryDiv.appendChild(perCategoryImg);
                perCategoryDiv.appendChild(perCategoryName);
                var secondId = item.id;
                //alert("second id is"+secondId);
                getSellingNum(perCategoryDiv, secondId);
            });


        },
    });

}

function getBrand() {
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/brand/" + categoryId,
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                var perCategoryDiv = document.createElement("div");
                perCategoryDiv.setAttribute("class", "per-category-div");
                perCategoryDiv.cid = item.id;
                perCategoryDiv.imgUrl = item.imgUrl;
                perCategoryDiv.cName = item.name;
                var perCategoryImg = document.createElement("img");
                perCategoryImg.src = item.imgUrl;
                var perCategoryName = document.createElement("h3");
                perCategoryName.innerHTML = item.name;
                perCategoryDiv.appendChild(perCategoryImg);
                perCategoryDiv.appendChild(perCategoryName);
                var secondId = item.id;
                //alert("second id is"+secondId);
                getBrandSellingNum(perCategoryDiv, secondId);
            });


        },
    });
}

function getSellingNum(perCategoryDiv, secondId) {
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/productCategory/count/" + secondId,
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            var middleCategory = document.getElementById("middleCategory");

            var perCategorySaleLeft = document.createElement("h4");
            var perCategorySaleRight = document.createElement("h4");
            perCategorySaleLeft.innerHTML = "个商品"
            perCategorySaleRight.innerHTML = "共有"
            var perCategorySaleNumber = document.createElement("h2");
            perCategorySaleNumber.setAttribute("class", "big-num");
            perCategorySaleNumber.innerHTML = data.data;
            //alert("种类"+secondId+"的商品个数为"+data.data);
            perCategoryDiv.appendChild(perCategorySaleLeft);
            perCategoryDiv.appendChild(perCategorySaleNumber);
            perCategoryDiv.appendChild(perCategorySaleRight);
            perCategoryDiv.onclick =
                function () {
                    showModifyMiddleCategoryNotice(this);
                };

            middleCategory.appendChild(perCategoryDiv);

        },
    });
}

function getBrandSellingNum(perCategoryDiv, secondId) {
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/brand/count/" + secondId,
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            var middleCategory = document.getElementById("brandGoods");

            var perCategorySaleLeft = document.createElement("h4");
            var perCategorySaleRight = document.createElement("h4");
            perCategorySaleLeft.innerHTML = "个商品"
            perCategorySaleRight.innerHTML = "共有"
            var perCategorySaleNumber = document.createElement("h2");
            perCategorySaleNumber.setAttribute("class", "big-num");
            perCategorySaleNumber.innerHTML = data.data;
            perCategoryDiv.appendChild(perCategorySaleLeft);
            perCategoryDiv.appendChild(perCategorySaleNumber);
            perCategoryDiv.appendChild(perCategorySaleRight);
            perCategoryDiv.onclick =
                function () {
                    showModifyBrandNotice(this);
                };

            middleCategory.appendChild(perCategoryDiv);

        },
    });
}


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
    inputRight.placeholder = "属性内容";
    newProductPerDiv.appendChild(inputLeft);
    newProductPerDiv.appendChild(inputRight);
    addNewProperty.appendChild(newProductPerDiv);


}

function newCategory() {
    //alert("新增类别了！");
    newCategoryForm();
}


function showCategoryNotice() {
//        alert("Show Name Notice!");
    noticeListInit();
    $("#categoryNotice").addClass("show-anyway");
}


function showMiddleCategoryNotice() {
//        alert("新增小品牌!");
    noticeListInit();
    $("#categoryMiddleNotice").addClass("show-anyway");
}

function showBrandNotice() {
//        alert("Show Category Notice!");
    noticeListInit();
    $("#brandNotice").addClass("show-anyway");
}

function showModifyCategoryNotice() {
//        alert("Show Category Notice!");
    noticeListInit();
    $("#modifyCategoryNotice").addClass("show-anyway");
}

//展示包含了数据的可修改小种类
function showModifyMiddleCategoryNotice(btn) {
    //alert("Show Category Notice!"+btn.cName+btn.imgUrl);
    //var mcid = document.getElementById("mcid");
    //var mcBelongToId = document.getElementById("mcBelongToId");
    var mcName = document.getElementById("mcName");
    var mcImg = document.getElementById("image23");
    var modifyMiddleBtn = document.getElementById("modifyMiddleBtn");
    var deleteMiddleCategoryBtn = document.getElementById("deleteMiddleCategoryBtn");
    modifyMiddleBtn.cid = btn.cid;
    deleteMiddleCategoryBtn.did = btn.cid;
    modifyMiddleBtn.imgUrl = btn.imgUrl;
    modifyMiddleBtn.mcName = btn.cName;
    modifyMiddleBtn.belongToId = categoryId;

    //mcid.innerHTML=btn.cid;
    mcBelongToId.innerHTML = categoryId;
    mcImg.src = btn.imgUrl;
    mcName.value = btn.cName;
    noticeListInit();
    $("#modifyMiddleCategoryNotice").addClass("show-anyway");
}

//点击修改小种类按钮后的操作
function modifyMiddleCategory(btn) {
    var mcNameInput = document.getElementById("mcName");
    var mcName = mcNameInput.value;
    var mmcform = $("form[name=modifyAMiddleCategory]");
    var mmcoptions = {
        url: 'https://we.chenhaonee.cn/v1/productCategory/' + btn.cid + ":" + mcName,
        type: 'put',
        success: function (data) {
            alert("修改成功!");
            reLoad();
        }
    };
    mmcform.ajaxSubmit(mmcoptions);
}

function showModifyCategoryNotice(btn) {
    //alert("Show Category Notice!"+btn.cName+btn.imgUrl);
    //var mcid = document.getElementById("mcid");
    //var mcBelongToId = document.getElementById("cBelongToId");
    var mcName = document.getElementById("cName");
    var mcImg = document.getElementById("image22");
    var modifyMiddleBtn = document.getElementById("modifyCategoryBtn");
    var deleteCategoryBtn = document.getElementById("deleteCategoryBtn");

    modifyMiddleBtn.cid = btn.cid;
    deleteCategoryBtn.did = btn.cid;
    modifyMiddleBtn.imgUrl = btn.imgUrl;
    modifyMiddleBtn.mcName = btn.cName;
    modifyMiddleBtn.belongToId = categoryId;

    //mcid.innerHTML=btn.cid;
    //mcBelongToId.innerHTML=categoryId;
    mcImg.src = btn.imgUrl;
    mcName.value = btn.cName;
    noticeListInit();
    $("#modifyCategoryNotice").addClass("show-anyway");
}

//点击修改种类按钮后的操作
function modifyCategory(btn) {
    var mcNameInput = document.getElementById("cName");
    var mcName = mcNameInput.value;
    var mmcform = $("form[name=modifyAMiddleCategory]");
    var mmcoptions = {
        url: 'https://we.chenhaonee.cn/v1/productCategory/' + btn.cid + ":" + mcName,
        type: 'put',
        success: function (data) {
            alert("修改成功!");
            reLoad();
        }
    };
    mmcform.ajaxSubmit(mmcoptions);
}

function showModifyBrandNotice(btn) {
    var mcName = document.getElementById("bName");
    var mcImg = document.getElementById("image24");
    var modifyMiddleBtn = document.getElementById("modifyBrandBtn");
    var deleteBrandBtn = document.getElementById("deleteBrandBtn");
    modifyMiddleBtn.cid = btn.cid;
    deleteBrandBtn.did = btn.cid;
    modifyMiddleBtn.imgUrl = btn.imgUrl;
    modifyMiddleBtn.mcName = btn.cName;
    modifyMiddleBtn.belongToId = categoryId;

    //mcid.innerHTML=btn.cid;
    mcBelongToId.innerHTML = categoryId;
    mcImg.src = btn.imgUrl;
    mcName.value = btn.cName;
//        alert("Show Category Notice!");
    noticeListInit();
    $("#modifyBrandNotice").addClass("show-anyway");
}

//点击修改品牌按钮后的操作
function modifyABrand(btn) {
    var mcNameInput = document.getElementById("bName");
    var mcName = mcNameInput.value;
    var mmcform = $("form[name=modifyBrand]");
    var mmcoptions = {
        url: 'https://we.chenhaonee.cn/v1/brand/' + btn.cid + ":" + mcName,
        type: 'put',
        success: function (data) {
            alert("修改成功!");
            reLoad();
        }
    };
    mmcform.ajaxSubmit(mmcoptions);
}


function noticeListInit() {
    var noticeList = [];
    var categoryNotice = document.getElementById("categoryNotice");
    var categoryMiddleNotice = document.getElementById("categoryMiddleNotice");
    var brandNotice = document.getElementById("brandNotice");
    var allCategoryNotice = document.getElementById("allCategoryNotice");
    var modifyCategoryNotice = document.getElementById("modifyCategoryNotice");
    var modifyMiddleCategoryNotice = document.getElementById("modifyMiddleCategoryNotice");
    var modifyBrandNotice = document.getElementById("modifyBrandNotice");

    noticeList.push(categoryNotice);
    noticeList.push(categoryMiddleNotice);
    noticeList.push(brandNotice);
    noticeList.push(allCategoryNotice);
    noticeList.push(modifyCategoryNotice);
    noticeList.push(modifyMiddleCategoryNotice);
    noticeList.push(modifyBrandNotice);


    for (var i = 0; i < noticeList.length; i++) {
        noticeList[i].setAttribute("class", "category-notice");
    }
}

function reLoad() {
    location.reload();
}

function newCategoryForm() {
    var form = $("form[name=newCategoryForm]");
    var options = {
        url: 'https://we.chenhaonee.cn/v1/productCategory',
        type: 'post',
        success: function (data) {
            alert("提交成功！");
            reLoad();
        }
    };
    form.ajaxSubmit(options);
}

//删除品牌
function deleteBrand(deleteBtn) {
    if (confirm("你确定删除该品牌吗？")) {
        //删除品牌
        var brandId = deleteBtn.did;
        $.ajax({
            url: "https://we.chenhaonee.cn/v1/brand/" + brandId,
            type: "DELETE",
            data: {},
            dataType: 'JSON',
            async: false,
            success: function (data) {
                alert("删除成功!");
                location.reload();
            },
            error: function (err) {
                alert("删除失败，请重试！(" + err + ")");
            }
        });
    }
    else {
        //DO NOTHING
    }
}
//删除种类
function deleteBrand(deleteBtn) {
    if (confirm("你确定删除该种类吗？")) {
        //删除种类
        var categoryId = deleteBtn.did;
        $.ajax({
            url: "https://we.chenhaonee.cn/v1/productCategory/" + categoryId,
            type: "DELETE",
            data: {},
            dataType: 'JSON',
            async: false,
            success: function (data) {
                alert("删除成功!");
                location.reload();
            },
            error: function (err) {
                alert("删除失败，请重试！(" + err + ")");
            }
        });
    }
    else {
        //DO NOTHING
    }
}

function changeIndex(btn) {
    var upon = true;
    if (btn.upon == "up") {
        upon = true;
    } else {
        upon = false;
    }
    $.ajax({
        //url: "http://192.168.1.107:8443/v1/productCategory/upon/" + btn.cid,
        url: "https://we.chenhaonee.cn/v1/productCategory/upon/" + btn.cid,
        type: "PUT",
        data: {
            upon: upon
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            alert("修改成功!");
            location.reload();
        },
        error: function (err) {
            alert("修改失败，请重试！(" + err + ")");
        }
    });
}