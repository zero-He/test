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
		// 学生考勤列表
		self.listdata = ko.observableArray();
		self.totalSize = ko.observable();
		self.curPage = ko.observable(1);
		// 班主任考勤列表
		self.listdata2 = ko.observableArray();
		self.totalSize2 = ko.observable();
		self.curPage2 = ko.observable(1);

		self.stateChange = ko.observable(1);
		self.disposables = ko.observableArray();// 动态监视数组对象
		self.disposables.push(ko.computed(function() {
			// 点击分页
			var curPage = self.curPage();
			self.loaddata();
		}));

		self.disposables2 = ko.observableArray();// 动态监视数组对象
		self.disposables2.push(ko.computed(function() {
			// 点击分页
			var curPage = self.curPage2();
			self.loaddata2();
		}));
		self.init();
	}
	// 初始化控件
	FormVM.prototype.init = function() {
		var self = this;
		// self.loaddata();
	}
	// 异步加载数据列表
	FormVM.prototype.loaddata = function() {
		var self = this;
		if (self.curPage.peek() == undefined) {
			return;
		}
		var query = self.toJson(window.data.query);
		query["curPage"] = self.curPage();
		var req = $.ajax({
			type : 'post',
			url : 'getStudentAttendanceClassList.htm',
			data : query
		});
		req.then(function(datas) {
			var record = eval(datas.datas.record);
			var page = datas.datas.page;
			$.each(record, function(i, n) {
				var url = '/auth/common/crm/attendance/dtlslist.htm';
				var query = self.toUrl(window.data.query);
				var classId = n.classId || 0;
				var className = n.className || '';
				url += '?' + query + 'classId=' + classId + '&className=' + className;
				n.url = url;
			});
			self.listdata(record || []);
			self.totalSize(page.totalSize || 0);
		}, function(msg) {
			Utils.Notice.alert('数据加载失败！');
		});
	};

	// 加载班主任考勤
	FormVM.prototype.loaddata2 = function() {
		var self = this;
		if (self.curPage2.peek() == undefined) {
			return;
		}
		var query = self.toJson(window.data.query);
		query["curPage"] = self.curPage2();
		var req = $.ajax({
			type : 'post',
			url : 'getTeacherAttendanceClassList.htm',
			data : query
		});
		req.then(function(datas) {
			var record = eval(datas.datas.record);
			var page = datas.datas.page;
			$.each(record, function(i, n) {
				var url = '/auth/common/crm/attendance/teacherLessonlist.htm';
				var query = self.toUrl(window.data.query);
				var classId = n.classId || 0;
				var className = n.className || '';
				var headTeacherId = n.headTeacherId || 0;
				var headTeacherName = n.headTeacherName || '';
				url += '?' + query + 'classId=' + classId + '&className=' + className + '&headTeacherId=' + headTeacherId
						+ '&headTeacherName=' + headTeacherName;
				n.url = url;
			});
			self.listdata2(record || []);
			self.totalSize2(page.totalSize || 0);
		}, function(msg) {
			Utils.Notice.alert('数据加载失败！');
		});
	};
	// 导出
	FormVM.prototype.exportExcel = function() {
		var self = this;
		var query = self.toJson(window.data.query);
		if ((self.listdata().length > 0 && self.stateChange() == "1") || (self.listdata2().length > 0 && self.stateChange() == "2")) {
			var query = query;
			var data = _.isString(query) ? query : JSON.stringify(query || {})
			if (self.stateChange() == "1") {
				window.location.href = 'exportExcelData.htm?action=classStudent&dataJson=' + data;
			}
			else {
				window.location.href = 'exportExcelData.htm?action=classTeacher&dataJson=' + data;
			}
		}
		else {
			Utils.Notice.alert('没有数据可以导出！');
		}
	};
	// 切换
	FormVM.prototype.changeSel = function(val, data) {
		var self = this;
		if (val == 1) {
			$(".init-btn").find("a").html("学生");
			$(".init-menu0").hide();
			$(".init-menu1").show();
			self.loaddata();
		}
		else if (val == 2) {
			$(".init-btn").find("a").html("班主任");
			$(".init-menu1").hide();
			$(".init-menu0").show();
			self.loaddata2();
		}
		data.stateChange(val);
	}

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
	// 格式化URl参数
	FormVM.prototype.toUrl = function(query) {
		var url = '';
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
				url += p + '=' + query[p] + '&';
			}
		}
		return url;
	}
	ko.applyBindings(new FormVM());
});