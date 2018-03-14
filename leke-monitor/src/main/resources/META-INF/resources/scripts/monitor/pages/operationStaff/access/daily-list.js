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
		return result;
	};
	
	var SORT_THS = [{
		prop: 'avgExecuteTime',
		name: '平均时长'
	}, {
		prop: 'maxExecuteTime',
		name: '最大时长'
	}, {
		prop: 'count',
		name: '访问次数'
	}];
	
	var RECORD_URL = Leke.ctx + '/auth/operationStaff/access/record.htm';
	
	function getRecordUrl(r) {
		var day = r.day + '';
		var startTs = LekeDate.parse(r.day + '', 'yyyyMMdd').getTime();
		if(startTs < TS_A_WEEK_AGO) {
			startTs = TS_A_WEEK_AGO;
		}
		var endTs = startTs + TS_OF_DAY;
		return RECORD_URL + '?startTs=' + startTs + '&endTs=' + endTs + '&serverName=' + r.serverName + '&servletPath=' + r.servletPath;
	}
	
	function ListVM() {
		var self = this;
		
		self.query = new Query();
		
		self.orderBy = ko.observable('avgExecuteTime');
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
				vr.avgExecuteTime = (r.avgExecuteTime || 0).toFixed(1);
				vr.name = ko.observable(r.name);
				vr.editing = ko.observable(false);
				vr.recordUrl = getRecordUrl(r);
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
	
	ListVM.prototype.load = function() {
		var self = this;
		http.get(Leke.ctx + '/auth/operationStaff/access/dailyDatas.htm', self.query.toJS())
			.done(function(datas) {
				var stats = datas.stats || [];
				_.each(stats, function(stat) {
					stat.avgExecuteTime = stat.totalExecuteTime / stat.count;
				});
				self.stats(stats);
			});
	};
	
	ListVM.prototype.doSearch = function() {
		var self = this;
		self.curPage(1);
		self.load();
	};
	
	ListVM.prototype.saveName = function(data) {
		var self = this;
		http.post(Leke.ctx + '/auth/operationStaff/access/saveUrlName.htm', {
			serverName: data.serverName,
			servletPath: data.servletPath,
			name: data.name()
		}).then(function() {
			utils.Notice.alert('功能名称保存成功');
			data.editing(false);
		}, function(msg) {
			utils.Notice.alert(msg || '功能名称保存失败');
		});
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