define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var http = require('common/base/http');
	var utils = require('utils');
	var dialog = require('dialog');
	var ListVM = require('repository/common/ListVM');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var RepoService = require('repository/service/RepoService');
	var DFN = require('homework/sheet.dfn');
	var Pages = require('common/base/pages');
	var KO_QUE_CART = require('question/quecart');
	var RepoQS = require('repository/service/RepoQueryString');
	require('question/component/que-cart');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	require('repository/fmssch-sidebar');
	require('repository/fmstch-sidebar');
	
	var DM = Leke.domain; 
	var DM_QUE = DM.questionServerName;
	var DM_BK = DM.beikeServerName;
	var DM_PAP = DM.paperServerName;
	
	function SearchVM(params, element) {
		var self = this;
		self.roleId = Leke.user.currentRoleId;
		self.scopeTabs = params.scopeTabs;
		self.tabs = params.tabs;
		self.loadUrl = params.loadUrl;
		self.scope = params.scope;
		self.shareScope = params.shareScope;
		
		self.$el = $(element);
		self.$qs = self.$el.find('.j-questions');
		
		self.types = ko.observableArray();
		
		self.init({
			koPage: false,
			qkeys: ['famousSchoolId', 'famousTeacherId', 'schoolStageId', 'subjectId', 'questionTypeId','content']
		});
		
		self.bindEvents();
		self._initLinks();
		
		
		
		self.disposables.push(ko.computed(function() {
			self.loadTypes();
		}));
		
		self.afterInit();
		
		self.disposables.push(ko.computed(function() {
			_.each(['famousSchoolId', 'famousTeacherId', 'schoolStageId', 'subjectId', 'questionTypeId','content'], function(field) {
				self.query[field]();
			});
			if(self.inited) {
				self.doSearch();
			}
		}));
	}
	
	_.extend(SearchVM.prototype, new ListVM(), {
		resType: 1
	});
	
	var URL_ADD = Leke.domain.questionServerName + '/auth/common/question/add/index.htm?action=create&';
	
	SearchVM.prototype._initLinks = function() {
		var self = this;
		
		self.urlAdd = ko.observable();
		
		self.disposables.push(ko.computed(function() {
			var scope = self.shareScope;
			var tid = self.query.famousTeacherId();
			var sid = self.query.famousSchoolId();
			
			var url;
			if(scope == 2) {
				url = URL_ADD + 'curScope=2';
			} else if(scope == 8 && tid) {
				url = URL_ADD + 'curScope=8&curFmsTchId=' + tid;
			} else if(scope == 9 && sid) {
				url = URL_ADD + 'curScope=9&curFmsSchId=' + sid;
			}
			self.urlAdd(url);
		}));
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
		
		self._initStgSbjSelect();
		
		self.$el.on('click.qlist', '.selectedAll', function(evt) {
			self.selectedAll();
		});
		
		self.$el.on('click.qlist', '.j-praise', function(evt) {
			self.doPraise(evt);
		});
		
		self.$el.on('click.qlist', '.j-edit', function(evt) {
			self.openEditDialog(evt);
		});
		
		self.$el.on('click.qlist', '.j-remove', function(evt) {
			self.doRemove(evt);
		});
		
		self.$el.on('click.qlist', '.j-add-quecart', function(evt) {
			self.doAddQueCart(evt);
		});
		
		self.$el.on('click.qlist', '.j-add-batch-quecart', function(evt) {
			self.doBatchAddQueCart();
		});
	};
	
	SearchVM.prototype.doMoreType = function() {
		var $ts = $(".resourceformat .list");
		if($ts.css("min-height") != "0px"){
			$ts.css({ "min-height": "0px", "height": "41px"});
		} else {
			$ts.css({ "min-height": "41px", "height": "auto"});
		}
	}
	
	var URL_EDIT = Leke.ctx + '/auth/common/question/questionEdit.htm?';
	
	SearchVM.prototype.openEditDialog = function(evt) {
		var self = this;
		var url = $(evt.target).data('url');
		if(!url) {
			url = URL_EDIT + RepoQS.stringify({
				curScope: self.shareScope,
				action: 'override',
				questionId: qid,
				crossDomain: true
			});
		}
		dialog.open({
			id: 'questionEditDialog',
			title: $.i18n.prop('que.question.dialog.title.edit'),
			url: url,
			size: 'full',
			onClose: function(type) {
				if(type === 'success') {
					utils.Notice.alert($.i18n.prop('que.question.alert.editSucceed'));
					self.load();
				}
			}
		});
	};
	
	SearchVM.prototype.doPraise = function(evt) {
		var self = this;
		var $q = $(evt.target).closest('.item');
		var qid = $q.data('questionId');
		RepoService.doPraiseQuestion(qid).then(function() {
			utils.Notice.alert('点赞操作成功');
		}, function(msg) {
			utils.Notice.alert(msg || '点赞操作失败');
		});
	};
	
	SearchVM.prototype.doFavorite = function(evt) {
		var self = this;
		var $q = $(evt.target).closest('.item');
		var qid = $q.data('questionId');
		RepoService.doFavoriteQuestion(qid).then(function() {
			utils.Notice.alert('习题收藏成功');
		}, function(msg) {
			utils.Notice.alert(msg || '习题收藏操作失败');
		});
	};
	
	SearchVM.prototype.doBatchFavorite = function(evt) {
		var self = this;
		var qids = self._selectedResIds();
		if(!qids.length){
			utils.Notice.alert('请选择习题！');
			return false;
		}
		RepoService.doBatchFavoriteQuestion(qids.join(',')).then(function() {
			utils.Notice.alert('习题收藏成功');
		}, function(msg) {
			utils.Notice.alert(msg || '习题收藏操作失败');
		});
	};
	
	SearchVM.prototype.doAddQueCart = function(evt) {
		var self = this;
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
		self.$el.find('.c-resource-list').find('input[name="resId"]:checked').each(function() {
			var que = {};
			que.qid =  $(this).closest('.item').data('questionId');
			que.qtypeid = $(this).closest('.item').data('questionTypeId');
			
			var qtypename = _getTypeName(self.types(), que.qtypeid)
			if (qtypename != -1){
				que.qtypename = qtypename;
			}
			
			KO_QUE_CART.addType(que);
			flag = true;
		});
		
		return flag;
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
	
	SearchVM.prototype._initStgSbjSelect = function() {
		var self = this;
		var query = self.query;
		var $ss = self.$ss = self.$el.find('[name=stageSubject]');
		$ss.stgGrdSbjSelect({
			data: {
				schoolStageId: query.schoolStageId(),
				subjectId: query.subjectId()
			},
			onChange: function(item) {
				_.each(['schoolStageId', 'subjectId'], function(k) {
					query[k](item[k]);
				});
			}
		});
	};
	
	SearchVM.prototype.loadTypes = function() {
		var self = this;
		var sid = self.query.subjectId();
		var req = sid ? BasicInfoService.questionTypes(sid) : BasicInfoService.questionTypes();
		req.then(function(types) {
			if(sid !== self.query.subjectId.peek()) {
				return;
			}
			
			self.types(types || []);
			
			// 修正题型ID选择
			self.query.questionTypeId(null);
		}, function() {
			self.types([]);
		});
	};
	
	/*
	 * 实际查询操作
	 */
	SearchVM.prototype.load = _.debounce(function() {
		var self = this;
		var query = self.query.toJS();
		var req = self._lastAjax = $.ajax({
			type: 'post',
			url: self.loadUrl,
			data: query,
			dataType: 'html'
		});
		req.then(function(resp, status, ajax) {
			if(ajax == self._lastAjax) {
				self.$qs.html(resp);
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
	
	SearchVM.prototype.dispose = function() {
		var self = this;
		_.each(self.disposables, function(d) {
			d.dispose();
		});
		self.$el.off('.quePage');
		self.$el.off('.qlist');
	};

	ko.components.register('agency-question-list', {
	    template: require('./agency-question-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new SearchVM(params, componentInfo.element);
	    	}
	    }
	});
});