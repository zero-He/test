define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _un = require('underscore');
	var _ = require('common/base/helper');
	var tree = require('tree');
	var utils = require('utils');
	var dialog = require('dialog');
	var WdatePicker = require('date');
	var DFN = require('homework/sheet.dfn');
	var Pages = require('common/base/pages');
	var koutils = require('common/knockout/koutils');
	require('common/knockout/bindings/i18n');
	require('common/knockout/bindings/datepicker');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var QueService = require('question/service/questionService');
	var ResearcherQuestionService = require('question/service/researcherQuestionService');
	require('core-business/widget/jquery.leke.matNodeSelect');
	require('core-business/widget/jquery.leke.knowledgeSelect');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	require('question/view/question/checker/component/que-knowledge-TPL');
	require('question/view/question/checker/component/que-tag-TPL');
	require('question/view/question/checker/component/que-tag-dialog');

	var REJECT_TPL = '<form><label for="" class="f-fl f-w60 f-tar">退回原因</label><textarea rows="3" cols="20" name="rejectContent" style="width:400px;height:150px;"></textarea></form>';
	var KNOWLEDGE_TPL = '<div class="q-propterty-k" data-bind="component: { name: \'que-knowledge-TPL\', params: $data }"></div>';
	var TAG_TPL = '<div class="q-propterty-t" data-bind="component: { name: \'que-tag-TPL\', params: $data }"></div>';
	var TAG_DIALOG = '<div data-bind="component: { name: \'que-tag-dialog\', params: $data }"></div>';
	
	function Query(params) {
		this.userId = params.userId;
		this.pressId = ko.observable();
		this.subjectId = ko.observable();
		this.schoolStageId = ko.observable();
		this.materialNodeId = ko.observable();
		this.checkDate = ko.observable();
		this.questionTypeId = ko.observable();
		this.questionId = ko.observable();
		this.content = ko.observableArray();
		this.minInputDate = ko.observable();
		this.maxInputDate = ko.observable();
		this.statusType = ko.observable(params.statusType || 1);
		this.curPage = ko.observable(1);
	};

	function ListVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$cont = self.$el.find('.quesCutContent');
		self.query = new Query({
			userId : params.userId
		});
		$('#stageSubject').stgGrdSbjSelect({
			type: 'stg_sbj',
			data: {},
			onChange: function(selection) {
				if(selection) {
					self.query.schoolStageId(selection.schoolStageId);
					self.query.subjectId(selection.subjectId);
				} else {
					self.query.schoolStageId(null);
					self.query.subjectId(null);
				}
			}
		});
		var data = self.data= _.extend({}, params.data);
		self.presses = ko.observableArray();
		self.schoolStages = ko.observableArray();
		self.subjects = ko.observableArray();
		self.materials = ko.observableArray();
		self.questionTypes = ko.observableArray();
		self.questions = ko.observableArray();
		self.selectAllVisiable = ko.observable(true);
		self.sectionPath = ko.observable();

		self.disposables = [];
		self.disposables.push(ko.computed(function() {
			self.loadQuestionTypes();
		}));
		self.disposables.push(ko.computed(function() {
			var curPage = self.query.curPage();
			self.doSearch();
		}));
		self.bindEvents();
	};
	
	/**
	 * 选择教材章节
	 */
	ListVM.prototype.openSectionSelect = function(vm, evt) {
		var self = this;
		$(evt.target).matNodeSelect({
			type: 'section',
			multi: false,
			cache: true,
			data: {
				subjectId: self.query.subjectId(),
				schoolStageId: self.query.schoolStageId()
				},
			onSelect: function(section) {
				self.sectionPath(section.path);
				self.query.materialNodeId(section.materialNodeId);
			}
		});
	};
	
	
	/**
	 * 删除教材章节
	 */
	ListVM.prototype.doDelSection = function() {
		var self = this;
		self.sectionPath('');
		self.query.materialNodeId('');
	};
	/*
	 * 加载题型
	 * */
	ListVM.prototype.loadQuestionTypes = function() {
		var self = this;
		BasicInfoService.questionTypes(self.query.subjectId()).done(function(types) {
			self.questionTypes(types);
		}).fail(function() {
			self.questionTypes([]);
		});
	};
	
	ListVM.prototype.dispose = function() {
		var self = this, $el = self.$el;
		$el.off('.tqlist');
		$el.off('.pages');
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};
	
	ListVM.prototype.doSearch2 = function(){
		var self = this;
		self.query.curPage(1);
		self.doSearch();
	}
	
	/*
	 * 搜索
	 * */
	ListVM.prototype.doSearch = _un.debounce(function() {
		var self = this;
		var query = koutils.peekJS(self.query);
		if(!query.schoolStageId || !query.subjectId) {
			self.$cont.html('<p>请先选择学段科目，再进行查询！</p>');
			return false;
		}
		
		var request = self._lastAjax = $.ajax({
			type: 'post',
			url: window.ctx + '/auth/researcher/question/statistic/details.htm',
			data: _.cleanup(query),
			dataType: 'html'
		});
		request.done(function(resp, status, ajax) {
			if(ajax == self._lastAjax) {
				self.$cont.html(resp);
				DFN.init();
				if(self.$cont.children().hasClass('jQueEmpty')){
					self.selectAllVisiable(false);
				} else {
					self.selectAllVisiable(true);
				}
			}
		});
	},300);
	
	ListVM.prototype.doChecked = function(evt){
		var self = this;
		var qid = $(evt.target).data('questionId');
		dialog.confirm('确认审核通过吗？').done(function(sure){
			if(sure){
				ResearcherQuestionService.batchCheckPass({
					questionIds: [qid]
				}).then(function(datas) {
					if(datas && datas.msg) {
						utils.Notice.alert(datas.msg || '题目审核操作成功！');
					}
					self.doSearch();
				}, function(msg) {
					utils.Notice.alert(msg || '题目审核操作失败！');
				});
			}
		});
	};
	
	/**
	 * 审核
	 */
	ListVM.prototype.doCheckeds = function() {
		var self = this;
		var $qs = self.$cont.find(':checked');
		if(!$qs.length) {
			utils.Notice.alert('请选择需审核通过的题目！');
			return false;
		}
		var qids = [];
		$qs.each(function() {
			var $q = $(this).closest('.questionContent');
			var qid = $q.data('qid');
			qids.push(qid);
		});
		ResearcherQuestionService.batchCheckPass({
			questionIds: qids
		}).then(function(datas) {
			if(datas && datas.msg) {
				utils.Notice.alert(datas.msg || '题目审核操作成功！');
			}
			self.doSearch();
		}, function(msg) {
			utils.Notice.alert(msg || '题目审核操作失败！');
		});
	};
	
	/**
	 * 绑定事件
	 */
	ListVM.prototype.bindEvents = function() {
		var self = this, $el = self.$el;
		
		Pages.bind(self.$el, {
			ns: '.quePage',
			load: function(page) {
				self.query.curPage(page);
				self.doSearch();
			}
		});

		$el.on('click.tqlist', '.q-propterty-d-n-edit,.q-propterty-d-y-edit', function(evt) {
			var $s = $(evt.target);
			var qid = $s.closest('.q-container').attr('qid');
			var yEd = 'q-propterty-d-y-edit', nEd = 'q-propterty-d-n-edit';
			var hadVal = $s.hasClass(yEd);
			var diff = _.num($s.index() * 0.2 + 0.1, {fixed: 1});
			QueService.setDifficulty({
				questionId: qid,
				difficulty: diff
			}).then(function() {
				$s.prevAll().removeClass(nEd).addClass(yEd);
				$s.nextAll().removeClass(yEd).addClass(nEd);
				$s.removeClass(nEd).addClass(yEd);
			}, function(msg) {
				utils.Notice.alert(msg || '题目设置难度值失败！');
			});
		});
		
		$el.on('click.tqlist', '.q-propterty-k-edit', function(evt) {
			evt.preventDefault();
			var $k = $(evt.target);
			var $ke = $k.closest('.q-propterty-k-e');
			var $q = $k.closest('.questionContent');
			var que = $q.data();
			$k.knowledgeSelect({
				data: {
					schoolStageId: que.schoolStageId,
					subjectId: que.subjectId
				},
				multi: true,
				onSelect: function(knowledges) {
					QueService.attachKnowledges({
						questionId: que.qid,
						knowledges: knowledges
					}).then(function(datas) {
						$ke.next('.q-propterty-k').remove();
						var $kl = $(KNOWLEDGE_TPL);
						$ke.after($kl);
						ko.applyBindings(datas, $kl.get(0));
					}, function(msg) {
						utils.Notice.alert(msg || '设置关联知识点失败！');
					});
				}
			});
			return false;
		});
		
		$el.on('click.tqlist', '.q-propterty-t-edit', function(evt) {
			evt.preventDefault();
			var $t = $(evt.target);
			var $te = $t.closest('.q-propterty-t-e');
			var $q = $t.closest('.questionContent');
			var qid = $q.data('qid');
			BasicInfoService.officialTags().done(function(tags) {
				var $ed = $(TAG_DIALOG);
				$('body').append($ed);
				ko.applyBindings(tags, $ed.get(0));
				var dlg = dialog.open({
					title: '选择题库标签',
					tpl: $ed,
					size: 'sm',
					btns: [{
						className: 'btn-ok',
						text: '确定',
						cb: function() {
							var $ts = $ed.find(':checked');
							if(!$ts.length) {
								utils.Notice.alert('请先选择题库标签！');
								return false;
							}
							var tags = [];
							$ts.each(function() {
								tags.push({
									questionId: qid,
									officialTagId: $(this).val()
								});
							});
							QueService.attachTags({
								questionId: qid,
								tags: tags
							}).then(function(datas) {
								$te.next('.q-propterty-t').remove();
								var $t = $(TAG_TPL);
								$te.after($t);
								ko.applyBindings(datas, $t.get(0));
								dlg.close();
							}, function(msg) {
								utils.Notice.alert(msg || '设置题库标签失败！');
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
			});
			return false;
		});
		
		$el.on('click.tqlist', '.q-propterty-t-delete', function(evt) {
			evt.preventDefault();
			var $t = $(evt.target);
			var tid = $t.attr('tid');
			QueService.detachTag({
				quesOfficialTagId: tid
			}).then(function() {
				$t.closest('.q-propterty-t-item').remove();
			}, function(msg) {
				utils.Notice.alert(msg || '移除题库标签失败！');
			});
			return false;
		});
		
		$el.on('click.tqlist', '.q-propterty-k-delete', function(evt) {
			evt.preventDefault();
			var $t = $(evt.target);
			var kid = $t.attr('kid');
			QueService.detachKnowledge({
				quesKnowledgeId: kid
			}).then(function() {
				$t.closest('.q-propterty-k-item').remove();
			}, function(msg) {
				utils.Notice.alert(msg || '移除知识点失败！');
			});
			return false;
		});
		
		$el.on('click.tqlist', '.j-btn-edit-checked', function(evt) {
			var $a = $(evt.target);
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			var url = window.ctx + '/auth/researcher/question/statistic/rejectEdit.htm?action=checkedit&curScope=7&questionId=' + qid;
			if(self.query.statusType() === 17) {
				url = window.ctx + '/auth/researcher/question/statistic/editChecked.htm?action=checkedit&curScope=7&questionId=' + qid;
			}
			dialog.open({
				id: 'questionEditDialog',
				title: '习题编辑',
				url: url,
				size: 'full',
				onClose: function(type) {
					if(type === 'success') {
						utils.Notice.alert('习题编辑成功！');
						self.doSearch();
					}
				}
			});
		});
		$el.on('click.tqlist', '.j-btn-edit', function(evt) {
			var $a = $(evt.target);
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			dialog.open({
				id: 'questionEditDialog',
				title: '习题编辑',
				url: window.ctx + '/auth/researcher/question/check/edit.htm?action=checkedit&curScope=7&questionId=' + qid,
				size: 'full',
				onClose: function(type) {
					if(type === 'success') {
						utils.Notice.alert('习题编辑成功！');
						self.doSearch();
					}
				}
			});
		});
		$el.on('click.tqlist', '.j-btn-reject', function(evt) {
			var $a = $(evt.target);
			var tag = $a.data();
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			var $ed = $(REJECT_TPL);
			var dlg = dialog.open({
				title: '习题退回',
				tpl: $ed,
				size: 'sm',
				btns: [{
					className: 'btn-ok',
					text: '确定',
					cb: function() {
						var data = $ed.serialize();
						var content = $ed.find('[name=rejectContent]').val();
						if(!content || !content.length || content.length > 150) {
							utils.Notice.alert('请输入 150 个字以内的退回原因！');
							return false;
						}
						QueService.reject({
							questionId: qid,
							rejectReason: content
						}).then(function() {
							dlg.close();
							utils.Notice.alert('习题退回操作成功！');
							self.doSearch();
						}, function(msg) {
							utils.Notice.alert(msg || '习题退回操作失败！');
						});
					}
				}, {
					className: 'btn-cancel',
					text: '取消',
					cb: function() {
						dlg.close();
					}
				}]
			});
		});
		$el.on('click.tqlist', '.j-btn-del', function(evt) {
			var $a = $(evt.target);
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			dialog.confirm('确定删除该习题吗？').done(function(sure) {
				if(sure) {
					QueService.del({
						questionId: qid
					}).then(function() {
						utils.Notice.alert('习题删除成功！');
						self.doSearch();
					}, function(msg) {
						utils.Notice.alert(msg || '习题删除失败！');
					});
				}
			});
		});
		
		$el.on('click.tqlist', '.j-btn-disable', function(evt) {
			var $a = $(evt.target);
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			dialog.confirm('确定禁用该习题吗？').done(function(sure) {
				if(sure) {
					QueService.disable({
						questionId: qid
					}).then(function() {
						utils.Notice.alert('习题禁用成功！');
						self.doSearch();
					}, function(msg) {
						utils.Notice.alert(msg || '习题禁用失败！');
					});
				}
			});
		});
		
		//设置教材
		$el.on('click.tqlist', '.q-propterty-s-edit', function(evt) {
			evt.preventDefault();
			var $t = $(evt.target);
			var $te = $t.closest('.q-propterty-t-e');
			var $q = $t.closest('.questionContent');
			var que = $q.data();
			self.editQuestionSection(que,evt);
			
		});
		
		//删除教材
		$el.on('click.tqlist', '.q-propterty-s-delete', function(evt) {
			evt.preventDefault();
			self.delQuestionSection(evt);
			
		});

		$el.on('click.tqlist', '.jSelectAll', function() {
			var $c = $(this), checked = $c.prop('checked');
			var qCheckBox = self.$cont.find('.q-checkbox :checkbox');
			checked ? qCheckBox.prop('checked', true) : qCheckBox.prop('checked', false);
		});
		
		//单个审核通过
		$el.on('click.tqlist', '.j-btn-checked', function(evt) {
			self.doChecked(evt);
		});
	};
	
	//修改习题教材
	ListVM.prototype.editQuestionSection = function(que,evt){
		var self = this;
		var qid = que.qid;
		var schoolStageId = que.schoolStageId;
		var subjectId = que.subjectId;
		$(evt.target).matNodeSelect({
			type: 'section',
			multi: true,
			data: {
				subjectId: subjectId,
				schoolStageId: schoolStageId
				},
			onSelect: function(sections) {
				var params = {};
				params.questionId = qid;
				params.sections = sections;
				var req = QueService.attachSections(params);
				req.then(function(){
					utils.Notice.alert('教材设置成功');
					self.doSearch();
				},function(msg){
					utils.Notice.alert(msg || '教材设置失败!');
				});
			}
		});
	};
	
	//删除习题教材
	ListVM.prototype.delQuestionSection = function(evt){
		var self = this;
		var $t = $(evt.target);
		var sid = $t.attr('sid');
		var req = QueService.detachSection({quesSectionId: sid});
		req.then(function(){
			utils.Notice.alert('教材删除成功');
			self.doSearch();
		},function(msg){
			utils.Notice.alert(msg || '教材删除失败!');
		});
	};
	
	ko.components.register('que-statistic-list', {
	    template: require('./que-statistic-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});