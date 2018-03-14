define(function(require,exports,module){
	var ko = require('knockout');
	var $ = require('jquery');
	var http = require('common/base/http');
	var dialog = require('dialog');
	var utils = require('utils');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var I18n = require('i18n');
	var validate =require('validate');
	var RepoQS = require('repository/service/RepoQueryString');
	
	I18n.init('queJS');
	require('common/knockout/bindings/i18n');
	
	var workbookAdd = {
		init: function(){
			var self = this;
			self.bindEvent();
			self.validate();
		},
		bindEvent: function(){
			$('#saveBtn').on('click',function(){
				if($('#form1').valid()){
					$('#schoolStageName').val($('#schoolStageId').find("option:selected").text());
					$('#subjectName').val($('#subjectId').find("option:selected").text());
					$('#pressName').val($('#pressId').find("option:selected").text());
					$('#materialName').val($('#materialId').find("option:selected").text());
					if (!$('#photoUrl').val() && Leke.user.currentRoleId == 402){
						utils.Notice.alert('请选择习题册封面');
						return;
					}
					var model = $('#form1').serialize();
					var URL_ADD = Leke.ctx + '/auth/common/workbook/doWorkbookAdd.htm?';
					var url = URL_ADD + RepoQS.stringify(RepoQS.scope);
					$.ajax({
						type: 'post',
						url: url,
						dataType: 'json',
						data: model,
						success: function(datas){
							if(datas.success){
								dialog.close('workbookAdd',true);
								utils.Notice.alert('保存成功');
							}
						}
					});
				}
			});
			
			$('#closeBtn').on('click',function(){
				dialog.close('workbookAdd', true);
			});
		},
		validate:function(){  
			$('#form1').validate({
				rules: {
					workbookName: {
						required: true,
						maxlength: 50
					},
					schoolStageId: {
						required: true
					},
					subjectId: {
						required: true
					},
					pressId: {
						required: Leke.user.currentRoleId == 402
					},
					materialId: {
						required: Leke.user.currentRoleId == 402
					}
				},
				messages: {
					workbookName: {
						required: '请填写习题册名称', 
						maxlength:'习题册最多输入50个汉字'
					},
					schoolStageId: {
						required: '请选择学段'
					},
					subjectId: {
						required: '请选择科目'
					},
					pressId: {
						required: '请选择教材版本'
					},
					materialId: {
						required: '请选择年级或课本'
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
		self.subjectId = ko.observable();
		self.pressId = ko.observable();
		self.materialId = ko.observable();
	};
	
	function Workbook(){
		var self = this;
		self.model = new Model();
		self.schoolStages = ko.observableArray();
		self.subjects = ko.observableArray();
		self.presses = ko.observableArray();
		self.materials = ko.observableArray();
		self.photoUrl = ko.observable();
		self.photoPath = ko.observable();
		self.photoPath(Leke.domain.staticServerName + '/images/resource/suoluetu.png');
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self.loadSubjects();
			self.loadPresses();
			self.loadMaterials();
		}));
		self.loadSchoolStages();
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
	
	var UPLOAD_DIR = Leke.ctx + '/auth/common/workbook/addPhoto.htm?';
	Workbook.prototype.workbookUpload = function(data){
		var self = this;
		var url = UPLOAD_DIR + 'photoUrl=' + self.photoPath();
		dialog.open({
			'title': '习题册封面上传',
			'url': url,
			'size': 'sm',
			'onClose' : function(message) {
				if (message) {
					self.photoPath(Leke.domain.fileServerName + '/' + message);
					self.photoUrl(message);
				}
			}
		});
	};
	
	Workbook.prototype.dispose = function() {
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	
	workbookAdd.init();
	ko.applyBindings(new Workbook());
});