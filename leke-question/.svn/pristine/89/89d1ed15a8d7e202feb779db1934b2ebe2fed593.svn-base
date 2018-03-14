define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var utils = require('utils');
	var _ = require('common/base/helper');
	var JSON = require('json');
	var BasicInfoService = require('core-business/service/basicInfoService');

	function SetSSVM(params, element) {
		var self = this;
		self.userId = params.userId;
		self.schoolStages = ko.observableArray();
		self.loadSchoolStages();
	};
	SetSSVM.prototype.loadSchoolStages = function() {
		var self = this;
		BasicInfoService.schoolStages().done(function(stages) {
			_.each(stages, function(stage) {
				_.each(stage.subjects, function(subject) {
					subject.checked = ko.observable(false);
				});
			});
			self.schoolStages(stages);
		}).fail(function() {
			self.schoolStages([]);
		});
	};
	
	
	SetSSVM.prototype.saveSS = function() {
		var self = this;
		var jsonObj = [];
		_.each(self.schoolStages(), function(stage) {
			_.each(stage.subjects, function(subject) {
				if(subject.checked()) {
					jsonObj.push({
						id: self.userId,
						schoolStageId: stage.schoolStageId,
						subjectId: subject.subjectId
					});
				}
			});
		});
		jsonObj = JSON.stringify(jsonObj);
		$.ajax({
			type : 'post',
			url : ctx + '/auth/admin/user/ajax/addSchoolStageSubject.htm',
			data : {
				jsonStr: jsonObj
			},
			dataType : 'json',
			success : function(json) {
				var msg = json && json.message;
				if(json.success) {
					utils.Notice.alert('设置成功！');
				} else {
					utils.Notice.alert(msg || '修改失败');
				}
				window.location.href = ctx + '/auth/admin/user/setResearcherRange.htm';
			}
		});
	};
	
	ko.components.register('que-set-schoolStage-subject', {
	    template: require('./que-set-schoolStage-subject.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new SetSSVM(params, componentInfo.element);
	    	}
	    }
	});
});