define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = require('utils');
	var KindEditor = require('editor');
	var Sound = require('homework/common/Sound');
	var I18n = require('homework/common/i18n');
	require('common/ui/ui-mask/jquery.mask');
	var wrongtopoc = require('homework/doubt/wrongtopic');
	var dialog = require('dialog');
	var BackDoubtList = {

		finit : function() {
			this.bindBackDoubtList();
			this.bindBack();
			this.bindSubmit();
			this.bindMark();
			this.binDelete();
			Sound.init();
		},
		bindMark : function(){
			$(".c-header__mark").click(function(){
				var doubtId = $('#doubtId').val();
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
				var doubtId = $("#doubtId").val();
				var _this = this;
				dialog.confirm("确定要删除该条提问？").done(function(sure) {
					if (sure) {
						$.post("deleteMySelfDoubt.htm",{doubtIds:doubtId},function(datas){
							if(datas.success){
								window.location.href = "/auth/student/myDoubt/doubtList/enterDoubtList.htm"
							}
						});
					}
				});
			});
		},
		bindBackDoubtList : function() {
			var _this = this;
			$("#aBackDoubtList").on("click", function() {
				window.location.href = ctx + "/auth/student/myDoubt/doubtList/enterDoubtList.htm";
			});
			$('#butMyAgain').click(function() {
				var init = $(this).data("init");
				if(!init){
					$(this).data("init","init");
					_this.loadKindEditor();
				}
				KindEditor.instances[0].html(""); 
				$('#divAgain').slideDown();
				if ($(document).height() > $(window).height()) {
					$("html,body").animate({
						scrollTop : $("#divAgain").offset().top
					}, 1000);
				}
				var init = $(this).data("init");
				if(!init){
					$(this).data("init","init");
					_this.loadKindEditor();
				}
				KindEditor.instances[0].html("")();
			});
		},

		bindBack : function() {
			$("#aBackTeacherDoubtList").on("click", function() {
				window.location.href = ctx + "/auth/teacher/myDoubt/enterTeacherDoubtList.htm";
			});
		},
		loadKindEditor : function() {
			editor = KindEditor.create('#iDoubtDiscript', {
				resizeType : 1,
				allowPreviewEmoticons : false,
				height : '200px',
				items : ['bold', 'italic', 'underline', 'subscript', 'superscript', 'image', '|', 'justifyleft',
						'justifycenter', 'justifyright', '|', 'undo', 'redo'],
				htmlTags : {
					img : ['src', 'width', 'height', 'border', 'alt', 'title', 'align', '.width', '.height', '.border',
							'.float'],
					p : ['align', '.text-align', '.color', '.background-color', '.font-size', '.font-family',
							'.background', '.font-weight', '.font-style', '.text-decoration', '.vertical-align',
							'.text-indent', '.margin-left'],
					'strong,b,em,i,u,sub,sup' : []
				},
				uploadJson : ctx + '/auth/common/upload/kindeditor.htm',
				afterChange : function() {
					var limitNum = 500; //设定限制字数
					var pattern = $.i18n.prop('homework.doubt.editor.patterncount',limitNum);
					$('.word_surplus').html(pattern); //输入显示
					if (this.count('text') > limitNum) {
						pattern = ($.i18n.prop('homework.doubt.editor.content'));
						//超过字数限制自动截取
						var strValue = editor.text();
						$('.word_surplus').html("");
						$("#sDoubtContentErr").html(pattern);
						utils.Notice.alert($.i18n.prop('homework.doubt.editor.alert'));
					} else {
						//计算剩余字数
						var result = limitNum - this.count('text');
						pattern = $.i18n.prop('homework.doubt.editor.patterncount',result);
						$('.word_surplus').html(pattern); //输入显示
						$("#sDoubtContentErr").html("");
					}
				}
			});
		},
		bindSubmit : function() {
			var _this = this;
			$("#butAgainDoubt").click(function() {
				editor.sync();
				var _this = this;
				var userName = $('#userName').val();
				var iDoubtDiscript = $("#iDoubtDiscript").val();
				if (iDoubtDiscript == "") {
					utils.Notice.alert($.i18n.prop('homework.doubt.editor.nonull'));
					return false;
				}
				$(_this).lock();
				$.post(ctx + "/auth/student/explainDoubt.htm", {
					"doubtId" : $('#doubtId').val(),
					"explainContent" : iDoubtDiscript,
					"expainAudio" : $('#audioUrl').val(),
					"userName" : userName,
					"subjectId" : $('#subjectId').val(),
					"doubtTitle" : $('#doubtTitle').val(),
					"questionId" : $('#questionId').val(),
					"targetType" : $('#targetType').val(),
					"teacherId" : $('#teacherId').val(),
					"resolved" : $('#resolved').val(),
					"isAdd" : false
				}, function(data) {
					if (data.success) {
						utils.Notice.alert("提交成功");
						window.location.reload();
					}else{
						$(_this).unlock();
					}
				});
			});
		}
	}

	BackDoubtList.finit();
	wrongtopoc($('#questionId').val(),Leke.user.userId,"#jWrongDiv");

});