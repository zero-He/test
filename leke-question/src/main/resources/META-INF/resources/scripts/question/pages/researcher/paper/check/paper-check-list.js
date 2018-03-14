define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var http = require('common/base/http');
	var utils = require('utils');
	var dialog = require('dialog');
	var DFN = require('homework/sheet.dfn');
	require('common/knockout/bindings/i18n');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var QueService = require('question/service/questionService');
	var ResearcherQuestionService = require('question/service/researcherQuestionService');
	require('core-business/widget/jquery.leke.matNodeSelect');
	require('core-business/widget/jquery.leke.knowledgeSelect');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	require('question/view/question/checker/component/que-knowledge-TPL');
	require('question/view/question/checker/component/que-tag-TPL');
	require('question/view/question/checker/component/que-tag-dialog');
	
	var KNOWLEDGE_TPL = '<div class="q-propterty-k" data-bind="component: { name: \'que-knowledge-TPL\', params: $data }"></div>';
	var TAG_TPL = '<div class="q-propterty-t" data-bind="component: { name: \'que-tag-TPL\', params: $data }"></div>';
	var TAG_DIALOG = '<div data-bind="component: { name: \'que-tag-dialog\', params: $data }"></div>';
	
	function Query(){
		var self = this;
		self.questionStatus = ko.observable(1);
	};
	

	function ListVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$cont = self.$el.find('.quesCutContent');
		var query = self.query = new Query();
		self.paper = params.paper;
		self.load();
		self.bindEvents();
		self.disposables = [];
		self.disposables.push(ko.computed(function(){
			var questionStatus = query.questionStatus();
			self.load();
		}));
	};
	
	var DET_DIR = Leke.ctx + '/auth/researcher/paper/check/details.htm';
	ListVM.prototype.load = function(){
		var self = this;
		var request = self._lastAjax = $.ajax({
			type: 'post',
			url: DET_DIR,
			data: {paperId: self.paper.paperId,questionStatus: self.query.questionStatus()},
			dataType: 'html'
		});
		request.done(function(resp, status, ajax) {
			if(ajax == self._lastAjax) {
				self.$cont.html(resp);
				$('.jSelectAll').prop('checked', false);
				DFN.init();
			}
		});
	};
	
	ListVM.prototype.scrollToTop = function() {
		var self = this;
		self.$bd = self.$bd || $('html,body');
		self.$bd.scrollTop(0);
	};
	
	/**
	 * 选择教材章节
	 */
	ListVM.prototype.openSectionSelect = function(vm, evt) {
		var self = this;
		$(evt.target).matNodeSelect({
			type: 'section',
			multi: false,
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
	
	var PAP_DIR = Leke.domain.paperServerName + '/auth/common/paper/jsonp/doCheckedPass.htm';
	ListVM.prototype.doPaperChecked = function(){
		var self = this;
		var paperId = self.paper.paperId;
		var req = http.jsonp(PAP_DIR,{paperId: paperId});
		req.then(function(datas){
			utils.Notice.alert('试卷审核通过！');
			window.location.reload(); 
		},function(msg){
			utils.Notice.alert(msg || '试卷审核错误！');
		});
	};
	
	ListVM.prototype.doChecked = function(evt){
		var self = this;
		var $a = $(evt.target);
		var $q = $a.closest('.questionContent');
		var que = $q.data();
		var qid = que.qid;
		dialog.confirm('确认审核通过吗？').done(function(sure){
			if(sure){
				ResearcherQuestionService.batchCheckPass({
					questionIds: [qid]
				}).then(function(datas) {
					if(datas && datas.msg) {
						utils.Notice.alert(datas.msg || '题目审核操作成功！');
					}
					self.load();
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
		var $qs = self.$cont.find('.q-checkbox :checked');
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
				self.$cont.find('.q-checkbox :checkbox').prop('checked',false);
				$('.jSelectAll').prop('checked',false);
				self.load();
			}
		}, function(msg) {
			utils.Notice.alert(msg || '题目审核操作失败！');
		});
	};
	
	/**
	 * 绑定事件
	 */
	ListVM.prototype.bindEvents = function() {
		var self = this, $el = self.$el;

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
			var $q = $a.closest('.questionContent');
			var que = $q.data();
			var qid = que.qid;
			var url = window.ctx + '/auth/researcher/question/statistic/editChecked.htm?action=checkedit&curScope=7&questionId=' + qid;
			dialog.open({
				id: 'questionEditDialog',
				title: '习题编辑',
				url: url,
				size: 'full',
				onClose: function(type) {
					if(type === 'success') {
						utils.Notice.alert('习题编辑成功！');
						self.load();
					}
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
		//
		$el.on('click.tqlist', '.j-btn-checkeds', function(evt) {
			self.doCheckeds();
		});
		//试卷审核
		$el.on('click.tqlist', '.j-btn-paper-checked', function(evt) {
			self.doPaperChecked();
		});
		//
		$el.on('click.tqlist', '.j-btn-paper-view', function(evt) {
			window.open(Leke.domain.paperServerName + '/auth/common/paper/view.htm?paperId=' + self.paper.paperId);
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
			multi: false,
			data: {
				subjectId: subjectId,
				schoolStageId: schoolStageId
				},
			onSelect: function(section) {
				var params = {};
				params.questionId = qid;
				params.sections = [];
				params.sections.push(section);
				var req = QueService.attachSections(params);
				req.then(function(){
					utils.Notice.alert('教材设置成功');
					self.load();
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
			self.load();
		},function(msg){
			utils.Notice.alert(msg || '教材删除失败!');
		});
	};
	
	ko.components.register('paper-check-list', {
	    template: require('./paper-check-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});