define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');

	function TaskVM(params, element) {
		var self = this;
		self.tasks = ko.observableArray();
		self.disable = ko.observable(false);
		self.loadList();
	};

	/**
	 * 数据列表
	 */
	TaskVM.prototype.loadList = function() {
		var self = this;
		$.ajax({
			type : 'post',
			url : ctx + '/auth/checker/questionTask/ajax/findQuestionTaskByUserId.htm',
			data : {},
			dataType : 'json',
			success : function(json) {
				var listjson = json.datas;
				var tasks = listjson.qtList || [];
				self.tasks(tasks);
				if(tasks.length > 0) {
					self.checkFinished();
				}
			}
		});
	};
	/**
	 * 领取习题
	 */
	TaskVM.prototype.questionTask = function(data) {
		var self = this;
		var count = data.count;
		var gradeId = data.gradeId;
		var subjectId = data.subjectId;
		
		if (!gradeId || !subjectId) {
			return false;
		}
		count = parseInt(count);
		if(!count || count <= 0) {
			utils.Notice.alert('输入数字不符！');
			return false;
		}
		$.ajax({
			type : 'post',
			url : ctx + '/auth/checker/questionTask/ajax/addQuestionTask.htm',
			data : {'taskCount':count, 'gradeId':gradeId, 'subjectId':subjectId},
			dataType : 'json',
			success : function(json) {
				var msg = json && json.message;
				if(json && json.success) {
					utils.Notice.alert('领取成功！');
					self.loadList();
				} else {
					utils.Notice.alert(msg || '领取失败');
				}
			}
		});
	};	
	/**
	 * 验证是否已全部审核完
	 */
	TaskVM.prototype.checkFinished = function() {
		var self = this;
		$.ajax({
			type : 'post',
			url : ctx + '/auth/checker/questionTask/ajax/checkFinished.htm',
			data : {},
			dataType : 'json',
			success : function(json) {
				if(json.datas.result==0){
					self.disable(true);
				} else {
					self.disable(false);
				}
			}
		});
	};
	/**
	 * 审核
	 */
	TaskVM.prototype.check = function() {
		window.location.href = window.ctx + '/auth/checker/question/questionList.htm';
	}
	
	ko.components.register('que-task', {
	    template: require('./que-task.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new TaskVM(params, componentInfo.element);
	    	}
	    }
	});
});