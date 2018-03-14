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
		self.stateChange = ko.observable(1);
		self.ResourceSelected = ko.observable(1);
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
		$('#startDay').val(seacheDate.add(-6, 'd').format());
		$('#endDay').val(seacheDate.format());
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
				readOnly : true
			});
		});
		$(".init-menu0").hide();
		
	}

	// 异步加载数据列表
	FormVM.prototype.loaddata = function() {
		var self = this;
		
		if (self.curPage.peek() == undefined) {
			return;
		}
		var req = $.ajax({
			type : 'get',
			url : 'getResourceTopFiftyData.htm',
			data : {
				curPage : self.curPage.peek(),
				fromDate : $('#startDay').val(),
				endDate : $('#endDay').val(),
				resType : $("#resType  option:selected").val(),
				schoolStageId : $('#schoolStage option:selected').val(),
				subjectId : $('#subject option:selected').val(),
			}
		});
		req.then(function(datas) {
			var i =  $("#resType  option:selected").val();
			self.ResourceSelected(i);
			var record = eval(datas.datas.record)||[];
			var page = datas.datas.page; 
			var href = '';
			$.each(record,function(index,obj){
				if(obj.resType == 1){
					obj.href = 'https://question.leke.cn/auth/common/question/view.htm?questionId='+obj.resId;
				}else if(obj.resType == 2){
					obj.href = 'https://paper.leke.cn/auth/common/paper/view.htm?paperId='+obj.resId;
				}else if(obj.resType == 3){
					obj.href = 'https://beike.leke.cn/auth/common/courseware/preview.htm?coursewareId='+obj.resId;
				}else if(obj.resType == 4){
					obj.href = 'https://beike.leke.cn/auth/common/microcourse/preview.htm?microcourseId='+obj.resId;
				}else if(obj.resType == 5){
					obj.href = 'https://paper.leke.cn/auth/common/workbook/view.htm?workbookId='+obj.resId;
				}else if(obj.resType == 6){
					obj.href = 'https://beike.leke.cn/auth/common/beikepkg/beikePkgPreview.htm?beikePkgId='+obj.resId;
				}
			})
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
					curPage : self.curPage.peek(),
					fromDate : $('#startDay').val(),
					endDate : $('#endDay').val(),
					resType : $("#resType  option:selected").val(),
					schoolStageId : $('#schoolStage option:selected').val(),
					subjectId : $('#subject option:selected').val(),
			};
			var data = _.isString(query) ? query : JSON.stringify(query || {})
			window.location.href = 'exportResourceTopData.htm?dataJson=' + data;
		}
		else {
			Utils.Notice.alert('没有数据可以导出！');
		}
	};
	ko.applyBindings(new FormVM());
});