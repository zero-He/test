define(function(require, exports, module) {
	var http = require('common/base/http');
	var dir = window.ctx + '/auth/admin/knowledge';
	var Service = {
		getRoots: function(params) {
			return http.post(dir + '/knowledgeRoots.htm', params);
		},
		addRoot: function(params) {
			return http.post(dir + '/addRootKnowledge.htm', params);
		},
		add: function(params) {
			return http.post(dir + '/addKnowledge.htm', params);
		},
		modify: function(params) {
			return http.post(dir + '/modifyKnowledge.htm', params);
		},
		del: function(params) {
			return http.post(dir + '/deleteKnowledge.htm', params);
		},
		moveUp: function(params) {
			return http.post(dir + '/moveUpKnowledge.htm', params);
		},
		moveDown: function(params) {
			return http.post(dir + '/moveDownKnowledge.htm', params);
		}
	};
	
	module.exports = Service;
});