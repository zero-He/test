define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var utils = require('utils');

	var win = {
		init : function() {
			this.bindEvent();
			var $firstClass = $('.jtabClass li a').first();
			if($firstClass){
				$('#jClassId').val($firstClass.data('classid'));
				$('#jGradeId').val($firstClass.data('gradeid'));
			}
			this.loadData();
			
		},
		loadData : function() {
			if($('#jGradeId').val() == ''){
				return;
			}
			var data = {
				classId : $('#jClassId').val(),
				gradeId : $('#jGradeId').val()
			};
			$.post(ctx + '/auth/classTeacher/achievement/findSubjectAchievement.htm',data, function(json) {
				var data = json.datas.dataList;
				if(data == undefined ||  data.length == 0){
					$('#jEmptyData').show();
				}
				Handlebars.render("#jTemplate", data , "#jBody");
			});
		},
		bindEvent : function() {
			$(document).on('click','.jtabClass li a',function(){
				$('.jtabClass li a').removeClass('active');
				$(this).addClass('active');
				$('#jClassId').val($(this).data('classid'));
				$('#jGradeId').val($(this).data('gradeid'));
				win.loadData();
			});
			$(document).on('click','.jdetails',function(){
				var className = $.trim($('.jtabClass li a.active').first().text());
				var url = ctx
						+ "/auth/classTeacher/achievement/achievementDetails.htm?classId="
						+ $('#jClassId').val() 
						+ "&subjectId=" + $(this).data('subjectid')
						+ "&subjectName=" + encodeURIComponent(encodeURIComponent($(this).data('subjectname')))
						+ "&className=" + encodeURIComponent(encodeURIComponent(className));
				window.open(url);
			});
		}
	};

	win.init();

});

