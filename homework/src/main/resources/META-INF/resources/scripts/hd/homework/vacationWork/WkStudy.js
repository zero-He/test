
	
	var postUrl = '/auth/common/holiday/ajax/updateMicroState.htm';
	var datas = {
		id : Csts.holidayId,
		microId : Csts.microId,
		position : Csts.position,
		duration : 10000,
		isPlayEnd : Csts.isPlayEnd
	}

  $('.playerBox').player({
	  	id: Csts.file.id,
	    currentPosition: Csts.position,
	    onPlaying: function (cur, total) {
	    	if(isNaN(cur) || isNaN(total)) {
	    		return; 
	    	}
	    	cur = parseInt(cur);
	    	total = parseInt(total);
	    	if(cur > total || cur == datas.position) {
	    		return;
	    	}
	    	datas = $.extend(datas, {
				position : cur,
				duration : total
			});
	    	updateState(cur, cur == total);
	    },
	    onPlayComplete: function () {
	    	updateState(0, true);
	    }
  });

	var throttle = function(delay, action) {
		var last = 0;
		return function() {
			var curr = +new Date();
			if (curr - last > delay) {
				action.apply(this, arguments);
				last = curr;
			}
		}
	}
	var doSaveWork = throttle(5 * 1000, function(datas) {
		$.post(postUrl, datas);
	});


	function updateState(position,isEnd) {
		   
			var duration = parseInt(datas.duration);
			if (duration == 0) {
				return;
			}
			if((position < duration && !isEnd) || datas.isPlayEnd ){
				if(datas.isPlayEnd) {
					position = 0;
				}
				datas.position = position;
				doSaveWork(datas);
			}else{
				datas.isPlayEnd = true;
				datas.position = 0;
				$.post(postUrl, datas,function(){
					Csts.isPlayEnd = true;
					if(Csts.exerciseId != undefined || Csts.nextMicroId != undefined){
						var btns = [];
						if(Csts.exerciseId != null){
							btns.push({
								className: 'btn-green',
								text: '去练习',
								cb: function() {
									$.ajax({url:'/auth/common/holiday/generateExerciseForMicro.htm',
										async:false,
										data:{subjectName:Csts.subjectName,subjectId:Csts.subjectId, exerciseId:Csts.exerciseId,knowledgeId:Csts.knowledgeId,knowledgeName:Csts.knowledgeName},
										success:function(data){
											if(data.datas.exerciseId != null){
												//window.homework.doMicroCourseHomework(data.datas.exerciseId,0);
												LeKeBridge.sendMessage2Native('doMicroCourseHomework',JSON.stringify({'exerciseId': data.datas.exerciseId, 'microHwState':0}));
												Leke.modal.close();
											}else{
												Utils.Notice.alert('对不起，该微课暂无练习！');
											}
									}})
								}
							});
						}
						if(Csts.nextMicroId!= undefined){
							btns.push({
								className: 'btn-green',
								text: '观看下一个',
								cb: function() {
									location.href = '/auth/hd/student/vacationWork/WkStudy.htm?id='+Csts.holidayId+'&microcourseId='+Csts.microId;
								}
							});
						}
						if(window.currentRoleId == 100) {
							Leke.modal.open({
								title: '提示',
								tpl: '本次微课已学习完',
								size: 'mini',
								btns: btns
							});
						}
					}
				});
			} 
	}


	