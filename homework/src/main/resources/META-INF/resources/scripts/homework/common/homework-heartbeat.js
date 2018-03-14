define(function(require, exports, module) {
	var $ = require('jquery');
	var Leke = window.Leke;
	if(!Leke || !Leke.user || !Leke.user.userId || Leke.user.currentRoleId != 100) {
		return;
	}
	
	function log(ipt) {
		if(window.console) {
			console.log(ipt);
		}
	}
	var homeworkId = $('#homeworkId').val();
	var HB_URL = Leke.domain.homeworkServerName + '/auth/common/homework/heartbeat.htm?homeworkId=' + homeworkId + '&callback=?';
	var DELAY = 30000; // 三分钟
	
	function sendHeartBeat() {
		$.getJSON(HB_URL, function() {
			log('student homework heartbeat at ' + new Date().getTime());
		});
	}
	
	var timer = null;
	
	function heartbeat() {
		if(timer) {
			clearTimeout(timer);
			timer = null;
		}
		
		sendHeartBeat();
		
		timer = setTimeout(heartbeat, DELAY);
	}
	
	heartbeat();
});