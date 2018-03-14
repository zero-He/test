define(function(require, exports, module) {
	var http = require('common/base/http');
	var dir = window.ctx + '/auth/common/base';
	
	var Service = {
		similarQueTypes: function(params) {
			return http.post(dir + '/similarQueTypes.htm', params);
		}
	};
	
	module.exports = Service;
});