/**
 * Created by mr.meng on 17/1/24.
 */
$(document).ready(function () {
    initCategorySelect();
});

var storeId=1;

//初始化商品类别的下拉框
function initCategorySelect() {

    var categorySelect = document.getElementById("categorySelect");

    categorySelect.innerHTML = "";

    $.ajax({
        url: "https://we.chenhaonee.cn/v1/productCategory",
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                if (idx == 0) {
                    getCategoryDetail(item.id);
                }
                var perOption = document.createElement("option");
                perOption.value = item.id;
                perOption.innerHTML = item.name;

                categorySelect.appendChild(perOption);

            });
        }

    });


}
//显示有几件商品
function getCategoryDetail(categoryId) {
    //alert(categoryId);
    var productAmount = document.getElementById("productAmount");
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/productCategory/count/" + categoryId,
        type: "GET",
        dataType: 'JSON',
        async: false,
        success: function (data) {
            productAmount.innerHTML = data.data;
        }

    });
    initGoodsList(categoryId);
    //alert("category id is "+categoryId);

}

//显示商品列表
function initGoodsList(categoryId) {
    var goodsList = document.getElementById("goodsList");
    goodsList.innerHTML="";
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/product/category/"+categoryId,
        type: "GET",
        data:{
            storeId:storeId,
            pageSize:100,
            pageNum:1
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            var perItem=data.data.listObject;
            //alert(perItem.length);
            for(var i =0;i<perItem.length;i++){
                var item=perItem[i];
                //alert(item.name);
                var perGoods = document.createElement("div");
                perGoods.setAttribute("class","goods-list-per-good");
                perGoods.pid=item.id;
                perGoods.onclick= function () {
                    showGoods(this);
                }
                var goodsImg= document.createElement("img");
                goodsImg.src=item.imgUrl;
                var goodsName = document.createElement("h3");
                goodsName.innerHTML=item.name;
                var goodsPrice = document.createElement("h5");
                goodsPrice.innerHTML="￥"+item.minUnitPrice+"-￥"+item.averagePrice;
                var goodsSale = document.createElement("h4");
                goodsSale.innerHTML="销量："+item.sales;
                perGoods.appendChild(goodsImg);
                perGoods.appendChild(goodsName);
                perGoods.appendChild(goodsPrice);
                perGoods.appendChild(goodsSale);

                goodsList.appendChild(perGoods);
            }


        }

    });
}

//跳转到商品详情
function showGoods(btn){
    //alert(btn.pid);
    window.location.href="showGoods.html?productId="+btn.pid;
}
