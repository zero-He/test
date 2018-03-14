function createdPage(){
	$("#jDroploadId").page({
		url : 'getDoubtList.htm',
	    curPage: 1,
	    form: $("#jForm")[0],
	    scrollCont: $("#jListBody")[0],
	    callbackPullDown: function(datas) {
	    	var doubtCasType = function () {
	    		if(this.source==1){
					return 'c-doubter__type--outclass';
				}else if(this.source==2){
					return 'c-doubter__type--classname';
				}else if(this.source==3){
					return 'c-doubter__type--inclass';
				}else if(this.source==4){
					return 'c-doubter__type--demand';
				}else{
					return 'c-doubter__type--outclass';
				}
			}
			datas.doubtCasType = doubtCasType;
			var output = Mustache.render($("#jListTpl").html(), datas);
			$("#jListBody").append(output);
	    }
	});
}

createdPage();

$(".j-resolved").click(function(){
	var i = $(this).data("i");
	if(!$(this).hasClass("cur")){
		$(this).addClass("cur");
		$(this).siblings().removeClass("cur");
		$("#jResolved").val(i);
	}
	createdPage();
});

var Question = {
        init: function () {
            this.checkIcon();
            this.starIcon();
            this.checkAll();
            this.deleteChecked();
        },
        checkAll:function(){
        	$("#jCheckAll").click(function(){
        		if($(this).text()=='全选'){
        			$(".c-doubter__select").addClass('c-doubter__select--act');
        			$(this).text("反选");
        		}else{
        			$(".c-doubter__select").removeClass('c-doubter__select--act');
        			$(this).text("全选");
        		}
        	});
        },
        deleteChecked:function(){
        	$("#jDelete").click(function(){
        		var ids = [];
        		$(".c-doubter__select--act").each(function(){
        			var i = $(this).closest("div").data("i");
        			ids.push(i);
        		});
        		if(ids.length>0){
        			Utils.Notice.confirm("确认删除吗？",function(sure){
        				if(sure){
        					$.post("deleteMySelfDoubt.htm",{doubtIds:ids.join(",")},function(datas){
        						createdPage();
        					});
        				}
        			});
        		}else{
        			Utils.Notice.alert("请选择要删除的条目");
        		}
        	});
        },
        checkIcon: function () {
        	$("#jListBody").on('click','.c-doubter__select', function () {
                if($(this).hasClass('c-doubter__select--act')) {
                    $(this).removeClass('c-doubter__select--act');
                }
                else {
                    $(this).addClass('c-doubter__select--act');
                }
            });
        },
        starIcon: function () {
            $("#jListBody").on('click','.c-doubter__collect', function () {
            	var i = $(this).data("i");
            	var _this = this;
            	$.post("markMySelfDoubt.htm",{doubtId:i},function(datas){
					if(datas.success){
						if($(_this).hasClass('c-doubter__collect--done')) {
		                    $(_this).removeClass('c-doubter__collect--done');
		                }
		                else {
		                    $(_this).addClass('c-doubter__collect--done');
		                }
					}
				});
            });
        }
    }
Question.init();

function CSearch(){
    this.pageContent = document.querySelector('.c-content');
    this.init();
}

function changeSubjects(){
	var subjects = [];
	$(".j-subject .c-searchbar__fcheck--act").each(function(){
		subjects.push($(this).data("i"));
	});
	$("#jSubject").val(subjects.join(","));
}

