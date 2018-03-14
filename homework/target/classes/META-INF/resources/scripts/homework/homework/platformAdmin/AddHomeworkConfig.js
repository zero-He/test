define(function(require, exports, module) {
	
	//var ko = require('knockout');
	//var VM = require('pay/platformFinance/virtualAccountInLog/BaseVM');
	//分页
	//require('common/knockout/component/leke-page');
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Page = require('page');
	var WdatePicker = require('date');
	var Utils = require('utils');
	var dialog = require('dialog');
	var date = require('common/base/date');

	
	var Body ={
		init:function(){
	       this.Bind();
	     
		},
		Bind:function(){
			var _this =this;
			
			  $('#year').click(function(){
				WdatePicker({
					dateFmt:'yyyy',
				//	maxDate:"#F{$dp.$D(\'endDate\')}",
					readOnly:true
				});
			});
			
//		    $('#work_begintime').click(function(){
//				WdatePicker({
//					dateFmt:'yyyy-MM-dd HH:mm:00',
//					maxDate:"#F{$dp.$D(\'work_endtime\')}",
//					readOnly:true
//				});
//			});
			
			$('#work_endtime').click(function(){
				WdatePicker({
					dateFmt:'yyyy-MM-dd HH:mm:00',
					 minDate:'%y-%M-%d',
					//maxDate:'%y-%M-%d',
					readOnly:true
				});
			});
			
			
			   $('#cycle_begintime').click(function(){
				WdatePicker({
					dateFmt:'yyyy-MM-dd HH:mm:00',
					maxDate:"#F{$dp.$D(\'cycle_endtime\')}",
					readOnly:true
				});
			});
			
			$('#cycle_endtime').click(function(){
				WdatePicker({
					dateFmt:'yyyy-MM-dd HH:mm:00',
					minDate:"#F{$dp.$D(\'cycle_begintime\')}",
					//maxDate:'%y-%M-%d',
					readOnly:true
				});
			});
			//通过
			$('#add').click(function(){
			           var flag =true;
				       $(".cknull").each(function(){
				       	var value =$(this).val();
				       	if(value.length<1){
				       		Utils.Notice.alert("必须输入  "+$(this).data("des"),3000); 
				       		flag =false;
				       		return false;
				       	}
				       });
				       if(!flag) return false;
				       
				          var url =ctx + '/auth/platformAdmin/homeworkConfig/AddConfig.htm';
			               var data =$("form").serialize();
			               	dialog.confirm('确定向全网推送寒暑假作业？操作不可撤回！').done(function(sure){
								if(sure){
					            	$.post(url, data, function(data) {      
						               			 if(data.success==true){
						               			 	Utils.Notice.alert('操作成功！',1000); 
						                 			window.location=ctx + '/auth/platformAdmin/homeworkConfig/HomeworkConfigList.htm';
						                  		  }
		             				 });
		             				// this.close(false); 
		             		   }
				      });

			});
			
			//通过
			$('#rtn').click(function(){
				        window.location=ctx + '/auth/platformAdmin/homeworkConfig/HomeworkConfigList.htm';
			});

		}
	
	}
	
	Body.init();

});
