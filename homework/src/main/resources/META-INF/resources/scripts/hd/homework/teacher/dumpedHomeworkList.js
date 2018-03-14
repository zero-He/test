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
		url : '/auth/hd/teacher/homework/loadDumpedHomeworkList.htm',
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
			var source = $("#jTpl").html();
			var html = (Handlebars.compile(source))(page);
			$("#jScrollContEle").append(html);
		}
	});
}

createPage();

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
        	chao.btn.toggleClass('item-on')
            chao.content.toggle()
        })
        chao.content.find('.select-item')[chao.event](function(ev){
            var $index=$(this).index();
            var val = $(this).text();
            chao.callback($index,val,this);
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
    btn:$('#jResTypeOpt'),
    content:$('#jResTypeList'),
    callback:function(index,val){
        $("#jResType").val(index);
        $("#jResTypeOpt").text("资源类型："+val);
        createPage();
    }
})

PullDown.fun({
    btn:$('#jHomeworkOpt'),
    content:$('#jHomeworkList'),
    callback:function(index,val,v){
        $("#jHomeworkType").val($(v).data("i"));
        $("#jHomeworkOpt").text("作业类型："+val);
        createPage();
    }
})

$("#jScrollContEle").on("click",".j-delete",function(){
	var i = $(this).data("i");
	Utils.Notice.confirm('一旦删除，试卷将无法找回，是否确认永久删除？',function(flag){
		if(flag){
			$.post('/auth/hd/teacher/homework/del.htm',{id:i},function(json){
				if(json.success){
					createPage();
				}
			});
		}
	});
}).on("click",".j-recover",function(){
	var i = $(this).data("i");
	$.post('/auth/hd/teacher/homework/recover.htm',{id:i},function(json){
		if (json.success) {
			createPage();
		} else {
			Utils.Notice.print(json.message);
		}
	});
});

