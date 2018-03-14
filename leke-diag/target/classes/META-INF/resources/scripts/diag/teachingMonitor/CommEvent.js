define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var WdatePicker = require('date');
	var HandleCommParam = require("./HandleCommParam");
	var StudentBehaviorComm = require("diag/studentMonitor/studentBehaviorComm");

	var CommEvent = {
			bindEvent : function(){
				$("body").on("click", function(e){
					if( !$(e.target).parents('.c-timerange__list') || !$(e.target).hasClass('c-timerange') ){
						if($(e.target).attr('id') == "dateText"){
							 return;
						}
						$('.c-timerange__list').removeClass("is-active");
					}
				});
				
				$('.c-timerange').on('click', function(e){
		            var $seleList = $(this).find('.c-timerange__list');
		            if( !$seleList.hasClass('is-active') ){
		                $seleList.addClass('is-active');
		            }else if(!$(e.target).parents('#custom').get(0) || $(e.target).hasClass('u-btn')){
		                $seleList.removeClass('is-active');
		            }
		        });
				
				$("#customStart").on("click",function(){
					var now = HandleCommParam.getCurrentDate();
					var nowDate = new Date(now);
					var currYear = nowDate.getFullYear();
					var min;
					
					if(nowDate > new Date(currYear + "-07-31")){
						min = currYear + "-08-01";
					}else{
						min = currYear + "-02-01";
					}
					
					WdatePicker({
						dateFmt : 'yyyy-MM-dd',
						maxDate : HandleCommParam.getDateByDelta(now, -1),
						minDate : min,
						readOnly : true
					});
					
				});
				
				$("#customEnd").on("click",function(){
					var now = HandleCommParam.getCurrentDate();
					var nowDate = new Date(now);
					var currYear = nowDate.getFullYear();
					var min;
					
					if(nowDate > new Date(currYear + "-07-31")){
						min = currYear + "-08-01";
					}else{
						min = currYear + "-02-01";
					}
					
					WdatePicker({
						dateFmt : 'yyyy-MM-dd',
						maxDate : HandleCommParam.getDateByDelta(now, -1),
						minDate : min,
						readOnly : true
					});
				});
				
				$("#ulSwitch").on("click", "a", function(){
					HandleCommParam.saveGlobalParam($("#gradeId").val(), $("#subjectId").val(), $("#startDate").val(), $("#endDate").val(), $("#dateText").html());
					$(this).parents(".c-typeswitch__item").attr("id")
				});

				$("#ulSwitch15").on("click", "a", function(){
					StudentBehaviorComm.saveGlobalParam($("#gradeId").val(), $("#subjectId").val(), $("#classId").val(), $("#startDate").val(), $("#endDate").val(), $("#dateText").html());
				});
			}
		};

	module.exports = CommEvent;
});
