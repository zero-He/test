define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
	var koutils = require('common/knockout/koutils');
	var dialog = require('dialog');
	var date = require('common/base/date');
	
	var MaterialService = require('../service/outlineService');
	var BasicInfoService = require('core-business/service/basicInfoService');
	//var UserPrefService = require('core-business/service/userPrefService');
	require('common/knockout/bindings/i18n');
	require('common/knockout/component/leke-page');
	require('./mat-edit');
	
	function Query() {
		this.schoolStageId = ko.observable();
		this.gradeId = ko.observable();
		this.outlineName = ko.observable();
		this.subjectId = ko.observable();
		this.pressId = ko.observable();
		this.curPage = ko.observable(1);
	}
	
	function ListVM() {
		var self = this;
		self.query = new Query();
		self.totalSize = ko.observable(0);
		self.outlines = ko.observableArray();
		
		self.grades = ko.observableArray();
		self.schoolStages = ko.observableArray();
		self.subjects = ko.observableArray();
		self.presses = ko.observableArray();
		self.materials = ko.observableArray();
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self.loadSubjects();
		}));
		self.disposables.push(ko.computed(function() {
			var curPage = self.query.curPage();
			self.load();
		}));
		
		self.loadSchoolStages();
		//self.loadGrades();通过stage查
		self.loadPresses();
	}
	
	ListVM.prototype.loadSchoolStages = function() {
		var self = this;
		var gradetests = [];
		//var subjecttests = [];
		
		MaterialService.querySchoolStages().done(function(datas) {
			self.schoolStages(datas.schoolStages);
			for ( var i = 0; i < datas.schoolStages.length; i++) {
				gradetests = gradetests.concat(datas.schoolStages[i].grades);
				//subjecttests = subjecttests.concat(stages[i].subjects)
			}
			
			self.grades(gradetests);
			//self.subjects(subjecttests);
			
		}).fail(function() {
			self.schoolStages([]);
			self.grade([]);
			//self.subjects([]);
		});
		
	};
	
	ListVM.prototype.loadGrades = function() {
		var self = this;
		var schoolStageId = self.query.schoolStageId();
		BasicInfoService.grades(schoolStageId).done(function(grades) {
			self.grades(grades);
		}).fail(function() {
			self.grades([]);
		});
	};
	
	ListVM.prototype.loadSubjects = function() {
		var self = this;
		//var schoolStageId = self.query.schoolStageId();
		var gradeId = self.query.gradeId();
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
				gradeId:  self.query.gradeId()
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
	
	ListVM.prototype.loadPresses = function() {
		var self = this;
		BasicInfoService.presses().done(function(presses) {
			self.presses(presses);
		}).fail(function() {
			self.presses([]);
		});
	};
	
	ListVM.prototype.doSearch = function() {
		this.query.curPage(1);
		this.load();
	};
	
	ListVM.prototype.load = function() {
		var self = this;
		var query = koutils.peekJS(self.query);
		MaterialService.getRoots(_.cleanup(query)).done(function(datas) {
			var page = datas.page || {};
			for ( var i = 0; i < datas.outlines.length; i++) {
				datas.outlines[i].createdOn = date.format(
						datas.outlines[i].createdOn,
						'yyyy-MM-dd HH:mm:ss');
				if (datas.outlines[i].status == 1){
					datas.outlines[i].statusStr = '上架';
				}else{
					datas.outlines[i].statusStr = '下架';
				}
				
			}
			
			self.outlines(datas.outlines || []);
			self.query.curPage(page.curPage || 1);
			self.totalSize(page.totalSize || 0);
			
		}).fail(function() {
			self.outlines([]);
			self.totalSize(0);
		});
	};
	
	ListVM.prototype.toTree = function(outline) {
		if(outline) {
			var query = {
				outlineId: outline.outlineId,
				/*schoolStageId: mat.schoolStageId,
				subjectId: mat.subjectId,
				pressId: mat.pressId*/
			};
			var qstr = '';
			_.each(query, function(v, k) {
				if(qstr) {
					qstr += '&';
				}
				qstr += (k + '=' + v);
			});
			var url = window.ctx + '/auth/schoolResearcher/syllabus/outline/outlineTree.htm?' + qstr;
			window.open(url);
		}
	};


	var URL_ADD = Leke.ctx + '/auth/schoolResearcher/syllabus/outline/outlineAdd.htm';
	
	ListVM.prototype.doAdd = function(){
		var self = this;
		var url = URL_ADD;
		
		url += '?sharePlatform=true';
		/*var roleId = Leke.user.currentRoleId;
		if(roleId == Roles.RESEARCHER) {
			url += '?sharePlatform=true';
		} else if(roleId == Roles.PROVOST) {
			url += '?shareSchool=true&status=1';
		} else if(roleId == Roles.TEACHER) {
			url += '?shareSchool=true&status=1';
		}*/
		dialog.open({
			id: 'outlineAdd',
			title: '创建大纲',
			url: url,
			size: 'nm',
			onClose: function(){
				self.load();
			}
		});
	};
	
	ListVM.prototype.doModify = function(outline) {
		var self = this;
		self.openEdit({
			data: 	outline,
			title: '修改大纲',
			errorMsg: '大纲修改失败！'
		});
	};
	
	var ED_TPL = '<div style="display: none;" data-bind="component: { name: \'mat-edit\', params: { outline: $data } }"></div>';
	
	ListVM.prototype.openEdit = function(options) {
		var self = this;
		var ops = $.extend({
			data: null,
			title: '大纲教材',
			errorMsg: '大纲修改失败！'
		}, options);
		
		var outline = new Outline(ops.data);
		var $ed = $(ED_TPL);
		$('body').append($ed);
		ko.applyBindings(outline, $ed.get(0));
		
		dialog.open({
			title: ops.title,
			tpl: $ed,
			size: 'sm',
			btns: [{
				className: 'btn-ok',
				text: '保存',
				cb: function() {
					var dlg = this;
					var params = ko.toJS(outline);
					var valid = _validate(params);
					if(!valid) {
						return false;
					}
					var req = params.outlineId ? MaterialService.modifyRoot(params) : MaterialService.addRoot(params);
					req.then(function() {
						self.load();
						dlg.close();
					}, function(msg) {
						utils.Notice.alert(msg || ops.errorMsg);
					});
				}
			}, {
				className: 'btn-cancel',
				text: '取消',
				cb: function() {
					this.close();
				}
			}],
			onClose: function() {
				ko.removeNode($ed.get(0));
			}
		});
	};
	
	function _validate(outline) {
		if(!outline) {
			utils.Notice.alert('缺少大纲信息！');
			return false;
		}
		if(!outline.gradeId) {
			utils.Notice.alert('请选择年级！');
			return false;
		}
		if(!outline.subjectId) {
			utils.Notice.alert('请选择科目！');
			return false;
		}

		var nm = outline.outlineName;
		if(!nm || nm.length < 1 || nm.length > 30) {
			utils.Notice.alert('请输入 1-30 个字之间的大纲名称！');
			return false;
		}
		return true;
	}
	
	function Outline(params) {
		var self = this;
		params = params || {};
		_.each(['outlineId', 'outlineName', 'materialId', 'materialName', 'schoolStageId', 'schoolStageName', 
				'subjectId', 'subjectName', 'pressId', 'pressName', 'gradeId', 'gradeName'], function(attr) {
			self[attr] = ko.observable(params[attr]);
		});
	}
	
	ListVM.prototype.doDel = function(outline) {		
		var self = this;
		dialog.confirm('确定删除该大纲吗？删除后章节将一起删除，不可恢复。').done(function(sure){
			if(!sure) {
				return;
			}
			MaterialService.delRoot({
				outlineId: outline.outlineId
			}).then(function() {
				utils.Notice.alert('大纲删除成功！');
				self.load();
			}, function(msg) {
				utils.Notice.alert(msg || '大纲删除失败！');
			});
		});
	};
	
	/*ListVM.prototype._confirmAction = function(data, url, action) {
		var self = this;
		dialog.confirm('确定' + action + '该大纲吗？').done(function(sure){
			if(!sure) {
				return;
			}
			http.post(Leke.ctx + '/schoolResearcher/syllabus/outline/' + url, {
				outlineId: data.outlineId
			}).then(function(){
				utils.Notice.alert(action + '操作成功');
				self.load();
			}, function(){
				utils.Notice.alert(action + '操作失败');
			});
			
		});
	};*/
	
	ListVM.prototype.outlineUp = function(data){
		//this._confirmAction(data, 'outlineUp.htm', '上架');
		
		var self = this;
		dialog.confirm('确定上架该大纲吗？').done(function(sure){
			if(!sure) {
				return;
			}
			MaterialService.outlineUp({
				outlineId: data.outlineId
			}).then(function() {
				utils.Notice.alert('上架操作成功');
				self.load();
			}, function(msg) {
				utils.Notice.alert('上架操作失败');
			});
			
		});
		
		
	};
	
	ListVM.prototype.outlineDown = function(data){
		//this._confirmAction(data, 'outlineDown.htm', '下架');
		
		var self = this;
		dialog.confirm('确定下架该大纲吗？').done(function(sure){
			if(!sure) {
				return;
			}
			MaterialService.outlineDown({
				outlineId: data.outlineId
			}).then(function() {
				utils.Notice.alert('下架操作成功');
				self.load();
			}, function(msg) {
				utils.Notice.alert('下架操作失败');
			});
			
		});
	};
	
	ListVM.prototype.doRebuildIndex = function(mat) {
		MaterialService.rebuildTreeIndex({
			materialId: mat.materialId
		}).then(function() {
			utils.Notice.alert('重建索引成功！');
		}, function(msg) {
			utils.Notice.alert(msg || '重建索引失败！');
		});
	};
	
	ListVM.prototype.dispose = function() {
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('mat-list', {
	    template: require('./mat-list.html'),
	    viewModel: ListVM
	});
});