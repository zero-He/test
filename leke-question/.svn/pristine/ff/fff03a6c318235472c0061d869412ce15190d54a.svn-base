define(function(require, exports, module) {
	var http = require('common/base/http');
	var DM = Leke.domain;
	var QUE_DM = DM.questionServerName;
	var P_PATH = QUE_DM + '/auth/common/workbook/';
	var WorkbookService = {
			addBatchWorkbookUserGroup: function(userResGroupId,workbookIds){
				return http.post(P_PATH + 'addBatchWorkbookUserGroup.htm', {userResGroupId: userResGroupId,workbookIds: workbookIds});
			},
			moveBatchWorkbookUserGroup: function(dataJson, workbookIds){
				return http.post(P_PATH + 'moveBatchWorkbookUserGroup.htm',{dataJson: dataJson,workbookIds: workbookIds});
			},
			delBatchWorkbookUserResGroup: function(userResGroupId,workbookIds){
				return http.post(P_PATH + 'delBatchWorkbookUserResGroup.htm',{userResGroupId: userResGroupId,workbookIds: workbookIds});
			},
			delPURUserGroup: function(userResGroupId){
				return http.post(P_PATH + 'delUserGroup.htm',{userResGroupId: userResGroupId});
			}
	};
	module.exports = WorkbookService;
});