define(function(require, exports, module) {
	var http = require('common/base/http');
	var dir = window.ctx + '/auth/admin/material';
	var dir2 = window.ctx + '/auth/researcher/syllabus/outline';
	var dir3 = window.ctx + '/auth/schoolResearcher/syllabus/outline';
	var Service = {
		getRoots: function(params) {
			return http.post(dir3 + '/outlines.htm', params);
		},
		addRoot: function(params) {
			return http.post(dir3 + '/addOutline.htm', params);
		},
		modifyRoot: function(params) {
			return http.post(dir3 + '/modifyOutline.htm', params);
		},
		delRoot: function(params) {
			return http.post(dir3 + '/deleteOutline.htm', params);
		},
		addSection: function(params) {
			return http.post(dir3 + '/addSection.htm', params);
		},
		modifySection: function(params) {
			return http.post(dir3 + '/modifySection.htm', params);
		},
		delSection: function(params) {
			return http.post(dir3 + '/deleteSection.htm', params);
		},
		moveUp: function(params) {
			return http.post(dir3 + '/moveUp.htm', params);
		},
		moveDown: function(params) {
			return http.post(dir3 + '/moveDown.htm', params);
		},
		queryChildNodes: function(params) {
			return http.post(dir3 + '/queryOutlineNodes.htm', params);
		},
		querySchoolStageIdByGradeId: function(params) {
			return http.post(dir3 + '/querySchoolStageIdByGradeId.htm', params);
		},
		querySchoolStages: function(params) {
			return http.post(dir3 + '/querySchoolStages.htm', params);
		},
		outlineUp: function(params) {
			return http.post(dir3 + '/outlineUp.htm', params);
		},
		outlineDown: function(params) {
			return http.post(dir3 + '/outlineDown.htm', params);
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
		}
	};
	
	module.exports = Service;
});