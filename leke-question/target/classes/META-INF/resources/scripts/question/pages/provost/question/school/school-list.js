define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var http = require('common/base/http');
	var dialog = require('dialog');
	var utils = require('utils');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var RepoService = require('repository/service/RepoService');
	var DFN = require('homework/sheet.dfn');
	var Pages = require('common/base/pages');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var FQ_KS = ['schoolStageId', 'subjectId', 'questionTypeId'];
	
	function Query(options) {
		var ops = _.extend({}, options);
		var self = this;
		self.content = ko.observable(ops.content || '');
		_.each(FQ_KS, function(k) {
			self[k] = ko.observable(ops[k]);
		});
		self.curPage = ko.observable(ops.curPage || 1);
	}
	
	Query.prototype.toJS = function() {
		var self = this;
		var result = {};
		result.content = self.content.peek();
		_.each(FQ_KS, function(k) {
			var val = self[k].peek();
			if(val != null && val != '') {
				result[k] = val;
			}
		});
		result.curPage = self.curPage.peek();
		return result;
	};
	
	function SearchVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$qs = self.$el.find('.j-questions');
		
		self.query = new Query(params);
		self.curPage = ko.observable(1);
		self.types = ko.observableArray();
		
		self.$el.find('[name=subject]').stgGrdSbjSelect({
			data: {
				schoolStageId: null,
				subjectId: null
			},
			onChange: function(item) {
				self.query.schoolStageId(item.schoolStageId);
				self.query.subjectId(item.subjectId);
			}
		});
		
		self.bindEvents();
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self.loadTypes();
		}));
		
		self.load();
	}
	
	SearchVM.prototype.bindEvents = function() {
		var self = this, $el = self.$el;

		Pages.bind(self.$el, {
			ns: '.quePage',
			load: function(page) {
				self.query.curPage(page);
				self.load();
			}
		});
		
		self.$el.on('click.qlist', '.j-remove', function(evt) {
			self.doRemove(evt);
		});
		
		self.$el.on('click.qlist', '.j-praise', function(evt) {
			self.doPraise(evt);
		});
	};
	
	SearchVM.prototype.doPraise = function(evt) {
		var self = this;
		var $q = $(evt.target).closest('.item');
		var qid = $q.data('questionId');
		RepoService.doPraiseQuestion(qid).then(function() {
			utils.Notice.alert($.i18n.prop('que.question.alert.praiseSucceed'));
			var $c = $q.find('.j-praise-count');
			var old = parseInt($c.text() || 0);
			$c.text(old + 1);
		}, function(msg) {
			utils.Notice.alert(msg || $.i18n.prop('que.question.alert.praiseFailed'));
		});
	};
	
	SearchVM.prototype.doRemove = function(evt) {
		var self = this;
		dialog.confirm('确定从学校习题库中移除该习题？').done(function(sure){
			if(sure) {
				var qid = $(evt.target).closest('.item').data('questionId');
				http.postJSON(Leke.ctx + '/auth/schoolResearcher/question/school/doRemove.htm', [qid]).then(function() {
					utils.Notice.alert('习题移除成功!');
					self.load();
				}, function(msg) {
					utils.Notice.alert(msg || '习题移除失败!');
				});
			}
		});
	};
	
	SearchVM.prototype.loadTypes = function() {
		var self = this;
		var sid = self.query.subjectId();
		var req = sid ? BasicInfoService.questionTypes(sid) : BasicInfoService.questionTypes();
		req.then(function(types) {
			self.types(types || []);
		}, function() {
			self.types([]);
		});
	};
	
	SearchVM.prototype.doSearch = function() {
		var self = this;
		self.query.curPage(1);
		self.load();
	};
	
	/*
	 * 实际查询操作
	 */
	SearchVM.prototype.load = _.throttle(function() {
		var self = this;
		var query = self.query.toJS();
		var req = self._lastAjax = $.ajax({
			type: 'post',
			url: Leke.ctx + '/auth/provost/question/school/details.htm',
			data: query,
			dataType: 'html'
		});
		req.then(function(resp, status, ajax) {
			if(ajax == self._lastAjax) {
				self.$qs.html(resp);
				self.scrollToTop();
				DFN.init();
			}
		}, function() {
			self.$qs.html('<div class="m-tiptext m-tiptext-err"><i class="iconfont icon">&#xf0155;</i> <span class="msg">习题列表加载失败！</span></div>');
			self.scrollToTop();
		});
	}, 300);
	
	SearchVM.prototype.scrollToTop = function() {
		var self = this;
		self.$bd = self.$bd || $('html,body');
		self.$bd.scrollTop(0);
	};
	
	SearchVM.prototype.dispose = function() {
		var self = this;
		_.each(self.disposables, function(d) {
			d.dispose();
		});
		self.$el.off('.quePage');
		self.$el.off('.qlist');
	};
	
	ko.components.register('school-list', {
	    template: require('./school-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new SearchVM(params, componentInfo.element);
	    	}
	    }
	});
});