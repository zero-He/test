var doubtId = null;
(function(){
	var initFirst = true;
	function createdPage(){
		$("#jDroploadId").page({
			url : 'getDoubtList.htm',
		    curPage: 1,
		    form: $("#jForm")[0],
		    scrollCont: $("#jPageListBody")[0],
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
				var output = Mustache.render($("#jPageListTpl").html(), datas);
				$("#jPageListBody").append(output);
				if(initFirst){
					$("#jPageListBody .c-doubter").eq(0).click();
		    	}
				SwipeRemove.init(deleteDoubt);
		    }
		});
	}
	
	//删除
	function deleteDoubt(id,fun){
		Utils.Notice.confirm("确认删除吗？",function(sure){
			if(sure){
				$.post("deleteMySelfDoubt.htm",{doubtIds:id},function(datas){
					fun();
					$("#jDoubtDetail").html('<div class="c-doubter__nodata"></div>');
					$(".z-doubt__main").removeClass("z-doubt__main--visible");
					$("#jTopSubmit").hide();
				});
			}
		});
	}
	//改变学科
	function changeSubjects(){
		var subjects = [];
		$(".j-subject .c-searchbar__fcheck--act").each(function(){
			subjects.push($(this).data("i"));
		});
		$("#jSubject").val(subjects.join(","));
	}
	
	function init(){
		createdPage();
	}
	
	function initMediaBtn(){
		$(".media-btn").click(function(){
			var audio = $(".do-wave").siblings()[0];
			if(audio){
				audio.pause();
			}
			$(".media-btn").removeClass("do-wave");
			$(this).addClass("do-wave");
			audio = $(this).siblings()[0];
			if(audio){
				audio.loop = false;
				audio.addEventListener('ended', function () {  
				     $(".media-btn").removeClass('do-wave');
				}, false);
				audio.play();
			}
		});
	}
	$("#jPageListBody").on("click",".c-doubter",function(){
		doubtId = $(this).data("i");
		$("#jAsking").show();
		$("#jTopSubmit").show().html($("#jTopSubmit").data("w")).data("t","");
		$.post("detail.htm",{doubtId:doubtId},function(data){
			$("#jDoubtDetail").html(data);
			$(".z-doubt__main").removeClass("z-doubt__main--visible");
			initMediaBtn();
		})
	});
	
	//返回
	$(".c-header__back").click(function(){
		LeTalkCorePlugin.close()
	});

	//输入框按钮
	$(".c-searchbar__ipt").on('keypress',function(e) {  
	    var keycode = e.keyCode;  
	    if(keycode=='13') {  
	        e.preventDefault();
	        createdPage();
	    }  
	}); 

	//选择来源
	$(".j-source span").click(function(){
		$(this).siblings().removeClass("c-searchbar__fcheck--act");
		$(this).addClass("c-searchbar__fcheck--act");
		$("#jSource").val($(this).data("i"));
	});
	
	//选择收藏
	$(".j-collect span").click(function(){
		$(this).siblings().removeClass("c-searchbar__fcheck--act");
		$(this).addClass("c-searchbar__fcheck--act");
		$("#jCollect").val($(this).data("i"));
	});
	
	

	//清空学科
	$("#jCleanSubject").click(function(){
		$(".j-subject span").removeClass("c-searchbar__fcheck--act");
		changeSubjects();
	});

	//选择学科
	$(".j-subject span").click(function(){
		if($(this).hasClass("c-searchbar__fcheck--act")){
			$(this).removeClass("c-searchbar__fcheck--act");
		}else{
			$(this).addClass("c-searchbar__fcheck--act");
		}
		changeSubjects();
	});

	//查询按钮
	$('#jSearch').on('click',function(){
		createdPage();
		$('.c-searchbar__filterbtn').removeClass('c-searchbar__filterbtn--act');
		$('.c-searchbar__filter').fadeOut();
	});

	//点击显示赛选项
	$('.c-searchbar__filterbtn').on('touchend', function(){
	    if( !this.classList.contains('c-searchbar__filterbtn--act') ){
	    	$(this).addClass('c-searchbar__filterbtn--act');
	    	$('.c-searchbar__filter').fadeIn();
	    }else{
	        $(this).removeClass('c-searchbar__filterbtn--act');
	        $('.c-searchbar__filter').fadeOut();
	    }
	})

	//收藏
	$("#jPageListBody").on('click','.c-doubter__collect', function () {
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

	$("#jAsking").click(function(){
		$.post("ask.htm",function(data){
			$("#jAsking").hide();
			$("#jTopSubmit").data("t","ask");
			$("#jTopSubmit").html("提交");
			$("#jDoubtDetail").html(data);
			$(".z-doubt__main").addClass("z-doubt__main--visible");
			DoubtAsk.init();
		})
	});

	$("#jTopSubmit").click(function(){
		var type = $(this).data("t");
		if(type=="ask"){
			//提问时
			DoubtAsk.submit(function(){
				$(this).data("t","");
				createdPage();
			});
		}else if(type=="explain"){
			//回答时
			DoubtExplain.submit(function(){
				$(this).data("t","");
				$("#jPageListBody .c-doubter [data-i='"+doubtId+"']").click();
			});
		}else if(!type){
			//type不存在时 展示追问或回答页面
			$.get("explain.htm",{doubtId:doubtId},function(data){
				$("#jDoubtDetail").html(data);
				$(".z-doubt__main").addClass("z-doubt__main--visible");
				$("#jAsking").hide();
				$("#jTopSubmit").data("t","explain");
				$("#jTopSubmit").html("提交");
				DoubtExplain.init();
			})
		}
	});
	//详情下拉
	$(document).on('touchend','.c-questioner__spread', function(){
        var moreInfo = $('.c-questioner__moreinfo');
        if( !$(this).hasClass('c-questioner__spread--act') ){
        	$(this).addClass('c-questioner__spread--act');
        	moreInfo.addClass('c-questioner__moreinfo--act');
        }else{
        	$(this).removeClass('c-questioner__spread--act');
        	moreInfo.removeClass('c-questioner__moreinfo--act');
        }
    }).on("click",".c-answer__text img",function(){
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
    
	init();
})();