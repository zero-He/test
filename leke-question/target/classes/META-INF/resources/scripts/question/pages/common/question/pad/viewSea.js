define(function(require, exports, module) {
	//var ko = require('knockout');
	//require('repository/head/scope-title');
	//require('question/component/question/preview-btns');
	/*var DFN = require('homework/sheet.dfn');
	
	ko.applyBindings();
	DFN.init();*/
	var KO_QUE_CART = require('https://repository.leke.cn/scripts/question/quecart.js?t=201704132100');
	questionVM = {
			init: function(){
				var self = this;
				self.bindEvents();
			},
			bindEvents : function() {
				var self = this;
				$('.count').html(KO_QUE_CART.qids().length);
				$('.j-add-que').on('click',function(){
					var self = this;
					var que = {};
					que.qid =  $('.j-question').data('questionId');
					que.qtypeid = $('.j-question').data('questionTypeId');
					var qtypename = $('.multiselect').html();
					if (qtypename != -1){
						que.qtypename = qtypename;
					}
					KO_QUE_CART.addType(que);
					$('.count').html(KO_QUE_CART.qids().length);
				 });
			},
			doAddQueCart : function() {
				var self = this;
				var que = {};
				que.qid =  $('.j-question').data('questionId');
				que.qtypeid = $('.j-question').data('questionTypeId');
				var qtypename = $('.multiselect').html();
				if (qtypename != -1){
					que.qtypename = qtypename;
				}
				KO_QUE_CART.addType(que);
				$('.count').html(KO_QUE_CART.qids().length);
			}
	}
	questionVM.init();
});