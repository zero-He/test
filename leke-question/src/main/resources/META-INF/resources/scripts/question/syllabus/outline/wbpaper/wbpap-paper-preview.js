define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var utils = require('utils');
	var dialog = require('dialog');
	var BeikeService = require('repository/service/PushBeikeService');
	var Sheet = require('homework/sheet');
	var TEACHER = 101;
	
	function PreviewVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$c = self.$el.find('.j-content');
		
		self.paperId = params.paperId;
		self.mode = params.mode;
		self.scene = params.scene;
		self.isTeacher = Leke.user.currentRoleId === TEACHER;
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self.load();
		}));
	}
	
	var WB_PVW_URL = Leke.domain.paperServerName + '/auth/common/workbook/paper/preview.htm';
	
	PreviewVM.prototype.load = function() {
		var self = this, paperId = self.paperId();
		if(!paperId) {
			self.$c.html('');
			return;
		}
		var req = $.ajax({
			type: 'post',
			url: WB_PVW_URL,
			data: {
				paperId: paperId
			},
			dataType: 'html'
		});
		req.then(function(html) {
			if(paperId !== self.paperId.peek()) {
				return;
			}
			self.$c.html(html);
			Sheet.fInit();
		}, function() {
			utils.Notice.alert('内容加载失败!');
			self.$c.html('');
		});
	};
	
	PreviewVM.prototype.toAddPaper = function() {
		var self = this;
		window.open(Leke.domain.paperServerName + '/auth/common/paper/edit/index.htm?copy=add&paperId=' + self.paperId());
	};
	
	PreviewVM.prototype.toFastBeike = function() {
		var self = this;
		var paperId = self.paperId();
		dialog.confirm('此种方式将默认为系统设置分数以及标题，是否继续？').then(function(sure) {
			if(sure) {
				BeikeService.pushPaperToBeike([paperId]);
			}
		});
	};
	
	PreviewVM.prototype.dispose = function() {
		var self = this;
		self.disposables.forEach(function(d) {
			d.dispose();
		});
	};
	
	ko.components.register('wbpap-paper-preview', {
	    template: require('./wbpap-paper-preview.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new PreviewVM(params, componentInfo.element);
	    	}
	    }
	});
});