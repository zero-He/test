define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = require('utils');
	require('homework/activities/radialIndicator');
	//倒计时
	var starttime = new Date("2017/10/07 23:59:59");
	if(remove_ul){
		$("#j_ul").remove();
	}
  	setInterval(function () {
	    var nowtime = new Date();
	    var time = starttime - nowtime;
	    var day = parseInt(time / 1000 / 60 / 60 / 24);
	    var hour = parseInt(time / 1000 / 60 / 60 % 24);
	    var minute = parseInt(time / 1000 / 60 % 60);
	    var seconds = parseInt(time / 1000 % 60);
	    if (hour<10) {
	    	hour="0"+hour;
	    }
	    if (minute<10) {
	    	minute="0"+minute;
	    }
	    if (seconds<10) {
	    	seconds="0"+seconds;
	    }
	    hour = hour.toString();
	    minute = minute.toString();
	    seconds = seconds.toString();
	    var hourarr = hour.split("");
	    var minutearr = minute.split("");
	    var secondsrr = seconds.split("");
	    $('#countdown').html("<span class='time'>" + day + "</span>天<span class='time'>" + hour[0] + "</span><span class='time'>" + hour[1] + "</span>小时<span class='time'>" + minute[0] + "</span><span class='time'>"+minute[1]+"</span>分钟<span class='time c-nation__countdown-second'>" + seconds[0] + "</span><span class='time c-nation__countdown-second'>"+seconds[1]+"</span>秒");
  	}, 1000);

  	//已订正进度条
	$('#indicatorContainer').radialIndicator({
		barColor: '#23b3a9',
    	displayNumber: false,
    	//进度条圆角
    	roundCorner: true,
		barWidth: 7,
	    radius: 105,
	    minValue: 0,
	    maxValue: $("#j_m_1_d").data("i")*100
	});
	var radialObj = $('#indicatorContainer').data('radialIndicator');
	if(radialObj){
		radialObj.animate($("#j_m_1_d").text()*100);
	}
	
	$.fn.isOnScreen = function(){
	     console.log("aaaaaaaaa");
	    var win = $(window);
	     
	    var viewport = {
	        top : win.scrollTop(),
	        left : win.scrollLeft()
	    };
	    viewport.right = viewport.left + win.width();
	    viewport.bottom = viewport.top + win.height();
	     
	    var bounds = this.offset();
	    bounds.right = bounds.left + this.outerWidth();
	    bounds.bottom = bounds.top + this.outerHeight();
	     
	    return (!(viewport.right < bounds.left || viewport.left > bounds.right || viewport.bottom < bounds.top || viewport.top > bounds.bottom));
	     
	};
	
	$("#jM_1_Link").click(function(){
		var i = $(this).data("i");
		if(i==1){
			if(window.LeKeBridge){
				LeTalkCorePlugin.close()
			}else{
				window.open("https://homework.leke.cn/auth/student/exercise/homework/myHomework.htm?ord=2");
			}
		}else if(i==2){
			$(this).unbind();
			acceptPrize("jMission_2");
		}
	});
	$("#jM_2_Link").click(function(){
		var i = $(this).data("i");
		if(i==1){
			if(window.LeKeBridge){
				LeTalkCorePlugin.close()
			}else{
				window.open("https://homework.leke.cn/auth/student/exercise/homework/myHomework.htm?ord=2");
			}
		}else if(i==2){
			$(this).unbind();
			acceptPrize("jMission_3");
		}
	});
	$("#jM_3_Link").click(function(){
		var i = $(this).data("i");
		if(i==1){
			if(window.LeKeBridge){
				LeTalkCorePlugin.close()
			}else{
				window.open("https://homework.leke.cn/auth/student/exercise/list.htm");
			}
		}else if(i==2){
			$(this).unbind();
			acceptPrize("jMission_3");
		}
	});
	
	function acceptPrize(id){
		$.post("acceptPrize.htm",function(data){
			if(data.success){
				if(data.datas.lekeVal>0){
					utils.Notice.alert("恭喜你完成任务，获得乐豆"+data.datas.lekeVal);
				}
				setTimeout(function(){
					window.location.href = window.location.href.split(".htm")[0]+".htm"+"?i="+Math.random()+"#"+id;
				}, 2000)
			}
		});
	}
})