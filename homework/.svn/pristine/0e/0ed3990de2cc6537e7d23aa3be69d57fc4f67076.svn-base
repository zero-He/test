	var ExerciseList = {
		init : function() {
			this.bindEvent();
			this.loadWeekRank();
			this.loadTodayRank();
		},
		loadWeekRank : function(){
			var week = $('#j-weekOfYear').val();
			
		},
		loadTodayRank:function(){
			
			
		},
		bindEvent : function() {
			var _this = this;
			$('.study').on('click', function(){
				window.location.href = Leke.domain.learnServerName + '/auth/student/topic/pad/subjectStudy.htm';
				//var url = Leke.ctx + '/auth/student/topic/pad/subjectStudy.htm';
				//window.location = url;
			});
			

			$('.practice').on('click', function(){
				var url = Leke.domain.homeworkServerName + '/auth/hd/student/exercise/chapterTree.htm';
				window.location = url;
			});
			
		}
	};
	ExerciseList.init();
