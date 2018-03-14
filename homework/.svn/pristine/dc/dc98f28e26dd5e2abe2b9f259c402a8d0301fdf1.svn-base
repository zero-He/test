var decList= function(){
    var dec =this;
	this.init=function(){
	  var  _this =this;	
	  var url ="/auth/hd/common/holiday/ajax/getStuSubjectHomeworkInfo.htm"
      var dbZY= DropBind({
			obj: $('#jbody'),      	    
			url:url,
			getPostData:function(){return {id:$("input[name='id']").val()};},
			GetList:_this.PostData,
			callBack:_this.BindData
      });
	  this.LoadSummary()
	   
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
							   		         dec.initCirle();     
									}		
							}
              		  });
        		  
		
	};
	this.RowBind=function(data){alert("super RowBind");};
	
	 this.BindData=function(datas,requestPage){
	 	_this=this;
	 	var today =new Date();
	    var rsJson= eval({'1':'寒假','2':'暑假'});	
	    var page =datas.datas.page;	
	    $body =$('#jDLstbody');
	    if(page==null||page.dataList==null||page.dataList.length==0){
	    
	    		return;		
	    	}	
		for (var index = 0; index < page.dataList.length; index++){
			data = page.dataList[index];
			data.islate =data.submitStatus ==2?"<i class='latter-sub-pro''>迟交</i>":"";
			//data.createdOn =(data.createdOn==null)?"--":new Date(data.createdOn).Format('yyyy-MM-dd hh:mm');
			data.createdOn=(data.createdOn==null)?"":(new Date(data.createdOn).Format('yyyy-MM-dd hh:mm:ss')+"布置");
			data.submitTime =(data.submitTime==null)?"--":new Date(data.submitTime).Format('yyyy-MM-dd hh:mm:ss');
			data.closeEndTime =(data.closeEndTime==null)?"--":new Date(data.createdOn).Format('yyyy-MM-dd hh:mm:ss');
			data.grade="";
			//data.score=75.4;
			data.grade+='<i class="holiday-work-grade-num grade-num-null"></i>';
			data.grade+='<i class="holiday-work-grade-num grade-num-null"></i>';
	        data.grade+='<i class="holiday-work-grade-num holiday-work-grade-grade"></i>';
			if(data.score!=null)
			{
				data.score =(Math.round(data.score * 100)/100).toFixed(1);
				var str =data.score.toString();
			    if(str.length>0){
					data.grade="";
					for(var i=0;i<str.length;i++){
						var num =str.substr(i,1);
						if(num=="."){
						data.grade+='<i class="holiday-work-grade-num grade-num-node"></i>';
						}
						else
						{
						data.grade+='<i class="holiday-work-grade-num grade-num'+num+'"></i>';
						}
					}
		            data.grade+='<i class="holiday-work-grade-num holiday-work-grade-grade"></i>';
			    }
			}
			
		    var closeEndTime =new Date(data.closeTimeStr);
		    data.isEnd =today>data.closeTimeStr?false:true;
		    if( data.isEnd)
		   		data.endStr ="已截止"+closeEndTime.Format('yyyy-MM-dd hh:mm:ss');
		    else{
		    	data.endStr=_this.getDateDiffStr(closeEndTime);
		    }
			dec.RowBind(data);
			
	    }
    	 var sOutput = Mustache.render($('#jDLsttpl').val(), page);
	     
	     if(requestPage==0)
	    	 $body.html(sOutput);
	     else
	    	 $body.append(sOutput);

    };
    this.PostData=function(datas){     
              return datas.datas.page.dataList;		  
		};
	this.getDateDiffStr=function(date){
		var today =new date();
		var hours = Math.round((Date.UTC(date.getFullYear(),date.getMonth()+1,date.getDate(),date.getHours())-Date.UTC(today.getFullYear(),today.getMonth()+1,today.getDate(),today.getHours()))/(1000*60*60));
		var str ="";
		var h =hours/24;
		if(h>0)
		 str+=h+"天"
		return "离截止时间还有: "+str+(hours%24)+"小时";
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
	
	
};