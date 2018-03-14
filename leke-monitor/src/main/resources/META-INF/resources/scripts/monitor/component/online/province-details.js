define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var store = require('./stat-store');
	require('./carousel');
	require('./pie-chart');
	
	var COLUMNS = [{ id: 'name', name: '地级市', width: '20%' },
	               { id: 'registered', name: '累计注册用户数', width: '16%' },
	               { id: 'platform', name: '在线用户数', width: '16%' },
	               { id: 'lesson', name: '实时课堂人数', width: '16%' },
	               { id: 'web', name: '网站在线人数', width: '16%' },
	               { id: 'device', name: '设备在线人数', width: '16%' }];
	
	function DetailsVM(params) {
		var self = this;
		var pid = self.provinceId = params.provinceId;
		self.stat = store.province(pid);
		self.stat.init();
		function url(data) {
			return '#/province/' + pid + '/city/' + data.id + '/details';
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
	    template: require('./province-details.html'),
	    viewModel: DetailsVM
	};
});