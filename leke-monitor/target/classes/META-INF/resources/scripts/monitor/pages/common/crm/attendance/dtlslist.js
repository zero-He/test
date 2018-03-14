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
		self.totalSize = ko.observable();
		self.curPage = ko.observable(1);
		self.disposables = ko.observableArray();// 动态监视数组对象
		self.disposables.push(ko.computed(function() {
			// 点击分页
			var curPage = self.curPage();
			self.loaddata();
		}));
		self.init();
	}
	// 初始化控件
	FormVM.prototype.init = function() {
		var self = this;
		var seacheDate = LekeDate.of(window.currentSystemTime);
		$('#startDay').val(window.data.query.fromDate);
		$('#endDay').val(window.data.query.endDate);
		$('#startDay').click(function() {
			My97({
				dateFmt : 'yyyy-MM-dd',
				minDate : window.data.query.fromDate,
				maxDate : '#F{$dp.$D(\'endDay\');}',
				readOnly : true
			});
		});
		// ||$dp.$DV(\'%y-%M-%d\',{M:0,d:-1})
		$('#endDay').click(function() {
			My97({
				dateFmt : 'yyyy-MM-dd',
				maxDate : window.data.query.endDate,
				// $dp.$DV(\'%y-%M-%d\',{d:-1})}',
				readOnly : true
			});
		});
	}
	// 异步加载数据列表
	FormVM.prototype.loaddata = function() {
		var self = this;
		if (self.curPage.peek() == undefined) {
			return;
		}
		var query = self.toJson(window.data.query);
		query["curPage"] = self.curPage();
		query["fromLessonDate"] = $('#startDay').val();
		query["endLessonDate"] = $('#endDay').val();
		query["lessonName"] = $('#lessonName').val();
		var req = $.ajax({
			type : 'post',
			url : 'getStudentAttendanceLessonList.htm',
			data : query
		});
		req.then(function(datas) {
			var record = eval(datas.datas.record);
			var page = datas.datas.page;
			self.listdata(record || []);
			self.totalSize(page.totalSize || 0);
		}, function(msg) {
			Utils.Notice.alert('数据加载失败！');
		});
	};
	// 导出
	FormVM.prototype.exportExcel = function() {
		var self = this;
		var query = self.toJson(window.data.query);
		if (self.listdata().length > 0) {
			var query = query;
			var data = _.isString(query) ? query : JSON.stringify(query || {})
			window.location.href = 'exportExcelData.htm?action=lessonStudent&dataJson=' + data;
		}
		else {
			Utils.Notice.alert('没有数据可以导出！');
		}
	};
	// 格式化参数
	FormVM.prototype.toJson = function(query) {
		var data = {};
		for ( var p in query) {
			if (query[p] != null) {
				if (p.indexOf('Date') != -1 || p.indexOf('date') != -1) {
					var date = new Date(query[p]);
					var month = date.getMonth() + 1;
					var day = date.getDate();
					if (month < 10) {
						month = '0' + month;
					}
					if (day < 10) {
						day = '0' + day;
					}
					query[p] = date.getFullYear() + '-' + month + '-' + day;
				}
				data[p] = query[p];
			}
		}
		return data;
	}
	ko.applyBindings(new FormVM());
});