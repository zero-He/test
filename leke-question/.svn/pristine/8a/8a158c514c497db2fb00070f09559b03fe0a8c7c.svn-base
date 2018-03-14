function listPage(){
	
};
listPage.prototype.init=function(){
    var	_this =this;
      
    this.initial();
	this.SetUserPref();

	
	$(".sc").live("click",function(){
		           var $obj=$(this);
		           var url ='/auth/common/workbook/jsonp/doUnFavorite.htm?callback=?';
		          // var df =_this.doFavorite
		       
		           Utils.Notice.confirm('是否取消收藏？',function(sure){
		           		if(!sure)return;
		          
			          
						var data ={'workbookId':$obj.parent().data("id")}
						Utils.HTTP.jsonp(url,data).then(function(data){
							_this.doUnFavorite($obj,data);
						},function(data){
							   Utils.Notice.print(data,2000);
						});
					
					
					});
	});
	
	
	
	
	$(".mx").live("click",function(){
					var $obj=$(this);
					var url =Leke.domain.paperServerName+'/auth/common/hd/workbook/view.htm?_newpage=1&workbookId='+$obj.data("id");
				     window.location.href= url;
	});
	
		$(".cxbtn").live("click",function(){
				$("#search").show();
				$(this).hide();
				//$(".sort .c-droplist li").first().click();
	});
	$(".celbtn").live("click",function(){
				$("#search").hide();
				$(".cxbtn").show();
			 // $("input[name='content']").val("");
	});

	var showKeys=new Array("pressId","pressName","materialId","materialName","materialNodeName","materialNodeId","materialNodePath");
	$(".spandel").live("click",function(){	
					
					var key =$(this).parent().data("key")
					$(this).parent().remove();
					 if(key=="content"){
				   	  var $tmp=$("[name='"+key+"']");
				        if($tmp){
				          	$tmp.val("");
			          	}	 
				   }
				   else{
						$.each(showKeys,function(index,value){   
							//if(key!=value)return true;       	   	      
						    var $tmp=$("[name='"+value+"']");
					        if($tmp){
					          	$tmp.val("");
				          	}	 
		        		});
				   }
			        _this.bind();
	});
	
	$(".sx").live("click",function(){

		  var json ={};
		  var req = HistoryServicePad.get()
    		.then(function(datas){
			     json =datas.pref;
			     json =$.extend(json,_this.getFormJson());
			      Leke.repo.pOpenWbFilter({ 
			      data: json||{}, 
			      stgAll: true,
			      onSelect: function(data) {
			    	  if(!data.schoolStageId){
			        		Utils.Notice.print('请选择学段！');
			        		return false;
			        	}
			        	if(!data.subjectId){
			        		Utils.Notice.print('请选择学科！');
			        		return false;
			        	}
			       _this.setFormData(data);
			         HistoryServicePad.put(data);
			       _this.bind();
			      }
			    });
			     
		  });
		 
	});
	
};

listPage.prototype.getFormJson=function(){		
	         var arr ={};
		 	 var array =$("form").serializeArray();
		 	 $.each(array,function(index,item){
			  	 if(item.value!=null&&item.value.length>0)
			  	 {
			  	  arr[item.name]=item.value;
			  	 }
			  	 //return  item.name+":"+item.value;
			  });  
			  return arr;
};
listPage.prototype.SetUserPref=function(){	
			var _this =this;
	         var req = HistoryServicePad.get()
    		.then(function(datas){
			    var data =datas.pref;	   		
				$("input[name='subjectId']").val(data.subjectId||"");
	        	$("input[name='schoolStageId']").val(data.schoolStageId||"");
			    if(data.schoolStageName||data.subjectName){
			    _this.addItem("subjectName_schoolStageName",(data.schoolStageName||"")+(data.subjectName||""),true);  
	        	}   
			    if(data.pressName||data.materialName){
				  _this.addItem("pressName_materialName",(data.pressName||"")+(data.materialName||""),false);
				}
				_this.bind();	     
			     
		  });
		
};

