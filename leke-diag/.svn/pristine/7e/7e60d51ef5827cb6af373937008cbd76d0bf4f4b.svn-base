define(function(require, exports, module) {
	var $ = require('jquery');
	var SubjectScoreBar = require('diag/common/SubjectScoreBar');

	var StuSubjectScore = {
		init : function() {
			this.bindEvent();
			SubjectScoreBar.init();
		},
		bindEvent : function() {
			$('#jStudentList li').click(function() {
				var $this = $(this);
				if (!$this.hasClass('active')) {
					$this.addClass("active").siblings().removeClass('active');
					var nUserId = $this.data('stuid');
					$('#jStudentId').val(nUserId);
					StuSubjectScore.fGetClassList(nUserId,$('jClassType').val())
				}
			});
			$('#jULid li').click(function() {
				var $this = $(this);
				if (!$this.hasClass('active')) {
					var nTypeId = $this.data('id');
					$this.addClass("active").siblings().removeClass('active');
					$('#jClassType').val(nTypeId);
					var nUserId = $('#jStudentId').val();
					StuSubjectScore.fGetClassList(nUserId,nTypeId)
				}
			});
			$('#jClassId').on('change', function() {
				StuSubjectScore.init();
			});

		},
		fGetClassList : function(userId, type) {
			var data = {
				userId : userId,
				type : type
			};
			$.post(ctx + '/auth/parent/data/clazz/getClasses.htm', data, function(data) {
				var sClasses = data.datas.classes;
				$('#jClassId').empty();
				if (sClasses.length === 0) {
					option = $("<option>").val(0).text("没有对应班级");
					$('#jClassId').append(option);
				} else {
					$.each(sClasses, function() {
						option = $("<option>").val(this.classId).text(this.className);
						$('#jClassId').append(option);
					});
				}
				StuSubjectScore.init();
			});
		}
	};

	StuSubjectScore.init();

});