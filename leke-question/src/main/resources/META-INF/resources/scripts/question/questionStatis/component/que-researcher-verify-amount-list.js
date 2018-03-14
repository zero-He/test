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
		self.totalAmount = ko.observable(0);
		self.proofreadingTotalAmount = ko.observable(0);
		self.unProofreadingTotalAmount = ko.observable(0);
		
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
			url : ctx + '/auth/admin/questionStatis/ajax/findProofreadingAmountList.htm',
			data : query,
			dataType : 'json',
			success : function(json) {
				var listjson = json.datas;
				var isl =  listjson.inputStatisList || [];
				self.inputStatisList(isl);
				self.query.startDate(listjson.startDate || '');
				self.query.endDate(listjson.endDate || '');
				
				var ta = 0;
				var pa = 0;
				var ua = 0;
				for(var i=0; i < isl.length; i++){
					ta += isl[i].amount;
					pa += isl[i].proofreadingAmount;
					ua += isl[i].unProofreadingAmount;
				}
				self.totalAmount(ta);
				self.proofreadingTotalAmount(pa);
				self.unProofreadingTotalAmount(ua);
			}
		});
	};
	/**
	 * 查看详细
	 */
	ListVM.prototype.viewUserVerifyAmount = function(data) {
		var self = this;
		window.location.href = window.ctx + '/auth/admin/questionStatis/researcher/verifyAmount.htm?userId=' + data.userId + '&userName=' + data.userName + '&startDate=' + self.query.startDate() + '&endDate=' + self.query.endDate();
	};
	
	ko.components.register('que-researcher-verify-amount-list', {
	    template: require('./que-researcher-verify-amount-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});