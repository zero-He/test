define(function(require, exports, module) {
	var $ = require('jquery');
	
	var Page = require('page');
	var Utils = require('utils');
	var Handlebars = require("common/handlebars");
	
	var date = require('common/base/date');

	var MyHomeworkList = {
		page : null,
		init : function() {
			this.loadMyHomeworkList();
			this.binEvent();
		},
		loadMyHomeworkList : function() {
			var contextID = "#myHomeworkList";
			var isParent = $("input[name='isParent']").val();
			this.page = Page.create({
				id : 'page',
				url : ctx + '/auth/common/holiday/ajax/loadListByStudent.htm',
				pageSize : 10,
				formId : 'myHomeworkForm',
				tipsId:'emptyTip',
				callback : function(datas) {
					var spmList = {
							"100":'100014',
							"102":'102021',
							"101":'101003',
							"103":'103022'
					};
					var ds = datas.page.dataList;
					for(var index=0 ;index<ds.length ;index++){
						d = ds[index];
						d.btnClass = "u-btn-bg-orange";
						var isZY = d.type==1;
						d.p = Math.round(d.finish/(d.total==0?1:d.total)*100)+"%";
						d.width ="style='width:"+d.p+"'";
						
						d.title = isZY? (d.year+"年"+(d.holiday==1?"寒假":"暑假")+"作业"+" • "+d.subjectName): (d.gradeName 
+"("+(d.semester==1?"上":"下")+")"+" • "+d.subjectName);
						d.typeName = isZY? "作业": "微课";
						if(d.firstTime==null){
							d.dtTime = "未开始";
						}else if(isZY){
							d.dtTime = date.format(d.firstTime,'yyyy-MM-dd HH:mm:ss')+" 开始答题";
						}else{
							d.dtTime = date.format(d.firstTime,'yyyy-MM-dd HH:mm:ss')+" 开始学习";
						}
						d.bookName = isZY? "":d.bookName;
						d.matVersionName = isZY? "":"·"+d.matVersionName;
						d.viewName = isZY? "vacationHomeworkDetail":"microHomeworkDetail";
						d.roleName = isParent=="true"? "parent":"student";
						var spm = '&spm='+ spmList[Leke.user.currentRoleId];
						d.viewUrl = "/auth/"+d.roleName+"/homework/"+d.viewName+".htm?id=" + d._id + spm;
						if(isParent=="true"){
							d.btnkName = "查看详情";
						}else if(isZY){
							if(d.finish==d.total){
								d.btnkName = "去查看";
								d.btnClass = "u-btn-bg-turquoise";
							}else{
								d.btnkName = "去答题";
							}
						}else{
							d.btnkName = "去学习";
						}
					}
				
					Handlebars.render(contextID, datas.page,contextID);
					$(contextID).show();
				}
			});
		},
		binEvent : function() {
			var _this = this;
			var lis = $('ul.corr li');
			$('ul.corr li').each(function(){
				var li = $(this);
				$(this).click(function(){
					lis.removeClass("active");
					li.addClass("active");
					$("input[name='type']").val($(this).attr("data-type"));
					_this.reloadPage();
				})
			});
			
			var liss = $('ul.corrs li');
			$('ul.corrs li').each(function(){
				var li = $(this);
				$(this).click(function(){
					liss.removeClass("active");
					li.addClass("active");
					$("input[name='studentId']").val($(this).attr("data-studentId"));
					_this.reloadPage();
				})
			})
			
		},
		reloadPage : function() {
			this.page.options.curPage = 1;
			this.page.loadData();
		}
	};
	MyHomeworkList.init();
});
