
/**
 * 试卷得分美化。
 */
define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');

	var Score = {
		tpl : '<div class="m-score">{{#list}}<span class="score-{{.}}"></span>{{/list}}<div class="underline">underline</div></div>',
		// 根据得分生成HTML并追加到给定父容器中
		// 父容器必须设置(position:relative)
		append : function(parent, score) {
			var html = this.render(score);
			$(parent).append(html)
		},
		// 根据得分生成HTML并返回
		render : function(score) {
			if (score == undefined || score === '') {
				return '';
			}
			var data = {
				list : []
			};
			for (var i = 0; i < score.length; i++) {
				var val = score.charAt(i);
				if (val == '.') {
					val = '10';
				}
				data.list.push(val);
			}
			return Mustache.render(this.tpl, data);
		}
	};

	module.exports = Score;
});
