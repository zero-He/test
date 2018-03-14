define(function(require, exports, module) {
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
	var tree = require('tree');
	var dialog = require('dialog');
	var Mustache = require('mustache');
	var Service = require('../../service/outlineService');
	
	var Widget = require('common/base/widget');
	
	var DB_LEN_MAT_NAME = 256;
	
	var SectionView = Widget.extend({
		tpl: '.sectionView',
		_kcTpl: $('#knowledges_tpl').html(),
		linkKeys: ['parentName', 'outlineNodeId', 'outlineNodeName', 'outlineNodeCode', 'ord'],
		init: function() {
			var self = this;
			self.$('input').each(function() {
				self.attrBind($(this));
			});
			self.$kc = self.$('.knowledgeContainer');
			
			self.on('change:outlineNodeId', function() {
			});
		},
		events: {
			'click .btnDelKlg': 'doDelKnowledge'
		},
		setSection: function(section) {
			var self = this;
			self.attr(_.pick(section || {}, self.linkKeys));
		},
		initKc: function() {
			var self = this;
			var outlineNodeId = self.attr('outlineNodeId');
			if(outlineNodeId) {
				Service.knowledges({
					parentId: outlineNodeId
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
				outlineNodeId: mid
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
		initKeys: ['parentId', 'parentName', 'outlineNodeId', 'outlineNodeName', 
		           'outlineNodeCode', 'ord'],
		init: function() {
			var self = this, data = self.options.data || {};
			self.attr(_.pick(data, self.initKeys));
			self.$('input').each(function() {
				self.attrBind($(this));
			});
		},
		doSave: function() {
			var params = this.attrs();
			var knm = params.outlineNodeName;
			if(!knm) {
				utils.Notice.alert('请填写名称！');
				return false;
			}
			var dbbytes = _.getDBBytes(knm);
			if(dbbytes > DB_LEN_MAT_NAME) {
				utils.Notice.alert('输入的章节名称过长,字数限制汉字85个！');
				return false;
			}
			var save = params.outlineNodeId ? Service.modifySection : Service.addSection;
			return save(params);
		}
	});
	
	var MTView = Widget.extend({
		tpl: '.base_view1',
		init: function() {
			var self = this;
			self.attr('section', null);
			self._sec = new SectionView();
			self.initTree();
			
			self.on('change:section', function(datas) {
				self._sec.setSection(datas);
			});
		},
		events: {
			'click .btnAdd': 'doAdd',
			'click .btnModify': 'doEdit',
			'click .btnDel': 'doDel',
			'click .btnUp': 'doMoveUp',
			'click .btnDown': 'doMoveDown',
			'click .btnAddKlg': 'doAddKnowledge'
		},
		initTree: function() {
			var self = this;
			var $ul = $('#outlineTree');
			var oid = $ul.data('outline-id');
			self._tree = tree.createTree('#outlineTree', {
				async : {
					url : window.ctx + '/auth/schoolResearcher/syllabus/outline/querySyllabusNodes.htm',
					autoParam : ['outlineNodeId=parentId'],
					otherParam: {
						outlineId: oid,
						/*showMode: 'section',
						visitMode: 'child'*/
					}
				},
				view : {
					selectedMulti : false
				},
				data : {
					key : {
						name : 'outlineNodeName'
					},
					simpleData : {
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
				}
			});
		},
		doAdd: function() {
			var self = this;
			var pnode = self.attr('section');
			var pord = 0;
			
			if(!pnode || !pnode.outlineNodeId) {
				utils.Notice.alert('请先选择父节点！');
				return false;
			}
			
			Service.queryChildNodes({
				parentId: pnode.outlineNodeId
			}).then(function(datas) {
				var pord = datas.outlineNodes.length;
				
				for(var i=0;i<datas.outlineNodes.length;i++) {
					if(datas.outlineNodes[i].ord > pord){
						pord = datas.outlineNodes[i].ord;
					}
				}
				
				self.openEdit({
					pnode: pnode,
					data: {
						parentId: pnode.outlineNodeId,
						parentName: pnode.outlineNodeName,
						ord: pord
					},
					title: '添加章节',
					errorMsg: '章节添加失败！'
				});
			}, function(msg) {
				utils.Notice.alert(msg || '章节添加失败！');
			});
			
		},
		refreshNode: function(node) {
			var self = this;
			if(node){ node.isParent = true; }
			self._tree.reAsyncChildNodes(node, "refresh");
			//location.reload();
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
								if (ops.title == '添加章节'){
									location.reload();
								}
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
			if(!node || !node.outlineNodeId) {
				utils.Notice.alert('请先选择要修改的节点！');
				return false;
			}
			if(!node.parentId) {
				utils.Notice.alert('根节点不能修改！');
				return false;
			}
			self.openEdit({
				pnode: node.getParentNode(),
				data: node
			});
			//修改后取消也选中
			self.doSelect(null);
		},
		doDel: function() {
			var self = this;
			var node = self.attr('section');
			if(!node || !node.outlineNodeId) {
				utils.Notice.alert('请先选择要删除的节点！');
				return false;
			}
			if(!node.parentId) {
				utils.Notice.alert('根节点不能删除！');
				return false;
			}
			
			dialog.confirm('确定删除该大纲节点吗？删除后不可恢复。').done(function(sure){
				if(!sure) {
					return;
				}
				
				var pnode = node.getParentNode();
				Service.delSection({
					outlineNodeId: node.outlineNodeId
				}).then(function() {
					self.refreshNode(pnode);
					self.doSelect(null);
				}, function(msg) {
					utils.Notice.alert(msg || '删除失败！');
				});
			});
		},
		doMoveUp: function() {
			var self = this;
			var node = self.attr('section');
			if(!node || !node.outlineNodeId) {
				utils.Notice.alert('请先选择要上移的节点！');
				return false;
			}
			if(!node.parentId) {
				utils.Notice.alert('根节点不能上移！');
				return false;
			}
			var pnode = node.getParentNode();
			Service.moveUp({
				outlineNodeId: node.outlineNodeId
			}).then(function() {
				self.refreshNode(pnode);
				self.doSelect(null);
				
			}, function(msg) {
				utils.Notice.alert(msg || '节点上移失败！');
			});
		},
		doMoveDown: function() {
			var self = this;
			var node = self.attr('section');
			if(!node || !node.outlineNodeId) {
				utils.Notice.alert('请先选择要下移的节点！');
				return false;
			}
			if(!node.parentId) {
				utils.Notice.alert('根节点不能下移！');
				return false;
			}
			var pnode = node.getParentNode();
			Service.moveDown({
				outlineNodeId: node.outlineNodeId
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
				datas.parentId = pnode.outlineNodeId;
				datas.parentName = pnode.outlineNodeName;
				datas.outlineNodeName = node.outlineNodeName;
			}
			self.attr('section', datas);
			
			/*var selected = _.pick(node, ['workbookNodeId', 'workbookNodeName', 'workbookId', 'ord', 'lft', 'rgt']);
			selected.path = _path(node, 'workbookNodeName');*/

			//self._tree.selectNode(node);
		},
		
		doSelectById: function(nid) {
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
		},
		
		doSelectRoot: function() {
			var self = this;
			self.doSelect(self._tree.getNodes()[0]);
		}
		
	});
	
	new MTView();
});