$(function () {
    
});

// /**
//  * 获取aiName
//  */
// function getAiNameInfo(status) {
//     doRequestwithnoheader({}, "get", urlData.queryRobotInfo, function (data) {
//         console.log("aiNameList : " + JSON.stringify(data.data));
//         $("#js-ai-name").html(template("js-ai-name-list", { "data": data.data}));
//         $(".tab-list .tab").eq(0).addClass('active');
//         var id;
//          id =  $(".tab-list .tab").eq(0).data('id');
//         doRequestwithnoheader({id:id,status:status}, "get", urlData.selectMt5ByUndone, function (data) {
//             console.log("aiNameList : " + JSON.stringify(data.data));
//             nudoneOrdersList(data.data);//未完成订单
//             positionsOrderList(data.data);//持仓订单
//             eveningUpOrder(data.data);//平仓订单
//         }, function (res) {
//         }, "application/json");
//         $(".tab-list .tab").click(function(){
//             //用户名切换动态查询
//             id = $(this).data("id");
//             doRequestwithnoheader({id:id,status:status}, "get", urlData.selectMt5ByUndone, function (data) {
//                 console.log("aiNameList : " + JSON.stringify(data.data));
//                 nudoneOrdersList(data.data);//未完成订单
//                 positionsOrderList(data.data);//持仓订单
//                 eveningUpOrder(data.data);//平仓订单
//             }, function (res) {
//             }, "application/json");
//             $(this).addClass('active').siblings().removeClass("active");
//         });
//     }, function (res) {
//     }, "application/json");
// }