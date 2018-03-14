define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var http = require('common/base/http');
	var dialog = require('dialog');
	var Utils = require('utils');
	var My97 = require('date');
	var LekeDate = require('common/base/date');
	var Echart = require('echart');
	var Chart = require('chart');
	require('common/knockout/component/leke-page');
	var PageSort = require("monitor/component/pagesort");
	// viewModel
	function FormVM(params) {
		var self = this;
		self.init();
		self.fLoadSsForTimeChart();
		self.listdata = ko.observableArray();
		self.totalSize = ko.observable();
		self.curPage = ko.observable(1);
		self.areas = ko.observableArray(window.data.areas);
		// 营销处
		self.dptData = ko.observableArray(window.data.areas);
		self.stateChange = ko.observable(1);
		self.disposables = ko.observableArray();// 动态监视数组对象
		PageSort.init();
		self.disposables.push(ko.computed(function() {
			// 点击分页
			var curPage = self.curPage();
			self.loaddata();
		}));
		
		self.marketChange = function() {
			var marketId = $("#market").val();

			var area = self.areas();

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
		 $('#jStartTime').val(seacheDate.format('yyyy-MM'));

		$('#jStartTime').click(function(){
			My97({
				dateFmt:'yyyy-MM',
				maxDate:$('#jEndTime').val() || '%y-%M-%d',
				readOnly:true,
				isShowClear:false
			});
		});
		

		$(".init-menu0").hide();
	}
	
	FormVM.prototype.fLoadSsForTimeChart = function() {
		var _this = this;
		var startStr = $('#jStartTime').val();
		$.ajax({
			type : 'post',
			url : ctx + '/auth/common/crm/loadStatisForChart.htm',
			data : {
				month : startStr,
			},
			dataType : 'json',
			async : true,
			success : function(datas) {
				var nameArr = datas.datas.nameArr;
				var roleId = datas.datas.roleId;
				var tradeAmountArr = datas.datas.tradeAmountArr;
				var commAmountArr = datas.datas.commAmountArr;
				if (nameArr != null && nameArr != '') {
					if (roleId !=112 ) {
						var newTeachNumArr = datas.datas.newTeachNumArr;
						
						Echart.init($('#barSta1')[0]).setOption({

						    tooltip : {
								trigger : 'axis',
								axisPointer : {
									type : 'shadow'
								}	
							},
							color : Chart.Color.SCORE,
							toolbox : {
								show : true,
								orient : 'vertical',
								y : 'center',
								feature : {
									magicType : {
										show : true,
										type : ['bar', 'stack']
									},
									restore : {
										show : true
									},
									saveAsImage : {
										show : true
									}
								}
							},
							dataZoom : {
								show : true,
						        realtime: true,

							},
						    xAxis : [{
						    	type : 'category',
						    	data : nameArr
						    }],
						    yAxis : [{
								type : 'value'
							}],
						    series : [{name:"新增老师数", type:'bar', data:newTeachNumArr}
						    ]
						});
					}
					var myEchart = Echart.init($('#barSta')[0]).setOption({

					    tooltip : {
							trigger : 'axis',
							axisPointer : {
								type : 'shadow'
							}	
						},

//						legend: {
//							data:['累计充值金额','提成金额']
//						
//						},
						dataZoom : {
							show : true,
					        realtime: true,
						},
						color : Chart.Color.SCORE,
						toolbox : {
							show : true,
							orient : 'vertical',
							y : 'center',
							feature : {
								magicType : {
									show : true,
									type : ['bar', 'stack']
								},
								restore : {
									show : true
								},
								saveAsImage : {
									show : true
								}
							}
						},

					    xAxis : [{
					    	type : 'category',
					    	data : nameArr
					    }],
					    yAxis : [{
							type : 'value'
						}],
					    series : [
					              {name:"充值金额", type:'bar', data:tradeAmountArr},{name:"提成金额", type:'bar', data:commAmountArr}
					    ]
					});
				} else {
					Echart.init($('#barSta1')[0]).setOption({});
					$('#barSta1').html("<div class='eval-rate-pictab m-tips f-block'><i></i> <span>对不起，没有您要查询的数据</span></div>")
					Echart.init($('#barSta')[0]).setOption({});
					$('#barSta').html("<div class='eval-rate-pictab m-tips f-block'><i></i> <span>对不起，没有您要查询的数据</span></div>")
				}
				$('#but').removeClass('u-btn-bg-gray');
				$('#but').addClass('u-btn-bg-turquoise');
				$('#but1').addClass('u-btn-bg-gray');
				$('#but1').removeClass('u-btn-bg-turquoise');
				$('#barSta1').css({ opacity: 1 });
				$('#barSta1').hide();
				$('#barSta').show();
			}
		});
	}, 
	
	// 异步加载数据列表
	FormVM.prototype.loaddata = function() {
		var self = this;
//		Utils.Notice.mask.create("正在查询请稍等");
		if (self.curPage.peek() == undefined) {
			return;
		}
		var req = $.ajax({
			type : 'get',
			url : 'getindividualsInData.htm',
			data : {
				curPage : self.curPage.peek(),
				month : $('#jStartTime').val(),
				teacherName : $('#teacherName').val(),
				sellerName : $('#sellerName').val(),
				loginName : $('#loginName').val(),
				marketId : $('#marketId').val(),
				departId : $('#market').val(),
				order :$('#jPageOrder').val(),
				sort :$('#jPageSort').val(),
			}
		});
		req.then(function(datas) {
//			Utils.Notice.mask.close();
			var record = eval(datas.datas.record);
			var page = datas.datas.page;
			var individualsInDto = datas.datas.detail;
			$('#teacherNum').text(individualsInDto.teacherNum);
			$('#newTeacherNum').text(individualsInDto.newTeacherNum);
			$('#newInTeacherNum').text(individualsInDto.newInTeacherNum);
			$('#tradeAmount').text(individualsInDto.tradeAmount)
			$('#commAmount').text(individualsInDto.commAmount)
			
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
					month : $('#jStartTime').val(),
					teacherName : $('#teacherName').val(),
					sellerName : $('#sellerName').val(),
					loginName : $('#loginName').val(),
					marketId : $('#marketId').val(),
					departId : $('#market').val(),
			};
			var data = _.isString(query) ? query : JSON.stringify(query || {})
			window.location.href = 'exportExcel.htm?action=grade&dataJson=' + data;
		}
		else {
			Utils.Notice.alert('没有数据可以导出！');
		}
	};
	FormVM.prototype.loadList = function() {
		var self = this;
		$('#barSta1').show();
		$('#barSta').show();
		$('#barSta1').css({ opacity: 0 });
		self.fLoadSsForTimeChart();
		self.loaddata();
	};
	
	FormVM.prototype.openlessondetail = function(data) {
		var self = this;
		window.open('indiClassStatistics.htm?teacherName=' + data.teacherName + '&month=' + $('#jStartTime').val()
				+ '&loginName=' + data.loginName) ;
	};
	FormVM.prototype.opendetail = function(data) {
		var self = this;
		window.open('sellerInDetail.htm?marketName=' + data.marketName + '&month=' + $('#jStartTime').val()) ;
	};
	FormVM.prototype.openteacherdetail = function(data) {
		var self = this;
		window.open('teacherInDetail.htm?sellerName=' + data.sellerName + '&month=' + $('#jStartTime').val()) ;
	};
	ko.applyBindings(new FormVM());
});