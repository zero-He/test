var stuWkList =function(){	
	wkList.call(this);		//继承 /homework/vacationWork/DecList.js
		this.RowBind=function(data){

	    var flag =(typeof(data.exerciseId)!='undefined')&&data.exerciseId.length>0;
		var btnClass=flag?"answer":"lesson-btn-cannot-work";
	    var json= "({exerciseId:'"+data.exerciseId+"',knowledgeId:'"+data.kId+"',microId:'"+data.microId+"',microState:'"+data.microState+"',microHwState:'"+data.microHwState+"',knowledgeName:'"+data.kName+"'})";
        var btnName=flag&&(data.microHwState==1)?"查看练习":"练习"; 
       
        var btn = '<input type="button" class="task-li-btn '+btnClass+'" data-json="'+json+'"  value="'+btnName+'"/>'
		 data.btns =btn;	
		 flag =((typeof(data.microState)!='undefined')&&data.microState!=0)	
		 btnName=flag?"重新学习":"开始学习";
		 btnClass="study"
		 var url ="/auth/hd/student/vacationWork/WkStudy.htm?microcourseId="+data.microId;
         btn = '<input type="button" class="task-li-btn '+btnClass+'"  data-url="'+url+'" value="'+btnName+'"/>'
		 data.btns +=btn;
		
		


	};
	
	//作业
	$(".answer").live("click",function(){
		 var json=$(this).data("json");
		 json =eval(json);
		var microState =json.microState;
		if(microState!='1'){
		 Utils.Notice.print('请先学习微课再做练习！',3000);
		 return;
		}
		json.subjectId=$("input[name='subjectId']").val();
		json.subjectName=$("input[name='subjectName']").val();
	
	   var url ="/auth/hd/common/holiday/generateExerciseForMicro.htm";
	   	$.post(url,json , function(datas) {      
	           			 if(datas.success==true){      
	               			  var page =datas.datas;
	               			  if(page!=null&&page.exerciseId!=null){
								  var exerciseId =page.exerciseId;
								  if(exerciseId.length>0){
//									window.homework.doMicroCourseHomework(exerciseId,json.microHwState)
									LeKeBridge.sendMessage2Native('doMicroCourseHomework',JSON.stringify({"exerciseId":exerciseId,"microHwState": json.microHwState	}));
								  }
								  else{
								  	Utils.Notice.print('对不起，该微课暂无练习！',3000);
								  }
								  
	               			  }
	               			  else{
	               			  	Utils.Notice.print('对不起，该微课暂无练习！',3000);
	               			  }
							}
              		  });

	});
	
	//学习
	$(".study").live("click",function(){
			var url =$(this).data("url");
			url+="&id="+$("[name='id']").val();
		window.open(url,'_blank');
	});
    
    	
  
};
stuWkList.prototype =new wkList();
var c =new stuWkList();
c.init();
