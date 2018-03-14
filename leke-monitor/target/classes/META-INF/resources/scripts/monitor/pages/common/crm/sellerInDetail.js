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
	var PageSort = require("monitor/component/pagesort");
	// viewModel
	function FormVM(params) {
		var self = this;
		self.listdata = ko.observableArray();
		self.totalSize = ko.observable();
		self.curPage = ko.observable(1);
		self.gradeOption = ko.observableArray();
		self.subjectOption = ko.observableArray();

		self.disposables = ko.observableArray();// 动态监视数组对象
		PageSort.init();
		self.disposables.push(ko.computed(function() {
			// 点击分页
			var curPage = self.curPage();
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
			url : 'getSellerInDetailData.htm',
			data : {
				curPage : self.curPage.peek(),
				month : $('#month').text(),
				sellerName : $('#sellerName').val(),
				loginName : $('#loginName').val(),
				marketName : $('#marketName').text(),
				order :$('#jPageOrder').val(),
				sort :$('#jPageSort').val(),
			}
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
	FormVM.prototype.exportExcel = function() {
		var self = this;
		if (self.listdata().length > 0) {
			var query = {
					month : $('#month').text(),
					sellerName : $('#sellerName').val(),
					loginName : $('#loginName').val(),
					marketName : $('#marketName').text(),
			};
			var data = _.isString(query) ? query : JSON.stringify(query || {})
			window.location.href = 'exportSellerdetail.htm?action=grade&dataJson=' + data;
		}
		else {
			Utils.Notice.alert('没有数据可以导出！');
		}
	};
	FormVM.prototype.opendetail = function(data) {
		var self = this;
		window.open('teacherInDetail.htm?sellerName=' + data.sellerName + '&month=' + $('#month').text()) ;
	};
	ko.applyBindings(new FormVM());
});