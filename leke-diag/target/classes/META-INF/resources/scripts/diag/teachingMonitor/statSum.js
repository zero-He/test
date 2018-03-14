define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Handlebars = require('common/handlebars');
	var Page = require("page");
	var Utils = require('utils');
	var HandleCommParam = require("./HandleCommParam");
	var CommEvent = require("./CommEvent");
	
	var Statistics = {

			init : function() {
				HandleCommParam.initDateSelect();
				CommEvent.bindEvent();
				this.bindEvent();
				this.loadStatSumPage(HandleCommParam.getParam());
				Utils.Notice.mask.create();
			},
			
			bindEvent : function(){
				
				$("#gradeId").change(function () {
					$('#graId').val($('#gradeId').val());
				});

				$("#subjectId").change(function () {
					$('#subId').val($('#subjectId').val());
				});
				
				$("#ulDate").on("click","li",function(){
					var startEndDate = HandleCommParam.getStartEndDate();
					if("month" == $(this).attr("id")){
						$("#startDate").val(startEndDate.monthStartDate);
						$("#endDate").val(startEndDate.monthEndDate);
						$("#dateText").html($(this).html());
					}else if("week" == $(this).attr("id")){
						$("#startDate").val(startEndDate.weekStartDate);
						$("#endDate").val(startEndDate.weekEndDate);
						$("#dateText").html($(this).html());
					}else if("term" == $(this).attr("id")){
						$("#startDate").val(startEndDate.termStartDate);
						$("#endDate").val(startEndDate.termEndDate);
						$("#dateText").html($(this).html());
					}
				});
				
				$("#submit").on("click",function(){
					var startTime = $('#customStart').val();
					var endTime = $("#customEnd").val();
					
					if(!startTime || !endTime){
						Utils.Notice.alert("开始日期、结束日期都不能为空!");
						return false;
					}else if(new Date(startTime) > new Date(endTime)){
						Utils.Notice.alert("开始日期不能大于结束日期!");
						return false;
					}
					
					$("#startDate").val(startTime);
					$("#endDate").val(endTime);
					$("#dateText").html(startTime + "--" + endTime);
				});
				
				$("#exportBase").on("click",function(){
					$("#exportType").val("base");
					$("#teaName").val($("#teacherName").val());
					$("form").submit();
				});
				
				$("#exportAll").on("click",function(){
					$("#exportType").val("all");
					$("#teaName").val($("#teacherName").val());
					$("form").submit();
				});
				
				$("#search").on("click",function(){
					Statistics.loadStatSumPage(HandleCommParam.getParam());
				});
				
				$(".m-sorting").on("click", function(){
					if($(this).hasClass("m-sorting-desc")){
						$(this).removeClass("m-sorting-desc").addClass("m-sorting-asc");
						$("#orderType").val("asc");
					}else{
						$(this).removeClass("m-sorting-asc").addClass("m-sorting-desc");
						$("#orderType").val("desc");
					}
					$("#orderAttr").val($(this).attr("id"));
					$(this).siblings().removeClass("m-sorting-asc").removeClass("m-sorting-desc");
					Statistics.loadStatSumPage(HandleCommParam.getParam());
				});
				
			},
			
			loadStatSumPage : function(param){

				var url = "/auth/provost/teachingMonitor/statsum/findStatSum.htm?startDate="+param.startDate+"&endDate="+param.endDate+
				"&gradeId="+param.gradeId+"&subjectId="+param.subjectId+"&orderAttr="+param.orderAttr+"&orderType="+param.orderType+"&teacherName="+param.teacherName;

				Page.create({
					id : 'jTablePage',
					url : url,
					formId : '',
					curPage : 1,
					pageSize : 15,
					pageCount : 10,// 分页栏上显示的分页数
					tips : '暂无数据！',
					callback : function(datas) {
						var data = datas.page;
						if (data) {
							Handlebars.render("#dtlTpl", data, "#jtbodyData");
						}
						Utils.Notice.mask.close();
					}
				});

				/*$('.m-page').on('click', 'li', function () {
					var className = this.target.className;

					$('.m-loading').show();
				});*/
			}
		};

		Statistics.init();
});
