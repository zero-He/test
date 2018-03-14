define(function(require, exports, module){
	var $ = require('jquery');
	var WdatePicker = require('date');
	var Mustache = require('mustache');
	
	var ProofreadingAmountList = {
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
				})
			},
			
			fLoadList : function() {
				var startDate = $('#startDate').val();
				var endDate = $('#endDate').val();
				var userName = $('#userName').val();
				
				$.ajax({
					type : 'post',
					url : ctx + '/auth/admin/questionStatis/ajax/findProofreadingAmountList.htm',
					data : {
						startDate:startDate,
						endDate:endDate,
						userName:userName
					},
					dataType : 'json',
					success : function(json) {
						var listjson = json.datas;
						$('#startDate').val(listjson.startDate);
						$('#endDate').val(listjson.endDate);
						var userNameEncode = function() {
							if(this.userName != null){
								return encodeURIComponent(this.userName);
							}
						};
						listjson.userNameEncode = userNameEncode;
						var output = Mustache.render($('#listContext_tpl').val(),listjson);
						$('#listContext').html(output);
					
						var proofreadingTotalAmount = 0;
						var unProofreadingTotalAmount = 0;
						var errorCorrectionTotalAmount = 0;
						var correctTotalAmount = 0;
						var unCorrectTotalAmount = 0;
						var effectiveTotalAmount = 0;
						for(var i=0; i<listjson.inputStatisList.length; i++){
							proofreadingTotalAmount += listjson.inputStatisList[i].proofreadingAmount;
							unProofreadingTotalAmount += listjson.inputStatisList[i].unProofreadingAmount;
							errorCorrectionTotalAmount += listjson.inputStatisList[i].errorCorrectionAmount;
							correctTotalAmount += listjson.inputStatisList[i].correctAmount;
							unCorrectTotalAmount += listjson.inputStatisList[i].unCorrectAmount;
							effectiveTotalAmount += listjson.inputStatisList[i].effectiveAmount;
						}
						$('#proofreadingTotalAmount').html(proofreadingTotalAmount);
						$('#unProofreadingTotalAmount').html(unProofreadingTotalAmount);
						$('#errorCorrectionTotalAmount').html(errorCorrectionTotalAmount);
						$('#correctTotalAmount').html(correctTotalAmount);
						$('#unCorrectTotalAmount').html(unCorrectTotalAmount);
						$('#effectiveTotalAmount').html(effectiveTotalAmount);
					}
				});
			}
	};
	
	ProofreadingAmountList.fInit();
});