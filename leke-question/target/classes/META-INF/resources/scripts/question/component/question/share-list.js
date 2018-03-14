define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var http = require('common/base/http');
	var utils = require('utils');
	var DM = Leke.domain;
	var DFN = require('homework/sheet.dfn');
	var Pages = require('common/base/pages');
	var dialog = require('dialog');
	
	function Query(options) {
		var self = this;
		var ops = _.extend({}, options);
		self.shareScope = ko.observable();//分享范围
		self.curPage = ko.observable(ops.curPage || 1);
	}
	
	Query.prototype.toJS = function() {
		var self = this;
		var result = {};
		result.shareScope = self.shareScope.peek();
		result.curPage = self.curPage.peek();
		return result;
	};
	
	function SearchVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$qs = self.$el.find('.j-questions');
		
		self.query = new Query(params);
		self.shareScopes = ko.observableArray([{name: '学校',value: 2},{name: '乐课',value: 6}]);
		self.curPage = ko.observable(1);

		self.disposables = [];

		self.disposables.push(ko.computed(function() {
			var val = self.query.shareScope();
			var curPage = self.query.curPage();
			self.load();
		}));
		self.bindEvents();
	};
	
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
			self.doRemoveShareLog(evt);
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
			url: Leke.ctx + '/auth/common/question/share/details.htm',
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
	
	SearchVM.prototype.doRemoveShareLog = function(evt){
		var self = this;
		var shareLogId = $(evt.target).closest('.item').data('shareLogId');
		dialog.confirm('确定要移除吗？').done(function(sure){
			if(sure){
				var rq = http.post('/auth/common/question/removeShareLog.htm?shareLogId=' + shareLogId);
				rq.then(function(data){
					utils.Notice.alert('移除成功!');
					self.load();
				},function(msg){
					utils.Notice.alert(msg || '移除失败!');
				});
			}
		});
	};
	
	SearchVM.prototype.dispose = function() {
		var self = this;
		_.each(self.disposables, function(d) {
			d.dispose();
		});
		self.$el.off('.quePage');
	};
	
	ko.components.register('share-list', {
	    template: require('./share-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new SearchVM(params, componentInfo.element);
	    	}
	    }
	});
});