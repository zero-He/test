define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var http = require('common/base/http');
	var dialog = require('dialog');
	var Utils = require('utils');
	var My97 = require('date');
	var Echart = require('echart');
	var Chart = require('chart');
	var LekeDate = require('common/base/date');
	require('common/knockout/component/leke-page');
	require('common/ui/ui-autocomplete/ui-autocomplete');
	// viewModel
	function FormVM(params) {
		var self = this;
		self.listdata = ko.observableArray();
		self.totalSize = ko.observable();
		self.curPage = ko.observable(1);
		self.school = ko.observable();
		self.schoolId = ko.observable();
		self.ResourceSelected = ko.observable(1);
		self.stateChange = ko.observable(1);
		self.init();
		self.disposables = ko.observableArray();// 动态监视数组对象
		self.fLoadSsForTimeChart();
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
		self.$sch = $('.j-school-select');
		self.$sch.autocomplete({
			url : Leke.ctx + '/auth/common/data/querySchoolLike.htm',
			nameKey : 'schoolName',
			onChange : function(school) {
				self.school(school);
				if (school != null) {
					self.schoolId(school.schoolId)
				}
			}
		});
		$(".init-menu0").hide();
	}
	// 异步加载数据列表
	FormVM.prototype.loaddata = function() {
		var self = this;
		var sch = self.school.peek();
		
		if (sch) {
			self.schoolId(sch.schoolId);
		}
		else {
			self.schoolId('');
		}
		var req = $.ajax({
			type : 'get',
			url : 'schoolResourceUsedDetail.htm',
			data : {
				curPage : self.curPage.peek(),
				startDateTime : $('#startDay').val(),
				endDataTime : $('#endDay').val(),
				schoolStageId : $('#schoolStage option:selected').val(),
				subjectId : $('#subject option:selected').val(),
				resSource: $('#resourceSource  option:selected').val(),
				resType :$('#resType  option:selected').val(),
				schoolId : self.schoolId.peek()
			}
		});
		req.then(function(datas) {
			var i =  $("#resType  option:selected").val();
			self.ResourceSelected(i);
			var record = eval(datas.datas.record);
			var page = datas.datas.page;
			self.listdata(record || []);
			self.totalSize(page.totalSize || 0);
		}, function(msg) {
			//Utils.Notice.alert('数据加载失败！');
		});
	};
	// 导出
	FormVM.prototype.exportExcel = function() {
		var self = this;
		var sch = self.school.peek();
		
		if (sch) {
			self.schoolId(sch.schoolId);
		}
		else {
			self.schoolId('');
		}
		if (self.listdata().length > 0) {
			var query = {
				startDateTime : $('#startDay').val(),
				endDataTime : $('#endDay').val(),
				schoolStageId : $('#schoolStage option:selected').val(),
				subjectId : $('#subject option:selected').val(),
				resSource: $('#resourceSource  option:selected').val(),
				resType :$('#resType  option:selected').val(),
				schoolId : self.schoolId.peek()
			};
			var data = _.isString(query) ? query : JSON.stringify(query || {})
			window.location.href = 'exportExcelData.htm?action=school&dataJson=' + data;
		}
		else {
			Utils.Notice.alert('没有数据可以导出！');
		}
	};
	FormVM.prototype.opendetail = function(data) {
		var self = this;

		window.open('schoolUsedTrend.htm?schoolId=' + data.schoolId +'&schoolName='+ data.schoolName
				+'&startDate='+ $('#startDay').val() + '&endDate=' + $('#endDay').val()
				+'&resType=' + $('#resType option:selected').val() 
				+'&resTypeName=' + $('#resType option:selected').text()
				+'&resResource=' + $('#resourceSource  option:selected').val()
				+'&resResourceName=' + $('#resourceSource  option:selected').text()
			);
	};
	
	FormVM.prototype.openTopDetail = function(data) {
		var self = this;
		window.open('resourceTopFifty.htm?resType=' + $('#resType option:selected').val());
		
	};

	FormVM.prototype.fLoadSsForTimeChart = function() {
		var _this = this;
//		Utils.Notice.mask.create("正在查询请稍等");
		$.ajax({
			type : 'post',
			url : ctx + '/auth/common/resource/resourceUsedChart.htm',
			data : {
				startDateTime : $('#startDay').val(),
				endDataTime : $('#endDay').val(),
				schoolStageId : $('#schoolStage option:selected').val(),
				subjectId : $('#subject option:selected').val(),
				resSource: $('#resourceSource  option:selected').val(),
				resType :$('#resType  option:selected').val(),
			},
			dataType : 'json',
			async : true,
			success : function(datas) {
//				Utils.Notice.mask.close();
				var dateArr = datas.datas.dateArr;
				var newResourceNumArr = datas.datas.newResourceNumArr;
				var usedResourceNumArr = datas.datas.usedResourceNumArr;
				var usedRescourceCountArr = datas.datas.usedRescourceCountArr;
				var res = datas.datas.resourceUsedSta;
				$('#resouceNum').text(res.resouceNum);
				$('#newResourceNum').text(res.newResourceNum);
				$('#usedResourceNum').text(res.usedResourceNum);
				$('#usedRescourceCount').text(res.usedRescourceCount)
				if(dateArr != null && dateArr != ''){
					Echart.init($('#barSta')[0]).setOption({
					    tooltip : {
							trigger : 'axis',
							axisPointer : {
								type : 'shadow'
							}	
						},
					    legend: {
					        data:['创建资源数','使用资源数','使用资源量'],
					        x: 'right',
					    },
//						color : Chart.Color.SCORE,
						toolbox : {
							show : true,
							orient : 'vertical',
							y : 'center',
							feature : {
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
					    	data : dateArr
					    }],
					    yAxis : [{
							type : 'value'
						}],
					    series : [{name:"创建资源数", type:'line', data:newResourceNumArr},
					              {name:"使用资源数", type:'line', data:usedResourceNumArr},
					              {name:"使用资源量", type:'line', data:usedRescourceCountArr}
					    ]
					});	
				}else {
					Echart.init($('#barSta')[0]).setOption({});
					$('#barSta').html("<div class='eval-rate-pictab m-tips f-block' style='padding-top:107px;'><i></i> <span>对不起，没有您要查询的数据</span></div>")
				}
				
			
			}
		});
	}, 
	FormVM.prototype.loadPage = function() {
		var self = this;
		self.school("");
		$('#schoolId').val('')
		self.fLoadSsForTimeChart();
		self.loaddata();
	}
	ko.applyBindings(new FormVM());
});