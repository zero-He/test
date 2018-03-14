define(function(require, exports, module) {
	var $ = require('jquery');
	var WdatePicker = require('date');
	var date = require('common/base/date.js');
	var Mustache = require('mustache');
	var Handlebars = require('common/handlebars');
	var Page = require('page');
	var Utils = require('utils');
	var dialog = require('dialog');
	require('homework/exercise/scrolling');

	var ExerciseList = {
		page : null,
		queryParams : {
			subjectId : null,
			submitTime : null,
			startTime : null,
			endTime : null,
			exerciseType :null
		},
		init : function() {
			this.initQueryParams();
			this.binEvent();
			this.loadWeekRank();
			this.loadTodayRank();
			this.loadList();
		},
		loadWeekRank : function(){
			var week = $('#j-weekOfYear').val();
			$.post('/auth/student/exercise/ajax/loadWeekRank.htm?week='+week,function(json){
				var datas= json.datas.dataList;
				var $body = $('#j-body-week-rank');
				if(datas != null && datas.length >0){
					datas.rankTop = function(){
				    	if(this.rank == 1){
							return "gold";
						}else if(this.rank == 2){
							return "silver";
						}
						else if(this.rank == 3){
							return "copper";
						}
						else{
							return null;
						}
					}
					datas.rankNo = function(){
						if(this.rank <= 3){
							return '';
						}else{
							return this.rank;
						}
					}
					var html = Mustache.render($('#j-tmpl-week-rank').html(),datas);
					$body.html(html);
					//提示信息
					var myRank = json.datas.myRank; 
					if(myRank.total == null){
						$('#j-self-rank-tips').html('<span>本周你还未练习，马上开始吧！</span>');
					}else if(myRank.total == 0){
						$('#j-self-rank-tips').html('<span>本周你的答题正确数为0题，再接再厉哦</span>');
					}
					else if(myRank.rank != null){
						$('#j-self-rank-tips').html('<span>本周你答对了<span>'+myRank.total+'</span>题，排行第<span>'+myRank.rank+'</span>名，表现不错哦~</span>');
					}else{
						$('#j-self-rank-tips').html('<span>本周你答对了<span>'+myRank.total+'</span>题，还需答对<span>'+myRank.difference+'</span>题就能进榜单啦，加油~');
					}
					$body.parents('.j-show-container').show();
					$('#j-no-week-rank').hide();
				}else{
					$body.parents('.j-show-container').hide();
					$('#j-no-week-rank').show();
				}
			})
		},
		loadTodayRank:function(){
			$.post('/auth/student/exercise/ajax/loadTodayRank.htm',function(json){
				var datas= json.datas.dataList;
				var $body = $('#j-body-today-rank');
				if(datas != null && datas.length >0){
					var html = Mustache.render($('#j-tmpl-today-rank').html(),datas);
					$body.html(html);
					if(datas.length > 7){
						setTimeout(function(){
							 $('.lists').scrollForever({
							        continuous:true,
							        dir:'top',
							        delayTime:50
							  })
						},1000)
					}
				}else{
					$body.parents('.j-show-container').hide();
					$('#j-no-today-rank').show();
				}
			});
			
		},
		loadList: function(){
			var _this = this;
			var queryParams = _this.queryParams;
			if(_this.queryParams.timeRangeDays == null){
				queryParams.startTime = $('#startDate').val();
				queryParams.endTime = $('#endDate').val();
			}
			_this.page = Page.create({
				id : 'page',
				url : '/auth/student/exercise/ajax/loadList.htm?'+$.param(queryParams),
				curPage : 1,
				pageSize : 10,
				tipsId : 'tips',
				callback : function(json) {
					var datas = json.page.dataList;
					datas.fmtSubmitTime = function(){
						if(this.submitTime == null){
							return '--';
						}
						return date.format(this.submitTime,'yyyy-MM-dd HH:mm:ss');
					}
					datas.fmtAccuracy = function(){
						if(this.accuracy == null) return '--';
						var rate = this.accuracy + '%';
			     		if(this.growth != null){
			     			if(this.growth ==0){
			     				return rate +'<i class="accuracy hold"></i>';
			     			}else if(this.growth > 0){
			     				return rate +'<i class="accuracy upper"></i>';
			     			}else{
			     				return rate +'<i class="accuracy down"></i>';
			     			}
			     		}else{
			     			return rate;
			     		}
					}
					datas.fmtDiffLevelName = function(){
						return _this.getLevelName(this.difficultyLevel);
					};
					
					var html = Mustache.render($('#j-tmpl-exercises').html(),datas);
					$('#j-body-exercises').html(html);
					// 数据处理，渲染表格
				}
			});
		},
		getLevelName : function(level){
			switch (level) {
			case 1:
				return "容易";
			case 2:
				return "较易";
			case 3:
				return "一般";
			case 4:
				return "较难";
			case 5:
				return "困难";
			default:
				return "";
			}
		},
		initQueryParams : function(){
			var _this = this;
			//渲染学科
			var schoolStages = Leke.userPref.authority.schoolStages;
			var datas=[];
			$.each(schoolStages,function(i,n){
				$.merge(datas,$.map(n.subjects,function(s,j){
					var isExists = false;
					if($.each(datas,function(i,n){
						if(n.code == s.subjectId){
							isExists = true;
						}
					}))
						if(!isExists){
							return {code:s.subjectId,name:s.subjectName};
						}
				}))
			})
		
			datas.sort(function(a,b){
				return a["code"] - b["code"];
			});
			_this.renderParamHtml('subjectId',datas);
		},
		renderParamHtml : function(paramName,data){
			var temple = '<li class="on"><a data-param ="{{param}}">全部</a></li>{{#.}}<li><a data-param="{{param}}" data-v="{{code}}">{{name}}</a></li>{{/.}}'
				.replace(/{{param}}/g,paramName);
			var html = Mustache.render(temple,data);
			var $ul = $('.j-ul-'+paramName);
			var deflen = $ul.data('deflen') || 10;
			$ul.html(html);
			$ulmore = $ul.parent('.right').find('.j-toggle');
			if($(html).length > deflen ){
				$ul.find('li:gt('+ (deflen -1) +')').hide();
				$ulmore.show();	
				$('.j-ul-'+paramName).next('.j-toggle').addClass('plus').removeClass('minus');
			}else{
				$ulmore.hide();
			}
		},
		binEvent : function() {
			var _this = this;
			$('.j-ul-week').on('click','a',function(){
				$('.j-ul-week li').removeClass('active');
				$(this).parents('li').addClass('active');
				$('#j-weekOfYear').val($(this).data('id'));
				_this.loadWeekRank();
			});
			$('#j-start-exercise').click(function(){
				
				var href = Leke.domain.homeworkServerName + '/auth/student/exercise/index.htm';
				window.open(href);	
			});
			
			$(document).on('click','.j-toggle',function(){
				var isPlus = $(this).attr('class').indexOf('plus')>0;
				var $ul = $(this).parents('.right').find('ul');
				if(isPlus){
					$ul.find('li').show();
					$(this).addClass('minus').removeClass('plus');
				}else{
					$(this).removeClass('minus').addClass('plus');
					var index = ($ul.data('deflen') || 10) - 1;
					$ul.find('li:gt('+ index +')').hide();
				}
			})
			$('.m-search-area').on('click','li a',function(){
				var paramName = $(this).data('param');
				$(this).parents('ul').find('li').removeClass('on');
				$(this).parent('li').addClass('on');
				_this.queryParams[paramName] = $(this).data('v') || null;
				if(paramName == 'timeRangeDays'){
					$('#startDate').val('');
					$('#endDate').val('');
					if($(this).data('v') == undefined && $(this).data('def') ==1){
						$('.j-def-SFTime').show();
					}else{
						$('.j-def-SFTime').hide();
					}
				}
				_this.loadList();
			});
			$('#startDate').click(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					maxDate : '#F{$dp.$D(\'endDate\')}',
					readOnly : true,
					onpicked : function(){
						_this.loadList();
					},oncleared:function(){
						_this.loadList();
					}
				});
			});
			$('#endDate').click(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					minDate : '#F{$dp.$D(\'startDate\')}',
					readOnly : true,
					onpicked : function(){
						_this.loadList();
					},oncleared:function(){
						_this.loadList();
					}
				});
			});
			$(document).on('click','.j-user-photo',function(){
				var uid = $(this).data('uid');
    			var href = Leke.domain.homeServerName + '/center/user/'+uid+'.htm';
    			window.open(href);
			})
		/*	$(document).on('mouseover', '.user-detail-show', function(event) {
				$('.userdetails').show();
				$('.userdetails').css("left", $(this).position().left + 300).css("top", $(this).position().top + 150);
				var userId = $(this).data('uid');
				$.getJSON( Leke.domain.incentiveServerName+ '/unauth/common/userCard.htm?userId='+ userId +'&jsoncallback=?',
				function(json) {
					if (json.success) {
						_this.cardUserId = userId;
						Handlebars.render('#j_userDetail_tmpl', json.datas, '#j_userDetail_body');
					}
				});
				$(document).on('mouseout',function(){
					$('.userdetails').hide();
				});
			});*/
		}
	};
	/*window.fromSubmit = function(exerciseType, exerciseName, relId,
			schoolStageId, schoolStageName, subjectId, subjectName,level) {
		var data = {};
		data.exerciseName = exerciseName;
		data.exerciseType = exerciseType;
		data.relId = relId;
		data.schoolStageId = schoolStageId;
		data.schoolStageName = schoolStageName;
		data.subjectId = subjectId;
		data.subjectName = subjectName;
		data.difficultyLevel = difficultyLevel;
		$.post('/auth/student/exercise/generate.htm',data);
	}*/
	ExerciseList.init();
	

});
