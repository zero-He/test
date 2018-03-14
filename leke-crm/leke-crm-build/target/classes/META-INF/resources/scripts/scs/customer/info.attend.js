define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var LekeDate = require('common/base/date');
	var WdatePicker = require('date');
	var Handlebars = require("common/handlebars");
	var html = require('./info.attend.html');

	var Attend = {
		element : null,
		init : function(container) {
			container.children().hide();
			if (this.element != null) {
				this.element.show();
			} else {
				this.element = $(html).appendTo(container);
				this.bindEvent();
			}
		},
		bindEvent : function() {
			$('#jAttendTeacherId').val(Leke.Page.lekeId);
			var date = LekeDate.of(Leke.Clock.date());
			var end = date.format('yyyy-MM-dd');
			var start = date.add(-1, 'M').format('yyyy-MM-dd');
			$('#jAttendStartTime').val(start).focus(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					maxDate : '#F{$dp.$D(\'jAttendEndTime\',{d:-1});}',
					isShowClear : false,
					readOnly : true
				});
			});
			$('#jAttendEndTime').val(end).focus(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					minDate : '#F{$dp.$D(\'jAttendStartTime\',{d:1});}',
					isShowClear : false,
					readOnly : true
				});
			});
			Page.create({
				id : 'jAttendPageFoot',
				url : Leke.ctx + '/auth/scs/attendance/teacher.htm',
				pageSize : 20,
				buttonId : 'jAttendPageSearch',
				formId : 'jAttendPageForm',
				callback : function(datas) {
					Handlebars.render('#jAttendPageTpl', datas.page, '#jAttendPageBody');
				}
			});
		}
	};

	module.exports = Attend;
});
