define(function(require, exports, module) {
	var ko = require('knockout');
	var I18n = require('i18n');
	require('common/knockout/bindings/i18n');
	I18n.init('homework');
	var utils = require('utils');
	var dialog = require('dialog');
	var $ = require('jquery');
	//var CST = require('paper/service/cst');
	var RepoQS = require('repository/service/RepoQueryString');
	require('repository/head/scope-title');
	
	function BaseVM() {
		var self = this;
		self.method = ko.observable('answersheet');
		var methods = self.paperGenMethods();
		var all = RepoQS.all;
		self.extrance = all.extrance;
		
		methods.forEach(function(m) {
			m.css = ko.pureComputed(function() {
				var r = m.method;
				if(m.method == self.method()) {
					r += ' clickcontent';
				}
				return r;
			});
		});
		self.methods = methods;
	}
	
	window.fromSubmit = function(exerciseType, exerciseName, relId,
			schoolStageId, schoolStageName, subjectId, subjectName,level) {
		var data = {};
		data.exerciseName = exerciseName;
		data.exerciseType = exerciseType;
		data.relId = relId;
		data.schoolStageId = schoolStageId;
		data.schoolStageName = schoolStageName;
		data.subjectId = subjectId;
		data.subjectName = subjectName;
		data.difficultyLevel = level;
		$.post('/auth/student/exercise/generate.htm',data,function(json){
			if(json.success){
				location.href= '/auth/student/exercise/doExerciseWork.htm?id='+json.datas.exerciseId;
			}else {
				utils.Notice.alert(json.message);
			}
		});
	}
	
	BaseVM.prototype.study = function(m){
		window.location.href = Leke.domain.learnServerName + "/auth/student/topic/pc/subjectStudy.htm";
		//var url = Leke.ctx + '/auth/student/topic/pad/subjectStudy.htm';
		//window.location = url;//到时候要路径qw
	}
	
	BaseVM.prototype.exercise = function(m){
		dialog.open({
			'title' : '开始练习',
			'url' : '/auth/student/exercise/page/chapterTree.htm',
			'size' : 'lg'
		});
		
	}
	
	BaseVM.prototype.paperGenMethods = function() {
		/*return _.map(['answersheet', 'automatic', 'manual'], function(key) {
			return {
				method: key,
				title: key,
				desc: key
			};
		});*/
		
		return _.map(['answersheet', 'automatic'], function(key) {
			if (key == 'answersheet'){
				return {
					method: key,
					title: key,
					desc: "学习模式"
				};
			}else{
				return {
					method: key,
					title: key,
					desc: "练习模式"
				};
			}
			
		});
	};
	
	BaseVM.prototype.toNext = function() {
		var self = this;
		var m = self.method();
		if(!m) {
			//utils.Notice.alert($.i18n.prop('pap.paper.alert.missing.method'));
			return false;
		}
		var url = Leke.ctx + '/auth/common/paper/add/' + m + '.htm?' + RepoQS.stringify(RepoQS.scope);
		window.location = url;
	};
	
	BaseVM.prototype.goBack = function() {
		if(history.length > 1) {
			history.go(-1);
		} else {
			window.close();
		}
	};
	
	ko.applyBindings(new BaseVM());
});