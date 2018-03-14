define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = require('utils');
	var Dialog = require("dialog");
	var Mustache = require('mustache');
	var KindEditor = require('editor');
	var Sound = require('homework/common/Sound');
	var Incentive = require('core-business/incentive');
	require('common/ui/ui-mask/jquery.mask');
	var I18n = require('homework/common/i18n');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	var s_alert = $.i18n.prop('homework.exercise.knowledge.alert');

	var LoadDoubt = {
		fInit : function() {
			this.loadKindEditor();
			this.bindSubmit();
			Sound.init();
		},
		// 加载编辑器控件
		loadKindEditor : function() {
			editor = KindEditor.create('#iDoubtDiscript', {
				crossDomain : document.domain == 'leke.cn',
				resizeType : 1,
				allowPreviewEmoticons : false,
				height : "160px",
				width : "520px",
				minWidth : 100,
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
				uploadJson : '/auth/common/upload/kindeditor.htm',
				afterChange : function() {
					var limitNum = 500; //设定限制字数
					var pattern = $.i18n.prop('homework.doubt.editor.patterncount', limitNum);
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
						pattern = $.i18n.prop('homework.doubt.editor.patterncount', result);
						$('.word_surplus').html(pattern); //输入显示
						$("#sDoubtContentErr").html("");
					}
				}
			});
		},
		bindSubmit : function() {
			var _this = this;
			$("#bPaperEdit").on("click", function() {
				var _this = this;
				editor.sync();
				var iDoubtDiscript = $("#iDoubtDiscript").val();

				if ($(".onError_top").text() != '') {
					utils.Notice.alert($(".onError_top").attr("tx") + $.i18n.prop('homework.doubt.editor.error'))
				} else if (iDoubtDiscript == "" || iDoubtDiscript.length<10) {
					utils.Notice.alert($.i18n.prop('homework.doubt.editor.morethan10'));
				} else if ($("#doubtTitleErr").text() != '') {
					utils.Notice.alert($.i18n.prop('homework.doubt.editor.fault'))
				} else if ($("#sDoubtContentErr").text() != '') {
					utils.Notice.alert($.i18n.prop('homework.doubt.editor.describeerror'))
				} else {
					$(this).lock();
					$.post(ctx + "../saveDoubt.htm", {
						"doubtType" : 1,
						"doubtContent" : iDoubtDiscript,
						"teacherId" : $("#iDoubtTeacherId").val(),
						"homeworkDtlId" : $('#jHomeworkDtlId').val(),
						"questionId" : $("#hQuestionId").val(),
						"subjectId" : $("#hSubjectId").val(),
						"doubtAudio" : $('#audioUrl').val(),
						"source" : 2,
						"sourceId" : $('#hSourceId').val(),
						"sourceType" : $('#hSourceType').val(),
						"sourceName" : $('#hSourceName').val(),
						"needIncentive" : true
					}, function(data) {
						if (data.success) {
							var tips = data.datas.tips || {};
							var leke = tips.leke;
							var exp = tips.exp;
							var str = "提问成功";
							if (leke) {
								str = str + "，乐豆+" + leke;
							}
							if (exp) {
								str = str + "，经验+" + exp;
							}
							utils.Notice.alert(str);
							setTimeout(function() {
								Dialog.close();
							}, 2000);
						}else{
							$(_this).unlock();
						}
					});
				}
			});
		}
	};

	LoadDoubt.fInit();

})