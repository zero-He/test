define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = require('utils');
	var KindEditor = require('editor');
	var Sound = require('homework/common/Sound');
	var I18n = require('homework/common/i18n');
	require('common/ui/ui-mask/jquery.mask');
	var Incentive = require('core-business/incentive');
	var dialog = require('dialog');
	var s_saveSuccess =  $.i18n.prop('homework.doubt.saveSuccess');
	var s_wrongDescribe = $.i18n.prop('homework.doubt.wrongDescribe');
	var s_inputContent = $.i18n.prop('homework.doubt.inputContent');
	var wrongtopoc = require('homework/doubt/wrongtopic');
	var s_tooManyPleaseDelete = $.i18n.prop('homework.doubt.tooManyPleaseDelete');
	var s_tooMany = $.i18n.prop('homework.doubt.tooMany');
	
	
	var ExplainDoubt = {

		finit : function() {
			this.loadEdit();
			this.explainDoubt();
			this.bindBack();
			this.sectionSelect();
			this.bindMark();
			this.binDelete();
			Sound.init();
		},
		bindMark : function(){
			$(".c-header__mark").click(function(){
				var doubtId = $("#hDoubtId").val();
				var _this = this;
				$.post("markMySelfDoubt.htm",{doubtId:doubtId},function(datas){
					if($(_this).hasClass("c-header__mark--act")){
						$(_this).removeClass("c-header__mark--act");
					}else{
						$(_this).addClass("c-header__mark--act");
					}
				});
			});
		},
		binDelete : function(){
			$(".c-header__remove").click(function(){
				var doubtId = $("#hDoubtId").val();
				var _this = this;
				dialog.confirm("确定要删除该条提问？").done(function(sure) {
					if (sure) {
						$.post("deleteMySelfDoubt.htm",{doubtIds:doubtId},function(datas){
							if(datas.success){
								window.location.href = "/auth/teacher/myDoubt/enterTeacherDoubtList.htm"
							}
						});
					}
				});
			});
		},
		bindBack : function() {
			$("#aBackTeacherDoubtList").on("click",function(){
				window.location.href = ctx +"/auth/teacher/myDoubt/enterTeacherDoubtList.htm";
			})
		},
		loadEdit : function() {
			editor = KindEditor.create('#tExplainContent', {
				resizeType : 1,
				allowPreviewEmoticons : false,
				height : "200px",
				width : "700px",
				items : [ 'bold', 'italic', 'underline', 'subscript', 'superscript', 'image', '|', 'justifyleft',
						'justifycenter', 'justifyright', '|', 'undo', 'redo' ],
				htmlTags : {
					img : [ 'src', 'width', 'height', 'border', 'alt', 'title', 'align', '.width', '.height',
							'.border', '.float' ],
					p : [ 'align', '.text-align', '.color', '.background-color', '.font-size', '.font-family',
							'.background', '.font-weight', '.font-style', '.text-decoration', '.vertical-align',
							'.text-indent', '.margin-left' ],
					'strong,b,em,i,u,sub,sup' : []
				},
				uploadJson : ctx + '/auth/common/upload/kindeditor.htm',

				afterChange : function() {
					var limitNum = 500; //设定限制字数
					var pattern = $.i18n.prop('homework.doubt.inputMore',limitNum);
					$('.word_surplus').html(pattern); //输入显示
					if (this.count('text') > limitNum) {
						pattern = (s_tooManyPleaseDelete);
						//超过字数限制自动截取
						$('.word_surplus').html("");
						$("#sDoubtContentErr").html(pattern);
						utils.Notice.alert(s_tooMany);
					} else {
						//计算剩余字数
						var result = limitNum - this.count('text');
						pattern = $.i18n.prop('homework.doubt.inputMore',result);
						$('.word_surplus').html(pattern); //输入显示
						$("#sDoubtContentErr").html("");
					}
				}

			});
		},
		sectionSelect : function(){
			
			$("#jUpdateSection").click(function(){
				require.async(['core-business/widget/jquery.leke.matNodeSelect'], function() {
					$("#jSectionSelect").matNodeSelect({
						type: 'section',
						multi: false,
						data: {
						},
						onSelect: function(section) {
							$("#jSectionSelect").val(section.path);
							$("#jMaterialNodeId").val(section.materialNodeId);
							$("#jPressId").val(section.pressId);
							var doubtId = $("#hDoubtId").val();
							$.post(ctx + "/auth/teacher/doubt/updateSection.htm", {
								"doubtId" : doubtId,
								"materialId" : section.materialId,
								"materialNodeId" : section.materialNodeId,
								"materialPathName" : section.path,
								"pressId" : section.pressId,
							}, function(data) {
								if (data.success) {
									var str = "修改成功";
									utils.Notice.alert(str);
									$("#jSectionSelect").html(section.path);
								}
							})
						}
					});
				});
			});
			
			
			$("#jSectionSelect").click(function(vm, evt) {
				
			});
		},
		explainDoubt : function() {
			$("#bExpainPaper").on("click", function() {
				editor.sync();
				var _this = this;
				var doubtId = $("#hDoubtId").val();
				var explainContent = $("#tExplainContent").val();
				var userName = $("#hUserName").val();
				var explainAudio = $("#audioUrl").val();
				if (explainContent == undefined || explainContent == "") {
					utils.Notice.alert(s_inputContent);
					return false;
				} else if ($("#sDoubtContentErr").text() != '') {
					utils.Notice.alert(s_wrongDescribe);
					return false;
				} else {
					$(_this).lock();
					$.post(ctx + "/auth/teacher/explainDoubt.htm", {
						"doubtId" : doubtId,
						"userName" : userName,
						"explainContent" : explainContent,
						"expainAudio" : explainAudio,
						"subjectId" : $('#subjectId').val(),
						"doubtTitle" : $('#doubtTitle').val(),
						"questionId" : $('#questionId').val(),
						"targetType" : $('#targetType').val(),
						"teacherId" : $('#teacherId').val(),
						"resolved" : $('#resolved').val(),
						"isAdd" : false
					}, function(data) {
						if (data.success) {
							var tips = data.datas.tips || {};
							var leke = tips.leke;
							var exp = tips.exp;
							var str = "提交成功";
							if (leke) {
								str = str + "，乐豆+" + leke;
							}
							if (exp) {
								str = str + "，经验+" + exp;
							}
							utils.Notice.alert(str);
							setTimeout(function() {
								window.location.href = ctx + "/auth/teacher/myDoubt/enterTeacherDoubtList.htm";
							}, 2000);
						}else{
							$(_this).unlock();
						}
					})
				}
			})
		}
	}
	wrongtopoc($('#questionId').val(),$("#jCreatedBy").val(),"#jWrongDiv");
	ExplainDoubt.finit();

})