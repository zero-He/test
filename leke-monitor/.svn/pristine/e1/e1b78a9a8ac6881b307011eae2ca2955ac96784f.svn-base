define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('underscore');
	var $ = require('jquery');
	var http = require('common/base/http');
	var LekeDate=require('common/base/date');
	var utils = require('utils');
	var ROLES = require('core-business/roles');
	var Echart = require('echart');
	require('common/knockout/bindings/datepicker');
	require('common/ui/ui-address/ui-market');
	require('common/ui/ui-autocomplete/ui-autocomplete');
	
	function Query(options) {
		var self = this;
		var ops = _.extend({
			type: 2
		}, options);
		['type', 'timeInput', 'area', 'school'].forEach(function(k) {
			self[k] = ko.observable(ops[k]);
		});
	}
	
	Query.prototype.toJS = function() {
		var self = this;
		var result = {};
		['type', 'timeInput'].forEach(function(k) {
			result[k] = self[k].peek();
		});
		var sch = self.school.peek();
		if(sch) {
			result.schoolIds = [sch.schoolId];
		} else {
			var area = self.area.peek();
			if(area) {
				result.area = area;
			}
		}
		return result;
	};
	
	function TableVM(params, element) {
		var self = this;
		self.$el = $(element);

		self.options = _.extend({}, params.options);
		self.query = new Query(params.query);
		self.$sbox = self.$el.find('.j-search-box');
		var roleId = self.options.roleId;
		if(roleId == ROLES.SELLER||roleId == ROLES.PRESIDENT){
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
	
	TableVM.prototype.load =function() {
		var self = this;
		var query = self.query.toJS();
		http.postJSON(ctx + self.options.postUrl, query).then(function(datas){
			var points = toChartPoints(datas.stats, query);
			self.showChart(points);
		},function(){
			utils.Notice.alert('数据获取失败');});
	};
	
	var TS_5_MIN = 300000;
	var TS_HOUR = 3600000;
	var TS_DAY = 86400000;
	
	function toChartPoints(stats, query) {
		var nowTs = new Date().getTime();
		var type = query.type;
		var start, end;
		if(type == 1) {
			end = nowTs - nowTs % TS_5_MIN;
			start = end - TS_HOUR;
		} else if(type == 2) {
			start = LekeDate.of().truncate('d').getTime();
			end = start + TS_DAY;
		} else if(type == 3) {
			start = LekeDate.of(query.timeInput).truncate('d').getTime();
			end = start + TS_DAY;
		}
		var points = [];
		var map = _.indexBy(stats || [], 'ts');
		for(var ts = start; ts <= end; ts += 300000) {
			points.push(map[ts] || {
				ts: ts,
				expectStuCount: 0,
				actualStuCount: 0
			});
		}
		return points;
	};
	
	TableVM.prototype.showChart = function(points) {
		var xArr = _.map(points, function(p) {
			return LekeDate.of(p.ts).format('HH:mm');
		});
		var yExpectArr = _.map(points, function(p) {
			return p.expectStuCount;
		});
		var yActualArr = _.map(points, function(p) {
			return p.actualStuCount;
		});
		
		var myEchart = Echart.init($('#totalMap')[0]).setOption({
			title:{
		        text: "课堂人数统计",
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
		              {name:'应到人数', type:'line', data:yExpectArr},
		              {name:'实际人数', type:'line', data:yActualArr}
		    ]
		});
	};
	
	ko.components.register('clazzroom-echart', {
	    template: require('./clazzroom-echart.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new TableVM(params, componentInfo.element);
	    	}
	    }
	});
});