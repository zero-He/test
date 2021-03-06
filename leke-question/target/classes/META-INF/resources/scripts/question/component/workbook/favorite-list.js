define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var utils = require('utils');
	var http = require('common/base/http');
	var RepoService = require('repository/service/RepoService');

	require('common/knockout/component/leke-page');
	
	function Query(options) {
		var ops = _.extend({}, options);
		var self = this;
		self.content = ko.observable(ops.content || '');
		self.curPage = ko.observable(ops.curPage || 1);
	}
	
	Query.prototype.toJS = function() {
		var self = this;
		var result = {};
		result.content = self.content.peek();
		result.curPage = self.curPage.peek();
		return result;
	};
	
	function SearchVM(params, element) {
		var self = this;
		
		self.options = _.extend({}, SearchVM.defaults, params);
		self.query = new Query(params);
		self.records = ko.observableArray();
		
		self.totalSize = ko.observable(0);
		self.disposables = [];
		
		self.disposables.push(ko.computed(function() {
			var curPage = self.query.curPage();
			self.load();
		}));
	}
	
	SearchVM.prototype.doUnfavorite = function(record) {
		var self = this;
		RepoService.doUnFavoriteWorkbook(record.resId).then(function(data) {
			utils.Notice.alert('取消收藏操作成功!');
			self.load();
		}, function(msg) {
			utils.Notice.alert(msg || '取消收藏操作失败!');
		});
	};
	
	SearchVM.prototype.doPraise = function(record) {
		var self = this;
		RepoService.doPraiseWorkbook(record.resId).then(function() {
			utils.Notice.alert('点赞操作成功!');
			record.praiseCount(record.praiseCount() + 1);
		}, function(msg) {
			utils.Notice.alert(msg || '点赞操作失败!');
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
		http.post(self.options.urlFavoriteDetails, query).then(function(datas) {
			var records = RepoService.processRecords(datas.records);
			self.records(records);
			self.totalSize(datas.page.totalSize || 0);
		}, function() {
			utils.Notice.alert('习题册列表加载失败!');
			self.records([]);
		});
	}, 300);
	
	SearchVM.prototype.dispose = function() {
		var self = this;
		_.each(self.disposables, function(d) {
			d.dispose();
		});
	};
	
	var DIR = Leke.ctx + '/auth/common/workbook/';
	
	SearchVM.defaults = {
		urlFavoriteDetails: DIR + 'favorite/details.htm',
		urlPersonalList: DIR + 'personal/list.htm?spm=101026'
	};
	
	ko.components.register('favorite-list', {
	    template: require('./favorite-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new SearchVM(params, componentInfo.element);
	    	}
	    }
	});
});