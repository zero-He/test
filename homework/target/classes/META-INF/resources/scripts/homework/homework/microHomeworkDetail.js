define(function(require, exports, module) {
	var $ = require('jquery');

	var date = require('common/base/date');

	var Page = require('page');
	var Utils = require('utils');
	var http = require('common/base/http');
	
	var Handlebars = require("common/handlebars");
	
	require("common/ui/ui-echarts/echarts-all-current.js");
	
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
	                        {name: '学习进度', value: data, itemStyle: labelTop},
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
				data.title = data.gradeName +"("+(data.semester==1?"上":"下")+")"+" • "+data.subjectName;
				data.roleName = roleName;
				data.firstTime = data.firstTime==null?"--":date.format(data.firstTime,'yyyy-MM-dd HH:mm:ss');
				data.bookName = data.bookName;
				data.matVersionName = " • "+data.matVersionName;
				
				$("#subjectName").val(data.subjectName);
				$("#subjectId").val(data.subjectId);
				
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
			
		loadHomeworkList : function(){
			var contextID = "#myHomeworkList";
			this.page = Page.create({
				id : 'page',
				
				url : ctx + '/auth/common/holiday/ajax/getStuSubjectMicroInfos.htm',
				pageSize : 10,
				formId : 'myHomeworkListForm',
				tipsId:'emptyTip',
				callback : function(datas) {
					var ds = datas.page.dataList;
					for(var index=0 ;index<ds.length ;index++){
						d = ds[index];
						d.accuracy = (d.accuracy==null?"--":((d.accuracy*100)+"%"));
						
						if(isTeacherHeader=="true"){
							//d.roleName = "classTeacher";
							d.btnName="查看";
							d.btnUrl="/auth/classTeacher/exercise/doExerciseWork.htm?id="+d.exerciseId;
							
						}else if(isTeacher=="true"){
							//d.roleName = "teacher";
							d.btnName="查看";
							d.btnUrl="/auth/teacher/exercise/doExerciseWork.htm?id="+d.exerciseId;
							
						}else if(isParent=="true"){
							//d.roleName = "parent";
							d.btnName="查看";
							d.btnUrl="/auth/parent/exercise/doExerciseWork.htm?id="+d.exerciseId;
						
						}else{
							 flag =((typeof(d.microState)!='undefined')&&d.microState!=0)	
							 d.btnName=flag?"重新学习":"开始学习";
							 d.workClass = flag?"":"u-btn-bg-gray";
							 
							 d.btnWorkName=(d.microHwState==1)?"查看练习":"练习";
							 
						}
					}
					Handlebars.render(contextID, datas.page,contextID);
					$(contextID).show();
				}
			});
		},
		init:function(){
			this.loadHomeworkList();
			this.binEvent(0);
		},binEvent : function() {
			$("a[data-type='learn']").live("click",function(){
			
				var t = $(this);
				var id = t.attr("data-id");
				var microId = t.attr("data-microId");
				if(isStudent=="true"){
					window.open("/auth/student/preview/viewMicro.htm?id="+id+"&microcourseId="+microId);
				}else{
					window.open("/auth/"+roleName+"/preview/viewMicro.htm?id="+id+"&microcourseId="+microId);
				}
			});
			$("a[data-type='work']").live("click",function(){
				
				var t = $(this);
				var learn = t.attr("date-microstate");
				if((typeof(learn)!='undefined')&&learn!=0){
					
					var exerciseId = t.attr("data-exerciseId");
					var knowledgeId = t.attr("data-knowledgeId");
					var knowledgeName = t.attr("data-knowledgeName");
					
					var subjectName = $("#subjectName").val();
					var subjectId = $("#subjectId").val();
					
					$.ajax({url:ctx + '/auth/common/holiday/generateExerciseForMicro.htm',
						async:false,
						data:{subjectName:subjectName,subjectId:subjectId, exerciseId:exerciseId,knowledgeId:knowledgeId,knowledgeName:knowledgeName},
						success:function(data){
							if(data.datas.exerciseId!=null){
								window.open("/auth/student/exercise/doExerciseWork.htm?id="+exerciseId);
							}else{
								Utils.Notice.alert('对不起，该微课暂无练习！');
							}
					}});
				}else{
					Utils.Notice.alert('请先学习微课再做练习！');
				}
			});
			$("a[data-type='view']").live("click",function(){
				var t = $(this);
				var microHwState = t.attr("data-microHwState");
				var exerciseId = t.attr("data-id");
				
				if(microHwState==1){
					window.open("/auth/"+roleName+"/exercise/doExerciseWork.htm?id="+exerciseId);
				}else{
					Utils.Notice.alert('学生还未提交练习！');
				}
			
			});
		}
			
	};
	MyHomeworkList.init();

});
