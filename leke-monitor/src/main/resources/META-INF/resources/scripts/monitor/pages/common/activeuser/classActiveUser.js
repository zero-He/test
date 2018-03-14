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
		self.gradeOption = ko.observableArray();
		self.subjectOption = ko.observableArray();
		self.activeInfo = ko.observableArray(window.data.activeInfo);
		
		self.classesData = ko.observableArray();
		self.gradesData = ko.observableArray();
		self.stateChange = ko.observable(1);
		self.disposables = ko.observableArray();// 动态监视数组对象
		self.disposables.push(ko.computed(function() {
			// 点击分页
			var curPage = self.curPage();
			self.loaddata();
		}));
		fun();
		function fun(){
			var roleId = $("#roleId").val();
			var activeInfo = self.activeInfo();
			var classes = [];
			
			var grades = [];
			
			_.each(activeInfo, function(n) {
						if (n.roleId == roleId) {
							_.each(n.classes, function(c) {
								classes.push({name:c})
							})
							_.each(n.grades, function(g) {
								grades.push({name:g})
							})
						}
					});
			self.classesData(classes);
			self.gradesData(grades);
		}
		
		self.roleChange = function() {
			fun();
		}
	}


	// 异步加载数据列表
	FormVM.prototype.loaddata = function() {
		var self = this;
//		Utils.Notice.mask.create("正在查询请稍等");
		if (self.curPage.peek() == undefined) {
			return;
		}
		var req = $.ajax({
			type : 'get',
			url : 'getClassActiveUserData.htm',
			data : {
				curPage : self.curPage.peek(),
				cycle : $('#ts').attr("title"),
				roleId : $('#roleId option:selected').val(),
				d : $('#terminal').attr("title"),
				schoolName : $('#schoolName').text(), 
				gradeName : $('#gradeName option:selected').text(),
				className : $('#className option:selected').text(),
				ts : $('#ts').text(),
			}
		});
		req.then(function(datas) {
//			Utils.Notice.mask.close();
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
					cycle : $('#ts').attr("title"),
					roleId : $('#roleId option:selected').val(),
					d : $('#terminal').attr("title"),
					schoolName : $('#schoolName').text(), 
					gradeName : $('#gradeName option:selected').text(),
					className : $('#className option:selected').text(),
					ts : $('#ts').text(),
			};
			var data = _.isString(query) ? query : JSON.stringify(query || {})
			window.location.href = 'exportClassActiveUser.htm?action=grade&dataJson=' + data;
		}
		else {
			Utils.Notice.alert('没有数据可以导出！');
		}
	};

	ko.applyBindings(new FormVM());
});