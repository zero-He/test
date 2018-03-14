define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var AnalysisRender = require('./AnalysisRender');
	var AnalysisPaper = require('./AnalysisPaper');
	var Panel = require('homework/question-panel-generate.js');

	var Analysis = {
		tpl : '<div id="jAnalysisPaper" class="f-mt20 f-mb10" style="height: 360px;"></div>',
		init : function() {
			// 总体分析正确率
			var $ele = $(this.tpl).prependTo('.p-exam-body');
			AnalysisPaper.render($ele[0], ReportCst.sums);
			// 详细题目分析
			AnalysisRender.render(ReportCst.charts);
			Analysis.loadPanel();
		},loadPanel :function(){
			if($('#j_question_panel').length> 0) {
				var params = {isDoWork:false,panel:{finished:[],groups:[]}};
				$('.p-group-title').each(function(i){ 
					var group = {title:$(this).find('dfn').text(),questions:[]};
					$(this).parents('li:first').find('.p-group-body > .j-question').each(function(j){
					  var qid = $(this).data('question-id');
					  var ord = $(this).data('ord');
					  group.questions.push({id:qid,ord:ord});
					});
					params.panel.groups.push(group);
				});
				Panel.init(params,null,'j_question_panel');
			}
			
		}
	};

	Analysis.init();

});
