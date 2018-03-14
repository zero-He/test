define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('underscore');
	var dialog = require('dialog');
	var utils = require('utils');
	var Mustache = require('mustache');
	var http = require('common/base/http');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var RepoService = require('repository/service/RepoService');
	var DFN = require('homework/sheet.dfn');
	var Pages = require('common/base/pages');
	var KO_QUE_CART = require('question/quecart');
	var FavoriteService = require('repository/service/FavoriteService');
	
	require('question/component/que-cart');
	
	var DIFF_LVLS = [{
		diffLevel: 1, 
		levelName: '容易'
	}, {
		diffLevel: 2, 
		levelName: '较易'
	}, {
		diffLevel: 3, 
		levelName: '一般'
	}, {
		diffLevel: 4, 
		levelName: '较难'
	}, {
		diffLevel: 5, 
		levelName: '困难'
	}];
	
	var ORDER_BYS = [{
		sort: 1,
		orderByName: '最新'
	}, {
		sort: 3,
		orderByName: '加入次数'
	}, {
		sort: 2,
		orderByName: '单题班级得分率'
	}];
	
	function Query(){
		var self = this;
		self.classId = ko.observable();
		self.subjectId = ko.observable();
		self.questionTypeId = ko.observable();
		self.diffLevel = ko.observable();
		self.minRate = ko.observable();
		self.maxRate = ko.observable();
		self.sort = ko.observable(1);
		self.curPage = ko.observable(1);
		self.knowledgeId = ko.observable();
		self.selKnowledge = ko.observable();
	};
	
	Query.prototype.toJS = function(){
		var self = this;
		var result = {};
		_.each(['classId','subjectId','questionTypeId','diffLevel','minRate','maxRate','sort','curPage','knowledgeId'],function(n){
			result[n] = self[n]();
		});
		return result;
	};
	
	function SearchVM(params, element) {
		var self = this;
		self.$el = $(element);
		self.$qs = self.$el.find('.j-questions');
		self.query = new Query();
		
		self.rate = ko.observable();
		self.teacherClass = ko.observableArray(params.clazzs);
		self.subjects = ko.observableArray(params.subjects);
		self.loadUrl = params.loadUrl;
		self.diffLevels = DIFF_LVLS;
		self.orderBys = ORDER_BYS;
		self.types = ko.observableArray();
		self.typesAll = ko.observableArray();
		BasicInfoService.questionTypes().then(function(types){
			self.typesAll(types);
		});
		self.wrongKlgs = ko.observableArray();
		self.wrongKlgsDesc = ko.observable(null);

		
		self.bindEvents();
		self._initslide();
		
		self.disposables = [];
		
		self.disposables.push(ko.computed(function() {
			_.each(['subjectId','questionTypeId','diffLevel','sort'],function(n){
				self.query[n]();
			});
			self.doSearch();
			self.getWrongSubject();
			self.hideEditRate();
		}));
		
		self.disposables.push(ko.computed(function() {
			var curPage = self.query.curPage();
			self.load();
		}));
		
		self.disposables.push(ko.computed(function() {
			var teacherClass = self.teacherClass();
			if(teacherClass.length){
				self.query.classId(teacherClass[0].classId);
			}
		}));
		
		self.disposables.push(ko.computed(function() {
			var query = self.query;
			var classId = query.classId();
			var subjects = self.subjects();
			query.questionTypeId(null);
			query.diffLevel(null);
			query.minRate(null);
			query.maxRate(null);
			query.sort(1);
			if(subjects.length){
				self.query.subjectId(subjects[0].subjectId);
			}
			self.doSearch();
			self.getWrongSubject();
			self.hideEditRate();
		}));
		
		self.disposables.push(ko.computed(function() {
			var subjectId = self.query.subjectId();
			self.loadTypes();
		}));
		
		self.disposables.push(ko.computed(function() {
			var wrongKlgsDesc = self.wrongKlgsDesc();
			self.orderByWrongKlgs();
		}));
		
		self.disposables.push(ko.computed(function(){
			var qids = KO_QUE_CART.qids();
			self.settingQuecartInfo();
		}));
	};
	
	var DIR_WS = Leke.ctx + '/auth/teacher/question/wrong/getWrongSubject.htm';
	SearchVM.prototype.getWrongSubject = _.debounce(function(){
		var self = this;
		var query = self.query;
		var classId = query.classId();
		var subjectId = query.subjectId();
		if(classId && subjectId){
			var req = http.post(DIR_WS,{classId: classId,subjectId: subjectId});
			req.then(function(datas){
				var wrongSubject = datas.wrongSubject || {};
				if(wrongSubject.rate != 0){
					self.rate(wrongSubject.rate || 50);
				}else{
					self.rate(0);
				}
				
			},function(msg){
				utils.Notice.alert(msg || '加载单题班级得分率错误！');
			});
		}
	},300);
	
	var DIR_FIND = Leke.ctx + '/auth/teacher/question/wrong/findWrongSubjectKnowledges.htm';
	SearchVM.prototype.findWrongSubjectKnowledges = _.debounce(function(){
		var self = this;
		var query = self.query.toJS();
		if(!query.classId || !query.subjectId){
			self.wrongKlgs([]);
			return;
		}
		var req = http.post(DIR_FIND,query);
		req.then(function(datas){
			var wrongKlgs = datas.wrongKlgs || [];
			self.wrongKlgs(wrongKlgs);
		},function(msg){
			utils.Notice.alert(msg || '知识点分布数据错误！');
		});
	},300);
	
	SearchVM.prototype.orderByWrongKlgs = function(){
		var self = this;
		var wrongKlgs = self.wrongKlgs();
		if(!wrongKlgs.length){
			return;
		}
		if(self.wrongKlgsDesc()){
			wrongKlgs = _.sortBy(wrongKlgs,'questionTotal');
		}else{
			wrongKlgs = _.sortBy(wrongKlgs,function(n){ return n.questionTotal * -1;});
		}
		self.wrongKlgs(wrongKlgs);
	};
	
	
	SearchVM.prototype.doSearch = function(){
		var self = this;
		self.query.curPage(1);
		self.load();
	};
	SearchVM.prototype.doSearchByKnowledge = function(know){
		var self = this;
		self.query.curPage(1);
		self.query.questionTypeId(null); 
		self.query.diffLevel(null);
		self.query.minRate(null);
		self.query.maxRate(null);
		self.query.sort(1);
		if(know != null){
			self.query.knowledgeId(know.knowledgeId);
		}else{
			self.query.knowledgeId(null);
		}
		self.query.selKnowledge(know);
		self.load();
	};
	
	SearchVM.prototype.load = _.debounce(function() {
		var self = this;
		var query = self.query.toJS();
		if(query.minRate && query.minRate != 0 &&!parseInt(query.minRate, 10)){
			utils.Notice.alert('请在0-100间查询单题班级得分率！');
			return false;
		}
		if(query.minRate != '' && (query.minRate < 0 || query.minRate > 100)){
			utils.Notice.alert('请在0-100间查询单题班级得分率！');
			return false;
		}

		if(query.maxRate && query.maxRate != 0 && !parseInt(query.maxRate, 10)){
			utils.Notice.alert('请在0-100间查询单题班级得分率！');
			return false;
		}
		if(query.maxRate != '' && (query.maxRate < 0 || query.maxRate > 100)){
			utils.Notice.alert('请在0-100间查询单题班级得分率！');
			return false;
		}
		if(parseInt(query.maxRate, 10) < parseInt(query.minRate, 10)){
			utils.Notice.alert('请输入正确范围的单题班级得分率查询！');
			return false;
		}
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
				self.settingFavInfo();
				self.settingQuecartInfo();
				self.hideAnswerExplain();
			}
			self.findWrongSubjectKnowledges();
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
		
		self.$el.on('click.qlist', '.j-favorite', function(evt) {
			self.doFavorite(evt);
		});
		
		self.$el.on('click.qlist', '.j-batch-favorite', function(evt) {
			self.doBatchFavorite();
		});
		
		self.$el.on('click.qlist', '.j-answer', function(evt) {
			self.doSlideAnswer(evt);
		});
		
		self.$el.on('click.qlist', '.j-add-quecart', function(evt) {
			self.doAddQueCart(evt);
		});
		
		self.$el.on('click.qlist', '.j-add-batch-quecart', function(evt) {
			self.doBatchAddQueCart();
		});
		
		self.$el.on('click.qlist', '.j-remove-quecart', function(evt) {
			self.doRemoveQueCart(evt);
		});
		
		self.$el.on('click.qlist', '.j-delete', function(evt) {
			self.doDelete(evt);
		});
		self.$el.on('click.qlist', '.j-view-student', function(evt) {
			self.doShowStudent(evt);
		});
		$('body').on('click',function(e){
	            var flag=false;
	            if($(e.target).parents('.e-students-list').length>=1 || $(e.target).hasClass('e-students-list') || $(e.target).hasClass('look-students')){
	                flag=true;
	            }
	            if(flag){
	                return;
	            }
	            $('.e-students-list').hide();
		});
	};
	
	/*导航滑动事件*/
	SearchVM.prototype._initslide  = function(){
		var self = this;
		 var $dom = $('.er-tab ul'),btnpre = $('.er-tab-pre'),btnnext = $('.er-tab-next'),item = self.teacherClass(),$left = 0;
	     if(item.length <= 7){
	         btnpre.hide();
	         btnnext.hide();
	     }else{
	         btnpre.show();
	         btnnext.show().addClass('er-tab-on');
	     }
	     btnpre.click(function(){
	        if($left >= -1106){
	             $left=0;
	             $dom.animate({margin:"0 0 0 "+$left+"px"});
	             btnpre.removeClass('er-tab-on');
	             btnnext.addClass('er-tab-on');
	         }else{
	             $left += 1106;
	             $dom.animate({margin:"0 0 0 "+$left+"px"});
	             btnnext.addClass('er-tab-on');
	         }
	     });
	     btnnext.click(function(){
	            var n=item.length%7;
	            var i=Math.floor(item.length/7);
	            if(i<=1){
	                $left=-(i*1106)
	                btnnext.removeClass('er-tab-on');
	                $dom.stop().animate({margin:"0 0 0 "+$left+"px"});
	                btnpre.addClass('er-tab-on');
	            }else{
	                if((($left==-(i*1106)+1106) && n==0) || ($left==-(i*1106)) && n>0){
	                    return;
	                }
	                $left-=1106;
	                $dom.stop().animate({margin:"0 0 0 "+$left+"px"});
	                btnpre.addClass('er-tab-on');
	            }
	            if((($left==-(i*1106)+1106) && n==0) || ($left==-(i*1106)) && n>0){
	                btnnext.removeClass('er-tab-on');
	                return;
	            }
	        });
	};
	
	
	SearchVM.prototype.doFavorite = function(evt) {
		var self = this;
		var $q = $(evt.target).closest('.error-collection-content-item');
		var qid = $q.data('questionId');
		RepoService.doFavoriteQuestion(qid).then(function(datas) {
			var msg, a = datas.award;
			if(a && a.succ) {
				msg = '习题收藏成功';
				if(a.lekeVal) {
					msg += '，乐豆+' + a.lekeVal;
				}
				if(a.expVal) {
					msg += '，经验 ' + a.expVal;
				}
			} else {
				msg = '习题收藏操作失败';
			}
			utils.Notice.alert(msg);
			_setFavClass($q);
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
		RepoService.doBatchFavoriteQuestion(qids.join(',')).then(function(datas) {
			var msg, a = datas.award;
			if(a && a.succ) {
				msg = '习题收藏成功';
				if(a.lekeVal) {
					msg += '，乐豆+' + a.lekeVal;
				}
				if(a.expVal) {
					msg += '，经验 ' + a.expVal;
				}
			} else {
				msg = '习题收藏操作失败';
			}
			utils.Notice.alert(msg);
			$('#selectedAll').prop('checked',false);
			self.load();
		}, function(msg) {
			utils.Notice.alert(msg || '习题收藏操作失败');
		});
	};
	
	SearchVM.prototype.doAddQueCart = function(evt) {
		var self = this;
		var que = {};
		que.qid =  $(evt.target).closest('.error-collection-content-item').data('questionId');
		que.qtypeid = $(evt.target).closest('.error-collection-content-item').data('questionTypeId');
		var qtypename = _getTypeName(self.typesAll(), que.qtypeid);
		if (qtypename != -1){
			que.qtypename = qtypename;
		}
		KO_QUE_CART.addType(que);
		require.async(['common/ui/ui-fly/ui-fly'], function(f) {
			var ops = {el:'.c-exercisebasket .basket .con'};
			f.doFly(evt, ops);
			self.settingQuecartInfo();
		});
	};
	
	SearchVM.prototype.doRemoveQueCart = function(evt) {
		var self = this;
		var que = {};
		que.qid =  $(evt.target).closest('.error-collection-content-item').data('questionId');
		que.qtypeid = $(evt.target).closest('.error-collection-content-item').data('questionTypeId');
		var qtypename = _getTypeName(self.typesAll(), que.qtypeid);
		if (qtypename != -1){
			que.qtypename = qtypename;
		}
		KO_QUE_CART.remove(que);
		self.settingQuecartInfo();
		$('#selectedAll').prop('checked',false);
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
		var flag = self._selectedResques();
		if(!flag){
			utils.Notice.alert('请选择习题!');
			return false;
		}

		utils.Notice.alert('加入成功！');
		$('#selectedAll').prop('checked',false);
		self.selectedAll();
	};
	
	SearchVM.prototype._selectedResques = function() {
		var self = this;
		var flag = false;
		self.$el.find('.j-questions .q-checkbox').find('input:checked').each(function() {
			var que = {};
			que.qid =  $(this).closest('.error-collection-content-item').data('questionId');
			que.qtypeid = $(this).closest('.error-collection-content-item').data('questionTypeId');
			
			var qtypename = _getTypeName(self.typesAll(), que.qtypeid)
			if (qtypename != -1){
				que.qtypename = qtypename;
			}
			
			KO_QUE_CART.addType(que);
			self.settingQuecartInfo();
			flag = true;
		});
		
		return flag;
	};
	
	var DIR_DEL = Leke.ctx + '/auth/teacher/question/wrong/delWrongQuestion.htm';
	SearchVM.prototype.doDelete = function(evt){
		var self = this;
		var id =  $(evt.target).closest('.error-collection-content-item').data('id');
		var req = http.post(DIR_DEL,{id: id});
		req.then(function(datas){
			utils.Notice.alert('删除成功！');
			self.load();
		},function(msg){
			utils.Notice.alert(msg || '删除失败！');
		});
	};
	SearchVM.prototype.doShowStudent = function(evt){
		var self = this;
		var $queContainer = $(evt.target).closest('.error-collection-content-item');
		var qid =  $queContainer.data('questionId');
		var hwId = $queContainer.data('homeworkId');
		if($queContainer.find('.j-student-ul').find('li').length == 0){
			var url = Leke.domain.homeworkServerName + '/auth/teacher/homework/wrongUsers.htm?jsonpcallback=?';
			var datas = {homeworkId:hwId,questionId:qid};
			$.getJSON(url,datas,function(json){
				var html =	Mustache.render('{{#.}} <li class="item"><a href="##">{{userName}}</a></li> {{/.}}',json.datas.studentList);
				$queContainer.find('.j-student-ul').html(html);
			});
		}
		$queContainer.find('.e-students-list').show();
	};
	
	SearchVM.prototype.selectedAll = function(){
		var self = this;
		var val = $('#selectedAll').prop('checked');
		self.$el.find('.error-collection-content .q-checkbox').find('input').prop('checked', val);
	};
	
	SearchVM.prototype._selectedResIds = function() {
		var self = this;
		var resIds = [];
		self.$el.find('.error-collection-content .q-checkbox').find('input:checked').each(function() {
			var que = {};
			que.qid =  $(this).closest('.error-collection-content-item').data('questionId');
			resIds.push(que.qid);
		});
		return resIds;
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
	
	
	SearchVM.prototype.settingFavInfo = function(){
		var self = this;
		var resIds = [];
		var questions = $('.j-questions').find('.error-collection-content-item');
		
		$('div',questions).each(function(){
			var qid = $(this).attr('data-question-id');
			if(qid){
				resIds.push(qid);
			}
		});
		if(resIds.length){
			var req =	FavoriteService.filterFavQuestions(resIds);
			req.then(function(datas){
				_.each(datas.resIds || [],function(resId){
					_.each(questions,function(n){
						var qid = $(n).attr('data-question-id');
						if(qid == resId){
							_setFavClass($('.error-collection-fr',n));
						}
					});
				});
			},function(msg){
				utils.Notice.alert(msg || '收藏信息加载失败');
			});
		}
	};
	
	function _setFavClass(context){
		var doc = $('.j-favorite',context);
		doc.addClass('error-operate-btn-collect');
	};
	
	SearchVM.prototype.hideAnswerExplain = function(){
		var self = this;
		$('.q-rightanswer',self.$qs).hide();
		$('.q-explain',self.$qs).hide();
	};
	
	SearchVM.prototype.settingQuecartInfo = function(){
		var self = this;
		var qids = KO_QUE_CART.getQids();
		self.$el.find('.j-add-quecart').show();
		self.$el.find('.j-remove-quecart').hide();
		_.each(qids,function(n){
			self.$el.find('div[data-question-id=' + n +']').find('.j-add-quecart').hide();
			self.$el.find('div[data-question-id=' + n +']').find('.j-remove-quecart').show();
		});
	};
	
	SearchVM.prototype.doSlideAnswer = function(evt){
		var self = this;
		var $q = $(evt.target);
		var $div = $(evt.target).closest('.error-collection-content-item');
        var $isHide = $q.hasClass('texthide');
        if(!$isHide){
        	$q.text("隐藏答案").removeClass("textshow").addClass("texthide");
            $('.q-rightanswer',$div).show();
            $('.q-explain',$div).show();
        }else{
        	$q.text("显示答案").removeClass("texthide").addClass("textshow");
            $('.q-rightanswer',$div).hide();
            $('.q-explain',$div).hide();
        }
	};
	
	var DIR_RATE = Leke.ctx + '/auth/teacher/question/wrong/updateWrongSubjRate.htm';
	SearchVM.prototype.doUpdateWrongSubjRate = function(){
		var self = this;
		var classId = self.query.classId();
		var subjectId = self.query.subjectId();
		var rate = self.rate();
		if(!rate){
			utils.Notice.alert('请在0-100间修改单题班级得分率！');
			return false;
		}
		if(rate < 0 || rate > 100){
			utils.Notice.alert('请在0-100间修改单题班级得分率！');
			return false;
		}
		var req = http.post(DIR_RATE,{classId: classId,subjectId: subjectId,rate: self.rate()});
		req.then(function(datas){
			utils.Notice.alert('修改成功');
			self.hideEditRate();
		},function(msg){
			utils.Notice.alert(msg || '单题班级得分率保存错误');
		});
	};
	
	SearchVM.prototype.showEditRate = function(){
		var self = this;
		$('.text-rate').show();
		$('.html-rate').hide();
		$('.btn-rate').hide();
		$('.average-grade').addClass('average-grade-edit');
		$('.btn-save-rate').show();
	};
	
	SearchVM.prototype.hideEditRate = function(){
		var self = this;
		$('.text-rate').hide();
		$('.html-rate').show();
		$('.btn-rate').show();
		$('.average-grade').removeClass('average-grade-edit');
		$('.btn-save-rate').hide();
	};
	
	SearchVM.prototype.dispose = function() {
		var self = this;
		_.each(self.disposables, function(d) {
			d.dispose();
		});
		self.$el.off('.quePage');
		self.$el.off('.qlist');
	};
	
	ko.components.register('question-wrong-list', {
	    template: require('./question-wrong-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new SearchVM(params, componentInfo.element);
	    	}
	    }
	});
});