define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Page = require('page');
	var Dialog = require('dialog');
	var userResGroupUtils = require('resgroup/service/userResGroupUtils');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	var I18n = require('i18n');
	
	var PopPapers = {
		init : function() {
			I18n.init('homework');
			this.bindEvent();
			this.buildPage();
		},

		bindEvent : function() {
			userResGroupUtils.paperOptions($('#userResGroupId'),$('#shareScope'));
			$('#jStageSubject').stgGrdSbjSelect({
				type: 'stg_sbj',
				onChange: function(selection) {
					$('#jSubjectId').val(selection.subjectId);
					$('#jSchoolStageId').val(selection.schoolStageId);
				}
			});
			$('#jConfirm').on('click', function() {
				var obj = function() {
					list : null;
				};
				var aChkValue = new Array();
				$('input[name="checkName"]:checked').each(function() {
					var hwJson = {};
					hwJson.paperId = $(this).data('paperid');
					hwJson.paperName = $(this).data('title');
					hwJson.subjective = $(this).data('subjective');
					hwJson.subjectId = $(this).data('subjectid');
					
					aChkValue.push(hwJson);
				});
				obj.list = aChkValue;
				Dialog.close(null,obj);
			});
			$('#jCancel').on('click', function() {
				Dialog.close();
			});
		},

		buildPage : function() {
			var paperIds = $('#jPaperIds').val();
			Page.create({
				id : 'pageStyle',
				url : ctx + "/auth/teacher/distribute/loadPapers.htm",
				curPage : 1,
				pageSize : 8,
				pageCount : 10,
				buttonId : 'iSearchPaper',
				formId : 'fSearch',
				callback : function(data) {
					$.extend(data.page, {
						paperTypeString : function() {
							if (this.paperType === 1) {
								return $.i18n.prop('homework.homework.paper.commonpaper');
							} else if (this.paperType === 2) {
								if (this.paperAttachmentId) {
									return $.i18n.prop('homework.homework.paper.answersheet');
								} else {
									return $.i18n.prop('homework.homework.paper.answersheetnothave');
								}
							}
						},
						isSubjective : function() {
							return this.subjective ? $.i18n.prop('homework.homework.paper.have') : $.i18n.prop('homework.homework.paper.nothave');
						},
						isHandwrite : function() {
							return this.handwrite ? $.i18n.prop('homework.homework.paper.have') : $.i18n.prop('homework.homework.paper.nothave');;
						},
						isChecked : function() {
							return paperIds.indexOf(this.paperId) != -1 ? "checked" : "noCheck";
						}
					});

					var output = Mustache.render($("#papersPage_tpl").val(), data.page);
					$('#papersPage').html(output);
				}
			});
		},

		e_submitPaper : function(target) {

		}
	};

	PopPapers.init();
});