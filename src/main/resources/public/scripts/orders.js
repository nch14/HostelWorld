/**
 * Created by mr.meng on 16/12/30.
 */

$(document).ready(function () {
    initLeftBtns();
    var b1 = document.getElementById("b1");
    b1.click();
    //initOrders();
});

function initLeftBtns() {
    //alert("init!");
    var ordersDiv = document.getElementById("ordersDiv");
    var childs = ordersDiv.childNodes;
    for (var i = childs.length - 1; i >= 0; i--) {
        ordersDiv.removeChild(childs[i]);
    }
    //var submitNum = document.getElementById("submitNum");
    var processNum = document.getElementById("processNum");
    var confirmNum = document.getElementById("confirmNum");
    //var processNumber = 0;
    var completeNum = document.getElementById("completeNum");
    var allNum = document.getElementById("allNum");
    //$.ajax({
    //    url: "https://we.chenhaonee.cn/v1/order/type/ORDER_SUBMIT",
    //    type: "GET",
    //    dataType: 'JSON',
    //    async: false,
    //    success: function (data) {
    //        submitNum.innerHTML = data.data.length;
    //    },
    //});
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/order/type/ORDER_PROCESSING",
        type: "GET",
        data:{
            page:1
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            processNum.innerHTML = data.data.length;
        },
    });
    //alert("掉了！");
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/order/type/ORDER_CONFIRM",
        //url: "http://192.168.1.107:8443/v1/order/type/ORDER_CONFIRM",
        type: "GET",
        data:{
            page:1
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            confirmNum.innerHTML = data.data.length;
        },
    });
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/order/type/ORDER_COMPLETE",
        type: "GET",
        data:{
            page:1
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            completeNum.innerHTML = data.data.length;
        },
    });
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/order/type/ORDER_ALL",
        type: "GET",
        data:{
            page:1
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            allNum.innerHTML = data.data.length;
        },
    });
}

function clearOrderDiv(){
    var ordersDiv = document.getElementById("ordersDiv");
    var childs = ordersDiv.childNodes;
    for(var i = childs.length - 1; i >= 0; i--) {
        ordersDiv.removeChild(childs[i]);
    }
}

function getSubmitOrder(btn) {
    toActive(btn);
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/order/type/ORDER_SUBMIT",
        type: "GET",
        data:{
            page:1
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                getOrderDetail(item);
            });
        },
    });
}

function getConfirmOrder(btn) {
    toActive(btn);

    $.ajax({
        url: "https://we.chenhaonee.cn/v1/order/type/ORDER_CONFIRM",
        //url: "http://192.168.1.107:8443/v1/order/type/ORDER_CONFIRM",

        type: "GET",
        data:{
            page:1
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                getOrderDetail(item);
            });
        },
    });
}

function getProcessOrder(btn) {
    toActive(btn);

    $.ajax({
        url: "https://we.chenhaonee.cn/v1/order/type/ORDER_PROCESSING",
        type: "GET",
        data:{
            page:1
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                getOrderDetail(item);
            });
        },
    });
}

function getCompleteOrder(btn) {
    toActive(btn);

    $.ajax({
        url: "https://we.chenhaonee.cn/v1/order/type/ORDER_COMPLETE",
        type: "GET",
        data:{
            page:1
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                getOrderDetail(item);
            });
        },
    });
}

