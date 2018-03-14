define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var utils = require('utils');
	var http = require('common/base/http');
	var dialog = require('dialog');
	var Utils = require('utils');
	var My97 = require('date');
	var LekeDate = require('common/base/date');
	require('common/knockout/component/leke-page');
	// viewModel
	function FormVM(params) {
		var self = this;
		self.init();
		self.listdata = ko.observableArray();
		self.totalSize = ko.observable();
		self.curPage = ko.observable(1);
		self.gradeOption = ko.observableArray();
		self.subjectOption = ko.observableArray();
		self.stateChange = ko.observable(1);
		self.disposables = ko.observableArray();// 动态监视数组对象
		self.disposables.push(ko.computed(function() {
			// 点击分页
			var curPage = self.curPage();
			self.loaddata();
		}));
	}
	// 初始化控件
	FormVM.prototype.init = function() {
		var self = this;
		var seacheDate = LekeDate.of(window.currentSystemTime);
		$('#startDay').click(function() {
			My97({
				dateFmt : 'yyyy-MM-dd',
				maxDate : '#F{$dp.$D(\'endDay\');}',
				readOnly : true
			}); 
		});
		$('#endDay').click(function() {
			My97({
				dateFmt : 'yyyy-MM-dd',
				minDate : '#F{$dp.$D(\'startDay\')}',
				readOnly : true
			});
		});
		$(".init-menu0").hide();
	} 
	FormVM.prototype.querydata = function() {
		var self = this;
		self.curPage(1);
		self.loaddata();
	}
	// 异步加载数据列表
	FormVM.prototype.loaddata = function() {
		var self = this;
		utils.Notice.mask.create("正在查询请稍等");
		if (self.curPage.peek() == undefined) {
			return;
		}
		var req = $.ajax({
			type : 'get',
			url : 'getSchoolHaviorData.htm',
			data : {
				curPage : self.curPage.peek(),
				fromDate : $('#startDay').val(),
				endDate : $('#endDay').val(),
				type : $('#test option:selected').text(),
				schoolName : $('#schoolName').val(),
				schoolStageId : $('#stage option:selected').val(),
			}
		});
		req.then(function(datas) {
			utils.Notice.mask.close();
			var record = eval(datas.datas.record);
			var page = datas.datas.page;
			self.listdata(record || []);
			self.totalSize(page.totalSize || 0);
			var dom = $('.btn-wrap button');
            if(dom != undefined ){
            	var btn = dom.get(0) || {};
            	btn.type = "button";
            }
		}, function(msg) {
			Utils.Notice.alert('数据加载失败！');
		});
	};
	FormVM.prototype.exportExcel = function() {
		var self = this;
		if (self.listdata().length > 0) {
			var query = {
				fromDate : $('#startDay').val(),
				endDate : $('#endDay').val(),
				type : $('#test option:selected').text(),
				schoolName : $('#schoolName').val(),
				schoolStageId : $('#stage option:selected').val(),
			};
			var data = _.isString(query) ? query : JSON.stringify(query || {})
			window.location.href = 'exportSchoolStatisViewData.htm?action=grade&dataJson=' + data;
		}
		else {
			Utils.Notice.alert('没有数据可以导出！');
		}
	};
	FormVM.prototype.opendetail = function(data) {
		var self = this;
		window.open('schoolLessondetail.htm?fromDate=' + $('#startDay').val() + '&endDate=' + $('#endDay').val()
				 + '&schoolName=' + data.schoolName + '&schoolStageName=' + data.schoolStageName);
	};
	FormVM.prototype.openteacher = function(data) {
		var self = this;
		window.open('teacherLessonStatistics.htm?fromDate=' + $('#startDay').val() + '&endDate=' + $('#endDay').val()
				+ '&schoolName=' + data.schoolName + '&schoolStageName=' + data.schoolStageName);
	};
	ko.applyBindings(new FormVM());
});