define(function(require,exports,module){
	var ko = require('knockout');
	var $ = require('jquery');
	var http = require('common/base/http');
	var dialog = require('dialog');
	var utils = require('utils');
	var MaterialService = require('./service/outlineService');
	var BasicInfoService = require('core-business/service/basicInfoService');
	//var UserPrefService = require('core-business/service/userPrefService');
	var I18n = require('i18n');
	var validate =require('validate');
	require('common/knockout/bindings/i18n');
	
	var outlineAdd = {
		init: function(){
			var self = this;
			self.bindEvent();
			self.validate();
		},
		bindEvent: function(){
			$('#saveBtn').on('click',function(){
				if($('#form1').valid()){
					$('#gradeName').val($('#gradeId').find("option:selected").text());
					$('#subjectName').val($('#subjectId').find("option:selected").text());
					if($('#pressId').val()!=''){
						$('#pressName').val($('#pressId').find("option:selected").text());
					}

					var model = $('#form1').serialize();
					$.ajax({
						type: 'post',
						url: Leke.ctx + '/auth/schoolResearcher/syllabus/outline/doOutlineAdd.htm',
						dataType: 'json',
						data: model,
						success: function(datas){
							if(datas.success){
								dialog.close('outlineAdd',true);
								utils.Notice.alert('保存成功');
							}
						}
					});
				}
			});
			$('#addMaterial').on('click',function(){
				if($('#gradeId').val()==''){
					utils.Notice.alert('请先选择年级');
					return false;
				}
				if($('#subjectId').val()==''){
					utils.Notice.alert('请先选择科目');
					return false;
				}
				$('#pressId').prop("disabled",true);
				var gradeId = $('#gradeId').val();
				var subjectId = $('#subjectId').val();
				var pressId = $('#pressId').val();
				var urlD ='gradeId=' + gradeId;
				urlD += '&subjectId=' + subjectId;
				urlD += '&pressId=' + pressId;
				urlD += '&crossDomain=' + window.crossDomain;
				dialog.open({
					id: 'materialView',
					title: '选择教材',
					url: Leke.ctx + '/auth/schoolResearcher/syllabus/outline/materialView.htm?' + urlD,
					size: 'lg',
					onClose: function(mData){
						$('#materialId').val(mData.materialId || '');
						$('#materialName').val(mData.materialName || '');
						$('#materialHtml').html(mData.materialName || '');
						$('#pressId').val(mData.pressId || '');
					}
				
				});
				
			});
			$('#closeBtn').on('click',function(){
				dialog.close('outlineAdd', true);
			});
		},
		validate:function(){  
			$('#form1').validate({
				rules: {
					outlineName: {
						required: true,
						maxlength: 30
					},
					gradeId: {
						required: true
					},
					subjectId: {
						required: true
					}
				},
				messages: {
					outlineName: {
						required: '请填写大纲名称', 
						maxlength:'大纲最多输入30个汉字'
					},
					gradeId: {
						required: '请填写学段'
					},
					subjectId: {
						required: '请填写科目'
					}
				},
				errorElement: 'em',
				errorPlacement: function (error, element) {
					error.appendTo(element.siblings('.checkinfo')); 
				}
			});
		}
	};
	function Model(){
		var self = this;
		self.schoolStageId = ko.observable();
		self.gradeId = ko.observable();
		self.subjectId = ko.observable();
		self.pressId = ko.observable();
	};
	function Outline(){
		var self = this;
		self.model = new Model();
		self.schoolStages = ko.observableArray();
		self.grades = ko.observableArray();
		self.subjects = ko.observableArray();
		self.presses = ko.observableArray();
		self.disposables = [];
		self.loadSchoolStages();
		self.disposables.push(ko.computed(function() {
			self.loadSubjects();
		}));
		
		//self.loadGrades(); 在加载schoolstage中确定grade
		self.loadPresses();
	};
	Outline.prototype.loadSchoolStages = function() {
		var self = this;
		var gradetests = [];
		
		MaterialService.querySchoolStages().done(function(datas) {
			self.schoolStages(datas.schoolStages);
			for ( var i = 0; i < datas.schoolStages.length; i++) {
				gradetests = gradetests.concat(datas.schoolStages[i].grades);
			}
			self.grades(gradetests);
		}).fail(function() {
			self.schoolStages([]);
			self.grade([]);
		});
	};
	
	Outline.prototype.loadGrades = function() {
		var self = this;
		//var schoolStageId = self.model.schoolStageId();
		//传空值取出所有年级
		var schoolStageId;
		BasicInfoService.grades(schoolStageId).done(function(grades) {
			self.grades(grades);
		}).fail(function() {
			self.grades([]);
		});
		
	};
	
	Outline.prototype.loadSubjects = function() {
		var self = this;
		var gradeId = self.model.gradeId();
		var schoolStageId;
		
		if(gradeId == undefined){
			var subjecttests = [];
			var subjecttests2 = [];
			
			//UserPrefService.currentSchoolStages().done(function(stages) {
			MaterialService.querySchoolStages().done(function(datas) {
				self.schoolStages(datas.schoolStages);
				for (var i = 0; i < datas.schoolStages.length; i++) {
					subjecttests = subjecttests.concat(datas.schoolStages[i].subjects);
				}
				
				for (var i = 0; i < subjecttests.length; i++) {
					for (var j = 0; j < subjecttests2.length; j++){
						if (subjecttests2[j].subjectId == subjecttests[i].subjectId){
							subjecttests[i].subjectId = null;
							break;
						}
					}
					if (subjecttests[i].subjectId == null){
						
					}else{
						subjecttests2.push(subjecttests[i]);
					}
				}
				
				self.subjects(subjecttests2);
				
			}).fail(function() {
				self.subjects([]);
			});
		}else{
			MaterialService.querySchoolStageIdByGradeId({
				gradeId:  self.model.gradeId()
			}).then(function(datas) {
				BasicInfoService.subjects(datas.outline.schoolStageId).done(function(subjects) {
							self.subjects(subjects);
				}).fail(function() {
					self.subjects([]);
				});
			}, function(msg) {
				utils.Notice.alert(msg || '查询学段失败！');
			});
		}
	};
	
	Outline.prototype.loadPresses = function() {
		var self = this;
		//出版社设为不可写20160715 by yangzy
		$('#pressId').prop("disabled",true);
		BasicInfoService.presses().done(function(presses) {
			self.presses(presses);
		}).fail(function() {
			self.presses([]);
		});
	};
	Outline.prototype.clean = function(){
		var self = this;
		$('#isCopyMaterial').attr('checked',false);
		$('#materialId').val('');
		$('#materialName').val('');
		$('#materialHtml').html('');
	};
	Outline.prototype.dispose = function() {
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	outlineAdd.init();
	I18n.init('queJS');
	ko.applyBindings(new Outline());
});