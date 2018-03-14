define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var http = require('common/base/http');
	var utils = require('utils');
	var dialog = require('dialog');
	var PushBeikeService = require('repository/service/PushBeikeService');
	require('common/knockout/bindings/i18n');
	var RepoQS = require('repository/service/RepoQueryString');
	
	function ListVM(params) {
		var self = this;
		
		self.mode = params.mode || 'view';
		self.wbnode = params.wbnode;
		self.workbook = params.workbook || {};
		
		self.workbookNodeId = ko.pureComputed(function() {
			var wbn  = self.wbnode();
			return wbn && wbn.workbookNodeId || null;
		});
		self.urlAddNormal = ko.pureComputed(function() {
			var nid = self.workbookNodeId();
			return nid ? Leke.ctx + '/auth/common/paper/add/manual.htm?' + RepoQS.stringify({ curWorkbookNodeId: nid}, RepoQS.scope) : '#';
		});
		self.urlAddAnswerSheet = ko.pureComputed(function() {
			var nid = self.workbookNodeId();
			return nid ? Leke.ctx + '/auth/common/paper/add/answersheet.htm?' + RepoQS.stringify({ curWorkbookNodeId: nid}, RepoQS.scope) : '#';
		});
		
		self.loading = ko.observable(false);
		self.papers = ko.observableArray();
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			var wbn  = self.wbnode();
			self.load();
		}));
	}
	
	var WB_PVW_URL = Leke.domain.paperServerName + '/auth/common/workbook/wbnodePapers.htm';
	
	function _processPapers(papers) {
		papers = papers || [];
		
		_.each(papers, function(p) {
			var wbn = p.path;
			if(wbn) {
				var idx = wbn.lastIndexOf('-');
				if(idx > -1) {
					wbn = wbn.substring(idx + 1);
				}
			}
			p.workbookNodeName = wbn;
			
			var urlParams = _.extend({
				paperId: p.paperId,
				curWorkbookNodeId: p.workbookNodeId
			}, RepoQS.scope);
			p.urlEdit = Leke.ctx + '/auth/common/paper/edit/index.htm?' + RepoQS.stringify(urlParams);
			p.urlView = Leke.ctx + '/auth/common/paper/view.htm?' + RepoQS.stringify(urlParams);
			p.urlAssign = Leke.domain.homeworkServerName + '/auth/teacher/batch/distribute.htm?paperIds=' + p.paperId;
		});
		
		return papers;
	}
	
	ListVM.prototype.load = _.debounce(function() {
		var self = this;
		var wbn = self.wbnode.peek();
		var wbnid = wbn && wbn.workbookNodeId;
		
		if(!wbnid) {
			self.papers([]);
			return;
		}
		self.loading(true);
		http.post(WB_PVW_URL, {
			workbookNodeId: wbnid,
			mode: self.mode
		}).then(function(datas) {
			self.papers(_processPapers(datas.papers));
			self.loading(false);
		}, function(msg) {
			utils.Notice.alert(msg || '内容加载失败!');
			self.papers([]);
			self.loading(false);
		});
	}, 300);
	
	ListVM.prototype.toMove = function(item) {
		var self = this;
		var url = Leke.ctx + '/auth/common/workbook/paper/move.htm' + '?paperWorkbookNodeId=' + item.paperWorkbookNodeId;
		dialog.open({
			id: 'workbookPaperMove',
			title: '移动习题册试卷',
			url: url,
			size: 'nm',
			onClose: function(){
				self.load();
			}
		});
	};
		
	ListVM.prototype.doRemove = function(item) {
		var self = this;
		http.post(Leke.ctx + '/auth/common/workbook/paper/doRemove.htm', {
			paperId: item.paperId,
			workbookNodeId: item.workbookNodeId
		}).then(function() {
			utils.Notice.alert('试卷删除成功！');
			self.load();
		}, function(msg) {
			utils.Notice.alert(msg || '试卷删除操作失败！');
		});
	};
	
	var PAP_S_TPL = '<div style="display: none;" data-bind="component: { name: \'wbpap-paper-chooser\', params: $data }"></div>';
	
	ListVM.prototype.openPaperSelect = function() {
		var self = this;
		require.async(['./wbpap-paper-chooser'], function() {
			self.openPaperSelectDialog(PAP_S_TPL, '选择已有试卷');
		});
	};
	
	ListVM.prototype.openPaperSelectDialog = function(tpl, title) {
		var self = this;
		require.async(['common/knockout/koutils'], function(koutils) {
			var vm = {
				onSelect: ko.observable()
			};
			koutils.openKoDialog(vm, {
				title : title,
				tpl : tpl,
				size : 'lg'
			}, function() {
				var dlg = this;
				var sel = vm.onSelect();
				if(sel) {
					sel().then(function(items) {
						self.doAddPapers(items).then(function() {
							self.load();
							dlg.close();
						});
					}, function(msg) {
						utils.Notice.alert(msg || '没有选中任何资源！');
					});
				} else {
					utils.Notice.alert('页面错误，请重新打开');
				}
			});
		});
	};
	
	ListVM.prototype.doAddPapers = function(papers) {
		var self = this;
		var dfd = $.Deferred();
		if(!papers || !papers.length) {
			utils.Notice.alert('没有选中任何资源！');
			return false;
		}
		var wbn = self.wbnode.peek();
		var wbnid = wbn && wbn.workbookNodeId;
		if(!wbnid) {
			utils.Notice.alert('当前没有选中任何关联章节，请关闭对话框重新选择左侧章节！');
			return false;
		}
		var params = {
			workbookNodeId: wbnid,
			paperIds: _.pluck(papers, 'paperId')
		}
		var req = http.postJSON(Leke.ctx + '/auth/common/workbook/paper/doAddBatch.htm', params);
		req.then(dfd.resolve, dfd.reject);
		return dfd.promise();
	};
	
	ListVM.prototype.pushToBeike = function(paper) {
		var self = this;
		PushBeikeService.pushPaperToBeike([paper.paperId]);
	};
	
	ListVM.prototype.dispose = function() {
		var self = this;
		self.disposables.forEach(function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('wbpap-paper-list', {
	    template: require('./wbpap-paper-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});