/**
 * Created by mr.meng on 17/2/5.
 */

$(document).ready(function(){
    getUrl();
});

var salerId;

function getUrl(){
    var url = location.href;
    var tmp1 = url.split("?")[1];
    var tmp2 = tmp1.split("&")[0];
    var tmp3 = tmp2.split("=")[1];
    var tmp4 = tmp3.split("#")[0];
    salerId=tmp4;
    newStore();
}
function newStore() {
    //alert("new!");
    $.ajax({
        url: "https://we.chenhaonee.cn/v1/store/url",
        type: "GET",
        data: {
            storeId:salerId
        },
        dataType: 'JSON',
        async: false,
        success: function (data) {
            var urlInput = document.getElementById("urlInput");
            urlInput.value=data.data;
            //alert(data.data);

        }
    });

}

function copyUrl(){
    //var storeUrl = document.getElementById("urlInput");
    //	if(window.clipboardData) { // Internet Explorer
    //		window.clipboardData.setData("Text", storeUrl.value);
    //
    //	}else {
    //		unsafeWindow.netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
    //		const clipboardHelper = Components.classes["@mozilla.org/widget/clipboardhelper;1"].getService(Components.interfaces.nsIClipboardHelper);
    //		clipboardHelper.copyString(storeUrl.value);
    //	}

    //	alert("您的商店链接已复制到剪贴板！");
    var copyTextarea = document.querySelector('#urlInput');
    copyTextarea.select();
    try {
        var successful = document.execCommand('copy');
        //var msg = successful ? 'successful' : 'unsuccessful';
        //	  console.log('Copying text command was ' + msg);
        alert("您的商店链接已复制到剪贴板！");
    } catch(err) {
        console.log('Oops, unable to copy');
        alert("复制失败，请重试！");
    }
}