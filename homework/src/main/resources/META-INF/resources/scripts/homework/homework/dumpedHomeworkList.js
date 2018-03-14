define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	var Utils = require("utils");
	var Dialog = require('dialog');
	var LekeDate = require('common/base/date');
	var HomeworkType = require("homework/common/homeworkType");

	var homeworkEditTpl = $('#jHomeworkEditTpl').html();
	Handlebars.registerHelper({
		fmtTime : function(time){
			return LekeDate.format(time,'yyyy-MM-dd HH:mm:ss');
		}
	});
	var HomeworkList = {
		page : null,
		init : function() {
			var _this = this;
			HomeworkType.render($('#jHomeworkType'));
			this.initLoadPage();
			this.bindEvent();
		},
		initLoadPage : function() {
			this.page = Page.create({
				id : 'divPage',
				url : ctx + '/auth/teacher/homework/loadDumpedHomeworkList.htm',
				curPage : 1,
				pageSize : 10,
				pageCount : 10,
				buttonId : 'bHomeworkList',
				formId : 'formPage',
				tipsId:'j-no-data',
				callback : function(datas) {
					Handlebars.render("#j-tmpl", datas.page,'#j-body');
				}
			});
			var _this = this;
		},
		bindEvent : function() {
			var _this = this;
			//作业类型change
			$('#jHomeworkType').change(function() {
				_this.page.loadData();
			});
			$('#jResType').change(function() {
				_this.page.loadData();
			});
			$('#j-chk-all').click(function(){
				if(this.checked){
					$('#j-body :checkbox').attr('checked','checked');
				}else{
					$('#j-body :checkbox').removeAttr('checked');
				}
			});
			$('#j-btn-recover').click(function(){
				var checkIds= _this.getCheckedHwIds();
				if(checkIds.length == 0){
					Utils.Notice.alert('请选择要恢复的作业');
					return ;
				}
				$.post('/auth/teacher/homework/recover.htm',{ids:checkIds},function(json){
					if (json.success) {
						Utils.Notice.alert('操作成功');
						_this.page.loadData();
					} else {
						Utils.Notice.alert(json.message);
					}
				});
			});
			$('#j-btn-del').click(function(){
				var ids= _this.getCheckedHwIds();
				var checkIds= _this.getCheckedHwIds();
				if(checkIds.length == 0){
					Utils.Notice.alert('请选择要永久删除的作业');
					return ;
				}
				Dialog.confirm('一旦删除，试卷将无法找回，是否确认永久删除？').done(function(flag){
					if(!flag){ return ;}
					$.post('/auth/teacher/homework/del.htm',{ids:checkIds},function(json){
						if(json.success){
							Utils.Notice.alert('操作成功');
							_this.page.loadData();
						}
					});
				});
			});
		},
		getCheckedHwIds : function(){
			var ids = [];
			$('#j-body :checked').each(function(i){
				ids.push($(this).data('hwid'));
			});
			return ids;
		}
	};

	HomeworkList.init();

});
