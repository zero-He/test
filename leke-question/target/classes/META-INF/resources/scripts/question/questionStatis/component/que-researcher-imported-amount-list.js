define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var WdatePicker = require('date');
	var koutils = require('common/knockout/koutils');
	var datepicker = require('common/knockout/bindings/datepicker');
	
	function Query(params) {
		this.startDate = ko.observable();
		this.endDate = ko.observable();
		this.userName = ko.observable();
	};

	function ListVM(params, element) {
		var self = this;
		self.query = new Query({});
		var data = self.data= _.extend({}, params.data);
		self.inputStatisList = ko.observableArray();
		self.importedTotalAmount = ko.observable(0);
		
		self.doSearch();
	};
	/**
	 * 搜索
	 */
	ListVM.prototype.doSearch = function() {
		var self = this;
		var query = koutils.peekJS(self.query);
		$.ajax({
			type : 'post',
			url : ctx + '/auth/admin/questionStatis/ajax/findResearcherImportedList.htm',
			data : query,
			dataType : 'json',
			success : function(json) {
				var listjson = json.datas;
				var isl =  listjson.inputStatisList || [];
				self.inputStatisList(isl);
				self.query.startDate(listjson.startDate || '');
				self.query.endDate(listjson.endDate || '');
				
				var ia = 0;
				for(var i=0; i < isl.length; i++){
					ia += isl[i].importedAmount;
				}
				self.importedTotalAmount(ia);
			}
		});
	};
	/**
	 * 查看详细
	 */
	ListVM.prototype.viewUserImportedAmount = function(data) {
		var self = this;
		window.location.href = window.ctx + '/auth/admin/questionStatis/researcher/importedAmount.htm?userId=' + data.userId + '&userName=' + data.userName + '&startDate=' + self.query.startDate() + '&endDate=' + self.query.endDate();
	};
	
	ko.components.register('que-researcher-imported-amount-list', {
	    template: require('./que-researcher-imported-amount-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});