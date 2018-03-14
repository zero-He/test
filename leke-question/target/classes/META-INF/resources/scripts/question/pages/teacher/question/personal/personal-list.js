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
	var KO_QUE_CART = require('question/quecart');
	var RepoQS = require('repository/service/RepoQueryString');
	
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	require('question/component/que-cart');
	
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
		
		self.$el.on('click.qlist', '.j-disable', function(evt) {
			self.doDisable(evt);
		});
		
		self.$el.on('click.qlist', '.j-edit', function(evt) {
			self.openEditDialog(evt);
		});
		
		self.$el.on('click.qlist', '.j-praise', function(evt) {
			self.doPraise(evt);
		});
		
		self.$el.on('click.qlist', '.j-add-quecart', function(evt) {
			self.doAddQueCart(evt);
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
	
	var URL_EDIT = Leke.ctx + '/auth/teacher/question/questionEdit.htm?';
	
	SearchVM.prototype.openEditDialog = function(evt) {
		var self = this;
		var qid = $(evt.target).closest('.item').data('questionId');
		var url = URL_EDIT + RepoQS.stringify({
			curScope: 1,
			questionId: qid
		});
		dialog.open({
			id: 'questionEditDialog',
			title: $.i18n.prop('que.question.dialog.title.edit'),
			url: url,
			size: 'full',
			onClose: function(type) {
				if(type === 'success') {
					utils.Notice.alert($.i18n.prop('que.question.alert.editSucceed'));
					self.load();
				}
			}
		});
	};
	
	SearchVM.prototype.doDisable = function(evt) {
		var self = this;
		dialog.confirm($.i18n.prop('que.question.dialog.disable')).done(function(sure){
			if(sure) {
				var qid = $(evt.target).closest('.item').data('questionId');
				http.post(Leke.ctx + '/auth/teacher/question/doDisable.htm', {
					questionId: qid
				}).then(function() {
					utils.Notice.alert($.i18n.prop('que.question.alert.disableSucceed'));
					self.load();
				}, function(msg) {
					utils.Notice.alert(msg || $.i18n.prop('que.question.alert.disableFailed'));
				});
			}
		});
	};
	
	SearchVM.prototype.doAddQueCart = function(evt) {
		var self = this;
		/*var qid = $(evt.target).closest('.item').data('questionId');
		KO_QUE_CART.add(qid);*/
		
		var que = {};
		que.qid =  $(evt.target).closest('.item').data('questionId');
		que.qtypeid = $(evt.target).closest('.item').data('questionTypeId');
		var qtypename = _getTypeName(self.types(), que.qtypeid);
		if (qtypename != -1){
			que.qtypename = qtypename;
		}
		KO_QUE_CART.addType(que);
		require.async(['common/ui/ui-fly/ui-fly'], function(f) {
			var ops = {el:'.c-exercisebasket .basket .con'};
			f.doFly(evt, ops);
		});
	};
	
	function _getTypeName(array, item) {
		if(!array || !array.length) {
			return -1;
		}
		for (var i = 0, len = array.length; i < len; i++) { 
			if (array[i].questionTypeId == item) {
				return array[i].questionTypeName;
			}
		}
		return -1;
	}
	
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
			url: Leke.ctx + '/auth/teacher/question/personal/details.htm',
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
	
	ko.components.register('personal-list', {
	    template: require('./personal-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new SearchVM(params, componentInfo.element);
	    	}
	    }
	});
});