define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var Utils = require('utils');
	var Mustache = require('mustache');
	
	var Statistics = {

		init : function() {
			this.loadStuExamReportDtl();
		},
		
		loadStuExamReportDtl : function(){
			var examReportId = $('#examReportId').val();
			var studentId = $('#studentId').val();
			$.post('/auth/m/parent/examReport/findExamReportDtl.htm', {examReportId : examReportId, studentId : studentId}, 
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
						} else {
							$('#jtbodyData').html('');
						}
				});
		}

	};

	Statistics.init();
});
