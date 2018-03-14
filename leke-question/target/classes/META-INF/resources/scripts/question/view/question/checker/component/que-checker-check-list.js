define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
	var dialog = require('dialog');
	var WdatePicker = require('date');
	var DFN = require('homework/sheet.dfn');
	var Pages = require('common/base/pages');
	var koutils = require('common/knockout/koutils');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var QueService = require('question/service/questionService');
	var CheckerQuestionService = require('question/service/checkerQuestionService');
	require('core-business/widget/jquery.leke.knowledgeSelect');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	require('./que-knowledge-TPL');
	require('./que-tag-TPL');
	require('./que-tag-dialog');

	var REJECT_TPL = '<div><label for="" class="f-fl f-w60 f-tar">退回原因</label><textarea rows="3" cols="20" name="rejectContent" style="width:300px;height:60px;"></textarea></div>';
	var KNOWLEDGE_TPL = '<div class="q-propterty-k" data-bind="component: { name: \'que-knowledge-TPL\', params: $data }"></div>';
	var TAG_TPL = '<div class="q-propterty-t" data-bind="component: { name: \'que-tag-TPL\', params: $data }"></div>';
	var TAG_DIALOG = '<div data-bind="component: { name: \'que-tag-dialog\', params: $data }"></div>';
	
	function Query(params) {
		this.userId = ko.observable();
		this.schoolId = params.schoolId;
		this.subjectId = ko.observable();
		this.schoolStageId = ko.observable();
		this.statusType = ko.observable(params.statusType || 96);
		this.curPage = ko.observable(1);
	};

	function ListVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$cont = self.$el.find('.quesCutContent');
		self.query = new Query({
			schoolId: params.schoolId
		});
		var data = self.data= _.extend({}, params.data);
		self.questions = ko.observableArray();
		self.stageSubject = ko.observableArray();
		self.selectAllVisiable = ko.observable(true);

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
		
		self.disposables = [];
		
		self.disposables.push(ko.computed(function() {
			var curPage = self.query.curPage();
			self.doSearch();
		}));
		self.bindEvents();

	};

	ListVM.prototype.dispose = function() {
		var self = this, $el = self.$el;
		$el.off('.tqlist');
		$el.off('.pages');
		_.each(this.disposables, function(d) {
			d.dispose();
		});
	};

	/*
	 * 搜索
	 * */
	ListVM.prototype.doSearch = function() {
		var self = this;
		var query = koutils.peekJS(self.query);
		var request = self._lastAjax = $.ajax({
			type: 'post',
			url: window.ctx + '/auth/checker/question/questionListDetails.htm',
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
	};
	/**
	 * 审核
	 */
	ListVM.prototype.doChecked = function() {
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
		CheckerQuestionService.importCheck({
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
		
		$el.on('click.tqlist', '.jCheckDate', function() {
			WdatePicker({
				dateFmt: 'yyyy-MM-dd',
				readOnly: true,
				isShowToday: false
			});
		});

		$el.on('click.tqlist', '.jReject', function(evt) {
			var $a = $(evt.target);
			var qid = $a.data('questionId');
			var $ed = $(REJECT_TPL);
			var dlg = dialog.open({
				title: '编辑',
				tpl: $ed,
				size: 'mini',
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
						CheckerQuestionService.importReject({
							questionId: qid,
							rejectReason: content
						}).then(function() {
							utils.Notice.alert('题目退回操作成功！');
							dlg.close();
							self.doSearch();
						}, function(msg) {
							utils.Notice.alert(msg || '题目退回操作失败！');
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
		$el.on('click.tqlist', '.jSelectAll', function() {
			var $c = $(this), checked = $c.prop('checked');
			var qCheckBox = self.$cont.find('.q-checkbox :checkbox');
			checked ? qCheckBox.prop('checked', true) : qCheckBox.prop('checked', false);
		});
	};
	
	ko.components.register('que-checker-check-list', {
	    template: require('./que-checker-check-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});