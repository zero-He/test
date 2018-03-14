define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	require('common/ui/ui-autocomplete/ui-autocomplete');
	var Utils = require('utils');
	var dialog = require('dialog');

	var Pages = {
		init : function() {
			/**
			 * 学校下拉框查询
			 */
			$('.j-school-select').autocomplete({
				url: Leke.ctx + '/auth/scs/school/querySchoolName.htm',
				nameKey: 'schoolName',
				onChange: function(item) {
					if(item != null){
						$('#schoolId').val(item.schoolId);
					}
				}
			});
			
			this.fBindEvent();
		},
		fBindEvent: function() {
			$('#jSave').click(function(){
				if($('#schoolId').val() == ''){
					Utils.Notice.alert("请先选择学校，再进行关联。");
					return;
				}
				
				$.ajax({
					 cache: true,
		                type: "POST",
		                url: ctx + '/auth/scs/customer/doAddSchoolCustomer.htm',
		                data:{'schoolId' : $('#schoolId').val(),
		                	'schoolName' : $('#schoolName').val()},
		                async: false,
		                success: function(data) {
		                	console.log(data);
		                	if(data.success){
		                		Utils.Notice.alert("关联成功！");
		                		window.location.href = ctx + '/auth/scs/customer/schoolList.htm';
		                	}else{
		                		dialog.alert(data.message);
		                	}
		                },
		                complete : function(){
		                }
				});
			});
			
		}
	
			
	};

	Pages.init();

});
