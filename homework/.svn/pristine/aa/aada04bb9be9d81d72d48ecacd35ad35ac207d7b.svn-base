define(function(require, exports, module) {
	var $ = require('jquery');
	var _ = require('underscore');
	var ko = require('knockout');

	$(document.body).append('<div data-bind="component: { name: \'homework-dynamic\', params: null }"></div>')

	var progressUrl = window.ctx + '/auth/student/homework/homeworkProgress.htm';

	function fillPhoto(v) {
		if (v.photo) {
			v.photo = Leke.domain.fileServerName + '/' + v.photo;
		} else {
			v.photo = Leke.domain.staticServerName + '/images/home/photo.png';
		}
		return v;
	}

	function filterSelf(v) {
		return v.userId != Leke.user.userId;
	}

	function Right(params, el) {
		var self = this;
		self.homeworkId = Csts.homeworkId;
		self.dynamicUrl = Leke.domain.incentiveServerName + '/auth/common/incentive/dynamic.htm';
		self.finished = ko.observableArray();//作业动态完成
		self.unfinished = ko.observableArray();//作业动态未完成
		self.workState = ko.observable(false);
		self.render();
		self.loadStatus();
		self.showStatus();
	};

	Right.prototype.loadHomeworkProgress = function() {
		var self = this;
		$.post(progressUrl, {
			homeworkId : Csts.homeworkId
		}).done(function(datas) {
			var progress = datas.datas.progress;
			var finished = (progress.finished || []).filter(filterSelf).map(fillPhoto);
			var unfinished = (progress.unfinished || []).filter(filterSelf).map(fillPhoto);
			if (finished.length > 0 || unfinished.length > 0) {
				self.workState(true);
			}
			self.finished(finished);
			self.unfinished(unfinished);
		});
	};

	Right.prototype.render = function() {
		var self = this;
		self.$statusBtn = $('.m-operation').find('.show-status');
		self.$statusBox = $('.m-operation').find('.box');
	};

	var TEN = 10000;//十秒
	Right.prototype.loadStatus = function() {
		var self = this;
		self.loadHomeworkProgress();
		self.$statusBtn.addClass('active');
		self.$statusBox.show();
		setTimeout(function() {
			self.$statusBtn.removeClass('active');
			self.$statusBox.hide();
		}, TEN);
	};
	/* 作业动态 */
	Right.prototype.showStatus = function() {
		var self = this;
		self.$statusBtn.on('click', function() {
			if (self.$statusBtn.hasClass('active')) {
				self.$statusBtn.removeClass('active');
				self.$statusBox.hide();
			} else {
				self.loadHomeworkProgress();
				self.$statusBtn.addClass('active');
				self.$statusBox.show();
			}
		});
	};

	ko.components.register('homework-dynamic', {
		template : require('./homework-dynamic.html'),
		viewModel : {
			createViewModel : function(params, componentInfo) {
				return new Right(params, componentInfo);
			}
		}
	});

	ko.applyBindings();
});