define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require("utils");
	var Handlebars = require("common/handlebars");
	var I18n = require('homework/common/i18n');
	var Dialog = require('dialog');
	var Homework = {
		sort : null,
		loadUrl : 'loadHomeworkDtlList.htm',
		init : function() {
			this.loadHomeworkDtlList(null);
			this.bindEvent();
		},
		bindEvent : function(){
			var _this = this;
			$(document).on('click','.j-change-correct',function(){
				var homeworkDtlId = $(this).data('id');
				var uid = $(this).data('uid');
				_this.findGroupUser(uid).then(function(json){
					Handlebars.render('#j-tmpl-users2',json.datas,'#j-body-users');
					_this.openGroupUser(homeworkDtlId);
				});
			})
			
			$('.m-sorting').click(function(){
				var className = $(this).attr('class');
				var sort = -1;
				if(className.indexOf('m-sorting-asc') > -1){
					$(this).addClass('m-sorting-desc').removeClass('m-sorting-asc');
				}else if(className.indexOf('m-sorting-desc') > -1){
					$(this).addClass('m-sorting-asc').removeClass('m-sorting-desc');
					sort = 1;
				}else{
					$(this).addClass('m-sorting-desc').removeClass('m-sorting-asc');
				}
				_this.sort = sort;
				_this.loadHomeworkDtlList(_this.sort);
			});
			$(document).on('click','.j_selfCheck',function(){
				var hwId= $(this).data('homeworkid');
				var event = this;
				Dialog.confirm('不作批改后，本次作业中未提交和未批改的学生作业将默认不再批改，不影响已批改的学生作业，确定进行此操作吗？').done(function(sure) {
					if (sure) {
						var url ="/auth/teacher/homework/selfCheck.htm";
						$.post(url,{'homeworkId':hwId},function(data){
							if(data.success){
								Utils.Notice.alert('操作成功');
								setTimeout(function(){
									location.reload();
								},1500)
							}else{
								Utils.Notice.alert('操作失败');
							}
						});
					}
				});
			});
			$(document).on('click','.j_mutualCorrection',function(){
				var hwId= $(this).data('homeworkid');
				var event = this;
				$.post('/auth/teacher/validateMutualCorrect.htm',{homeworkId:hwId},function(json){
					if(!json.success){
						Utils.Notice.alert(json.message);
					}else{
						Dialog.open({
							title: '学生互批',
							tpl: $('#j-mutualCorrect-dialog').html(),
							size: 'sm',
							btns: []
						});
					}
				});
			
			});
			$(document).on('click','.j_random',function(){
				var msg = '学生将随机交换作业匿名互批，确定进行随机互批吗？';
				Dialog.confirm(msg).done(function(sure){
					if(sure){
						Dialog.close();
						_this.saveCorrection();
					}
				});
			})
			$(document).on('click','.j_appoint',function(){
				_this.findGroupUser().then(function(json){
					Handlebars.render('#j-tmpl-users',json.datas,'#j-body-users');
					_this.openGroupUser();
				});
				
				
			});
				
		},
		changeCorrection : function(userId,id){
			var _this = this;
			$.post('/auth/teacher/changeCorrectUser.htm',{homeworkDtlId: id,userId:userId},function(json){
				Utils.Notice.mask.close();
				if(json.success){
					Utils.Notice.alert('操作成功',2000);
					_this.loadHomeworkDtlList(_this.sort);
				} else {
					Utils.Notice.alert(json.message);
				}
			});
		},
		saveCorrection : function(userIds){
			var _this = this;
			var hwId = $('#j_homeworkId').val();
			userIds = userIds || [];
			var datas = {homeworkId: hwId,userIds:userIds};
			Utils.Notice.mask.create('系统正在将作业分配给学生，请稍后!');
			$.post('/auth/teacher/saveCorrection.htm',datas,function(json){
				Utils.Notice.mask.close();
				if(json.success){
					Utils.Notice.alert('分配完毕',2000);
					_this.loadHomeworkDtlList(_this.sort);
				} else {
					Utils.Notice.alert(json.message);
				}
			});
		},
		openGroupUser : function(id){
			var _this = this;
			Dialog.open({
				title: id != null && id != undefined ?'换人批改':'指定批改学生',
				tpl: $('#j-correct-users').html(),
				size: 'nm',
				onClose: function(sure) {
				},
				btns: [{
					className: 'btn-green',
					text: '确定',
					cb: function() {
						var chekUserId = [];
						 $('.j-chk-user').each(function(i,n){
							 if(n.checked){
								 chekUserId.push($(n).data('id'));
							 }
							});
						 if(chekUserId.length == 0){
							 Utils.Notice.alert('请指定批改的学生');
						 }else {
							 Dialog.close();
							 Dialog.close();
							 if(id != null && id != undefined){
								 _this.changeCorrection(chekUserId[0],id);
							 }else {
								 _this.saveCorrection(chekUserId);
							 }
						 }
					}
				}, {
					className: 'btn-gray',
					text: '取消',
					cb: function() {
						this.close();
					}
				}]
		})
		},
		findGroupUser : function(excludeId){
			var classId = $('#j_homework_classId').val();
			var hwId = $('#j_homeworkId').val();
			return $.post('/auth/teacher/findClassGroups.htm',{classId:classId,homeworkId:hwId,excludeId:excludeId});
		},
		loadHomeworkDtlList : function(sort) {
			var data = {
				homeworkId : $('#j_homeworkId').val(),
				studentName : '',
				sort : sort,
				questionNum : $('#j_questionNum').val()
			};
			$.post(this.loadUrl, data, function(json) {
				if (json.success && json.datas.list && json.datas.list.length > 0) {
					var page = {
						dataList : json.datas.list,
						trStyle : function() {
							return this.submitStatus == 2 ? " style=background:#f9f4eb" : "";
						}
					}
					$.each(page.dataList, function() {
						this.score = Utils.Number.toFixed(this.score, 1);
						if (Leke.user.currentRoleId == 101 && this.correctTime == null) {
							this.buttonName = $.i18n.prop('homework.homework.check.correct');
							this.buttonAction = 'correctWork';
						} else {
							this.buttonName = $.i18n.prop('homework.homework.check.view');
							this.buttonAction = 'viewWork';
						}
						this.timsHtmlStr = function() {
							if (this.submitTime == null) {
								return '<span class="s-c-orange">'+$.i18n.prop('homework.homework.message.span')+'</span>';
							}
							return this.submitTimeStr;
						};
						this.usedTimeStr = function(){
							if(this.usedTime == null ||this.usedTime == 0 ){
								return '--';
							}else{
								return this.usedTime + '分钟';
							}
						}
						this.chijiao = function(){
							if(this.submitStatus ==2 ){
								return '（迟交）';
							}
						};
						this.progressRate = function(){
							return Utils.Number.toFixed(Utils.Number.toFixed(this.paperQuestionNum == 0 ? 0 : this.correctCount / this.paperQuestionNum, 2) * 100, 2);
						};
					});
					Handlebars.render("#homeworkDtlContext_tpl", page, '#homeworkDtlListContext');
					$('.j-no-data').hide();
				} else {
					$('#homeworkDtlListContext').html('');
					$('.j-no-data').show();
				}
			});
		}
	};

	Homework.init();

});
