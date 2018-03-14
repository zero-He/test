define(function(require, exposts, module) {
	var ko = require('knockout');
	var _ = require('common/base/helper');
	
	var RecordService = require('question/service/wordImportRecordService');
	
	require('common/knockout/component/leke-page');
	require('./que-word-form');
	require('./que-word-record-progress');
	
	function PageVM(params) {
		var self = this;
		self.ticket = params.ticket;
		self.records = ko.observableArray();
		self.curPage = ko.observable(1);
		self.totalSize = ko.observable(0);
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			var curPage = self.curPage();
			self.loadRecords();
		}));
	}
	
	PageVM.prototype.loadRecords = function() {
		var self = this;
		var curPage = self.curPage.peek();
		RecordService.myRecords({
			curPage: curPage
		}).done(function(datas) {
			var records = datas.records || [];
			var page = _.extend({
				curPage: 1,
				totalSize: 0
			}, datas.page);
			self.records(records);
			self.totalSize(page.totalSize);
		}).fail(function() {
			self.records([]);
			self.curPage(1);
			self.totalSize(0);
		});
	};
	
	PageVM.prototype.dispose = function() {
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('que-word-page', {
	    template: require('./que-word-page.html'),
	    viewModel: PageVM
	});
});