//init
var DoubtAsk = {
	init : function(){
		DoubtTools.commonEvent();
		DoubtTools.selectSubjectAndTeacherForPad();
		DoubtTools.selectVersion(true);
		DoubtTools.handbord();
		DoubtTools.takePhoto();
		DoubtTools.record();
		$(".c-choose-box").on("click", ".item", function() {
			var teacherId = $(this).data("teacherid");
			var subjectId = $(this).data("subjectid");
			if (teacherId) {
				$(".teacher .item").removeClass("cur");
				$(this).addClass("cur");
			} else if (subjectId) {
				$(".teachercon").hide();
				$(".c-choose-box .item").removeClass("cur");
				$(this).addClass("cur");
				$("#subjectTeacher_" + subjectId).show();
			}
		});
	},
	submit : function(fn){
		var datas = {
				"subjectId" : $('.result').data("subjectid"),
				"teacherId" : $('.result').data("teacherid"),
				"subjectName" : $('.result').data("subjectname"),
				"doubtType" : 1,
				"questionId" : 0
				};
		DoubtTools.doubtSubmit(datas,function(datas,content,doubtAudio){
			datas["doubtContent"] = content;
			datas["doubtAudio"] = doubtAudio;
			if (!datas.teacherId) {
				Utils.Notice.alert("请选择老师");
				$(".m-load").hide();
				return;
			}
			$.post("save.htm",datas,function(data){
				if(data.success){
					var tips = data.datas.tips || {};
					var str = "提交成功";
					str = tips.leke?[str,"，乐豆+",tips.leke].join(""):str;
					str = tips.exp?[str,"，经验+",tips.exp].join(""):str;
					Utils.Notice.alert(str);
					fn();
				}
			});
		});
	}
}