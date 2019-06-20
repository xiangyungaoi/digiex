var basePath='/digiex';
//var basePath='http://192.168.1.6:8092/digiex';//cs

var baseProjectPath = '/digiex';//项目名称
// var baseProjectPath = '/digiex';

$(function(){
	//关闭罩层
	$(".Close").unbind("click").bind("click",function(){
		$('.mask',parent.document).hide();
//		$('#navbar',parent.document).hide();
	});
	$(document).keydown(function(e){
		if(e.keyCode==27){
			$('.mask',parent.document).hide();
			$(".modal-backdrop").hide();
		}
	})
	window.onresize = function(){
	
	}
});
function mask(){
	$('.mask',parent.document).show();
	$('#navbar',parent.document).show();
}
function maskHidden(){
	$('.mask',parent.document).hide();
	$('#navbar',parent.document).hide();
}

/***二进制转换 ***/
function binary(num){
    var mun1 = num.toString(2);
    var arr = mun1.split("");
    var newArr = [];
    for(var i = arr.length;i>=0;i-- ){
        if(Number(arr[i]) > 0){
            newArr.unshift(arr.length-i-1);
        }
    }
    return newArr;
}

function change2unit(num){
	var tenThousand = 10000,
		hundredMillion = 100000000,
		str = "";
	if(num>=0&&num<tenThousand){
		return str = num + "个";
	}else if(num>=tenThousand&&num<hundredMillion){
		return str = num/tenThousand.toFixed(2) + "万";
	}else if(num>=hundredMillion){
		return str = num/hundredMillion.toFixed(2) + "亿";
	}
}
function changePopulation2unit(num){
	var tenThousand = 10000,
		hundredMillion = 100000000,
		str = "";
	if(num>=0&&num<tenThousand){
		return str = num + "万";
	}else if(num>=tenThousand&&num<hundredMillion){
		return str = num/tenThousand.toFixed(2) + "亿";
	}else if(num>=hundredMillion){
		return str = num/hundredMillion.toFixed(2) + "亿万";
	}
}

template.helper("change2unit",function(num){
	return change2unit(num);
})
template.helper("changePopulation2unit",function(num){
	return changePopulation2unit(num);
})
function strToJson(str) {
	var json = eval('(' + str + ')');
	return json;
}

/**
 * 字符串序列化为对象
 * @method str2Args
 * @param {String} 待分割字符串
 * @param {String} 分隔符
 */
