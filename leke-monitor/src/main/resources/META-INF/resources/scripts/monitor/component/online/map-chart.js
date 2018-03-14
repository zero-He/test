define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var echarts = window.echarts;
	
	var OPTION = {
		tooltip: {
			trigger: 'item'
		},
		visualMap: {
			type: 'piecewise',
			textStyle: {
				color: 'white'
			},
			pieces: [
				{gt: 5000, color: '#FF4400'},
				{gt: 3000, lte: 5000, color: '#FF7200'},
				{gt: 2000, lte: 3000, color: '#FFAF00'},
				{gt: 1500, lte: 2000, color: '#F7FA12'},
				{gt: 1000, lte: 1500, color: '#C8F246'},
				{gt: 500, lte: 1000, color: '#85EA49'},
				{gt: 0, lte: 500, color: '#70E1FC'},
				{value: 0, color: '#BABABA'}
			]
		}
	};
	
	function ChartVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.mapinfo = params.mapinfo;
		self.details = params.details;
		self.onClick = params.onClick;
		
		self.$m = self.$el.find('.map');
		self._m = null;
		self._d = null;
		
		self._init();
	}
	
	ChartVM.prototype._init = function() {
		var self = this, mapinfo = self.mapinfo;
		function mapSeries(data) {
			return {
				series: [{
					name: '在线用户数',
					type: 'map',
					mapType: mapinfo.map == 'china' ? 'china' : mapinfo.name,
					roam: false,
					label: {
						normal: {
							show: mapinfo.map != 'hainan',
							textStyle: {
								color: 'black'
							}
						},
						emphasis: {
							textStyle: {
								color: 'white'
							}
						}
					},
					itemStyle: {
						normal: {
							areaColor: '#BABABA'
						},
                        emphasis: {
                            areaColor: '#FDDD30'
                        }
                    },
					data : data
				}]
			};
		}
		require.async(['common/ui/ui-echarts/maps/' + mapinfo.map], function() {
			self._m = echarts.init(self.$m.get(0));
			self._m.setOption($.extend({
			}, OPTION, mapSeries([])));
			self._m.on('click', function(params) {
				var data = params.data;
				if(data && data.id) {
					self.onClick(data);
				}
			});
			
			if(self._d) {
				self._d.dispose();
				self._d = null;
			}
			self._d = ko.computed(function() {
				var details = self.details();
				var data = details.map(function(d) {
					return { id: d.id, name: d.name, value: d.platform };
				});
				self._m.setOption(mapSeries(data));
			});
		});
	};
	
	ChartVM.prototype.dispose = function() {
		var self = this;
		if(self._d) {
			self._d.dispose();
			self._d = null;
		}
		if(self._m) {
			self._m.dispose();
			self._m = null;
		}
	};
	
	ko.components.register('map-chart', {
		template: '<div class="map"></div>',
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ChartVM(params, componentInfo.element);
	    	}
	    }
	});
});