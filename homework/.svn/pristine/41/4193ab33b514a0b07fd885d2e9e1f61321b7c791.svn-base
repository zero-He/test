define(function(require, exports, module) {
	var $ = require('jquery');
	var WdatePicker = require('date');
	var Pages = require('common/base/pages');
	var Mustache = require('mustache');
	var Page = require('page');
	var deleteAndMark = require("homework/doubt/deleteAndMark");
	
	
	var LoadDoubtList = {
		page : null,
		finit : function() {
			this.page = Page.create({
				id : 'page',
				url : window.ctx + '/auth/teacher/doubt/teacherDoubtList_tpl.htm',
				pageSize : 20,
				buttonId : 'bSeachDoubt',
				formId : 'doubtListForm',
				callback : function(datas) {
					var page = datas.page;
					var output = Mustache.render($('#jDoubtListTpl').html(), page);
					$('#jDoubtBody').html(output);
				}
			});
			deleteAndMark.init(this.page);
			this.timeClick();
			this.bindGetDetail();
			this.bindSolve();
		},
		bindSolve : function() {
			var _this = this;
			$(".aIsSolve").on('click', function() {
				$(this).addClass('active').siblings().removeClass('active');
				$("#hIsSolve").val($(this).data('value'));
				_this.page.loadData();
			});
		},
		bindGetDetail : function() {
			$(".doubtDetail").each(function() {
				$(this).on("click", function() {
					window.location.href = ctx + "/auth/teacher/myDoubt/getDoubtDetail.htm?doubtId="
							+ $(this).data("id");
				});
			});
		},
		timeClick : function() {
			$('#tStartTime').click(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					maxDate : $('#tEndtTime').val(),
					readOnly : true
				});
			});
			$('#tEndTime').click(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					minDate : $('#tStartTime').val(),
					readOnly : true
				});
			});
		}
	}

	LoadDoubtList.finit();

})