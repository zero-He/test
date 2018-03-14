define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
	var dialog = require('dialog');
	var DFN = require('homework/sheet.dfn');
	var Pages = require('common/base/pages');
	var koutils = require('common/knockout/koutils');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var LeagueService = require('question/service/leagueService');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	function Query(params) {
		this.userId = ko.observable();
		this.schoolId = params.schoolId;
		this.subjectId = ko.observable();
		this.schoolStageId = ko.observable();
		this.questionTypeId = ko.observable();
		this.questionId = ko.observable();
		this.content = ko.observableArray();
		this.leagueQuestionState = ko.observable(params.leagueQuestionState || 1);
		this.curPage = ko.observable(1);
	};

	function ListVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$cont = self.$el.find('.quesStyleCut');
		self.query = new Query({
			schoolId: params.schoolId
		});
		var data = self.data= _.extend({}, params.data);

		self.questionTypes = ko.observableArray();
		self.questions = ko.observableArray();
		self.questionId = ko.observableArray();
		self.content = ko.observableArray();
		
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
		
		self.disposables = [];
		self.bindEvents();
		
		self.disposables.push(ko.computed(function() {
			var curPage = self.query.curPage();
			self.loadQuestionTypes();
			self.doSearch();
		}));
	};

	
	ListVM.prototype.dispose = function() {
		var self = this, $el = self.$el;
		$el.off('.tqlist');
		$el.off('.pages');
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};

	/*
	 * 加载题型
	 * */
	ListVM.prototype.loadQuestionTypes = function() {
		var self = this;
		BasicInfoService.questionTypes(self.query.subjectId()).done(function(types) {
			self.questionTypes(types);
		});
	};
	/*
	 * 搜索
	 * */
	ListVM.prototype.doSearch = function() {
		var self = this;
		var query = koutils.peekJS(self.query);
		var request = self._lastAjax = $.ajax({
			type: 'post',
			url: window.ctx + '/auth/league/question/checkDetail.htm',
			data: _.cleanup(query),
			dataType: 'html'
		});
		request.done(function(resp, status, ajax) {
			if(ajax == self._lastAjax) {
				self.$cont.html(resp);
				DFN.init();
			}
		});
	};
	/**
	 * 绑定事件
	 */
	ListVM.prototype.bindEvents = function() {
		var self = this, $el = self.$el;

		Pages.bind(self.$el, {
			ns: '.quePage',
			load: function(page) {
				self.query.curPage(page);
				self.doSearch();
			}
		});
		
		$el.on('click.tqlist', '.jCheckPass', function() {
			self.doChecked();
		});
		
		$el.on('click.tqlist', '.jSelectAll', function() {
			var $c = $(this), checked = $c.prop('checked');
			var qCheckBox = self.$cont.find('.q-checkbox :checkbox');
			checked ? qCheckBox.prop('checked', true) : qCheckBox.prop('checked', false);
		});
		
		$el.on('click.tqlist', '.jCheckBtn', function() {
			var $c = $(this), state = $c.data('checkState');
			var lqId = $c.closest('.queWrapper').data('leagueQuestionId');
			var actionName = $.i18n.prop('que.question.dialog.checkPass');
			var nUnCheckedState = 3;
			if(state == nUnCheckedState) {
				actionName = $.i18n.prop('que.question.dialog.checkReturn');
			}
			
			dialog.confirm($.i18n.prop('que.question.dialog.sureTo') + actionName +'?').done(function(sure){
				if(sure) {
					LeagueService.checkLeagueQuestion({
						leagueQuestionId: lqId,
						leagueQuestionState: state
					}).then(function() {
						utils.Notice.alert(actionName + $.i18n.prop('que.question.alert.success'));
						self.doSearch();
					}, function(msg) {
						utils.Notice.alert(msg ||actionName + $.i18n.prop('que.question.alert.fail'));
					});
				}
			});
		});
		
		$el.on('click.tqlist', '.jDisable', function() {
			var $c = $(this);
			var lqId = $c.closest('.queWrapper').data('leagueQuestionId');
			dialog.confirm($.i18n.prop('que.question.dialog.disable')).done(function(sure){
				if(sure) {
					LeagueService.disable({
						leagueQuestionId: lqId
					}).then(function() {
						utils.Notice.alert($.i18n.prop('que.question.alert.disableSucceed'));
						self.doSearch();
					}, function(msg) {
						utils.Notice.alert(msg || $.i18n.prop('que.question.alert.disableFailed'));
					});
				}
			});
		})
	};
	/**
	 * 审核通过事件
	 */
	ListVM.prototype.doChecked = function() {
		var self = this;
		var oChecked = self.$cont.find('.q-checkbox :checked');
		if(!oChecked.length) {
			utils.Notice.alert($.i18n.prop('que.question.alert.noShareQuestion'));
			return false;
		}
		var leagueQuestionIds = [];
		oChecked.each(function() {
			var $p = $(this), pId = $p.closest('.queWrapper').data('leagueQuestionId');
			leagueQuestionIds.push(pId);
		});
		var jsonIds = JSON.stringify(leagueQuestionIds);
		dialog.confirm($.i18n.prop('que.question.alert.sureToAllCheckPass')).done(function(sure){
			if(sure) {
				LeagueService.checkLeagueQuestions({
					jsonIds: jsonIds,
					leagueQuestionState: 2
				}).then(function() {
					utils.Notice.alert($.i18n.prop('que.question.alert.allCheckPassSucceed'));
					self.doSearch();
				}, function(msg) {
					utils.Notice.alert(msg || $.i18n.prop('que.question.alert.allCheckPassFailed'));
				});
			}
		});
	};
	
	ko.components.register('que-check-list', {
	    template: require('./que-check-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});