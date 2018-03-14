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
					+ '&homeworkDtld=' + Csts.homeworkDtlId + '&isMicro=0';
		} else if (isiOS) {
			//ios
			var param={'cwId':Csts.micId,'cwType':3,'homeworkId': Csts.homeworkId,'homeworkDtld':Csts.homeworkDtlId,'isMicro':0};
			var paramJson = encodeURIComponent(JSON.stringify(param));
			window.webkit.messageHandlers.playMicroClass.postMessage(paramJson);
		}
	}
	require('common/ui/ui-fileplayer/ui-fileplayer.js');
	
	$('#jPreviewDiv').player({ 
	    id: Csts.file.id
	});
	
});

