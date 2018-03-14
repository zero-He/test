define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var http = require('common/base/http');
	var WdatePicker = require('date');
	var koutils = require('common/knockout/koutils');
	var datepicker = require('common/knockout/bindings/datepicker');
	var utils = require('utils');
	
	function Query(params) {
		var self  = this;
		self.minCreatedOn = ko.observable();
		self.maxCreatedOn = ko.observable();
	};

	function ListVM(params, element) {
		var self = this;
		self.query = new Query({});
		self.total = ko.observable({total: 0,totalFromPaper: 0,totalFromWb: 0});
		self.disposables = [];
	};
	
	/**
	 * 搜索
	 */
	ListVM.prototype.doSearch = function() {
		var self = this;
		var query = koutils.peekJS(self.query);
		if(!query.minCreatedOn){
			utils.Notice.alert('请选择开始日期！');
			return false;
		}
		if(!query.maxCreatedOn){
			utils.Notice.alert('请选择结束日期！');
			return false;
		}
		var req = http.post(window.ctx + '/auth/common/question/amount/inputAmount.htm',query);
		req.then(function(data){
			self.total(data.total || {});
		},function(msg){
			utils.Notice.alert(msg || '统计查询失败!');
		});
		
	};
	
	ListVM.prototype.dispose = function(){
		var self = this;
		_.each(self.disposables || [], function(d){
			d.dispose();
		});
		
	};
	
	ko.components.register('input-amount-list', {
	    template: require('./input-amount-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});