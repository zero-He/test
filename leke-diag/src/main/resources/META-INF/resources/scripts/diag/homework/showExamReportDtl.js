define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var Utils = require('utils');
	var Mustache = require('mustache');
	var Page = require("page");

	var examReportId = $("#examReportId").val();
	
	var Statistics = {

		init : function() {
			this.page();
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
			$('#publishExamReport').click(function(){
				$.post("/auth/"+Statistics.getRoleName()+"/examReport/updateExamReportStatus.htm", {examReportId : examReportId, status : 2}, 
						function(json) {
							Utils.Notice.alert(json.message);
							if(json.success){
								$("#publishExamReport").hide();
								window.location.href="/auth/"+Statistics.getRoleName()+"/examReport/toShowExamReportDtl.htm?examReportId="+examReportId+"&isCreatedBySelf=1&statusCd=2";
							}
						});
			});
		},
		
		page : function(){Page.create({
			id : 'jTablePage',
			url : "/auth/"+Statistics.getRoleName()+"/examReport/findExamReportDtl.htm?examReportId="+examReportId,
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
				} else {
					$('#jtbodyData').html('');
				}
			}
		})}

	};

	Statistics.init();
});
