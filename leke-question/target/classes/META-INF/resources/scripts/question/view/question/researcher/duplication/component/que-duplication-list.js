define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
	var dialog = require('dialog');
	var Pages = require('common/base/pages');
	var koutils = require('common/knockout/koutils');
	var DupQuestionService = require('./DupQuestionService');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	function Query(params) {
		this.schoolStageId = ko.observable();
		this.subjectId = ko.observable();
		this.curPage = ko.observable(1);
	}

	function ListVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$cont = self.$el.find('.quesCutContent');
		self.query = new Query();
		
		self.$el.find('#stageSubject').stgGrdSbjSelect({
			type: 'stg_sbj',
			onChange: function(item) {
				self.query.schoolStageId(item.schoolStageId);
				self.query.subjectId(item.subjectId);
			}
		});

		self.bindEvents();
		self.doSearch();
	};
	
	ListVM.prototype.doSearch = function() {
		var self = this;
		self.query.curPage(1);
		self.loadContent();
	};
	
	ListVM.prototype.loadContent = function() {
		var self = this;
		var query = koutils.peekJS(self.query);
		if(!query.schoolStageId || !query.subjectId) {
			self.$cont.html('<p class="m-tips"><i></i><span>请先选择学段科目，再进行查询！</span></p>');
			return false;
		}
		
		var request = self._lastAjax = $.ajax({
			type: 'post',
			url: window.ctx + '/auth/researcher/question/duplication/details.htm',
			data: _.cleanup(query),
			dataType: 'html'
		});
		request.done(function(resp, status, ajax) {
			if(ajax == self._lastAjax) {
				self.$cont.html(resp);
			}
		});
	};
	
	ListVM.prototype.loadSimilarities = function(page, evt) {
		var self = this;
		var $c = $(evt.target).closest('.j-que-group-sims');
		var $g = $c.closest('.j-que-group');
		var gid = $g.data('dupGroupId');
		var request = $.ajax({
			type: 'post',
			url: window.ctx + '/auth/researcher/question/duplication/similarities.htm',
			data: {
				questionId: gid,
				curPage: page
			},
			dataType: 'html'
		});
		request.done(function(resp, status, ajax) {
			$c.html(resp);
		});
	};
	
	ListVM.prototype.bindEvents = function() {
		var self = this, $el = self.$el;

		Pages.bind(self.$el, {
			ns: '.quePage',
			load: function(page) {
				self.query.curPage(page);
				self.loadContent();
			}
		});
		
		Pages.bind(self.$el, {
			ns: '.j-que-group-sims',
			load: function(page, evt) {
				self.loadSimilarities(page, evt);
			}
		});
		
		$el.on('click.tqlist', '.j-btn-not-dup', function(evt) {
			var $a = $(evt.target);
			var qid = $a.data('questionId');
			DupQuestionService.notDup({
				questionId: qid
			}).then(function() {
				utils.Notice.alert('习题设置为非重复操作成功！');
				self.loadContent();
			}, function(msg) {
				utils.Notice.alert(msg || '习题设置为非重复操作失败！');
			});
		});
		
		$el.on('click.tqlist', '.j-btn-disable', function(evt) {
			var $a = $(evt.target);
			var qid = $a.data('questionId');
			dialog.confirm('确定禁用该习题吗？').done(function(sure) {
				if(sure) {
					DupQuestionService.disable({
						questionId: qid
					}).then(function() {
						utils.Notice.alert('习题禁用成功！');
						self.loadContent();
					}, function(msg) {
						utils.Notice.alert(msg || '习题禁用失败！');
					});
				}
			});
		});
		
		$el.on('click.tqlist', '.j-btn-del', function(evt) {
			var $a = $(evt.target);
			var qid = $a.data('questionId');
			dialog.confirm('确定删除该习题吗？').done(function(sure) {
				if(sure) {
					DupQuestionService.del({
						questionId: qid
					}).then(function() {
						utils.Notice.alert('习题删除成功！');
						self.loadContent();
					}, function(msg) {
						utils.Notice.alert(msg || '习题删除失败！');
					});
				}
			});
		});
		
		$el.on('click.tqlist', '.j-btn-disable-others', function(evt) {
			var $a = $(evt.target);
			var $g = $a.closest('.j-que-group');
			var qid = $a.data('questionId');
			var gid = $g.data('dupGroupId');
			dialog.confirm('禁用其它将禁用该组其它习题，而仅保留本题。确定继续吗？').done(function(sure) {
				if(sure) {
					DupQuestionService.disableOthers({
						dupGroupId: gid,
						questionId: qid
					}).then(function() {
						utils.Notice.alert('禁用其它操作成功！');
						self.loadContent();
					}, function(msg) {
						utils.Notice.alert(msg || '禁用其它操作失败！');
					});
				}
			});
		});
	};
	
	ListVM.prototype.dispose = function() {
		var self = this, $el = self.$el;
		$el.off('.tqlist');
		$el.off('.pages');
	};
	
	ko.components.register('que-duplication-list', {
	    template: require('./que-duplication-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});