function getAllOrder(btn) {
    //alert("拿全部的！");
    toActive(btn);

    $.ajax({
        url: "https://we.chenhaonee.cn/v1/order/type/ORDER_ALL",
        type: "GET",
        data:{
            page:1
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, item) {
                //alert(item.orderState);
                if (item.orderState != "ORDER_DRAFT") {
                    //alert("这是草稿！");
                    getOrderDetail(item);
                }

            });
        },
    });
}
function getOrderDetail(item) {

    var perOrderDiv = document.createElement("div");
    perOrderDiv.setAttribute("class", "per-order-div");
    var perOrderState = document.createElement("div");
    perOrderState.setAttribute("class", "per-order-state");
    var stateP = document.createElement("p");
    if (item.orderState == "ORDER_CANCEL") {
        stateP.innerHTML = "已取消";
        perOrderState.appendChild(stateP);
        var perOrderPrice = document.createElement("div");
        perOrderPrice.setAttribute("class", "per-order-price");
        //var company = document.createElement("input");
        //company.type = "text";
        //company.placeholder = "快递公司(选填)";
        //var orderId = document.createElement("input");
        //orderId.type = "text";
        //orderId.placeholder = "快递公司(选填)";
        var priceP = document.createElement("h5");
        priceP.innerHTML = "￥" + item.totalPrice;
        //var actBtn = document.createElement("button");
        //actBtn.innerHTML = "确认发货";

        //perOrderPrice.appendChild(company);
        //perOrderPrice.appendChild(orderId);
        perOrderPrice.appendChild(priceP);
        //perOrderPrice.appendChild(actBtn);
    }
    if (item.orderState == "ORDER_SUBMIT") { //已提交
        stateP.innerHTML = "待确认";
        perOrderState.appendChild(stateP);
        var perOrderPrice = document.createElement("div");
        perOrderPrice.setAttribute("class", "per-order-price");
        //var company = document.createElement("input");
        //company.type = "text";
        //company.placeholder = "快递公司(选填)";
        //var orderId = document.createElement("input");
        //orderId.type = "text";
        //orderId.placeholder = "快递公司(选填)";
        var priceP = document.createElement("h2");
        priceP.innerHTML = "￥" + item.totalPrice;
        //var actBtn = document.createElement("button");
        //actBtn.innerHTML = "确认订单";

        //perOrderPrice.appendChild(company);
        //perOrderPrice.appendChild(orderId);
        perOrderPrice.appendChild(priceP);
        //perOrderPrice.appendChild(actBtn);

    }
    if (item.orderState == "ORDER_CONFIRM") { //已确认
        stateP.innerHTML = "待发货";
        perOrderState.appendChild(stateP);
        var perOrderPrice = document.createElement("div");
        perOrderPrice.setAttribute("class", "per-order-price");
        var company = document.createElement("select");

        $.ajax({
            url: "https://we.chenhaonee.cn/v1/express",
            type: "GET",
            dataType: 'JSON',
            async: false,
            success: function (data) {
                var companyList= data.data;
                for(var i = 0 ;i<companyList.length;i++){
                    var perOption = document.createElement("option");
                    perOption.innerHTML = companyList[i].name;
                    perOption.value= companyList[i].id;
                    company.appendChild(perOption);
                }
            },
        });


        company.id="com"+item.id;
        var orderId = document.createElement("input");
        orderId.type = "text";
        orderId.placeholder = "快递单号";
        orderId.id="order"+item.id;
        var priceP = document.createElement("p");
        priceP.innerHTML = "￥" + item.totalPrice;
        var actBtn = document.createElement("button");
        //TODO 是不是  item.id 此处存疑
        actBtn.oid=item.id;
        actBtn.innerHTML = "确认发货";
        actBtn.onclick= function () {
            onTheWay(this);
        }

        perOrderPrice.appendChild(company);
        perOrderPrice.appendChild(orderId);
        perOrderPrice.appendChild(priceP);
        perOrderPrice.appendChild(actBtn);

    }
    if (item.orderState == "ORDER_PROCESSING") { //处理中
        stateP.innerHTML = "待收货";
        perOrderState.appendChild(stateP);
        var perOrderPrice = document.createElement("div");
        perOrderPrice.setAttribute("class", "per-order-price");
        var company = document.createElement("h3");
        company.innerHTML = "快递公司(选填)";
        var orderId = document.createElement("h4");
        orderId.innerHTML = "快递公司(选填)";
        var priceP = document.createElement("p");
        priceP.innerHTML = "￥" + item.totalPrice;
        var actBtn = document.createElement("button");
        actBtn.innerHTML = "确认收货";
        actBtn.oid=item.id;
        actBtn.onclick= function () {
            finish(this);
        }


        perOrderPrice.appendChild(company);
        perOrderPrice.appendChild(orderId);
        perOrderPrice.appendChild(priceP);
        perOrderPrice.appendChild(actBtn);


    }
    if (item.orderState == "ORDER_COMPLETE") { //已完成
        stateP.innerHTML = "已完成";
        perOrderState.appendChild(stateP);
        var perOrderPrice = document.createElement("div");
        perOrderPrice.setAttribute("class", "per-order-price");
        //var company = document.createElement("h3");
        //company.innerHTML = "快递公司(选填)";
        //var orderId = document.createElement("h4");
        //orderId.innerHTML = "快递公司(选填)";
        //var priceP = document.createElement("p");
        //priceP.innerHTML = "￥" + item.totalPrice;
        //var actBtn = document.createElement("button");
        //actBtn.innerHTML = "确认收货";



        //perOrderPrice.appendChild(company);
        //perOrderPrice.appendChild(orderId);
        //perOrderPrice.appendChild(priceP);
        //perOrderPrice.appendChild(actBtn);

    }


    var perOrderAddress = document.createElement("div");
    perOrderAddress.setAttribute("class", "per-order-address");
    var addressLineOne = document.createElement("p");
    var addressLineTwo = document.createElement("p");
    var nameSpan = document.createElement("span");
    var telSpan = document.createElement("span");
    var personIdSpan = document.createElement("span");
    var timeSpan = document.createElement("span");
    var addressSpan = document.createElement("span");

    nameSpan.innerHTML = "收货人:" + item.recipient.validName;
    telSpan.innerHTML = "电话:" + item.recipient.telNumber;
    personIdSpan.innerHTML = "身份证:" + item.recipient.validIdnumber;
    timeSpan.innerHTML = "时间:" + item.makeTime;
    addressLineOne.appendChild(nameSpan);
    addressLineOne.appendChild(telSpan);
    addressLineOne.appendChild(personIdSpan);
    addressLineOne.appendChild(timeSpan);
    addressSpan.innerHTML = "地址:" + item.recipient.rProvince + "省" + item.recipient.rCity + "市" + item.recipient.rDetailAddress;
    addressLineTwo.appendChild(addressSpan);
    perOrderAddress.appendChild(addressLineOne);
    perOrderAddress.appendChild(addressLineTwo);
    perOrderDiv.appendChild(perOrderState);
    perOrderDiv.appendChild(perOrderPrice);
    perOrderDiv.appendChild(perOrderAddress);

    var goodsList = item.goods;
    var perOrderGoods = document.createElement("div");
    perOrderGoods.setAttribute("class", "per-order-goods");
    for (var i = 0; i < goodsList.length; i++) {
        var perGoodsSpan = document.createElement("span");

        perGoodsSpan.innerHTML += goodsList[i].name + " 规格:" + goodsList[i].shipAddressValue + " " + goodsList[i].specificationValue + " " + goodsList[i].packValue + " X" + goodsList[i].selectCount;
        perOrderGoods.appendChild(perGoodsSpan);

    }

    perOrderDiv.appendChild(perOrderGoods);
    var ordersDiv = document.getElementById("ordersDiv");

    ordersDiv.appendChild(perOrderDiv);
}

