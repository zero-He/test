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
		self.areas = ko.observableArray(window.data.areas);
		// 营销处
		self.dptData = ko.observableArray(window.data.areas);
		self.stateChange = ko.observable(1);
		self.disposables = ko.observableArray();// 动态监视数组对象
		self.disposables.push(ko.computed(function() {
			// 点击分页
			var curPage = self.curPage();
			self.loaddata();
		}));
		self.marketChange = function() {
			var marketId = $("#market").val();

			var area = self.areas();c                                                                                 

			var market = [];

			_.each(area, function(n) {
						if (marketId.length <= 0
								|| parseInt(n.pId) == parseInt(marketId)) {
							market.push(n);
						}
					});

			self.dptData(market);
		}
	}
	// 初始化控件
	FormVM.prototype.init = function() {
		var self = this;
		var seacheDate = LekeDate.of(window.currentSystemTime);
		 $('#startDay').val(seacheDate.add(-1, 'd').format());
		 $('#endDay').val(seacheDate.add(-1, 'd').format());
		 $('#jStartTime').val(seacheDate.format('yyyy-MM'));
		 $('#jEndTime').val(seacheDate.format('yyyy-MM'));
        new Weekpicker({ output: '#startWeek' }).initEmpty();
        new Weekpicker({ output: '#endWeek' }).initEmpty();
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
		$('#jStartTime').click(function(){
			My97({
				dateFmt:'yyyy-MM',
				maxDate:$('#jEndTime').val() || '%y-%M-%d',
				readOnly:true,
				isShowClear:false
			});
		});
		
		$('#jEndTime').click(function(){
			My97({
				dateFmt:'yyyy-MM',
				minDate:$('#jStartTime').val(),
				maxDate:'%y-%M-%d',
				readOnly:true,
				isShowClear:false
			});
		});
		$(".init-menu0").hide();
	}

	// 异步加载数据列表
	FormVM.prototype.loaddata = function() {
		var self = this;
		Utils.Notice.mask.create("正在查询请稍等");
		if (self.curPage.peek() == undefined) {
			return;
		}
		var req = $.ajax({
			type : 'get',
			url : 'getschoolActiveUserData.htm',
			data : {
				curPage : self.curPage.peek(),
				fromDate : $('#startDay').val(),
				endDate : $('#endDay').val(),
				cycle : $("#cycle_sel option:selected").text(),
				d : $("#leke_device option:selected").val(),
				roleId : $('#roleId option:selected').val(),
				os : $('#os option:selected').text(),
				schoolName : $('#schoolName').val(),
				sellerName : $('#sellerName').val(),
				loginName : $('#loginName').val(),
				marketId : $('#marketId').val(),
				departId : $('#market').val(),
				startMonth : $('#jStartTime').val(),
				endMonth : $('#jEndTime').val(),
				startWeek : $('#startWeek').val(),
				endWeek : $('#endWeek').val(),
			}
		});
		req.then(function(datas) {
			Utils.Notice.mask.close();
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
				cycle : $("#cycle_sel option:selected").text(),
				d : $("#leke_device option:selected").val(),
				roleId : $('#roleId option:selected').val(),
				os : $('#os option:selected').text(),
				schoolName : $('#schoolName').val(),
				sellerName : $('#sellerName').val(),
				loginName : $('#loginName').val(),
				marketId : $('#marketId').val(),
				departId : $('#market').val(),
				startMonth : $('#jStartTime').val(),
				endMonth : $('#jEndTime').val(),
				startWeek : $('#startWeek').val(),
				endWeek : $('#endWeek').val(),
			};
			var data = _.isString(query) ? query : JSON.stringify(query || {})
			window.location.href = 'exportSchoolActiveUser.htm?action=grade&dataJson=' + data;
		}
		else {
			Utils.Notice.alert('没有数据可以导出！');
		}
	};
	FormVM.prototype.opendetail = function(data) {
		var self = this;
		window.open('classActiveUser.htm?d=' + $("#leke_device option:selected").val() + '&ts=' + data.ts
				 + '&cycle=' + $("#cycle_sel option:selected").text() + '&schoolName=' + data.schoolName
				 + '&terminal=' + $("#leke_device option:selected").text() + '&sellerName=' + data.sellerName
				 + '&marketName=' + data.marketName + '&roleId='+$('#roleId option:selected').val()) ;
	};
	ko.applyBindings(new FormVM());
});