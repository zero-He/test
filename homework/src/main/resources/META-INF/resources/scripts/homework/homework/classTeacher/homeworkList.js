define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	var Utils = require("utils");
	var WdatePicker = require('date');
	var LekeDate = require("common/base/date");
	var HomeworkType = require('homework/common/homeworkType');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');

	var HomeworkList = {
		page : null,
		init : function() {
			var clazz = $('#jULid li');
			if(clazz.length > 0){
				$('#jClassId').val(clazz.first().data('id'));
				$('#jClassName').val(clazz.first().data('cname'));
			}else{
				$('#jULid').html('<span class="s-c-orange">没有班级，请及时设置</span>');
				return;
			}
			this.initLoadPage();
			this.fBindEvent();
			this.fReloadClass();
			HomeworkType.render($('#jHomeworkType'));
		},
		initLoadPage : function() {
			this.page = Page.create({
				id : 'divPage',
				url : ctx + '/auth/classTeacher/homework/loadHomeworkList.htm',
				curPage : 1,
				pageSize : 10,
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
			var _this = this;

			$('#jSubject').stgGrdSbjSelect({
				type : 'sbj',
				caption : '全部',
				onChange : function(selection) {
					$('#jSubjectId').val(selection.subjectId);
					
				}
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
			$('#jHomeworkType').change(function() {
				_this.fReloadPage();
			});
			$('#jResType').change(function() {
				_this.fReloadPage();
			});
		},
		fReloadPage : function() {
			this.page.options.curPage = 1;
			this.page.loadData();
		},
		fReloadClass : function() {
			var classType = $('#jClassType').val();
			var data = {
				type : classType
			};
			$('#jClazz').empty();
			$.post(ctx + '/auth/classTeacher/homework/getClasses.htm', data, function(data) {
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
					$('#jClassId').val($this.data('id'));
					$('#jClassName').val($this.data('cname'));
					_this.fReloadClass();
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
			$('#jExportData').click(function(){
				var form = document.forms[0];
				var params= $(form).serialize();
				var url = Leke.domain.homeworkServerName + '/auth/classTeacher/homework/exportClassHomeWork.htm?'+ params;
				$(this).attr('href', url);
			});
			$("#jExportStatusData").click(function(){
				var form = document.forms[0];
				var params= $(form).serialize();
				var url = Leke.domain.homeworkServerName + '/auth/classTeacher/homework/export.htm?'+ params;
				$(this).attr('href', url);
			});
		}
	};

	HomeworkList.init();

});