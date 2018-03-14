define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = require('utils');
	var Mustache = require('mustache');
	var KindEditor = require('editor');
	var Sound = require('homework/common/Sound');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	require('common/ui/ui-mask/jquery.mask');
	var I18n = require('homework/common/i18n');
	var s_alert = $.i18n.prop('homework.exercise.knowledge.alert');
	var LoadDoubt = {

		finit : function() {
			this.loadSubject();
			this.loadKindEditor();
			this.bindSubmit();
			this.sectionSelect();
			Sound.init();
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
		loadSubject : function() {
			var _this = this;
			var teacherId = $('#j_teacherId').val();
			if(teacherId !=''){
				return;
			}
			$('#jSubject').stgGrdSbjSelect({
				type : 'sbj',
				caption : false,
				onChange : function(selection) {
					$('#hSubjectId').val(selection.subjectId);
					$('#hSubjectName').val(selection.subjectName);
					_this.loadTeacher(selection.subjectId);
				}
			});
		},
		sectionSelect : function(){
			$("#jSectionSelect").click(function(vm, evt) {
				var self = this;
				var schoolStageId = $("#jSchoolStageId").val();
				var subjectId = $('#hSubjectId').val();
				if(!schoolStageId){
					subjectId = null;
				}
				require.async(['core-business/widget/jquery.leke.matNodeSelect'], function() {
					$(self).matNodeSelect({
						type: 'section',
						multi: false,
						model: self.model,
						data: {
							schoolStageId:schoolStageId,
							subjectId:subjectId
						},
						onSelect: function(section) {
							$("#jSectionSelect").val(section.path);
							$("#jMaterialNodeId").val(section.materialNodeId);
							$("#jPressId").val(section.pressId);
							$("#jMaterialId").val(section.materialId);
							console.log(section);
						}
					});
				});
			});
		},
		loadTeacher : function(subjectId) {
			$.post(ctx + "/auth/student/myDoubt/getTeachers.htm", {
				"subjectId" : subjectId
			}, function(data) {
				if (data.datas.resultList != "") {
					var output = Mustache.render($("#teacherOption_tpl").val(), data.datas);
					$("#iDoubtTeacherId").html(output);
				} else {
					$("#iDoubtTeacherId").html("<option value='0'>" + $.i18n.prop('homework.doubt.editor.notea')
							+ "</option>");
				}
			})
		},
		bindSubmit : function() {
			var _this = this;
			$("#bPaperEdit").on("click", function() {
				editor.sync();
				var iDoubtDiscript = $("#iDoubtDiscript").val();
				var _this = this;
				if (iDoubtDiscript == "") {
					utils.Notice.alert($.i18n.prop('homework.doubt.editor.nonull'));
				} else if ($("#sDoubtContentErr").text() != '') {
					utils.Notice.alert($.i18n.prop('homework.doubt.editor.describeerror'))
				} else if ($("#iDoubtTeacherId").val() == 0) {
					utils.Notice.alert($.i18n.prop('homework.doubt.editor.chooseteacher'))
				} else {
					var iDoubtTeacherId = $("#iDoubtTeacherId").val();
					var hQuestionId = $("#hQuestionId").val();
					if (hQuestionId == "" || hQuestionId == undefined) {
						hQuestionId = 0;
					}
					var hSubjectId = $("#hSubjectId").val();
					var hSubjectName = $("#hSubjectName").val();
					var doubtAudio = $('#audioUrl').val();
					$(_this).lock();
					$.post(ctx + "/auth/student/myDoubt/saveDoubt.htm", {
						"doubtType" : 1,
						"doubtContent" : iDoubtDiscript,
						"teacherId" : iDoubtTeacherId,
						"questionId" : hQuestionId,
						"subjectId" : hSubjectId,
						"subjectName" : hSubjectName,
						"materialNodeId" : $("#jMaterialNodeId").val(),
						"materialPathName" : $("#jSectionSelect").val(),
						"materialId" : $("#jMaterialId").val(),
						"schoolStageId" : $("#jSchoolStageId").val(),
						"pressId" : $("#jPressId").val(),
						"doubtAudio" : doubtAudio
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
								window.location.href = ctx + "/auth/student/myDoubt/doubtList/enterDoubtList.htm";
							}, 2000);
						}else{
							$(_this).unlock();
						}
					})
				}
			})
		}
	}

	LoadDoubt.finit();

})