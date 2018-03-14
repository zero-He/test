
/**
 * 扩展：提问、收藏、心得
 */
define(function(require, exports, module) {
	var $ = require('jquery');
	var Doc = require('homework/sheet.doc');
	var Dialog = require("dialog");
	var Utils = require('utils');
	require('common/ui/ui-mask/jquery.mask');
	var Extend = {
		screenDialog : null,
		favoriteQuestion : [],
		questionStem : $('#j-questionStem').data('qid') != '',
		init : function() {
			this.initBindExperie();
			this.initBindDoubt();
			this.initBindFavorite();
			this.initFavouriteState();
			//this.initCardFavorite();
		},
		initCardFavorite : function() {
			var _this =this;
			//暂时不支持截图
			//_this.insertScreenshot();
			if (Doc) {
				// Doc API: 
				// bind : function(event, func)
				// startScreenShot : function()
				// cancelScreenShot : function()
				Doc.bind('screenShot', function(url) {
					// 截图成功回调
					if (url) {
						// 截图成功，显示收藏窗口
						$('#j_stemContent').val(url);
						$('.j_screenshot_img').attr('src',url);
						$('.j_screenshot_img').removeClass('f-hide');
						_this.screenDialog.show();
						_this.setBindCopePaster(true);
					} else {
						// 取消截图
						$('#j_stemContent').val('');
						Dialog.close();
						_this.setBindCopePaster(false);
					}
				});
				$('.p-card-left').on('click','.j-screen',function() {
					var d_title = _this.questionStem ? '选择题干': '收藏题目';
					if(_this.screenDialog == null){
						_this.screenDialog = Dialog.open({
							title : d_title,
							size : 'nm',
							tpl : require('./screenshot.html'),
							onClose : function() {
								_this.screenDialog = null;
								_this.setBindCopePaster(false);
							}
						});
						_this.setBindCopePaster(true);
						_this.bindEvent();
						_this.initBindSelect();
						_this.bindSubmit();
						$('.cut').click(function() {
							_this.screenDialog.hide();
							_this.setBindCopePaster(false);
							Doc.startScreenShot();
						});
					}else{
						_this.screenDialog.show();
						_this.setBindCopePaster(true);
					}
					
				});
			}
		},
		initBindDoubt : function() {
			require.async('core-business/QuestionDoubt', function(QuestionDoubt) {
				QuestionDoubt.init(true);
				$('.p-sheet').on('click', '.q-re-doubt', function() {
					var question = $(this).closest('.q-container');
					var questionId = question.attr('qid');
					var subjectId = $('#jSubjectId').val();
					var correctError = $('#jCorrectError').val();
					var homeworkDtlId = '';
					var techerId =$('#jTeacherId').val();
					if(techerId == '' || techerId == undefined || $('#homeworkType').val() == 7){
						techerId = null;
					}
					//周月错题作业不能提问批改错误
					if (correctError == 'true' && $('#homeworkType').val() != 6 && $('#homeworkType').val() != 7) {
						homeworkDtlId = $('#homeworkDtlId').val();
					}
					QuestionDoubt.open(questionId, subjectId, homeworkDtlId,techerId,$('#homeworkDtlId').val(),"");
				});
			});
		},
		initBindExperie : function() {
			require.async('core-business/QuestionExerie', function(QuestionExerie) {
				QuestionExerie.init(true);
				$('.p-sheet').on('click', '.q-re-experie', function() {
					var question = $(this).closest('.q-container');
					var questionId = question.attr('qid');
					QuestionExerie.open(questionId);
				});
			});
		},
		initBindFavorite : function() {
			require.async('core-business/QuestionFavorite', function(QuestionFavorite) {
				$('.p-sheet').on('click', '.q-re-wrong', function() {
					var $element = $(this);
					
					var questionId = $(this).closest('.q-container').attr('qid');
					var subjectId = $('#jSubjectId').val();
					var questionSrc = $('#jQuestionSource').val();
					QuestionFavorite.doFavorite(questionId, subjectId, questionSrc,function(favourite){
						if(favourite) {
							$element.removeClass('p-collect').addClass('p-has-collect');
//							_this.favoriteQuestion.push(questionId);
							$element.attr('title','点击取消收藏')
						} else {
							$element.removeClass('p-has-collect').addClass('p-collect');
//							_this.favoriteQuestion.splice(_this.favoriteQuestion.indexOf(questionId),1);
							$element.attr('title','收藏到好题本')
						}
					});
				
				});
			});
		},initFavouriteState : function () {
			var _this = this;
			var url = Leke.domain.wrongtopicServerName + "/auth/common/findFavoriteQuestion.htm?jsoncallback=?";
			var questionIds = [];
			$('.q-re-wrong').parents('.j-question').each(function(i,n){
				questionIds.push($(n).data('question-id'));
			})
			if(questionIds.length == 0) {
				return;
			}
			var params = {questionIds:questionIds}
			$.getJSON(url,params,function(json){
				if(json.success){
					var favQueIds = json.datas.list;
					_this.favoriteQuestion = favQueIds;
					$('.q-re-wrong').parents('.j-question').each(function(i,n){
						var qid = $(n).data('question-id');
						if(favQueIds.indexOf(qid) > -1){
							var $element = $(n).find('.q-re-wrong');
							$element.addClass('p-has-collect');
							$element.attr('点击取消收藏');
							
						}
					})
				}
			})
			
		},bindEvent : function() {
			var _this =this;
			$('#j_content').keyup(function(){
				_this.keypress();
			});
			$('#j_content').blur(function(){
				_this.keypress();
			});
		},
		initBindSelect :function(){
			var _this = this;
			var datas = [];
			datas.push('<option value="">请选择</option>');
			var itemOptionGroupTmpl = '<optgroup label="{{groupTitle}}">{{options}}</optgroup>' 
			var itemOptionTmpl = '<option value ="{{questionId}}">{{ord}}</option>';
			$('.p-group-title').each(function(i){ 
				var options = [];
				$(this).parents('li:first').find('.p-group-body > .j-question').each(function(j){
				  var qid = $(this).data('question-id');
				  var ord = $(this).data('ord');
				 
				  var ot = itemOptionTmpl.replace('{{questionId}}',qid).replace('{{ord}}',ord)
				  options.push(ot);
				});
				var otg = itemOptionGroupTmpl.replace('{{groupTitle}}',$(this).find('dfn').text()).replace('{{options}}',options.join(''));
				datas.push(otg);
			});
			$('#j_screeQuestionId').html(datas.join(''));
			if($('#j-questionStem').data('qid') != ''){
				$('.j-sel-question').hide();
			}
		},
		bindSubmit : function() {
			var _this = this;
			$("#j_submit").on("click", function() {
				var submit_btn = this;
				var stemContent = $('#j_stemContent').val();
				var questionId  = $('#j-questionStem').data('qid');
				if(questionId != ''){
					$('#j_screeQuestionId').val(questionId); 
				} else {
					questionId =  $('#j_screeQuestionId').val();
				}
				if(questionId == ''){
					Utils.Notice.alert('请选择题号');
					return;
				}
				if (stemContent == '') {
					Utils.Notice.alert('题目不能为空');
				} else {
					questionTitle =  $('#j_screeQuestionId option:selected').parent('optgroup').attr('label') + '第'+$('#j_screeQuestionId option:selected').text()+'题';
					var url = Leke.domain.wrongtopicServerName + "/auth/student/wrong/saveScreenshot.htm?jsoncallback=?";
					var stemContentTmpl = '<div style="" class="q-question f-bfc">【{{title}}】<div class="imgmaxwidht"><img src="{{src}}" /></div></div>';
					var params = {
						"stemContent" : stemContentTmpl.replace('{{src}}',stemContent).replace('{{title}}',questionTitle),
						"questionId" : questionId,
						"exerieContent" : $('#j_content').val(),
						"subjectId" : $('#jSubjectId').val(),
						"isQueStem" : _this.questionStem
					};
					$(submit_btn).lock();
					$.getJSON(url, params, function(json) {
						if (json.success) {
							if(json.datas.result > 0){
								Utils.Notice.alert('操作成功!');
							}else {
								Utils.Notice.alert('操作异常!');
							}
							setTimeout(function() {
								$(submit_btn).unlock();
								Dialog.close();
							}, 600);
						}
					});
				}
			});
		},	keypress : function() {   
			var myContent = $('#j_content').val();
			var maxLen = 120;
			var len = myContent.length;//已输入字符个数
			var surplusLen = maxLen - len;//剩余可输入字符个数
			if (len > maxLen) {
				$('#j_content').val(myContent.substr(0,maxLen));
				len = maxLen;
				surplusLen = 0;   
			}  
			$('#j_already_len').text(len);  
			$('#j_surplus_len').text(surplusLen);  
		}, insertScreenshot : function(){
			//答题卡
			if(Leke.user.currentRoleId == 100 && $('.p-card-left h5').length > 0){
				var btnName = $('#j-questionStem').data('qid') != '' ? '选择题干' : '收藏题目';
			  $('.p-card-left h5').append('<a><input type="button" class="u-btn u-btn-nm u-btn-bg-turquoise f-fr j-screen" value="'+ btnName +'" /></a>');
			}
		}, setBindCopePaster : function(flag){
			if(flag == undefined || flag ==''){
				flag =false;
			}
			function stopfun(event) {
				return flag;
			}
			document.body.oncopy = stopfun;
			document.body.oncontextmenu = function(){return false;};
			document.oncontextmenu = function(){return false;};
			document.onkeydown = document.body.onkeydown = function(event) {
				if (!event) {
					event = window.event;
				}
				if (event.ctrlKey && (event.keyCode == 67 || event.which == 67 || event.keyCode == 86 || event.which == 86)) {
					return flag;
				}
			}
			document.onmousedown = document.body.onmousedown = function(event) {
				if (!event) {
					event = window.event;
				}
				if (event.button == 2 || event.button == 3) {
					return flag;
				}
			}
		}
	};

	Extend.init();

	module.exports = Extend;
});