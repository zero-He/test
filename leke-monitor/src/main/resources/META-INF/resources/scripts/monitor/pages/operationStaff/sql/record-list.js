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
		var ops = _.extend({}, options);
		var startTime = LekeDate.of(ops.startTs).format(FMT_TIME);
		self.startTime = ko.observable(startTime);
		var endTime = LekeDate.of(ops.endTs).format(FMT_TIME);
		self.endTime = ko.observable(endTime);
		self.hash = ops.hash;
	}
	
	function _toTs(str) {
		return LekeDate.parse(str, FMT_TIME).getTime();
	}
	
	Query.prototype.toJS = function() {
		var self = this;
		var result = {};
		result.startTs = _toTs(self.startTime.peek());
		result.endTs = _toTs(self.endTime.peek());
		result.hash = self.hash;
		return result;
	};
	
	function ListVM(params) {
		var self = this;
		
		self.query = new Query(params.query);
		
		self.curPage = ko.observable(1);
		self.totalSize = ko.observable(0);
		self.pageSize = 10;
		
		self.sqlId = ko.observable();
		self.className = ko.observable();
		self.avgCostTime = ko.observable();
		self.maxCostTime = ko.observable();
		self.count = ko.observable();
		self.records = ko.observableArray();
		
		self.inited = false;
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self.curPage();
			if(self.inited) {
				self.loadRecords();
			}
		}));
		
		self.doSearch();
		self.inited = true;
	}
	
	var INFO_URL = Leke.ctx + '/auth/operationStaff/sql/info.htm';
	function getInfoUrl(r) {
		return INFO_URL + '?hash=' + r.hash;
	}
	
	ListVM.prototype.loadRecords = _.throttle(function() {
		var self = this;
		var query = self.query.toJS();
		query.curPage = self.curPage.peek();
		query.pageSize = self.pageSize;
		http.get(Leke.ctx + '/auth/operationStaff/sql/recordDatas.htm', query)
			.then(function(datas) {
				var records = datas.records || [];
				_.each(records, function(r) {
					r.timeStr = LekeDate.of(r.ts).format('yyyy-MM-dd HH:mm:ss');
					r.infoUrl = getInfoUrl(r);
				})
				self.records(records);
				self.totalSize(datas.page.totalSize || 0);
			}, function(msg) {
				utils.Notice.alert('记录列表加载失败!');
				self.records([]);
				self.totalSize(0);
			});
	}, 300);
	
	ListVM.prototype.loadStat = _.throttle(function() {
		var self = this;
		var query = self.query.toJS();
		http.get(Leke.ctx + '/auth/operationStaff/sql/recordStat.htm', query)
			.then(function(datas) {
				var stat = datas.stat || {};
				var info = datas.info || {};
				var avg = stat.totalCostTime / stat.count;
				self.sqlId(info.sqlId);
				self.className(info.className);
				self.avgCostTime(avg.toFixed(1));
				self.maxCostTime(stat.maxCostTime);
				self.count(stat.count);
			}, function(msg) {
				utils.Notice.alert('统计信息加载失败!');
				self.sqlId('');
				self.className('');
				self.avgCostTime('');
				self.maxCostTime('');
				self.count('');
			});
	}, 300);
	
	ListVM.prototype.doSearch = function() {
		var self = this;
		self.curPage(1);
		self.loadStat();
		self.loadRecords();
	};
	
	ListVM.prototype.dispose = function() {
		var self = this;
		_.each(self.disposables, function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('record-list', {
	    template: require('./record-list.html'),
	    viewModel: ListVM
	});
});