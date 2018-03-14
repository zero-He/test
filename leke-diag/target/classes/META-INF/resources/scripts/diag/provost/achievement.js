define(function(require, exports, module) {
	var $ = require('jquery');
	var PeriodDate = require('diag/common/PeriodDate');
	var AchievementLine = require('diag/common/AchievementLine');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');

	var Achievement = {
		init : function() {
			this.bindEvent();
			AchievementLine.init();
		},
		bindEvent : function() {
			$('#jSearch').click(function() {
				AchievementLine.load();
			});
			$('#jULid li').click(function() {
				var $this = $(this);
				if (!$this.hasClass('active')) {
					var nTypeId = $this.data('id');
					$('#jClassType').val(nTypeId);
					$('#jTimeDiv').toggle(nTypeId === 1);
					if (nTypeId === 2) {
						$('#jStartTime').val('');
						$('#jSubmitTime').val('');
					} else {
						PeriodDate.init('jStartTime', 'jEndTime');
					}
					$this.addClass("active").siblings().removeClass('active');
					Achievement.fLoadClass();
				}
			});
			$('#jGradeSubject').stgGrdSbjSelect({
				type : 'grd_sbj',
				caption : false,
				onChange : function(selection) {
					$('#jGradeId').val(selection.gradeId);
					$('#jSubjectId').val(selection.subjectId);
					Achievement.fLoadClass();
				}
			});
		},
		fLoadClass : function() {
			var nTypeId = $('#jClassType').val();
			var nGradeId = $('#jGradeId').val();
			var nSubjectId = $('#jSubjectId').val();
			var data = {
				gradeId : nGradeId,
				subjectId : nSubjectId,
				type : nTypeId
			};
			$.post(ctx + '/auth/provost/data/getClasses.htm', data, function(data) {
				var sClasses = data.datas.classes;
				$('#jClassId').empty();
				if (sClasses.length === 0) {
					option = $("<option>").val(0).text("没有对应班级");
					$('#jClassId').append(option);
				} else {
					if (nTypeId == 1) {
						option = $("<option>").val('').text("全部");
						$('#jClassId').append(option);
					};
					$.each(sClasses, function(index, domEle) {
						option = $("<option>").val(this.classId).text(this.className);
						$('#jClassId').append(option);
					});
				}
				AchievementLine.load();
			})
		}
	};

	Achievement.init();

});
