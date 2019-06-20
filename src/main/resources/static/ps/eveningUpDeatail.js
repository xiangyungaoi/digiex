var pno = 1;
var v;//全局订单详情值
$(function () {
    //var thisURL = decodeURIComponent(document.URL);   // 获取当前页面的 url, 用decodeURI() 解码
    var thisURL = location.search;   //获取当前页面的 url
    var getVal =(thisURL.split('?')[1]);
    var showVal= getVal.split("=")[1];
    var val = (decodeURIComponent(decodeURIComponent(showVal)));
    v=JSON.parse(val);
    eveningUPDetailList(pno,JSON.parse(val));
});

function eveningUPDetailList(n,detailInfo) {
    eveningUPDetail({"pageNumber":n},detailInfo);
}

/**
 *  平仓详情
 * @param detailInfo
 * @param params
 */
function eveningUPDetail(params,detailInfo) {
    $("#evening-up-aiName").html(detailInfo.aiName);//机器人名字
    doRequestwithnoheader({orderId:detailInfo.orderId}, "get", urlData.mt5OrderDetail, function (data) {
        $(".js-ai-type").html(data.data.aiType);
        $(".js-order-type").html(data.data.orderType);
        $(".js-evening-orderId").html(data.data.orderId);
        $(".js-evening-buyTime").html(data.data.buyTime);
        $(".js-evening-buyPrice").html(data.data.buyPrice);
        $(".js-evening-buyServiceFee").html("-" + data.data.buyServiceFee + "$");
        $(".js-evening-totalHands").html(data.data.totalStandardHands);
        $(".js-evening-totalFee").html(data.data.totalFee + "$");
        $(".js-evening-sellTime").html( data.data.sellTime);
        $(".js-evening-sellPrice").html( data.data.sellPrice);
        $(".js-evening-sellServicePrice").html("-" + data.data.sellServiceFee + "$");
        $(".js-inventory-fee").html("-" + data.data.inventoryFee + "$");
        $(".js-evening-totalWarnings").html(data.data.totalEarnings + "$");
    }, function (res) {
    }, "application/json");
    var req={"pageNumber":params.pageNumber,"pageSize":$rows,"orderId":detailInfo.orderId};
    doRequestwithnoheader(req, "get", urlData.userOrdersByMT5OrderId, function (data) {
        $("#user-order-detail").html(template("user-order-detail-list", { "data": data.data.list}));
        var $page =  Math.ceil(parseInt(data.data.total)/5);
        kkpage(params.pageNumber,$page, data.data.total, function(n){
            eveningUPDetailList(n,v);
            pno = n;
        });
    }, function (res) {
    }, "application/json");
}