var wkList= function(){
    var wk =this
	this.init=function(){
	  var  _this =this;	
	   this.LoadSummary();
	  var url ="/auth/hd/common/holiday/ajax/getStuSubjectMicroInfos.htm"
      var dbZY= DropBind({
			obj: $('#jbody'),      	    
			url:url,
			getPostData:function(){return {id:$("input[name='id']").val()};},
			GetList:_this.PostData,
			callBack:_this.BindData
      });
      
         var $devicePixelRatio=window.devicePixelRatio;
        if($devicePixelRatio==1.5){
            $('#homework-meta').attr('content','width=device-width,initial-scale=0.65, maximum-scale=0.65, minimum-scale=0.65')
        }else{
            $('#homework-meta').attr('content','width=1920px,initial-scale=0.5, maximum-scale=0.5, minimum-scale=0.5')
        }
      
	 	
	};
	this.LoadSummary=function(){
		var url ="/auth/hd/common/holiday/ajax/getHolidayHwMicroSummary.htm"
    	var data={id:$("input[name='id']").val()};
    	$.post(url, data, function(datas) {      
	           			 if(datas.success==true){
	           			 	       var rsJson= eval({'1':'寒假','2':'暑假'});
	               			 	    var page =datas.datas;
									if(page!=null&&page.summary!=null){
										    data = page.summary;
										    data.type=rsJson[data.type];
											data.finishRate=(Math.round(data.finish * 10000)/(data.total*100)).toFixed(0);
											if(isNaN(data.finishRate))
											{
												data.finishRate='0';
											}
											
											data.relay =data.total-data.finish;
											data.firstTime=(data.firstTime==null)?"--":new Date(data.firstTime).Format('yyyy-MM-dd hh:mm:ss');
											//data.lastTime=(data.lastTime==null)?"--":new Date(data.lastTime).Format('yyyy-MM-dd hh:mm:ss');
											
											 var sOutput = Mustache.render($('#jLsttpl').val(), data);
										     $body =$('#jLstbody');
								   		     $body.append(sOutput);
							   		         wk.initCirle();   
									}		
							}
              		  });
              		  
	   	
	};
	
	
	 this.BindData=function(datas,requestPage){
	 	_this=this;
	 	var today =new Date();
	    var rsJson= eval({'1':'寒假','2':'暑假'});
	    $body =$('#jDLstbody');	
	    var page =datas.datas.page;	
	     if(page==null||page.dataList==null||page.dataList.length==0){
	     	
	    		return;		
	    	}	
		for (var index = 0; index < page.dataList.length; index++){
			data = page.dataList[index];
		    //data.time= wk.getDateDiffStr(data.time);
		    if(data.accuracy==null)
			{
				data.accuracy= "配套作业正确率：--"
			}
			else
			{
			 data.accuracy= "配套作业正确率："+data.accuracy*100+"%"; 
			}		
			wk.RowBind(data);
		}
	    
    	 var sOutput = Mustache.render($('#jDLsttpl').val(), page);
	     
	     if(requestPage==0)
	    	 $body.html(sOutput);
	     else
	    	 $body.append(sOutput);

    };
    this.RowBind=function(data){alert("super RowBind");};
    this.PostData=function(datas){
              return datas.datas.page.dataList;		  
		};
	this.getDateDiffStr=function(m){
		var h =parseInt(m/60);
		var m =parseInt(m%60);
		var time="";
		if(h>0)
		{
			day =h+"分"
		}
		return "离截止时间还有: "+time+m+"秒";
	};    
 //进度条
	   this.initCirle = function () {
        var circlePercent = $('.circlePercent').html(),
            circleAngle = parseInt(circlePercent) * 3.6,
            leftCircleAngle = 225,
            rightCircleAngle = 225,
            leftCircle = $('.work-wrapper-leftcircle'),
            rightCircle = $('.work-wrapper-rightcircle');
        if (circleAngle <= 180) {
            leftCircleAngle = 225;
            rightCircleAngle = 225+circleAngle;
        } else {
            leftCircleAngle = 45+circleAngle;
            rightCircleAngle = 405;
        }
        leftCircle.css('-webkit-transform', 'rotate(' + leftCircleAngle + 'deg)');
        rightCircle.css('-webkit-transform', 'rotate(' + rightCircleAngle + 'deg)');
    };
	
}