function toActive(btn) {
    var buttonsList = [];
    //var submit = document.getElementById("b0");
    var send = document.getElementById("b1");
    var achieve = document.getElementById("b2");
    var commit = document.getElementById("b3");
    var all = document.getElementById("b4");
    //buttonsList.push(submit);
    buttonsList.push(send);
    buttonsList.push(achieve);
    buttonsList.push(commit);
    buttonsList.push(all);
    //alert("here");
    for (var i = 0; i < buttonsList.length; i++) {
        buttonsList[i].setAttribute("class", "orders-left-button");
    }
    btn.setAttribute("class", "orders-left-button-active");
    clearOrderDiv();
}

function onTheWay(order){
    var transportId=1;
    var comInput = document.getElementById("com"+order.oid);
    var orderInput = document.getElementById("order"+order.oid);
    if(comInput.value==""||orderInput==""){
        transportId=2;
    }
    //alert("确认订单！"+order.oid);
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/order/"+order.oid,
        type: "PUT",
        data:{
            state:3,
            transportMeans:comInput.value,
            expressNumber:orderInput.value
        },
        dataType: 'JSON',
        async: false,
        success: function(data) {
            alert("发货成功！");
            location.reload();
        }
    });
}


function finish(order){

    alert("确认收货！"+order.oid);
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/order/"+order.oid,
        type: "PUT",
        data:{
            state:4,
        },
        dataType: 'JSON',
        async: false,
        success: function(data) {
            alert("已收货！");
            location.reload();
        }
    });
}