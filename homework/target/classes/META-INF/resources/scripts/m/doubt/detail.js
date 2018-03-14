var jResolved = $("#jResolved").val();
var jDoubtId = $("#jDoubtId").val();

$(".media-btn").click(function(){
	var audio = $(".do-wave").parent().find(".audio")[0];
	if(audio){
		audio.pause();
	}
	$(".media-btn").removeClass("do-wave");
	$(this).addClass("do-wave");
	audio = $(this).parent().find(".audio")[0];
	if(audio){
		audio.loop = false;
		audio.addEventListener('ended', function () {  
		     $(".media-btn").removeClass('do-wave');
		}, false);
		audio.play();
	}
});
$(".con img").click(function(){
	var w = $(this).width();
	var h = $(this).height();
	var pswpElement = document.querySelectorAll('.pswp')[0];
	var items = [
	    {
	        src: this.src,
	        w: w,
	        h: h
	    }
	];
	var options = {
	    index: 0
	};
	var gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default, items, options);
	gallery.init();
});

$('.mark').click(function(){
	var i = $(this).data("i");
	var _this = this;
	$.post("markMySelfDoubt.htm",{doubtId:i},function(datas){
		if(datas.success){
			if($(_this).hasClass('marked')) {
                $(_this).removeClass('marked');
            }
            else {
                $(_this).addClass('marked');
            }
		}
	});
})
var moreInfo = $('.more-info');
$('.spread').on("click",function(){
	if( !$(this).hasClass('spread--act') ){
		$(this).addClass('spread--act');
        moreInfo.css("height",'103px');
    }else{
    	$(this).removeClass('spread--act');
        moreInfo.css("height",'0px');
    }
})


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

var word = $("#jWord").val();

LekeHeader.appendMenu("init",[{
   	"id" : 1,
   	"groupId" : 1,
   	"groupOrder" : 0,
   	"order":0,
   	"icon" : "",
   	"title" : word,
   	"count" : 0,
   	"isShowNum" : false,
   	"fun" : function(){
   		var doubtId = $("#jDoubtId").val();
   		var resolved = $("#jResolved").val();
   		window.location.href = "explain.htm?doubtId="+doubtId+"&resolved="+resolved;
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
