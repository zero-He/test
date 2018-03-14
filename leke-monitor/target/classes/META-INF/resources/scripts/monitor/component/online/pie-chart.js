define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var echarts = window.echarts;
	
	function ChartVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.details = params.details;
		
		self.$p = self.$el.find('.percent');
		self._p = null;
		self._d = null;
		
		self._init();
	}
	
	ChartVM.prototype._init = function() {
		var self = this;
		function pieSeries(data) {
			return {
				series: [{
					name: '注册用户数',
					type: 'pie',
					radius: ['40%', '60%'],
					data : data
				}]
			};
		}
		self._p = echarts.init(self.$p.get(0));
		self._p.setOption($.extend({
			tooltip: {
				trigger: 'item',
				formatter: "{a} <br/>{b}: {c} ({d}%)"
			}
		}, pieSeries([])));
		
		if(self._d) {
			self._d.dispose();
			self._d = null;
		}
		self._d = ko.computed(function() {
			var details = self.details();
			var data = details.map(function(d) {
				return { id: d.id, name: d.name, value: d.registered };
			}).filter(function(data) { return data.value > 0; });
			self._p.setOption(pieSeries(data));
		});
	};
	
	ChartVM.prototype.dispose = function() {
		var self = this;
		if(self._d) {
			self._d.dispose();
			self._d = null;
		}
		if(self._p) {
			self._p.dispose();
			self._p = null;
		}
	};
	
	ko.components.register('pie-chart', {
	    template: '<div class="c-box"><h2 class="title">注册用户数占比展示</h2><div class="percent"></div></div>',
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ChartVM(params, componentInfo.element);
	    	}
	    }
	});
});