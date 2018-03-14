define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var tree = require('tree');
	var utils = require('utils');
	var dialog = require('dialog');
	var http = require('common/base/http');
	var helper = require('common/base/helper');
	
	var ED_TPL = require('./form-wbnode.html');
	
	var WBTREE_URL = Leke.ctx + '/auth/schoolResearcher/syllabus/outline/querySyllabusNodes';
	var URL_WBN_DIR = Leke.domain.questionServerName + '/auth/schoolResearcher/syllabus/outline/';
	var URL_WBN_SAVE = URL_WBN_DIR + 'doSaveWbNode.htm';
	var URL_WBN_ADD = URL_WBN_DIR + 'addSection.htm';
	var URL_WBN_EDIT = URL_WBN_DIR + 'modifySection.htm';
	var URL_WBN_DEL = URL_WBN_DIR + 'deleteSection.htm';
	var URL_WBN_MOVEUP = URL_WBN_DIR + 'moveUp.htm';
	var URL_WBN_MOVEDOWN = URL_WBN_DIR + 'moveDown.htm';
	var URL_WBN_CHILD = URL_WBN_DIR + 'queryChildren.htm';
	
	var DB_LEN_MAT_NAME = 256;
	
	function ChooserVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$t = self.$el.find('.ztree');
		self._tid = _.uniqueId('oltree_');
		self.$t.attr('id', self._tid);
		
		self.outline = params.outline;
		self.selected = params.selected;
		self.mode = params.mode || 'view';
		self.outlineName = ko.pureComputed(function() {
			var ol = self.outline();
			return ol && ol.outlineName || '';
		});
		
		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self._initTree();
		}));
	}
	
	ChooserVM.prototype._initTree = function() {
		var self = this;
		if(self._tree) {
			self._tree.destroy();
			self._tree = null;
			self.selected(null);
		}
		var ol = self.outline();
		var olid = ol && ol.outlineId || null;
		if(olid) {
			self._tree = tree.createTree('#' + self._tid, {
				async: {
					/*url :  WBTREE_URL,*/
					url : window.ctx + '/auth/schoolResearcher/syllabus/outline/querySyllabusNodes.htm',
					autoParam: ['outlineNodeId=parentId'],
					otherParam: {
						outlineId: olid
					}
				},
				view: {
					selectedMulti: false
				},
				data: {
					key: {
						name: 'outlineNodeName'
					},
					simpleData: {
						enable: true,
						rootPId : 0,
						idKey: 'outlineNodeId',
						pIdKey: 'parentId'
					}
				},
				callback : {
					onClick : function(e, treeId, treeNode, flag) {
						self.doSelect(treeNode);
					},
					onAsyncSuccess: function(evt, treeId, node, cns) {
						var t = self._tree;
						var selected = self.selected();
						if(!selected) {
							self.doSelectRoot();
						} else {
							self.doSelectById(selected.outlineNodeId);
						}
					}
				}
			});
		}
	};
	
	ChooserVM.prototype.doSelectById = function(nid) {
		var self = this, t = self._tree;
		var sn = t.getSelectedNodes()[0];
		if(!sn || (sn.outlineNodeId != nid)) {
			var n = t.getNodesByParam('outlineNodeId', nid)[0];
			if(n) {
				self.doSelect(n);
			} else {
				self.doSelectRoot();
			}
		}
	};
	
	ChooserVM.prototype.doSelectRoot = function() {
		var self = this;
		self.doSelect(self._tree.getNodes()[0]);
	};
	
	ChooserVM.prototype.doSelect = function(node) {
		var self = this;
		if(!node) {
			self.selected(null);
			return;
		}
		self._tree.selectNode(node);
		var selected = _.pick(node, ['outlineNodeId', 'outlineNodeName', 'outlineId', 'ord', 'lft', 'rgt']);
		
		selected.path = _path(node, 'outlineNodeName');
		self.selected(selected);
	};
	
	ChooserVM.prototype.refreshNode = function(node) {
		var self = this;
		if(node) { node.isParent = true; }
		self._tree.reAsyncChildNodes(node, 'refresh');
	};
	
	ChooserVM.prototype.openEditor = function(options) {
		var self = this;
		var ops = $.extend({
			pnode: null,
			datas: null,
			title: '修改章节',
			errorMsg: '章节修改失败！'
		}, options);
		
		var ed = new Editor(ops.datas);
		dialog.open({
			title: ops.title,
			tpl: ed.$el,
			size: 'sm',
			btns: [{
				className: 'btn-ok',
				text: '保存',
				cb: function() {
					var dlg = this;
					var req = ed.doSave();
					req && req.then(function() {
						self.refreshNode(ops.pnode);
						dlg.close();
					}, function(msg) {
						utils.Notice.alert(msg || ops.errorMsg);
					});
				}
			}, {
				className: 'btn-cancel',
				text: '取消',
				cb: function() {
					this.close();
				}
			}]
		});
	};
	
	ChooserVM.prototype.addSubSection = function() {
		var self = this;
		var pnode = self._tree.getSelectedNodes()[0];
		if(!pnode) {
			utils.Notice.alert('请先选择父节点！');
			return;
		}
		
		http.jsonp(URL_WBN_CHILD, {
			parentId: pnode.outlineNodeId
		}).then(function(datas) {
			var pord = datas.outlineNodes.length;
			var dataOut = _.pick(pnode, ['parentId', 'parentName', 'ord']);
			
			for(var i=0;i<datas.outlineNodes.length;i++) {
				if(datas.outlineNodes[i].ord > pord){
					pord = datas.outlineNodes[i].ord;
				}
			}
			
			if(pnode) {
				dataOut.parentId = pnode.outlineNodeId;
				dataOut.parentName = pnode.outlineNodeName;
				dataOut.ord = pord;
			}

			self.openEditor({
				pnode: pnode,
				datas: dataOut,
				title: '添加子章节',
				errorMsg: '子章节添加失败！'
			});
		}, function(msg) {
			utils.Notice.alert(msg || '章节添加失败！');
		});
	};
	
	ChooserVM.prototype.editSection = function() {
		var self = this;
		var node = self._tree.getSelectedNodes()[0];
		if(!node || !node.outlineNodeId) {
			utils.Notice.alert('请先选择要修改的节点！');
			return false;
		}
		if(!node.parentId) {
			utils.Notice.alert('根节点不能修改！');
			return false;
		}
		var pnode = node.getParentNode();
		var datas = _.pick(node, ['outlineNodeId', 'outlineNodeName', 'outlineId', 'ord'])
		if(pnode) {
			datas.parentId = pnode.outlineNodeId;
			datas.parentName = pnode.outlineNodeName;
		}
		
		self.openEditor({
			pnode: pnode,
			datas: datas
		});
	};
	
	ChooserVM.prototype.deleteSection = function() {
		var self = this;
		var node = self._tree.getSelectedNodes()[0];
		if(!node || !node.outlineNodeId) {
			utils.Notice.alert('请先选择要删除的节点！');
			return false;
		}
		if(!node.parentId) {
			utils.Notice.alert('根节点不能修改！');
			return false;
		}
		
		dialog.confirm('确定删除该大纲节点吗？删除后不可恢复。').done(function(sure){
			if(!sure) {
				return;
			}
			
			var pnode = node.getParentNode();
			http.jsonp(URL_WBN_DEL, {
				outlineNodeId: node.outlineNodeId
			}).then(function() {
				self.refreshNode(pnode);
			}, function(msg) {
				utils.Notice.alert(msg || '章节删除失败！');
			});
		});
	};
	
	ChooserVM.prototype.moveUpSection = function() {
		var self = this;
		var node = self._tree.getSelectedNodes()[0];
		if(!node || !node.outlineNodeId) {
			utils.Notice.alert('请先选择要上移的节点！');
			return false;
		}
		if(!node.parentId) {
			utils.Notice.alert('根节点不能修改！');
			return false;
		}
		var pnode = node.getParentNode();
		http.jsonp(URL_WBN_MOVEUP, {
			outlineNodeId: node.outlineNodeId
		}).then(function() {
			self.refreshNode(pnode);
		}, function(msg) {
			utils.Notice.alert(msg || '章节上移失败！');
		});
	};
	
	ChooserVM.prototype.moveDownSection = function() {
		var self = this;
		var node = self._tree.getSelectedNodes()[0];
		if(!node || !node.outlineNodeId) {
			utils.Notice.alert('请先选择要下移的节点！');
			return false;
		}
		if(!node.parentId) {
			utils.Notice.alert('根节点不能修改！');
			return false;
		}
		var pnode = node.getParentNode();
		http.jsonp(URL_WBN_MOVEDOWN, {
			outlineNodeId: node.outlineNodeId
		}).then(function() {
			self.refreshNode(pnode);
		}, function(msg) {
			utils.Notice.alert(msg || '章节下移失败！');
		});
	};
	
	function _path(node, nameKey) {
		var ns = [];
		var n = node;
		while(n != null) {
			ns.unshift(n[nameKey]);
			n = n.getParentNode();
		}
		return ns.join('—');
	}
	
	ChooserVM.prototype.dispose = function() {
		var self = this;
		if(self._tree) {
			self._tree.destroy();
			self._tree = null;
		}
		self.disposables.forEach(function(d) {
			d.dispose();
		});
	};
	
	function Editor(vm) {
		var self = this;
		var $el = self.$el = $(ED_TPL);
		self.vm = $.extend({}, vm);
		
		self.init();
	}
	
	Editor.prototype.init = function() {
		var self = this, $el = self.$el, vm = self.vm;
		//$el.find('.j-pname').text(vm.parentName);
		self.$nm = $el.find('.j-pname');
		self.$nm.val(vm.parentName);
		self.$nm = $el.find('.j-name');
		self.$nm.val(vm.outlineNodeName);
		self.$ord = $el.find('.j-ord');
		self.$ord.val(vm.ord);
	};
	
	Editor.prototype.doSave = function() {
		var self = this, $el = self.$el, vm = self.vm;
		var nm = self.$nm.val();
		if(!nm) {
			utils.Notice.alert('请输入章节名称');
			return false;
		}
		//新加字数校验
		var dbbytes = helper.getDBBytes(nm);
		if(dbbytes > DB_LEN_MAT_NAME) {
			utils.Notice.alert('输入的章节名称过长,字数限制汉字85个！');
			return false;
		}
		
		vm.outlineNodeName = nm;
		vm.ord = self.$ord.val();
		//return http.jsonp(URL_WBN_SAVE, vm);
		if (vm.outlineNodeId){
			return http.jsonp(URL_WBN_EDIT, vm);
		}else{
			return http.jsonp(URL_WBN_ADD, vm);
		}
	};
	
	ko.components.register('wbpap-wbnode-chooser', {
	    template: require('./wbpap-wbnode-chooser.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ChooserVM(params, componentInfo.element);
	    	}
	    }
	});
});