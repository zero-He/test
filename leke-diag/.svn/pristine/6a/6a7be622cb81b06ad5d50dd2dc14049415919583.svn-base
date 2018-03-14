define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	
	var statistical = {
		init : function() {
			this.bindEvent();
			var $firstStu = $('#jStudentList li').first();
			if($firstStu.length > 0 && Leke.user.currentRoleId == 102){
				$('#jStudentId').val($firstStu.data('stuid'));
				$('#jStudentName').text($firstStu.find('a').text());
			}
			this.loadCourseSubjectInfo();
		},
		loadCourseSubjectInfo:function(){
			var url = ctx + '/auth/student/data/loadStuStatisticalData.htm';
			var studentId = $('#jStudentId').val();
			if(studentId==''){
				return ;
			};
			var data ={studentId: studentId};
			$.post(url, data, function(json){
				var dataList = json.datas.dataList;
				if(dataList != null) {
					var output = Handlebars.render("#jtemplateData",dataList,"#jtbodyData");
					$('#jEmptyData').hide();
				}else{
					$('#jEmptyData').show();
					$('#jtbodyData').empty();
				}
			});
		},bindEvent: function(){
			var _this = this;
			$('#jStudentList li').click(function() {
				var $this = $(this);
				if (!$this.hasClass('active')) {
					$this.addClass("active").siblings().removeClass('active');
					$('#jStudentId').val($this.data('stuid'));
					$('#jStudentName').text($this.find('a').text());
					_this.loadCourseSubjectInfo();
				}
			});
			$(document).on('click','.jDetails',function(){
				var href = $(this).data('href')+'&studentId='+$('#jStudentId').val();
				window.open(href);
			});
		}
	};
	statistical.init();
});
