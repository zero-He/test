define(function(require, exports, module) {
		var $ = require('jquery');
		var Dialog = require('dialog');
		var Utils = require('utils');
		require('common/ui/ui-fileplayer/ui-fileplayer.js');
		
		var postUrl = '/auth/common/holiday/ajax/updateMicroState.htm';
		var datas = {
			id : Csts.holidayId,
			microId : Csts.microId,
			position : Csts.position,
			duration : 10000,
			isPlayEnd : Csts.isPlayEnd
		}
		
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
		
		var doSubmitWork = function () {
			if (datas.isPlayEnd == false) {
				datas.isPlayEnd = true;
				datas.position = 0;
				$.post(postUrl, datas,function(){
					if(Csts.exerciseId != undefined || Csts.nextMicroId != undefined){
						var btns = [];
						if(Csts.exerciseId != null){
							btns.push({
								className: 'btn-green',
								text: '去练习',
								cb: function() {
									$.ajax({url:ctx + '/auth/common/holiday/generateExerciseForMicro.htm',
										async:false,
										data:{subjectName:Csts.subjectName,subjectId:Csts.subjectId, exerciseId:Csts.exerciseId,knowledgeId:Csts.knowledgeId,knowledgeName:Csts.knowledgeName},
										success:function(data){
											if(data.datas.exerciseId!=null){
												location.href = '/auth/student/exercise/doExerciseWork.htm?id='+Csts.exerciseId;
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
									location.href = '/auth/student/preview/viewMicro.htm?id='+Csts.holidayId+'&microcourseId='+Csts.microId;
								}
							});
						}
						if(Leke.user.currentRoleId == 100) {
							Dialog.open({
								title: '提示',
								tpl: '<div class="confirm-msg">本次微课已学习完</div>',
								size: 'mini',
								btns: btns
							});
						}
					}
				});
			}
		}
		var doSaveWork = throttle(10 * 1000, function(datas) {
			$.post(postUrl, datas);
		});
		

		$('#jPreviewDiv').player({ 
		    id: Csts.file.id,
		    currentPosition: Csts.position, 
		    onPlaying: function(position, duration) {
				if (isNaN(duration) || duration == 0) {
					return;
				}
				datas = $.extend(datas, {
					position : parseInt(position),
					duration : parseInt(duration)
				});
				doSaveWork(datas);
		    },
		    onPlayComplete: function () {
		    	doSubmitWork();
		    }
		});
		
	});
