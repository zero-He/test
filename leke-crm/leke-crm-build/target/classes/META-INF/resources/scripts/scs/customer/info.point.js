define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var LekeDate = require('common/base/date');
	var WdatePicker = require('date');
	var Handlebars = require("common/handlebars");
	var html = require('./info.point.html');

	var Point = {
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
			// 时间初始化
			var date = LekeDate.of(Leke.Clock.date());
			var end = date.format('yyyy-MM-dd');
			var start = date.add(-1, 'M').format('yyyy-MM-dd');
			$('#jPointStartTime').val(start).focus(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					maxDate : '#F{$dp.$D(\'jPointEndTime\',{d:-1});}',
					isShowClear : false,
					readOnly : true
				});
			});
			$('#jPointEndTime').val(end).focus(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					minDate : '#F{$dp.$D(\'jPointStartTime\',{d:1});}',
					isShowClear : false,
					readOnly : true
				});
			});
			// 点账户信息
			var url = Leke.domain.payServerName + '/auth/common/account/pointinfo.htm';
			var params = 'jsoncallback=?&ownerId=' + Leke.Page.lekeId + '&ownerType=' + Leke.Page.customerType;
			$.getJSON(url, params, function(json) {
				if (json.success) {
					var book = json.datas.pointBook;
					$('#jPointUnable').text(book.balance);
					$('#jPointTotal').text(book.total);
					$('#jPointBookId').val(book.bookId);
					// 点账户明细
					Page.create({
						id : 'jPointPageFoot',
						url : Leke.domain.payServerName + '/auth/common/account/pointlog.htm',
						pageSize : 20,
						jsonp : true,
						buttonId : 'jPointPageSearch',
						formId : 'jPointPageForm',
						callback : function(datas) {
							Handlebars.render('#jPointPageTpl', datas.page, '#jPointPageBody');
						}
					});
				}
			});
		}
	};

	module.exports = Point;
});
