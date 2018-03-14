define(function(require, exports, module) {
	var KindEditor = require('editor');

	var options = {
		pasteType : 0,
		width : '100%',
		height : '200px',
		resizeType : 1,
		autoHeightMode : true,
		crossDomain : (document.domain == Leke.domain.mainDomain),
		items : ['bold', 'italic', 'underline', 'subscript', 'superscript', 'image', 'paint', 'photo', '|',
				'justifyleft', 'justifycenter', 'justifyright', '|', 'undo', 'redo', 'wordnumber'],
		htmlTags : {
			img : ['src', 'width', 'height', 'border', 'alt', 'title', 'align', '.width', '.height', '.border',
					'.float'],
			p : ['align', '.text-align', '.color', '.background-color', '.font-size', '.font-family', '.background',
					'.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.text-indent',
					'.margin-left'],
			'strong,b,em,i,u,sub,sup' : []
		},
		uploadJson : ctx + '/auth/common/upload/kindeditor.htm',
		afterChange : function() {
			if (!this.wordnumber) {
				var tpl = '<div style="border-top: 1px solid #ccc; font-size: 12px; padding: 2px 10px;"></div>';
				this.wordnumber = KindEditor(tpl);
				this.statusbar.before(this.wordnumber);
			}
			this.wordnumber.html('已输入约 ' + this.count('text') + ' 个字符');
		}
	};

	var RichEditor = {
		editors : [],
		init : function(target) {
			this.editors.push(KindEditor.create(target, options));
		},
		// 同步KindEditor的内容到textarea
		sync : function() {
			this.editors.forEach(function(editor) {
				editor.sync();
			});
		}
	};

	module.exports = RichEditor;
});