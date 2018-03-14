define(function(require,exports,module){
	var $ = require('jquery');
	var ko = require('knockout');
	var _ = require('underscore');
	var utils = require('utils');
	var tree = require('tree');
	var http = require('common/base/http');
	var basicInfoService = require('core-business/service/basicInfoService');
	
	require('./mat-courseware-list');
	require('./mat-microcourse-list');
	require('./mat-beikepkg-list');
	require('./mat-paper-list');
	
	var RES_TYEPS = [{
		name: '课件',
		resType: 3
	},{
		name: '微课',
		resType: 4
	},{
		name: '试卷',
		resType: 2
	},{
		name: '备课包',
		resType: 6
	}];
	
	function BindingVM(params,element){
		var self = this;
		self.$el = $(element);
		
		self.data = {};
		var material = self.data.material = params.material;
		self.schoolStageId = material.schoolStageId;
		self.subjectId = material.subjectId;
		self.data.destMaterialNode = ko.observable();
		self.data.origMaterialNode = ko.observable();
		
		self.pressId = ko.observable();
		self.presses = ko.observableArray();
		self.materialId = ko.observable();
		self.materials =  ko.observableArray();

		self.bindingMmaterials =  ko.observableArray();
		
		self.isCopy = ko.observable(false);
		
		self.resTypes = RES_TYEPS;
		self.resType = ko.observable(3);
		
		self._init();
		
		self.disposables = [];
		self.disposables.push(ko.computed(function(){
			var pressId = self.pressId();
			self.materialId(null);
			self.loadMaterial();
		}));
		
		self.disposables.push(ko.computed(function(){
			var materialId = self.materialId();
			self.initOrigTree();
		}));
		
		self.disposables.push(ko.computed(function(){
			var origMaterialNode = self.data.origMaterialNode();
			if(origMaterialNode){
				self.loadBindingMmaterials();
			}
		}));
		
	};
	
	BindingVM.prototype._init = function(){
		var self = this;
		self.loadPress();
		self.initDestTree();
	};
	
	BindingVM.prototype.loadPress = function(){
		var self = this;
		var schoolStageId = self.schoolStageId;
		var subjectId = self.subjectId;
		basicInfoService.presses(schoolStageId,subjectId).then(function(presses){
			self.presses(presses);
		},function(){
			self.presses([]);
		});
	};
	
	BindingVM.prototype.loadMaterial = function(){
		var self = this;
		var schoolStageId = self.schoolStageId;
		var subjectId = self.subjectId;
		var pressId = self.pressId();
		if(!schoolStageId || !subjectId || !pressId){
			self.materials([]);
			return;
		}
		basicInfoService.materials(schoolStageId,subjectId,pressId).then(function(materials){
			self.materials(materials);
		},function(){
			self.materials([]);
		});
	};
	
	var FIND_BIND_DIR = Leke.ctx +  '/auth/admin/material/findBindingMaterials.htm';
	BindingVM.prototype.loadBindingMmaterials = function(){
		var self = this;
		var materialNodeId = self.data.origMaterialNode().materialNodeId;
		var req = http.post(FIND_BIND_DIR,{origMaterialNodeId: materialNodeId});
		req.then(function(datas){
			self.bindingMmaterials(datas.mnjs || []);
		},function(msg){
			utils.Notice.alert(msg || '加载教材章节关联关系错误！');
		});
		
	};
	
	BindingVM.prototype.initDestTree = function(){
		var self = this;
		if(self._origTree) {
			self._origTree.destroy();
			self._origTree = null;
		}
		var mid = self.data.material.materialId;
		if(!mid){
			return;
		}
		self._origTree = _createTree('destMaterialTree',mid,function(treeNode){
			self.data.destMaterialNode({
				pressId: self.pressId(),
				materialId: self.materialId(),
				materialNodeId: treeNode.materialNodeId,
				materialNodeName: treeNode.materialNodeName,
				path: _path(treeNode)
			});
			
		});

	};
	
	BindingVM.prototype.initOrigTree = function(){
		var self = this;
		if(self._destTree) {
			self._destTree.destroy();
			self._destTree = null;
			self.data.destMaterialNode({});
		}
		var mid = self.materialId();
		if(!mid){
			return;
		}
		self._destTree = _createTree('origMaterialTree',mid,function(treeNode){
			self.data.origMaterialNode({
				pressId: self.pressId(),
				materialId: self.materialId(),
				materialNodeId: treeNode.materialNodeId,
				materialNodeName: treeNode.materialNodeName,
				path: _path(treeNode)
			});
		});
	};
	
	function _createTree(treeId,mid,onClick){
		return tree.createTree('#' + treeId, {
			async : {
				service : 'materialTreeDataService',
				autoParam : ['materialNodeId=parentId'],
				otherParam: {
					materialId: mid,
					showMode: 'section',
					visitMode: 'child'
				}
			},
			view : {
				selectedMulti : false
			},
			data : {
				key : {
					name : 'materialNodeName'
				},
				simpleData : {
					enable: true,
					rootPId : 0,
					idKey: 'materialNodeId',
					pIdKey: 'parentId'
				}
			},
			callback : {
				onClick : function(e, treeId, treeNode, flag) {
					onClick(treeNode);
				},
				beforeClick: function(treeId, treeNode, clickFlag){
					 return !treeNode.isParent;//当是父节点 返回false 不让选取
				}
			}
		});
	};
	
	function _path(treeNode){
		var path = treeNode.materialNodeName;
		var parentNodel = treeNode.getParentNode();
		while(parentNodel){
			path = parentNodel.materialNodeName + '-'+ path
			parentNodel = parentNodel.getParentNode();
		}
		return path;
	};
	
	
	BindingVM.prototype.showCopy = function(){
		var self = this;
		var data = self.data;
		var origMaterialNode = data.origMaterialNode();
		var destMaterialNode = data.destMaterialNode();
		if(!origMaterialNode){
			utils.Notice.alert('请选择需要绑定的教材章节!');
			return false;
		}
		if(!destMaterialNode){
			utils.Notice.alert('请选择主版本的教材章节!');
			return false;
		}
		self.isCopy(!self.isCopy());
	};
	
	var BIND_DIR = Leke.ctx +  '/auth/admin/material/doBindingMaterial.htm';
	BindingVM.prototype.doBinding = function(){
		var self = this;
		var data = self.data;
		var origMaterialNode = data.origMaterialNode();
		var destMaterialNode = data.destMaterialNode();
		if(!origMaterialNode || !origMaterialNode.materialNodeId){
			utils.Notice.alert('请选择主版本教材章节！');
			return false;
		}
		if(!destMaterialNode || !destMaterialNode.materialNodeId){
			utils.Notice.alert('请选择要绑定的教材章节！');
			return false;
		}
		if(origMaterialNode.materialNodeId == destMaterialNode.materialNodeId){
			utils.Notice.alert('不能绑定相同的节点！');
			return false;
		}
		var assoc = {
				origMaterialNodeId: origMaterialNode.materialNodeId,
				destMaterialNodeId: destMaterialNode.materialNodeId,
				destPath: destMaterialNode.path
		};
		var req = http.post(BIND_DIR,assoc);
		req.then(function(datas){
			utils.Notice.alert('绑定章节成功！');
			self.loadBindingMmaterials();
		},function(msg){
			utils.Notice.alert(msg || '绑定章节失败！');
		});
		
	};
	
	var DEL_DIR = Leke.ctx +  '/auth/admin/material/doDelBindingMaterial.htm';
	BindingVM.prototype.doDelBinding = function($data){
		var self = this;
		var materialNodeJoinId = $data.materialNodeJoinId;
		var req = http.post(DEL_DIR,{materialNodeJoinId: materialNodeJoinId});
		req.then(function(datas){
			utils.Notice.alert('删除成功！');
			self.loadBindingMmaterials();
		},function(msg){
			utils.Notice.alert(msg || '删除失败！');
		});
	};
	
	BindingVM.prototype.dispose = function(){
		var self = this;
		_.each(self.disposables || [],function(d){
			d.dispose();
		});
	};
	
	ko.components.register('mat-binding', {
	    template: require('./mat-binding.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new BindingVM(params, componentInfo.element);
	    	}
	    }
	});
	
});