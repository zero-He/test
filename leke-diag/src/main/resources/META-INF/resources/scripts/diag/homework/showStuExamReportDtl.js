define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var Utils = require('utils');
	var Mustache = require('mustache');
	var Page = require("page");
	
	var Statistics = {

		init : function() {
			this.loadStuExamReportDtl();
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
		
		loadStuExamReportDtl : function(){
			var examReportId = $('#examReportId').val();
			var studentId = $('#studentId').val();
			$.post("/auth/"+Statistics.getRoleName()+"/examReport/findExamReportDtl.htm", {examReportId : examReportId, studentId : studentId}, 
					function(json) {
						var data = json.datas;
						var examReportDtl = data.dataList[0]; 
						if (data) {
							var output = Mustache.render($("#stuExamReportDtlTpl").html(), data);
							$('#jtbodyData').html(output);
							$('#studentName').html(examReportDtl.studentName);
							$('#greatings').html(examReportDtl.greatings);
							$('#schoolName').html(examReportDtl.schoolName);
							$('#createdOn').html(examReportDtl.createdOn);
							
					        var talkHei = $('.ts-talk').height();
					        var talkTop = $('.ts-talk').position().top;
					        var talkHeptoB = talkHei + talkTop;
					        $('.ts-body').css('padding-top', talkHeptoB + 'px');
						} else {
							$('#jtbodyData').html('');
						}
				});
		}

	};

	Statistics.init();
});
