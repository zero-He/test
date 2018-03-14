define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var Utils = require('utils');
	var Dialog = require('dialog');
	var Mustache = require('mustache');
	var Page = require("page");
	
	var Statistics = {

		init : function() {
			if(this.getRoleName() == "parent"){
				this.loadChildren();
			}else{
				this.page();
			}
			this.bindEvent();
		},
		
		getRoleName : function(){
			var roleName = "common";
			if(Leke.user.currentRoleId == 100){
				roleName = "student";
			}else if(Leke.user.currentRoleId == 101){
				roleName = "teacher";
			}else if(Leke.user.currentRoleId == 102){
				roleName = "parent";
			}else if(Leke.user.currentRoleId == 103){
				roleName = "classTeacher";
			}else if(Leke.user.currentRoleId == 104){
				roleName = "provost";
			}
			return roleName;
		},
		
		bindEvent : function(){
			$('#createExamReport').click(function(){
				window.open("/auth/"+Statistics.getRoleName()+"/examReport/toAddExamReport.htm", "_blank");
			});
			
			$("#examReportDiv").on("click","#jDiscardExamReport",function(ele){
				var examReportId = ele.srcElement.target;
				Dialog.confirm('确定要作废该成绩单吗？').done(function(sure) {
					if(sure){
						$.post("/auth/"+Statistics.getRoleName()+"/examReport/updateExamReportStatus.htm", {examReportId : examReportId, status : 3}, 
							function(json) {
								Utils.Notice.alert(json.message);
								Statistics.init();
    					});
					}
				});
				
			});
			
			$("#ulbodyData").on("click","li",function(){
				studentId = $(this).data('id');
				$('#ulbodyData li').each(function(){
					$(this).removeClass();
				});
				$(this).addClass("active");
				Statistics.page(studentId);
			});
		},
		
		loadChildren : function(){
			$.post("/auth/"+Statistics.getRoleName()+"/examReport/findChildrenByParentId.htm", {}, function(json){
				var data = json.datas;
				if (data) {
					var output = Mustache.render($("#chidrenTpl").html(), data);
					$("#ulbodyData").html(output);
					
					var studentId = data.childrenList[0].studentId;
					$("li:first-child").addClass("active");
					Statistics.page(studentId);
				} else {
					$("#ulbodyData").html('');
				}
			});
		},
		
		page : function(studentId){
			var url;
			if(studentId){
				url = "/auth/"+Statistics.getRoleName()+"/examReport/findExamReport.htm?studentId="+studentId;
			}else{
				url = "/auth/"+Statistics.getRoleName()+"/examReport/findExamReport.htm";
			}
			
			Page.create({
				id : 'jTablePage',
				url : url,
				formId : '',
				curPage : 1,
				pageSize : 10,
				pageCount : 10,// 分页栏上显示的分页数
				tips : '暂无成绩单数据！',
				callback : function(datas) {
					var data = datas.page;
					if (data) {
						var output = Mustache.render($("#examReportListTpl").html(), data);
						$('#jtbodyData').html(output);
						
						$('.jView').each(function() {
							var href = $(this).attr('href').replace('roleName', Statistics.getRoleName());
							$(this).attr('href', href);
						});
					} else {
						$('#jtbodyData').html('');
					}
				}
			})}

	};

	Statistics.init();
});
