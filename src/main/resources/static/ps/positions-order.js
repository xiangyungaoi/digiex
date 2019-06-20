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
 * 持仓订单列表
 * @param data
 */
function positionsOrderList(data) {
	$("#position-orders").empty();
	$("#position-orders").html(template("position-orders-list", { "data": data}));
}
/**
 * 获取aiName
 */
var aiName;
function getAiNameInfo(cIndex) {
	doRequestwithnoheader({}, "get", urlData.queryRobotInfo, function (data) {
		$("#js-ai-name").html(template("js-ai-name-list", { "data": data.data}));
		$(".tab-list .tab").eq(cIndex).addClass('active');
		var id;
		id =  $(".tab-list .tab").eq(cIndex).data('id');
		aiName =  $(".tab-list .tab").eq(cIndex).data('name');
		doRequestwithnoheader({"aiRobotId":id}, "get", urlData.selectMt5ByBuy, function (data) {
			positionsOrderList(data.data.list);//持仓订单
			$(".js-evening-up").on("click",function () {
				var orderId = $(this).data("order");
				doRequestwithnoheader({"orderId":orderId.toString()}, "get", urlData.mt5OrderDetail, function (data) {
					eveningU0p(orderId,data.data);
				}, function (res) {
				}, "application/json");
			});
			$(".js-position-order-detail").on("click",function () {
				var orderId = $(this).data("id");
				toPositionOrderDetail(orderId);
			});
		}, function (res) {
		}, "application/json");
		$(".tab-list .tab").click(function(){
			//用户名切换动态查询
			id = $(this).data("id");
			aiName =  $(this).data("name");
			var index = $(".tab-list .tab").index(this);
			chooseIndex = index;
			doRequestwithnoheader({aiRobotId:id}, "get", urlData.selectMt5ByBuy, function (data) {
				positionsOrderList(data.data.list);//持仓订单
				$(".js-evening-up").on("click",function () {
					var orderId = $(this).data("order");
					doRequestwithnoheader({"orderId":orderId.toString()}, "get", urlData.mt5OrderDetail, function (data) {
						//console.log("详情 : " + JSON.stringify(data.data));
						eveningU0p(orderId,data.data);
					}, function (res) {
					}, "application/json");
				});
				$(".js-position-order-detail").on("click",function () {
					var orderId = $(this).data("id");
					toPositionOrderDetail(orderId);
				});
			}, function (res) {
			}, "application/json");
			$(this).addClass('active').siblings().removeClass("active");
		});
	}, function (res) {
	}, "application/json");
}

/**
 *跳转详情页面
 */
function toPositionOrderDetail(orderId) {
	var condition = {};
	condition.orderId=orderId;
	condition.aiName=aiName;
	condition.chooseIndex = chooseIndex;
	location.href=("positions-dateils.html?orderId="+encodeURIComponent(encodeURIComponent(JSON.stringify(condition))));
}

/**
 * 平仓
 * @param orderId
 * @param fee
 * @param hands
 * @param price
 * @param service
 * @param index
 */
function eveningU0p(orderId,data) {
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
			$(layero).find(".js-ai-type").html(data.aiType);
			$(layero).find(".js-order-type").html(data.orderType);
			$(layero).find(".js-total-fee").html(data.totalFee +  "$");
			$(layero).find(".js-total-hands").html(data.totalStandardHands);
			$(layero).find(".js-buy-price").html(data.buyPrice);
			$(layero).find(".js-buy-service-price").html("-" +　data.buyServiceFee + "$");

			parent.laydate.render({
				elem:'#sellTime',
				type:'datetime'
			});
			$(layero).find(".js-sell-sure").on("click",function () {
				var condition ={};
				condition.orderId=orderId;
				condition.chooseIndex = chooseIndex;
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

				doRequestwithnoheader({"orderId":orderId.toString(),sellTime:sellTime,sellPrice:sellPrice,sellServiceFee:sellServiceFee,inventoryFee:inventoryFee,totalEarnings:totalWarnings}, "get", urlData.updateMt5OrderAndPosition, function (data) {
					parent.layer.msg("平仓成功！");
					$(location).prop("href",basePath + "/positions-order.html?condition="+encodeURIComponent(JSON.stringify(condition)));
					parent.layer.close(index);
				}, function (res) {
				}, "application/json");
			});
		}
	})
}