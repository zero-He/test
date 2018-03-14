define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	require('./carousel');
	var store = require('./stat-store');
	
	var COLUMNS = [{ id: 'schoolName', name: '学校', width: '20%' },
	               { id: 'registered', name: '累计注册用户数', width: '16%' },
	               { id: 'platform', name: '在线用户数', width: '16%' },
	               { id: 'lesson', name: '实时课堂人数', width: '16%' },
	               { id: 'web', name: '网站在线人数', width: '16%' },
	               { id: 'device', name: '设备在线人数', width: '16%' }];
	
	function DetailsVM(params) {
		var self = this;
		var pid = self.provinceId = params.provinceId;
		var cid = self.cityId = params.cityId;
		self.stat = store.city(cid);
		self.columns = COLUMNS;
		self.stat.init();
	}
	
	DetailsVM.prototype.dispose = function() {
		var self = this;
		self.stat.stop();
	};
	
	module.exports = {
	    template: require('./city-details.html'),
	    viewModel: DetailsVM
	};
});