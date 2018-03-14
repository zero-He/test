//init
DoubtTools.commonEvent();
DoubtTools.takePhoto();
DoubtTools.selectVersion();
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
   				"doubtId" : $("#jDoubtId").val(),
   				"resolved": $("#jResolved").val()
   				};
   		DoubtTools.doubtSubmit(datas,function(datas,content,doubtAudio){
   			datas["explainContent"] = content;
   			datas["expainAudio"] = doubtAudio;
   			var form = $("#jForm");
   			for ( var data in datas) {
   				var input = $("<input></input>");
   				input.attr("name",data);
   				input.val(datas[data]);
   				form.append(input);
   			}
   			form.submit();
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