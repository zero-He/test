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
	require('common/knockout/component/leke-page');
	
	function Model(){
		var self = this;
		self.schoolStageId = ko.observable($('#oldSchoolStageId').val());
		self.subjectId = ko.observable($('#oldSubjectId').val());
		self.pressId = ko.observable($('#oldPressId').val());
		self.materialId = ko.observable();
		self.materialName = ko.observable();
		self.curPage = ko.observable(1);
	};
	
	Model.prototype.toJS = function() {
		var self = this;
		var result = {};
		_.each(['schoolStageId','subjectId','pressId','materialId','materialName','curPage'], function(key) {
			var val = self[key].peek();
			if(val != null && val !== '') {
				result[key] = val;
			}
		});
		return result;
	};
	
	function Material(){
		var self = this;
		self.model = new Model();
		self.totalSize = ko.observable(0);
		self.materials = ko.observableArray();
		self.schoolStages = ko.observableArray();
		self.subjects = ko.observableArray();
		self.presses = ko.observableArray();
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self.loadSubjects();
		}));
		self.loadSchoolStages();
		self.loadPresses();
		self.disposables.push(ko.computed(function() {
			self.model.pressId();
			self.load();
		}));
		self.disposables.push(ko.computed(function() {
			//点击分页
			var curPage = self.model.curPage();
			self.load();
		}));
	};
	Material.prototype.loadSchoolStages = function() {
		var self = this;
		BasicInfoService.schoolStages().done(function(stages) {
			self.schoolStages(stages);
		}).fail(function() {
			self.schoolStages([]);
		});
	};
	
	Material.prototype.loadSubjects = function() {
		var self = this;
		var schoolStageId = self.model.schoolStageId();
		BasicInfoService.subjects(schoolStageId).done(function(subjects) {
			self.subjects(subjects);
		}).fail(function() {
			self.subjects([]);
		});
	};
	
	Material.prototype.loadPresses = function() {
		var self = this;
		BasicInfoService.presses().done(function(presses) {
			self.presses(presses);
		}).fail(function() {
			self.presses([]);
		});
	};
	Material.prototype.load = function(){
		var self = this;
		var query = self.model.toJS();
		http.post(Leke.ctx + '/auth/common/workbook/findMaterials.htm',query).done(function(datas){
			var page = datas.page||{};
			self.materials(datas.materials || []);
			self.model.curPage(page.curPage || 1);
			self.totalSize(page.totalSize || 0);
		}).fail(function(){
			self.materials([]);
			self.totalSize(0);
		});
	};
	Material.prototype.doSearch =function(){
		var self = this;
		self.model.curPage(1);
		self.load();
	};
	Material.prototype.doMaterial = function(data){
		var self = this;
		self.model.materialId(data.materialId);
		self.model.materialName(data.materialName);
		return true;
	};
	Material.prototype.doSave = function(){
		var self = this;
		var m = self.model.toJS();
		if(!m.materialId || !m.materialName){
			utils.Notice.alert('请选择教材');
			return false;
		}
		dialog.close(null, m);
	};
	
	Material.prototype.dispose = function() {
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	
	I18n.init('queJS');
	ko.applyBindings(new Material());
});