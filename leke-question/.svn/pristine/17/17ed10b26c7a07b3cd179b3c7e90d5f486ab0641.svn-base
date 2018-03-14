define(function(require, exports, module) {
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
	var tree = require('tree');
	var dialog = require('dialog');
	var Mustache = require('mustache');
	var Service = require('../../service/knowledgeService');
	var Widget = require('common/base/widget');
	
	var View = Widget.extend({
		tpl: '.base_view1',
		init: function() {
			var self = this;
			var _vtpl = $('#view_tpl').val();
			self.$v = $('#knowledge_view');
			self.initTree();
			
			self.on('change:knowledge', function(datas) {
				var contents = Mustache.render(_vtpl, datas);
				self.$v.html(contents);
			});
		},
		events: {
			'click .btnAdd': 'doAdd',
			'click .btnModify': 'doEdit',
			'click .btnDel': 'doDel'
		},
		initTree: function() {
			var self = this;
			var $ul = $('#knowledgeTree');
			self._tree = tree.createTree('#knowledgeTree', {
				async : {
					url : window.ctx + '/auth/admin/knowledge/queryTreeNodes.htm',
					autoParam : ['knowledgeId=parentId'],
					otherParam: {
						schoolStageId: $ul.data('schoolStageId'),
						subjectId: $ul.data('subjectId')
					}
				},
				view : {
					selectedMulti : false
				},
				data : {
					key : {
						name : 'knowledgeName'
					},
					simpleData : {
						enable: true,
						rootPId : 0,
						idKey: 'knowledgeId',
						pIdKey: 'parentId'
					}
				},
				callback : {
					onClick : function(e, treeId, treeNode, flag) {
						self.doSelect(treeNode);
					}
				}
			});
		},
		doAdd: function(evt) {
			var self = this;
			var pnode = self.attr('knowledge');
			if(!pnode || !pnode.knowledgeId) {
				utils.Notice.alert('请先选择父节点！');
				return false;
			}
			self.openEdit({
				pnode: pnode,
				datas: {
					parentId: pnode.knowledgeId,
					parentName: pnode.knowledgeName,
					ord: 0
				},
				title: '添加知识点',
				errorMsg: '知识点添加失败！'
			});
		},
		refreshNode: function(node) {
			var self = this;
			if(node){ node.isParent = true; }
			self._tree.reAsyncChildNodes(node, "refresh");
		},
		openEdit: function(options) {
			var self = this;
			var ops = $.extend({
				pnode: null,
				datas: null,
				title: '修改知识点',
				errorMsg: '知识点修改失败！'
			}, options);
			var view = new EditView({
				model: ops.datas
			});
			dialog.open({
				title: ops.title,
				tpl: view.$el,
				btns: [{
					className: 'btn-ok',
					text: '保存',
					cb: function() {
						var dlg = this;
						view.doSave().then(function() {
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
		},
		doEdit: function(evt) {
			var self = this;
			var node = self.attr('knowledge');
			if(!node || !node.knowledgeId) {
				utils.Notice.alert('请先选择要修改的节点！');
				return false;
			}
			if(!node.parentId) {
				utils.Notice.alert('根节点不能修改！');
				return false;
			}
			var pnode = node.getParentNode();
			var datas = $.extend({}, node);
			if(pnode) {
				datas.parentId = pnode.knowledgeId;
				datas.parentName = pnode.knowledgeName;
			}
			self.doSelect(null);
			self.openEdit({
				pnode: pnode,
				datas: datas
			});
		},
		doDel: function(evt) {
			var self = this;
			var node = self.attr('knowledge');
			if(!node || !node.knowledgeId) {
				utils.Notice.alert('请先选择要删除的节点！');
				return false;
			}
			if(!node.parentId) {
				utils.Notice.alert('根节点不能删除！');
				return false;
			}
			var pnode = node.getParentNode();
			Service.del({
				knowledgeId: node.knowledgeId
			}).then(function() {
				self.refreshNode(pnode);
				self.doSelect(null);
			}, function(msg) {
				utils.Notice.alert(msg || '知识点删除失败！');
			});
		},
		doSelect: function(node) {
			var self = this, pnode;
			var datas = {};
			if(node) {
				pnode = node.getParentNode();
				$.extend(datas, node);
			}
			if(pnode) {
				datas.parentId = pnode.knowledgeId;
				datas.parentName = pnode.knowledgeName;
			}
			self.attr('knowledge', datas);
		}
	});
	
	function EditView(options) {
		this.ops = $.extend({
			type: 'add',
			model: null
		}, options);
		this.render();
	}
	
	var DB_LEN_K_NAME = 256;
	
	EditView.prototype = {
		tpl: $('#edit_tpl').val(),
		render: function(datas) {
			var self = this;
			var contents = Mustache.render(self.tpl, self.ops.model);
			this.$el = $(contents);
		},
		doSave: function() {
			var $el = this.$el;
			var knm = $('[name="knowledgeName"]', $el).val();
			if(!knm || knm === '') {
				utils.Notice.alert('请填写名称！');
				return false;
			}
			var dbbytes = _.getDBBytes(knm);
			if(dbbytes > DB_LEN_K_NAME) {
				utils.Notice.alert('输入的知识点名称过长！');
				return false;
			}
			var kid = $('[name="knowledgeId"]', $el).val();
			var params = $el.serialize();
			var save = kid ? Service.modify : Service.add;
			return save(params);
		}
	};
	
	new View();
});