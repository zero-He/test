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
	       
		   this.LoadList();
		   this.Bind();
		},
		Bind:function(){
			  $("a").each(function(){
				$(this).attr("href",function(){return encodeURI(this.href);});
			});
		},
		LoadList:function(){
			
			var _this = this;
			Page.create({
				id : 'jDivPage',
				url : ctx + '/auth/platformAdmin/homeworkConfig/HomeworkConfigList.htm',
				buttonId : 'jSelect',
				formId : 'jFormPage',
				callback : function(datas) {
						var page = datas.page;
						if(page!=null){
						for(var index =0;index<page.dataList.length;index++){
							data = page.dataList[index];
							
							var rsJson= eval({'1':'寒假','2':'暑假'});
							data.type=rsJson[data.type];
							
							data.createdOn=date.format(data.createdOn,'yyyy-MM-dd HH:mm')
							
								data.work_begintime=date.format(data.work_begintime,'yyyy-MM-dd HH:mm')
							if(data.work_endtime)
								data.work_endtime="~"+date.format(data.work_endtime,'yyyy-MM-dd HH:mm')
								data.cycle_begintime=date.format(data.cycle_begintime,'yyyy-MM-dd HH:mm')
							if(data.work_endtime)
								data.cycle_endtime="~"+date.format(data.cycle_endtime,'yyyy-MM-dd HH:mm')
						}
							
						var sOutput = Mustache.render($('#jApplyListTpl').val(), page);
						$body =$('#jApplyListTbody');
				   		$body.html(sOutput);
					
				
					}
				}
			});
		}	
		
	};
	Body.init();

});
