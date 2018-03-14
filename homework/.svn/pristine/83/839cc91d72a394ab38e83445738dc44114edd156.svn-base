var dropBind=function(optns){
	        var  _dropBind=this;
    		this.options = $.extend({
							obj: null,
							dataContentObj:null,
							url:null,
							pageSize:15,
							curPage:1,
							autoLoad:true,
							getPostData:function(){return [];},//获取查询参数
							GetList:function(datas){ return datas.datas.page.dataList; },//提交并返回数据
							callBack:function(datas,requestPage){}//回调
						}, optns);

		    //拖动对象
		    this.dropObj=null;
		    //添加分页参数
		    this.getPageData=function(){
		   		 var data=this.options.getPostData();
			      //添加分页参数
			      data.pageSize=this.options.pageSize;
				  data.curPage=this.options.curPage;
				  return data;
		    }
		    //post请求
		    this.getBkData=function(url,postData){
		    	var _this =this;
			      var ds=[]
			      $.ajax({
						   type: "POST",
						   url: url,
						   async:false,
						   data: postData,
						   success: function(datas){
						   	if(datas.success)
						   	{
						   	  ds=datas;
						   	}
						   	else
						   	{
						   		alert("err:"+datas.message+"url:"+url)
						   	}
						   	 
						   }
						});
	              return ds;		  
		    }
		    
		     this.getBackPageData=function(data){
		   		 var datas=this.getBkData(this.options.url,data);
				  return datas;
		    }
		      this.load=function(){
		    	this.dropObj.opts.loadDownFn(this.dropObj);
		    }
		    //初始化
		    this.Initial=function(){
		    	this.bindEvent();
		    	
		    }
		    this.changeDataSource=function(){
		    	 if (this.dropObj!=null&&(this.dropObj.isLockDown ==true)) {                                           
						 this.dropObj.unlock();	
						}
				if(this.options.dataContentObj!=null){
					this.options.dataContentObj.html("");
				}
					
		    	this.dropObj.opts.loadUpFn(this.dropObj);
		    }
		    this.refresh=function(){
		    	
		    	this.dropObj.opts.loadUpFn(this.dropObj);
		    }
		    //绑定 dropload
		   this.bindEvent =function(){
		    var	_bind=this;
			this.dropObj=this.options.obj.dropload({
				        scrollArea : window,
						autoLoad: _bind.options.autoLoad,
						threshold : 50,
						domDown: {
					    
					        domNoData: ''
					          //domNoData  : '<div class="dropload-noData"><span>暂无数据</span></div>'
					      },
				        loadUpFn : function(me){
				        	//alert("loadUpFn");
				        	//当前页重置为1
				        	_bind.options.curPage=1;
				            var data=_bind.getPageData();
				        	var pageData = _bind.getBackPageData(data);
				        	 if(!pageData.success){
				        	  	me.resetload();
				        	    return;
				        	  }
				        	 var datas=[];
				        	 　try {
				        	 　	datas= _bind.options.GetList(pageData); 
							　　// 此处是可能产生例外的语句
							　} catch(error) {
							　	  alert("回调函数GetList报错,没有正确的datas;"+error) 
							     me.resetload();
							     return;
							　}  
				        	if(datas.length>=0&&_bind.options.curPage<=pageData.datas.page.totalPage){
	                            try {
	                            	//刷新 page传0
		            		   	   _bind.options.callBack(pageData,0);
			            		   	　
								　} catch(error) {
								　	  alert("callBack报错;err："+error)
								   
								     me.resetload();
								     return;
								　}  
				        	}
 	                                  
						 if(datas.length == 0 ||_bind.options.curPage>=pageData.datas.page.totalPage){
			                    me.lock();
			                    me.noData();
			                      $(me.$element.selector).find(".dropload-refresh").remove();
			                     if( $(me.$element.selector).find(".dropload-noData").size()<1){
					  				 $(me.$element.selector).append('<div class="dropload-noData"><span>没有更多的内容！</span></div>');
			                     }
			                }else{
			            	    $(me.$element.selector).find(".dropload-noData").remove();
				            	if (me.isLockDown ==true) {   
	                                       me.isLockDown =false;                                        
								}
			            }		
						 _bind.options.curPage++;
						    me.resetload();	 
				        },
				        loadDownFn : function(me){
				        	
				            // alert("loadDownFn cur page"+_bind.options.curPage);
				            var data=_bind.getPageData();
				            var pageData = _bind.getBackPageData(data);
				            if(pageData.success!=true){
				        	  	me.resetload();
				        	    return;
				        	 }
				        	 var datas=[];
				        	 try {
				        	 　	datas= _bind.options.GetList(pageData); 
							　　// 此处是可能产生例外的语句
							　} catch(error) {
							　	  alert("回调函数GetList报错,没有正确的datas;err:"+error)
							     me.resetload();
							     return;
							　}  
				            if(datas.length>=0&&_bind.options.curPage<=pageData.datas.page.totalPage){
				            	 try {
		            		   	   _bind.options.callBack(pageData,_bind.options.curPage);
			            		   	　　// 此处是可能产生例外的语句
								　} catch(error) {
								　	  alert("callBack报错;err："+error)
								     me.resetload();
								     return;
								　}  
			                // 如果没有数据
			                }
				            if(datas.length == 0 ||_bind.options.curPage>=pageData.datas.page.totalPage){
			                    me.lock();
			                    me.noData();
			                   
			                     if( $(me.$element.selector).find(".dropload-noData").size()<1){
					  				 $(me.$element.selector).append('<div class="dropload-noData"><span>没有更多的内容！</span></div>');
			                     }
			                }

			                //加一页
						   _bind.options.curPage++;
			               // 每次数据插入，必须重置
			               me.resetload();
				        }
			    });
		    };
		    
		 
	 
	 };
var DropBind =function(options){
	var db =new dropBind(options);
	db.Initial();
	return db;
}	 