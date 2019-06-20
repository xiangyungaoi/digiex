var pno = 1;
var v;//全局订单详情值
$(function () {
    //var thisURL = decodeURIComponent(document.URL);   // 获取当前页面的 url, 用decodeURI() 解码
    var thisURL = location.search;   //获取当前页面的 url
    var getVal =(thisURL.split('?')[1]);
    var showVal= getVal.split("=")[1];
    var val = (decodeURIComponent(decodeURIComponent(showVal)));
    v=JSON.parse(val);
    positionDetailUserList(pno,JSON.parse(val));
});

function positionDetailUserList(n,detail) {
    eveningUpDetail({"pageNumber":n},detail);
}


/**
 * 持仓详情
 * @param detailInfo
 * @param params
 */
function eveningUpDetail(params,detailInfo) {
    $("#js-detail-aiName").html(detailInfo.aiName);//机器人名字
    doRequestwithnoheader({orderId:detailInfo.orderId}, "get", urlData.mt5OrderDetail, function (data) {

        $("#js-ai-type").html(data.data.aiType);
        $("#js-order-type").html(data.data.orderType);
        $("#js-detail-orderId").html(data.data.orderId);
        $("#js-detail-buyTime").html(data.data.buyTime);
        $("#js-detail-buyPrice").html(data.data.buyPrice);
        $("#js-detail-buyService").html("-" + data.data.buyServiceFee+"$");
        $("#js-detail-totalHands").html(data.data.totalStandardHands);
        $("#js-detail-totalFee").html(data.data.totalFee + "$");
        $("#js-detail-updateTime").html( data.data.updatedTime);

        $(".js-evening-up").on("click",function () {
            parent.layer.open({
                type:1,
                title:'平仓EURUSD',
                area:["640px","680px"],
                btn:false,
                move:false,
                closeBtn:0,
                skin:"Height512",
                content:template("evening-up-main",{}),
                success:function (layero, index) {
                    $(layero).find(".js-ai-type").html(data.data.aiType);
                    $(layero).find(".js-order-type").html(data.data.orderType);
                    $(layero).find(".js-total-fee").html("$" + data.data.totalFee);
                    $(layero).find(".js-total-hands").html(data.data.totalStandardHands);
                    $(layero).find(".js-buy-price").html(data.data.buyPrice);
                    $(layero).find(".js-buy-service-price").html("-$" +　data.data.buyServiceFee);

                    parent.laydate.render({
                        elem:'#sellTime',
                        type:'datetime'
                    });
                    $(layero).find(".js-sell-sure").on("click",function () {
                        var condition = {};
                        condition.chooseIndex = v.chooseIndex;
                        if(helper.isEmpty($(layero).find("input[name='sellTime']").val())){
                            parent.layer.msg("请选择平仓时间！");
                            return false;
                        }else if(helper.isEmpty($(layero).find("input[name='sellPrice']").val())){
                            parent.layer.msg("请输入平仓点数！");
                            return false;
                        }else if(helper.isEmpty($(layero).find("input[name='sellServiceFee']").val())){
                            parent.layer.msg("请输入平仓手续费！");
                            return false;
                        }else if(helper.isEmpty($(layero).find("input[name='inventoryFee']").val())){
                            parent.layer.msg("请输入库存费！");
                            return false;
                        }else if(helper.isEmpty($(layero).find("input[name='totalWarnings']").val())){
                            parent.layer.msg("请输入总平仓收益！");
                            return false;
                        }

                        var sellPrice = $(layero).find("input[name='sellPrice']").val();
                        var sellServiceFee = $(layero).find("input[name='sellServiceFee']").val();
                        var totalWarnings = $(layero).find("input[name='totalWarnings']").val();
                        var inventoryFee = $(layero).find("input[name='inventoryFee']").val();
                        var sellTime = $(layero).find("input[name='sellTime']").val();
                        var orderId = detailInfo.orderId;
                        doRequestwithnoheader({orderId:orderId,sellTime:sellTime,sellPrice:sellPrice,sellServiceFee:sellServiceFee,inventoryFee:inventoryFee,totalEarnings:totalWarnings}, "get", urlData.updateMt5OrderAndPosition, function (data) {
                            parent.layer.msg("平仓成功！");
                            $(location).prop("href",basePath + "/positions-order.html?condition="+encodeURIComponent(JSON.stringify(condition)));
                            parent.layer.close(index);
                        }, function (res) {
                        }, "application/json");
                    });
                }
            });
        });
    }, function (res) {
    }, "application/json");
    var req={"pageNumber":params.pageNumber,"pageSize":$rows,orderId:detailInfo.orderId};
    doRequestwithnoheader(req, "get", urlData.userOrdersByMT5OrderId, function (data) {
        $("#position-order-detail").html(template("position-order-detail-list", { "data": data.data.list}));
        var $page =  Math.ceil(parseInt(data.data.total)/5);
        kkpage(params.pageNumber,$page, data.data.total, function(n){
            positionDetailUserList(n,v);
            pno = n;
        });
    }, function (res) {
    }, "application/json");
}

