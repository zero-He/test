define(function(require, exports, module) {
	var $ = require('jquery');
	var ko = require('knockout');
	var _ = require('underscore');
	var dialog = require('dialog');
	var utils = require('utils');
	
	function EditVM(){
		var self = this;
		var que = window.QuestionCtx || {};
		self.queType = que.queType || {};
		self.queTypes = que.queTypes || [];
		self.queTypeSubs =  que.queTypeSubs || [];
		self.parseQueTypes = ko.observableArray([]);
		self.init();
	};
	
	EditVM.prototype.init = function(){
		var self = this;
		var types = self.queTypes;
		var subs = _.indexBy(self.queTypeSubs,'questionTypeId');
		var parseTypes = _.map(types,function(t){
			var check = false;
			var key = t.questionTypeId + '';
			if(subs[key]){
				check=true;
			}
			t.check = check;
			t.vis = self.test(t);
			return t;
		});
		self.parseQueTypes(parseTypes);
	};
	
	EditVM.prototype.test = function(t){
		var self = this;
		//主观题显示所有，客观题只显示客观题
		var subjective = self.queType.subjective;
		if(subjective){
			return true;
		}else{
			return !t.subjective;
		}
	};
	
	EditVM.prototype.save = function(){
		var self = this;
		var queType = self.queType;
		
		//选中的
		var subs = _.filter(self.parseQueTypes(),function(n){
			return n.vis && n.check;
		});
		
		//转化题目子类型
		var i = 0;
		subs = _.map(subs,function(n){
			var ret = {};
			ret.ord = i++;
			ret.subTypeId = n.questionTypeId;//选择的子题
			ret.questionTypeId = queType.questionTypeId;//主题ID
			return ret;
		});
		var queTypeSub = {
				questionTypeId: queType.questionTypeId,
				subs: subs
			};
		var dataJson = JSON.stringify(queTypeSub);
		var req = $.ajax({
			type: 'post',
			url: window.ctx + '/auth/admin/questionType/addQuestionTypeSub.htm',
			dataType : 'json',
			data:{dataJson: dataJson}
		});
		req.then(function(data){
			if(data.success){
				utils.Notice.alert('保存成功！');
				dialog.close('editQuestionTypeSub', true);
			}
		});
	};
	
	EditVM.prototype.close = function(){
		dialog.close('editQuestionTypeSub', true);
	};
	
	ko.applyBindings(new EditVM());
});
