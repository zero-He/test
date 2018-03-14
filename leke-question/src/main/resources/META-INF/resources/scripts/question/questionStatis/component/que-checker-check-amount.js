define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var WdatePicker = require('date');
	var koutils = require('common/knockout/koutils');
	var datepicker = require('common/knockout/bindings/datepicker');
	
	function Query(params) {
		this.userId = params.userId;
		this.startDate = ko.observable(params.startDate,'yyyy-MM-dd');
		this.endDate = ko.observable(params.endDate);
	};

	function ListVM(params, element) {
		var self = this;
		self.query = new Query({
			userId: params.userId,
			startDate: params.startDate,
			endDate: params.endDate
		});
		var data = self.data= _.extend({}, params.data);
		self.roleId = params.roleId;
		self.userName = params.userName;
		self.inputStatisList = ko.observableArray();
		self.taskTotalAmount = ko.observable(0);
		self.checkTotalAmount = ko.observable(0);
		self.effectiveTotalAmount = ko.observable(0);
		
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
			url : ctx + '/auth/common/questionStatis/ajax/findCheckerCheckAmountList.htm',
			data : query,
			dataType : 'json',
			success : function(json) {
				var listjson = json.datas;
				var isl =  listjson.inputStatisList || [];
				self.inputStatisList(isl);
				self.query.startDate(listjson.startDate);
				self.query.endDate(listjson.endDate);
				
				var ta = 0;
				var ca = 0;
				var ea = 0;
				for(var i=0; i < isl.length; i++){
					ta += isl[i].taskAmount;
					ca += isl[i].checkAmount;
					ea += isl[i].effectiveAmount;
				}
				self.taskTotalAmount(ta);
				self.checkTotalAmount(ca);
				self.effectiveTotalAmount(ea);
			}
		});
	};
	/**
	 * 查看详细
	 */
	ListVM.prototype.viewUserCheckAmount = function(data) {
		var self = this;
		window.location.href = window.ctx + '/auth/admin/questionStatis/statisQueList.htm?operateDate=' + data.createdDateStr + '&statusType=17';
	};
	
	ko.components.register('que-checker-check-amount', {
	    template: require('./que-checker-check-amount.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});