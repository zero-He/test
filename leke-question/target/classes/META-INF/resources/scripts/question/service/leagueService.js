define(function(require, exports, module) {
	var http = require('common/base/http');
	var dir = window.ctx + '/auth/league/question';
	var teacherDir = window.ctx + '/auth/teacher/question';
	var Service = {
			findLeagueMembers: function(params) {
				return http.post(teacherDir + '/findLeagueMembers.htm', params);
			},
			findLeagues: function(params) {
				return http.post(teacherDir + '/findLeagues.htm', params);
			},
			findLeagueQuestions: function(params) {
				return http.post(teacherDir + '/findLeagueQuestions.htm', params);
			},
			setLeagueMultiShare: function(params) {
				return http.postJSON(teacherDir + '/setLeagueMultiShare.htm', params);
			},
			setLeagueReShare: function(params) {
				return http.postJSON(teacherDir + '/setLeagueReShare.htm', params);
			},
			deleteLeagueQuestion: function(params) {
				return http.post(teacherDir + '/deleteLeagueQuestion.htm', params);
			},
			deleteLeagueQuestions: function(params) {
				return http.postJSON(teacherDir + '/deleteLeagueQuestions.htm', params);
			},
			leagueCheckDetail: function(params) {
				return http.post(dir + '/leagueCheckDetail.htm', params);
			},
			disable: function(params) {
				return http.post(dir + '/disable.htm', params);
			},
			checkLeagueQuestion: function(params) {
				return http.post(dir + '/checkLeagueQuestion.htm', params);
			},
			checkLeagueQuestions: function(params) {
				return http.post(dir + '/checkLeagueQuestions.htm', params);
			}
			
	};
	
	module.exports = Service;
});