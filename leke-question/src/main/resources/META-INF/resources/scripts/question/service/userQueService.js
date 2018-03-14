define(function(require, exports, module) {
	var http = require('common/base/http');
	var dir = window.ctx + '/auth/common/userQue';
	var Service = {
		get: function(params) {
			return http.post(dir + '/get.htm', params);
		},
		add: function(params) {
			return http.postJSON(dir + '/add.htm', params);
		},
		modify: function(params) {
			return http.postJSON(dir + '/modify.htm', params);
		},
		del: function(params) {
			return http.post(dir + '/del.htm', params);
		},
		addFav: function(params) {
			return http.post(dir + '/addFav.htm', params);
		},
		delFav: function(params) {
			return http.post(dir + '/delFav.htm', params);
		},
		addSchFav: function(params) {
			return http.post(dir + '/addSchFav.htm', params);
		},
		delSchFav: function(params) {
			return http.post(dir + '/delSchFav.htm', params);
		},
		feedback: function(params) {
			return http.post(dir + '/feedback.htm', params);
		},
		praise: function(params) {
			return http.post(dir + '/praise.htm', params);
		},
		comment: function(params) {
			return http.post(dir + '/comment.htm', params);
		},
		disable: function(params) {
			return http.post(dir + '/disable.htm', params);
		},
		replaceEdit: function(params) {
			return http.postJSON(dir + '/replaceEdit.htm', params);
		}
	};
	module.exports = Service;
});