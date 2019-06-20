var pno = 1;
var initIndex =0;
var chooseIndex =0;
$(function(){
    var thisURL = location.search;   //获取当前页面的 url
    if(thisURL){
        var getVal =(thisURL.split('?')[1]);
        var showVal= getVal.split("=")[1];
        var val = decodeURIComponent(showVal);
        chooseIndex = JSON.parse(val).chooseIndex;
        $(".user-nav-list .user-nav").eq(2).addClass("active").siblings().removeClass("active");
        getAiNameListInfo(pno,JSON.parse(val).chooseIndex);
    }else{
        getAiNameListInfo(pno,initIndex);
    }
});

function getAiNameListInfo(n,i) {
    getAiNameInfo({"pageNumber":n,"chooseIndex":i});
}

/**
 * 平仓订单
 * @param data
 */
function eveningUpOrder(data) {
    $("#evening-up-order").empty();
    $("#evening-up-order").html(template("evening-up-order-list", { "data": data}));
}

/**
 * 获取aiName
 */
var aiName;
function getAiNameInfo(params) {
    doRequestwithnoheader({}, "get", urlData.queryRobotInfo, function (data) {
        $("#js-ai-name").html(template("js-ai-name-list", { "data": data.data}));
        params.chooseIndex = chooseIndex;
        $(".tab-list .tab").eq(params.chooseIndex).addClass('active');
        var id;
        id =  $(".tab-list .tab").eq(params.chooseIndex).data('id');
        aiName =  $(".tab-list .tab").eq(params.chooseIndex).data('name');
        var req={"pageNumber":params.pageNumber,"pageSize":$rows,aiRobotId:id};
        doRequestwithnoheader(req, "get", urlData.selectMt5BySell, function (data) {
            eveningUpOrder(data.data.list);//平仓订单
            var $page =  Math.ceil(parseInt(data.data.total)/5);
            kkpage(params.pageNumber,$page, data.data.total, function(n){
                getAiNameListInfo(n,params.chooseIndex);
                pno = n;
            });
            $(".js-evening-up-detail").on("click",function () {
                var orderId = $(this).data("id");
                toEveningOrderDetail(orderId);
            });
        }, function (res) {
        }, "application/json");
        $(".tab-list .tab").click(function(){
            pno = 1;
           // params.pageNumber = 1;
            //用户名切换动态查询
            id = $(this).data("id");
            aiName = $(this).data("name");
            //$(".tab-list .tab").eq(params.chooseIndex).addClass('active').siblings().removeClass("active");
            var index = $(".tab-list .tab").index(this);
            params.chooseIndex = index;
            chooseIndex = index;
            var req={"pageNumber":params.pageNumber,"pageSize":$rows,aiRobotId:id};
            doRequestwithnoheader(req, "get", urlData.selectMt5BySell, function (data) {
                eveningUpOrder(data.data.list);//平仓订单
                var $page =  Math.ceil(parseInt(data.data.total)/5);
                kkpage(params.pageNumber,$page, data.data.total, function(n){
                    getAiNameListInfo(n,params.chooseIndex);
                    pno = n;
                });
                $(".tab-list .tab").eq(params.chooseIndex).addClass('active').siblings().removeClass("active");
                $(".js-evening-up-detail").on("click",function () {
                    var orderId = $(this).data("id");
                    toEveningOrderDetail(orderId);
                });

            }, function (res) {
            }, "application/json");
           // $(this).addClass('active').siblings().removeClass("active");
        });
    }, function (res) {
    }, "application/json");
}

/**
 *跳转详情页面
 */
function toEveningOrderDetail(orderId) {
    var condition = {};
    condition.orderId=orderId;
    condition.aiName=aiName;
    location.href=("evening-up-details.html?orderId="+encodeURIComponent(encodeURIComponent(JSON.stringify(condition))));
}