define(function(require, exports, module) {
	var http = require('common/base/http');
	var dir = window.ctx + '/auth/common/question';
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
		setDifficulty: function(params) {
			return http.post(dir + '/setDifficulty.htm', params);
		},
		attachKnowledges: function(params) {
			return http.postJSON(dir + '/attachKnowledges.htm', params);
		},
		detachKnowledge: function(params) {
			return http.post(dir + '/detachKnowledge.htm', params);
		},
		attachSections: function(params) {
			return http.postJSON(dir + '/attachSections.htm', params);
		},
		detachSection: function(params) {
			return http.post(dir + '/detachSection.htm', params);
		},
		attachTags: function(params) {
			return http.postJSON(dir + '/attachTags.htm', params);
		},
		detachTag: function(params) {
			return http.post(dir + '/detachTag.htm', params);
		},
		check: function(params) {
			return http.postJSON(dir + '/check.htm', params);
		},
		reject: function(params) {
			return http.post(dir + '/reject.htm', params);
		},
		correct: function(params) {
			return http.post(dir + '/correct.htm', params);
		},
		verify: function(params) {
			return http.postJSON(dir + '/verify.htm', params);
		},
		setQuestionType: function(params) {
			return http.post(dir + '/setQuestionType.htm', params);
		},
		review: function(params) {
			return http.postJSON(dir + '/review.htm', params);
		},
		verifyEdit: function(params) {
			return http.postJSON(dir + '/verifyEdit.htm', params);
		},
		disable: function(params) {
			return http.post(dir + '/disable.htm', params);
		},
		editChecked: function(params) {
			return http.postJSON(dir + '/editChecked.htm', params);
		},
		addChecked: function(params) {
			return http.postJSON(dir + '/addChecked.htm', params);
		},
		editImported: function(params) {
			return http.postJSON(dir + '/editImported.htm', params);
		},
		rejectEdit: function(params) {
			return http.postJSON(dir + '/rejectEdit.htm', params);
		}
	};
	
	module.exports = Service;
});