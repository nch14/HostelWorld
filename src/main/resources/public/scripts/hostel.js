/**
 * Created by mr.meng on 16/12/19.
 */

$(document).ready(function () {

    //initSaleLineChart();

});

function initSaleLineChart() {

}

var imageList = [];
var image = '';
var tempList = [1];
var formData = new FormData();


function selectImage(file) {
    //alert("selectImage!"+file.id);

    var imageId = file.id

    //alert(imageId);

    if (!file.files || !file.files[0]) {
        return;
    }
    var reader = new FileReader();
    reader.onload = function (evt) {
        document.getElementById('image' + imageId).src = evt.target.result;
        image = evt.target.result;
        var fd = new FormData();
        //var blob = dataURItoBlob(image);
        //alert(blob);
        //imageList.push(image);
        //imageList.push("123456");
        //alert(image);
        //alert("list length "+ imageList);
        //formData.append('goodDisplayImgs',imageList );
        //alert("top fd is "+fd.file);
        //imageList.push(fd);
        //alert("push is finished!");
    }
    reader.readAsDataURL(file.files[0]);
}


function dataURItoBlob(base64Data) {
    var byteString;
    if (base64Data.split(',')[0].indexOf('base64') >= 0)
        byteString = atob(base64Data.split(',')[1]);
    else
        byteString = unescape(base64Data.split(',')[1]);
    var mimeString = base64Data.split(',')[0].split(':')[1].split(';')[0];
    var ia = new Uint8Array(byteString.length);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }
    alert("ia is " + ia);
    return new Blob(ia, {type: mimeString});
}


function toInfo() {
    window.location.href = "/hostelInfo.html";
}

function toCheckin() {
    window.location.href = "/hostelCheckin.html";
}
function toCheckout() {
    window.location.href = "/hostelCheckout.html";
}

function toPlan() {
    window.location.href = "/hostelPlan.html";
}

function toToday(){
    window.location.href = "/hostelToday.html";

}

function toFinance() {
    window.location.href = "/hostelFinance.html";
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
    inputLeft.placeholder = "客人姓名";
    inputLeft.id = "inputLeft" + npdNum;
    //alert("inputLeft id is "+inputLeft.id);
    inputRight.placeholder = "身份证号";
    inputRight.id = "inputRight" + npdNum;
    //alert("inputRight id is "+inputRight.id);
    var closeBtn = document.createElement("button");
    closeBtn.setAttribute("class", "add-goods-close-btn");
    closeBtn.onclick = function () {
        closeThis(this);
    }
    closeBtn.innerHTML = "X";
    closeBtn.id = "c" + npdNum;

    newProductPerDiv.appendChild(inputLeft);
    newProductPerDiv.appendChild(inputRight);
    newProductPerDiv.appendChild(closeBtn);
    newProductPerDiv.id = "cusc" + npdNum;
    //newProductPerDiv.id="npd"+npdNum;
    npdNum++;
    addNewProperty.appendChild(newProductPerDiv);
}

function closeThis(thisDiv) {
    //DO SOMETHING

    //alert(npdNum);

    var addNewProperty = document.getElementById("addNewProperty");
    var toRemove = document.getElementById("cus" + thisDiv.id);
    addNewProperty.removeChild(toRemove);

    //npdNum--;
}

function toMember() {
    window.location.href="newMember.html";
}


function toManager() {
    window.location.href="joinAsInn.html";
}

