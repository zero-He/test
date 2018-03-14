<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<div class="c-resource-list">
		<div class="m-table">
			<table class="c-exercisestable"><tbody>
				<tr>
					<th width="20px"><span class="checkbox"><input type="checkbox" class="selectedAll" id="selectedAll"></span></th>
					<th colspan="2">
						<div class="f-fr f-mr5">
							<input type="button" class="c-resouce-btnhollow j-add-batch-quecart" value="加入习题篮">
							<input type="button" class="c-resouce-btnhollow batchMove" value="移动">
							<c:if test="${userResGroupId != null}">
								<input type="button" class="c-resouce-btnhollow batchRemove" value="移出分组">
							</c:if>
							<c:if test="${shareScope == 4}">
								<input type="button" class="c-resouce-btnhollow batchUnfavorite" value="取消收藏">
							</c:if>
						</div>
					</th>
				</tr>
									
				<c:forEach items="${records}" var="record" varStatus="recordStat">
					<tr class="item" data-question-id="${record.resId}" data-question-type-id="${record.questionTypeId}"  data-share-school="" data-share-platform="">
						<td>
                			<span class="checkbox"> <input type="checkbox" name="resId" value="${record.resId}"></span>
                		</td>
						<td>
						    <i class="c-restype question"></i>
							<div class="item maxwidthexercises">
								<leke:question questionId="${record.resId}" showCheckbox="false" showSequence="false" 
										showExplain="false" showProperty="false" showPropertyEdit="false" showRightAnswer="false"/>
	                            <div class="remarks">
		                            <span>${record.createdOnStr }</span>
	                           </div>
	                           <div class="remarks" >
	                    		<span>纠错状态：${record.source} </span>
	                        	</div>
							</div>
						</td>
						<td width="260">
							<div class="c-maxoperationlistbtn">
                           		<!-- <i class="edit j-edit" title="编辑" ></i>
	                           	<i class="exercises j-add-quecart" title="加入习题篮"></i> -->
	                           	
                           		<a  target="_blank" href="${ctx}/auth/common/question/view.htm?questionId=${record.resId}&curScope=${shareScope}&curResGrpId=${userResGroupId}" title="查看">
	                           		<i class="see"></i>
	                           	</a>
	                           	<c:if test="${shareScope == 4}">
								<a class="j-unfavorite">取消收藏</a>
								</c:if>
								<div class="m-btns c-btns f-fr f-mt11">
									<div class="init-btn">
										<a href="#">更多</a><b><i></i></b>
									</div>
									<menu>
										<li><a href="#" class="j-move">移动</a></li>
										<c:if test="${not empty userResGroupId}">
											<li title="移出分组"><a href="#" class="j-moveOut">移出分组</a></li>
										</c:if>	
										<c:if test="${shareScope  == 1 }">
											<li><a class="j-disable">删除</a></li>
										</c:if>
										<c:if test="${shareScope  == 1 && currentRoleId == 101}">
											<li><a class="j-share">分享</a></li>
										</c:if>
									</menu>
								</div>
	                        </div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<%@ include file="/pages/common/quePage.jsp"%>
</div>
<c:if test="${empty records}">
	<p class="m-tips"><i></i><span>当前没有教学资源！</span></p>
</c:if>