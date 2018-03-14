define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var chinaMapinfo = require('./china-mapinfo');
	var store = require('./stat-store');
	var hasher = require('hasher');
	var utils = require('utils');
	require('./pie-chart');
	require('./map-chart')
	
	function MapVM(params) {
		var self = this;
		self.stat = store.country();
		self.stat.init();
		self.mapinfo = chinaMapinfo;
	}
	
	MapVM.prototype.onClickProv = function(data) {
		var self = this;
		var prov = self.mapinfo.provinces[data.id];
		if(!prov) {
			utils.Notice.alert('无法找到省份信息');
			return false;
		}
		var pid = prov.id;
		var cities = prov.cities;
		if(!cities || $.isEmptyObject(cities)) {
			hasher.setHash('province/' + pid + '/city/' + pid + '/details');
		} else {
			hasher.setHash('province/' + pid + '/map');
		}
	};
	
	MapVM.prototype.dispose = function() {
		var self = this;
		self.stat.stop();
	};
	
	module.exports = {
	    template: require('./country-map.html'),
	    viewModel: MapVM
	};
});