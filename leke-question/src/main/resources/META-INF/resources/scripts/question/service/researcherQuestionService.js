define(function(require, exports, module) {
	var http = require('common/base/http');
	var dir = window.ctx + '/auth/researcher/question';
	var Service = {
		batchCheckPass: function(params) {
			return http.postJSON(dir + '/batchCheckPass.htm', params);
		},
		batchCheckPassTeacherShare: function(params) {
			return http.postJSON(dir + '/batchCheckPassTeacherShare.htm', params);
		},
		checkPassTeacherShare: function(params) {
			return http.post(dir + '/checkPassTeacherShare.htm', params);
		},
		rejectTeacherShare: function(params) {
			return http.post(dir + '/rejectTeacherShare.htm', params);
		}
	};
	
	module.exports = Service;
});