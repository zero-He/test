document.addEventListener("deviceready", function(){
	
	//判断是否是新录制微课
	if(Csts.isNewVod) {
		$('.j-container').addClass('c-recplay__cover');
		$('.j-container').click(function(){
			var u = navigator.userAgent;
			var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; // android终端
			var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
			if (isAndroid) {
				// andorid
				//LeTalkCorePlugin.back();
				location.href = 'cmake://demoPlayer/param?name='+ encodeURIComponent(Csts.micName) +'&cwId=' + Csts.micId
				+ '&cwType=3&homeworkId=' + Csts.homeworkId
				+ '&homeworkDtlId=' + Csts.homeworkDtlId + '&isMicro=1&position=' + Csts.position ;
			} else if (isiOS) {
				//ios
				var param = {
					'name' : encodeURIComponent(Csts.micName),
					'cwid' : Csts.micId,
					'cwtype' : 3,
					'homeworkId' : Csts.homeworkId,
					'homeworkDtlId' : Csts.homeworkDtlId,
					'isMicro' : 1,
					'position' : Csts.position
				};
				var paramJson = encodeURIComponent(JSON.stringify(param));
				//LeTalkCorePlugin.back();
				LeTalkCorePlugin.playMicroClass(paramJson);
			}
		});
		return;
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
	var datas = {
		homeworkId : Csts.homeworkId,
		homeworkDtlId : Csts.homeworkDtlId,
		position : Csts.position,
		duration : -1,
		isPlayEnd : false
	}
	
	//播放器
	var def_delay = 0;
	var doSaveWork;
	function doPlay(position, duration) {
		position = parseInt(position), duration = parseInt(duration);
		if (isNaN(duration) || duration == 0 ) {
			return;
		}
		if (position != Csts.position) {
			Csts.position = position;
			if (position == duration) {
				position = 0;
			}
			datas = $.extend(datas, {
				position : position,
				duration : duration
			});
			doSaveWork(datas);
		}
	}
	function initPlayer() {
		$('#app').player({ 
			id: Csts.file.id, 
			currentPosition: Csts.position,
			onFetchComplete: function (fileInfo) {
				if (fileInfo.file.type == 'ppt') {
					LeTalkCorePlugin.showMenu(menu_preview_data);
				}
				if ('audio/video'.indexOf(fileInfo.file.type) > -1) {
					def_delay = 5000;
				}
				doSaveWork = throttle(def_delay, function(datas) {
					$.post('/auth/m/student/homework/saveWork2.htm', datas);
				});
			},
			onPlaying:function(position, duration) {
				//播放中
				doPlay(position, duration)
			}
		});
	}
	
	function initPlayerPPT () {
		$('#app').ppt({
			   id: Csts.file.id,
			   currentPosition: Csts.position,
			   onPlaying:function(position, duration) {
			    	//播放中
			    	doPlay(position, duration);
			    }
		});
		LeTalkCorePlugin.showMenu(menu_back_data);
	}
	
	//移动端按钮交互
	var menu_preview_data = [{
	    "id": 1,
		"groupId":0,
		"groupOrder" : 0,
		"order":0,
		"icon" : "",
		"title":"播放",
		"count":0,
		"isShowNum":false
	}];
	var menu_back_data = [{
		    "id": 0,
			"groupId":0,
			"groupOrder" : 0,
			"order":0,
			"icon" : "",
			"title":"退出",
			"count":0,
			"isShowNum":false
		}];
	
	window.clickMenu = function(data) {
		if(data.id == 1) {
			initPlayerPPT();
		} else {
			$('#app').ppt('destroy');
			initPlayer();
		}
	}
	//初始化播放器
	initPlayer();

},false);