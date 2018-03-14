define(function(require, exports, module) {
	var $ = require('jquery');
	
	//判断是否是新录制微课
	if(Csts.isNewVod) {
		var u = navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; // android终端
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
		if (isAndroid) {
			// andorid
			location.href = 'cmake://demoPlayer/param?cwId=' + Csts.micId
					+ '&cwType=3&homeworkId=' + Csts.homeworkId
					+ '&homeworkDtld=' + Csts.homeworkDtlId + '&isMicro=1';
		} else if (isiOS) {
			//ios
			var param={'cwId':Csts.micId,'cwType':3,'homeworkId': Csts.homeworkId,'homeworkDtld':Csts.homeworkDtlId,'isMicro':1};
			var paramJson = encodeURIComponent(JSON.stringify(param));
			window.webkit.messageHandlers.playMicroClass.postMessage(paramJson);
		}
	}
	
	
	
	require('common/ui/ui-fileplayer/ui-fileplayer.js');
     
	var datas = {
		homeworkId : Csts.homeworkId,
		homeworkDtlId : Csts.homeworkDtlId,
		position : Csts.position,
		duration : -1,
		isPlayEnd : false
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
    var def_delay = 0 ;
    var doSaveData;
	$('#jPreviewDiv').player({ 
	    id: Csts.file.id,
	    currentPosition: Csts.position, 
	    onFetchComplete: function (fileInfo) {
	    	if ('audio/video'.indexOf(fileInfo.file.type) > -1) {
	    		def_delay = 5000;
	    	}
	    	doSaveData = throttle(def_delay, function(datas){
		    	$.post('saveWork2.htm', datas);
		    })
	    },
	    onPlaying: function(position, duration) {
			position = parseInt(position);
			duration = parseInt(duration);
			if (isNaN(duration) || duration == 0) {
				return;
			}
			if (position >= duration) {
				position = 0;
			}
			if(position != datas.position) {
				datas = $.extend(datas, {
					position : position,
					duration : duration
				});
				doSaveData(datas);
			}
	    },
	    onPlayComplete: function () {
	    	if (datas.isPlayEnd == false) {
				datas.isPlayEnd = true;
				$.post('submitWork2.htm', datas);
			}
	    }
	});
	
});
