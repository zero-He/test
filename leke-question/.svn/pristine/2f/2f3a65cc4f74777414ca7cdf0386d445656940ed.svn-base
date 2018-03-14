function listPage(){
	
};
listPage.prototype.init=function(){
    var	_this =this;
      
    this.initial();
	this.SetUserPref();
	$(".sc").live("click",function(){
		           var $obj=$(this);
		           var url ='/auth/common/workbook/jsonp/doFavorite.htm?callback=?';
		          // var df =_this.doFavorite
					var data ={'workbookId':$obj.parent().data("id")}
					
					Utils.HTTP.jsonp(url,data).then(function(data){
						
											var tips = data && data.tips || {};
											//var vardata=$.parseJSON(data);
											var msg = '收藏成功';
											//$obj.removeClass("");
																														
											if(tips.leke) {
												msg += '，乐豆+' + tips.leke;
											}
											if(tips.exp) {
												msg += '，经验+' + tips.exp;
											}
										    _this.doFavorite($obj,data);
											Utils.Notice.print(msg,2000);
				    					
					},function(data){
						   Utils.Notice.print(data,2000);
					});
	});
	
	$(".dz").live("click",function(){
					var $obj=$(this);
					var url ='/auth/common/workbook/jsonp/doPraise.htm?callback=?';
					//var dp =_this.doPraise;
					var data ={'workbookId':$obj.parent().data("id")}
					Utils.HTTP.jsonp(url,data).then(function(data){
						_this.doPraise($obj,data);
					},function(data){
						   Utils.Notice.print(data,2000);
					});
	});
	
	$(".mx").live("click",function(){
					var $obj=$(this);
					var url =Leke.domain.paperServerName+'/auth/common/hd/workbook/view.htm?_newpage=1&workbookId='+$obj.data("id");
				     window.location.href= url;
	});
	
	
	$(".cmm").live("click",function(){
					var $obj=$(this);
				alert("我做");
	});
	$(".bj").live("click",function(){
					var $obj=$(this);
					alert("我做");
					
	});
		$(".cxbtn").live("click",function(){
				$("#search").show();
				$(this).hide();
				$(".sort .c-droplist li").first().click();
	});
	$(".celbtn").live("click",function(){
				$("#search").hide();
				$(".cxbtn").show();
			 //  $("input[name='content']").val("");
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

listPage.prototype.doFavorite=function(obj,data){		
	             //  Utils.Notice.print("收藏成功",3000);
	               var $em =$(obj).find("em");
			   	var praiseCount =  $em.text();
    	    	$em.text((parseInt(praiseCount)||0)+1); 
    	    	$(obj).removeClass('nocollect').addClass('collect'); 
    	    	  
};


listPage.prototype.doPraise=function(obj,data){
				  Utils.Notice.print("点赞成功",3000);
				  var $em =$(obj).find("em");
			   	var praiseCount =  $em.text();
    	    	$em.text((parseInt(praiseCount)||0)+1); 
    	    	$(obj).removeClass('praise').addClass('praisecur');	 
};


listPage.prototype.bind=function(){

    //	$('#Jpage').unbind();
    	$('#Jpage').page({
            url : '/auth/teacher/hd/leke/workbook/school/details.htm',
            form: $("#Jform").get(0),
            scrollCont: $("#Jdetails").get(0),
            callbackPullDown: function(page) {
            	   var 	dataList =page.dataList;
              if(dataList!=null&&dataList.length>0){
              	$.each(dataList,function(index,item){
              		item.photoUrl=(item.photoUrl!=null&&item.photoUrl.length>0)
              		?(Leke.domain.fileServerName+"/resize_402x415/"+item.photoUrl)
              		:(Leke.domain.staticServerName+"/images/resource/suoluetu.png");
              	   // item.creatorName=item.schoolId == 0?"匿名":item.creatorName;
              	});
              	
              }
            	var tpl = Mustache.render($('#JdetailsTpl').val(), page);
            	$("#Jdetails").append(tpl);	
            }
        });
};

listPage.prototype.initial=function(){

      var _this =this;
   	$(".sort ").click(function(){
    		$(".sort  .c-droplist").show();
    		return false;
    	});
    	$(".sort  .c-droplist li").click(function(){
    		
    		 $(".sort  .c-droplist li").removeClass("cur");
    		 $(this).addClass("cur");
    		 $("input[name='orderBy']").val($(this).data("id"));		
    		 $(".sort  .c-droplist").hide();
    		 _this.bind();
    		 return false;
    	});
    
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
    	
	
	  $(".main").live("click",function(){
		$(".c-droplist").hide();
		return false;
	});
    	
};
var lstpage =new listPage();
$(document).ready(function(){
	lstpage.init();
});


