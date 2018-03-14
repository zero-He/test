define(function(require, exports, module) {
	var $ = require('jquery');
	var _ = require('underscore');
	var ko = require('knockout');
	var LekeDate = require('common/base/date.js');
	var Panel = require('homework/question-panel-generate.js');
	var dialog = require('dialog');
	var doWork;
	var Answer;
	function Right(params,el){
		var self = this;
		self.paperIsIntact = params.paperIsIntact || false;//是否显示保存和提交作业按钮
		self.workType = params.workType;// 0 学生做作业 1老师批改作业 2 学生订正作业 3 老师复批作业
		self.questionsHtml = ko.observable();//题目面板显示
		self.render();
		self.showAnchor();
		self.smartPosition(this.$anchorPanle);

		if(self.workType == 0){
			doWork = require('homework/homework/service/doWorkService');
			Answer = require('homework/common/sheet.answer');
		} else if(self.workType == 1){
			doWork = require('homework/homework/service/correctWorkService');
			Answer = require('homework/common/sheet.correct');
		} else if(self.workType == 2){
			doWork = require('homework/homework/service/doBugFixService');
			Answer = require('homework/common/sheet.answer');
		} else if(self.workType == 3){
			doWork = require('homework/homework/service/reviewWorkService');
			Answer = require('homework/common/sheet.review');
		}
		doWork.fInit();
		//渲染题目面板
		initPanel();
		refreshTime();
	};
	
	Right.prototype.render = function(){
		var self = this;
		self.$anchorPanle = $('.m-anchorpanel');
		self.$anchorBtn = $('.m-anchorpanel').find('.show-box');
		self.$anchorBox = $('.m-anchorpanel').find('.box');
	};
	
	/* 题号状态 */
	Right.prototype.showAnchor = function(){
		var self = this;
		this.$anchorBtn.on('click', function() {
			if (self.$anchorBtn.hasClass('active')) {
				self.$anchorBtn.removeClass('active');
				self.$anchorBox.hide();
			} else {
				self.$anchorBtn.addClass('active');
				self.$anchorBox.show();
			}
		});
	};
	
	/* 智能浮动 */
	Right.prototype.smartPosition = function(ele){
		var elementTopVal = ele.offset().top,   
		storePosition = ele.css("position");
		
		$(window).scroll(function() {
			var scrollTopVal = $(this).scrollTop();
			if (scrollTopVal >= elementTopVal) {
				ele.css({
					position: "fixed",
					top: 0
				});    
			}else {
				ele.css({
					position: storePosition,
					top: elementTopVal
				});    
			}
		});
	};
	
	Right.prototype.close = function(){
		dialog.confirm('确认关闭页面吗？').done(function(sure){
			if(sure){
				window.close();
			}
		});
	};
	
	var timer = null;
	var DELAY = 1500;//一秒
	
	/* 动态时间*/
	 function refreshTime(){
		var self = this;
		if(timer){
			clearTimeout(timer);
			timer == null;
		}
		var sheet = Answer.parseSheet();
		var  ques = Answer.parseComplete() || [];
		var count = 0;
		_.each(ques,function(n,index){
			var isDone = n.isDone;
			if(!isDone){
				Panel.done(n.qid);//已完成
			}else{
				Panel.done(n.qid,-1);//标记未完成
				count++;
			}
		});
		
		$('#count1').html(count);
		timer = setTimeout(refreshTime, DELAY);
	};
	 function initPanel(){
			var params = {isDoWork:true,panel:{groups:[]}};
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
		};

	ko.components.register('question-panel-right',{
		template: require('./question-panel-right.html'),
		viewModel: {
			createViewModel: function(params,componentInfo){
				return new Right(params,componentInfo);
			}
		}
	});
	
});