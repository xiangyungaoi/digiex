var pno = 1;
var v;//全局订单详情值
$(function () {
   var thisURL = location.search;   //获取当前页面的 url
    var getVal =(thisURL.split('?')[1]);
    var showVal= getVal.split("=")[1];
    var val = (decodeURIComponent(decodeURIComponent(showVal)));
    v=JSON.parse(val);
    orderDetailUserList(pno,JSON.parse(val));
});

function orderDetailUserList(n,detail) {
    orderDetail({"pageNumber":n},detail);
}

/**
 * aiName和订单详情信息
 * @param detailInfo 订单详情信息
 * @param params
 */
function orderDetail(params,detailInfo) {
    $("#js-detail-aiName").html(detailInfo.aiName);//机器人名字
    $("#js-detail-orderId").html(detailInfo.orderId);//订单ID
    $("#js-detail-cutoff").html(detailInfo.cutoff);//截止日期
    $("#js-detail-totalHands").html(detailInfo.hands);//总手数
    $("#js-detail-totalFee").html(detailInfo.totalFee+ "$");//总金额
    var req={"pageNumber":params.pageNumber,"pageSize":$rows,aiRobotId:detailInfo.id};
    doRequestwithnoheader(req, "get", urlData.selectMt5ByUndoneInfoView, function (data) {
        $("#undone-order-detail").html(template("undone-order-detail-list", { "data": data.data.list}));
        var $page =  Math.ceil(parseInt(data.data.total)/5);
        kkpage(params.pageNumber,$page, data.data.total, function(n){
            orderDetailUserList(n,v);
            pno = n;
        });
    }, function (res) {
    }, "application/json");
    $(".js-down-order").on("click",function () {
        parent.layer.open({
            type:1,
            title: '建仓EURUSD',
            area: ["640px", "600px"],
            btn: false,
            move: false,
            closeBtn: 0,
            skin: "Height332",
            content: template("down-order-main", {}),
            success: function (layero, index) {
                layero.find(".js-total-hands").html(detailInfo.hands);
                layero.find(".js-total-fee").html("$" + detailInfo.totalFee);
                parent.laydate.render({
                    elem:'#buyTime',
                    type:'datetime'
                });
                $(layero).find(".js-buy-sure").on("click",function () {
                    var condition = {};
                    condition.chooseIndex = v.chooseIndex;
                    if(helper.isEmpty($(layero).find("#direction").val())){
                        parent.layer.msg("请选择方向！");
                        return false;
                    }else if(helper.isEmpty($(layero).find("input[name='currency']").val())){
                        parent.layer.msg("请输入币种！");
                        return false;
                    }else if(helper.isEmpty($(layero).find("input[name='buyTime']").val())){
                        parent.layer.msg("请选择建仓时间！");
                        return false;
                    }else if(helper.isEmpty( $(layero).find("input[name='buyPrice']").val())){
                        parent.layer.msg("请输入建仓点数！");
                        return false;
                    }else if(helper.isEmpty($(layero).find("input[name='buyServiceFee']").val())){
                        parent.layer.msg("请输入建仓手续费！");
                        return false;
                    }
                    var buyPrice = $(layero).find("input[name='buyPrice']").val();
                    var buyServiceFee = $(layero).find("input[name='buyServiceFee']").val();
                    var buyTime = $(layero).find("input[name='buyTime']").val();
                    var aiType = $(layero).find("input[name='currency']").val();
                    var direction = $(layero).find("#direction").val();
                    var orderType = null;
                    if(parseInt(direction )=== 0){
                        orderType = "buy";
                    }else{
                        orderType = "sell";
                    }
                    doRequestwithnoheader({aiRobotId:detailInfo.id,orderType:orderType,aiType:aiType,buyTime:buyTime,buyPrice:buyPrice,buyServiceFee:buyServiceFee}, "get", urlData.updateMt5UndoneOrder, function (data) {
                        parent.layer.msg("建仓成功！");
                        $(location).prop("href",basePath + "/undone-order.html?condition="+encodeURIComponent(JSON.stringify(condition)));
                        parent.layer.close(index);
                    }, function (res) {
                    }, "application/json");
                });

            }, end: function (index, layero) {

            }
        });
    });

}
