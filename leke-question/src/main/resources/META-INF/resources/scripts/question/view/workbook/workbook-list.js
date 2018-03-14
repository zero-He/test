define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var http = require('common/base/http');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var I18n = require('i18n');
	var dialog = require('dialog');
	var utils = require('utils');
	var Roles = require('core-business/roles');
	require('common/knockout/bindings/i18n');
	require('common/knockout/component/leke-page');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	function Query(){
		var self = this;
		self.schoolStageId = ko.observable();
		self.subjectId = ko.observable();
		self.pressId = ko.observable();
		self.status = ko.observable();
		self.workbookName = ko.observable();
		self.curPage = ko.observable(1);
	}
	
	Query.prototype.toJS = function() {
		var self = this;
		var result = {};
		_.each(['schoolStageId','subjectId','pressId','status','curPage','workbookName'], function(key) {
			var val = self[key].peek();
			if(val != null && val !== '') {
				result[key] = val;
			}
		});
		return result;
	};
	function ListVM(params,el){
		var self = this;
		self.$el = $(el);
		self.roleId = Leke.user.currentRoleId;
		self.query = new Query();
		self.totalSize = ko.observable(0);
		self.workbooks = ko.observableArray();
		self.schoolStages = ko.observableArray();
		self.subjects = ko.observableArray();
		self.presses = ko.observableArray();
		self.statusOptions = ko.observableArray([{status: 0,statusName: '未上架'},{status: 1,statusName: '已上架'}]);
		self.disposables = [];
	
		self.disposables.push(ko.computed(function() {
			//点击分页
			var curPage = self.query.curPage();
			self.load();
		}));
		
		self.disposables.push(ko.computed(function() {
			var schoolStageId = self.query.schoolStageId();
			var subjectId = self.query.subjectId();
			self.loadPresses();
		}));
		
		self._initStgSbjSelect();
	};
	
	ListVM.prototype._initStgSbjSelect = function() {
		var self = this;
		var query = self.query;
		var $ss = self.$ss = $('[name=stageSubject]');
		$ss.stgGrdSbjSelect({
			data: {
				schoolStageId: query.schoolStageId(),
				subjectId: query.subjectId()
			},
			model: 'nocache',
			stgAll: true,
			onChange: function(item) {
				_.each(['schoolStageId', 'subjectId'], function(k) {
					query[k](item[k]);
				});
			}
		});
	};
	
	ListVM.prototype.loadPresses = _.debounce(function() {
		var self = this;
		var schoolStageId = self.query.schoolStageId();
		var subjectId = self.query.subjectId();

		BasicInfoService.presses(schoolStageId,subjectId).done(function(presses) {
			self.presses(presses);
		}).fail(function() {
			self.presses([]);
		});
	},300);
	
	var URL_DIR = '/auth/common/workbook/';
	ListVM.prototype.load = function(){
		var self = this;
		var roleId = Leke.user.currentRoleId;
		var query = self.query.toJS();
		http.post(window.ctx + URL_DIR +'findWorkbooks.htm', query)
			.then(function(datas) {
				var page = datas.page||{};
				var workbook = datas.workbooks || [];
				_.each(workbook,function(n){
					var editUrl = Leke.domain.paperServerName + URL_DIR + 'edit.htm?workbookId=' + n.workbookId;
					if(roleId == Roles.INPUTER) {
						editUrl += '&curScope=7';
					}
					n.editUrl = editUrl;
					if (n.photoUrl == ""){
						n.uploadStr = "未上传";
					}
				});
				self.workbooks(workbook);
				self.query.curPage(page.curPage || 1);
				self.totalSize(page.totalSize || 0);
			}, function(){
				self.workbooks([]);
				self.totalSize(0);
			});
	};
	
	var URL_ADD = Leke.ctx + '/auth/common/workbook/workbookAdd.htm';
	
	ListVM.prototype.workbookAdd = function(){
		var self = this;
		var url = URL_ADD;
		var roleId = Leke.user.currentRoleId;
		if( roleId == Roles.INPUTER || roleId == Roles.QUESTION_ADMIN) {
			url += '?action=create&curScope=7';
		}else if (roleId == Roles.RESEARCHER){
			url += '?action=create&curScope=7';
		} else if(roleId == Roles.PROVOST) {
			url += '?action=create&curScope=2&status=1';
		} else if(roleId == Roles.TEACHER) {
			url += '?action=create&curScope=2&status=1';
		}
		dialog.open({
			id: 'workbookAdd',
			title: '创建习题册',
			url: url,
			size: 'nm',
			onClose: function(){
				self.load();
			}
		});
	};
	
	ListVM.prototype.workbookDel = function(data){
		var self = this;
		dialog.confirm('确定删除该习题册吗？').done(function(sure){
			if(!sure) {
				return;
			}
			http.jsonp(Leke.ctx + '/auth/common/workbook/jsonp/doDelete.htm',{
				workbookId: data.workbookId
			}).then(function(){
				utils.Notice.alert('习题册删除成功');
				self.load();
			}, function(msg) {
				utils.Notice.alert(msg || '习题册删除失败');
			});
		});
	};
	
	ListVM.prototype.rebuildIndex = function(data){
		this._confirmAction(data, 'rebuildIndex.htm', '重新索引');
	};
	
	ListVM.prototype.doSearch = function(){
		var self = this;
		self.query.curPage(1);
		self.load();
	};
	
	ListVM.prototype.workbookEdit = function(data){
		var self = this;
		dialog.open({
			id: 'workbookEdit',
			title: '习题册重命名',
			url: window.ctx + '/auth/common/workbook/workbookEdit.htm?workbookId=' + data.workbookId,
			size: 'nm',
			onClose: function(){
				self.load();
			}
		});
	};
	
	ListVM.prototype.workbookUpload = function(data){
		var self = this;
		var photoPathHere = {};
		if (!data.photoUrl){
			photoPathHere = Leke.domain.staticServerName + "/images/resource/suoluetu.png";
		}else{
			photoPathHere = Leke.domain.fileServerName + '/' + data.photoUrl;
		}
		var url = window.ctx + URL_DIR + 'preUpdatePhoto.htm?workbookId=' + data.workbookId;
		url += '&photoUrl=' + photoPathHere;
		
		dialog.open({
			'title': '习题册封面上传',//修改头像
			'url': url,
			'size': 'sm',
			'onClose' : function(message) {
				if (message != undefined && message != '') {
					self.load();
				}
			}
		});
	};
	
	ListVM.prototype._confirmAction = function(data, url, action) {
		var self = this;
		dialog.confirm('确定' + action + '该习题册吗？').done(function(sure){
			if(!sure) {
				return;
			}
			http.post(Leke.ctx + '/auth/common/workbook/' + url, {
				workbookId: data.workbookId
			}).then(function(){
				utils.Notice.alert(action + '操作成功');
				self.load();
			}, function(msg) {
				utils.Notice.alert(msg || action + '操作失败');
			});
		});
	};
	
	ListVM.prototype.workbookUp = function(data){
		this._confirmAction(data, 'workbookUp.htm', '上架');
	};
	
	ListVM.prototype.workbookDown = function(data){
		this._confirmAction(data, 'workbookDown.htm', '下架');
	};
	
	ListVM.prototype.dispose = function() {
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('workbook-list', {
	    template: require('./workbook-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params,componentInfo.element);
	    	}
	    }
	});
	I18n.init('queJS');
	ko.applyBindings();
});