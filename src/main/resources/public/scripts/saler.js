/**
 * Created by mr.meng on 17/1/1.
 */
function newSaler() {
    var salerName = document.getElementById("salerName");
    var img1 = document.getElementById("image1");
    var img2 = document.getElementById("image2");
    var storeName = document.getElementById("storeName");
    var storeTel = document.getElementById("storeTel");
    var storeWechat = document.getElementById("storeWechat");
    var storeAddress = document.getElementById("storeAddress");
    var storeDescription = document.getElementById("storeDescription");

    //alert(salerName.value);
    //alert(img1.value);
    //alert(img2.value);
    //alert(storeName.value);
    //alert(storeTel.value);
    //alert(storeWechat.value);
    //alert(storeAddress.value);
    //alert(storeDescription.value);

    $.ajax({
        url: "https://we.chenhaonee.cn/v1/productCategory/" + btn.id,
        type: "GET",
        data: {},
        dataType: 'JSON',
        async: false,
        success: function (data) {
            alert("成功!");
        }
    });
}

$(document).ready(function () {

    getSalers();

});

var ifNew = "true"
var salerId = "";

function getSalers() {
    //alert("get salers!!!");
    var salersDiv = document.getElementById("salersDiv");
    var childs = salersDiv.childNodes;
    for (var i = childs.length - 1; i >= 0; i--) {
        salersDiv.removeChild(childs[i]);
    }

    $.ajax({
        url: "https://we.chenhaonee.cn/v1/store",
        type: "GET",
        data: {
            //pageNum:1,
            //pageSize:1000
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            //alert(data);
            $.each(data.data, function (idx, item) {
                //alert(item.storeName);
                var perSaler = document.createElement("div");
                var salerImg = document.createElement("img");
                var salerIndex = document.createElement("h2");
                var salerName = document.createElement("h3");
                var salerTel = document.createElement("h4");
                var salerWechat = document.createElement("h4");
                var salerI = document.createElement("i");

                perSaler.setAttribute("class", "per-saler");
                perSaler.sid = item.sid;
                perSaler.onclick = function () {
                    showThisSaler(this);
                }
                salerIndex.innerHTML=idx+1;
                salerImg.src = item.storeHeadUrl;
                salerName.innerHTML = item.storeName;
                salerTel.innerHTML = "电话：" + item.telNumber;
                salerWechat.innerHTML = "微信：" + item.contactWechatId;
                salerI.id = item.sid;
                //alert(salerI.id);
                salerI.setAttribute("class", "fa-saler-trash");
                salerI.onclick = function () {
                    deleteSaler(this);
                }

                perSaler.appendChild(salerIndex);
                perSaler.appendChild(salerImg);
                perSaler.appendChild(salerName);
                perSaler.appendChild(salerTel);
                perSaler.appendChild(salerWechat);
                perSaler.appendChild(salerI);
                salersDiv.appendChild(perSaler);

            });
        },
    });


}


function checkIfNull() {
    var flag = true;

   var picOne = document.getElementById("1");
   var picTwo = document.getElementById("2");
    var storeName = document.getElementById("storeName");
    var userName = document.getElementById("userName");
    var storeTel = document.getElementById("storeTel");
    var storeWechat = document.getElementById("storeWechat");
    var storeAddress = document.getElementById("storeAddress");
    var storeDescription = document.getElementById("storeDescription");
    //alert(picOne.value == "");
    if(ifNew=="true"){
        //alert("新建的！！!");
        if (picOne.value == ""||picTwo.value == ""||storeName.value == ""
            ||userName.value == ""||storeTel.value == ""||storeWechat.value == ""
            ||storeAddress.value == ""||storeDescription.value == "") {

            flag = false;
        }
    }else{
        //alert("不是新建的！！!");
        if (storeName.value == ""
            ||userName.value == ""||storeTel.value == ""||storeWechat.value == ""
            ||storeAddress.value == ""||storeDescription.value == "") {

            flag = false;
        }
    }


    if (flag == false) {
        alert("请填写全部信息！");
    } else {
        //alert("全填了！");
        ajaxForm();
    }
    //addNewSaler();

    //document.forms.newSalerForm.submit();
    //alert("post is finished!");
    //window.location.href="customerPersonal.html";
    //getSalers();
    //}
}

function ajaxForm() {
    //if(ifNew=="true"){
    var form = $("form[name=newSalerForm]");
    var options = {
        url: 'https://we.chenhaonee.cn/v1/store',
        type: 'post',
        success: function (data) {
            alert("提交成功");
            if (ifNew == "true") {
                window.location.href = "salerUrl.html?storeId=" + data.data.sid;
            } else {
                getSalers();
                newASaler();

            }


        }
    };
    form.ajaxSubmit(options);
    //}else{
    //
    //    alert("修改而已");
    //}

}


function addNewSaler() {

    //var fdd = new FormData($("#newSalerForm"));
    var fdd = new FormData();
    var formmm = $("#newSalerForm").serializeArray();
    //alert("form's length is " + fdd.length);


    $.ajax({

        type: 'POST',
        url: 'https://we.chenhaonee.cn/v1/store',

        data: formmm
        //$('#newGoodsForm').serialize()
        //name:"",
        //origin:"",
        //brandId:"",
        //categoryId:"",
        //productExtraProperties:tempList,
        //goodDisplayImgs: imageList,
        //goodAdvertisingImgs:tempList
        ,

        //async: false,

        //dataType: 'json',

        contentType: false,
        processData: false,

        success: function (data) {

            alert('上传成功');

        },

        error: function (err) {

            alert('网络故障' + err);

        }

    });
}

