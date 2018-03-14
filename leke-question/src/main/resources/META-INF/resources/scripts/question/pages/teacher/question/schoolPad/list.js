
var listVM = {
		init: function(){
			var self = this;
			self.$count = $('#j-question-cart-count');
			self.query = {
					curPage: 1,
					content: null,
					schoolStageId: null,
					schoolStageName: null,
					subjectId: null,
					subjectName: null,
					pressId: null,
					pressName: null,
					materialNodeId: null,
					materialNodeName: null,
					materialNodePath: null,
					knowledgeId: null,
					knowledgeName: null,
					sourceType: null,
					diffLevel: null,
					levelName: null,
					questionTypeId: null,
					questionTypeName: null,
					orderBy: 1,
					$el: $('.j-conditionlist')
			};
			self.query.toJS = function(){
				var q = this;
				var result = {};
				['content','schoolStageId','subjectId','materialNodeId','knowledgeId','sourceType','diffLevel','questionTypeId','orderBy'].forEach(function(n){
					result[n] = q[n];
				});
				return result;
			};
			
			self.query.refreshHtml = function(){
				var q = this;
				var html = '';
				
				if(q.schoolStageId){
					//<em class="del j-search-clean-schoolStageId">×</em>
					html += '<span class="item"><em class="con">' + q.schoolStageName + q.subjectName + '</em></span>';
				}
				if(q.materialNodeId){
					html += '<span class="item"><em class="con">' + q.materialNodePath + '</em><em class="del j-search-clean-materialNodeId">×</em></span>';
				}
				if(q.knowledgeId){
					html += '<span class="item"><em class="con">' + q.knowledgeName + '</em><em class="del j-search-clean-knowledgeId">×</em></span>';
				}
				if(q.diffLevel){
					html += '<span class="item"><em class="con">' + q.levelName + '</em><em class="del j-search-clean-diffLevel">×</em></span>';
				}
				if(q.questionTypeId){
					html += '<span class="item"><em class="con">' + q.questionTypeName + '</em><em class="del j-search-clean-questionTypeId">×</em></span>';
				}
				if(q.content){
					html += '<span class="item"><em class="con">' + q.content + '</em><em class="del j-search-clean-content">×</em></span>';
				}
				q.$el.html(html);
			};
			
			self.query.bindEvent = function(){
				var q = this;
				q.$el.on('click','.j-search-clean-content',function(){
					var $this = $(this);
					$this.closest('span').remove();
					q.content = '';
					self.doSearch();
				});
				q.$el.on('click','.j-search-clean-schoolStageId',function(){
					var $this = $(this);
					$this.closest('span').remove();
					q.schoolStageId = '';
					q.schoolStageName = '';
					q.subjectId = '';
					q.subjectName = '';
					self.doSearch();
				});
				
				q.$el.on('click','.j-search-clean-materialNodeId',function(){
					var $this = $(this);
					$this.closest('span').remove();
					q.materialNodeId = '';
					q.materialNodeName = '';
					self.doSearch();
				});
				
				q.$el.on('click','.j-search-clean-knowledgeId',function(){
					var $this = $(this);
					$this.closest('span').remove();
					q.knowledgeId = '';
					q.knowledgeName = '';
					self.doSearch();
				});
				q.$el.on('click','.j-search-clean-diffLevel',function(){
					var $this = $(this);
					$this.closest('span').remove();
					q.diffLevel = '';
					q.levelName = '';
					self.doSearch();
				});
				
				q.$el.on('click','.j-search-clean-questionTypeId',function(){
					var $this = $(this);
					$this.closest('span').remove();
					q.questionTypeId = '';
					q.questionTypeName = '';
					self.doSearch();
				});
				
			};
			
			self.bindEventQuery();
			self.bindEvent();
			self.query.bindEvent();
			var req = HistoryServicePad.get();
			req.then(function(datas){
				var pref = datas.pref || {};
				self.query = $.extend(self.query, pref);
				self.load();
			},function(msg){
				Utils.Notice.print(msg || '加载历史操作数据错误！');
			});
		},
		bindEventQuery: function(){
			var self = this;
			
			$('.j-scope-cur').on('click',function(event){
				event.stopPropagation();
				$(".main").click(function(event) {
					 $('.j-scope').hide();
			      });
				$('.j-scope').toggle();
			});
			
			$('.j-scope').on('click','.item',function(){
				var $this = $(this);
				$('.j-scope .item').removeClass('cur');
				$this.addClass('cur');
				$('.j-scope').toggle();
				self.query.sourceType = $this.data('sourceType');
				self.doSearch();
				$('.j-scope-cur').html($this.html());
			});
			
			$('.j-content-del').on('click',function(){
				$('#j-content').val('');
				if(self.query.content){
					self.query.content = '';
					self.doSearch();
				}
			});
			
			$('.j-search').on('click',function(){
				self.query.content = $('#j-content').val();
				self.doSearch();
			});
			
			$('.j-search-cancel').on('click',function(){
				$('.j-search-content').hide();
				$('.j-searchico').show();
			});
			
			$('.j-searchico').on('click',function(){
				$(this).hide();
				$('.j-search-content').show();
			});
			
			
			$('.j-search-setting').on('click',function(){
				var req = HistoryServicePad.get();
				req.then(function(datas){
					var pref = datas.pref || {};
					var query = self.query;
					if(query.materialNodeId){
						pref.materialNodeId = query.materialNodeId;
					}
					if(query.knowledgeId){
						pref.knowledgeId = query.knowledgeId;
		        	}
		        	if(query.diffLevel){
		        		pref.diffLevel = query.diffLevel;
		        	}
		        	if(query.questionTypeId){
		        		pref.questionTypeId = query.questionTypeId;
		        	}
					Leke.repo.pOpenQueFilter({
						stgAll: true,
				        data: pref, 
				        onSelect: function(data) {
				        	if(!data.schoolStageId){
				        		Utils.Notice.print('请选择学段！');
				        		return false;
				        	}
				        	if(!data.subjectId){
				        		Utils.Notice.print('请选择学科！');
				        		return false;
				        	}
				        	if(data.schoolStageId && data.subjectId){
				        		query.schoolStageId = data.schoolStageId;
				        		query.schoolStageName = data.schoolStageName;
				        		query.subjectId = data.subjectId;
				        		query.subjectName = data.subjectName;
				        	}
				        	if(data.materialNodeId){
				        		query.pressId = data.pressId;
				        		query.pressName = data.pressName;
				        		query.materialNodeId = data.materialNodeId;
				        		query.materialNodeName = data.materialNodeName;
				        		query.materialNodePath =  data.materialNodePath;
				        	}else{
				        		query.materialNodeId = null;
				        		query.materialNodeName = '';
				        		query.materialNodePath = '';
				        	}
				        	if(data.knowledgeId){
				        		query.knowledgeId = data.knowledgeId;
				        		query.knowledgeName = data.knowledgeName;
				        	}else{
				        		query.knowledgeId = null;
				        		query.knowledgeName = '';
				        	}
				        	if(data.diffLevel){
				        		query.diffLevel = data.diffLevel;
				        		query.levelName = data.levelName;
				        	}else{
				        		query.diffLevel = null;
				        		query.levelName = '';
				        	}
				        	if(data.questionTypeId){
				        		query.questionTypeId = data.questionTypeId;
				        		query.questionTypeName = data.questionTypeName;
				        	}else{
				        		query.questionTypeId = null;
				        		query.questionTypeName = '';
				        	}
				        	HistoryServicePad.put(data);
				        	self.doSearch();
				        }
				    });
				},function(msg){
					Utils.Notice.print(msg || '加载历史操作记录错误！');
				});
			});
			
			$('.j-order').on('click',function(event){
				event.stopPropagation();
		        $(".main").click(function(event) {
		        	$('.j-order-by').hide();
		        });
				$('.j-order-by').toggle();
			});
			
			$('.j-order-by').on('click','.item',function(){
				var $this = $(this);
				var orderBy = $this.data('orderBy');
				$('.j-order-by .item').removeClass('cur');
				$this.addClass('cur');
				self.query.orderBy = orderBy;
				self.doSearch();
			});
		},
		bindEvent: function(){
			var self = this;
			
			var $jQuestionUl = $('.j-question-ul');
			
			$jQuestionUl.on('click','.j-question-item-favorite',function(){
				var $this = $(this);
				var $li = $this.closest('li');
				var questionId = $li.data('questionId');
				var $favCount = $li.find('.favCount');
				var req = questionService.doFavorite(questionId);
				req.then(function(datas){
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
					Utils.Notice.print(msg);
					$this.find('i').removeClass('nocollect');
					$this.find('i').addClass('collect');
					$favCount.html(parseInt($favCount.html(), 10) + 1);
				},function(msg){
					Utils.Notice.print(msg || '习题收藏失败！');
				});
			});
			
			$jQuestionUl.on('click','.j-question-item-praise',function(){
				var $this = $(this);
				var $li = $this.closest('li');
				var $praiseCount = $li.find('.praiseCount');
				var questionId = $li.data('questionId');
				var req = questionService.doPraise(questionId);
				req.then(function(datas){
					Utils.Notice.print('点赞成功！');
					$praiseCount.html(parseInt($praiseCount.html(), 10) + 1);
				},function(msg){
					Utils.Notice.print(msg || '习题点赞失败！');
				});
			});
			
			//加入习题篮
			$jQuestionUl.on('click','.j-question-item-checkbox',function(){
				var $this = $(this);
				var qid = $this.data('questionId');
				var $count = self.$count;
				var count = parseInt($count.html(), 10);
				if($this.hasClass('cur')){
					$this.removeClass('cur');
					Leke.questionCart.del(qid);
					$count.html(--count);
				}else{
					$this.addClass('cur');
					Leke.questionCart.add(qid);
					$count.html(++count);
				}
			});
			
			$('#j-to-paper-add').on('click',function(){
				window.location.href = Leke.domain.paperServerName +  '/auth/common/quecart/pad/addPaper.htm';
			});
			
		},
		doSearch: function(){
			var self = this;
			self.query.curPage = 1;
			self.load();
		},
		load: function(){
			var self = this;
			self.query.refreshHtml();
			$(".j-question-content").page({
			    url : Leke.ctx + '/auth/teacher/question/schoolPad/details.htm',
			    queryData: self.query.toJS(),
			    curPage: self.query.curPage,
			    scrollCont: document.querySelector('.j-question-ul'),
			    callbackPullDown: function(datas) {
			    	if(datas.dataList && datas.dataList.length){
			    		var $qul = $(datas.dataList[0]);
			    		$('.j-question-ul').append($qul);
			    		//初始化习题篮
						Leke.questionCart.init({callBack: function(ids){
							self.$count.html(ids.length);
							$('.j-question-ul').find('.j-question-item-checkbox').each(function(){
								var $this = $(this);
								var qid = $this.data('questionId');
								if(ids.indexOf(qid) > -1){
									$this.addClass('cur');
								}
							});
						}});
			    		self.filterFavorite($qul);
			    	}
			    }
			 });
		},
		filterFavorite: function($qul){
			var resIds = [];
			$qul.each(function(){
				if($(this).data('questionId')){
					resIds.push($(this).data('questionId'));
				}
			});
			if(resIds.length){
				var req = questionService.filterFavorite(resIds);
				req.then(function(datas){
					if(datas && datas.resIds && datas.resIds.length){
						datas.resIds.forEach(function(n){
							var $fav = $('#j-question-item-favorite-' + n);
							$fav.removeClass('nocollect');
							$fav.addClass('collect');
						});
					}
				},function(msg){
					Utils.Notice.print(msg || '加载习题列表收藏信息错误！');
				});
			}
		}
};

listVM.init();