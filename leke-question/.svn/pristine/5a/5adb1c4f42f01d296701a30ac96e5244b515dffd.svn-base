define(function(require, exports, module) {
	var http = require('common/base/http');
	var dir = window.ctx + '/auth/admin/material';
	var Service = {
		getRoots: function(params) {
			return http.post(dir + '/materials.htm', params);
		},
		addRoot: function(params) {
			return http.post(dir + '/addMaterial.htm', params);
		},
		modifyRoot: function(params) {
			return http.post(dir + '/modifyMaterial.htm', params);
		},
		delRoot: function(params) {
			return http.post(dir + '/deleteMaterial.htm', params);
		},
		addSection: function(params) {
			return http.post(dir + '/addSection.htm', params);
		},
		modifySection: function(params) {
			return http.post(dir + '/modifySection.htm', params);
		},
		delSection: function(params) {
			return http.post(dir + '/deleteSection.htm', params);
		},
		knowledges: function(params) {
			return http.post(dir + '/knowledges.htm', params);
		},
		addKnowledges: function(params) {
			return http.postJSON(dir + '/addKnowledges.htm', params);
		},
		delKnowledge: function(params) {
			return http.post(dir + '/deleteKnowledge.htm', params);
		},
		rebuildTreeIndex: function(params) {
			return http.post(dir + '/rebuildTreeIndex.htm', params);
		},
		moveUp: function(params) {
			return http.post(dir + '/moveUp.htm', params);
		},
		moveDown: function(params) {
			return http.post(dir + '/moveDown.htm', params);
		}
	};
	
	module.exports = Service;
});