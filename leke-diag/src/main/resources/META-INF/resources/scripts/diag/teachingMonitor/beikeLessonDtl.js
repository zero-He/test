define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Mustache = require('mustache');
	var Page = require("page");
	var HandleCommParam = require("./HandleCommParam");
	
	var Statistics = {

			init : function() {
				this.loadDtlDataPage(HandleCommParam.getParam());
				this.bindEvent();
			},
			
			bindEvent : function(){
				
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
					Statistics.loadDtlDataPage(HandleCommParam.getParam());
				});
				
				$("#export").on("click", function(){
					var param = HandleCommParam.getParam();
					var form = $("form");
					form.attr("action", "/auth/provost/teachingMonitor/beike/exportLessonDtlData.htm");
					form.submit();
				});
				
			},
			
			loadDtlDataPage : function(param){
				var url = "/auth/provost/teachingMonitor/beike/findLessonDtlOfTeacher.htm?startDate="+param.startDate+"&endDate="+param.endDate+
				"&teacherId="+param.teacherId+"&orderAttr="+param.orderAttr+"&orderType="+param.orderType;
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
							var output = Mustache.render($("#dtlTpl").html(), data);
							$('#jtbodyData').html(output);
						} else {
							$('#jtbodyData').html('');
						}
					}
				})},
			
			loadDtlData : function(param){
				var url;
				if(param.orderAttr && param.orderType){
					url = "/auth/provost/teachingMonitor/beike/findLessonDtlOfTeacherOrder.htm";
				}else{
					url = "/auth/provost/teachingMonitor/beike/findLessonDtlOfTeacher.htm";
				}
				
				$.post(url, param, function(json){
					var data = json.datas;
					if (data) {
						var statInfo = Mustache.render($("#dtlTpl").html(), data);
						$("#jtbodyData").html(statInfo);
					}
				});
			},
			
		};

		Statistics.init();
});
