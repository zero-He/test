define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('underscore');
	var $ = require('jquery');
	var http = require('common/base/http');
	var LekeDate=require('common/base/date');
	var utils = require('utils');
	var ROLES = require('core-business/roles');
	var Echart = require('echart');
	var WdatePicker = require('date');
	var datepicker = require('common/knockout/bindings/datepicker');
	require('common/knockout/bindings/datetext');
	require('common/ui/ui-address/ui-market');
	require('common/ui/ui-autocomplete/ui-autocomplete');
	function Query(options) {
		var self = this;
		var ops = _.extend({}, options);
		//默认查询一月
		var seachDate=LekeDate.of(new Date());
		self.startDay=ko.observable(seachDate.add(-1,'M').format());
		self.endDay=ko.observable(seachDate.format());
		self.area = ko.observable(ops.area);
		self.school = ko.observable(ops.school);
		self.schoolId=ko.observable();
		
	};
	
	Query.prototype.toJS = function() {
		var self = this;
		var result = {};
		var sch = self.school.peek();
		if(sch) {
			self.schoolId(sch.schoolId);
			result.schoolIds = [sch.schoolId];
		} else {
			var area = self.area.peek();
			if(area) {
				result.area = area;
			}
		}
		result.startDay=self.startDay.peek().replace('-','').replace('-','');
		result.endDay=self.endDay.peek().replace('-','').replace('-','');
		return result;
	};
	function TableVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.list=ko.observableArray();//列表
		self.expectCountsTotal=ko.observable(0);//应到学生人数合计
		self.actualCountsTotal=ko.observable(0);//实到学生人数合计
		self.expectTimesTotal=ko.observable(0);//应到学生人次合计
		self.actualTimesTotal=ko.observable(0);//实到学生人次合计
		self.ratioTimesTotal=ko.observable(0);//到课率的合计
		
		self.options = _.extend({}, params.options);
		self.query = new Query(params.query);
		self.$sbox = self.$el.find('.j-search-box');
		var roleId = self.options.roleId;
		self.isPresident=roleId == ROLES.PRESIDENT;//是否是校长
		if(roleId == ROLES.SELLER||self.isPresident) {
			// hide search box
			self.$sbox.hide();
		} else {
			// init area select
			self.$a = self.$sbox.find('.j-area-select');
			if(roleId != ROLES.EDUCATIONDIRECTOR) { // 除教育局长外
				self.$a.market({
					onSelect : function(obj) {
						self.query.area(obj);
					}
				});
			} else {
				self.$sbox.find('.j-area-label').hide();
			}
			
			// init school select
			self.$sch = self.$sbox.find('.j-school-select');
			self.$sch.autocomplete({
				url: Leke.ctx + '/auth/common/data/querySchoolLike.htm',
				nameKey: 'schoolName',
				onChange: function(school) {
					self.query.school(school);
				}
			});
		}
		self.load();
	};
	
	function _ratio(act, exp) {
		return exp == 0 ? '--' : (act / exp * 100).toFixed(2) + '%';
	}
	
	function _int(item, field) {
		return item[field] = parseInt(item[field], 10) || 0;
	}
	
	TableVM.prototype.load =function() {
		var self = this;
		var query = self.query.toJS();
		if(query.startDay == ''){
			utils.Notice.alert('查询开始日期不能为空');
			return false;
		}
		if(query.endDay == ''){
			utils.Notice.alert('查询结束日期不能为空');
			return false;
		}
		if(parseInt(query.startDay, 10)>parseInt(query.endDay, 10)){
			utils.Notice.alert('查询开始日期不能大于结束日期');
			return false;
		}
		http.postJSON(ctx+self.options.postUrl, query).then(function(datas){
			var expectCountsTotal = 0;
			var actualCountsTotal = 0;
			var expectTimesTotal = 0;
			var actualTimesTotal = 0;
			
			var stats = _.sortBy(datas.stats || [], 'day');
			_.each(stats, function(n) {
				var dayStr = n.day.toString();
				n.dayStr = dayStr.substr(4,2) + '.' + dayStr.substr(6,2);//格式化日期
				
				expectCountsTotal += _int(n, 'expectStuCount');
				actualCountsTotal += _int(n, 'actualStuCount');
				expectTimesTotal += _int(n, 'expectStuTimes');
				actualTimesTotal += _int(n, 'actualStuTimes');
				n.ratioTimes = _ratio(n.actualStuTimes, n.expectStuTimes);
			});
			
			var points = self.toChartPoints(stats);
			
			self.expectCountsTotal(expectCountsTotal);
			self.actualCountsTotal(actualCountsTotal);
			self.expectTimesTotal(expectTimesTotal);
			self.actualTimesTotal(actualTimesTotal);
			self.ratioTimesTotal(_ratio(actualTimesTotal, expectTimesTotal));
			self.showChart(points);
			self.list(stats);
		}, function() {
			utils.Notice.alert('数据获取失败');
		});
	};
	
	var TS_DAY = 86400000;
	TableVM.prototype.toChartPoints=function(stats){
		var self=this;
		var startDay = LekeDate.of(self.query.startDay()).truncate('d').getTime();
		var endDay = LekeDate.of(self.query.endDay()).truncate('d').getTime();
		var maps = _.indexBy(stats || [],'day');
		var points = [];
		for(var day = startDay; day <= endDay; day += TS_DAY) {
			var myDay = LekeDate.of(day);
			var fmDay = myDay.format('yyyyMMdd');
			var dayStr = myDay.format('MM.dd');
			points.push(maps[fmDay] || {
				day:fmDay,
				dayStr:dayStr,
				expectStuCount:0,
				actualStuCount:0,
				expectStuTimes:0,
				actualStuTimes:0
			});
		}
		return points;
	};
	
	TableVM.prototype.showChart=function(points){
		var xArr=_.map(points,function(n){
			return n.dayStr;
		});
		var yExpectCounts=_.map(points,function(n){
			return n.expectStuCount;
		});
		var yActualCounts=_.map(points,function(n){
			return n.actualStuCount;
		});
		var myEchart = Echart.init($('#totalMap')[0]).setOption({
			title:{
		        text: "上课人数统计",
		        x:'center'
		    },
		    legend:{
		    	x:'right',
		    	data:['应到人数','实际人数']
		    },
		    tooltip : {
				trigger : 'axis'
			},
			toolbox : {
				show : true
			},
		    xAxis : [{
		    	type : 'category',
		    	name:'时间',
		    	data : xArr
		    }],
		    yAxis : [{
				type : 'value',
				name:'人数'
			}],
		    series : [
		              {name:'应到人数', type:'line', data:yExpectCounts},
		              {name:'实际人数', type:'line', data:yActualCounts}
		    ]
		});
	};
	TableVM.prototype.exportExcel=function(){
		var self=this;
		var query = self.query.toJS();
		 var data=_.isString(query) ? query : JSON.stringify(query || {})
		window.location.href = ctx+self.options.exportUrl+'?dataJson='+data;
	};
	ko.components.register('class-people-total-echart', {
	    template: require('./class-people-total-echart.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new TableVM(params, componentInfo.element);
	    	}
	    }
	});
});