define(function(require, exports, module) {
	var ko = require("knockout");
	var uResGroupService = require('resgroup/service/uResGroupService');
	var questionService = require('question/pages/common/questionService');
	var I18n = require('i18n');
	require('common/knockout/bindings/i18n');
	require('resgroup/component/left-user-res-group');
	require('resgroup/component/top-user-res-group');
	require('question/component/question/right-resource-list');
	require('question/component/question/feedback-list');
	I18n.init('queJS');
	function UserResGroupVM(){
		var self = this;
		self.resType = uResGroupService.resType.QUESTION;
		self.shareScope = ko.observable(uResGroupService.shareScopes.PERSONAL);
		self.feedbackScope = ko.observable(0);
		self.userResGroupId = ko.observable();
	};
	
	UserResGroupVM.prototype.delUserResGroup = function(userResGroupId){
		return questionService.delPURUserGroup(userResGroupId);
	};
	ko.applyBindings(new UserResGroupVM());
});