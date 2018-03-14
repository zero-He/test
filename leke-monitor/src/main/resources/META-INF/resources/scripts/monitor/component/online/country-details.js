define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	require('./carousel');
	require('./pie-chart');
	var store = require('./stat-store');
	var chinaMapinfo = require('./china-mapinfo');
	
	var COLUMNS = [{ id: 'name', name: '省份', width: '20%' },
	               { id: 'registered', name: '累计注册用户数', width: '16%' },
	               { id: 'platform', name: '在线用户数', width: '16%' },
	               { id: 'lesson', name: '实时课堂人数', width: '16%' },
	               { id: 'web', name: '网站在线人数', width: '16%' },
	               { id: 'device', name: '设备在线人数', width: '16%' }];
	
	function DetailsVM(params) {
		var self = this;
		self.stat = store.country();
		self.stat.init();
		self.mapinfo = chinaMapinfo;
		function url(data) {
			var prov = self.mapinfo.provinces[data.id];
			if(!prov) {
				return '#/';
			}
			var pid = prov.id;
			var cities = prov.cities;
			if(!cities || $.isEmptyObject(cities)) {
				return '#/province/' + pid + '/city/' + pid + '/details';
			} else {
				return '#/province/' + pid + '/map';
			}
		}
		self.columns = COLUMNS.map(function(col) {
			if(col.id == 'name') {
				return $.extend({}, col, { url: url });
			} else {
				return col;
			}
		});
	}
	
	DetailsVM.prototype.dispose = function() {
		var self = this;
		self.stat.stop();
	};
	
	module.exports = {
	    template: require('./country-details.html'),
	    viewModel: DetailsVM
	};
});