define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var utils = require('utils');
	var dialog = require('dialog');
	var DM = Leke.domain;
	var PAP_SVR = DM.paperServerName;
	var QUE_SVR = DM.questionServerName;
	var RepoService = require('repository/service/RepoService');
	var LekeDate = require('common/base/date');
	var ListVM = require('repository/common/ListVM');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	require('repository/fmssch-sidebar');
	require('repository/fmstch-sidebar');
	
	function SearchVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.roleId = Leke.user.currentRoleId;
		self.scopeTabs = params.scopeTabs;
		self.tabs = params.tabs;
		self.shareScope = params.shareScope;
		self.resType = 5;
		self.loadUrl = params.loadUrl;
		self.schoolStages = ko.observableArray();
		self.subjects = ko.observableArray();
		self.init({
			qkeys: ['famousSchoolId', 'famousTeacherId', 'schoolStageId', 'subjectId','content']
		});
		
		self._initLinks();
		self._initStgSbjSelect();
		
		self.disposables.push(ko.computed(function() {
			_.each(['famousSchoolId', 'famousTeacherId', 'schoolStageId', 'subjectId','content'], function(field) {
				self.query[field]();
			});
			if(self.inited) {
				self.doSearch();
			}
		}));
		
		self.afterInit();
	}
	
	_.extend(SearchVM.prototype, new ListVM());
	
	var URL_ADD = QUE_SVR + '/auth/common/workbook/workbookAdd.htm?action=create&';
	
	SearchVM.prototype._initLinks = function() {
		var self = this;
		
		self.urlAdd = ko.observable();
		
		self.disposables.push(ko.computed(function() {
			var scope = self.shareScope;
			var tid = self.query.famousTeacherId();
			var sid = self.query.famousSchoolId();
			
			var url;
			if(scope == 2) {
				url = URL_ADD + 'curScope=2';
			} else if(scope == 8 && tid) {
				url = URL_ADD + 'curScope=8&curFmsTchId=' + tid;
			} else if(scope == 9 && sid) {
				url = URL_ADD + 'curScope=9&curFmsSchId=' + sid;
			}
			self.urlAdd(url);
		}));
	};
	
	SearchVM.prototype._initStgSbjSelect = function() {
		var self = this;
		var query = self.query;
		var $ss = self.$ss = self.$el.find('[name=stageSubject]');
		$ss.stgGrdSbjSelect({
			data: {
				schoolStageId: query.schoolStageId(),
				subjectId: query.subjectId()
			},
			onChange: function(item) {
				_.each(['schoolStageId', 'subjectId'], function(k) {
					query[k](item[k]);
				});
			}
		});
	};
	
	SearchVM.prototype.doPraise = function(record) {
		var self = this;
		RepoService.doPraiseWorkbook(record.workbookId).then(function() {
			utils.Notice.alert('点赞操作成功');
			record.praiseCount(record.praiseCount() + 1);
		}, function(msg) {
			utils.Notice.alert(msg || '点赞操作失败!');
		});
	};
	
	SearchVM.prototype.doFavorite = function(record) {
		var self = this;
		RepoService.doFavoriteWorkbook(record.workbookId).then(function() {
			utils.Notice.alert('习题册收藏成功');
		}, function(msg) {
			utils.Notice.alert(msg || '习题册收藏操作失败');
		});
	};
	
	
	
	SearchVM.prototype.openAddDialog = function() {
		var self = this;
		dialog.open({
			id: 'workbookAdd',
			title: '创建习题册',
			url: self.urlAdd(),
			size: 'nm',
			onClose: function(){
				self.load();
			}
		});
	};
	
	SearchVM.prototype.doRename = function(data) {
		var self = this;
		dialog.open({
			id: 'workbookEdit',
			title: '习题册重命名',
			url: QUE_SVR + '/auth/common/workbook/workbookEdit.htm?workbookId=' + data.workbookId,
			size: 'nm',
			onClose: function(){
				self.load();
			}
		});
	};
	
	SearchVM.prototype.processRecords = function(datas) {
		var self = this;
		return RepoService.processRecords(datas.records || [], {
			curScope: self.shareScope,
			action: 'override'
		});
	};
	
	SearchVM.prototype.dispose = function() {
		var self = this;
		_.each(self.disposables, function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('agency-workbook-list', {
	    template: require('./agency-workbook-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new SearchVM(params, componentInfo.element);
	    	}
	    }
	});
});