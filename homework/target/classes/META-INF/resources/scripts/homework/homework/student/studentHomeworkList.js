Handlebars.registerHelper({
	cif: function (expression, options) {
		if (eval(expression)) {
			return options.fn(this);
		} else {
			return options.inverse(this);
		}
	},
	expression: function (expression, options) {
		return eval(expression);
	}
});

function createPage() {
	$("#jScrollContEle").page({
		url: '/auth/hd/queryStuHomeworkListData.htm',
		form: $("#jForm")[0],
		scrollCont: $("#jScrollArea")[0],
		callbackPullDown: function (page) {
			$.each(page.dataList, function () {
				var self = this;
				if (self.startTime == null || self.startTime == '') {
					self.startTime = "--";
				} else {
					self.startTime = new Date(self.startTime).Format("yyyy-MM-dd hh:mm");
				}
				if (self.submitTime == null || self.submitTime == '') {
					self.submitTime = "--";
				} else {
					self.submitTime = new Date(self.submitTime).Format("yyyy-MM-dd hh:mm");
				}
				if (self.closeTime > (new Date()).getTime()) {
					this.closeTimeStr = calcDayHourMin(self.closeTime);
					this.subTag = 0;  //表示按时提交
				} else {
					this.closeTimeStr = "--";
					this.subTag = 1;  //表示迟交
					//超时未提交
				}
				if (self.paperType == 2) {
					this.paperTypeStr = "答题卡";
				} else {
					this.paperTypeStr = "普通试卷";
				}

				if (self.score != null && score != undefined && score != '') {
					var score = self.score.toString();
					for (var i = 0; i < score.length; i++) {
						var splitVal = score.charAt(i);
						var claName = scoreSwitch(splitVal);
						if (i == 0) {
							this.cn0 = claName;
						} else if (i == 1) {
							this.cn1 = claName;
						} else if (i == 2) {
							this.cn2 = claName;
						} else if (i == 3) {
							this.cn3 = claName;
						} else if (i == 4) {
							this.cn4 = claName;
						} else {
							this.cn5 = claName;
						}
					}
				} else {
					this.cn0 = "holiday-work-grade-num grade-num-null";
					this.cn1 = "holiday-work-grade-num grade-num-null";
				}


			});
			page.isInvalid = function () {
				return this.status == 2 ? true : [];
			};
			page.isInclass = function () {
				return this.homeworkType == 2;
			};
			var source = $("#jTpl").html();
			var html = (Handlebars.compile(source))(page);
			$("#jScrollArea").append(html);

		}
	});
}


createPage();

/**
 * tab页切换
 */
$(".c-tab-nav").on("click", ".item", function () {
	$(this).addClass('item-on').siblings().removeClass('item-on');
	$("#jHomeFinishFlag").val($(this).data("i"));
	createPage();
});

function initModal() {
	var btns = $('.get-more'),
		$cover = $('.cover'),
		$modalbox = $('.modal-box'),
		$item = $('.btns-list .item');
	$index = 0;

	/**
	 * 点击更多
	 */
	$("#jScrollArea").on("click", ".get-more", function () {
		var type = $(this).data("t");
		var id = $(this).data("i");
		var dtlid = $(this).data("dtlid");
		var openAnswer = $(this).data("o");
		var s = $(this).data("s");
		var c = $(this).data("c") || false;
		modalShow(id, dtlid, type, openAnswer, s && !c);
	});

	/**
	 * 弹出更多种的选项
	 */
	function modalShow(id, dtlid, type, open, proofread) {
		$cover.show();
		$modalbox.show();
		$item.data("dtlid", dtlid);
	}

	//如果需要去除注释可以监听点击第几个item
	$item.click(function () {
		var i = $(this).data("dtlid");
		var m = $(this).data("m");
		$index = $(this).index();
		modalhide();
	});

	/**
	 * 隐藏更多选项
	 */
	function modalhide() {
		$cover.hide();
		$modalbox.hide();
	}

	$modalbox.click(function (ev) {
		modalhide();
	});
}
initModal();


/**
 * 根据传入的数字返回对应的类名
 * @Author LIU.SHITING
 * @Version 1.0.0
 * @Date 2017/4/12 14:39
 */
function scoreSwitch(parameter) {
	var clName = "holiday-work-grade-num grade-num-null";
	switch (parameter) {
		case '0':
			clName = "holiday-work-grade-num grade-num0";
			break;
		case '1':
			clName = "holiday-work-grade-num grade-num1";
			break;
		case '2':
			clName = "holiday-work-grade-num grade-num2";
			break;
		case '3':
			clName = "holiday-work-grade-num grade-num3";
			break;
		case '4':
			clName = "holiday-work-grade-num grade-num4";
			break;
		case '5':
			clName = "holiday-work-grade-num grade-num5";
			break;
		case '6':
			clName = "holiday-work-grade-num grade-num6";
			break;
		case '7':
			clName = "holiday-work-grade-num grade-num7";
			break;
		case '8':
			clName = "holiday-work-grade-num grade-num8";
			break;
		case '9':
			clName = "holiday-work-grade-num grade-num9";
			break;
		default:
			clName = "holiday-work-grade-num grade-num-node";
	}
	return clName;
}

/**
 * 格式化时间
 * @Author LIU.SHITING
 * @Version 1.0.0
 * @Date 2017/4/12 14:40
 */
Date.prototype.Format = function (fmt) {
	var o = {
		"M+": this.getMonth() + 1, //月份
		"d+": this.getDate(), //日
		"h+": this.getHours(), //小时
		"m+": this.getMinutes(), //分
		"s+": this.getSeconds(), //秒
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度
		"S": this.getMilliseconds() //毫秒
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

/**
 * 计算两个时间相差多少天多少小时多少分钟多少秒
 * @Author LIU.SHITING
 * @Version 1.0.0
 * @Date 2017/4/11 16:21
 */
function calcDayHourMin(date1) {
	var date3 = new Date(date1).getTime() - (new Date()).getTime();   //时间差的毫秒数

	//计算出相差天数
	var days = Math.floor(date3 / (24 * 3600 * 1000));

	//计算出小时数
	var leave1 = date3 % (24 * 3600 * 1000);    //计算天数后剩余的毫秒数
	var hours = Math.floor(leave1 / (3600 * 1000));

	//计算相差分钟数
	var leave2 = leave1 % (3600 * 1000);       //计算小时数后剩余的毫秒数
	var minutes = Math.floor(leave2 / (60 * 1000));

	//计算相差秒数
	var leave3 = leave2 % (60 * 1000);     //计算分钟数后剩余的毫秒数
	var seconds = Math.round(leave3 / 1000);
	return days + "天" + hours + "小时" + minutes + "分";
}




