var tchDecList =function(){
	decList.call(this);	//继承 /homework/vacationWork/DecList.js
	this.RowBind=function(data){
	
	//data.url=data.homeworkDtlId;//
	
	data.btnName="查看";
	//var flag =(typeof(data.submitStatus)!='undefined')&&data.submitStatus>0;
	//data.btnClass= flag?"view":"lesson-btn-cannot-work";
	data.btnClass= "view"

	};
	
	$(".view").live("click",function(){
		var homeworkDtlId =$(this).data("hdid");
		var submitStatus =$(this).data("state");
		//alert("window.homework.viewZy(homeworkDtlId),homeworkDtlId:"+homeworkDtlId);
//		window.homework.viewZy(homeworkDtlId,submitStatus);
		LeKeBridge.sendMessage2Native('viewZy',JSON.stringify({homeworkDtlId:homeworkDtlId,submitStatus:submitStatus}));
	});

    
    	
};
tchDecList.prototype =new decList();
var c =new tchDecList();
c.init();