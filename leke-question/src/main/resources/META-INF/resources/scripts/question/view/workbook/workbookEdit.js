define(function(require,exports,module){
	var ko = require('knockout');
	var $ = require('jquery');
	var http = require('common/base/http');
	var dialog = require('dialog');
	var utils = require('utils');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var I18n = require('i18n');
	var validate =require('validate');
	require('common/knockout/bindings/i18n');

	function Model(){
		var self = this;
		self.workbookId = ko.observable($('#workbookId').val());
		self.workbookName = ko.observable($('#oldWorkbookName').val());
		self.schoolStageId = ko.observable($('#oldSchoolStageId').val());
		self.subjectId = ko.observable($('#oldSubjectId').val());
		self.pressId = ko.observable($('#oldPressId').val());
		self.materialId = ko.observable($('#oldMaterialId').val());
	};
	
	Model.prototype.toJS = function(){
		var self = this;
		var result = {};
		result.workbookId = self.workbookId();
		result.workbookName = self.workbookName();
		result.schoolStageId = self.schoolStageId();
		result.subjectId = self.subjectId();
		result.pressId = self.pressId();
		result.materialId = self.materialId();
		return result;
	};
	
	function Workbook(){
		var self = this;
		self.model = new Model();
		self.schoolStages = ko.observableArray();
		self.subjects = ko.observableArray();
		self.presses = ko.observableArray();
		self.materials = ko.observableArray();
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self.loadSubjects();
			self.loadPresses();
			self.loadMaterials();
		}));
		self.loadSchoolStages();
	};
	
	Workbook.prototype.doSave = function(){
		var self = this;
		var result = self.model.toJS();
		if(!result.workbookName){
			utils.Notice.alert('习题册不能为空！');
			return false;
		}
		if(!result.schoolStageId){
			utils.Notice.alert('学段不能为空！');
			return false;
		}
		if(!result.subjectId){
			utils.Notice.alert('科目不能为空！');
			return false;
		}
		if(!result.pressId && Leke.user.currentRoleId == 402){
			utils.Notice.alert('教材版本不能为空！');
			return false;
		}
		if(!result.materialId && Leke.user.currentRoleId == 402){
			utils.Notice.alert('年级或课本不能为空！');
			return false;
		}
		
		$.ajax({
			type: 'post',
			url: Leke.ctx + '/auth/common/workbook/doWorkbookEdit.htm',
			dataType: 'json',
			data: result,
			success: function(datas){
				if(datas.success){
					dialog.close('workbookEdit',true);
					utils.Notice.alert('保存成功');
				}
			}
		});
	};
	
	Workbook.prototype.doClose = function(){
		dialog.close('workbookEdit', true);
	};
	
	Workbook.prototype.loadSchoolStages = function() {
		var self = this;
		BasicInfoService.schoolStages().done(function(stages) {
			self.schoolStages(stages);
		}).fail(function() {
			self.schoolStages([]);
		});
	};
	
	Workbook.prototype.loadSubjects = function() {
		var self = this;
		var schoolStageId = self.model.schoolStageId();
		BasicInfoService.subjects(schoolStageId).done(function(subjects) {
			self.subjects(subjects);
		}).fail(function() {
			self.subjects([]);
		});
	};
	
	Workbook.prototype.loadPresses = function() {
		var self = this;
		var model = self.model;
		var schoolStageId = model.schoolStageId();
		var subjectId = model.subjectId()
		BasicInfoService.presses(schoolStageId,subjectId).done(function(presses) {
			self.presses(presses);
		}).fail(function() {
			self.presses([]);
		});
	};
	
	Workbook.prototype.loadMaterials = function(){
		var self = this;
		var model = self.model;
		var schoolStageId = model.schoolStageId();
		var subjectId = model.subjectId()
		var pressId = model.pressId();
		BasicInfoService.materials(schoolStageId,subjectId,pressId).done(function(materials) {
			self.materials(materials);
		}).fail(function() {
			self.materials([]);
		});
	};
	
	Workbook.prototype.dispose = function() {
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	I18n.init('queJS');
	ko.applyBindings(new Workbook());
});