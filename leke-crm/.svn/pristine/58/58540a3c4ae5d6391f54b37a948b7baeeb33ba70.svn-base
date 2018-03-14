<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>法定节假日设置</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/common/global.css">

</head> 
<body>
 	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<div class="m-search-box">
				<select id="selYear" class="u-select u-select-nm j-curpanelyear">
				</select>
				<select id="selMonth" class="u-select u-select-nm j-curpanelmonth">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
				</select>
				<input id="jReturnNow" type="button" value="返回当月" class="u-btn u-btn-at u-btn-bg-turquoise">				
			</div>
			
			<!-- Calendar -->
			<div id="jCalendar" class="m-calendar"></div>
			
			<div class="f-tac f-pt25">
				<input id="jSave" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="保存" />
				<a id="jCancel" href="${ctx}/auth/scs/specialDate/specialDateSetting.htm" class="u-btn u-btn-nm u-btn-bg-gray" type="button" >取消</a>
			</div>
    
		</div>
	</div>
		
	
	
	
	<script type="text/javascript">
		seajs.use('scs/specialDate/specialDateSetting');
	</script>
</body>
</html>