var helper = {
	str2Args: function(query, split) {
		var args = {};
		query = query || '';
		split = split || '&';
		var pairs = query.split(split);
		for(var i = 0; i < pairs.length; i++) {
			var pos = pairs[i].indexOf('=');
			if(pos == -1) {
				continue;
			}
			var argname = pairs[i].substring(0, pos).replace(/amp;/, "");
			var value = pairs[i].substring(pos + 1);
			args[argname] = decodeURIComponent(value);
		}
		return args;
	},
	/**
	 * 将Object转换为字符串参数
	 * @method args2Str
	 * @param {Object} args 需要转换的对象
	 * @param {String} split 分隔符，默认是&
	 * @return String 字符串参数
	 */
	args2Str: function(args, split) {
		split = split || '&';
		var key, rtn = '',
			sp = '';
		for(key in args) {
			//value为空的忽略
			if(args[key]) {
				rtn += (sp + key + '=' + encodeURIComponent(args[key]));
				sp = split;
			}
		}
		return rtn;
	},
	jsonObjParams2Str: function(params, link) {
		if(!link) {
			link = "&";
		}
		var argsstr = '';
		for(key in params) {
			//value为空的忽略
			if(params[key]) {
				var tmpstr = key + '=' + params[key];
				tmpstr += '&';
				argsstr += tmpstr;
			}
		}
		return argsstr;
	},
	showMsg: function(msg) {
		toast.show(msg);
	},
	isWeiXin: function() {
		var ua = navigator.userAgent.toLowerCase();
		if(ua.match(/MicroMessenger/i) == "micromessenger") {
			return true;
		} else {
			return false;
		}
	},
	isHttps: function() {
		var url = window.location.href;
		var isHttps = url.toUpperCase().indexOf("HTTPS");
		if(isHttps > -1) {
			return true;
		}
		return false;
	},
	toLogin: function() {
		location.href = '/login.html';
	},
	gotoLogin: function() {
		top.document.location.href = baseProjectPath + '/login.html?ref=' + encodeURIComponent(location.href);//iframe刷新父集
	},
	getProtocol: function() {
		if(helper.isHttps()) {
			return "https"
		}
		return "http";
	},
	preventBackgroundScroll: function() {
		$("body,html").css({
			"overflow": "hidden"
		});
	},
	resumeBackgroundScroll: function() {
		$("body,html").css({
			"overflow": "auto"
		});
	},
	setTitle: function(t) {
		var $body = $('body');
		document.title = t;
		var $iframe = $('<iframe src="/fav.ico" height="0"></iframe>').on('load', function() {
			setTimeout(function() {
				$iframe.off('load').remove()
			}, 0)
		}).appendTo($body);
	},

	//****************************************************************
	//* 名　　称：IsEmpty
	//* 功    能：判断是否为空
	//* 入口参数：fData：要检查的数据
	//* 出口参数：True：空
	//*           False：非空
	//*****************************************************************
	isEmpty: function(v) {
		switch(typeof v) {
			case 'undefined':
				return true;
			case 'string':
				if(v.replace(/(^[ \t\n\r]*)|([ \t\n\r]*$)/g, '').length == 0) return true;
				if(v == "无") return true;
				break;
			case 'boolean':
				if(!v) return true;
				break;
			case 'number':
				if(0 === v || isNaN(v)) return true;
				break;
			case 'object':
				if(null === v || v.length === 0) return true;
				for(var i in v) {
					return false;
				}
				return true;
		}
		return false;
	},

	/***
	 * 本地化存储数据，
	 * @param key   string类型
	 * @param obj   存储非函数类型的对象，不深度拷贝
	 * @private
	 */
	_store: function(key, obj) {
		var value;
		// 因为open_id和手机号是关系断开的,所以,暂时用localstorage忽悠一下.
		//优先使用localstorege,其次使用cookie,
		if(window.localStorage) {
			if(obj) {
				if(typeof obj == "object") {
					window.localStorage.setItem(key, JSON.stringify(obj));
				} else if(typeof obj == "string") {
					window.localStorage.setItem(key, obj);
				} else {
					alert("_storeByBrowser:你存储的是非对象");
				}
			} else {
				try {
					value = JSON.parse(window.localStorage.getItem(key));
				} catch(e) {
					return window.localStorage.getItem(key);
				}
			}
		} else { //使用cookie
			TQ.cookie('test', 'test');
			if(!document.cookie || document.cookie == '') {
				//alert('请设置您的浏览器支持cookie以便正常访问');暂时放空
				if(obj) {
					value = TQ._store(key, obj);
				} else {
					value = TQ._store(key);
				}
			} else {
				if(obj) {
					if(typeof obj == "object") {
						value = TQ.cookie(key, JSON.stringify(obj));
					} else if(typeof obj == "string") {
						value = TQ.cookie(key, obj);
					} else {
						alert("_storeByBrowser:你存储的是非对象");
					}
				} else {
					try {
						value = JSON.parse(TQ.cookie(key));
					} catch(e) {
						value = TQ.cookie(key);
					}
				}
			}
		}
		return value;
	},
	setCookie: function(name, value) {
		var Days = 30; //此 cookie 将被保存 30 天
		var exp = new Date();
		exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
		document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();

	},
	
	getCookie: function(name) { //获取cookie
		var reg = eval("/(?:^|;\\s*)" + name + "=([^=]+)(?:;|$)/");
		return reg.test(document.cookie) ? RegExp.$1 : "";
	},
	delCookie: function (name) {
	    var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval=getCookie(name);
        if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toUTCString();
	},
	/**
	 * 获取url参数字段
	 */
	getUrlParams: function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); //匹配目标参数
		if(r != null) return decodeURIComponent(r[2]);
		return null; //返回参数值
	},
	getStrMatch: function(name, b) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = b.match(reg); //匹配目标参数
		if(r != null) return decodeURIComponent(r[2]);
		return null; //返回参数值
	},
	//获取字符串的长度(区分中英文)
	Bytelen: function(str) {
		return str.replace(/[^\x00-\xff]/g, "**").length;
	},
	isLogin: function() {
		var identityObj = helper._store("identity");
		if(!helper.isEmpty(identityObj)) {
			if(!helper.isEmpty(identityObj.user_id)) {
				return true;
			}
		}
		return false;
	},
	//计算对象成员个数
	countObjNum: function(o) {
		var n = 0;
		for(var i in o) {
			n++;
		}
		return n;
	},
	countTextNum: function(text, indexof) {
		var n = 0;
		for(i = 0; i < text.length; i++) {
			var c = text.charAt(i);
			if(c == indexof) {
				n += 1;
			}
		}
		return n;
	},
	/**
	 * 动态显示时间
	 * @param timetamp  时间差
	 * @param model  要显示时间的标签的ID属性
	 */
	getTimeStr: function(timetamp, model) {
		var day = "0";
		var hour = "0";
		var minute = "0";
		var second = "0";
		if(timetamp > 24 * 3600) {
			day = parseInt(timetamp / (24 * 60 * 60));
		}
		var dayMillisecond = day * 24 * 60 * 60;
		if((timetamp - dayMillisecond) > 60 * 60) {
			hour = parseInt((timetamp - dayMillisecond) / (60 * 60));
			hour = hour < 10 ? "0" + hour : hour;
		}
		var hourMillisecond = hour * 60 * 60;
		if((timetamp - dayMillisecond - hourMillisecond) > 60) {
			minute = parseInt((timetamp - dayMillisecond - hourMillisecond) / (60));
			minute = minute < 10 ? "0" + minute : minute;
		}
		var minuteMillisecond = minute * 60;
		if((timetamp - dayMillisecond - hourMillisecond - minuteMillisecond) > 1) {
			second = parseInt((timetamp - dayMillisecond - hourMillisecond - minuteMillisecond));
			second = second < 10 ? "0" + second : second;
		}
		time = timetamp - 1;
		var timeStr = day + "天" + hour + "时" + minute + "分" + second + "秒";
		$("#" + model).html(timeStr);
		if(time <= 0) {
			$("#" + model).html("活动已截止");
			window.clearInterval(clock);
		}
	},
	/**
	 * 展示公共关注弹层
	 */
	showPubGuanZhu: function() {},
	//获取字符串的长度(区分中英文)
	Bytelen: function(str) {
		return str.replace(/[^\x00-\xff]/g, "**").length;
	},
	ln2br: function(str) {
		if(!helper.isEmpty(str)) {
			while(str.indexOf("\n") > 0) {
				str = str.replace("\n", "<br>");
			}
			return str;
		}
		return "";
	}
};
// 数字转中文数字
var num_change = {
	ary0: ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"],
	ary1: ["", "十", "百", "千"],
	ary2: ["", "万", "亿", "兆"],
	init: function(name) {
		this.name = name;
	},
	strrev: function() {
		var ary = []
		for(var i = this.name.length; i >= 0; i--) {
			ary.push(this.name[i])
		}
		return ary.join("");
	}, //倒转字符串。
	pri_ary: function() {
		var $this = this
		var ary = this.strrev();
		var zero = ""
		var newary = ""
		var i4 = -1
		for(var i = 0; i < ary.length; i++) {
			if(i % 4 == 0) { //首先判断万级单位，每隔四个字符就让万级单位数组索引号递增
				i4++;
				newary = this.ary2[i4] + newary; //将万级单位存入该字符的读法中去，它肯定是放在当前字符读法的末尾，所以首先将它叠加入$r中，
				zero = ""; //在万级单位位置的“0”肯定是不用的读的，所以设置零的读法为空

			}
			//关于0的处理与判断。
			if(ary[i] == '0') { //如果读出的字符是“0”，执行如下判断这个“0”是否读作“零”
				switch(i % 4) {
					case 0:
						break;
						//如果位置索引能被4整除，表示它所处位置是万级单位位置，这个位置的0的读法在前面就已经设置好了，所以这里直接跳过
					case 1:
					case 2:
					case 3:
						if(ary[i - 1] != '0') {
							zero = "零"
						}; //如果不被4整除，那么都执行这段判断代码：如果它的下一位数字（针对当前字符串来说是上一个字符，因为之前执行了反转）也是0，那么跳过，否则读作“零”
						break;

				}

				newary = zero + newary;
				zero = '';
			} else { //如果不是“0”
				newary = this.ary0[parseInt(ary[i])] + this.ary1[i % 4] + newary; //就将该当字符转换成数值型,并作为数组ary0的索引号,以得到与之对应的中文读法，其后再跟上它的的一级单位（空、十、百还是千）最后再加上前面已存入的读法内容。
			}

		}
		if(newary.indexOf("零") == 0) {
			newary = newary.substr(1)
		} //处理前面的0
		return newary;
	}
}

