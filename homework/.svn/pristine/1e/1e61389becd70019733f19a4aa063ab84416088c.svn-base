define(function(require, exports, module) {
	var $ = require('jquery');
	var ko = require('knockout');
	var CTS = {
			progressUrl: window.ctx + '/auth/common/homework/homeworkProgress.htm'
	};
	var userId = Leke.user.userId;
	function Right(params,el){
		var self = this;
		self.homeworkId = params.homeworkId;
		self.fileServerName = params.fileServerName;
		self.staticServerName = params.staticServerName;
		self.dynamicUrl = params.incentiveServerName + '/auth/common/incentive/dynamic.htm';
		self.paperIsIntact = params.paperIsIntact;//是否显示保存和提交作业按钮
		self.finished = ko.observableArray();//作业动态完成
		self.unfinished = ko.observableArray();//作业动态未完成
		self.workState = ko.observable(false);
		self.render();
		self.loadStatus();
		self.showStatus();
	};
	
	Right.prototype.loadHomeworkProgress = function(){
		var self = this;
		var req = $.ajax({
			type: 'post',
			url: CTS.progressUrl,
			data: {homeworkId: self.homeworkId},
			dataType: 'json'
		});
		req.then(function(datas){
			var progress = datas.datas.progress;
			var finished =_.filter(progress.finished || [],function(n){
				return n.userId != userId
			});
			var unfinished =_.filter(progress.doing || [],function(n){
				return n.userId != userId
			});
			finished = _.map(finished,function(n){
				if(n.photo){
					n.photo = self.fileServerName + '/' + n.photo;
				}else{
					n.photo = self.staticServerName + '/images/home/photo.png';
				}
				
				return n;
			});
			unfinished = _.map(unfinished,function(n){
				if(n.photo){
					n.photo = self.fileServerName + '/' + n.photo;
				}else{
					n.photo = self.staticServerName + '/images/home/photo.png';
				}
				return n;
			});
			if(finished.length > 0 || unfinished.length > 0){
				self.workState(true);
			}
			self.finished(finished);
			self.unfinished(unfinished);
		},function(msg){});
	};
	
	Right.prototype.render = function(){
		var self = this;
		self.$statusBtn = $('.m-operation').find('.show-status');
		self.$statusBox = $('.m-operation').find('.box');
	};
	
	
	
	var TEN = 10000;//十秒
	Right.prototype.loadStatus = function(){
		var self = this;
		self.loadHomeworkProgress();
		self.$statusBtn.addClass('active');
		self.$statusBox.show();
		setTimeout(function(){
			self.$statusBtn.removeClass('active');
			self.$statusBox.hide();
		},TEN);
	};
	/* 作业动态 */
	Right.prototype.showStatus = function(){
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
	
	/* 全屏*/
	Right.prototype.fullscreen = function(){
		
	};

	ko.components.register('do-work-right',{
		template: require('./do-work-right.html'),
		viewModel: {
			createViewModel: function(params,componentInfo){
				return new Right(params,componentInfo);
			}
		}
	});
});