/**
 * Created by Administrator on 2016/12/12 0012.
 */
 
var stuWork = function(){
    //初始化函数
   
    this.init = function () {
     var _this=this;
      var ul ="/auth/hd/common/holiday/ajax/loadListByStudent.htm"
      
       var dbZY= new DropBind({  
			obj: $('#jzy'),   	    
			url:ul,
			getPostData:function(){return {type:1};},
			GetList:_this.GetList,
			callBack:_this.BindZYData
      });
     
        var dbWK=new DropBind({
			obj: $('#jwk'),      	    
			url:ul,
			getPostData:function(){return {type:2};},
			GetList:_this.GetList,
			callBack:_this.BindWKData
      });
     // dbZY.refresh();
      dbWK.refresh();//微课页面在加载的时候不触发drop；手动刷一次
      _this.bindBtn();
            //作业及微课tab切换
        $('.homework-hd-work-item').on('click', function () {
            var $index = $(this).index();
            $(this).addClass('homework-hd-work-item-on').siblings().removeClass('homework-hd-work-item-on');
            $('.homework-bd-work-item').eq($index).show().siblings().hide();
        });
        
           var $devicePixelRatio=window.devicePixelRatio;
        if($devicePixelRatio==1.5){
            $('#homework-meta').attr('content','width=device-width,initial-scale=0.65, maximum-scale=0.65, minimum-scale=0.65')
        }else{
            $('#homework-meta').attr('content','width=1920px,initial-scale=0.5, maximum-scale=0.5, minimum-scale=0.5')
        }
        
    };
    this.bindBtn =function(){
    	//答题
    	$(".answer").live("click",function(){
    		var url =$(this).data("url");
    		
    		window.location.href=url;
    	});
    	//工作
    	$(".work").live("click",function(){
    		var url =$(this).data("url");
    	
    		window.location.href=url;
    	});
    	//学习
    	$(".study").live("click",function(){
    		var url =$(this).data("url");
    	  
    		window.location.href=url;
    	});
    	
    };
    this.BindWKData=function(datas,requestPage){
    	var rsJson= eval({'1':'寒假','2':'暑假'});		
    	var page = datas.datas.page;
    	$body =$('#jWKbody');
    		if(page==null||page.dataList==null||page.dataList.length==0){
	    		
	    		return;		
	    	}
			for (var index = 0; index < page.dataList.length; index++){
					data = page.dataList[index];
				    data.holiday=rsJson[data.holiday];
					
					data.finishRate=(Math.round(data.finish * 10000)/(data.total*100)).toFixed(0);
					if(isNaN(data.finishRate))
					{
						data.finishRate='0';
					}
					//data.btnNam =data.finishRate!=100?"去学习":"查看";
			        //data.btnClass=data.finishRate!=100?"":"task-li-btn-orange";
			        data.btnNam ="去学习";
			        data.btnClass="task-li-btn-orange";
					if(!data.firstTime){
						data.startMsg="未开始";
					}else{
						data.startMsg=new Date(data.firstTime).Format('yyyy-MM-dd hh:mm:ss')+" 开始学习";
					}
					var title =data.year+"年"+data.holiday+"作业-"+data.subjectName
					
					data.url="/auth/hd/student/vacationWork/StuWkList.htm?workId="+data._id+"&title="+title+"&subjectId="+data.subjectId+"&subjectName="+data.subjectName;										
		   }
		  var sOutput = Mustache.render($('#jWKtpl').val(), page);
	     
	     if(requestPage==0)
	     	$body.html(sOutput);
	     else 
	     	$body.append(sOutput);
	
		
	    

    }
    this.BindZYData=function(datas,requestPage){
	    var rsJson= eval({'1':'寒假','2':'暑假'});		
	    	var page = datas.datas.page;
	    	 $body =$('#jZYbody');
	    	if(page==null||page.dataList==null||page.dataList.length==0){
	    		
	    		return;		
	    	}
			for (var index = 0; index < page.dataList.length; index++){
				data = page.dataList[index];
				data.holiday=rsJson[data.holiday];
				data.finishRate=(Math.round(data.finish * 10000)/(data.total*100)).toFixed(0)
				if(isNaN(data.finishRate))
				{
					data.finishRate='0';
				}
				data.btnNam =data.finishRate!=100?"去答题":"去查看";
			    data.btnClass=data.finishRate==100?"":"task-li-btn-orange";
			  
				if(!data.firstTime){
					data.startMsg="未开始";
				}else{
					data.startMsg=new Date(data.firstTime).Format('yyyy-MM-dd hh:mm:ss')+" 开始答题";
				}
				var title =data.year+"年"+data.holiday+"作业"+" · "+data.subjectName
				data.url="/auth/hd/student/vacationWork/StuDecList.htm?workId="+data._id+"&title="+title;		
			}
			 var sOutput = Mustache.render($('#jZYtpl').val(), page);
		     if(requestPage==0)
		    	 $body.html(sOutput);
		     else
		    	 $body.append(sOutput);
	    	
    	
	    
    }
    this.GetList=function(datas){          
	           	return datas.datas.page.dataList;     	  
		}    
  
 };
 $(document).ready(function(){
	var stu =new stuWork();
	stu.init();
});

	    
 

