define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var http = require('common/base/http');
	var dialog = require('dialog');
	var utils = require('utils');
	
	function FormVM(params) {
		var self = this;
		var backend = self.backend = params.backend;
		
		self.showShareSchool = Leke.user.currentSchoolNature != 3;
		self.shareSchool = ko.observable(!!backend.shareSchool);
		self.sharePlatform = ko.observable(!!backend.sharePlatform);
		if(!backend.shareSchool && !backend.sharePlatform) {
			self.shareSchool(true);
			self.sharePlatform(true);
		}
		self.section = ko.observable(backend.section);
		self.knowledge = ko.observable(backend.knowledge);
		
		self.sectionPath = ko.pureComputed(function() {
			var sec = self.section();
			return sec ? sec.path : '';
		});
		self.knowledgePath = ko.pureComputed(function() {
			var klg = self.knowledge();
			return klg ? klg.path : '';
		});
		
		self.disposables = [];
	}
	
	FormVM.prototype.openSectionSelect = function(vm, evt) {
		var self = this;
		evt = $.event.fix(evt);
		require.async(['core-business/widget/jquery.leke.matNodeSelect'], function() {
			$(evt.target).matNodeSelect({
				type: 'section',
				multi: false,
				data: self.backend,
				onSelect: function(section) {
					self.section(section);
				}
			});
		});
	};
	
	FormVM.prototype.doRemoveSection = function() {
		var self = this;
		self.section(null);
	};
	
	FormVM.prototype.openKnowledgeSelect = function(vm, evt) {
		var self = this;
		evt = $.event.fix(evt);
		require.async(['core-business/widget/jquery.leke.knowledgeSelect'], function() {
			$(evt.target).knowledgeSelect({
				multi: false,
				data: self.backend,
				onSelect: function(klg) {
					self.knowledge(klg);
				}
			});
		});
	};
	
	FormVM.prototype.doRemoveKnowledge = function() {
		var self = this;
		self.knowledge(null);
	};
	
	FormVM.prototype.doSubmit = function() {
		var self = this;
		var backend = self.backend;
		var params = {};
		params.questionId = backend.questionId;
		if(self.showShareSchool) {
			params.shareSchool = self.shareSchool();
		}
		params.sharePlatform = self.sharePlatform();
		var section = self.section();
		if(section) {
			var secid = section.materialNodeId;
			var oldSec = backend.section;
			var oldSecid = oldSec ? oldSec.materialNodeId : null;
			if(secid != oldSecid) {
				params.section = section;
			}
		}
		var knowledge = self.knowledge();
		if(knowledge) {
			var klgid = knowledge.knowledgeId;
			var oldKlg = backend.knowledge;
			var oldKlgid = oldKlg ? oldKlg.knowledgeId : null;
			if(klgid != oldKlgid) {
				params.knowledge = knowledge;
			}
		}
		if(params.sharePlatform) {
			if(!section) {
				utils.Notice.alert('习题分享给乐课网需选择章节!');
				return false;
			}
		}
		http.postJSON(Leke.ctx + '/auth/teacher/question/doShare.htm', params).then(function() {
			utils.Notice.alert('习题分享操作成功！窗口将在 3 秒中后关闭...');
			setTimeout(function() {
				window.close();
			}, 3000);
		}, function(msg) {
			utils.Notice.alert(msg || '习题分享操作失败！');
		});
	};
	
	FormVM.prototype.doCancel = function() {
		var self = this;
		window.close();
	};
	
	FormVM.prototype.dispose = function() {
		var self = this;
		_.each(self.disposables, function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('question-share', {
	    template: require('./question-share.html'),
	    viewModel: FormVM
	});
});