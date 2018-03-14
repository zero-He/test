define(function(require, exports, module) {
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
	var tree = require('tree');
	var dialog = require('dialog');
	var Mustache = require('mustache');
	var Service = require('../../service/materialService');
	var KnowledgeService = require('../../service/knowledgeService');
	
	var Widget = require('common/base/widget');
	
	var DB_LEN_MAT_NAME = 256;
	
	var SectionView = Widget.extend({
		tpl: '.sectionView',
		_kcTpl: $('#knowledges_tpl').html(),
		linkKeys: ['parentName', 'materialNodeId', 'materialNodeName', 'materialNodeCode', 'ord'],
		linkKeysKlg: ['knowledgeId'],
		init: function() {
			var self = this;
			self.$('input').each(function() {
				self.attrBind($(this));
			});
			self.$kc = self.$('.knowledgeContainer');
			
			self.on('change:materialNodeId', function() {
				self.initKc();
			});
			
			self.initKc();
		},
		events: {
			'click .btnDelKlg': 'doDelKnowledge'
		},
		setSection: function(section) {
			var self = this;
			self.attr(_.pick(section || {}, self.linkKeys));
		},
		setSectionKlg: function(sectionklg) {
			var self = this;
			self.attr(_.pick(sectionklg || {}, self.linkKeysKlg));
		},
		initKc: function() {
			var self = this;
			var materialNodeId = self.attr('materialNodeId');
			if(materialNodeId) {
				Service.knowledges({
					parentId: materialNodeId
				}).done(function(datas) {
					var contents = Mustache.render(self._kcTpl, datas);
					self.$kc.html(contents);
				});
			} else {
				self.$kc.empty();
			}
		},
		doDelKnowledge: function(evt) {
			var $row = $(evt.target).closest('.knowledgeNode'), mid = $row.data('mid');
			Service.delKnowledge({
				materialNodeId: mid
			}).then(function() {
				$row.remove();
			}, function() {
				utils.Notice.alert('知识点节点删除失败！');
			});
		},
		getKids: function() {
			var self = this, kids = [];
			self.$kc.find('.knowledgeNode').each(function() {
				kids.push($(this).data('kid'));
			});
			return kids;
		}
	});
	
	var EditView = Widget.extend({
		options: {
			data: null
		},
		tpl: $('#edit_tpl').html(),
		initKeys: ['parentId', 'parentName', 'materialNodeId', 'materialNodeName', 
		           'materialNodeCode', 'ord'],
		init: function() {
			var self = this, data = self.options.data || {};
			self.attr(_.pick(data, self.initKeys));
			self.$('input').each(function() {
				self.attrBind($(this));
			});
		},
		doSave: function() {
			var params = this.attrs();
			var knm = params.materialNodeName;
			if(!knm) {
				utils.Notice.alert('请填写名称！');
				return false;
			}
			var dbbytes = _.getDBBytes(knm);
			if(dbbytes > DB_LEN_MAT_NAME) {
				utils.Notice.alert('输入的章节名称过长！');
				return false;
			}
			var save = params.materialNodeId ? Service.modifySection : Service.addSection;
			return save(params);
		}
	});
	
	var MTView = Widget.extend({
		tpl: '.base_view1',
		init: function() {
			var self = this;
			self.attr('section', null);
			self.attr('sectionklg', null);
			self._sec = new SectionView();
			self.initTree();
			self.initKTree();
			
			self.on('change:section', function(datas) {
				self._sec.setSection(datas);
			});
			
			self.on('change:sectionklg', function(datas) {
				self._sec.setSectionKlg(datas);
			});
		},
		events: {
			'click .btnAdd': 'doAdd',
			'click .btnModify': 'doEdit',
			'click .btnDel': 'doDel',
			'click .btnUp': 'doMoveUp',
			'click .btnDown': 'doMoveDown',
			'click .btnAddKlg': 'doAddKnowledge',
			'click .btnUpKlg': 'doMoveUpKlg',
			'click .btnDownKlg': 'doMoveDownKlg'
		},
		initTree: function() {
			var self = this;
			var $ul = $('#materialTree');
			var mid = $ul.data('materialId');
			self._tree = tree.createTree('#materialTree', {
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
						self.doSelect(treeNode);
					}
				}
			});
		},
		initKTree: function() {
			var self = this;
			var $ul = $('#knowledgeTree');
			self._ktree = tree.createTree('#knowledgeTree', {
				async : {
					url : window.ctx + '/auth/admin/knowledge/queryTreeNodes.htm',
					autoParam : ['knowledgeId=parentId'],
					otherParam: {
						schoolStageId: $ul.data('school-stage-id'),
						subjectId: $ul.data('subject-id')
					}
				},
				view : {
					selectedMulti : false
				},
				check: {
					enable: true,
					chkStyle: 'checkbox',
					chkboxType: { 'Y': '', 'N': '' }
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
						self.doSelectKlg(treeNode);
					}
				}
			});
		},
		doAdd: function() {
			var self = this;
			var pnode = self.attr('section');
			if(!pnode || !pnode.materialNodeId) {
				utils.Notice.alert('请先选择父节点！');
				return false;
			}
			self.openEdit({
				pnode: pnode,
				data: {
					parentId: pnode.materialNodeId,
					parentName: pnode.materialNodeName,
					ord: 0
				},
				title: '添加章节',
				errorMsg: '章节添加失败！'
			});
		},
		refreshNode: function(node) {
			var self = this;
			if(node){ node.isParent = true; }
			self._tree.reAsyncChildNodes(node, "refresh");
		},
		refreshKNode: function(node) {
			var self = this;
			if(node){ node.isParent = true; }
			self._ktree.reAsyncChildNodes(node, "refresh");
		},
		openEdit: function(options) {
			var self = this;
			var ops = $.extend({
				pnode: null,
				data: null,
				title: '修改章节',
				errorMsg: '章节修改失败！'
			}, options);
			var view = new EditView({
				data: ops.data
			});
			dialog.open({
				title: ops.title,
				tpl: view.$el,
				btns: [{
					className: 'btn-ok',
					text: '保存',
					cb: function() {
						var dlg = this;
						var req = view.doSave();
						if(req) {
							req.then(function() {
								self.refreshNode(ops.pnode);
								dlg.close();
							}, function(msg) {
								utils.Notice.alert(msg || ops.errorMsg);
							});
						}
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
		doEdit: function() {
			var self = this;
			var node = self.attr('section');
			if(!node || !node.materialNodeId) {
				utils.Notice.alert('请先选择要修改的节点！');
				return false;
			}
			if(node.nodeType == 1) {
				utils.Notice.alert('根节点不能修改！');
				return false;
			}
			self.openEdit({
				pnode: node.getParentNode(),
				data: node
			});
			self.doSelect(null);
		},
		doDel: function() {
			var self = this;
			var node = self.attr('section');
			if(!node || !node.materialNodeId) {
				utils.Notice.alert('请先选择要删除的节点！');
				return false;
			}
			if(node.nodeType == 1) {
				utils.Notice.alert('根节点不能删除！');
				return false;
			}
			var pnode = node.getParentNode();
			Service.delSection({
				materialNodeId: node.materialNodeId
			}).then(function() {
				self.refreshNode(pnode);
				self.doSelect(null);
			}, function(msg) {
				utils.Notice.alert(msg || '章节删除失败！');
			});
		},
		doMoveUp: function() {
			var self = this;
			var node = self.attr('section');
			if(!node || !node.materialNodeId) {
				utils.Notice.alert('请先选择要上移的节点！');
				return false;
			}
			if(node.nodeType == 1) {
				utils.Notice.alert('根节点不能上移！');
				return false;
			}
			var pnode = node.getParentNode();
			Service.moveUp({
				materialNodeId: node.materialNodeId
			}).then(function() {
				self.refreshNode(pnode);
				self.doSelect(null);
				//暂时不做变黄
				//self.doSelectById(node.outlineNodeId);
			}, function(msg) {
				utils.Notice.alert(msg || '节点上移失败！');
			});
		},
		doMoveDown: function() {
			var self = this;
			var node = self.attr('section');
			if(!node || !node.materialNodeId) {
				utils.Notice.alert('请先选择要下移的节点！');
				return false;
			}
			if(node.nodeType == 1) {
				utils.Notice.alert('根节点不能下移！');
				return false;
			}
			var pnode = node.getParentNode();
			Service.moveDown({
				materialNodeId: node.materialNodeId
			}).then(function() {
				self.refreshNode(pnode);
				self.doSelect(null);
			}, function(msg) {
				utils.Notice.alert(msg || '节点下移失败！');
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
				datas.parentId = pnode.materialNodeId;
				datas.parentName = pnode.materialNodeName;
				datas.ord = node.ord;
			}
			self.attr('section', datas);
		},
		doSelectKlg: function(node) {
			var self = this, pnode;
			var datas = {};
			if(node) {
				pnode = node.getParentNode();
				$.extend(datas, node);
			}
			/*if(pnode) {
				datas.parentId = pnode.materialNodeId;
				datas.parentName = pnode.materialNodeName;
				datas.ord = node.ord;
			}*/
			self.attr('sectionklg', datas);
		},
		doAddKnowledge: function() {
			var self = this;
			if(!self._ktree) {
				utils.Notice.alert('知识点树未初始化！');
				return false;
			}
			var knowledges = self._ktree.getCheckedNodes(true);
			if(!knowledges || !knowledges.length) {
				utils.Notice.alert('请选择知识点！');
				return false;
			}
			var materialNodeId = self._sec.attr('materialNodeId');
			if(!materialNodeId) {
				utils.Notice.alert('请选择章节！');
				return false;
			}
			var olds = self._sec.getKids();
			var kids = [];
			$.each(knowledges, function(i, k) {
				if($.inArray(k.knowledgeId, olds) === -1) {
					kids.push(k.knowledgeId);
				}
			});
			if(!kids.length) {
				utils.Notice.alert('所选知识点都已存在！');
				return false;
			}
			Service.addKnowledges({
				materialNodeId: materialNodeId,
				knowledgeIds: kids
			}).then(function() {
				utils.Notice.alert('关联知识点保存成功！');
				self._ktree.checkAllNodes(false);
				self._sec.initKc();
			}, function(msg) {
				utils.Notice.alert(msg || '关联知识点保存失败！');
			});
		},
		doMoveUpKlg: function() {
			var self = this;
			var node = self.attr('sectionklg');
			if(!node || !node.knowledgeId) {
				utils.Notice.alert('请先选择要上移的节点！');
				return false;
			}
			if(node.nodeType == 1) {
				utils.Notice.alert('根节点不能上移！');
				return false;
			}
			var pnode = node.getParentNode();
			KnowledgeService.moveUp({
				knowledgeId: node.knowledgeId
			}).then(function() {
				self.refreshKNode(pnode);
				self.doSelect(null);
				//暂时不做变黄
				//self.doSelectById(node.outlineNodeId);
			}, function(msg) {
				utils.Notice.alert(msg || '节点上移失败！');
			});
		},
		doMoveDownKlg: function() {
			var self = this;
			var node = self.attr('sectionklg');
			if(!node || !node.knowledgeId) {
				utils.Notice.alert('请先选择要下移的节点！');
				return false;
			}
			if(node.nodeType == 1) {
				utils.Notice.alert('根节点不能下移！');
				return false;
			}
			var pnode = node.getParentNode();
			KnowledgeService.moveDown({
				knowledgeId: node.knowledgeId
			}).then(function() {
				self.refreshKNode(pnode);
				self.doSelect(null);
			}, function(msg) {
				utils.Notice.alert(msg || '节点下移失败！');
			});
		}
	});
	
	new MTView();
});