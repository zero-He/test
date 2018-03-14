define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var http = require('common/base/http');
	var WdatePicker = require('date');
	var koutils = require('common/knockout/koutils');
	var datepicker = require('common/knockout/bindings/datepicker');
	var userPrefService = require('core-business/service/userPrefService');
	var utils = require('utils');
	
	function Query(params) {
		var self  = this;
		self.minCreatedOn = ko.observable();
		self.maxCreatedOn = ko.observable();
		self.schoolStages = [];
		userPrefService.userPref().then(function(userPref){
			self.schoolStages = userPref.authority.schoolStages;
		});
	};
	
	Query.prototype.toJs = function(){
		var self = this;
		var result = {};
		result.minCreatedOn = self.minCreatedOn.peek() || '';
		result.maxCreatedOn = self.maxCreatedOn.peek() || '';
		result.schoolStageSubjects = [];
		_.each(self.schoolStages || [],function(n){
			_.each(n.subjects || [],function(m){
				result.schoolStageSubjects.push({schoolStageId: n.schoolStageId,subjectId: m.subjectId});
			});
		});
		return result;
	};

	function ListVM(params, element) {
		var self = this;
		self.query = new Query({});
		self.checked = ko.observable(0);//审核数量
		self.unchecked = ko.observable(0);//未审核数量
		self.disposables = [];
		self.disposables.push(ko.computed(function(){
			var minCreatedOn = self.query.minCreatedOn();
			var maxCreatedOn = self.query.maxCreatedOn();
			self.doSearch();
		}));
	};
	
	/**
	 * 搜索
	 */
	ListVM.prototype.doSearch = function() {
		var self = this;
		var query = self.query.toJs();
		if(!query.minCreatedOn){
			return false;
		}
		if(!query.maxCreatedOn){
			return false;
		}
		var req = http.post(window.ctx + '/auth/researcher/question/amount/checkAmount.htm',{dataJson: JSON.stringify(query)});
		req.then(function(data){
			self.checked(data.checked || 0);
			self.unchecked(data.unchecked || 0);
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
	
	ko.components.register('check-amount-list', {
	    template: require('./check-amount-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});