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
	var RepoCst = require('repository/service/RepoCst');
	var DFN = require('homework/sheet.dfn');
	var Pages = require('common/base/pages');
	var KO_QUE_CART = require('question/quecart');
	require('question/component/que-cart');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var DM = Leke.domain; 
	var DM_QUE = DM.questionServerName;
	var DM_BK = DM.beikeServerName;
	var DM_PAP = DM.paperServerName;
	
	function SearchVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$qs = self.$el.find('.j-questions');
		self.types = ko.observableArray();
		
		self.init({
			koPage: false,
			qkeys: ['schoolStageId', 'subjectId', 'questionTypeId', 'status','content'],
			initQuery: {
				status: 0
			}
		});
		
		self.bindEvents();
		
		self.disposables.push(ko.computed(function() {
			self.loadTypes();
		}));
		self.disposables.push(ko.computed(function() {
			_.each(['schoolStageId', 'subjectId', 'questionTypeId', 'status'],function(field){
				self.query[field]();
			});
			if(self.inited) {
				self.load();
			}
		}));
		
		self.afterInit();
	}
	
	_.extend(SearchVM.prototype, new ListVM(), {
		resType: 1,
		shareScope: 2,
		loadUrl: DM_QUE + '/auth/schoolResearcher/question/school/checkDetails.htm',
		tabs: RepoCst.tabs('/school/checkList.htm'),
		statusTabs: RepoCst.checkStatusTabs
	});
	
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
		
		self.$el.on('click.qlist', '.j-remove', function(evt) {
			self.doRemove(evt);
		});
		
		self.$el.on('click.qlist', '.j-batch-remove', function(evt) {
			self.doBatchRemove();
		});
		
		self.$el.on('click.qlist', '.j-pass', function(evt) {
			self.doPass(evt);
		});
		
		self.$el.on('click.qlist', '.j-batch-pass', function(evt) {
			self.doBatchPass();
		});
		
		self.$el.on('click.qlist', '.j-reject', function(evt) {
			self.doReject(evt);
		});
		
		self.$el.on('click.qlist', '.j-batch-reject', function(evt) {
			self.doBatchReject();
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
		self._doBatch(function(qids) {
			RepoService.doBatchFavoriteQuestion(qids.join(',')).then(function() {
				utils.Notice.alert('习题收藏成功');
			}, function(msg) {
				utils.Notice.alert(msg || '习题收藏操作失败');
			});
		});
	};
	
	SearchVM.prototype.doRemove = function(evt) {
		var self = this;
		var qid = $(evt.target).closest('.item').data('questionId');
		self.sendRemove([qid]);
	};
	
	SearchVM.prototype.doBatchRemove = function() {
		var self = this;
		self._doBatch(function(qids) {
			self.sendRemove(qids);
		});
	};
	
	var API_DIR = Leke.ctx + '/auth/schoolResearcher/question/school/';
	
	SearchVM.prototype.sendRemove = function(qids) {
		var self = this;
		dialog.confirm('确定从学校库中移除吗？').then(function(sure) {
			if(sure) {
				http.postJSON(API_DIR + 'doRemove.htm', qids).then(function() {
					utils.Notice.alert('习题移出操作成功');
					self.load();
				}, function(msg) {
					utils.Notice.alert(msg || '习题移出操作失败');
				});
			}
		});
	};
	
	SearchVM.prototype.doPass = function(evt) {
		var self = this;
		var qid = $(evt.target).closest('.item').data('questionId');
		self.sendPass([qid]);
	};
	
	SearchVM.prototype.doBatchPass = function() {
		var self = this;
		self._doBatch(function(qids) {
			self.sendPass(qids);
		});
	};
	
	SearchVM.prototype.sendPass = function(qids) {
		var self = this;
		http.postJSON(API_DIR + 'doCheckPass.htm', qids).then(function() {
			utils.Notice.alert('习题审核通过操作成功');
			self.load();
		}, function(msg) {
			utils.Notice.alert(msg || '习题审核通过操作失败');
		});
	};
	
	SearchVM.prototype.doReject = function(evt) {
		var self = this;
		var qid = $(evt.target).closest('.item').data('questionId');
		self.promptReject([qid]);
	};
	
	SearchVM.prototype.doBatchReject = function() {
		var self = this;
		self._doBatch(function(qids) {
			self.promptReject(qids);
		});
	};
	
	SearchVM.prototype.promptReject = function(qids) {
		var self = this;
		dialog.prompt('退回原因').then(function(reason) {
			http.postJSON(API_DIR + 'doCheckReject.htm', {
				resIds: qids,
				checkNote: reason
			}).then(function() {
				utils.Notice.alert('习题审核不通过操作成功');
				self.load();
			}, function(msg) {
				utils.Notice.alert(msg || '习题审核不通过操作失败');
			});
		});
	};
	
	SearchVM.prototype.doAddQueCart = function(evt) {
		var self = this;
		/*var qid = $(evt.target).closest('.item').data('questionId');
		KO_QUE_CART.add(qid);*/
		
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
	
	SearchVM.prototype._doBatch = function(action) {
		var self = this;
		var resIds = self._selectedResIds();
		if(!resIds.length){
			utils.Notice.alert('请选择习题!');
			return false;
		}
		action.call(self, resIds);
	};
	
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

	ko.components.register('check-question-list', {
	    template: require('./check-question-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new SearchVM(params, componentInfo.element);
	    	}
	    }
	});
});