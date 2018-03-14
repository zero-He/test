
var listVM = {
		init: function(){
			var self = this;
			self.$count = $('#j-question-cart-count');
			self.query = {
					curPage: 1,
					shareScope: 1,
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
					diffLevel: null,
					levelName: null,
					questionTypeId: null,
					questionTypeName: null,
					$el: $('.j-conditionlist')
			};
			self.query.toJS = function(){
				var q = this;
				var result = {};
				['shareScope','content','schoolStageId','subjectId','materialNodeId','knowledgeId','diffLevel','questionTypeId'].forEach(function(n){
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
				self.query.shareScope = pref.scope;
				var $scope = $('.j-scope-cur');
				if(self.query.shareScope  == 10 ){
					$scope.html('我的分享');
					$('.j-setup').hide();
					$('.j-conditionlist').hide();
					$('.j-search-content').hide();
				}
				if(self.query.shareScope  == 4 ){
					$scope.html('我的收藏');
				}
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
				self.query.shareScope = $this.data('shareScope');
				if(self.query.shareScope  == 10 ){
					$('.j-setup').hide();
					$('.j-conditionlist').hide();
					$('.j-search-content').hide();
				}else{
					$('.j-setup').show();
					$('.j-conditionlist').show();
					$('.j-search-content').show();
				}
				self.doSearch();
				$('.j-scope-cur').html($this.html());
				HistoryServicePad.scope(self.query.shareScope);
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
					var query = self.query
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
		},
		bindEvent: function(){
			var self = this;
			var $jQuestionUl = $('.j-question-ul');
			
			$jQuestionUl.on('click','.j-question-item-del',function(){
				var $this = $(this);
				var $li = $this.closest('li');
				var questionId = $li.data('questionId');
				Utils.Notice.confirm('确定删除吗？',function(sure){
					if(sure){
						var req = questionService.doDisable(questionId);
						req.then(function(datas){
							Utils.Notice.print('删除成功！');
							self.doSearch();
						},function(msg){
							Utils.Notice.print(msg || '习题删除失败！');
						});
					}
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
			
			$jQuestionUl.on('click','.j-question-item-favorite',function(){
				var $this = $(this);
				var $li = $this.closest('li');
				var $favCount = $li.find('.favCount');
				var questionId = $li.data('questionId');
				var req = questionService.doFavorite(questionId);
				req.then(function(datas){
					Utils.Notice.print('收藏成功！');
					$this.find('i').removeClass('nocollect');
					$this.find('i').addClass('collect');
					$favCount.html(parseInt($favCount.html(), 10) + 1);
				},function(msg){
					Utils.Notice.print(msg || '习题收藏失败！');
				});
			});
			
			$jQuestionUl.on('click','.j-question-item-unfavorite',function(){
				var $this = $(this);
				var $li = $this.closest('li');
				var questionId = $li.data('questionId');
				Utils.Notice.confirm('确定取消收藏吗？',function(sure){
					if(sure){
						var req = questionService.doUnFavorite(questionId);
						req.then(function(datas){
							Utils.Notice.print('取消成功！');
							self.doSearch();
						},function(msg){
							Utils.Notice.print(msg || '习题删除失败！');
						});
					}
				});
			});
			
			$jQuestionUl.on('click','.j-question-item-share-move',function(){
				var $this = $(this);
				var shareLogId = $this.data('shareLogId');
				Utils.Notice.confirm('确定要移除吗？',function(sure){
					if(sure){
						var req = Utils.HTTP.post(Leke.ctx +'/auth/common/question/removeShareLog.htm',{shareLogId: shareLogId});
						req.then(function(datas){
							Utils.Notice.print('取消成功！');
							self.doSearch();
						},function(msg){
							Utils.Notice.print(msg || '习题删除失败！');
						});
					}
				});
			});
			
			$jQuestionUl.on('click','.j-question-item-share',function(){
				var $this = $(this);
				var $li = $this.closest('li');
				var questionId = $li.data('questionId');
				var req =  Utils.HTTP.post(Leke.ctx + '/auth/common/question/getQuestionShareInfo.htm',{questionId: questionId});
				req.then(function(datas){
					var info = datas.info || {};
					$this.openShareDialog({
						data: {
							schoolStageId: info.schoolStageId,
							subjectId: info.subjectId,
							sharePlatform: info.sharePlatform,
							shareSchool: info.shareSchool,
							section: info.sections.length && info.sections[0] || {}
						},
						onSelect: function(onData){
							onData.questionId = questionId;
							var req2 =  Utils.HTTP.post(Leke.ctx + '/auth/common/question/doShare.htm',{dataJson: JSON.stringify(onData)});
							req2.then(function(datas2) {
								var msg = '分享成功', a = datas2.tips;
								if(a && a.leke) {
									msg += '，乐豆+' + a.leke;
								}
								if(a && a.exp) {
									msg += '，经验+' + a.exp;
								}
								Utils.Notice.print(msg,3000);
							}, function(msg) {
								Utils.Notice.print(msg || '分享失败',3000);
							});
						}
					});
				},function(msg){
					Utils.Notice.print(msg || '获取习题信息错误！');
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
					var qids = Leke.questionCart.get();
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
			    url : Leke.ctx + '/auth/teacher/question/personalPad/details.htm',
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
						
			    	}
			    }
			 });
		}
};

listVM.init();