define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
	var koutils = require('common/knockout/koutils');
	var dialog = require('dialog');
	
	var MaterialService = require('../service/materialService');
	var BasicInfoService = require('core-business/service/basicInfoService');
	require('common/knockout/bindings/i18n');
	require('common/knockout/component/leke-page');
	require('./mat-edit');
	
	function Query() {
		this.schoolStageId = ko.observable();
		this.subjectId = ko.observable();
		this.pressId = ko.observable();
		this.ord = ko.observable();
		this.curPage = ko.observable(1);
	}
	
	function ListVM() {
		var self = this;
		self.query = new Query();
		self.totalSize = ko.observable(0);
		self.materials = ko.observableArray();
		
		self.schoolStages = ko.observableArray();
		self.subjects = ko.observableArray();
		self.presses = ko.observableArray();
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self.loadSubjects();
		}));
		self.disposables.push(ko.computed(function() {
			var curPage = self.query.curPage();
			self.load();
		}));
		
		self.loadSchoolStages();
		self.loadPresses();
	}
	
	ListVM.prototype.loadSchoolStages = function() {
		var self = this;
		BasicInfoService.schoolStages().done(function(stages) {
			self.schoolStages(stages);
		}).fail(function() {
			self.schoolStages([]);
		});
	};
	
	ListVM.prototype.loadSubjects = function() {
		var self = this;
		var schoolStageId = self.query.schoolStageId();
		BasicInfoService.subjects(schoolStageId).done(function(subjects) {
			self.subjects(subjects);
		}).fail(function() {
			self.subjects([]);
		});
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
			self.materials(datas.materials || []);
			self.query.curPage(page.curPage || 1);
			self.totalSize(page.totalSize || 0);
		}).fail(function() {
			self.materials([]);
			self.totalSize(0);
		});
	};
	
	ListVM.prototype.toTree = function(mat) {
		if(mat) {
			var query = {
				materialId: mat.materialId,
				schoolStageId: mat.schoolStageId,
				subjectId: mat.subjectId,
				pressId: mat.pressId
			};
			var qstr = '';
			_.each(query, function(v, k) {
				if(qstr) {
					qstr += '&';
				}
				qstr += (k + '=' + v);
			});
			var url = window.ctx + '/auth/admin/material/materialTree.htm?' + qstr;
			window.open(url);
		}
	};

	ListVM.prototype.doAdd = function() {
		var self = this;
		var mat = ko.toJS(self.query);
		mat.ord = mat.ord || 0;
		self.openEdit({
			data: mat,
			title: '新增教材',
			errorMsg: '教材新增失败！'
		});
	};
	
	ListVM.prototype.doModify = function(mat) {
		var self = this;
		mat.ord = mat.ord || 0;
		self.openEdit({
			data: mat,
			title: '修改教材',
			errorMsg: '教材修改失败！'
		});
	};
	
	var ED_TPL = '<div style="display: none;" data-bind="component: { name: \'mat-edit\', params: { material: $data } }"></div>';
	
	ListVM.prototype.openEdit = function(options) {
		var self = this;
		var ops = $.extend({
			data: null,
			title: '修改教材',
			errorMsg: '教材修改失败！'
		}, options);
		
		var mat = new Material(ops.data);
		var $ed = $(ED_TPL);
		$('body').append($ed);
		ko.applyBindings(mat, $ed.get(0));
		
		dialog.open({
			title: ops.title,
			tpl: $ed,
			size: 'sm',
			btns: [{
				className: 'btn-ok',
				text: '保存',
				cb: function() {
					var dlg = this;
					var params = ko.toJS(mat);
					var valid = _validate(params);
					if(!valid) {
						return false;
					}
					var req = params.materialId ? MaterialService.modifyRoot(params) : MaterialService.addRoot(params);
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
	
	function _validate(mat) {
		if(!mat) {
			utils.Notice.alert('缺少教材信息！');
			return false;
		}
		if(!mat.schoolStageId) {
			utils.Notice.alert('请选择学段！');
			return false;
		}
		if(!mat.subjectId) {
			utils.Notice.alert('请选择科目！');
			return false;
		}
		if(!mat.pressId) {
			utils.Notice.alert('请选择出版社！');
			return false;
		}
		
		if(!mat.ord){
			utils.Notice.alert('请输入序号！');
			return false;
		}
		var reg = /^[0-9]*[1-9][0-9]*$/;
		if(!reg.test(mat.ord)){
			utils.Notice.alert('序号请输入正整数！');
			return false;
		}
		var nm = mat.materialName;
		if(!nm || nm.length < 1 || nm.length > 30) {
			utils.notice.alert('请输入 1-30 个字之间的教材名称！');
			return false;
		}
		return true;
	}
	
	function _isInteger(obj) {
		 return Math.floor(obj) == obj
	};
	
	function Material(params) {
		var self = this;
		params = params || {};
		_.each(['materialId', 'materialName', 'schoolStageId', 'schoolStageName', 
		        'subjectId', 'subjectName', 'pressId', 'pressName','ord'], function(attr) {
			self[attr] = ko.observable(params[attr]);
		});
	}
	
	ListVM.prototype.doDel = function(mat) {
		var self = this;
		MaterialService.delRoot({
			materialId: mat.materialId
		}).then(function() {
			utils.Notice.alert('教材删除成功！');
			self.load();
		}, function(msg) {
			utils.Notice.alert(msg || '教材删除失败！');
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