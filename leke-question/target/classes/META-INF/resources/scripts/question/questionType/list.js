define(function(require, exports, module) {
	var $ = require('jquery');
	var ko = require('knockout');
	var _ = require('underscore');
	var dialog = require('dialog');
	var utils = require('utils');
	
	function ListVM(){
		var self = this;
		self.qTypes = ko.observableArray();
		self.init();
	};
	
	ListVM.prototype.init = function(){
		var self = this;
		var req = $.ajax({
			type:'get',
			url:window.ctx + '/auth/admin/questionType/findAllQuestionTypes.htm',
		});
		req.then(function(datas){
			var queTypes = eval(datas);
			queTypes = _.filter(queTypes,function(n){
				return n.template != 'Cloze' && n.template != 'Reading';
			});
			self.qTypes(queTypes); 
		},function(msg){
			utils.Notice.alert('数据加载失败！');
		});
	};
	
	ListVM.prototype.open = function($data){
		var self = this;
		var queType = $data;
		var cfg = {
				id: 'editQuestionTypeSub',
				title: '添加子题型',
				size: 'nm',
				onClose: function() {
				}
			};
			
		var params = {
			questionTypeId: queType.questionTypeId,
			questionTypeName: encodeURI(queType.questionTypeName),
			subjective: queType.subjective
		};
		var url = window.ctx + '/auth/admin/questionType/editQuestionTypeSub.htm';
		cfg.url = linkTo(url, params);
		dialog.open(cfg);
	};
	
	function linkTo(url, params) {
		var result = url;
		if(params) {
			var pairs = [];
			_.each(params, function(v, k) {
				pairs.push(k + '=' + v);
			});
			result += '?' + pairs.join('&');
		}
		return result;
	}
	
	ko.applyBindings(new ListVM());
});
