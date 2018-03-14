define(function(require, exposts, module) {
	var ko = require("knockout");
	var _ = require('common/base/helper');
	var dialog = require('dialog');
	
	var RecordService = require('question/service/wordImportRecordService');
	
	function Record(params) {
		var self = this;
		self.recordId = params.recordId;
		self.subjectName = ko.observable();
		self.fileName = ko.observable();
		self.total = ko.observable(0);
		self.successed = ko.observable(0);
		self.failed = ko.observable(0);
		self.status = ko.observable(0).extend({ notify: 'always' });
		self.errors = ko.observableArray();
		
		self.progress = ko.pureComputed(function() {
			var total = self.total();
			var successed = self.successed();
			var failed = self.failed();
			var result = total ? ((successed + failed) / total) : 0;
			return _.num(result * 100) + '%';
		});
		
		self.update(params);
	}
	
	Record.prototype.update = function(params) {
		var self = this;
		if(params && params.recordId) {
			_.each(['subjectName', 'fileName', 'total', 'successed', 'failed', 'status'], function(attr) {
				self[attr](params[attr]);
			});
			self.errors(params.errors || []);
		} else {
			self.status = ko.observable(3);
		}
	};
	
	function ProgressVM(params) {
		var self = this;
		self.record = new Record(params);
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			var status = self.record.status();
			if(!status) {
				_.delay(function() {
					self.refresh();
				}, 1000);
			}
		}));
	}
	
	ProgressVM.prototype.refresh = function() {
		var self = this;
		RecordService.getRecord({
			recordId: self.record.recordId
		}).done(function(datas) {
			self.record.update(datas.record);
		});
	};
	
	ProgressVM.prototype.dispose = function() {
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	
	ProgressVM.prototype.delError = function(error) {
		var self = this;
		dialog.confirm('确定删除本条错误消息？').done(function(sure) {
			if(sure) {
				self.record.errors.remove(error);
				RecordService.delError(error).done(function() {
					self.refresh();
				});
			}
		});
	};
	
	ko.components.register('que-word-record-progress', {
	    template: require('./que-word-record-progress.html'),
	    viewModel: ProgressVM
	});
});