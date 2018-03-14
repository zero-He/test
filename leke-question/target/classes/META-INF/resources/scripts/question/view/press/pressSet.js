define(function(require, exports, module) {
	var $ = require('jquery');
	var ko = require('knockout');
	var http = require('common/base/http');
	var _ = require('underscore');
	var utils = require('utils');
	var dialog = require('dialog');
	var BasicInfoService = require('core-business/service/basicInfoService');
	
	function ModelVM(params){
		var self = this;
		self.schoolStageId = params.schoolStageId;
		self.subjectId = params.subjectId;
		self.pressesMap = _.indexBy(params.pressesed || [],'pressId');
		self.presses = ko.observableArray();
		self.pressId = ko.observable();
		self.loadPresses();
	};
	
	ModelVM.prototype.loadPresses = function(){
		var self = this;
		var map = self.pressesMap;
		BasicInfoService.presses().then(function(presses){
			var pressing = _.map(presses || [] ,function(n){
				n.checked = ko.observable(false);
				n.ord = ko.observable();
				n.isMain = ko.observable(false);
				var pressed = map[n.pressId];
				if(pressed){
					n.checked(true);
					n.ord(pressed.ord);
					n.isMain(pressed.isMain || false);
				}
				return n;
			});
			pressing = _.sortBy(pressing,function(n){
				var ord = n.ord();
				if(ord == undefined){
					ord = 10000;
				}
				return Math.floor(ord);
			});
			self.presses(pressing);
		},function(msg){
			utils.Notice.alerr(msg || '教材版本加载出错！');
		});
	};
	
	ModelVM.prototype.toDataJson = function(){
		var self = this;
		var result = [];
		_.each(self.presses() || [],function(n){
			if(n.checked()){
				var m = {};
				m.schoolStageId = self.schoolStageId;
				m.subjectId = self.subjectId;
				m.pressId = n.pressId;
				m.ord = n.ord.peek();
				m.isMain = n.isMain.peek();
				result.push(m);
			}
		});
		return result;
	};
	
	ModelVM.prototype.verification = function(result){
		var index = 0;
		var data = _.find(result,function(n){
			index++;
			return n.ord == undefined;
		});
		if(data){
			utils.Notice.alert('第'+ index +'行序号必填！');
			return false;
		}
		
		index = 0;
		var data2 = _.find(result,function(n){
			index++;
			return Math.floor(n.ord) != n.ord;
		});
		if(data2){
			utils.Notice.alert('第' + index + '行要为整数！');
			return false;
		}
		return true;
	};
	
	function ListVM(){
		var self = this;
		self.stageSubjectPresss = [];
		self.stages = ko.observableArray();
		
		self.schoolStages = ko.observableArray();
		self.subjects = ko.observableArray();
		self.schoolStageId = ko.observable();
		self.subjectId = ko.observable();
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self.loadSubjects();
		}));
		self.load();
	};

	
	ListVM.prototype.loadSubjects = function() {
		var self = this;
		BasicInfoService.subjects(self.schoolStageId()).done(function(subjects) {
			self.subjects(subjects);
		}).fail(function() {
			self.subjects([]);
		});
	};
	
	var SSP_DIR = Leke.ctx + '/auth/admin/press/findStageSubjectPress.htm';
	ListVM.prototype.load = function(){
		var self = this;
		var req = http.post(SSP_DIR);
		req.then(function(datas){
			var map = _.groupBy(datas.stageSubjectPress || [],function(n){
				return n.schoolStageId + '' + n.subjectId;
			});
			self.stageSubjectPresss = map;
			self.loadStages();
		},function(msg){
			utils.Notice.alert(msg || '加载学段学科教材版本关联信息错误！');
		});
	};
	
	ListVM.prototype.doSearch = function(){
		var self = this;
		var schoolStageId = self.schoolStageId.peek();
		var subjectId = self.subjectId.peek();
		var context = $('.m-table');
		$('tr',context).show();
		if(schoolStageId){
			$('tr[data-schoolStageId !=' + schoolStageId + ']',context).hide();
		}
		if(subjectId){
			$('tr[data-subjectId !=' + subjectId + ']',context).hide();
		}
	};
	
	ListVM.prototype.loadStages = function(){
		var self = this;
		var req = BasicInfoService.schoolStages();
		req.then(function(stages) {
			self.schoolStages(stages);
			var result = _.map(stages || [] ,function(m){
				_.each(m.subjects,function(n){
					var key = m.schoolStageId + '' + n.subjectId;
					var obj = self.stageSubjectPresss[key] || [];
					n.presses = _.sortBy(obj,'ord');
				});
				return m;
			});
			self.stages(result || []);
			self.doSearch();
		}, function() {
			self.stages([]);
		});
	};
	
	
	var SAVE_DIR = Leke.ctx + '/auth/admin/press/doSaveStageSubjectPress.htm';
	ListVM.prototype.doSave = function(dataJson){
		var req = http.post(SAVE_DIR,{dataJson: JSON.stringify(dataJson)});
		return req;
	};
	
	
	var TPL = require('./press.html');
	ListVM.prototype.openAddDialog = function(schoolStageId,subjectId,pressesed){
		var self = this;
		require.async(['common/knockout/koutils'],function(koutils){
			var vm = new ModelVM({schoolStageId: schoolStageId,subjectId: subjectId,pressesed: pressesed});
			koutils.openKoDialog(vm, {
				title : '学段学科教材版本关联',
				tpl : TPL,
				size : 'nm'
			}, function() {
				var dlg = this;
				var result = vm.toDataJson();
				if(vm.verification(result)){
					var req = self.doSave(result);
					req.then(function(){
						self.load();
						dlg.close();
					},function(msg){
						utils.Notice.alert(msg || '信息保存失败！');
					});
				}
			});
		});
	};
	
	ListVM.prototype.dispose = function() {
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	
	ko.applyBindings(new ListVM());
});
