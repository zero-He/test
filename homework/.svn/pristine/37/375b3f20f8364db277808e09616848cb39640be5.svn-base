define(function(require, exports, module) {
	var $ = require('jquery');
	var _ = require('underscore');
	var ko = require('knockout');
	var LekeDate = require('common/base/date');
	var Dialog = require('dialog');
	var Utils = require('utils');
	function millisecondToDate (msd) {
        var time = parseFloat(msd);
        if (null != time && "" != time) {
        	if(time < 60) {
                time = "00:00:" +Utils.Number.format(parseInt(time),'0#');
            }
            else if (time >= 60 && time < 60 * 60) {
                time = "00:" + Utils.Number.format(parseInt(time / 60.0),'0#') + ":" + 
                Utils.Number.format(parseInt(time % 60),'0#');
            }
            else {
                time = Utils.Number.format(parseInt(time / 3600.0),'0#') + ":" + 
                Utils.Number.format(parseInt(time % 3600)/60,'0#') + ":" +
                Utils.Number.format(parseInt(time % 3600)%60,'0#');
            }
            
        }
        return time;
    }

	// 滚动定位
	function scrollPosition(ele) {
		var $qbody = $('.q-body,.q-body-with-attach');
		var offsetTop = $qbody.offset().top;
		$(window).scroll(function() {
			var scrollTop = $(window).scrollTop();
			ele.css('top', scrollTop >= offsetTop ? scrollTop - offsetTop : 0);
		});
	}

	function scrollPanelFixBottom() {
		var $qbody = $('.q-body,.q-body-with-attach');
		var offsetTop = $qbody.offset().top;
		$('.q-panel-bottom').show();
		function handler() {
			var winHeight = $(window).height();
			var scrollTop = $(window).scrollTop();
			$('.q-panel-bottom').css('top', scrollTop - offsetTop + winHeight - 70);
		}
		$(window).scroll(handler);
		$(window).resize(handler);
		$(document).ready(handler);
	}

	var ServicePanel = {
		bind : function() {
			this.bindScroll();
			this.bindToggleAnswer();
			this.bindToggleQueNav();
			this.bindToggleView();
			this.bindRedirectWork();
			this.bindCloseWindow();
		},
		bindScroll : function() {
			var attach = $('.q-attach-preview');
			if (attach.length > 0) {
				scrollPosition(attach);
				$(attach).height($(window).height());
				$(window).resize(function() {
					$(attach).height($(window).height());
				});
			} else {
				scrollPosition($('.q-papnav'));
				scrollPosition($('.q-papbtns'));
			}
		},
		bindToggleQueNav : function() {
			$('#panel_btnToggleQueNav').click(function(e) {
				e.stopPropagation();
				$('.q-papnav').toggle();
				$('.q-body').toggleClass('q-body-large');
			});
			if ($('.q-panel-bottom').length > 0) {
				scrollPanelFixBottom();
				$(document).click(function() {
					$('.q-papnav').hide();
				});
			}
			var millsecondTotal = 0;
			setInterval(function() {
				millsecondTotal++;
				$('.panel-head-clock').text(millisecondToDate(millsecondTotal));
			}, 1000);
		},
		bindRedirectWork : function() {
			function getIdAndRedirect(url) {
				$.post(url, {
					homeworkId : Csts.homeworkId,
					homeworkDtlId : Csts.homeworkDtlId
				}).done(function(json) {
					if (json.success) {
						location.href = 'viewWork.htm?homeworkId=' + Csts.homeworkId + '&homeworkDtlId='
								+ json.datas.homeworkDtlId;
					}
				})
			}
			$('#panel_btnPrevWork').click(function() {
				getIdAndRedirect('prevId.htm');
			});
			$('#panel_btnNextWork').click(function() {
				getIdAndRedirect('nextId.htm');
			});
		},
		bindCloseWindow : function() {
			$('#panel_btnCloseWindow').click(function() {
				Dialog.confirm('确认关闭页面吗？').done(function(sure) {
					if (sure) {
						window.close();
					}
				});
			});
		},
		bindToggleView : function() {
			var btn = $('#panel_btnToggleView');
			if (btn.length == 0) {
				return;
			}

			var toggleCls = 'q-display-toggle';
			$('.q-paper-group').each(function() {
				$(this).find('.q-que').filter(function() {
					return $(this).data('editable') != true && $(this).data('corrable') != true;
				}).each(function() {
					var qid = $(this).data('qid');
					$('#panel_link_' + qid).addClass(toggleCls);
				}).parent().addClass(toggleCls);
				var hasVisabled = $(this).find('.q-que').filter(function() {
					return $(this).data('editable') == true || $(this).data('corrable') == true;
				}).length > 0;
				if (hasVisabled == false) {
					$(this).addClass(toggleCls);
				}
			});
			$('.panel-body-ques').each(function() {
				var childSize = $(this).find('a').length;
				var toggleSize = $(this).find('.' + toggleCls).length;
				if (childSize === toggleSize) {
					$(this).prev().addClass(toggleCls);
				}
			});
			if ($('.' + toggleCls).length > 0) {
				var html = btn.html();
				var isShowAll = true;
				btn.click(function() {
					$('.' + toggleCls).toggle();
					btn.html(isShowAll ? '查看<br>整卷' : html);
					isShowAll = !isShowAll;
				}).triggerHandler('click');
			} else {
				btn.hide();
			}
		},
		bindToggleAnswer : function() {
			$('#panel_btnToggleAnswer').click(function() {
				$('.que-extend').toggle();
			});
		},
		showAnswer : function() {
			$('.que-extend').show();
		},
		startRefreshState : function(Answer) {
			setInterval(function() {
				var sheet = Answer.parseSheet();
				$('.panel-body-ques span').addClass('finished');
				sheet.unqids.forEach(function(qid) {
					$('#panel_link_' + qid).find('span').removeClass('finished');
				});
			}, 1000);
		}
	}

	module.exports = ServicePanel;
});