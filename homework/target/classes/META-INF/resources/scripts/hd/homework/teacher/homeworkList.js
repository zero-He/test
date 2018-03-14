Handlebars.registerHelper({
		cif : function(expression, options) {
			if (eval(expression)) {
				return options.fn(this);
			} else {
				return options.inverse(this);
			}
		},
		expression : function(expression, options) {
			return eval(expression);
		}
	});

function createPage(){
	$("#jScrollArea").page({
		url : '/auth/hd/teacher/homework/loadHomeworkList.htm',
		form: $("#jForm")[0],
		scrollCont: $("#jScrollContEle")[0],
		callbackPullDown: function(page) {
			$.each(page.dataList, function() {
				var self = this;
				self.avgScore = Utils.Number.toFixed(self.avgScore, 1);
				var studentGroups = eval(self.studentGroupsJson);
				var className = self.className == null ? "" : self.className;
				var classNameStr = '';
				if(studentGroups&&studentGroups.length>0){
					for(var i=0;i<studentGroups.length;i++){
						var n = studentGroups[i];
						className += ' ' + n.groupName;
					}
				}
				if(className.length > 10){
					classNameStr = className.substring(0,10) + '...';
				}else{
					classNameStr = className;
				}
				this.className = className;
				this.classNameStr = classNameStr;
			});
			page.isInvalid = function() {
				return this.status == 2 ? true : [];
			}
			page.isInclass = function() {
				return this.homeworkType == 2;
			}
			//var output = Mustache.render($("#homeworkContext_tpl").html(), page);
			//Handlebars.render("#homeworkContext_tpl", page,'#homeworkListContext');
			var source = $("#jTpl").html();
			if($("#jHomeFinishFlag").val()=='reCorrect'){
				source = $("#jReCorrectTpl").html();
			}
			var html = (Handlebars.compile(source))(page);
			$("#jScrollContEle").append(html);
		}
	});
}

createPage();

$(".c-tab-nav").on("click",".item",function(){
	$(this).addClass('item-on').siblings().removeClass('item-on');
	$("#jHomeFinishFlag").val($(this).data("i"));
	$("#jHomeworkName").val("");
	$("#jHomeworkNameSearch").val("");
	$("#jResType").val("");
    $(".search-type .item").text("全部");
	createPage();
});

$('.c-input-delete').click(function(ev){
    ev.stopPropagation();
    $(".c-search-top").hide();
	$(".c-tab-nav").show();
})

$('.search-btn').click(function(){
    $("#jHomeworkName").val($("#jHomeworkNameSearch").val());
    createPage();
})

function showSearch(){
	$(".c-tab-nav").hide();
	$(".c-search-top").show();
	if($("#jHomeFinishFlag").val()=='all'){
		$(".search-type").show();
	}else{
		$(".search-type").hide();
	}
}

var PullDown=window.PullDown || {};
PullDown={
    fun:function(ta){
        var da={
            btn:$('element'),
            event:"click",
            content:$('element'),
            callback:function(){} || "",
            flag:true,
        }
        var chao=$.extend(da,ta);
        chao.btn[chao.event](function(ev){
            $(this).toggleClass('item-on')
            chao.content.toggle()
        })
        chao.content.find('.select-item')[chao.event](function(ev){
            var $index=$(this).index();
            var val = $(this).text();
            chao.callback($index,val);
        })
        $('body')[chao.event](function(ev){
            var e=document.ev || ev;
            if(e.target != chao.btn[0]){
                chao.btn.removeClass('item-on')
                chao.content.hide();
            }
        })
    }
}

PullDown.fun({
    btn:$('.c-pullDown-tab .item'),
    content:$('.c-pullDown-tab .select-list'),
    callback:function(index,val){
        $("#jResType").val(index);
        $(".search-type .item").text(val);
        createPage();
    }
})

function initModal() {
    var btns = $('.j-more'),
            $cover = $('.cover'),
            $modalbox = $('.modal-box'),
            $item = $('.j-btns-item')
    $index=0;
    $("#jScrollContEle").on("click",".j-more",function(){
    	var type = $(this).data("t");
    	var id = $(this).data("i");
    	var openAnswer = $(this).data("o");
    	var s = $(this).data("s");
    	var c = $(this).data("c")||false;
    	modalShow(id,type,openAnswer,s&&!c);
    });
    $item.click(function(){
	    var i = $(this).data("i");
	    var m = $(this).data("m");
	    if(m=='open'){
	    	Utils.Notice.confirm("公布答案后，学生可以看到正确答案和解析，确定吗？",function(sure) {
	    		if (sure) {
	    			$.post('/auth/hd/teacher/homework/openAnswer.htm', {
	    				homeworkId : i
	    			}, function(message) {
	    				createPage();
	    			});
	    		}
	    	});
	    }else if(m=='proofread'){
	    	Utils.Notice.confirm('自行校对后，本次作业中未提交的和未批改的学生作业将默认不再批改，不影响已批改的学生作业批改情况，确定进行自行校对操作吗？',function(sure) {
				if (sure) {
					$.post("/auth/hd/teacher/homework/selfCheck.htm",{'homeworkId':i},function(data){
						if(data.success){
							createPage();
						}
					});
				}
			});
	    }else if(m=='invalid'){
	    	Utils.Notice.confirm("确认要作废此份作业吗？",function(sure) {
				if (sure) {
					$.post('/auth/hd/teacher/homework/homeworkInvalid.htm', {
						homeworkId : i
					}, function(message) {
						createPage();
					});
				}
			});
	    }else if(m=='cuijiao'){
			$.post('/auth/hd/teacher/homework/cuijiao.htm',{homeworkId:i},function(json){
				if(!json.success){
					return;
				}
				var prefix = '作业距离截止时间还有';
				if(json.datas.past){
					prefix ='作业已经超过截止时间';
				}
				var confirm_msg = prefix + json.datas.time +'，还有'+ json.datas.stuCount +'学生未提交作业，确认催交作业吗？'
				Utils.Notice.confirm(confirm_msg,function(sure) {
					if (sure) {
						$.post('/auth/hd/teacher/homework/doCuijiao.htm',{'homeworkId':i});
					}
				});
			})
	    }
    })
    $modalbox.click(function(ev){
        modalhide();
    })
    function modalShow(id,type,open,proofread){
        $cover.show();
        $modalbox.show();
        if(type==3){
        	$(".j-cuijiao").show();
        	if(!open){
        		$(".j-open").show();
        	}else{
        		$(".j-open").hide(); 
        	}
        	if(proofread){
        		$(".j-proofread").show();
        	}else{
        		$(".j-proofread").hide();
        	}
        }else{
        	$(".j-cuijiao").hide();
        	$(".j-open").hide();
        	$(".j-proofread").hide();
        }
        $item.data("i",id);
    }
    function modalhide(){
        $cover.hide();
        $modalbox.hide();
    }
}
initModal();
