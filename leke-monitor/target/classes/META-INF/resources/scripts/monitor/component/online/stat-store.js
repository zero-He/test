define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('underscore');
	var http = require('common/base/http');
	var utils = require('utils');
	
	var Stat = {
		item: function(data) {
			var s = _.extend({}, data);
			['registered', 'platform', 'lesson', 'web', 'device'].forEach(function(k) {
				s[k] = s[k] || 0;
			});
			return s;
		},
		items: function(details) {
			return (details || []).map(function(d) {
				return Stat.item(d);
			});
		}
	};
	
	var URL_DIR = Leke.ctx + '/auth/common/online/monitor/';
	
	function KoStat(options) {
		var self = this;
		self.options = _.extend({}, KoStat.defaults, options);
		self.total = ko.observable(Stat.item());
		self.details = ko.observableArray();
		self._t = null;
	}
	
	KoStat.defaults = {
		url: URL_DIR + 'countryStat.htm',
		interval: 60000
	};
	
	KoStat.prototype.init = function() {
		var self = this;
		if(!self._t) {
			self.refresh();
		}
	};
	
	KoStat.prototype.loadStat = function() {
		var self = this;
		http.post(self.options.url).then(function(datas) {
			var stat = datas.stat || {};
			self.total(Stat.item(stat.total));
			self.details(Stat.items(stat.details));
		}, function(msg) {
			utils.Notice.alert(msg || '加载数据失败');
		});
	};
	
	KoStat.prototype.refresh = function() {
		var self = this;
		
		if(self._t) {
			clearTimeout(self._t);
			self._t == null;
		}
		
		self.loadStat();
		
		self._t = setTimeout(function() {
			self.refresh();
		}, self.options.interval);
	};
	
	KoStat.prototype.stop = function() {
		var self = this;
		
		if(self._t) {
			clearTimeout(self._t);
			self._t == null;
		}
	};
	
	KoStat.prototype.dispose = function() {
		var self = this;
		self.stop();
	};
	
	function StatStore() {
		var self = this;
		self._c = null;
		self._ps = {};
	}
	
	StatStore.prototype.country = function() {
		var self = this;
		return self._c || (self._c = new KoStat({ url: URL_DIR + 'countryStat.htm' }));
	};
	
	StatStore.prototype.province = function(pid) {
		var self = this;
		return self._ps[pid] || (self._ps[pid] = new KoStat({ url: URL_DIR + 'provinceStat.htm?provinceId=' + pid }));
	};
	
	StatStore.prototype.city = function(cid) { // 市级明细不做缓存
		return new KoStat({ url: URL_DIR + 'cityStat.htm?cityId=' + cid, interval: 300000 });
	};
	
	StatStore.prototype.dispose = function() {
		var self = this;
		if(self._c) {
			self._c.dispose();
			self._c = null;
		}
		_.each(self._ps, function(p) {
			p.dispose();
		});
		self._ps = {};
	};
	
	module.exports = new StatStore();
	
});