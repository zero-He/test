define(function(require, exports, module) {
	var $ = require('jquery');
	var SubjectScoreBar = require('diag/common/SubjectScoreBar');
	var StuSubjectScore = {
		init : function() {
			var $firstStu = $('#jStudentList li').first();
			if($firstStu.length > 0 && Leke.user.currentRoleId == 102){
				$('#jStudentId').val($firstStu.data('stuid'));
				$('#jStudentName').text($firstStu.find('a').text());
			}
			SubjectScoreBar.init();
			this.loadData();
			this.bindEvent();
		},
		loadData : function() {
			var studentId = $('#jStudentId').val();
			if(studentId == ''){
				return ;
			}
			$.post(ctx + '/auth/student/data/clazz/getClasses.htm', {type : 1,studentId : studentId}, function(data) {
				var sClasses = data.datas.classes;
				if (sClasses.length > 0) {
					$.each(sClasses, function() {
						$('#jclassId').val(this.classId);
						$('#jclassName').text(this.className);
					});
				}
			});
		},
		bindEvent : function () {
			var _this = this;
			$('#jStudentList li').click(function() {
				var $this = $(this);
				if (!$this.hasClass('active')) {
					$this.addClass("active").siblings().removeClass('active');
					$('#jStudentId').val($this.data('stuid'));
					$('#jStudentName').text($this.find('a').text());
					SubjectScoreBar.init();
					_this.loadData();
				}
			});
		}
	};

	StuSubjectScore.init();

});