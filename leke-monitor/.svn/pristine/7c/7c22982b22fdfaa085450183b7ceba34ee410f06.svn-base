define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('underscore');
	var http = require('common/base/http');
	var LekeDate = require('common/base/date');
	var utils = require('utils');
	
	require('common/knockout/bindings/datepicker');
	require('common/knockout/component/leke-page');
	
	var FMT_TIME = 'yyyy-MM-dd HH:mm:ss';
	
	function Query(options) {
		var self = this;
		var now = LekeDate.of();
		var endTime = now.format(FMT_TIME);
		var startTime = now.add(-1, 'd').format(FMT_TIME);
		self.startTime = ko.observable(startTime);
		self.endTime = ko.observable(endTime);
	}
	
	function _toTs(str) {
		return LekeDate.parse(str, FMT_TIME).getTime();
	}
	
	Query.prototype.toJS = function() {
		var self = this;
		var result = {};
		result.startTs = _toTs(self.startTime.peek());
		result.endTs = _toTs(self.endTime.peek());
		return result;
	};
	
	var RECORD_URL = Leke.ctx + '/auth/operationStaff/jobex/record.htm';
	
	function getRecordUrl(stat, q) {
		return RECORD_URL + '?startTs=' + q.startTs + '&endTs=' + q.endTs + '&type=' + stat.type;
	}
	
	function ListVM(params) {
		var self = this;
		
		self.query = new Query();
		
		self.curPage = ko.observable(1);
		self.totalSize = ko.observable(0);
		self.pageSize = 10;
		
		self.stats = ko.observableArray();
		self.visibleStats = ko.observableArray();
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			var stats = self.stats();
			self.totalSize(stats.length);
		}));
		self.disposables.push(ko.computed(function() {
			var stats = self.stats();
			var offset = (self.curPage() - 1) * self.pageSize;
			var visible = stats.slice(offset, offset + self.pageSize);
			self.visibleStats(visible);
		}));
		
		self.doSearch();
	}
	
	ListVM.prototype.load = _.throttle(function() {
		var self = this;
		var q = self.query.toJS();
		http.get(Leke.ctx + '/auth/operationStaff/jobex/statDatas.htm', q)
			.done(function(datas) {
				var stats = datas.stats || [];
				_.each(stats, function(stat) {
					stat.recordUrl = getRecordUrl(stat, q);
				});
				self.stats(stats);
			});
	}, 300);
	
	ListVM.prototype.doSearch = function() {
		var self = this;
		self.curPage(1);
		self.load();
	};
	
	ListVM.prototype.dispose = function() {
		var self = this;
		_.each(self.disposables, function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('stat-list', {
	    template: require('./stat-list.html'),
	    viewModel: ListVM
	});
});