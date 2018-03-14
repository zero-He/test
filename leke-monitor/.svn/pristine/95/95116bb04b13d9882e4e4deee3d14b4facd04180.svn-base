define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var http = require('common/base/http');
	var dialog = require('dialog');
	var Utils = require('utils');
	var My97 = require('date');
	var LekeDate = require('common/base/date');
	require('common/knockout/component/leke-page');
	// viewModel
	function FormVM(params) {
		var self = this;
		self.listdata = ko.observableArray();
		self.curPage = ko.observable(1);
		
		self.disposables = ko.observableArray();// 动态监视数组对象
		self.disposables.push(ko.computed(function() {
			self.loaddata();
		}));
	}
	// 异步加载数据列表
	FormVM.prototype.loaddata = function() {
		var self = this;
		if (self.curPage.peek() == undefined) {
			return;
		}
		var req = $.ajax({
			type : 'get',
			url : 'getClassStatisticsData.htm',
			data : {
				loginName : $('#loginName').text(),
				teacherName : $('#teacherName').text(),
				month : $('#month').text(),
			}
		});
		req.then(function(datas) {
				var record = eval(datas.datas.record);
				self.listdata(record || []);
			}, function(msg) {
				Utils.Notice.alert('数据加载失败!');
			});
	};

	// 导出
	FormVM.prototype.exportExcel = function() {
		var self = this;
			var query = {
					loginName : $('#loginName').text(),
					teacherName : $('#teacherName').text(),
					month : $('#month').text(),
			};
			var data = _.isString(query) ? query : JSON.stringify(query || {})
					window.location.href = 'exportClassStatisticsData.htm?dataJson=' + data;
	};
	ko.applyBindings(new FormVM());
});