CSearch.prototype = {
    init: function(){
        this.iptFocus();
        this.filterCtrl();
        this.searchCtrl();
        this.selectCtrl();
    },
    iptFocus: function(){
        var inputFake = document.querySelector('.c-searchbar__fake'),
            inputForm = document.querySelector('.c-searchbar__iptform'),
            inputReal = document.querySelector('.c-searchbar__ipt');
        
        inputFake.addEventListener('touchend', function(){
            this.style.display = 'none';
            inputForm.style.display = 'block';
            inputReal.focus();
        })
        inputReal.addEventListener('blur', function(){
            if( this.value === '' ){
                inputForm.style.display = 'none';
                inputFake.style.display = 'block';
            }
        })
        $(".c-searchbar__ipt").on('keypress',function(e) {  
            var keycode = e.keyCode;  
            if(keycode=='13') {  
                e.preventDefault();
                createdPage();
            }  
     });  
    },
    selectCtrl: function(){
    	var self = this,
    	fBtn = $('.c-searchbar__filterbtn'),
    	fPanel = $('.c-searchbar__filter');
    	$(".j-source span").click(function(){
    		$(this).siblings().removeClass("c-searchbar__fcheck--act");
    		$(this).addClass("c-searchbar__fcheck--act");
    		$("#jSource").val($(this).data("i"));
    	});

    	$("#jCleanSubject").click(function(){
    		$(".j-subject span").removeClass("c-searchbar__fcheck--act");
    		changeSubjects();
    	});

    	$(".j-subject span").click(function(){
    		if($(this).hasClass("c-searchbar__fcheck--act")){
    			$(this).removeClass("c-searchbar__fcheck--act");
    		}else{
    			$(this).addClass("c-searchbar__fcheck--act");
    		}
    		changeSubjects();
    	});
    	$('#jSearch').on('click',function(){
        	createdPage();
        	fBtn.removeClass('c-searchbar__filterbtn--act');
			fPanel.fadeOut();
			self.pageContent.style.overflowY = 'auto';
        });
    },
    filterCtrl: function(){
        var self = this,
            fBtn = $('.c-searchbar__filterbtn'),
            fPanel = $('.c-searchbar__filter');
        fBtn.on('touchend', function(){
            if( !this.classList.contains('c-searchbar__filterbtn--act') ){
            	$(this).addClass('c-searchbar__filterbtn--act');
                fPanel.fadeIn();
                self.pageContent.style.overflowY = 'hidden';
            }else{
                $(this).removeClass('c-searchbar__filterbtn--act');
                fPanel.fadeOut();
                self.pageContent.style.overflowY = 'auto';
            }
        })
    },
    searchCtrl: function(){
        var ctrlCon = document.querySelector('.ctrl-con'),
        	doubtCon = document.querySelector('.c-doubter__con'),
            startX, startY, endX, endY,
            dir = null,
        	self = this;

        doubtCon.addEventListener('touchstart', function(event){
            startX = event.touches[0].pageX;
            startY = event.touches[0].pageY;
        });
		doubtCon.addEventListener('touchmove', function(e){
            endX = e.touches[0].pageX;
            endY = e.touches[0].pageY;
            dir = ( Math.abs(endY-startY) > Math.abs(endX-startX)) ? ( endY-startY > 0 ? 'up' : 'down') : (endX-startX > 0 ? 'right' : 'left');
		});
        doubtCon.addEventListener('touchend', function(){
            if( dir === 'up' ){
                self.pageContent.classList.add('c-content--fixed');
            }else{
                self.pageContent.classList.remove('c-content--fixed');
            }
        });
    }
}

var cSearch = new CSearch();


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

LekeHeader.appendMenu("list_cancel",[{
   	"id" : 4,
   	"groupId" : 1,
   	"groupOrder" : 0,
   	"order":0,
   	"icon" : "",
   	"title" : "取消",
   	"count" : 0,
   	"isShowNum" : false,
   	"fun" : function(){
   		LekeHeader.showMenu("list_init");
   		$('.c-check-opts').addClass('c-hidden');
   		$('#jListBody').removeClass("ul-p-show");
   		$('.c-doubter__con').removeClass('c-doubter__con--edit');
   		$(".c-doubter__select").removeClass('c-doubter__select--act');
   	}
   }]);
LekeHeader.appendMenu("list_init",[{
   	"id" : 1,
   	"groupId" : 1,
   	"groupOrder" : 0,
   	"order":0,
   	"icon" : "",
   	"title" : "全部",
   	"count" : 0,
   	"isShowNum" : false,
   	"fun" : function(){
   		$("#jCollect").val(0);
   		createdPage();
   	}
   },
   {
   	"id" : 2,
   	"groupId" : 1,
   	"groupOrder" : 0,
   	"order":1,
   	"icon" : "",
   	"title" : "收藏",
   	"count" : 0,
   	"isShowNum" : false,
   	"fun" : function(){
   		$("#jCollect").val(1);
   		createdPage();
   	}
   },
   {
   	"id" : 3,
   	"groupId" : 1,
   	"groupOrder" : 0,
   	"order":0,
   	"title" : "编辑",
   	"count" : 0,
   	"isShowNum" : false,
   	"fun" : function(){
   		LekeHeader.showMenu("list_cancel");
   	    $('.c-doubter__con').addClass('c-doubter__con--edit');
   	    $('.c-check-opts').removeClass('c-hidden');
   	}
   }
]);
document.addEventListener("deviceready", function(){
	setTimeout(function(){
		LekeHeader.showMenu("list_init");
	}, 500);
	}, false);

function onLoad(){
	LekeHeader.showMenu("list_init");
}