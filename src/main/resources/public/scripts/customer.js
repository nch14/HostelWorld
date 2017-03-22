/**
 * Created by mr.meng on 16/12/19.
 */

$(document).ready(function() {

    //initSaleLineChart();

});

function initSaleLineChart(){

}

var imageList = [];
var image = '';
var tempList=[1];
var formData = new FormData();


function selectImage(file){
    //alert("selectImage!"+file.id);

    var imageId= file.id

    //alert(imageId);

    if(!file.files || !file.files[0]){
        return;
    }
    var reader = new FileReader();
    reader.onload = function(evt){
        document.getElementById('image'+imageId).src = evt.target.result;
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
    alert("ia is "+ia);
    return new Blob(ia, {type:mimeString});
}



function toIndex(){
    window.location.href="customerIndex.html";
}

function toReservation(){
    window.location.href="/member/myOrderPage";
}

function toHistory(){
    window.location.href="/customerHistory.html";
}

function toPersonal(){
    window.location.href="../customerPersonal.html";
}


function toIndexManagement(){
    window.location.href="indexManagement.html";
}

function toOrdersManagement(){
    window.location.href="ordersManagement.html";
}

function toSaleManagement(){
    window.location.href="saleManagement.html";
}

function toSalersManagement(){
    window.location.href="../customerPersonal.html";
}



