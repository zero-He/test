define(function(require, exports, module) {
	var $ = require('jquery');
	var ClsAnalysisPie = require('diag/common/ClsAnalysisPie');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');

	var ClsAnalysis = {
		init : function() {
			this.bindEvent();
			ClsAnalysisPie.init();
		},
		bindEvent : function() {
			$('#jSearch').click(function() {
				ClsAnalysisPie.load();
			});
			$('#jDetail').click(function() {
				window.location.href = 'stuAnalysis.htm';
			});
			$('#jStageGrade').stgGrdSbjSelect({
				type : 'grd',
				caption : false,
				onChange : function(selection) {
					$('#jGradeId').val(selection.gradeId);
					var nTypeId = $('#jClassType').val();
					var data = {
						gradeId : selection.gradeId,
						type : nTypeId
					};
					$.post(ctx + '/auth/provost/data/getClasses.htm', data, function(data) {
						var sClasses = data.datas.classes;
						$('#jClassId').empty();
						if (sClasses.length === 0) {
							var option = $("<option>").val('0').text('没有对应班级');
							$('#jClassId').append(option);
						}
						$.each(data.datas.classes, function() {
							option = $("<option>").val(this.classId).text(this.className);
							$('#jClassId').append(option);
						});
					});
				}
			});
		}
	};

	ClsAnalysis.init();

});
