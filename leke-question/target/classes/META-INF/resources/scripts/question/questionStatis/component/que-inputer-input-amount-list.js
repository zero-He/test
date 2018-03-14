define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
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
		self.questionTotals = ko.observableArray();
	};
	/**
	 * 搜索
	 */
	ListVM.prototype.doSearch = function() {
		var self = this;
		var query = koutils.peekJS(self.query);
		if(!query.startDate){
			utils.Notice.alert('请选择录入开始日期！');
			return;
		}
		if(!query.endDate){
			utils.Notice.alert('请选择录入结束日期！');
			return;
		}
		
		var role = $('#jRoleSelect').val();
		if (role == 'researcher'){
			query.roleId = 402;
		}else if (role == 'inputer'){
			query.roleId = 400;
		}else if (role == 'manager'){
			query.roleId = 403;
		}else{
			query.roleId = 0;
		}
		
		$.ajax({
			type : 'post',
			url : ctx + '/auth/admin/questionStatis/ajax/findInputAmountList.htm',
			data : query,
			dataType : 'json',
			success : function(json) {
				var listjson = json.datas;
				var isl =  listjson.questionTotals || [];
				self.questionTotals(isl);
				self.query.startDate(listjson.startDate || '');
				self.query.endDate(listjson.endDate || '');

			}
		});
	};
	/**
	 * 查看详细
	 */
	ListVM.prototype.viewUserInputAmount = function(data) {
		var self = this;
		window.location.href = window.ctx + '/auth/admin/questionStatis/input/userInputAmount.htm?userId=' + data.userId + '&userName=' + data.userName + '&startDate=' + self.query.startDate() + '&endDate=' + self.query.endDate();
	};
	
	ListVM.prototype.clickOutPut = function(data) {
		var url = ctx+'/auth/admin/questionStatis/input/exportUserList.htm?';
		//var url = ctx+'/auth/admin/questionStatis/input/exportUserList.htm';
		/*$("#dUser").find("[name]").each(function(){
			var name = $(this).attr("name");
			var val = $(this).val();
			if(name&&val){
				url = url+name+"="+val+"&";
			}
		});
		var userList = $("[name='userIdList']:checked");
		var datas = new Array();
		for(var i=0;i<userList.length;i++){
			datas.push($(userList[i]).val());
		}
		if(datas.length>0){
			var a = "userIds="+datas.join("&userIds=");
			url = url+a;
		}*/
		
		var self = this;
		var query = koutils.peekJS(self.query);
		if(!query.startDate){
			utils.Notice.alert('请选择录入开始日期！');
			return;
		}
		url = url+"startDate"+"="+query.startDate+"&";
		if(!query.endDate){
			utils.Notice.alert('请选择录入结束日期！');
			return;
		}
		url = url+"endDate"+"="+query.endDate+"&";
		
		var role = $('#jRoleSelect').val();
		if (role == 'researcher'){
			query.roleId = 402;
		}else if (role == 'inputer'){
			query.roleId = 400;
		}else if (role == 'manager'){
			query.roleId = 403;
		}else{
			query.roleId = 0;
		}
		url = url+"roleId"+"="+query.roleId;
		/*$.ajax({
			type : 'post',
			url : ctx + url,
			data : query,
			dataType : 'json',
			success : function(json) {
				
				

			}
		});*/
		window.location.href = url;
	}

	
	ko.components.register('que-inputer-input-amount-list', {
	    template: require('./que-inputer-input-amount-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});