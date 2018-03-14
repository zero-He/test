define(function(require, exports, module) {
	var $ = require('jquery');
	var WdatePicker = require('date');
	var Mustache = require('mustache');
	var Page = require('page');
	var HomeworkType = require("homework/common/homeworkType");
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');

	var MyHomeworkList = {
		page : null,
		init : function() {
			this.binEvent();
			this.loadMyHomeworkList();
		},
		loadMyHomeworkList : function() {
			this.page = Page.create({
				id : 'page',
				url : ctx + '/auth/parent/homework/loadStuHomeworkList.htm',
				pageSize : 10,
				buttonId : 'ButMyHomework',
				formId : 'myHomeworkForm',
				callback : function(datas) {
					var page = datas.page;
					var linkTpl = $("#myHomeworkLink_tpl").html()
					$.each(datas.page.dataList, function(index, homework) {
						var linkHref = Leke.domain.homeworkServerName+'/auth/parent/homework/viewWork.htm?homeworkDtlId='+homework.homeworkDtlId;
						var  linkName = '查看作业';
						if(homework.resType==1){
							linkHref = Leke.domain.beikeServerName+'/auth/common/courseware/preview.htm?coursewareId='+homework.paperId;
							linkName = '查看课件';
						}else if(homework.resType==2){
							linkHref = Leke.domain.beikeServerName+'/auth/common/microcourse/preview.htm?microcourseId='+homework.paperId;
							linkName = '查看微课';
						}
						var linkHtml = Mustache.render(linkTpl, {
							linkName : linkName,
							linkHref : linkHref
						});
						homework.linkHtml = linkHtml;
					});
					page.isInvalid = function() {
						return this.status == 2 ? true : false;
					}
					var output = Mustache.render($("#myHomeworkContext_tpl").html(), page);
					$('#myHomeworkContext').html(output);
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
				_this.changeStatusActive('#jCorrectFlag', status);
				$('#correctFlag').val(status);
				if(status == 1){
					$('#jisSubmit').val('0');
					$('#j_bugFixStage').val('');
				}else if(status == 2){
					$('#jisSubmit').val('');
					$('#j_bugFixStage').val(1);
				}else{
					$('#jisSubmit').val('');
					$('#j_bugFixStage').val('');
				}
				_this.reloadPage();
			});
			HomeworkType.render($('#jHomeworkType'));
			$('#jHomeworkType').change(function() {
				_this.reloadPage();
			});
			
			$('#jResType').change(function() {
				_this.fReloadPage();
			});
			
			$('#jStuswitch li').click(function() {
				var status = $(this).data('status');
				_this.changeStatusActive('#jStuswitch', status);
				$('#jSchoolId').val($(this).data('schoolid'));
				$('#jStudentId').val($(this).data('studentid'));
				_this.reloadPage();
			});
		},
		reloadPage : function() {
			this.page.options.curPage = 1;
			this.page.loadData();
		},
		changeStatusActive : function($id, status) {
			$($id + ' li').removeClass('active');
			$($id + ' li[data-status=' + status + ']').addClass('active');
		}
	};

	MyHomeworkList.init();

});
