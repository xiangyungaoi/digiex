
$(function(){
	$(".js-user").load("user.html",function () {
	});
	$(".section").height($(window).height()-99);
	$(".sidebar").height($(window).height()-185);
	var orderDivIndex = 0;
	var silderW=$(".sidebar").width();
	var windowW= $(window).width();
	var warp = windowW-silderW-180+'px';
	$(".main-content").width(warp);
	$(".main-content").height($(".sidebar").height());
	//导航
	$(".iframeurl").click(function() {
		var cid = $(this).attr("name");
		var cname = $(this).attr("title");
		var chref = $(this).attr('href');
		$("#iframe").attr("src", cid).ready();
	});
	//导航高亮
	$(".user-nav-list .user-nav").on("click",function(){
		 orderDivIndex = $(".user-nav-list .user-nav").index(this);
		//console.log("orderFIVIndex : " + orderDivIndex);
		$(this).addClass("active").siblings().removeClass("active");
	});


});
