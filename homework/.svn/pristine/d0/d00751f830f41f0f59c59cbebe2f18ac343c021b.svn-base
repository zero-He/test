define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require("utils");
	var Handlebars = require("common/handlebars");
	var I18n = require('homework/common/i18n');
	var Homework = {
		sort : null,
		loadUrl : 'loadHomeworkDtlList.htm',
		init : function() {
			this.loadHomeworkDtlList(null);
			this.bindEvent();
		},
		bindEvent : function(){
			var _this = this;
			
			$('.m-sorting').click(function(){
				var className = $(this).attr('class');
				var sort = -1;
				if(className.indexOf('m-sorting-asc') > -1){
					$(this).addClass('m-sorting-desc').removeClass('m-sorting-asc');
				}else if(className.indexOf('m-sorting-desc') > -1){
					$(this).addClass('m-sorting-asc').removeClass('m-sorting-desc');
					sort = 1;
				}else{
					$(this).addClass('m-sorting-desc').removeClass('m-sorting-asc');
				}
				_this.sort = sort;
				_this.loadHomeworkDtlList(_this.sort);
			});
		},
		loadHomeworkDtlList : function(sort) {
			var data = {
				homeworkId : $('#j_homeworkId').val(),
				studentName : '',
				sort : sort,
				questionNum : $('#j_questionNum').val()
			};
			$.post(this.loadUrl, data, function(json) {
				if (json.success && json.datas.list && json.datas.list.length > 0) {
					var page = {
						dataList : json.datas.list,
						trStyle : function() {
							return this.submitStatus == 2 ? " style=background:#f9f4eb" : "";
						}
					}
					$.each(page.dataList, function() {
						this.score = Utils.Number.toFixed(this.score, 1);
						if (Leke.user.currentRoleId == 101 && this.correctTime == null) {
							this.buttonName = $.i18n.prop('homework.homework.check.correct');
							this.buttonAction = 'correctWork';
						} else {
							this.buttonName = $.i18n.prop('homework.homework.check.view');
							this.buttonAction = 'viewWork';
						}
						this.timsHtmlStr = function() {
							if (this.submitTime == null) {
								return '<span class="s-c-orange">'+$.i18n.prop('homework.homework.message.span')+'</span>';
							}
							return this.submitTimeStr;
						};
						this.usedTimeStr = function(){
							if(this.usedTime == null ||this.usedTime == 0 ){
								return '--';
							}else{
								return this.usedTime + '分钟';
							}
						}
						this.chijiao = function(){
							if(this.submitStatus ==2 ){
								return '（迟交）';
							}
						};
						this.progressRate = function(){
							return Utils.Number.toFixed(Utils.Number.toFixed(this.paperQuestionNum == 0 ? 0 : this.correctCount / this.paperQuestionNum, 2) * 100, 2);
						};
					});
					Handlebars.render("#homeworkDtlContext_tpl", page, '#homeworkDtlListContext');
					$('.j-no-data').hide();
				} else {
					$('#homeworkDtlListContext').html('');
					$('.j-no-data').show();
				}
			});
		}
	};

	Homework.init();

});
