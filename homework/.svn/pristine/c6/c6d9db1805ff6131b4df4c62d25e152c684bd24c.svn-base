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
   	"title" : "分享1",
   	"count" : 0,
   	"isShowNum" : false,
   	"fun" : function(){
   		
   		var aa = {
   				"id": 9527,
   				"type": 40962,
   				"name": "商品名称",
   				"img": "http://static.leke.cn/images/common/support.png",
   				"url": "http://index.leke.cn/images/linkleke/excessive.html",
   				"price": 666666,
   				"buyNum": 233333,
   				"extra": "extra"
   			};
   		var a = {
   				"items": [2,3,4,5,1],
   				"title": "分享啦啦啦",
   				"img": "http://static.leke.cn/images/common/support.png",
   				"description": "你知道我爱你吗",
   				"url": "http://index.leke.cn/images/linkleke/excessive.html",
   				"nativeUrl": "letalk://share?content="+JSON.stringify(aa)
   			};
   		
   		
   		LeTalkCorePlugin.openThirdShare(a);
   	}
   },{
	   	"id" : 2,
	   	"groupId" : 1,
	   	"groupOrder" : 0,
	   	"order":0,
	   	"icon" : "",
	   	"title" : "分享2",
	   	"count" : 0,
	   	"isShowNum" : false,
	   	"fun" : function(){
	   		
	   		var aa = {
	   			    "id": 89757,
	   				"type": 40963,
	   				"name": "分享标题",
	   				"describe": "分享描述",
	   				"img": "http://leke.cn/mmmm.png",
	   				"url": "http://leke.cn",
	   				"extra": "拓展字段"
	   			};
	   		var a = {
	   				"items": [2,3,4,5,1],
	   				"title": "分享啦啦啦",
	   				"img": "http://static.leke.cn/images/common/support.png",
	   				"description": "你知道我爱你吗",
	   				"url": "http://index.leke.cn/images/linkleke/excessive.html",
	   				"nativeUrl": "letalk://share?content="+JSON.stringify(aa)
	   			};
	   		
	   		
	   		LeTalkCorePlugin.openThirdShare(a);
	   	}
	   }]);


document.addEventListener("deviceready", function(){
	setTimeout(function(){
		LekeHeader.showMenu("init");
	},500);
}, false);

function onLoad(){
	LekeHeader.showMenu("init");
}
