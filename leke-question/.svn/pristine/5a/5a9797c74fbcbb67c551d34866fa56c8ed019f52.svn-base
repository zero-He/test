define(function(require, exports, module) {
	var http = require('common/base/http');
	var dir = window.ctx + '/auth/researcher/question/duplication';
	var Service = {
		notDup: function(params) {
			return http.post(dir + '/notDup.htm', params);
		},
		del: function(params) {
			return http.post(dir + '/del.htm', params);
		},
		disable: function(params) {
			return http.post(dir + '/disable.htm', params);
		},
		disableOthers: function(params) {
			return http.post(dir + '/disableOthers.htm', params);
		}
	};
	
	module.exports = Service;
});