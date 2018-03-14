define(function(require, exports, module) {
	var $ = require('jquery');
	var ko = require('knockout');
	var uResGroupService = require('resgroup/service/uResGroupService');
	var workbookService = require('question/pages/common/workbookService');
	require('resgroup/component/left-user-res-group');
	require('resgroup/component/top-user-res-group');
	require('question/component/workbook/right-resource-list');
	
	function UserResGroupVM(){
		var self = this;
		self.resType = uResGroupService.resType.WORKBOOK;
		self.shareScope = ko.observable(uResGroupService.shareScopes.FAVORITE);
		self.userResGroupId = ko.observable();
		self.feedbackScope = ko.observable(0);
	};
	
	UserResGroupVM.prototype.delUserResGroup = function(userResGroupId){
		return workbookService.delPURUserGroup(userResGroupId);
	};
	ko.applyBindings(new UserResGroupVM());
});