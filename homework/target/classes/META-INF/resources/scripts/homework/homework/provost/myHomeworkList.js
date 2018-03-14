define(function(require, exports, module) {
	var $ = require('jquery');
	var WdatePicker = require('date');
	
	var Page = require('page');
	var Utils = require('utils');
	var HomeworkType = require("homework/common/homeworkType");
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var Handlebars = require("common/handlebars");
	
	var MyHomeworkList = {
		page : null,
		init : function() {
			this.loadMyHomeworkList();
			this.binEvent();
		},
		loadMyHomeworkList : function() {
			this.page = Page.create({
				id : 'page',
				url : ctx + '/auth/student/homeworkDtl/getMyHomeworkList.htm',
				pageSize : 20,
				buttonId : 'ButMyHomework',
				formId : 'myHomeworkForm',
				tipsId:'f_emptyDataContainer',
				callback : function(datas) {
					Handlebars.render("#myHomeworkContext_tpl", datas.page,"#myHomeworkContext");
				}
			});
		},
		binEvent : function() {
			var _this = this;
			$('#jSubject').stgGrdSbjSelect({
				type : 'sbj',
				onChange : function(selection) {
					$('#jSubjectId').val(selection.subjectId);
				}
			});
			$('#closeTime').click(function() {
				WdatePicker({
					maxDate : $('#closeEndTime').val(),
					isShowClear : true,
					readOnly : true
				});
			});
			$('#closeEndTime').click(function() {
				WdatePicker({
					minDate : $('#closeTime').val(),
					isShowClear : true,
					readOnly : true
				});
			});
			$('#submitTime').click(function() {
				WdatePicker({
					maxDate : $('#submitEndTime').val(),
					isShowClear : true,
					readOnly : true
				});
			});
			$('#submitEndTime').click(function() {
				WdatePicker({
					minDate : $('#submitTime').val(),
					isShowClear : true,
					readOnly : true
				});
			});
			$('#jCorrectFlag li').click(function() {
				var status = $(this).data('status');
				_this.changeStatusActive(status);
				if (status == '1') {
					//待完成
					$('#isSubmit').val('false');
					$('#isCorrect').val('false');
					$('#bugFixStage').val('');
				} else if (status == '2') { 
					//待订正
					$('#bugFixStage').val('1');
				}else {
					$('#isSubmit,#isCorrect,#bugFixStage').val('');
				}
				$('#correctFlag').val(status);
				_this.reloadPage();
			});

			HomeworkType.render($('#jHomeworkType'));
			
			//分析click事件
			$(document).on('click','.J_analysis',function(){
				var homeworkId = $(this).data('homeworkid');
				var isAllow=true;
				$.ajax({
					url : $('#J_homeworkServerName').val()+'/auth/teacher/homework/validateAnalysis.htm?homeworkId=' + homeworkId,
					async : false,
					success:function(data){
						if(!data.success){
							Utils.Notice.alert(data.message);
							isAllow=false;
						}
					}
				});
				if(!isAllow){
					return false;
				}

			});
			$(document).on('click','.jHomeworkTitle',function(){
				var id = $(this).data('id');
				var isdo = $(this).data('status') != 2 
					&& $(this).data('submittime') =='' 
					&& $(this).data('homeworktype') != 2;
				var href = Leke.domain.homeworkServerName + '/auth/student/homework/viewWork.htm?homeworkDtlId='+id;
				if(isdo){
					href = Leke.domain.homeworkServerName + '/auth/student/homework/doWork.htm?homeworkDtlId='+id;
				}
				window.open(href);
			});
		},
		reloadPage : function() {
			this.page.options.curPage = 1;
			this.page.loadData();
		},
		changeStatusActive : function(status) {
			$('#jCorrectFlag li').removeClass('active');
			$('#jCorrectFlag li[data-status=' + status + ']').addClass('active');
		}
	};

	MyHomeworkList.init();

});
