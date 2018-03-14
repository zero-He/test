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
		self.init();
		self.listdata = ko.observableArray();
		self.totalSize = ko.observable();
		self.curPage = ko.observable(1);
		self.gradeOption = ko.observableArray();
		self.subjectOption = ko.observableArray();
		/*
		 * self.state = ko.observableArray([ { id : 1, name : "课前" }, { id : 2,
		 * name : "课中" } ]);
		 */
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
		// $('#startDay').val(seacheDate.add(-1, 'M').add(-1, 'd').format());
		// $('#endDay').val(seacheDate.add(-1, 'd').format());
		$('#startDay').click(function() {
			My97({
				dateFmt : 'yyyy-MM-dd',
				// minDate : '#F{$dp.$D(\'endDay\',{M:-6});}',
				maxDate : '#F{$dp.$D(\'endDay\');}',
				readOnly : true
			});
		});
		// ||$dp.$DV(\'%y-%M-%d\',{M:0,d:-1})
		$('#endDay').click(function() {
			My97({
				dateFmt : 'yyyy-MM-dd',
				// maxDate : '#F{$dp.$D(\'startDay\',{M:6}) ||
				// $dp.$DV(\'%y-%M-%d\',{d:-1})}',
				readOnly : true
			});
		});
		$(".init-menu0").hide();
		self.loadGradeData();
	}
	FormVM.prototype.loadGradeData = function() {
		var self = this;
		var req = $.ajax({
			type : 'get',
			url : 'getGradeSubjectList.htm',
			data : {
				schoolId : $('#schoolId').val()
			}
		});
		req.then(function(datas) {
			var gradeList = eval(datas.datas.gradeList);
			var subjectList = eval(datas.datas.subjectList)
			self.gradeOption(gradeList);
			self.subjectOption(subjectList);
		}, function(msg) {
			//Utils.Notice.alert('数据加载失败！');
		});
	};
	// 异步加载数据列表
	FormVM.prototype.loaddata = function() {
		var self = this;
		if (self.curPage.peek() == undefined) {
			return;
		}
		var req = $.ajax({
			type : 'get',
			url : 'getGradebeikeData.htm',
			data : {
				curPage : self.curPage.peek(),
				fromDate : $('#startDay').val(),
				endDate : $('#endDay').val(),
				schoolId : $('#schoolId').val(),
				gradeId : $("#gradeId").val(),
				subjectId : $("#subjectId").val()
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
				fromDate : $('#startDay').val(),
				endDate : $('#endDay').val(),
				schoolId : $('#schoolId').val(),
				gradeId : $("#gradeId").val(),
				subjectId : $("#subjectId").val()
			};
			var data = _.isString(query) ? query : JSON.stringify(query || {})
			window.location.href = 'exportExcelData.htm?action=grade&dataJson=' + data;
		}
		else {
			Utils.Notice.alert('没有数据可以导出！');
		}
	};
	FormVM.prototype.changeSel = function(val, data) {
		if (val == 1) {

			$(".init-btn").find("a").html("课前");
			$(".init-menu0").hide();
			$(".init-menu1").show();
			$(".init-menu2").show();
		}
		else if (val == 2) {
			$(".init-btn").find("a").html("课中");
			$(".init-menu0").show();
			$(".init-menu1").hide();
			$(".init-menu2").show();
		}
		else {
			$(".init-btn").find("a").html("课后");
			$(".init-menu0").show();
			$(".init-menu1").show();
			$(".init-menu2").hide();
		}
		data.stateChange(val);
	}
	ko.applyBindings(new FormVM());
});