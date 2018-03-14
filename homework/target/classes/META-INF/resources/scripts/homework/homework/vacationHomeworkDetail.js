define(function(require, exports, module) {
	var $ = require('jquery');
	var date = require('common/base/date');

	var Page = require('page');
	var Utils = require('utils');
	var http = require('common/base/http');
	
	var Handlebars = require("common/handlebars");
	
	require("common/ui/ui-echarts/echarts-all-current.js");
	
	var isTeacherHeader = $("input[name='isTeacherHeader']").val();
	var isTeacher = $("input[name='isTeacher']").val();
	var isParent = $("input[name='isParent']").val();
	var isStudent = $("input[name='isStudent']").val();
	
	var roleName = "student";
	if(isTeacherHeader=="true"){
		roleName = "classTeacher";
	}else if(isTeacher=="true"){
		roleName = "teacher";
	}else if(isParent=="true"){
		roleName = "parent";
	}
	var MyHomeworkMailCharts = {
		
		init: function(container,remain){
			
			var myChart = echarts.init(document.querySelector(container));
			
		    // 配置和数据
		    var labelFromatter = {
		        normal: { label: { ormatter: function (params){ return 100 - params.value + '%'; }, textStyle: {  baseline: 'top' } } }
		    };

		    var labelTop = {
		        normal: {color: '#0ba29a',label: {show: true,position: 'center',formatter: '{b}',textStyle: {baseline: 'bottom'}},
		            labelLine: {show: false}}
		    };

		    var labelBottom = {
		        normal: {color: '#ccc',label: {show: true,position: 'center'},
		            labelLine: {show: false}},
		        emphasis: {color: 'rgba(0,0,0,0)'}
		    };
		    var data = remain;
		    var baseData = 100 - data;
		    var option = {
		    	series: [
	                {
	                    type: 'pie',                   // 图像
	                    center: ['50%', '50%'],        // 位置
	                    radius: ['75%', '100%'],       // 圆形大小与比例
	                    itemStyle: labelFromatter,
	                    data: [
	                        {name: '作业进度', value: data, itemStyle: labelTop},
	                        {name: data+"%", value: baseData, itemStyle: labelBottom}
	                    ]
	                }
	            ]
	        };

		    myChart.setOption(option);
		}
	};
	
	var id=$("input[name='id']").val();
	
	var isTeacherHeader = $("input[name='isTeacherHeader']").val();
	var isTeacher = $("input[name='isTeacher']").val();
	var isParent = $("input[name='isParent']").val();
	var isStudent = $("input[name='isStudent']").val();
	
	var roleName = "student";
	if(isTeacherHeader=="true"){
		roleName = "classTeacher";
	}else if(isTeacher=="true"){
		roleName = "teacher";
	}else if(isParent=="true"){
		roleName = "parent";
	}
	
	
	var MyHomeworkMailInfo = {
		
		loadHomeworkMailInfo : function(){
			
			var contextID = "#homeworkMainInfo";

			http.post(ctx + '/auth/common/holiday/ajax/getHolidayHwMicroSummary.htm',{id:id})
			.then(function(data){

				data = data.summary;
				data.remain = data.total - data.finish;
				data.title = (data.year+(data.holiday==1?"寒假作业":"暑假作业")+" • "+data.subjectName)
				data.roleName = roleName;
				data.firstTime = data.firstTime==null?"--": date.format(data.firstTime,'yyyy-MM-dd HH:mm:ss');
				Handlebars.render(contextID, data, contextID);
				
				MyHomeworkMailCharts.init(".canvas",Math.round(data.finish/(data.total==0?1:data.total)*100));
				$(contextID).show();
			});
		},
		init:function(){
			this.loadHomeworkMailInfo();
		}
			
	};
	MyHomeworkMailInfo.init();
	
	
	var MyHomeworkList = {
		
			
		fmtState:	function (submitTime,correctTime,bugFixStage){
				if(submitTime == null){
					return '<span class="s-c-orange">未提交</span>';
				}else{
					if(bugFixStage == 0){
						if(correctTime != null){
							return '<span class="s-c-green">已批改</span>';
						} 	
						if(correctTime == null){
							return '<span class="s-c-green">待批改</span>';
						} 	
					}
					if(bugFixStage == 1){
						return '待订正';
					} else if(bugFixStage == 2){
						return '已订正 '
					}else if(bugFixStage == 3) {
						return '订正通过';
					}

				}
		},
		loadHomeworkList : function(){
			var _this = this;
			var contextID = "#myHomeworkList";
			this.page = Page.create({
				id : 'page',

				url : ctx + '/auth/common/holiday/ajax/getStuSubjectHomeworkInfo.htm',
				pageSize : 10,
				formId : 'myHomeworkListForm',
				tipsId:'emptyTip',
				callback : function(datas) {
				
					var ds = datas.page.dataList;
					for(var index=0 ;index<ds.length ;index++){
						var d = ds[index];
						d.score = d.score==null?"--": d.score.toFixed(1);
						d.homeworkStatus = _this.fmtState(d.submitTime,d.correctTime,d.bugFixStage);
						d.submitTime = d.submitTime==null?"--":date.format(d.submitTime,'yyyy-MM-dd HH:mm:ss');
						
						//0：未提交，1：正常提交，2：延迟提交
						//订正进度, 1:待订正,2:待复批,3:订正通过
						
						if(isTeacherHeader=="true"){
							//d.roleName = "classTeacher";
							d.btnName="查看";
							d.btnUrl="/auth/classTeacher/homework/viewWork.htm?homeworkDtlId="+d.homeworkDtlId;
							
						}else if(isTeacher=="true"){
							//d.roleName = "teacher";
							d.btnName="查看";
							d.btnUrl="/auth/teacher/homework/viewWork.htm?homeworkDtlId="+d.homeworkDtlId;
							
						}else if(isParent=="true"){
							//d.roleName = "parent";
							d.btnName="查看";
							d.btnUrl="/auth/parent/homework/viewWork.htm?homeworkDtlId="+d.homeworkDtlId;
						
						}else{
							d.roleName = "student";
							if(d.submitStatus == 0){
								d.btnName="答题";
								d.btnUrl="/auth/student/homework/doWork.htm?homeworkDtlId="+d.homeworkDtlId;
							}else {
								if(d.bugFixStage == 1){
									d.btnName="订正";
									d.btnUrl="/auth/student/homework/doBugFix.htm?homeworkDtlId="+d.homeworkDtlId;
								}else{
									d.btnName="查看";
									d.btnUrl="/auth/student/homework/viewWork.htm?homeworkDtlId="+d.homeworkDtlId;
								}
							}
						}
					}
					Handlebars.render("#myHomeworkList", datas.page,"#myHomeworkList");
					$(contextID).show();
				}
			});
		},
		init:function(){
			this.loadHomeworkList();
		}
	};
	
	MyHomeworkList.init();
});
