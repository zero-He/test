/*
 * 重批渲染总得分
 * */
define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = require('utils');
	var scoreUtils = require('homework/common/sheet.score.js');
	var win = {
			init : function(score){
				this.renderTotalScore();
			},
			renderTotalScore : function(score) {
				if ($('#isAgain').val()=='true' || $('#isAgain').val() =='1') {
					var score = this.getTotalScore();
					var scoreHtml = scoreUtils.render(score);
					if ($('.p-exam-head .m-score').length > 0) {
						$('.p-exam-head .m-score').remove();
					}
					$('.p-exam-head').append(scoreHtml);
				}
			},
			getTotalScore : function(){
				var totalScore = 0;
				$('.p-group-score-value').each(function(i){
					totalScore = totalScore + parseFloat($(this).html());
				});
				return utils.Number.toFixed(totalScore,1)+'';
			}
	};
    win.init();
    module.exports = win;
});
