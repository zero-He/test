	var ExerciseList = {
		init : function() {
			this.binEvent();
			this.loadWeekRank();
			this.loadTodayRank();
		},
		loadWeekRank : function(){
			var week = $('#j-weekOfYear').val();
			$.post(Leke.domain.homeworkServerName + '/auth/hd/student/exercise/ajax/loadWeekRank.htm?week='+week,function(json){
				var datas= json.datas.dataList;
				var $body = $('#j-body-week-rank');
				if(datas != null && datas.length >0){
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
					$body.parents('.j-show-container').removeClass('f-hide');
					$('#j-no-week-rank').addClass('f-hide');
				}else{
					$body.parents('.j-show-container').addClass('f-hide');
					$('#j-no-week-rank').removeClass('f-hide');
				}
			})
		},
		loadTodayRank:function(){
			$.post(Leke.domain.homeworkServerName + '/auth/hd/student/exercise/ajax/loadTodayRank.htm',function(json){
				var datas= json.datas.dataList;
				var $body = $('#j-body-today-rank');
				//datas =[{photo:'',userName:'',submitTime:'',schoolName:'',rightNum:1,subjectName:'语文'},{},{},{},{},{},{},{},{}];
				if(datas != null && datas.length >0){
					var html = Mustache.render($('#j-tmpl-today-rank').html(),datas);
					$body.html(html);
					if(datas.length > 6){
						console.log('s');
						setTimeout(function(){
							 $('.lists').scrollForever({
							        continuous:true,
							        dir:'top',
							        delayTime:50
							  })
						},1000)
					}
					$('#j-no-today-rank').addClass('f-hide');
					$body.parents('.j-show-container').removeClass('f-hide');
				}else{
					$body.parents('.j-show-container').addClass('f-hide');
					$('#j-no-today-rank').removeClass('f-hide');
				}
			});
			
		},
		binEvent : function() {
			var _this = this;
			$('.j-ul-week').on('click','a',function(){
				$('.j-ul-week li').removeClass('active');
				$(this).parents('li').addClass('active');
				$('#j-weekOfYear').val($(this).data('id'));
				_this.loadWeekRank();
			});
		}
	};
	ExerciseList.init();
