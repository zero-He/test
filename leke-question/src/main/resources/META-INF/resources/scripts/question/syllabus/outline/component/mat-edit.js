define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('common/base/helper');
	require('common/knockout/bindings/i18n');
	var MaterialService = require('../service/outlineService');
	var BasicInfoService = require('core-business/service/basicInfoService');
	
	function EditVM(params) {
		var self = this;
		//self.material = params.material;
		self.outline = params.outline;
		
		self.schoolStages = ko.observableArray();
		self.subjects = ko.observableArray();
		self.grades = ko.observableArray();
		//self.presses = ko.observableArray();
		
		self.schoolStage = self._selection('schoolStages', 'schoolStageId');
		self.subject = self._selection('subjects', 'subjectId');
		self.grade = self._selection('grades', 'gradeId');
		//self.press = self._selection('presses', 'pressId');
		
		self.loadSchoolStages();
		//self.loadPresses();
		self.loadGrades()
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self.loadSubjects();
		}));
		self.disposables.push(self._syncName('schoolStage', 'schoolStageName'));
		self.disposables.push(self._syncName('subject', 'subjectName'));
		self.disposables.push(self._syncName('grade', 'gradeName'));
		//self.disposables.push(self._syncName('press', 'pressName'));
	}
	
	EditVM.prototype._selection = function(optionsKey, idKey) {
		var self = this;
		return ko.pureComputed(function() {
			var id = self.outline[idKey]();
			return _.find(self[optionsKey](), function(opt) {
				return opt[idKey] == id;
			});
		});
	};
	
	EditVM.prototype._syncName = function(selection, nameKey) {
		var self = this;
		return ko.computed(function() {
			var val = self[selection]() || {};
			self.outline[nameKey](val[nameKey]);
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
		var gradeId = self.outline.gradeId();
		var schoolStageId;
		
		MaterialService.querySchoolStageIdByGradeId({
			gradeId:  self.outline.gradeId()
		}).then(function(datas) {
			BasicInfoService.subjects(datas.outline.schoolStageId).done(function(subjects) {
						self.subjects(subjects);
			}).fail(function() {
				self.subjects([]);
			});
		}, function(msg) {
			utils.Notice.alert(msg || '查询学段失败！');
		});
	};
	
	EditVM.prototype.loadGrades = function() {
		var self = this;
		var schoolStageId = self.outline.schoolStageId();
		BasicInfoService.grades(schoolStageId).done(function(grades) {
			self.grades(grades);
		}).fail(function() {
			self.grades([]);
		});
	};
	
	/*EditVM.prototype.loadPresses = function() {
		var self = this;
		BasicInfoService.presses().done(function(presses) {
			self.presses(presses);
		}).fail(function() {
			self.presses([]);
		});
	};*/
	
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