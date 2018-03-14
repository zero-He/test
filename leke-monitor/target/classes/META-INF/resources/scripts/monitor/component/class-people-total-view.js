define(function(require,exports,module){
	var ko = require('knockout');
	var _ = require('underscore');
	var $ = require('jquery');
	var LekeDate=require('common/base/date');
	
	function TableVM(params, element){
		var self = this;
		self.$el = $(element);
		var ops = _.extend({}, params.options);
		self.schoolId=ops.schoolId;
		self.postUrl=ops.postUrl;
		self.exportUrl=ops.exportUrl;
		self.schoolName=$('#schoolName').val();
		self.nowDate=ko.observable($('#day').val());
		self.actual=ko.observable(0);//实到合计
		self.expect=ko.observable(0);//应到合计
		self.ratioTotalStr=ko.observable();//总到课率
		self.list=ko.observableArray();//列表数据
		self.load();
	};
	TableVM.prototype.toJS = function(){
		var self=this;
		var calDate=LekeDate.of(self.nowDate());
		self.nowDate(calDate.format('yyyy-MM-dd'));
		var day=calDate.format('yyyyMMdd');
		var dataJson = {};
		if(self.schoolId != undefined){
			dataJson.startDay = day;
			dataJson.endDay = day;
			dataJson.schoolIds = [self.schoolId];
		}else{
			dataJson.startDay = day;
			dataJson.endDay = day;
		}
		return JSON.stringify(dataJson);
	};
	TableVM.prototype.load = function(){
		var self = this;
		var dataJson = self.toJS();
		$.ajax({
			type: 'post',
			url: ctx + self.postUrl,
			data: { dataJson:dataJson },
			dataType: 'json',
			success: function(resp){
				var items = resp && resp.datas && resp.datas.items || [];
				var items = _.sortBy(items, 'startTime');
				var totalActual = 0;
				var totalExpect = 0;
				_.each(items, function(n) {
					n.startTimeStr = LekeDate.format(n.startTime,"HH:mm");
					n.endTimeStr = LekeDate.format(n.endTime,"HH:mm");
					var actual = parseInt(n.actualStuCount, 10);
					totalActual += actual;
					var expect = parseInt(n.expectStuCount, 10);
					totalExpect += expect;
					n.ratioCounts = _ratio(actual, expect);
				});
				
				self.list(items);
				self.actual(totalActual);
				self.expect(totalExpect);
				self.ratioTotalStr(_ratio(totalActual, totalExpect));
			}
		});
	};
	
	function _ratio(act, exp) {
		return exp == 0 ? '--' : (act / exp * 100).toFixed(2) + '%';
	}
	
	TableVM.prototype.toPrevDay = function(){
		var self=this;
		var calDate = LekeDate.of(self.nowDate()).add(-1,'d');
		self.nowDate(calDate.format('yyyy-MM-dd'));
		self.load();
	};
	
	TableVM.prototype.toNextDay = function(){
		var self = this;
		var calDate = LekeDate.of(self.nowDate()).add(1,'d');
		self.nowDate(calDate.format('yyyy-MM-dd'));
		self.load();
	};
	TableVM.prototype.exportExcel = function(){
		var self = this;
		var dataJson = self.toJS();
		window.location.href = ctx+self.exportUrl+'?dataJson='+dataJson;
	};
	ko.components.register('class-people-total-view', {
	    template: require('./class-people-total-view.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new TableVM(params, componentInfo.element);
	    	}
	    }
	});
});