
var Statistics = {

	init : function() {
		if(this.getRoleName() == "parent"){
			this.loadChildren();
		}else{
			this.page();
			$("#childrenHeader").hide();
		}
		this.bindEvent();
	},
	
	getRoleName : function(){
		var roleName = "student";
		var currentRoleId = $("#roleId").val();
		if(currentRoleId == 100){
			roleName = "student";
		}else if(currentRoleId == 101){
			roleName = "teacher";
		}else if(currentRoleId == 102){
			roleName = "parent";
		}else if(currentRoleId == 103){
			roleName = "classTeacher";
		}else if(currentRoleId == 104){
			roleName = "provost";
		}
		return roleName;
	},
	
	bindEvent : function(){
		
		$("#jtbodyData").on("click","#viewDtl",function(){
			var examReportId = $(this).data('id');
			var studentId = $(this).next().html();
			window.location.href = "/auth/m/"+Statistics.getRoleName()+"/examReport/toShowStuExamReportPhone.htm?examReportId="+examReportId+"&studentId="+studentId;
		});
		
		$("#ulbodyData").on("click","li",function(){
			var studentId = $(this).data('id');
			$(this).siblings().removeClass("active");
			$(this).addClass("active");
			Statistics.page(studentId);
		});
	},
	
	loadChildren : function(){
		$.post("/auth/m/"+Statistics.getRoleName()+"/examReport/findChildrenByParentId.htm", {}, function(json){
			var data = json.datas;
			if (data) {
				var output = Mustache.render($("#chidrenTpl").html(), data);
				if(data.childrenList.length > 1){
					$("#childrenHeader").show();
				}else{
					$("#childrenHeader").hide();
				}
				$("#ulbodyData").html(output);
				
				var studentId = data.childrenList[0].studentId;
				$("li").first().addClass("active");
				Statistics.page(studentId);
			} else {
				$("#ulbodyData").html('');
			}
		});
	},
	
	page : function(studentId){
		$(window).page({
		    url: "/auth/m/"+Statistics.getRoleName()+"/examReport/findExamReport.htm",
		    queryData: {studentId : studentId},
		    curPage: 1,
		    loadTips: {                     
		    	loading: '<i></i>数据加载中',
		    	loaded: '没有更多数据',
		    	noData: '暂无成绩单数据'
		    },
		    scrollCont: $('#jtbodyData')[0],
		    callbackPullDown: function(page) {
				var data = page;
				if (data) {
					var output = Mustache.render($("#examReportListPhoneTpl").html(), data);
					$('#jtbodyData').append(output);
				} else {
					$('#jtbodyData').html('');
				}
		    }
		});
	
	}

};

Statistics.init();
