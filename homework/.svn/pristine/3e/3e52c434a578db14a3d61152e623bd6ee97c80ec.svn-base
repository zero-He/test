define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Utils = require('utils');
	var Dialog = require('dialog');
	var SheetFlash = require('./sheet.flash');

	function renderAndWrap(type, comments, render) {
		var text = comments.map(render).join('');
		return '<div class="que-comment que-comment-' + type + '"><div class="que-comment-content">' + text
				+ '</div></div>';
	}

	function renderHtml(que, comments) {
		if (comments.length == 0) {
			$(que).find('.que-comments').html('');
			return;
		}
		comments.forEach(function(v, i) {
			v.index = i;
		});
		var texts = comments.filter(function(v) {
			return v.type == 'text';
		});
		var audios = comments.filter(function(v) {
			return v.type == 'audio';
		});
		var micros = comments.filter(function(v) {
			return v.type == 'micro';
		});

		var html = '';
		html += '<div class="que-comment-json">' + JSON.stringify(comments) + '</div>';
		if (texts.length > 0) {
			html += renderAndWrap('text', texts, function(v) {
				return v.text;
			});
		}
		var isTeacher = Leke.user.currentRoleId == 101;
		if (audios.length > 0) {
			html += renderAndWrap('audio', audios, function(v) {
				var del = isTeacher ? '<i data-index="' + v.index + '"></i>' : '';
				return '<div><dfn class="j-dfn" data-type="audio" data-url="' + v.link + '"></dfn>' + del + '</div>';
			});
		}
		if (micros.length > 0) {
			html += renderAndWrap('micro', micros, function(v) {
				var del = isTeacher ? '<i data-index="' + v.index + '"></i>' : '';
				return '<div><a href="' + v.link + '" target="_blank">' + v.text + '</a>' + del + '</div>';
			});
		}
		$(que).find('.que-comments').html(html);
		SheetFlash.scan();

		$(que).find('.que-comments').find('i').click(function() {
			var index = $(this).data('index');
			if(comments[index].type == 'micro'){
				var qid = $(this).parents('.q-que').data('qid');
				var resId = comments[index].link.substr(comments[index].link.lastIndexOf("=") + 1);
				$.post('/auth/teacher/commentary/removeCommentary.htm',{resId:resId,questionId: qid,homeworkId:Csts.homeworkId,homeworkDtlId:Csts.homeworkDtlId});
			}
			comments.splice(index, 1);
			renderHtml(que, comments);
		});
	}

	function mergeComments(comments, type, comments2) {
		if (type == 'text') {
			for (var i = 0; i < comments.length; i++) {
				if (comments[i].type == 'text') {
					comments[i].text = comments2[0].text;
					return comments;
				}
			}
			return comments.concat(comments2);
		} else if (type == 'audio') {
			return comments.concat(comments2);
		} else if (type == 'micro') {
			comments2 = comments2.filter(function(v) {
				return comments.filter(function(c) {
					return c.type == 'micro' && c.link == v.link;
				}).length == 0;
			});
			if (comments2.length > 0) {
				return comments.concat(comments2);
			}
			return comments;
		}
	}

	var Comment = {
		render : function(que) {
			var commentJson = $(que).find('.que-comment-json').html();
			if (commentJson && commentJson.length > 0) {
				var comments = JSON.parse(commentJson);
				renderHtml(que, comments);
			}
		},
		doComment : function(que) {
			var _this = this;
			var comments = [], text = '';
			var commentJson = $(que).find('.que-comment-json').html();
			if (commentJson && commentJson.length > 0) {
				comments = JSON.parse(commentJson);
				comments.forEach(function(v) {
					if (v.type == 'text') {
						text = v.text;
					}
				});
			}
			Dialog.open({
				id : "comment",
				title : '批注',
				url : '/auth/teacher/homework/commentText.htm?text=' + encodeURIComponent(encodeURIComponent(text)),
				size : 'lg',
				onClose : function(type, comments2) {
					if (type && comments) {
						if(type == 'micro') {
							_this.doSaveMicroComment(que,comments2);
						}
						comments = mergeComments(comments, type, comments2);
						renderHtml(que, comments);
					}
				}
			});
		},
		doSaveMicroComment : function (que, comments2) {
			var homeworkId = Csts.homeworkId;
			var questionId = $(que).data('qid');
			var list = [];
			for(var i = 0 ;i <comments2.length; i++ ) {
				var resName = comments2[i].text;
				var resId = comments2[i].link.substr(comments2[i].link.lastIndexOf("=")+1);
				var item = {resId: resId,resName: resName,homeworkId: homeworkId, questionId: questionId };
				list.push(item);
			}
			$.post('/auth/teacher/commentary/shareCommentary.htm',{dataJson: JSON.stringify(list),homeworkDtlId:Csts.homeworkDtlId });
		}
	}

	module.exports = Comment;
});
