var pno = 1;
$(function(){
	aiManagementList(pno);//加载列表
});

function aiManagementList(n) {
	aiManagement({"pageNumber":n})
}

/**
 * ai管理列表
 */
function aiManagement(params) {
	var req={"pageNumber":params.pageNumber,"pageSize":$rows};
	doRequestwithnoheader(req,"get",urlData.aiRobotList,function (data) {
		$("#aiRobotList").html(template("aiRobotList-info", { "data": data.data.list}));
		var $page =  Math.ceil(parseInt(data.data.total)/5);
		kkpage(params.pageNumber,$page, data.data.total, function(n){
			aiManagementList(n);
			pno = n;
		});
		$(".js-agreement").on("click",function(){
			var url = $(this).data("id");
			var title = $(this).data("title");
			layer.open({
				type : 2,
				title:title,
				area:["650px","600px"],
				btn:false,
				move:false,
				skin:"Height412",
				content:url,
				success:function (layero, index) {
				}
			})
		});
		$(".js-add").on("click",function(){
			var type = $(this).data("type");
			var id = $(this).data("id");
			var title;
			if(type === "add"){
				title="添加"
			}else {
				title = "编辑"
			}
			layer.open({
				title:title,
				area:["630px","500px"],
				btn:false,
				move:false,
				closeBtn:0,
				skin:"Height412",
				content:template("add-main",{}),
				success:function (layero, index) {
					var featuresList;
					doRequestwithnoheader({},"get",urlData.labelList,function (data) {
						featuresList = data.data;
						$("#feature-info").html(template("feature-info-list", { "data": data.data}));
					},function (res) {},"application/json");
					if(type === "add"){
						initUploader($(layero).find("#file"), true);
					}else if(type === "edit"){
						initUploader($(layero).find("#file"), true);
						doRequestwithnoheader({id:id},"get",urlData.viewRobotInfo,function (data) {
							$(layero).find("input[name='ai-name']").val(data.data.name);
							$(layero).find("input[name='ai-type']").val(data.data.aiType);
							$(layero).find("input[name='ai-type-name']").val(data.data.aiTypeName);
							$(layero).find("input[name='agreement-name']").val(data.data.agreementName);
							$(layero).find("input[name='leverageTimes']").val(data.data.leverageTimes);
							$(layero).find("input[name='depositRate']").val(data.data.depositRate);
							$(layero).find("input[name='feeRate']").val(data.data.feeRate);
							$(layero).find("input[name='stopLossLimit']").val(data.data.stopLossLimit);
							$(layero).find("#fileUrl").val(data.data.agreementUrl);
							//$(layero).find("#file").val(data.data.agreementUrl);
							if(data.data.isActivated ===true){
								$(layero).find("#open").prop("checked",true);
							}else{
								$(layero).find("#close").prop("checked",true);
							}
							var feature = data.data.features;
							var labelArr = $(layero).find("input[name = 'label-name']");
							if (!helper.isEmpty(feature)) {
								var splitFeature = feature.split(",");
								for(var i in splitFeature){
									$(labelArr).each(function(){
										if($(this).data("name") === splitFeature[i]){
											$(this).attr("checked","checked");
										}
									});
								}
							}
						},function (res) {},"application/json")
					}

					$(".js-sure").on("click",function () {
						var open = $(layero).find("#open").prop("checked");
						var close = $(layero).find("#close").prop("checked");
						var manage = {};
						if (helper.isEmpty($(layero).find("input[name = 'ai-name']").val())){
							layer.msg("ai名称不能为空！");
							return false;
						} else if (helper.isEmpty($(layero).find("input[name = 'ai-type']").val())){
							layer.msg("币种不能为空！");
							return false;
						} else if (helper.isEmpty($(layero).find("input[name = 'ai-type-name']").val())){
							layer.msg("币种中文名称不能为空！");
							return false;
						} else if (helper.isEmpty($(layero).find("input[name = 'agreement-name']").val())){
							layer.msg("协议名称不能为空！");
							return false;
						} else if (helper.isEmpty($(layero).find("#fileUrl").val())){
							layer.msg("协议路径名称不能为空！");
							return false;
						} else if (open === false && close === false){
							layer.msg("请选择开启或关闭！");
							return false;
						} else if (helper.isEmpty($(layero).find("input[name = 'leverageTimes']").val())){
							layer.msg("请填写杠杆率！！");
							return false;
						} else if (helper.isEmpty($(layero).find("input[name = 'depositRate']").val())){
							layer.msg("请填写初始保证金！");
							return false;
						} else if (helper.isEmpty($(layero).find("input[name = 'feeRate']").val())){
							layer.msg("请填写本金！");
							return false;
						} else if (helper.isEmpty($(layero).find("input[name = 'stopLossLimit']").val())) {
							layer.msg("请填写终止跟单触发条件！");
							return false;
						}

						var featureArr = $(layero).find("input[name = 'label-name']");
						var features = "";
						var len = featureArr.filter(":checked").length;
						if(len > 3){
							layer.msg("最多可选三个AI标签！");
							return false;
						}
						for(var k in featureArr){
							if(featureArr[k].checked === true){
								features += featureArr[k].value + ","
							}
						}
						console.log("arr : " + features.substring(0,features.length-1));
						manage.name = $(layero).find("input[name='ai-name']").val();
						manage.aiType = $(layero).find("input[name='ai-type']").val();
						manage.aiTypeName = $(layero).find("input[name='ai-type-name']").val();
						manage.agreementName = $(layero).find("input[name='agreement-name']").val();
						manage.leverageTimes = $(layero).find("input[name = 'leverageTimes']").val();
						manage.depositRate = $(layero).find("input[name = 'depositRate']").val();
						manage.feeRate = $(layero).find("input[name = 'feeRate']").val();
						manage.stopLossLimit = $(layero).find("input[name = 'stopLossLimit']").val();
						manage.agreementUrl = $(layero).find("#fileUrl").val();
						manage.features = features.substring(0,features.length-1);
						if(open === true){
							manage.isActivated=true;
						}else{
							manage.isActivated=false;
						}
						if(type === "edit"){
							manage.id =  id;
							doRequestwithnoheader(JSON.stringify(manage), "POST", urlData.modifyRobotInfo, function (data) {
								layer.msg("修改成功");
								layer.close(index);
								aiManagementList(1);
							}, function (res) {
							}, "application/json");
						}else if(type === "add"){
							doRequestwithnoheader(JSON.stringify(manage), "POST", urlData.addAiRobot, function (data) {
								layer.msg("新增成功");
								layer.close(index);
								aiManagementList(1);
							}, function (res) {
							}, "application/json");
						}
					});
					$(".js-cancel").on("click",function () {

					});
				}
			})
		});
		$(".js-delete").on("click",function(){
			var id = $(this).data("id");
			var name = $(this).data("name");
			layer.open({
				title:'删除',
				area:["480px","238px"],
				btn:false,
				move:false,
				closeBtn:0,
				skin:"Height155",
				content:template("delete-main",{}),
				success:function (layero, index) {
					$(layero).find("#delete-ai-name").html(name);
					$(".js-delete-sure").on("click",function () {
						doRequestwithnoheader({id:id},"get",urlData.delAiRobot,function (data) {
							layer.msg("删除成功");
							aiManagementList(1);
						},function (res) {},"application/json")
					});
				}
			})
		});
	},function (res) {},"application/json")
}

/**
 * 判断是否为正整数
 * @param $this
 */
function carNum($this){
	//输入框的值
	var value = $this.val() ;

	if (isNaN(value)) {//判断值是不是数字
		$this.val(1) ;
	} else if (value === "") {//这是当只有1位的时候，删除这个会进入这个判断，如果没有该判断，当只有一位的时候就不能删除
		$this.val(1) ;
	} else if (value === 0) {//判断值是不是0
		$this.val(1) ;
	} else if(value.indexOf(".") !== -1) {//判断有没有输入小数点
		$this.val(value.substring(0,value.indexOf(".")))
	}
}