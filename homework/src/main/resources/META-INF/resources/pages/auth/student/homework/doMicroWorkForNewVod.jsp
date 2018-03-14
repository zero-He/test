<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/pages/common/meta.jsp"%>
<meta name="google" value="notranslate" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>做作业-乐课网</title>
    <style type="text/css" media="screen"> 
        html, body  { height:100%; }
        body { margin:0; padding:0; overflow:auto; text-align:center; 
               background-color: #ffffff; }   
        object:focus { outline:none; }
        #flashContent { display:none; }
    </style>
</head>
<body>
       
        <div id="flashContent">
        	<p>
                To view this page ensure that Adobe Flash Player version 
                0.0.0 or greater is installed. 
            </p>
           
        </div>
        
        
        <noscript>
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="StrongClassPlayer">
				<param name="movie" value="${assets}/flashs/common/ui/ui-videoclass/cmaking/StrongClassPlayer.swf?0801" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="always" />
                <param name="allowFullScreen" value="true" />
                <param name="allowFullScreenInteractive" value="true" />
                <!--[if !IE]>-->
                <object type="application/x-shockwave-flash" data="StrongClassPlayer.swf" width="100%" height="100%">
                    <param name="quality" value="high" />
                    <param name="bgcolor" value="#ffffff" />
                    <param name="allowScriptAccess" value="always" />
                    <param name="allowFullScreen" value="true" />
                    <param name="allowFullScreenInteractive" value="true" />
                <!--<![endif]-->
                <!--[if gte IE 6]>-->
                    <p> 
                        Either scripts and active content are not permitted to run or Adobe Flash Player version
                        0.0.0 or greater is not installed.
                    </p>
                <!--<![endif]-->
                   
                <!--[if !IE]>-->
                </object>
                <!--<![endif]-->
            </object>
        </noscript>     
  
	<script type="text/javascript">
		window.Csts = ${Csts};
		var datas = {
	    		homeworkId : Csts.homeworkId,
	    		homeworkDtlId : Csts.homeworkDtlId,
	    		position : Csts.position,
	    		duration : -1,
	    		isPlayEnd : Csts.status > 0
	    }
	
		seajs.use(['beike/microcourse/FlashRecord','jquery'],function(FlashView,$) {
			var flashvars = {};
            var attributes = {};
            var id = "flashContent";
            
            flashvars.cwId = '${microcourse.microcourseId}';
            flashvars.cwName = '${microcourse.microcourseName}';
            flashvars.ticket = '${ticket}';
            flashvars.fserver = '${initParam.fileServerName}';
            flashvars.userId = Leke.user.userId;
            flashvars.userName = Leke.user.currentUserName;
            flashvars.schoolId = Leke.user.currentSchoolId;
            flashvars.roleId = Leke.user.currentRoleId;
            flashvars.invoke = Leke.domain.beikeServerName+"/api/w/beike/invoke.htm";
            flashvars.isMicro = 1;
            flashvars.flag = 0;
            flashvars.demo = 0;
            flashvars.drag = 0;
            flashvars.postion = datas.position;
            
            attributes.id = "MicrocoursePlayer";
            attributes.name = "MicrocoursePlayer";
            
            var swfUrl = Leke.domain.staticServerName + '/flashs/common/ui/ui-videoclass/cmaking/StrongClassPlayer.swf';
           
            if(!datas.isPlayEnd) {
	            var play_callBack = function(currentDuration,totalDuration) {
	            	datas.position = currentDuration;
	            	datas.duration = totalDuration;
	            	if(!datas.isPlayEnd && currentDuration == totalDuration) {
	            		datas.isPlayEnd = true;
	          			$.post('submitWork2.htm', datas);
	            	}
	            }
	            setInterval(function() {
	            	if(datas.isPlayEnd){
	            		return;
	            	}
	          		if(datas.position == datas.duration) {
	          			datas.isPlayEnd = true;
	          			$.post('submitWork2.htm', datas);
	          		} else {
	          			$.post('saveWork2.htm', datas);
	          		}
	            },5000);
	            FlashView.load(swfUrl,id,attributes,flashvars,play_callBack);
            }else {
            	FlashView.load(swfUrl,id,attributes,flashvars);
            }
        });	   
        
	</script>

        
    </body>
</html>
