define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('common/base/helper');
	require('common/knockout/bindings/i18n');
	var BasicInfoService = require('core-business/service/basicInfoService');
	
	function EditVM(params) {
		var self = this;
		self.material = params.material;
		
		self.schoolStages = ko.observableArray();
		self.subjects = ko.observableArray();
		self.presses = ko.observableArray();
		
		self.schoolStage = self._selection('schoolStages', 'schoolStageId');
		self.subject = self._selection('subjects', 'subjectId');
		self.press = self._selection('presses', 'pressId');
		
		self.loadSchoolStages();
		self.loadPresses();
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self.loadSubjects();
		}));
		self.disposables.push(self._syncName('schoolStage', 'schoolStageName'));
		self.disposables.push(self._syncName('subject', 'subjectName'));
		self.disposables.push(self._syncName('press', 'pressName'));
	}
	
	EditVM.prototype._selection = function(optionsKey, idKey) {
		var self = this;
		return ko.pureComputed(function() {
			var id = self.material[idKey]();
			return _.find(self[optionsKey](), function(opt) {
				return opt[idKey] == id;
			});
		});
	};
	
	EditVM.prototype._syncName = function(selection, nameKey) {
		var self = this;
		return ko.computed(function() {
			var val = self[selection]() || {};
			self.material[nameKey](val[nameKey]);
		});
	};
	
	EditVM.prototype.loadSchoolStages = function() {
		var self = this;
		BasicInfoService.schoolStages().done(function(stages) {
			self.schoolStages(stages);
		}).fail(function() {
			self.schoolStages([]);
		});
	};
	
	EditVM.prototype.loadSubjects = function() {
		var self = this;
		var schoolStageId = self.material.schoolStageId();
		BasicInfoService.subjects(schoolStageId).done(function(subjects) {
			self.subjects(subjects);
		}).fail(function() {
			self.subjects([]);
		});
	};
	
	EditVM.prototype.loadPresses = function() {
		var self = this;
		BasicInfoService.presses().done(function(presses) {
			self.presses(presses);
		}).fail(function() {
			self.presses([]);
		});
	};
	
	EditVM.prototype.dispose = function() {
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('mat-edit', {
	    template: require('./mat-edit.html'),
	    viewModel: EditVM
	});
});