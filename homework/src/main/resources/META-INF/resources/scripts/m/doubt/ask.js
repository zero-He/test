//init
DoubtTools.commonEvent();
DoubtTools.selectSubjectAndTeacherForPhone();
DoubtTools.selectVersion(true);
DoubtTools.takePhoto();
DoubtTools.record();

//--------------------原生头

var LekeHeader = {
	funs : {},
	menus : {},
	appendMenu : function(name,menu){
		LekeHeader.menus[name] = menu;
		for(var i =0;i<menu.length;i++){
			var m = menu[i];
			LekeHeader.funs[m.id] = m.fun;
		}
	},
	showMenu : function(name){
		var menu = LekeHeader.menus[name];
		if(menu){
			LeTalkCorePlugin.showMenu(LekeHeader.menus[name]);
		}
	}

}


function clickMenu(id){
	var menu = eval(id);
	var fun = LekeHeader.funs[menu.id];
	if(fun){
		fun(menu);
	}
}

LekeHeader.appendMenu("init",[{
   	"id" : 1,
   	"groupId" : 1,
   	"groupOrder" : 0,
   	"order":0,
   	"icon" : "",
   	"title" : "提交",
   	"count" : 0,
   	"isShowNum" : false,
   	"fun" : function(){
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
   					setTimeout(function(){
   						window.location.href = $("#jBackUrl").val();
   					}, 500);
   				}
   			});
   		});
   	}
   }]);
document.addEventListener("deviceready", function(){
	setTimeout(function(){
		LekeHeader.showMenu("init");
	}, 500);
}, false);

function onLoad(){
	LekeHeader.showMenu("init");
}
