<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>分成规则设置 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/crm/ruzhu.css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<div class="z-rule">
				<table class="z-rule-table f-mb30">
					<tr>
						<td colspan="5" class="tit">客户经理分成规则 <span style="font-size: 14px;">
								（生效时间：
								<fmt:formatDate value="${currentCommRule.validTime}" pattern="yyyy-MM-dd" />
								~
								<fmt:formatDate value="${currentCommRule.expireTime}" pattern="yyyy-MM-dd" />
								）
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="5" class="notice">这是当前分成规则，如需更改请变更分成规则</td>
					</tr>
					<tr>
						<td class="wtp">第一年: ${currentDetail.rates[0]}%</td>
						<td class="wtp">第二年：${currentDetail.rates[1]}%</td>
						<td class="wtp">第三年：${currentDetail.rates[2]}%</td>
						<td class="wtp">第四年：${currentDetail.rates[3]}%</td>
						<td class="wtp">第五年：${currentDetail.rates[4]}%</td>
					</tr>
				</table>
				<form id="jCommRuleForm" action="pointsave.htm" method="post">
					<table class="z-rule-table">
						<tr>
							<td colspan="2" class="tit">变更分成规则</td>
						</tr>
						<tr>
							<td colspan="2" class="notice">新规则将在设置时间到达后生效</td>
						</tr>
						<tr>
							<td class="wt">第一年</td>
							<td><input type="text" id="jRate1" class="ipt" name="rates" value="${futureDetail.rates[0]}">%</td>
						</tr>
						<tr>
							<td class="wt">第二年</td>
							<td><input type="text" id="jRate2" class="ipt" name="rates" value="${futureDetail.rates[1]}">%</td>
						</tr>
						<tr>
							<td class="wt">第三年</td>
							<td><input type="text" id="jRate3" class="ipt" name="rates" value="${futureDetail.rates[2]}">%</td>
						</tr>
						<tr>
							<td class="wt">第四年</td>
							<td><input type="text" id="jRate4" class="ipt" name="rates" value="${futureDetail.rates[3]}">%</td>
						</tr>
						<tr>
							<td class="wt">第五年</td>
							<td><input type="text" id="jRate5" class="ipt" name="rates" value="${futureDetail.rates[4]}">%</td>
						</tr>
						<tr class="hover">
							<td class="wt">生效时间:</td>
							<td><input type="text" class="ipt Wdate" id="jValidTime" name="validTime"
									value="<fmt:formatDate value="${futureCommRule.validTime}" pattern="yyyy-MM-dd" />"></td>
						</tr>
					</table>
					<div class="m-btn-wrap f-mt60" style="height: 50px;">
						<div class="btn-wt">
							<div class="btn-wt2 f-clearfix f-fl">
								<input id="jBtnSave" type="submit" class="u-btn u-btn-bg-turquoise u-btn-lg f-fl f-mr20" value="保存">
							</div>
						</div>
					</div>
				</form>
			</div>

		</div>
	</div>
</body>
<c:if test="${not empty message}">
<script type="text/javascript">
	seajs.use('utils', function(Utils) {
		Utils.Notice.alert('${message}');
	});
</script>
</c:if>
<script type="text/javascript">
	seajs.use('scs/commrule/pointedit');
</script>
</html>