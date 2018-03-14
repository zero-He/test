define(function(require, exports, module) {
	var http = require('common/base/http');
	var dir = window.ctx + '/auth/checker/question';
	var Service = {
		importCheck: function(params) {
			return http.postJSON(dir + '/importCheck.htm', params);
		},
		importReject: function(params) {
			return http.post(dir + '/importReject.htm', params);
		}
	};
	
	module.exports = Service;
});