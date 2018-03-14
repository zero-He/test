define(function(require, exports, module) {
	var $ = require('jquery');
	var ClsAnalysisPie = require('diag/common/ClsAnalysisPie');

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
			$('#jULid li').click(function() {
				var $this = $(this);
				if (!$this.hasClass('active')) {
					var nTypeId = $this.data('id');
					$this.addClass("active").siblings().removeClass('active');
					$('#jClassType').val(nTypeId);
					var data = {
						type : nTypeId
					};
					$.post(ctx + '/auth/classTeacher/achievement/getClasses.htm', data, function(data) {
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
						ClsAnalysisPie.load();
					});
				}
			});
		}
	};

	ClsAnalysis.init();

});
