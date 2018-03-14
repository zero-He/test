define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var http = require('common/base/http');
	var dialog = require('dialog');
	var utils = require('utils');
	var DM = Leke.domain;
	var BK_DM = DM.beikeServerName;
	var BasicInfoService = require('core-business/service/basicInfoService');
	var RepoService = require('repository/service/RepoService');
	var shareService = require('core-business/service/shareService');
	var DFN = require('homework/sheet.dfn');
	var Pages = require('common/base/pages');
	var KO_QUE_CART = require('question/quecart');
	var questionService = require('question/pages/common/questionService');
	var RepoQS = require('repository/service/RepoQueryString');
	
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	require('question/component/que-cart');
	
	var FQ_KS = ['schoolStageId', 'subjectId', 'questionTypeId'];
	
	function Query(options) {
		var ops = _.extend({}, options);
		var self = this;
		self.content = ko.observable(ops.content || '');
		_.each(FQ_KS, function(k) {
			self[k] = ko.observable(ops[k]);
		});
		
		self.resType = ops.resType;//资源类型
		self.shareScope = ops.shareScope;//个人资源或收藏
		self.schoolStageId = ko.observable( );//课件名称
		self.subjectId = ko.observable();
		self.pressId = ko.observable();
		self.materialNodeId = ko.observable();
		self.sections = ko.observableArray();
		self.userResGroupId = ops.userResGroupId;
		self.knowledges = ko.observableArray();
		
		self.curPage = ko.observable(ops.curPage || 1);
	}
	
	Query.prototype.toJS = function() {
		var self = this;
		var result = {};
		result.content = self.content.peek();
		_.each(FQ_KS, function(k) {
			var val = self[k].peek();
			if(val != null && val != '') {
				result[k] = val;
			}
		});
		result.userResGroupId = self.userResGroupId.peek();
		result.shareScope = self.shareScope.peek();
		if (self.schoolStageId){
			result.schoolStageId = self.schoolStageId.peek();
		}
		
		if (self.subjectId){
			result.subjectId = self.subjectId.peek();
		}
		
		if (self.materialNodeId){
			result.materialNodeId = self.materialNodeId.peek();
		}
		
		var knowledges = self.knowledges.peek();
		if (knowledges[0]){
			var knowledgeIds = [];
			for (var i = 0, len = knowledges.length; i < len; i++) { 
				knowledgeIds.push(knowledges[i].knowledgeId);
			}
			
			result.knowledgeIds = knowledgeIds.join(',');
		}
		
		result.curPage = self.curPage.peek();
		return result;
	};
	
	var URL_ADD = Leke.ctx + '/auth/common/question/add/index.htm?';
	
	function SearchVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$qs = $('#j-questions',self.$el);
		
		self.query = new Query(params);
		self.curPage = ko.observable(1);
		self.types = ko.observableArray();
		
		var query = self.query;
		
		self.$el.find('[name=subject]').stgGrdSbjSelect({
			data: {
				schoolStageId: null,
				subjectId: null
			},
			model: 'nocache',
			onChange: function(item) {
				self.query.schoolStageId(item.schoolStageId);
				self.query.subjectId(item.subjectId);
			}
		});
		
		self.bindEvents();
		
		self.urlAdd = ko.pureComputed(function() {
			return URL_ADD + RepoQS.stringify({
				action: 'create',
				curScope: self.query.shareScope(),
				curResGrpId: self.query.userResGroupId(),
				spm: '101026'
			});
		});
		
		self.disposables = [];
		
		self.disposables.push(ko.computed(function() {
			var curPage = self.query.curPage();
		}));
		
		self.disposables.push(ko.computed(function() {
			var shareScope = self.query.shareScope();
			self.query.curPage(1);
		}));
		
		self.disposables.push(ko.computed(function() {
			var userResGroupId = self.query.userResGroupId();
			self.query.curPage(1);
		}));
		
		self.disposables.push(ko.computed(function() {
			self.loadTypes();
		}));
		
		self.disposables.push(ko.computed(function() {
			var curPage = self.query.curPage();
			var userResGroupId = self.query.userResGroupId();
			var shareScope = self.query.shareScope();
			self.load();
		}));
		
		//self._initStgSbjSelect();
		self.sectionPath = ko.observable('请选择教材章节');
		self.tagContent = ko.observable();
		
		//没必要把microcourse选中的传进来，编辑才需要
		self.disposables.push(ko.computed(function(){
			var content = '';
			_.each(self.query.sections() ||{},function(n){
				content += n.path;
			});
			self.sectionPath(content);
		}));
	}

	SearchVM.prototype.openSectionSelect = function(vm, evt) {
		var self = this, query = self.query;
		evt = $.event.fix(evt);
		require.async(['core-business/widget/jquery.leke.matNodeSelect'], function() {
			$(evt.target).matNodeSelect({
				type: 'section',
				multi: false,
				cache: true,
				data: {
					schoolStageId: query.schoolStageId(),
					subjectId: query.subjectId()
				},
				onSelect: function(section) {
					var sec =  {
						materialNodeId: section.materialNodeId,
						materialId: section.materialId,
						path: section.path,
						materialNodePath: section.path,
						queMaterialId: section.materialId
					};
					query.sections([sec]);
					query.materialNodeId(section.materialNodeId);
				}
			});
		});
	};

	SearchVM.prototype.delSection = function(){
		var self = this;
		self.query.sections([]);
		self.query.materialNodeId({});
	};
	
	SearchVM.prototype.bindEvents = function() {
		var self = this, $el = self.$el;

		Pages.bind(self.$el, {
			ns: '.quePage',
			load: function(page) {
				self.query.curPage(page);
				self.load();
			}
		});
		
		self.$el.on('click.qlist', '.selectedAll', function(evt) {
			self.selectedAll();
		});
		
		self.$el.on('click.qlist', '.batchMove', function(evt) {
			self.batchMove();
		});
		
		self.$el.on('click.qlist', '.batchRemove', function(evt) {
			self.batchRemove();
		});
		
		self.$el.on('click.qlist', '.j-disable', function(evt) {
			self.doDisable(evt);
		});
		
		self.$el.on('click.qlist', '.j-unfavorite', function(evt) {
			self.doUnfavorite(evt);
		});
		
		self.$el.on('click.qlist', '.j-edit', function(evt) {
			self.openEditDialog(evt);
		});
		
		self.$el.on('click.qlist', '.j-praise', function(evt) {
			self.doPraise(evt);
		});
		
		self.$el.on('click.qlist', '.j-add-quecart', function(evt) {
			self.doAddQueCart(evt);
		});
		
		self.$el.on('click.qlist', '.j-add-batch-quecart', function(evt) {
			self.doBatchAddQueCart();
		});
		
		self.$el.on('click.qlist', '.batchUnfavorite', function(evt) {
			self.doBatchUnfavorite();
		});
		
		self.$el.on('click.qlist', '.j-move', function(evt) {
			self.move(evt);
		});
		
		self.$el.on('click.qlist', '.j-moveOut', function(evt) {
			self.remove(evt);
		});
		
		self.$el.on('click.qlist', '.j-share', function(evt) {
			self.share(evt);
		});
	};
	
	SearchVM.prototype.doPraise = function(evt) {
		var self = this;
		var $q = $(evt.target).closest('.item');
		var qid = $q.data('questionId');
		RepoService.doPraiseQuestion(qid).then(function() {
			utils.Notice.alert($.i18n.prop('que.question.alert.praiseSucceed'));
			var $c = $q.find('.j-praise-count');
			var old = parseInt($c.text() || 0);
			$c.text(old + 1);
		}, function(msg) {
			utils.Notice.alert(msg || $.i18n.prop('que.question.alert.praiseFailed'));
		});
	};
	
	var URL_EDIT = Leke.ctx + '/auth/common/question/questionEdit.htm?';
	
	SearchVM.prototype.openEditDialog = function(evt) {
		var self = this;
		var qid = $(evt.target).closest('.item').data('questionId');
		var url = URL_EDIT + RepoQS.stringify({
			curScope: self.query.shareScope(),
			curResGrpId: self.query.userResGroupId(),
			crossDomain: true,
			questionId: qid
		});
		
		dialog.open({
			id: 'questionEditDialog',
			title: $.i18n.prop('que.question.dialog.title.edit'),
			url: url,
			size: 'full',
			onClose: function(type) {
				if(type === 'success') {
					utils.Notice.alert('保存至我的资源');
					self.load();
				}
			}
		});
	};
	
	SearchVM.prototype.doDisable = function(evt) {
		var self = this;
		dialog.confirm($.i18n.prop('que.question.dialog.delete')).done(function(sure){
			if(sure) {
				var qid = $(evt.target).closest('.item').data('questionId');
				http.post(Leke.ctx + '/auth/common/question/doRemove.htm', {
					questionId: qid
				}).then(function() {
					utils.Notice.alert($.i18n.prop('que.question.alert.deleteSucceed'));
					self.load();
				}, function(msg) {
					utils.Notice.alert(msg || $.i18n.prop('que.question.alert.deleteFailed'));
				});
			}
		});
	};
	
	SearchVM.prototype.doUnfavorite = function(evt) {
		var self = this;
		var $q = $(evt.target).closest('.item');
		var qid = $q.data('questionId');
		dialog.confirm("你确定要取消收藏吗？").done(function(sure) {
			if(sure){
				RepoService.doUnfavoriteQuestion(qid).then(function() {
					utils.Notice.alert($.i18n.prop('que.question.alert.cancelCollectSucceed'));
					self.load();
				}, function(msg) {
					utils.Notice.alert(msg || $.i18n.prop('que.question.alert.cancelCollectFailed'));
				});
			}
		});
	};
	
	SearchVM.prototype.doBatchUnfavorite = function(evt) {
		var self = this;
		var questionIds = self._selectedResIds();
		if(!questionIds.length){
			utils.Notice.alert('请选择习题!');
			return false;
		}
		dialog.confirm("你确定要取消收藏吗？").done(function(sure) {
			if(sure){
				RepoService.doBatchUnfavoriteQuestion(questionIds).then(function() {
					utils.Notice.alert($.i18n.prop('que.question.alert.cancelCollectSucceed'));
					self.load();
				}, function(msg) {
					utils.Notice.alert(msg || $.i18n.prop('que.question.alert.cancelCollectFailed'));
				});
			}
		});
	};
	
	
	SearchVM.prototype.doAddQueCart = function(evt) {
		var self = this;
		//var qid = $(evt.target).closest('.item').data('questionId');
		//KO_QUE_CART.add(qid);

		var que = {};
		que.qid =  $(evt.target).closest('.item').data('questionId');
		que.qtypeid = $(evt.target).closest('.item').data('questionTypeId');
		var qtypename = _getTypeName(self.types(), que.qtypeid);
		if (qtypename != -1){
			que.qtypename = qtypename;
		}
		KO_QUE_CART.addType(que);
		
		require.async(['common/ui/ui-fly/ui-fly'], function(f) {
			var ops = {el:'.c-exercisebasket .basket .con'};
			f.doFly(evt, ops);
		});
	};

	function _getTypeName(array, item) {
		if(!array || !array.length) {
			return -1;
		}
		for (var i = 0, len = array.length; i < len; i++) { 
			if (array[i].questionTypeId == item) {
				return array[i].questionTypeName;
			}
		}
		return -1;
	}
	
	SearchVM.prototype.doBatchAddQueCart = function() {
		var self = this;
		//var qids = self._selectedResIds();
		var flag = self._selectedResques();
		if(!flag){
			utils.Notice.alert('请选择习题!');
			return false;
		}

		utils.Notice.alert('加入成功！');
	};
	
	SearchVM.prototype.loadTypes = function() {
		var self = this;
		var sid = self.query.subjectId();
		var req = sid ? BasicInfoService.questionTypes(sid) : BasicInfoService.questionTypes();
		req.then(function(types) {
			self.types(types || []);
		}, function() {
			self.types([]);
		});
	};
	
	SearchVM.prototype.doSearch = function() {
		var self = this;
		self.query.curPage(1);
		self.load();
	};
	
	/*
	 * 实际查询操作
	 */
	SearchVM.prototype.load = _.throttle(function() {
		var self = this;
		var query = self.query.toJS();
		var req = self._lastAjax = $.ajax({
			type: 'post',
			url: Leke.ctx + '/auth/common/question/personal/details.htm',
			data: query,
			dataType: 'html'
		});
		req.then(function(resp, status, ajax) {
			if(ajax == self._lastAjax) {
				//兼容IE8
				self.$qs.html('<div>' + resp + '</div>');
				self.scrollToTop();
				DFN.init();
				
			}
		}, function() {
			self.$qs.html('<div class="m-tiptext m-tiptext-err"><i class="iconfont icon">&#xf0155;</i> <span class="msg">习题列表加载失败！</span></div>');
			self.scrollToTop();
		});
	}, 300);
	
	SearchVM.prototype.scrollToTop = function() {
		var self = this;
		self.$bd = self.$bd || $('html,body');
		self.$bd.scrollTop(0);
	};
	
	//移动
	SearchVM.prototype.move = function(evt){
		var self = this;
		var questionId = $(evt.target).closest('.item').data('questionId');
		self.doMove([questionId]);
	};
	
	SearchVM.prototype.batchMove = function(){
		var self = this;
		var questionIds = self._selectedResIds();
		if(!questionIds.length){
			utils.Notice.alert('请选择习题!');
			return false;
		}
		self.doMove(questionIds);
	};
	
	SearchVM.prototype.doMove = function(questionIds){
		var self = this;
		var resType = self.query.resType;
		var shareScope = self.query.shareScope.peek();
		var userResGroupId = self.query.userResGroupId.peek();
		document.domain = 'leke.cn';
		if(!questionIds.length){
			utils.Notice.alert('请选择试卷');
			return;
		}
		var ops = {
			id : 'userResGroup',
			title : '选择资源分组',
			url: BK_DM + '/auth/common/resgroup/choose.htm?crossDomain=true&resType=' + resType + '&shareScope=' + shareScope,
			size : 'lg',
			onClose : function(newUserResGroupId){
				if(!newUserResGroupId){
					return;
				}
				var req;
				if(userResGroupId){
					var model = {
						oldUserResGroupId: userResGroupId,
						newUserResGroupId: newUserResGroupId
					};
					req = questionService.moveBatchQuestionUserGroup(JSON.stringify(model),questionIds);
				}else{
					req = questionService.addBatchQuestionUserGroup(newUserResGroupId,questionIds);
				}
				req.then(function(data){
					utils.Notice.alert('移动成功');
					self.load();
				},function(msg){
					utils.Notice.alert(msg || '移动失败');
					
				});
			}
		};
		dialog.open(ops);
	};
	
	SearchVM.prototype.selectedAll = function(){
		var self = this;
		var val = $('#selectedAll').prop('checked');
		self.$el.find('.c-resource-list').find('input[name="resId"]').prop('checked', val);
	};
	
	SearchVM.prototype._selectedResIds = function() {
		var self = this;
		var resIds = [];
		self.$el.find('.c-resource-list').find('input[name="resId"]:checked').each(function() {
			resIds.push($(this).val());
		});
		return resIds;
	};
	
	SearchVM.prototype._selectedResques = function() {
		var self = this;
		var flag = false;
		//var ques = [];
		self.$el.find('.c-resource-list').find('input[name="resId"]:checked').each(function() {
			var que = {};
			que.qid =  $(this).closest('.item').data('questionId');
			//que.qtypeid = $(this).closest('.item').find('div[hassub="false"]').data('questionTypeId');
			que.qtypeid = $(this).closest('.item').data('questionTypeId');
			
			var qtypename = _getTypeName(self.types(), que.qtypeid)
			if (qtypename != -1){
				que.qtypename = qtypename;
			}
			
			//ques.push(que);
			
			KO_QUE_CART.addType(que);
			flag = true;
		});
		
		return flag;
		//return ques;
	};
	
	//移出资源分组
	SearchVM.prototype.remove = function(evt){
		var self = this;
		var questionId =  $(evt.target).closest('.item').data('questionId');
		var userResGroupId = self.query.userResGroupId();
		if(userResGroupId){
			self.delQuestionUserResGroup(userResGroupId,[questionId]);
		}
	};
	
	SearchVM.prototype.batchRemove = function(){
		var self = this;
		var questionIds = self._selectedResIds();
		var userResGroupId = self.query.userResGroupId();
		if(!questionIds.length){
			utils.Notice.alert('请选择习题!');
			return false;
		}
		if(userResGroupId){
			self.delQuestionUserResGroup(userResGroupId,questionIds);
		}
	};
	
	SearchVM.prototype.delQuestionUserResGroup = function(userResGroupId, questionIds){
		var self = this;
		if(!questionIds.length){
			utils.Notice.alert('请选择要移出的习题！');
			return;
		}
		dialog.confirm('确认要移出分组吗？').done(function(sure){
			if(sure){
				var req = questionService.delBatchQuestionUserResGroup(userResGroupId,questionIds);
				req.then(function(data){
					utils.Notice.alert('习题移出操作成功!');
					self.load();
				},function(msg){
					utils.Notice.alert(msg || '习题移出操作失败!');
				});
			}
		});
	};
	
	SearchVM.prototype.share = function(evt){
		var self = this;
		var questionId = $(evt.target).closest('.item').data('questionId');
		var shareInfo = {questionId: questionId};
		var req =  http.get(Leke.ctx + '/auth/common/question/getQuestionShareInfo.htm',{questionId: questionId});
		req.then(function(datas){
			var info = datas.info;
			shareInfo.sharePlatform = info.sharePlatform || false;
			shareInfo.shareSchool = info.shareSchool || false;
			shareInfo.outlineNode = info.outlineNode || {};
			shareInfo.schoolStageId = info.schoolStageId || '';
			shareInfo.subjectId = info.subjectId || '';
			var sections = info.sections || [];
			shareInfo.sections = sections;
			shareService.share(shareInfo,function(vm){
				if(!vm.shareSchool && !vm.sharePlatform){
					utils.Notice.alert('请选择分享范围！');
					return false;
				}
				if (!vm.sections.length){
					utils.Notice.alert('请绑定教材');
					return false;
				}
				var postData = _formatSections(vm);
				postData.questionId = questionId;
				var req2 = http.post(Leke.ctx + '/auth/common/question/doShare.htm',{dataJson: JSON.stringify(postData)});
				req2.then(function(datas) {
					var msg = '分享成功', a = datas.tips;
					if(a && a.leke) {
						msg += '，乐豆+' + a.leke;
					}
					if(a && a.exp) {
						msg += '，经验+' + a.exp;
					}
					utils.Notice.alert(msg);
				}, function(msg) {
					utils.Notice.alert(msg || '分享失败');
				});
			},{multi: true});
		}, function(msg) {
			utils.Notice.alert(msg || '习题信息获取失败');
		});
	};
	
	function _formatSections(result){
		var data = {};
		data.shareSchool = result.shareSchool;
		data.sharePlatform = result.sharePlatform;
		var outSection = result.outSection;
		data.sections = result.sections;
		if(outSection.path){
			data.outlineNode = outSection;
		}
		return data;
	};
	
	
	SearchVM.prototype.dispose = function() {
		var self = this;
		_.each(self.disposables, function(d) {
			d.dispose();
		});
		self.$el.off('.quePage');
		self.$el.off('.qlist');
	};
	
	SearchVM.prototype.openKnowledgeSelect = function(vm, evt) {
		var self = this;
		var query = self.query;
		var schoolStageId = query.schoolStageId.peek();
		var subjectId = query.subjectId.peek();
		evt = $.event.fix(evt);
		require.async(['core-business/widget/jquery.leke.knowledgeSelect'], function() {
			$(evt.target).knowledgeSelect({
				multi: true,
				data: {schoolStageId: schoolStageId,subjectId: subjectId},
				onSelect: function(klgs) {
					query.knowledges(klgs);
				}
			});
		});
	};
	
	
	ko.components.register('right-resource-list', {
	    template: require('./right-resource-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new SearchVM(params, componentInfo.element);
	    	}
	    }
	});
});