define(function(require, exports, module) {
	var http = require('common/base/http');
	var $ = require('jquery');
	
	function NameMap(stages) {
		var self = this;
		self.stg = {};
		self.gra = {};
		self.sub = {};
		
		$.each(stages || [], function(i, stage) {
			self.putStage(stage);
		});
	}
	
	NameMap.prototype.putStage = function(stage) {
		if(!stage) {
			return;
		}
		var self = this;
		self.stg[stage.schoolStageId] = stage.schoolStageName;
		$.each(stage.grades, function(i, grade) {
			self.gra[grade.gradeId] = grade.gradeName;
		});
		$.each(stage.subjects, function(i, subject) {
			self.sub[subject.subjectId] = subject.subjectName;
		});
	};
	
	NameMap.prototype.fix = function(datas) {
		var self = this;
		$.each(datas || [], function(i, data) {
			var stgId = data.schoolStageId;
			if(stgId) {
				data.schoolStageName = self.stg[stgId];
			}
			var graId = data.gradeId;
			if(graId) {
				data.gradeName = self.gra[graId];
			}
			var subId = data.subjectId;
			if(subId) {
				data.subjectName = self.sub[subId];
			}
		});
		return datas;
	};
	
	var p_nm = http.cacheGet(Leke.ctx + '/auth/common/base/schoolStages.htm').then(function(datas) {
		return new NameMap(datas.schoolStages);
	});
	var Service = {};
	Service.inputStatis = function(datas) {
		return p_nm.then(function(nm) {
			return nm.fix(datas);
		});
	};
	
	module.exports = Service;
});