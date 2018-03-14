define(function(require, exports, module) {
	var ko = require('knockout');
	var chinaMapinfo = require('./china-mapinfo');
	var store = require('./stat-store');
	var hasher = require('hasher');
	var utils = require('utils');
	require('./pie-chart');
	require('./map-chart');
	
	function MapVM(params) {
		var self = this;
		var pid = self.provinceId = params.provinceId;
		self.stat = store.province(pid);
		self.stat.init();
		self.mapinfo = chinaMapinfo.provinces[pid];
	}
	
	MapVM.prototype.onClickCity = function(data) {
		var self = this, mapinfo = self.mapinfo;
		if(!mapinfo) {
			return false;
		}
		var pid = mapinfo.id;
		var cid = data.id;
		var city = mapinfo.cities[cid];
		if(!city) {
			utils.Notice.alert('无法找到地级市信息');
			return false;
		}
		hasher.setHash('province/' + pid + '/city/' + cid + '/details');
	};
	
	MapVM.prototype.dispose = function() {
		var self = this;
		self.stat.stop();
	};
	
	module.exports = {
	    template: require('./province-map.html'),
	    viewModel: MapVM
	};
});