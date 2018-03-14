define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('underscore');
	var http = require('common/base/http');
	var LekeDate = require('common/base/date');
	var utils = require('utils');
	
	require('common/knockout/bindings/datepicker');
	require('common/knockout/component/leke-page');
	
	var TS_A_WEEK_AGO = LekeDate.of().truncate('d').add(-7, 'd').getTime();
	var TS_OF_DAY = 86400000;
	var FMT_DATE = 'yyyy-MM-dd';
	
	function Query(options) {
		var self = this;
		var today = LekeDate.of().format(FMT_DATE);
		self.date = ko.observable(today);
		self.sqlId = ko.observable();
		self.serverName = ko.observable();
	}
	
	function _toDay(str) {
		return LekeDate.parse(str, FMT_DATE).format('yyyyMMdd');
	}
	
	Query.prototype.toJS = function() {
		var self = this;
		var result = {};
		result.day = _toDay(self.date.peek());
		var svr = self.serverName.peek();
		if(svr) {
			result.serverName = svr;
		}
		var sid = self.sqlId.peek();
		if(sid) {
			result.sqlId = sid;
		}
		return result;
	};
	
	var SORT_THS = [{
		prop: 'avgCostTime',
		name: '平均时长'
	}, {
		prop: 'maxCostTime',
		name: '最大时长'
	}, {
		prop: 'count',
		name: '访问次数'
	}];
	
	var RECORD_URL = Leke.ctx + '/auth/operationStaff/sql/record.htm';
	var INFO_URL = Leke.ctx + '/auth/operationStaff/sql/info.htm';
	
	function getRecordUrl(r) {
		var day = r.day + '';
		var startTs = LekeDate.parse(r.day + '', 'yyyyMMdd').getTime();
		if(startTs < TS_A_WEEK_AGO) {
			startTs = TS_A_WEEK_AGO;
		}
		var endTs = startTs + TS_OF_DAY;
		return RECORD_URL + '?startTs=' + startTs + '&endTs=' + endTs + '&hash=' + r.hash;
	}
	
	function getInfoUrl(r) {
		return INFO_URL + '?hash=' + r.hash;
	}
	
	function ListVM() {
		var self = this;
		
		self.query = new Query();
		
		self.orderBy = ko.observable('avgCostTime');
		self.asc = ko.observable(false);
		self.curPage = ko.observable(1);
		self.totalSize = ko.observable(0);
		self.pageSize = 20;
		self.domains = ko.observableArray();
		self.sortThs = SORT_THS;
		
		self.stats = ko.observableArray();
		self.sortedStats = ko.observableArray();
		self.visibleStats = ko.observableArray();
		
		self.loadDomains();
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			var stats = self.stats();
			self.totalSize(stats.length);
		}));
		self.disposables.push(ko.computed(function() {
			var stats = self.stats();
			var orderBy = self.orderBy();
			var asc = self.asc();
			var sorted = _.sortBy(stats, function(r) {
				var val = r[orderBy];
				return asc ? val : (- val);
			});
			self.sortedStats(sorted);
		}));
		self.disposables.push(ko.computed(function() {
			var stats = self.sortedStats();
			var offset = (self.curPage() - 1) * self.pageSize;
			var visible = _.map(stats.slice(offset, offset + self.pageSize), function(r) {
				var vr = _.extend({}, r);
				vr.avgCostTime = (r.avgCostTime || 0).toFixed(1);
				vr.name = ko.observable(r.name);
				vr.recordUrl = getRecordUrl(r);
				vr.infoUrl = getInfoUrl(r);
				return vr;
			});
			self.visibleStats(visible);
		}));
		
		self.doSearch();
	}
	
	ListVM.prototype.loadDomains = function() {
		var self = this;
		http.get(Leke.ctx + '/auth/operationStaff/domain/datas.htm').done(function(datas) {
			self.domains(datas.domains || []);
		});
	};
	
	ListVM.prototype.load = _.throttle(function() {
		var self = this;
		http.get(Leke.ctx + '/auth/operationStaff/sql/dailyDatas.htm', self.query.toJS())
			.done(function(datas) {
				var stats = datas.stats || [];
				_.each(stats, function(stat) {
					stat.avgCostTime = stat.totalCostTime / stat.count;
				});
				self.stats(stats);
			});
	});
	
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
	
	ko.components.register('daily-list', {
	    template: require('./daily-list.html'),
	    viewModel: ListVM
	});
});