var nowStoreId="";  //当前选中的storeId

function showThisSaler(saler) {
    nowStoreId=saler.sid;
    //alert("store id is "+saler.sid );
    ifNew = "false";
    var storeIdInput = document.getElementById("storeId");
    storeIdInput.value = saler.sid;
    salerId = saler.sid;
    //var account = document.getElementById("account");
    //var password = document.getElementById("password");
    var userName = document.getElementById("userName");
    var img1 = document.getElementById("image1");
    var img2 = document.getElementById("image2");
    var storeName = document.getElementById("storeName");
    var storeTel = document.getElementById("storeTel");
    var storeWechat = document.getElementById("storeWechat");
    var storeAddress = document.getElementById("storeAddress");
    var storeDescription = document.getElementById("storeDescription");

    $.ajax({
        url: "https://we.chenhaonee.cn/v1/store/" + saler.sid,
        type: "GET",
        data: {},
        dataType: 'JSON',
        async: false,
        success: function (data) {
            //alert(data.userId);
            var actualData = data.data;
            if (actualData == "") {
                alert("目前还没有分销商！");
            } else {
                userName.value = actualData.shopkeeperName;
                img1.src = actualData.storeHeadUrl;
                img2.src = actualData.payUrl;
                storeName.value = actualData.storeName;
                storeTel.value = actualData.telNumber;
                storeWechat.value = actualData.contactWechatId;
                storeAddress.value = actualData.address;
                storeDescription.value = actualData.description;
                var urlDivBtn = document.getElementById("urlDivBtn");

                urlDivBtn.style.display = "";
                //alert("display is "+urlDivBtn.style.display);
                urlDivBtn.uid=actualData.sid;
                //getSalerUrl(saler.sid);
            }
            //account.value=actualData.userId;
            //password.value=actualData.pwd;

        }
    });

    //alert("show " + saler.id);
}

function newASaler() {
    ifNew = "true";
    var storeIdInput = document.getElementById("storeId");
    storeIdInput.value = "";
    //var account = document.getElementById("account");
    //var password = document.getElementById("password");
    var userName = document.getElementById("userName");
    var img1 = document.getElementById("image1");
    var img2 = document.getElementById("image2");
    var storeName = document.getElementById("storeName");
    var storeTel = document.getElementById("storeTel");
    var storeWechat = document.getElementById("storeWechat");
    var storeAddress = document.getElementById("storeAddress");
    var storeDescription = document.getElementById("storeDescription");
    userName.value = "";
    img1.src = "";
    img2.src = "";
    storeName.value = "";
    storeTel.value = "";
    storeWechat.value = "";
    storeAddress.value = "";
    storeDescription.value = "";
    var urlDiv = document.getElementById("urlDivBtn");
    urlDiv.style.display = "none";

}


function getSalerUrl(urlDiv) {
    //var urlDiv=urlDiv;
    //alert("get url!");
    if (confirm("你确定生成新的店铺url吗？(重新生成后原店主将失去店主身份)")) {
        $.ajax({
            url: "https://we.chenhaonee.cn/v1/store/url",
            type: "GET",
            data: {
                storeId: urlDiv.uid
            },
            dataType: 'JSON',
            async: false,
            success: function (data) {
                var urlDivP = document.getElementById("urlDivP");
                urlDivP.innerHTML="单击以复制链接";
                urlDiv.onclick=function(){
                    copyUrl();
                };
                var storeUrl = document.getElementById("storeUrl");
                storeUrl.value = data.data;

                //alert(data.data);
            }
        });
    }

}

function copyUrl() {
    //alert("copy!");
    var copyTextarea = document.querySelector('#storeUrl');
    //alert(copyTextarea.value);

    copyTextarea.select();
    try {
        var successful = document.execCommand('copy');

        alert("您的商店链接已复制到剪贴板！");
    } catch (err) {
        //console.log('Oops, unable to copy');
        alert("复制失败，请重试！");
    }
}


function deleteSaler(deleteBtn) {

    //利用对话框返回的值 （true 或者 false）
    if (confirm("你确定删除该店铺吗？")) {
        //删除店铺

        var storeId = deleteBtn.id;
        $.ajax({
            url: "https://we.chenhaonee.cn/v1/store/" + storeId,
            type: "DELETE",
            data: {},
            dataType: 'JSON',
            async: false,
            success: function (data) {
                alert("删除成功!");
                if(nowStoreId==storeId){
                    location.reload();
                }else{
                    getSalers();
                }

            },

            error: function (err) {

                alert("删除失败，请重试！");

            }
        });

    }
    else {
        //DO NOTHING
    }


    //alert("deleting is completed!");
}


//function iframeLoad(iframe){
//    var doc = iframe.contentWindow.document;
//    var html = doc.body.innerHTML;
//    if(html != ''){
////将获取到的json数据转为json对象
//        var obj = eval("("+html+")");
////判断返回的状态
//        if(obj.status < 1){
//            alert(obj.msg);
//        }else{
//            alert(obj.msg);
//            window.location.href="customerPersonal.html";
//        }
//    }
//    window.location.href="customerPersonal.html";
//}
