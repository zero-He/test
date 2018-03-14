$(function () {
	//初始化数据
	initStuData();
});


/**
 * 初始化课件&微课数据
 */
function initStuData() {
	var homeworkDtlId = $("#homeworkDtlId").val();
	var httpUrl = $("#jOnlineServerName").val();
	//learnType:0.学习,1.复习
	var learnType = $("#learnType").val();
	var data = {homeworkDtlId: homeworkDtlId};
	$.ajax({
		url: "/auth/m/student/homework/learningDatas.htm",
		type: "POST",
		data: data,
		dataType: "JSON",
		success: function (result) {
			var res = $.parseJSON(result);
			if (res.success) {
				var fileData = res.datas.fileDesc;
				var type = fileData.type;
				var pagesArr = fileData.pages;
				//定位点:如果是音视频是时间(s),如果是ppt、doc等则是页码
				var position = res.datas.position;
				if (type === "doc" || type === "ppt" || type === "img") {
					var items = [];
					for(var i = 0; i < pagesArr.length; i++){
						items.push({
							src: httpUrl + pagesArr[i].paths[pagesArr[i].paths.length - 1],
							w: 400,
							h: 400
						});
					}
					imgPlugin(items);
				}

				if (type === "mp3") {
					var url = httpUrl + pagesArr[pagesArr.length - 1].paths[pagesArr[pagesArr.length - 1].paths.length - 1];
					musicPlugin(url);
				}

				if (type === "mp4") {
					var suffix = fileData.cwSuffix;
					var url = httpUrl + pagesArr[pagesArr.length - 1].paths[pagesArr[pagesArr.length - 1].paths.length - 1].replace(/http:/, 'https:');
					videoPlugin(url, suffix);
				}

				if (learnType == "0") {

					//resType:1.课件,2.微课
					if (res.datas.resType == 1) {
						var param = "";
						//学生查看课件两分钟后标记为已观看状态
						setTimeout(function () {
							if (type == "mp3" || type == "mp4") {
								var allTime = $(".time").html();
								var playTime = allTime.split("/")[0];
								var param = time_to_sec(playTime);
							} else {
								var allPage = $(".pswp__counter").html();
								var param = allPage.split("/")[0];
							}
							$.post("/auth/m/student/homework/dealwithStatus.htm", {
								homeworkDtlId: homeworkDtlId,
								type: type,
								param: param,
							}, function (data) {

							});
							window.onbeforeunload = null;
						}, 20000);
						window.onbeforeunload = function () {
							return "4444444444";
						}

					} else {
						//把当前系统时间转换为秒
						var myDate = new Date();
						var time = myDate.getHours() + ":" + myDate.getMinutes() + ":" + myDate.getSeconds();
						var initTime = htime_to_sec(time);

						window.onbeforeunload = function () {
							return "4444444444";
						}

						//如果是微课，要在用户关闭当前窗口的时候触发后台方法做相应的一些判断
						$(".pswp__button pswp__button--close").on('click', function () {
							var myDate = new Date();
							var time = myDate.getHours() + ":" + myDate.getMinutes() + ":" + myDate.getSeconds();
							var closeTime = htime_to_sec(time);
							//时间差，单位为秒
							//由后端方法判断时间差是否大于2分钟，如果大，则标记为学习完，否则false
							var timeDiff = closeTime - initTime;
							if (type == "mp3" || type == "mp4") {
								var allTime = $(".time").html();
								var playTime = allTime.split("/")[0];
								var param = time_to_sec(playTime);
								$.post("/auth/m/student/homework/dealwithStatus.htm", {
									homeworkDtlId: homeworkDtlId,
									type: type,
									param: param,
									timeDiff: timeDiff,
								}, function (data) {

								});
							} else {
								var allPage = $(".pswp__counter").html();
								var param = allPage.split("/")[0];
								$.post("/auth/m/student/homework/dealwithStatus.htm", {
									homeworkDtlId: homeworkDtlId,
									type: type,
									param: param,
									timeDiff: timeDiff,
								}, function (data) {

								});
								console.log(allPage);
							}

							window.onbeforeunload = null;

						});

					}
				}

			}
		},
		error: function (xhr) {
			console.log(xhr);
		}
	});

}


/**
 * img
 * @param items
 */
function imgPlugin(items) {
	new CWPreview({
		wrapEle: document.querySelector('.picviewer'),
		resourceType: 'img',
		url: items
	});
}

/**
 * 音频
 * @param url
 */
function musicPlugin(url) {
	new CWPreview({
		wrapEle: document.querySelector('.audio'),
		resourceType: 'audio',
		url: url
	});
}

/**
 * 视频
 * @param url
 * @param cwSuffix
 */
function videoPlugin(url, cwSuffix) {
	new CWPreview({
		wrapEle: document.querySelector('.video'),
		resourceType: 'video',
		url: url,
		type: cwSuffix
	});
}

/**
 * 分秒转换为秒
 * @param time
 * @returns {string}
 */
var time_to_sec = function (time) {
	var s = '';
	var min = time.split(':')[0];
	var sec = time.split(':')[1];
	s =  Number(min * 60) + Number(sec);
	return s;
};

/**
 * 时分秒转换为秒
 * @param time
 * @returns {string}
 */
var htime_to_sec = function (time) {
	var s = '';
	var hour = time.split(':')[0];
	var min = time.split(':')[1];
	var sec = time.split(':')[2];
	s = Number(hour*3600) + Number(min*60) + Number(sec);
	return s;
};