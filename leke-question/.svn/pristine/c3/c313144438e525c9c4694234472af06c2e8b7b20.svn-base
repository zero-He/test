define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
	var dialog = require('dialog');
	var DFN = require('homework/sheet.dfn');
	var Pages = require('common/base/pages');
	
	var BasicInfoService = require('core-business/service/basicInfoService');
	var QuestionService = require('question/service/questionService');

	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	require('common/knockout/bindings/i18n');
	
	function Query(params) {
		this.schoolStageId = ko.observable();
		this.subjectId = ko.observable();
		this.status = ko.observable(12);
		this.curPage = ko.observable(1);
	}
	
	function ListVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$cont = self.$el.find('.j-questions');

		self.query = new Query({});
		self.loading = ko.observable(false);
		
		self.schoolStages = ko.observableArray();
		self.subjects = ko.observableArray();
		self.schoolStage = ko.observable();

		$('#stageSubject').stgGrdSbjSelect({
			type: 'stg_sbj',
			data: {},
			onChange: function(selection) {
				if(selection) {
					self.query.schoolStageId(selection.schoolStageId);
					self.query.subjectId(selection.subjectId);
				} else {
					self.query.schoolStageId(null);
					self.query.subjectId(null);
				}
			}
		});
		
		BasicInfoService.schoolStages().done(function(schoolStages) {
			self.schoolStages(schoolStages);
		});
		
		self.bindEvents();

		self.disposables = [];
		self.disposables.push(self.schoolStage.subscribe(function(s) {
			if(s) {
				self.query.schoolStageId(s.schoolStageId);
				self.subjects(s.subjects);
			} else {
				self.query.schoolStageId(null);
				self.subjects([]);
			}
		}));
		
		self.disposables.push(ko.computed(function() {
			self.doSearch();
		}).extend({ rateLimit: { timeout: 100, method: "notifyWhenChangesStop" } }));
	};

	ListVM.prototype.dispose = function() {
		var self = this, $el = self.$el;
		$el.off('.qlist');
		$el.off('.pages');
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	ListVM.prototype.doSearch = function() {
		var self = this;
		
		var query = ko.toJS(self.query);
		self.loading(true);
		var request = self._lastAjax = $.ajax({
			type: 'post',
			url: window.ctx + '/auth/researcher/question/imported/details.htm',
			data: _.cleanup(query),
			dataType: 'html'
		});
		request.done(function(resp, status, ajax) {
			if(ajax == self._lastAjax) {
				self.$cont.html(resp);
				DFN.init();
				self.loading(false);
			}
		}).fail(function() {
			self.loading(false);
		});
	};
	ListVM.prototype.bindEvents = function() {
		var self = this, $el = self.$el;
		
		$el.on('click.qlist', '.jQueDisable', function(evt) {
			self.doDisable(evt);
		});
		$el.on('click.qlist', '.jQueDelete', function(evt) {
			self.doDelete(evt);
		});
		$el.on('click.qlist', '.jQueEdit', function(evt) {
			self.doEdit(evt);
		});
		
		Pages.bind(self.$el, {
			ns: '.quePage',
			load: function(page) {
				self.query.curPage(page);
				self.doSearch();
			}
		});
	};
	
	ListVM.prototype.doDisable = function(evt) {
		var self = this;
		dialog.confirm($.i18n.prop('que.question.dialog.disable')).done(function(sure){
			if(sure) {
				var qid = $(evt.target).closest('.j-que-wraper').data('questionId');
				QuestionService.disable({
					questionId: qid
				}).then(function() {
					utils.Notice.alert($.i18n.prop('que.question.alert.disableSucceed'));
					self.doSearch();
				}, function(msg) {
					utils.Notice.alert(msg || $.i18n.prop('que.question.alert.disableFailed'));
				});
			}
		});
	};
	
	ListVM.prototype.doDelete = function(evt) {
		var self = this;
		var questionId = $(evt.target).closest('.j-que-wraper').data('questionId');
		dialog.confirm($.i18n.prop('que.question.dialog.delete')).done(function(sure){
			if(sure) {
				QuestionService.del({
					questionId: questionId
				}).then(function() {
					utils.Notice.alert($.i18n.prop('que.question.alert.deleteSucceed'));
					self.doSearch();
				}, function(msg) {
					utils.Notice.alert(msg || $.i18n.prop('que.question.alert.deleteFailed'));
				});
			}
		});
	};
	
	ListVM.prototype.doEdit = function(evt) {
		var self = this;
		var qid = $(evt.target).closest('.j-que-wraper').data('questionId');
		dialog.open({
			id: 'questionEditDialog',
			title: $.i18n.prop('que.question.dialog.title.edit'),
			url: window.ctx + '/auth/researcher/question/imported/edit.htm?questionId=' + qid,
			size: 'full',
			onClose: function(type) {
				if(type === 'success') {
					utils.Notice.alert($.i18n.prop('que.question.alert.editSucceed'));
					self.doSearch();
				}
			}
		});
	};
	
	ko.components.register('que-imported-list', {
	    template: require('./que-imported-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});