//创建class类
function numChange() {
	this.init.apply(this, arguments);
}
numChange.prototype = num_change;

//瀑布流滚动加载
function _reachBottom() {
	var clientHeight = 0;
	var scrollHeight = 0;
	if(document.documentElement && document.documentElement.scrollTop) {
		scrollTop = document.documentElement.scrollTop;
	} else if(document.body) {
		scrollTop = document.body.scrollTop;
	}
	if(document.body.clientHeight && document.documentElement.clientHeight) {
		clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
	} else {
		clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
	}
	scrollHeight = Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
	if(scrollTop + clientHeight + 100 > scrollHeight && clientHeight != scrollHeight) {
		return true;
	} else {
		return false;
	}
}

function getRandomArrItem(arr) {
	if(helper.isEmpty(arr)) {
		return "";
	}
	return arr[Math.round(Math.random() * arr.length)];
}
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt) { //author: meizz
	var o = {
		"M+": this.getMonth() + 1, //月份
		"d+": this.getDate(), //日
		"h+": this.getHours(), //小时
		"m+": this.getMinutes(), //分
		"s+": this.getSeconds(), //秒
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度
		"S": this.getMilliseconds() //毫秒
	};
	if(/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
var showMsg = function(msg) {
	toast.show(msg);
};

function timeStampToDate(nS, f) {
	var newDate = new Date();
	newDate.setTime(parseInt(nS));
	if(!f) {
		f = 'yyyy-MM-dd';
	}
	return newDate.Format(f);
}

function dateToTimeStamp(stringTime) {
	//var stringTime = "2014-07-10 10:21:12";
	var timestamp2 = Date.parse(new Date(stringTime));
	timestamp2 = timestamp2 / 1000;
	//2014-07-10 10:21:12的时间戳为：1404958872
	//console.log(stringTime + "的时间戳为：" + timestamp2);
	//VM2548:6 2014-07-10 10:21:12的时间戳为：1404958872
	return timestamp2;
}
/**
 * 用于判断提交的参数是否为空（先弄个简单的吧，后面有时间了写个完美的）
 * @param req
 * @param config
 * @returns {boolean}
 */
function checkParamIsReady(req, config) {
	for(var p in req) {
		if(helper.isEmpty(req[p])) {
			try {
				if(config[p].required) {
					layer.msg(config[p].errorMsg);
					return false;
				}
			} catch(e) {

			}
		}
	}
	return true;
}


template.openTag = '<%';
template.closeTag = '%>';
template.helper('alert', function(obj) {
	return alert(obj);
});
template.helper('console', function(obj) {
	return console.log(obj);
});
template.helper('consoletype', function(obj) {
	return console.log(typeof obj);
});
template.helper('json2stringify', function(obj) {
	return JSON.stringify(obj);
});
template.helper('timeStampToDate', function(timestamp) {
	return timeStampToDate(timestamp, "yyyy-MM-dd hh:mm:ss");
});
template.helper('timeStampToDate2', function(timestamp) {
	return timeStampToDate(timestamp, "MM-dd hh:mm");
});
template.helper('timeStampToDate3', function(timestamp) {
	return timeStampToDate(timestamp, "yyyy-MM-dd hh:mm");
});
template.helper('timeStampToDate4', function(timestamp) {
	return timeStampToDate(timestamp, "yyyy-MM-dd");
});
template.helper('timeStampToDateFormart', function(timestamp, formart) {
	return timeStampToDate(timestamp, formart);
});
template.helper('toFixed', function(formart) {
	return toFixed(formart);
});

function toHHMM(minute) {
	var h = Math.floor(minute / 60);
	var m = minute % 60;
	h = h < 10 ? '0' + h : h;
	m = m < 10 ? '0' + m : m;
	return(h + ":" + m);
}
template.helper('timestamp2HHMM', function(minute) {
	var h = Math.floor(minute / 60);
	var m = minute % 60;
	h = h < 10 ? '0' + h : h;
	m = m < 10 ? '0' + m : m;
	return(h + ":" + m);
});
template.helper('setKuohao', function(val) {
	if(!helper.isEmpty(val)) {
		return "(" + val + ")";
	}
	return "";
});
template.helper('ln2br', function(val) {
	if(!helper.isEmpty(val)) {
		while(val.indexOf("\n") > 0) {
			val = val.replace("\n", "<br>");
		}
		return val;
	}
	return "";
});
template.helper('sex2text', function(type) {
	if(type == 1) {
		return "男";
	} else if(type == 0) {
		return "未知";
	} else {
		return "女";
	}
});
template.helper('text2Arr', function(str) {
	if(str == "" || !str || str == "[]") return "";
	var arr = [],
		start = str.indexOf("["),
		end = str.indexOf("]"),
		newStr = str.substr(start + 1, end - 2);
	if(newStr.indexOf(",") > 0) {
		arr = newStr.split(",");
	} else {
		arr.push(newStr);
	}
	return arr;
});
template.helper('textspace2list', function(str) {
	if(str == "" || !str) return "";
	var arr = str.split(",");
	return arr;
});
template.helper('textspaceToArr', function(str, s) {
	if(str == "" || !str) return "";
	var arr;
	if(s == "" || !s) {
		arr = str.split(" ");
	} else {
		arr = str.split(s);
	}

	return arr;
});
template.helper('arrayToString', function(arr, ch) {
	var str;
	if(!arr || arr.length == 0) return "";

	str = arr.join(ch);

	return str;
});
template.helper('isEmpty', function(obj) {
	return helper.isEmpty(obj);
});

template.helper('parseInt', function(val) {
	return parseInt(val);
});

// 分转元，并保留两位小数
template.helper('moneyChange', function(money) {
	var m;
	if(money == "" || !money) {
		m = 0.00;
	} else {	
		m = (money / 100).toFixed(2);
	}

	return m;
});
// 阿拉伯数字转中文数字
template.helper('numChange', function(num) {
	var number;
	if(num == "" || !num) {
		number = "";
	} else {
		var k = new numChange(num + '');
		number = k.pri_ary();
		if(number == "一十") {
			number = "十";
		}
	}
	return number;
});

template.helper('countObjNum', function(o) {
	var n = 0;
	for(var i in o) {
		n++;
	}
	return n;
});

function cuontNum(v) {
	var num = 0;
	switch(typeof v) {
		case 'undefined':
			return num;
		case 'string':
			if(v.replace(/(^[ \t\n\r]*)|([ \t\n\r]*$)/g, '').length == 0) {
				return num
			} else if(v.indexOf(",") != -1) {
				arr = v.split(",");
				return num = arr.length;
			};
			break;
	    case 'boolean':
	        if (!v) return num;
	        break;
	    case 'number':
	        if (0 === v || isNaN(v)) return num;
	        break;
	    case 'object':
	        if (null === v || v.length === 0) return num;
	        for (var i in v) {
	            return false;
	        }
	        return true;
	}
	return num;
}

function coutAge(timestamp) {
	var age,
		nowYear = (new Date()).getFullYear();
	age = nowYear - (new Date(timestamp)).getFullYear() + 1;
	return age;
}

//图片路径传参
function img2params(obj) {
	var imgLoad = "";
	var count = $(obj).length;
	if(count == 0) return imgLoad;
	for(var i = 0; i < count; i++) {
		imgLoad += obj.eq(i).attr("src") + ",";
	}
	imgLoad = imgLoad.substr(0, imgLoad.length - 1);
	//  imgLoad = "[" + imgLoad + "]";
	return imgLoad;
}

//数组传参
function object2params(obj) {
	var params = "";
	var count = $(obj).length;
	if(count == 0) return params;
	for(var i = 0; i < count; i++) {
		params += obj[i] + ",";
	}
	params = params.substr(0, params.length - 1);
	return params;
}

template.helper("params2img", function(str) {
	var imgStr = "";
	var rets = new Array();;
	if(str != '' && str.length > 4) {
		if(str.indexOf("[") != -1) {
			str = str.substring(1, str.length - 1);
		}
		if(str.indexOf(",") != -1) {
			str = str.replace(/\"/g, "");
			var splits = str.split(",");
			for(var i = 0, k = 0; i < splits.length; i++) {
				if(splits[i] != "") {
					rets[k++] = splits[i];
				}
			}
		} else {
			rets[0] = str;
		}
	}
	return rets;
});

function date2parse(s) {
	return Date.parse(s);
}
template.helper("date2parse", function(v) {
	return date2parse(v);
})
template.helper("string2cut", function(s) {
	return string2cut(s);
})
function string2cut(s){
	return s.substr(0,s.length-2);
}
template.helper("string2arr", function(s) {
	return JSON.parse(s);
})
template.helper("isNaN", function(s) {
	return isNaN(s);
})
function sex2text(type) {
	if(type == 1) {
		return "男";
	} else if(type == 0) {
		return "未知";
	} else {
		return "女";
	}
};
function telShow(tel){
	var str = tel.substr(0,3) + "****" + tel.substr(7);
	return str;
}
template.helper("telShow",function(t){
	return telShow(t);
})
template.helper('filterTime', function(day) {
	var nowTime = new Date().getTime();
	var setTime = new Date(day);
	if(setTime > nowTime) {
		return true;
	} else {
		return false;
	}
});

//图片放大
function imgMagnify(obj) {
	if(!obj) {
		obj = $("js-img-view") || $("img");
	}
	obj.find("img").unbind("click").bind("click", function() {
		$("body").append('<div class="viewImg"><img style="max-width=100%;" src="' + $(this).attr("src") + '"><span class="closed">x</span></div>');
		$(".closed").unbind("click").bind("click", function() {
			$(this).parents(".viewImg").remove();
		});
	});
}

var t_paramsArr = window.location.href.split("?");
var g_paramsStr = t_paramsArr.length > 1 ? t_paramsArr[1] : "";
var g_params = helper.str2Args(g_paramsStr, "&");
var g_platform = ""; //默认浏览器
var g_userId = "";
var protocol = helper.getProtocol();
var host = window.location.host;
window.g_Domain = protocol + '://' + host + '/';
window.IMGCACHE_DOMAIN = protocol + '://' + host;

template.helper("g_params",function(){
	return helper.str2Args(g_paramsStr, "&");
});
var deviceType  = "PC",//默认为PC
	token = "";
if(helper.getCookie("TOKEN")&&!helper.isEmpty(helper.getCookie("TOKEN"))){
	token = helper.getCookie("TOKEN");
	if(token.indexOf("%3D")>-1){
		token = token.replace("%3D","=");
	}
}
var doRequestwithnoheader = function(req, _type,  _url, handler, errorHandler, requestType) {
	var defer = $.Deferred(),
		$contentType = "application/x-www-form-urlencoded";
	//layer.msg("正在加载数据");
	//	$.QianLoad.PageLoading({
	//  	sleep: 50
	//	});
	if(requestType&&!helper.isEmpty(requestType)){
		$contentType = requestType;
	}
	
	$.ajax({
	 	headers: {
	        "device": deviceType,
	        "Authorization": token,
	        'Content-Type': $contentType
	   	},
		type: _type,
		url: basePath + _url,
		data: req,
		dataType: 'json',
		
		
        
        
		contentType: $contentType,
		success: function(res) {
//			console.log("ajax-res:", res);
			if(res.statusCode == '00') {
				handler(res); 
			} else if(res.statusCode == "20") {
				errorHandler(res); 
			} else if(res.statusCode == "02"||res.statusCode == "60") {
				// alert("系统检测到您需要登陆,即将跳转");
				helper.gotoLogin();
			} else {
				errorHandler(res);
				layer.msg(res.statusMsg,{offset:["70%","40%"],"background":"#666666"});
			}
		},
		error: function(xhr, type) {
			if(errorHandler) {
				errorHandler(xhr, type);
				return;
			}
			alert('网络请求失败，请稍后再试!');
		}
	});
	return defer.promise();
}

function toFixed(date) {
	return date.toFixed(2);
}

var $rows = 5;
/**
 * {pno}当前页
 * {page}总页码
 * {totalpage}总条数
 * **/
//分页
var kkpage = function(pno, page, totalpage, handler,flag,DivID) {
	if(page>1){
		
		$("#kkpager").show().empty();
		kkpager.total = page;
		kkpager.totalpage = kkpager;
		//生成分页控件  
		kkpager.generPageHtml({
			pagerid:DivID,
			pno: pno,
			mode: 'click', //设置为click模式
			//总页码  
			total: page,
			//总数据条数  
			totalRecords: totalpage,
			//点击页码、页码输入框跳转、以及首页、下一页等按钮都会调用click
			//适用于不刷新页面，比如ajax
			click: function(n) {
				//这里可以做自已的处理
				//...
				handler(n);
				//处理完后可以手动条用selectPage进行页码选中切换
				this.selectPage(n);
			},
			//getHref是在click模式下链接算法，一般不需要配置，默认代码如下
			getHref: function(n) {
				return '#';
			}
		},flag);
	}else{
		$("#kkpager").hide();
	}
}

//回复时间
function date2time(v){
	var secondTimestamp = 1000,
		minuteTimestamp = 60000,
		hourTimestamp = 3600000,
		dayTimestamp = 86400000,
		dubbleDayTimestamp = 172800000,
		newDate = date2parse(new Date()),
		time = date2parse(v),
		differenceTime = newDate-time,
		$timeName = "";
	if(differenceTime>secondTimestamp&&differenceTime<minuteTimestamp){
		return $timeName = "刚刚";
	}else if(differenceTime>minuteTimestamp&&differenceTime<hourTimestamp){
		return $timeName = Math.ceil(differenceTime/minuteTimestamp) + "分钟前";
	}else if(differenceTime>hourTimestamp&&differenceTime<dayTimestamp){
		return $timeName = Math.floor(differenceTime/hourTimestamp) + "小时前";
	}else if(differenceTime>dayTimestamp&&differenceTime<dubbleDayTimestamp){
		return $timeName = "昨天";
	}else if(differenceTime>dayTimestamp*2&&differenceTime<dubbleDayTimestamp*2){
		return $timeName = Math.floor(differenceTime/dayTimestamp) + "天前";
	}else if(differenceTime>dubbleDayTimestamp*2){
		return $timeName = timeStampToDate(time, "MM-dd hh:mm");
	}
}
template.helper("date2time", function(v) {
	return date2time(v);
})
function initUploader(obj,_flag) {
	var flag = true,
	_obj = obj ? obj:$('#file');
	
	_obj.on("change", function() {
		var _that = $(this);
			formData = new FormData(_that.parents(".upfORM")[0]);
//		if($(this)[0].files) {
//			//读取图片数据  
//			var f = $(this)[0].files[0];
//			var reader = new FileReader();
//			reader.onload = function(e) {
//				var data = e.target.result;
//				//加载图片获取图片真实宽度和高度  
//				var $image = new Image();
//				$image.onload = function() {
//					var width = $image.width;
//					var height = $image.height;
//					if(width>500||height>500){
//						layer.msg("上传图片过大，请重新上传！")
//						flag = false;
//						
//					}else{
//						flag = true;
//					}
//				};
//				console.log(data)
//				$image.src = data;
//				return
//			};
//			reader.readAsDataURL(f);
//		} else {
////			var $image = new Image();
////			$image.onload = function() {
////				var width = $image.width;
////				var height = $image.height;
////				var fileSize = $image.fileSize;
////				alert(width + '======' + height + "=====" + fileSize);
////			}
////			$image.src = $(this).value;
////			return;
//		}
//		if(flag){
//			return false;
//		}
		$.ajax({
			/*headers: {
		        "device": deviceType,
		        "Authorization": token
		   	},*/
			url: basePath + '/cms/common/upload',//上传文件
			type: 'POST',
			data: formData,
			async: false,
			cache: false,
			contentType: false,
			processData: false,
//	        xhrFields:{withCredentials:true},
//	        crossDomain: true,
			success: function(data) {
				console.log(data.data);
				if(!_flag){
					_that.prev().html("<input type='hidden' value='"+data.data+"'/>");
					if(_that.data("type")){
						_that.prev().prev().val(data.data);
						_that.parents(".upfORM").find(".hiddenInput").remove();
						_that.parents(".upfORM").append("<input class='hiddenInput' type='hidden' value='"+data.data+"' />");
					}
				}else{
					$("#fileUrl").attr("value",data.data);
				}
			}
		});
	});
}