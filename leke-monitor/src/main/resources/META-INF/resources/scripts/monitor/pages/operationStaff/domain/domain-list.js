define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('underscore');
	var http = require('common/base/http');
	var utils = require('utils');
	
	function LekeDomain(options) {
		var self = this;
		self.status = ko.observable(options.status);
		
		var data = _.extend({}, options.data);
		self.subdomain = ko.observable(data.subdomain);
		self.name = ko.observable(data.name);
	}
	
	function ListVM() {
		var self = this;
		
		self.domains = ko.observableArray();
		self.disposables = [];
		
		self.load();
	}
	
	ListVM.prototype.load = function() {
		var self = this;
		http.get(Leke.ctx + '/auth/operationStaff/domain/datas.htm').done(function(datas) {
			var domains = _.map(datas.domains || [], function(d) {
				return new LekeDomain({
					status: 'saved',
					data: d
				});
			});
			self.domains(domains);
		});
	};
	
	ListVM.prototype.remove = function(d) {
		var self = this;
		var status = d.status();
		if(status === 'init') {
			self.domains.remove(d);
		} else {
			http.post(Leke.ctx + '/auth/operationStaff/domain/remove.htm', {
				subdomain: d.subdomain()
			}).then(function() {
				utils.Notice.alert('移除操作成功!');
				self.domains.remove(d);
			}, function(msg) {
				utils.Notice.alert(msg || '移除操作失败!');
			});
		}
	};
	
	ListVM.prototype.add = function() {
		var self = this;
		var d = new LekeDomain({
			status: 'init'
		});
		self.domains.push(d);
	};
	
	ListVM.prototype.save = function(d) {
		var self = this;
		http.post(Leke.ctx + '/auth/operationStaff/domain/save.htm', {
			subdomain: d.subdomain(),
			name: d.name()
		}).then(function() {
			d.status('saved');
		}, function(msg) {
			utils.Notice.alert(msg || '保存失败!');
		});
	};
	
	ListVM.prototype.dispose = function() {
		var self = this;
		_.each(self.disposables, function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('domain-list', {
	    template: require('./domain-list.html'),
	    viewModel: ListVM
	});
});