define(function(require, exports, module) {
	var http = require('common/base/http');
	var dir = window.ctx + '/auth/common/word';
	var Service = {
		wordImport: function(params) {
			return http.postJSON(dir + '/wordImport.htm', params);
		},
		getRecord: function(params) {
			return http.post(dir + '/getRecord.htm', params);
		},
		myRecords: function(params) {
			return http.post(dir + '/myRecords.htm', params);
		},
		delError: function(params) {
			return http.post(dir + '/delError.htm', params);
		}
	};
	
	module.exports = Service;
});