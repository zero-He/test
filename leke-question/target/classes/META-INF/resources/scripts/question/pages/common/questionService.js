define(function(require, exports, module) {
	var http = require('common/base/http');
	var DM = Leke.domain;
	var QUE_DM = DM.questionServerName;
	var P_PATH = QUE_DM + '/auth/common/userQue/';
	var QuestionService = {
			addBatchQuestionUserGroup: function(userResGroupId,questionIds){
				return http.post(P_PATH + 'addBatchQuestionUserGroup.htm', {userResGroupId: userResGroupId,questionIds: questionIds});
			},
			moveBatchQuestionUserGroup: function(dataJson, questionIds){
				return http.post(P_PATH + 'moveBatchQuestionUserGroup.htm',{dataJson: dataJson,questionIds: questionIds});
			},
			delBatchQuestionUserResGroup: function(userResGroupId,questionIds){
				return http.post(P_PATH + 'delBatchQuestionUserResGroup.htm',{userResGroupId: userResGroupId,questionIds: questionIds});
			},
			delPURUserGroup: function(userResGroupId){
				return http.post(P_PATH + 'delUserGroup.htm',{userResGroupId: userResGroupId});
			}
	};
	module.exports = QuestionService;
});