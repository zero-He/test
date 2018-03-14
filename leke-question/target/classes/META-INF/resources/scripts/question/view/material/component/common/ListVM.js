define(function(require,exports,module){
	var $ = require('jquery');
	var ko = require('knockout');
	var http = require('common/base/http');
	var _ = require('underscore');
	var utils = require('utils');
	
	require('common/knockout/component/leke-page');
	
	function ListVM(){
		var self = this;
	};
	
	ListVM.prototype.init = function(options){
		var self = this;
		self.destMaterialNode = options.destMaterialNode;
		self.origMaterialNode = options.origMaterialNode;
		self.LOAD_DIR = options.LOAD_DIR;//加载列表地址
		self.COPY_DIR = options.COPY_DIR;//复制资源
		
		self.records = ko.observableArray();
		self.totalSize = ko.observable(0);
		self.checkedAll = ko.observable(false);
		
		self.disposables = [];
		
		self.disposables.push(ko.computed(function(){
			var checkedAll = self.checkedAll();
			self.$el.find('input[name="resId"]').prop('checked', checkedAll);
		}));
		
		self._initQuery();
	};
	
	ListVM.prototype._initQuery = function(){
		var self = this;
		var query = {};
		query.destMaterialNode = self.destMaterialNode;
		query.curPage = ko.observable(1);
		
		query.toJS = function(){
			var result = {};
			var materialNode = query.destMaterialNode();
			if(materialNode){
				result.materialNodeId = materialNode.materialNodeId;
			}
			result.curPage = query.curPage.peek();
			return result;
		};
		self.query = query;
		self.disposables.push(ko.computed(function(){
			var curPage = self.query.curPage();
			var materialNode = query.destMaterialNode();
			self.load();
		}));
		
	};
	
	ListVM.prototype.load = _.debounce(function(){
		var self = this;
		var query = self.query.toJS();
		var req = http.jsonp(self.LOAD_DIR,query);
		req.then(function(datas){
			self.records(datas.records);
			self.totalSize(datas.page.totalSize || 0);
		},function(msg){
			utils.Notice.alert(msg || '资源列表加载失败！');
		});
	},300);
	
	ListVM.prototype._selectedResIds = function() {
		var self = this;
		var resIds = [];
		self.$el.find('input[name="resId"]:checked').each(function() {
			resIds.push($(this).val());
		});
		return resIds;
	};
	
	ListVM.prototype.verify = function(){
		var self = this;
		var materialNode = self.destMaterialNode();
		if(!materialNode){
			utils.Notice.alert('请选择要绑定的教材章节！');
			return false;
		}
		var resIds = self._selectedResIds();
		if(!resIds.length){
			utils.Notice.alert('请选择要复制的资源！');
			return false;
		}
		return true;
	};
	
	
	ListVM.prototype.doCopy = function(){
		var self = this;
		if(self.verify()){
			var result = self.toJS();
			var req = http.jsonp(self.COPY_DIR,{dataJson: JSON.stringify(result)});
			req.then(function(datas){
				self.checkedAll(false);
				utils.Notice.alert('复制资源成功！');
			},function(msg){
				utils.Notice.alert(msg || '复制资源错误！');
			});
		}
	};
	
	module.exports = ListVM;
});