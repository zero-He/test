define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	var utils = require('utils');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var TeaDiligent = {
		init : function() {
			var _this = this;
			var $firstStu = $('#jStudentList li').first();
			if($firstStu.length > 0 && Leke.user.currentRoleId == 102){
				$('#jStudentId').val($firstStu.data('stuid'));
			}
			this.loadCourseSubjectInfo();
			this.bindEvent();
		},
		bindEvent : function() {
			var _this = this;
			$('#jStageSubject').stgGrdSbjSelect({
				type : 'sbj',
				onChange : function(selection) {
					$('#jSubjectId').val(selection.subjectId);
				}
			});
			$('#jSearch').click(function(){
				var _this = this;
				var subjectId = $('#jSubjectId').val();
				var jsonStr = $('#jDataList').val();
				if($.trim(jsonStr).length == 0){
					return;
				}
				var dataList = JSON.parse(jsonStr);
				if($.trim(subjectId).length == 0){
					Handlebars.render("#jtemplateData",dataList,"#jtbodyData");
				}else{
					var filterDataList =[];
					for(var i=0; i<dataList.length; i++){
						var flag = true;
						var item = dataList[i];
						if($.trim(subjectId).length != 0 && item.subjectId == subjectId){
							filterDataList.push(item);
						}
					}
					Handlebars.render("#jtemplateData",filterDataList,"#jtbodyData");
					if (filterDataList.length > 0) {
						$('#jEmptyData').hide();
					} else {
						$('#jEmptyData').show();
					}
				}
			});
			$('#jStudentList li').click(function() {
				var $this = $(this);
				if (!$this.hasClass('active')) {
					$this.addClass("active").siblings().removeClass('active');
					$('#jStudentId').val($this.data('stuid'));
					_this.loadCourseSubjectInfo();
				}
			});
			$(document).on('click','.jDetails',function(){
				var href = $(this).data('href') + '&stuId=' + $('#jStudentId').val();
				window.open(href);
			});
		},
		loadCourseSubjectInfo:function(){
			var url = ctx + '/auth/student/data/findMyHomeworkList.htm';
			var studentId = $('#jStudentId').val();
			if(studentId==''){
				$('#jEmptyData').show();
				return ;
			};
			var data = {studentId : studentId};
			$.post(url, data, function(json){
				var dataList = json.datas.dataList;
				if( dataList != null && dataList.length > 0 ){
					 Handlebars.render("#jtemplateData",dataList,"#jtbodyData");
					 $('#jDataList').val(JSON.stringify(dataList));
					 $('#jEmptyData').hide();
				}else{
					$('#jEmptyData').show();
					$('#jtbodyData').empty();
				}
			});
		}
	};

	TeaDiligent.init();

});
