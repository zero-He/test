define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	var Utils = require('utils');
	var JSON = require("json");
	var WdatePicker = require('date');
	var LekeDate = require("common/base/date");
	var HomeworkType = require('homework/common/homeworkType');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');

	var HomeworkList = {
		page : null,
		init : function() {
			this.initLoadPage();
			this.fBindEvent();
		},
		initLoadPage : function() {
			var _this = this;
			this.page = Page.create({
				id : 'divPage',
				url : ctx + '/auth/provost/homework/loadHomeworkList.htm',
				curPage : 1,
				pageSize : 20,
				pageCount : 10,
				buttonId : 'bHomeworkList',
				formId : 'formPage',
				callback : function(datas) {
					var page = datas.page;
					$.each(page.dataList, function() {
						var self = this;
						var start = new Date(self.startTime);
						var close = new Date(self.closeTime);
						self.avgScore = Utils.Number.toFixed(self.avgScore, 1);
						self.isInvalid = self.status == 2 ? true : false;
						self.isPushWork = self.homeworkType == 6;
						self.isShowAnaly = self.finishNum > 0 && self.homeworkType != 6;
						self.startCloseTime = LekeDate.format(start,'yyyy-MM-dd HH:mm') + LekeDate.format(close,'/yyyy-MM-dd HH:mm');
					});
					Handlebars.render("#homeworkContext_tpl", page,'#homeworkListContext');
				}
			});
			$('#jGrade').stgGrdSbjSelect({
				type : 'grd',
				caption : '全部',
				onChange : function(selection) {
					$('#jGradeId').val(selection.gradeId);
					_this.fReloadClass(selection.gradeId);
				}
			});
			$('#jSubject').stgGrdSbjSelect({
				type : 'sbj',
				caption : '全部',
				onChange : function(selection) {
					$('#jSubjectId').val(selection.subjectId);
					_this.fReloadPage();
				}
			});

			HomeworkType.render($('#jHomeworkType'));
			$('#jHomeworkType').change(function() {
				_this.fReloadPage();
			});
			$('#jResType').change(function() {
				_this.fReloadPage();
			});
			$('#homeworkListContext').on('click', '.jStats', function() {
				var stats = $(this).data('stats');
				if (stats != 2) {
					Utils.Notice.alert('分析数据还未生成，待布置作业老师生成后再查看。');
					return false;
				} else {
					return true;
				}
			});
		},
		fReloadPage : function() {
			this.page.options.curPage = 1;
			this.page.loadData();
		},
		fReloadClass : function(gradeId) {
			var classType = $('#jClassType').val();
			var data = {
				gradeId : gradeId,
				type : classType
			};
			$('#jClazz').empty();
			$.post(ctx + '/auth/provost/homework/getClasses.htm', data, function(data) {
				$('#jClazz').append('<option value="">全部</option>');
				$.each(data.datas.classes, function() {
					$('#jClazz').append($("<option>").val(this.classId).text(this.className));
				});
			});
		},
		fBindEvent : function() {
			var _this = this;
			$('#jULid li').click(function() {
				var $this = $(this);
				if (!$this.hasClass('active')) {
					$this.addClass("active").siblings().removeClass('active');
					$('#jClassType').val($this.data('id'));
					_this.fReloadClass($('#jGradeId').val());
					HomeworkList.fReloadPage();
				}
			});
			$('#jStartTime').click(function() {
				WdatePicker({
					maxDate : $('#jSubmitTime').val(),
					isShowClear : true,
					readOnly : true
				});
			});
			$('#jSubmitTime').click(function() {
				WdatePicker({
					minDate : $('#jStartTime').val(),
					isShowClear : true,
					readOnly : true
				});
			});
			$('#bExportData').click(function(){
				var form = document.forms[0];
				var params= $(form).serialize();
				var url=Leke.domain.homeworkServerName + '/auth/provost/homework/exportClassHomeWork.htm?'+ params;
				location.href= url;
			});
		}
	};

	HomeworkList.init();

});
