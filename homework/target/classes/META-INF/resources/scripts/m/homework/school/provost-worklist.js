Handlebars.registerHelper({
	cif : function(expression, options) {
		if (eval(expression)) {
			return options.fn(this);
		} else {
			return options.inverse(this);
		}
	},
	expression : function(expression, options) {
		return eval(expression);
	}
});


/**
 * 请求列表分页数据
 */
function createPage() {
	$("#jScrollContEle").page({
		url: 'worklist.htm',
		form: $("#jForm")[0],
		scrollCont: $("#jScrollArea")[0],
		callbackPullDown: function (page) {
			$.each(page.dataList, function () {
				/*var self = this;
				if (self.startTime == null || self.startTime == '') {
					self.startTime = "--";
				} else {
					self.startTime = new Date(self.startTime).Format("yyyy-MM-dd hh:mm");
				}*/
			});
			page.isInvalid = function () {
				return this.status == 2 ? true : [];
			};
			page.isInclass = function () {
				return this.homeworkType == 2;
			};
			var source = $("#jListTpl").html();
			var html = (Handlebars.compile(source))(page);
			$("#jScrollArea").append(html);

		},
		callbackPullUp:function (page) {
			$.each(page.dataList, function () {
			});
			page.isInvalid = function () {
				return this.status == 2 ? true : [];
			};
			page.isInclass = function () {
				return this.homeworkType == 2;
			};
			var source = $("#jListTpl").html();
			var html = (Handlebars.compile(source))(page);
			$("#jScrollArea").html(html);
		},
		loadTips: {
			loading: '加载中...',
			loaded: '没有更多了~~',
			noData: '<div class="c-scroll-box" style="overflow:inherit;"><div class="scroll-tips"><span class="letalk"></span><span>搜索结果为空</span></div></div>'
		}
	});
}
createPage();


$(function () {
	//初始化数据
	initData();
	//初始化呼出层中的筛选列表
	initGradeSubjectDataList();
	//清空文本框内容事件
	deleteClick();
});

/**
 * 页面初始化
 */
function initData() {
	/*全局定时隐藏*/
	var $time;
	/*点击漏斗显示*/
	$('.c-icon-sift').click(function () {
		showModal();
	})
	/*模态显示隐藏*/
	var showModal = function () {
		$('.modal-cover').show();
		$('.modal-body').show();
		$('.modal-box').css('right', 0);
		$('.bottom-btn').css('right',0);
	}

	/*点击遮挡隐藏*/
	$('.modal-cover').click(function () {
		hideModal();
	})
	var hideModal = function () {
		$('.modal-box').css('right', -285 + 'px');
		$('.bottom-btn').css('right',-285+'px');
		clearTimeout($time);
		$time = setTimeout(function () {
			$('.modal-body').hide();
			$('.modal-cover').hide();
		}, 500)
	}

	/*筛选项点击事件*/
	$('.main-list').on('click', '.item', function () {
		$(this).parents('.main-list').find('.item').removeClass('c-btn-green');
		$(this).addClass('c-btn-green');
		var gradeId = $('.main-list-grade').find('.c-btn-green').data('gradeid');
		var subjectId = $('.main-list-subject').find('.c-btn-green').data('subjectid');
		var jgradeId = $("#jGradeId").val();
		var jsubjectId = $("#jSubjectId").val();
		if (gradeId === undefined) {
			if (jgradeId != null && jgradeId != '') {
				gradeId = jgradeId;
			} else {
				gradeId = null;
			}
		}
		if (subjectId === undefined) {
			if (jsubjectId != null && jsubjectId != '') {
				subjectId = jsubjectId;
			} else {
				subjectId = null;
			}
		}
		$("#jGradeId").val(gradeId);
		$("#jSubjectId").val(subjectId);
		console.log(gradeId);
		console.log(subjectId);
	})

	/*重置按钮事件*/
	$('.reset-btn').click(function () {
		$('.main-list .item').removeClass('c-btn-green');
		$("#jGradeId").val('');
		$("#jSubjectId").val('');
	})

	/*下拉显示筛选项事件*/
	$('.tit .pull-icon').click(function () {
		$(this).toggleClass('pull-icon-up');
		$(this).parents('.main-list').toggleClass('main-list-auto');
	})

	/*确定点击事件*/
	$('.bottom-btn .sub-btn').click(function () {
		createPage();
		hideModal();
	});

}

/**
 * 初始化呼出层中的筛选列表
 */
function initGradeSubjectDataList() {
	$.ajax({
		"url": "filters.htm",
		"type": "POST",
		"dataType": "JSON",
		success: function (result) {
			var res = $.parseJSON(result);
			if (res.success) {
				//班级列表
				var gradeHtmlTmpl = $("#gradeContext_tpl").html();
				var gradeTemplate = Handlebars.compile(gradeHtmlTmpl);
				var gradeResultData = res.datas;
				var gradeContent = gradeTemplate(gradeResultData);
				$(".grade-context").html(gradeContent);
				//学科列表
				var subHtmlTmpl = $("#mySubjectContext_tpl").html();
				var subTemplate = Handlebars.compile(subHtmlTmpl);
				var subResultData = res.datas;
				var subContent = subTemplate(subResultData);
				$(".subject-context").html(subContent);
			}
		},
		error: function (xhr) {
			console.log(xhr);
		}
	});
}

/**
 * 回车提交
 * @param e
 */
function handleKeyUps(e){
	e = event || window.event;
	if (e.keyCode == 13) {
		var text = $("#text").val();
		$("#jKeyWords").val(text);
		createPage();
	}
}

/**
 * 清空文本框事件
 */
function deleteClick() {
	$(".delete").click(function () {
		console.log("delete");
		$("#text").val('');
		$("#text").focus();
	});
}
