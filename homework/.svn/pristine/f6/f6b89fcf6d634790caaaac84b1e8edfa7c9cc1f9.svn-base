define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var Dialog = require('dialog');
	var KindEditor = require('editor');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	require('common/ui/ui-mask/jquery.mask');
	var swfobject = require('common/swfobject');

	window.onDeleteAudioComplete = function(elementId) {
		$('#' + elementId).val('');
	};
	window.onAddAudioComplete = function(url, elementId) {
		$('#' + elementId).val(url);
	};

	var Sound = {
		init : function() {
			$('.j-dfn').each(function() {
				var type = $(this).data('type');
				if (Sound[type]) {
					Sound[type](this);
				}
			});
		},
		recorder : function(target) {
			var flashUrl = ctx + '/flashs/common/ui/ui-videoclass/sound/SoundRecorder.swf';
			var flashvars = {
				elementId : $(target).data('elementid'),
				isCanDelete : true,
				ticket : Leke.ticket,
				uploadUrl : '/auth/common/upload/audioUpload.htm',
				audioUrl : '',
				duration : 180
			};
			this.embed(target, flashUrl, '220px', '145px', flashvars, null, {
				styleclass : 'f-vam'
			});
		},
		audio : function(target) {
			var audioUrl = $(target).data('url');
			var flashUrl = assets + '/flashs/common/ui/ui-videoclass/sound/SoundPlayer.swf?audioUrl=' + audioUrl;
			this.embed(target, flashUrl, '200px', '30px', null, null, {
				styleclass : 'f-vam'
			});
		},
		embed : function(target, url, width, height, flashvars, params, attrs) {
			var id = 'dfnid_' + Math.random();
			$(target).attr('id', id);
			params = $.extend({
				wmode : "transparent",
				menu : "false"
			}, params || {});
			swfobject.embedSWF(url, id, width, height, "11.1.0", "expressInstall.swf", flashvars, params, attrs);
		}
	}

	var Page = {
		subjectId : null,
		subjectName : null,
		audioUrl : null,
		init : function() {
			this.initSubject();
			this.initEditor();
			this.bindEvent();
			Sound.init();
		},
		initSubject : function() {
			$('#jSubjectCombo').stgGrdSbjSelect({
				type : 'sbj',
				caption : false,
				onChange : function(selection) {
					this.subjectId = selection.subjectId;
					this.subjectName = selection.subjectName;
					this.initTeacher(selection.subjectId);
				}.bind(this)
			});
		},
		initTeacher : function(subjectId) {
			$.post('teachers.htm', {
				subjectId : subjectId
			}).done(function(json) {
				var teachers = json.datas.teachers;
				if (teachers && teachers.length) {
					var html = teachers.map(function(v) {
						return '<option value="' + v.id + '">' + v.userName + '</option>';
					}).join('');
					$("#jTeacherCombo").html(html);
				} else {
					$("#jTeacherCombo").html('');
					Utils.Notice.alert('该学科没有老师不能提问');
				}
			});
		},
		initEditor : function() {
			var editor = this.editor = KindEditor.create('#jRemark', {
				crossDomain : document.domain == 'leke.cn',
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
					if (this.count('text') > 500) {
						Utils.Notice.alert('你输入的字数超过限制');
						$('.word_surplus').html('字数超过限制，请适当修改内容');
					} else {
						var limitNum = 500 - this.count('text');
						$('.word_surplus').html('还可以输入' + limitNum + '字');
					}
				}
			});
		},
		bindEvent : function() {
			var _this = this;
			$("#jSubmitBtn").on("click", function() {
				var teacherId = $("#jTeacherCombo").val();
				if (teacherId == null || teacherId == '') {
					Utils.Notice.alert('没有老师不能提问');
					return;
				}
				var _self = this;

				_this.editor.sync();
				var doubtContent = $("#jRemark").val();
				if (doubtContent == '') {
					Utils.Notice.alert('内容不能为空');
					return;
				} else if (_this.editor.count('text') > 500) {
					Utils.Notice.alert('你输入的字数超过限制');
					return;
				}
				$(this).lock();
				$.post("saveDoubt.htm", {
					"doubtType" : 1,
					"doubtContent" : doubtContent,
					"teacherId" : teacherId,
					"subjectId" : Page.subjectId,
					"subjectName" : Page.subjectName,
					"doubtAudio" : Page.doubtAudio
				}).done(function(json) {
					if (json.success) {
						var tips = json.datas.tips;
						var msg = "提问成功";
						if (tips && tips.leke) {
							msg += "，乐豆+" + tips.leke;
						}
						if (tips && tips.exp) {
							msg += "，经验+" + tips.exp;
						}
						Dialog.close('toolbar_question', msg);
					}else{
						$(_self).unLock();
					}
				});
			});
			$('#jCancelBtn').on('click', function() {
				Dialog.close();
			});
		}
	}

	Page.init();

})