listPage.prototype.setFormData=function(data){
	           var _this =this;
	          // var readonlyKeys =new Array("schoolStageName","subjectName");
	         //  var showKeys=new Array("pressName","materialName","materialNodeName"); // materialName materialNodeName  materialNodePath 
			       if(data.schoolStageName||data.subjectName){
				  	 _this.addItem("subjectName_schoolStageName",(data.schoolStageName||"")+(data.subjectName||""),true);
		          }
			   if(data.pressName||data.materialName){
			  	 _this.addItem("pressName_materialName",(data.pressName||"")+(data.materialName||""),false);
			   }
           	   $.each(data,function(key,value){          	   	      
				   
				     var $tmp=$("[name='"+key+"']");
			        if($tmp){
			          	$tmp.val(value);
		          	}	 
        		});
};


listPage.prototype.addItem=function(key,value,readonly){
		
		         $(".conditionlist").find("."+key).remove(); 
		         if(value=="")return;
		        var html ='<span class="item  '+(key=="content"?(" mycontent "+key):key) +'"  data-key="'+key+'"><em class="con">'+value+'</em><em class="del spandel">×</em></span>';
		        if(readonly){
		          html ='<span class="item readonly '+key+'"  data-key="'+key+'"><em class="con">'+value+'</em></span>';
		        }
	        	var delItem =$(".conditionlist").find(".delitem");
	        	var $content=$(".mycontent");
	        	if(!$content||$content.length<1){
	        		$(".conditionlist").append(html);
	        	}else{
	        		$content.before(html);
	        	}

};

listPage.prototype.doUnFavorite=function($obj,data){	
	        var _this =this;		
			Utils.Notice.print("取消成功",2000);
			_this.bind();
			
			//$obj.parents("li").remove();
};


listPage.prototype.bind=function(){

    	//$('#Jpage').unbind();
    	$('#Jpage').page({
            url : '/auth/teacher/hd/leke/workbook/favorite/details.htm',
            form: $("#Jform").get(0),
            scrollCont: $("#Jdetails").get(0),
            callbackPullDown: function(page) {
            	   var 	dataList =page.dataList;
              if(dataList!=null&&dataList.length>0){
              	$.each(dataList,function(index,item){
              		item.photoUrl=(item.photoUrl!=null&&item.photoUrl.length>0)
              		?(Leke.domain.fileServerName+"/resize_402x415/"+item.photoUrl)
              		:(Leke.domain.staticServerName+"/images/resource/suoluetu.png");
              		item.creatorName=item.schoolId == 0?"匿名":item.creatorName;
              	});
              	
              }
            	var tpl = Mustache.render($('#JdetailsTpl').val(), page);
            	$("#Jdetails").append(tpl);	
            }
        });
};

listPage.prototype.initial=function(){

		 var _this =this;
    	
    	$(".myserch").click(function(){
    		$(".sort .c-droplist li").removeClass("cur");
    		$(".sort .c-droplist li").first().addClass("cur");
    		$("input[name='orderBy']").val("0");
    			var content =$.trim($("[name='content']").val());
			
					_this.addItem("content",content,false);  		
				
    		
    		 _this.bind();
    		 return false;
    	});
    	$(".mydel").click(function(){
    		$("input[name='content']").val("");
    		// _this.bind();
    		 return false;
    	});
    	
    	
    	$(".resouceselect .text").click(function(){
    		$(".resouceselect  .c-droplist").show();
    		return false;
    	});
    	$(".resouceselect  .c-droplist li").click(function(){
    		 $(".resouceselect  .c-droplist li").removeClass("cur");
    		 $(this).addClass("cur");
    		 $(".resouceselect .text").html($(this).text());
    		 var queryType =$(this).data("type");
    		 $("input[name='queryType']").val(queryType);
    		 var scope =$(this).data("id");
    		 $("input[name='shareScope']").val(scope);
  			 $(".resouceselect .c-droplist").hide();
  			 HistoryServicePad.scope(scope);	 	
		 	if($("input[name='subjectId']").val()==""){
		 		_this.SetUserPref();
		 	}else
		 	{
		 		_this.bind();
		 		
		 	}
    		
    	
  			 
  			 return false;
   	});
    	
    	
    $(".main").live("click",function(){
		$(".c-droplist").hide();
		return false;
	});
};
var lstpage =new listPage();
$(document).ready(function(){
	lstpage.init();
});


