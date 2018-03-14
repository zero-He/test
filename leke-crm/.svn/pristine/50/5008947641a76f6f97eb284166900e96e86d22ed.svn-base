define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Mustache = require('mustache');
	var Utils = require('utils');
	var WdatePicker = require('date');
	
	var School = {
		init : function() {
			this.bindEvents();
			this.loadData();
		},
		
		loadData : function() {
			Page.create({
				id : 'jPageFoot',
				url : ctx + '/auth/scs/school/personalSchoolListData.htm',
				pageSize : 10,
				buttonId : 'jPageSearch',
				formId : 'jPageForm',
				callback : function(datas) {
					var page = datas.page;
					var content = Mustache.render($('#jPageTpl').val(), page);
					$('#jPageBody').html(content);
				}
			});
		},
		
		bindEvents : function() {
			$('#jCreatedOn').focus(function() {
				WdatePicker({
					dateFmt:'yyyy-MM-dd',
					maxDate:$('#jCreatedOnR').val(),
					readOnly:true
				});
			});
			$('#jCreatedOnR').focus(function() {
				WdatePicker({
					dateFmt:'yyyy-MM-dd',
					minDate:$('#jCreatedOn').val(),
					readOnly:true
				});
			});
			//查询条件切换手机与乐号
			$('#jNameOrPhoneSelect').on('change', function() {				
				$('#jNameOrPhone').attr("name", $('#jNameOrPhoneSelect option:selected').val());
			});
			//导出数据
			$('#jExport').on('click', function() {
				var loginName = $('input[name="loginName"]').val();
				var phone = $('input[name="phone"]').val();
				var createdOn = $('input[name="createdOn"]').val();
				var createdOnR = $('input[name="createdOnR"]').val();
				var hasSeller = $('#jHasSeller').val();
				var origin = $('#jOrigin').val();
				if(!loginName) {
					loginName = '';
				}
				if(!phone) {
					phone = '';
				}
				var url = ctx + '/auth/scs/school/export.htm?loginName='+loginName+'&phone='+phone+'&createdOn='+createdOn+'&createdOnR='+createdOnR+'&hasSeller='+hasSeller+'&origin='+origin;
				$('#jFrames').attr('src',url);
			});
		}
	};
	
	School.init();
})