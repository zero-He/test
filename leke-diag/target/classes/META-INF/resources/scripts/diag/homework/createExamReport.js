define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var Utils = require('utils');
	var fileProgress = require('fileprogress');
	var SWFUpload = require('fileupload');
	require('placeholder');
	
	var ticket = Leke.ticket;
	SWFUpload
		.createFileUpload(
				{
					context : ctx,
					upload_url : window.ctx
							+ '/auth/common/upload/fileUpload.htm?ticket='
							+ ticket,
					button_placeholder_id : 'jUpload',
					file_types : '*.xls;*.xlsx;',
					file_types_description : '浏览',
					file_post_name : 'file',
					file_upload_limit : 1,
					file_queue_limit : 1,
					file_size_limit : '10MB',
					custom_settings : {
						progressTarget : 'progressExcelUpload'
					}
				},
				fileProgress,
				{
					onError : function(msg) {
						Utils.Notice.alert(msg);
					},
					uploadSuccess : function(datas) {
						var url = datas.urls;
						$('#filePath').val(url[1]);
					}
				});
	
	
	var Statistics = {
		init : function() {
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

		bindEvent : function() {
			$('#add').click(function() {
				var examReportName = $('#examReportName').val();
				var greatings = $('#greatings').val();
				if (greatings == null || greatings == '') {
					greatings = $('#greatings').attr('placeholder');
				}

				if (examReportName == null || examReportName == '') {
					Utils.Notice.alert("必须输入成绩单名称（1-20个字符）");
					return false;
				} else if (examReportName.length > 20) {
					Utils.Notice.alert("成绩单名称必须在1-20个字符范围内");
					return false;
				}

				var filePath = $('#filePath').val();
				if (filePath == null || filePath == '') {
					Utils.Notice.alert("必须上传成绩单");
					return false;
				}

				var examReport = {
					'examReportName' : examReportName,
					'greatings' : greatings,
					'filePath' : filePath
				};
				
				$.post("/auth/"+Statistics.getRoleName()+"/examReport/createExamReport.htm", examReport, function(json){
					if (json.success) {
						if (json.datas.success){
							var examReportId = json.datas.examReportId;
							window.location.href = "toShowExamReportDtl.htm?statusCd=1&isNewCreated=1&isCreatedBySelf=1&examReportId="+examReportId;
						}else{
							var msg = json.datas.msg;
							window.location.href = "toShowError.htm?msg="+msg;
						}
					}
				});
			});
		}

	};

	Statistics.init();

});
