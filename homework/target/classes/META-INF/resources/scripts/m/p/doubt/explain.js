//init
var DoubtExplain = {
	init : function(){
		DoubtTools.commonEvent();
		DoubtTools.handbord();
		DoubtTools.selectVersion();
		DoubtTools.takePhoto();
		DoubtTools.record();
	},
	submit : function(fn){
		$(".m-load").show();
		DoubtTools.doubtSubmit({"doubtId" : doubtId},function(datas,content,doubtAudio){
			datas["explainContent"] = content;
			datas["expainAudio"] = doubtAudio;
			$.post("explain.htm",datas,function(data){
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
