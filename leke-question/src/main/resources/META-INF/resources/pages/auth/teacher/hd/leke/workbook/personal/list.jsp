<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>我的习题册 </title>
<leke:context />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">


<link rel="stylesheet" href="${assets}/styles/mobile/global.css">
<link rel="stylesheet" href="${assets}/styles/mobile/resource-pad.css">
<script src="${assets}/scripts/common/mobile/common.js?_t=${_t}"></script>

    <script src="${assets}/scripts/common/mobile/zepto.min.js"></script>
    <script src="${assets}/scripts/common/knockout/knockout-3.4.0.js"></script>
    <script src="${assets}/scripts/common/mobile/common.js"></script>
    <script src="/scripts/repository/filter/pad/repo-filters.min.js"></script>
    <script type="text/javascript" src="/scripts/repository/service/HistoryServicePad.js"></script>
</head>

<body>
	<div class="main">
		<div class="z-resource">
			<div class="menu">
				<div id="divChoseMenu" class="menu"></div>
			</div>
			<form action="" method="post" id="Jform">
				<div class="c-condition">
					<div class="condition">
						<div class="resouceselect">
							<div class="text">我的收藏</div>
							<div class="c-droplist" style="display: none;">
								<ul>
									<li data-id="4" data-type="sc" class="item cur">我的收藏</li>
								</ul>
							</div>
						</div>
						<div class="searchcon">
							<div id="search" class="search" style="display:none">
								<div class="c-search">
									<input type="text" placeholder="请输入名称或内容" name="content">
									<span class="del mydel">×</span> <span class="searchbtn myserch"> <i></i></span>
								</div>
								<em class="cancel celbtn">取消</em>
							</div>

						</div>
						<div class="setup">
							 <span class="searchico cxbtn"></span>
                             <span class="search sx"></span> 

							
						<!-- 	<span class="sort">
								<div class="c-droplist" style="display: none;">
									<ul>
										<li data-id="1" class="item cur">最新</li>
										<li data-id="3" class="item">点赞最多</li>
										<li data-id="4" class="item">收藏最多</li>
									</ul>
								</div>
							</span> -->
						</div>
					</div>
			    <input type="hidden" name ="schoolStageId" value ="">
                <input type="hidden" name ="subjectId" value ="">
                <input type="hidden" name ="pressId" value ="">
                <input type="hidden" name ="materialNodeId" value ="">
                <input type="hidden" name ="materialId" value ="">
                <input type="hidden" name ="materialNodePath" value ="">
                 
					<input type="hidden" name="shareScope" value="4">

                   <div class="conditionlist">
                   
                </div>

				</div>
			</form>
			<div id="Jpage" class="resourcecon c-exercisebook conheight">
				<ul id="Jdetails">

				</ul>
			</div>
		</div>
	</div>
</body>
<textarea style="display: none" id="JdetailsTpl">


{{#dataList}}
           <li class="item">
                     <div class="img mx" data-id="{{workbookId}}">
							<img src="{{photoUrl}}" alt="{{workbookName}}">
					</div>
                     <div class="info">
                         <div class="title mx" data-id="{{workbookId}}">{{content}}</div>
                         <div class="operation" data-id="{{workbookId}}">
							<span class="collect sc"><i></i>取消收藏<em></em></span>
							 <span	class=""><i></i><em></em></span>
						</div>
                         <div class="attr"> <span>分享者：<em>{{creatorName}}</em></span> <span
							class="date">{{createdOnStr}}</span>
						</div>
                     </div>
                 </li>
   {{/dataList}}              
</textarea>
<script src="/scripts/question/pages/teacher/hd/leke/workbook/personal/list.js"></script>
	<script type="text/javascript" src="/scripts/repository/service/RepoCst-pad.js"></script>

</html>
