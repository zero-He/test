define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var PaperService = require('paper/service/paperService');
	var ROLES = require('core-business/roles');
	
	var EditorVM = require('paper/viewmodel/editor');
	var WBPAP_QUE = require('question/wbpap-que');
	
	require('paper/component/paper.editor.content');
	
	function initPaper(paperId, workbook, wbnode) {
		var paper = {
			shareSchool: false,
			sharePlatform: false,
			schoolStageId: workbook.schoolStageId,
			subjectId: workbook.subjectId,
			subjectName: workbook.subjectName,
			title: wbnode.workbookNodeName,
			paperType: 1,
			wbnodes: [wbnode]
		};
		var roleId = Leke.user.currentRoleId;
		if(roleId == ROLES.RESEARCHER) {
			paper.sharePlatform = true;
		}
		if(roleId == ROLES.PROVOST) {
			paper.shareSchool = true;
		}
		
		var dfd = $.Deferred();
		if(paperId) {
			PaperService.get({
				paperId: paperId
			}).then(function(datas) {
				dfd.resolve($.extend(paper, datas.paper, { wbnodes: [wbnode] }));
			}, function() {
				dfd.resolve(paper);
			});
		} else {
			dfd.resolve(paper);
		}
		return dfd.promise();
	}
	
	function ContentVM(params, element) {
		var self = this;
		self.$el = $(element);
		
		self.paperId = params.paperId;
		self.workbook = params.workbook;
		self.wbnode = params.wbnode;
		self.scene = params.scene;
		
		self.inited = ko.observable(false);
		self.rootvm = null;
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			var paperId = self.paperId();
			var workbook = self.workbook.peek();
			var wbnode = self.wbnode();
			
			setTimeout(function() {
				self.initEditorVM(paperId, workbook, wbnode);
			}, 1);
		}));
	}
	
	ContentVM.prototype.initEditorVM = function(paperId, workbook, wbnode) {
		var self = this;
		self.inited(false);
		initPaper(paperId, workbook, wbnode).done(function(paper) {
			WBPAP_QUE.setWbnode(wbnode);
			
			var _u = Leke.user;
			var vm = new EditorVM({
				paper: paper,
				roleId: _u.currentRoleId,
				schoolId: _u.currentSchoolId,
				mode: 'add',
				wbpap: true
			});
			vm.afterCancelAdd = vm.afterCancelEdit = vm.afterSave = function() {
				self.scene('list');
			};
			self.rootvm = vm;
			
			self.inited(true);
		});
	};
	
	ContentVM.prototype.dispose = function() {
		var self = this;
		self.disposables.forEach(function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('wbpap-edit-content', {
	    template: require('./wbpap-edit-content.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ContentVM(params, componentInfo.element);
	    	}
	    }
	});
});