define(function(require, exports, module){
	var $ = require('jquery');
	var WdatePicker = require('date');
	var Mustache = require('mustache');
	
	var UserProofreadingAmountList = {
			fInit : function() {
				this.fBindEvent();
				this.fLoadList();
			},
			
			fBindEvent:function() {
				var _this = this;
				$('#startDate').click(function() {
					WdatePicker({
						dateFmt : 'yyyy-MM-dd',
						maxDate : $('#endDate').val(),
						onpicked : function() {
							$(this).change();
						}
					});
				});
				
				$('#endDate').click(function() {
					WdatePicker({
						dateFmt : 'yyyy-MM-dd',
						minDate : $('#startDate').val(),
						onpicked : function() {
							$(this).change();
						}
					});
				});
				
				$('#bSearch').click(function() {
					_this.fLoadList();
				});
			},
			
			fLoadList : function() {
				var startDate = $('#startDate').val();
				var endDate = $('#endDate').val();
				var userId = $('#userId').val();
				
				$.ajax({
					type : 'post',
					url : ctx + '/auth/common/questionStatis/ajax/findProofreadingAmountListByUserIdOrderByDate.htm',
					data : {
						startDate:startDate,
						endDate:endDate,
						userId:userId
					},
					dataType : 'json',
					success : function(json) {
						var listjson = json.datas;
						$('#startDate').val(listjson.startDate);
						$('#endDate').val(listjson.endDate);
						var output = Mustache.render($('#listContext_tpl').val(),listjson);
						$('#listContext').html(output);
						
					}
				});
			}
	};
	
	UserProofreadingAmountList.fInit();
});
