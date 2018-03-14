/**
 * Created by Administrator on 2016/12/12 0012.
 */
/**
 * 催交寒暑假作业
 */
window.callVacationHomework = function () {
	var subjectId = $('#jXKbody').find('i').attr('data-id');
	var classId = $('#jBJbody').find('i').attr('data-id');
	var yearid = $('#jHSJbody').find('i').attr('data-yearid');
	var holiday = $('#jHSJbody').find('i').attr('data-holiday');
	var data = {
		classId: classId,
		subjectId: subjectId,
		year: yearid,
		holiday: holiday
	}

	Utils.Notice.confirm("确定要催交作业吗？", function (sure) {
		if (sure) {
			$.post("/auth/common/holiday/callVacationHomework.htm", data, function (datas) {
				if (datas.success) {
					Utils.Notice.alert('作业催交成功！');
				} else {
					Utils.Notice.alert('作业催交失败，请重新催交！');
				}
			});
		}
	});

}

var tchWork = function () {

	//初始化函数
	var _tch = this;
	var dropBind = null;
	this.init = function () {


		$('body').click(function (ev) {
			var targetClass = $(ev.target).attr('class');
			if (typeof(targetClass) == 'undefined')return;
			if (targetClass.indexOf('assign-bd-nav') == -1) {
				if (targetClass.indexOf('assign-testpaper-bank-li') > -1) {
					$('.assign-testpaper-bank-li').removeClass('assign-testpaper-bank-li-active');
					$(ev.target).addClass('assign-testpaper-bank-li-active');
					$(ev.target).parents('.assign-bd-nav').find('.assign-bd-nav-text').html($(ev.target).html());
					$(ev.target).parents('.assign-bd-nav').find('.assign-bd-nav-text').data("id", $(ev.target).data("id"));
					$(ev.target).parents('.assign-bd-nav').find('.assign-bd-nav-text').data("yearid", $(ev.target).data("yearid"));
					$(ev.target).parents('.assign-bd-nav').find('.assign-bd-nav-text').data("holiday", $(ev.target).data("holiday"));
				}
				$('.assign-bd-nav').removeClass('assign-bd-nav-active');

				if (targetClass.indexOf('assign-testpaper-bank-li') > -1) {
					_tch.loadBJ($(ev.target), true);
				}

			}
		});

		_tch.loadXK();
		_tch.loadHSJ();
		_tch.bind();
		//tab切换
		$('.homework-hd-work-item').on('click', function () {
			var $index = $(this).index();
			$(this).addClass('homework-hd-work-item-on').siblings().removeClass('homework-hd-work-item-on');
			$('.homework-bd-work-item').eq($index).show().siblings().hide();
		});


		//下拉
		$('.assign-bd-nav').on('click', function () {
			var _this = this;
			if ($(_this).hasClass('assign-bd-nav-active')) {
				$('.assign-bd-nav').removeClass('assign-bd-nav-active');
				return;
			} else {
				$('.assign-bd-nav').removeClass('assign-bd-nav-active');
				$(_this).addClass('assign-bd-nav-active');
			}

		});

		var $devicePixelRatio = window.devicePixelRatio;
		if ($devicePixelRatio == 1.5) {
			$('#homework-meta').attr('content', 'width=device-width,initial-scale=0.65, maximum-scale=0.65, minimum-scale=0.65')
		} else {
			$('#homework-meta').attr('content', 'width=1920px,initial-scale=0.5, maximum-scale=0.5, minimum-scale=0.5')
		}

	};
	this.bind = function () {
		//查看详情
		$(".review").live("click", function () {

			var url = $(this).data("url");
			var title = $(this).parent().find("div.title h3").text();
			if (!!title && title.length > 0)
				url += "&title=" + title
			window.location.href = url+'&_newpage=1';
		});

	};

	/**
	 * 学科
	 */
	this.loadXK = function () {
		var url = "/auth/hd/teacher/vacationWork/tchSubjList.htm"
		$.post(url, "", function (datas) {
			if (datas.success == true) {
				var page = datas.datas
				if (page == null || page.dataList == null || page.dataList.length == 0) {
					return;
				}

				for (var index = 0; index < page.dataList.length; index++) {
					data = page.dataList[index];
					if (index == 0) {
						page.select_id = data.subjectId;
						page.select_name = data.subjectName;
						data.active = "assign-testpaper-bank-li-active";
					}

					data.Clazz = JSON.stringify(data)
				}
				;
			}
			var sOutput = Mustache.render($('#jXKtpl').val(), page);
			$body = $('#jXKbody');
			$body.html(sOutput);

			var url2 = "/auth/hd/common/holiday/ajax/loadListByClass.htm"
			dropBind = new DropBind({
				obj: $('#jbody'),
				dataContentObj: $('#jLstbody'),
				url: url2, autoLoad: false,
				getPostData: _tch.getRequestData,
				GetList: _tch.GetList,
				callBack: _tch.BindData
			});
			_tch.loadBJ($body.find("ul li").eq(0), false);
		});
	};

	/**
	 * 班级
	 * @param obj
	 * @param isclick
	 */
	this.loadBJ = function (obj, isclick) {
		var clazz = $(obj).data("clazz");
		if (typeof(clazz) != 'undefined') {
			var page = eval(clazz);
			if (page != null && page.clazzList != null && page.clazzList.length > 0) {

				for (var index = 0; index < page.clazzList.length; index++) {
					data = page.clazzList[index];
					if (index == 0) {
						page.select_id = data.classId;
						page.select_name = data.className;
						data.active = "assign-testpaper-bank-li-active";
					}
				}
				var sOutput = Mustache.render($('#jBJtpl').val(), page);
				$body = $('#jBJbody');
				$body.html(sOutput);
			}
		}
		if (dropBind != null) {
			dropBind.changeDataSource();
		}

	};

	/**
	 * 寒暑假
	 */
	this.loadHSJ = function () {
		var url = "/auth/hd/teacher/vacationWork/queryVacationList.htm"
		$.post(url, "", function (datas) {
			if (datas.success == true) {
				var page = datas.datas;
				if (page == null || page.yearList == null || page.yearList.length == 0) {
					return;
				}

				for (var index = 0; index < page.yearList.length; index++) {
					data = page.yearList[index];
					if (index == 0) {
						page.year_id = data.yearId;
						page.holiday_id = data.holiday;
						page.year_name = data.yearName;
						data.active = "assign-testpaper-bank-li-active";
					}

				}
				;

			}
			var sOutput = Mustache.render($('#jHSJtpl').val(), page);
			$body = $('#jHSJbody');
			$body.html(sOutput);

			var url2 = "/auth/hd/common/holiday/ajax/loadListByClass.htm"
			dropBind = new DropBind({
				obj: $('#jbody'),
				dataContentObj: $('#jLstbody'),
				url: url2, autoLoad: false,
				getPostData: _tch.getRequestData,
				GetList: _tch.GetList,
				callBack: _tch.BindData
			});
		});

	};

	this.getRequestData = function () {
		return {
			subjectId: $("#XK").data("id"),
			classId: $("#BJ").data("id"),
			year: $("#HSJ").data("yearid"),
			holiday: $("#HSJ").data("holiday")
		};
	}

	this.BindData = function (datas, requestPage) {
		var rsJson = eval({'1': '寒假', '2': '暑假'});
		var leType = eval({'1': '作业', '2': '微课'});
		var page = datas.datas.page;
		$body = $('#jLstbody');
		var photoSrc = datas.datas.userPhoto;
		if (page == null || page.dataList == null || page.dataList.length == 0) {

			return;
		}
		for (var index = 0; index < page.dataList.length; index++) {
			data = page.dataList[index];
			data.holiday = rsJson[data.holiday];
			data.finishRate = (Math.round(data.finish * 10000) / (data.total * 100)).toFixed(0);
			if (isNaN(data.finishRate)) {
				data.finishRate = '0';
			}

			data.firstTime = (data.firstTime == null) ? "--" : new Date(data.firstTime).Format('yyyy-MM-dd hh:mm:ss');
			data.lastTime = (data.lastTime == null) ? "--" : new Date(data.lastTime).Format('yyyy-MM-dd hh:mm:ss');
			var title = data.year + "年" + data.holiday + "作业-" + data.subjectName
			if (data.type == 1) {
				data.url = "/auth/hd/teacher/vacationWork/TchDecList.htm?workId=" + data._id + "&title=" + title;
			} else {
				data.url = "/auth/hd/teacher/vacationWork/TchWkList.htm?workId=" + data._id + "&title=" + title;
			}
			data.type = leType[data.type]
			data.src = photoSrc[data.userId];
		}

		var sOutput = Mustache.render($('#jLsttpl').val(), page);
		$body = $('#jLstbody');
		if (requestPage == 0)
			$body.html(sOutput);
		else
			$body.append(sOutput);

	};
	this.GetList = function (datas) {
		return datas.datas.page.dataList;
	}


};
var tch = new tchWork();
tch.init();
