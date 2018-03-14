define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var I18n = require('diag/common/i18n');

	var AnalysisRender = {
		domTpl : '<div class="q-analysis f-clearfix"><label>【' + $.i18n.prop('diag.common.html.label.text')
				+ '】</label><div class="q-analysis-chart" qid="{{qid}}"></div></div>',
		init : function() {
			var paperType = $('.j-sheet').data('paper-type');
			if (paperType == 2) {
				this.domTpl = '<div class="q-analysis f-clearfix"><label>' + $.i18n.prop('diag.common.html.label.text')
						+ '：</label><div class="q-analysis-chart" qid="{{qid}}"></div></div>';
			}
		},
		/**
		 * 根据数据生成图表。<br>
		 * 数据格式：[{questionId:123,datas:''},{questionId:124,datas:''}]
		 */
		render : function(dataList) {
			var _this = this;
			$.each(dataList, function(i, el) {
				var qid = el.questionId, datas = json.parse(el.datas);
				var analysisDom = $(_this.domTpl.replace('{{qid}}', qid));

				var question = $('.j-question[data-question-id=' + qid + ']');
				question.find('.q-question,.p-answer-answer').append(analysisDom);
				// 根据题型模板，同步加载图表对象
				require.async('./' + question.data('template'), function(_Render) {
					_Render.render(analysisDom.find('.q-analysis-chart')[0], datas, qid, el.names);
				});
			});
		}
	};
	
	AnalysisRender.init();

	module.exports = AnalysisRender;
});
