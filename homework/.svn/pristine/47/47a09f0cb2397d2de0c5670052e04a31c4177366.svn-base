define(function(require, exports, module) {
	var $ = require('jquery');
	var LekeDate = require('common/base/date');

	// 滚动定位
	function scrollPosition(ele) {
		var $qbody = $('.q-body,.q-body-with-attach');
		var offsetTop = $qbody.offset().top;
		$(window).scroll(function() {
			var scrollTop = $(window).scrollTop();
			ele.css('top', scrollTop >= offsetTop ? scrollTop - offsetTop : 0);
		});
	}

	var ServicePanel = {
		bind : function() {
			this.bindScroll();
			this.bindToggleAnswer();
			this.bindToggleQueNav();
		},
		bindScroll : function() {
			scrollPosition($('.q-papnav'));
			scrollPosition($('.q-papbtns'));
		},
		bindToggleQueNav : function() {
			$('#panel_btnToggleQueNav').click(function() {
				$('.q-papnav').toggle();
				$('.q-body').toggleClass('q-body-large');
			});
			setInterval(function() {
				$('.panel-head-clock').text(LekeDate.format(Leke.Clock.time(), 'HH:mm:ss'));
			}, 1000);
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