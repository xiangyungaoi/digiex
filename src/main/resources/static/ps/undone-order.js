var time1 = {
	elem:'#sellTime'
};
var chooseIndex=0;
$(function(){
	var thisURL = location.search;   //获取当前页面的 url
	if(thisURL){
		var getVal =(thisURL.split('?')[1]);
		var showVal= getVal.split("=")[1];
		var val = decodeURIComponent(showVal);
		var condition = JSON.parse(val);
		chooseIndex = condition.chooseIndex;
		getAiNameInfo(chooseIndex);
	}else{
		getAiNameInfo(chooseIndex);
	}
});

/**
 * 获取未完成订单表
 * @param data
 */
function undoneOrdersList(data) {
	$("#incomplete-orders").empty();
	$("#incomplete-orders").html(template("incomplete-orders-list", { "data": data}));
}

/**
 * 获取aiName
 */
var aiName;
var robotId;
function getAiNameInfo(cIndex) {
	doRequestwithnoheader({}, "get", urlData.queryRobotInfo, function (data) {
		$("#js-ai-name").html(template("js-ai-name-list", { "data": data.data}));
		$(".tab-list .tab").eq(cIndex).addClass('active');
		var id;
		id =  $(".tab-list .tab").eq(cIndex).data('id');
		aiName =  $(".tab-list .tab").eq(cIndex).data('name');
		robotId = id;
		doRequestwithnoheader({aiRobotId:id}, "get", urlData.selectMt5ByUndone, function (data) {
			undoneOrdersList(data.data.list);//未完成订单
			$(".js-down-order").on("click",function(){
				var fee = $(this).data("fee");
				var hands = $(this).data("hands");
				downOrder(robotId,fee,hands);
			});
			$(".js-undone-order-detail").on("click",function () {
				var orderId = $(this).data("id");
				var cutoff = $(this).data("cutoff");
				var hands = $(this).data("hands");
				var totalFee = $(this).data("fee");
				toUndoneOrderDetail(orderId,cutoff,hands,totalFee);
			});
		}, function (res) {
		}, "application/json");
		$(".tab-list .tab").click(function(){
			//用户名切换动态查询
			id = $(this).data("id");
			aiName =  $(this).data("name");
			robotId = id;
			var index = $(".tab-list .tab").index(this);
			chooseIndex = index;
			doRequestwithnoheader({aiRobotId:id}, "get", urlData.selectMt5ByUndone, function (data) {
				undoneOrdersList(data.data.list);//未完成订单
				$(".js-down-order").on("click",function(){
					var fee = $(this).data("fee");
					var hands = $(this).data("hands");
					downOrder(robotId,fee,hands);
				});
				$(".js-undone-order-detail").on("click",function () {
					var orderId = $(this).data("id");
					var cutoff = $(this).data("cutoff");
					var hands = $(this).data("hands");
					var totalFee = $(this).data("fee");
					toUndoneOrderDetail(orderId,cutoff,hands,totalFee);
				});
			}, function (res) {
			});
			$(this).addClass('active').siblings().removeClass("active");
		});
		$(".js-undone-order-detail").on("click",function () {
			location.href="";
		});
	}, function (res) {
	}, "application/json");
}

/**
 *跳转详情页面
 */
function toUndoneOrderDetail(orderId,cutoff,hands,totalFee) {
	var  condition = {};
	condition.orderId=orderId;
	condition.cutoff = cutoff;
	condition.hands = hands;
	condition.totalFee = totalFee;
	condition.aiName=aiName;
	condition.id=robotId;
	condition.chooseIndex = chooseIndex;
	location.href=("undone-details.html?orderId="+encodeURIComponent(encodeURIComponent(JSON.stringify(condition))));
}

/**
 * 订单页面的建仓
 * @param id
 * @param fee
 * @param hands
 * @param index1
 */
function downOrder(id,fee,hands){
		parent.layer.open({
			type:1,
			title:'建仓EURUSD',
			area:["640px","600px"],
			btn:false,
			move:false,
			closeBtn:0,
			skin:"Height332",
			content:template("down-order-main",{}),
			success:function (layero, index) {
				$(layero).find(".js-total-hands").html(hands);
				$(layero).find(".js-total-fee").html(fee + "$");

				parent.laydate.render({
					elem:'#buyTime',
					type:'datetime'
				});
				$(layero).find(".js-buy-sure").on("click",function () {
					var condition = {};
					condition.chooseIndex = chooseIndex;
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
					doRequestwithnoheader({aiRobotId:id,orderType:orderType,aiType:aiType,buyTime:buyTime,buyPrice:buyPrice,buyServiceFee:buyServiceFee}, "get", urlData.updateMt5UndoneOrder, function (data) {
						parent.layer.msg("建仓成功！");
						$(location).prop("href",basePath + "/undone-order.html?condition="+encodeURIComponent(JSON.stringify(condition)));
						parent.layer.close(index);
					}, function (res) {
					}, "application/json");
				});
			}
		})
}
