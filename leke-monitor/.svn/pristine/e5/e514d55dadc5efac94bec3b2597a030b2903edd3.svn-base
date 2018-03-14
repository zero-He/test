<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>全网备课数据统计- 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/crm/ruzhu.css" type="text/css">
<style>
	.m-table th{white-space:nowrap;}
</style>
</head>

<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">

		<div class="z-statisticalcenter">
			<div class="m-search-box f-pt15">
				<label for class="title">统计时间：</label>
				<input type="text" id="startDay" name="startDay" class="Wdate u-ipt u-ipt-nm" />
				<label for="" class="title f-ml5 f-mr5">至</label>
				<input type="text" id="endDay" name="endDay" class="Wdate u-ipt u-ipt-nm" />
				<div class="operation">
					<input type="button" id="btn_search" class="u-btn u-btn-nm u-btn-bg-turquoise f-mr10" value="查询" data-bind="click:loaddata">
					<input type="button" class="f-fr u-btn u-btn-nm u-btn-bg-orange" value="导出" data-bind="click:exportExcel">
				</div>
			</div>
			<div class="f-mt20 bgwhite">
				<h3 class="tabtitle">
					全网备课数据统计<i class="queries"
						title="1.上传：资源来源不属于他人资源库，且该资源原创者属于自己，推送备课的。&#13;
2.引用：资源来源属于他人资源库，且该资源原创者属于他人，推送备课的。&#13;
3.编辑引用：资源来源属于他人资源库，自己进行过二次编辑。该资源原创属于他人，且推送备课的。&#13;
4.占比：（引用+编辑引用）/（上传+引用+编辑引用），取百分数&#13;
5.课件绑定率：绑定课件的课堂数/备课课堂数&#13;
6.作业绑定率：绑定作业课堂数/备课课堂数&#13;
7.课件，作业，微课根据备课类型分别以“课前”，“课中”，“课后”备课资源统计。统计单位：个数
"></i>
					<div class="f-fr m-btns f-mt5 f-mr10">
						<div class="init-btn">
							<a href="javascript:void(0);">课前</a><b><i></i></b>
						</div>
						<menu style="">
							<li class="init-menu0">
								<a href="javascript:void(0);" data-bind="click:changeSel.bind($data,1)">课前</a>
							</li>
							<li class="init-menu1">
								<a href="javascript:void(0);" data-bind="click:changeSel.bind($data,2)">课中</a>
							</li>
							<li class="init-menu2">
								<a href="javascript:void(0);" data-bind="click:changeSel.bind($data,3)">课后</a>
							</li>
						</menu>
					</div>
				</h3>
				<div class="m-table m-table-center f-mb0 classdetail">
					<table>
						<tr>
							<th width="6%">学校数</th>
							<th width="5%">结束<br>课堂数
							</th>
							<th width="5%">备课<br>课堂数
							</th>
							<th width="5%">备课率</th>
							<th width="5%">课件<br>绑定率
							</th>
							<th width="5%">作业<br>绑定率
							</th>
							<th width="17%" colspan="4">备课包</th>
							<!-- ko if: stateChange() == 1 -->
							<th width="17%" colspan="4">课件(课前)</th>
							<th width="17%" colspan="4">作业(课前)</th>
							<th width="13%" colspan="3">微课(课前)</th>
							<!-- /ko -->
							<!-- ko if: stateChange() == 2 -->
							<th width="30%" colspan="4">课件(课中)</th>
							<th width="30%" colspan="4">作业(课中)</th>
							<!-- /ko -->
							<!-- ko if: stateChange() == 3 -->
							<th width="17%" colspan="4">作业(课后)</th>
							<th width="13%" colspan="3">微课(课后)</th>
							<!-- /ko -->
							<th width="5%">操作</th>
						</tr>
						<tr>
							<td width="6%"></td>
							<td width="5%"></td>
							<td width="5%"></td>
							<td width="5%"></td>
							<td width="5%"></td>
							<td width="5%"></td>
							<td width="4%">上传</td>
							<td width="4%">引用</td>
							<td width="4%">编辑<br>引用
							</td>
							<td width="5%">占比</td>
							<td width="4%">上传</td>
							<td width="4%">引用</td>
							<td width="4%">编辑<br>引用
							</td>
							<td width="5%">占比</td>
							<!-- ko if: stateChange() == 1 || stateChange() == 2 -->
							<td width="4%">上传</td>
							<td width="4%">引用</td>
							<td width="4%">编辑<br>引用
							</td>
							<td width="5%">占比</td>
							<!-- /ko -->
							<!-- ko if: stateChange() == 1 || stateChange() == 3 -->
							<td width="4%">上传</td>
							<td width="4%">引用</td>
							<td width="5%">占比</td>
							<!-- /ko -->
							<td width="5%"></td>
						</tr>
						<tbody id="papersPage" data-bind="foreach:listdata">
							<tr>
								<td><em data-bind="text:schoolNums"></em></td>
								<td><em data-bind="text:mustLessons"></em></td>
								<td><em data-bind="text:beikeLessons"></em></td>
								<td><em data-bind="text:beikeRate"></em></td>
								<td><em data-bind="text:coursewareRate"></em></td>
								<td><em data-bind="text:homeWorkRate"></em></td>
								<td><em data-bind="text:bkgUpNums"></em></td>
								<td><em data-bind="text:bkgQuoteNums"></em></td>
								<td><em data-bind="text:bkgEditNums"></em></td>
								<td><em data-bind="text:bkgRate"></em></td>
								<!-- ko if: $parent.stateChange() == 1 -->
								<td><em data-bind="text:beforeCwUpNums"></em></td>
								<td><em data-bind="text:beforeCwQuoteNums"></em></td>
								<td><em data-bind="text:beforeCwEditNums"></em></td>
								<td><em data-bind="text:beforeCwRate"></em></td>
								<td><em data-bind="text:beforeHwUpNums"></em></td>
								<td><em data-bind="text:beforeHwQuoteNums"></em></td>
								<td><em data-bind="text:beforeHwEditNums"></em></td>
								<td><em data-bind="text:beforeHwRate"></em></td>
								<td><em data-bind="text:beforeWkUpNums"></em></td>
								<td><em data-bind="text:beforeWkQuoteNums"></em></td>
								<td><em data-bind="text:beforeWkRate"></em></td>
								<!-- /ko -->
								<!-- ko if: $parent.stateChange() == 2 -->
								<td><em data-bind="text:inClassCwUpNums"></em></td>
								<td><em data-bind="text:inClassCwQuoteNums"></em></td>
								<td><em data-bind="text:inClassCwEditNums"></em></td>
								<td><em data-bind="text:inClassCwRate"></em></td>
								<td><em data-bind="text:inClassHwUpNums"></em></td>
								<td><em data-bind="text:inClassHwQuoteNums"></em></td>
								<td><em data-bind="text:inClassHwEditNums"></em></td>
								<td><em data-bind="text:inClassHwRate"></em></td>
								<!-- /ko -->
								<!-- ko if: $parent.stateChange() == 3 -->
								<td><em data-bind="text:afterHwUpNums"></em></td>
								<td><em data-bind="text:afterHwQuoteNums"></em></td>
								<td><em data-bind="text:afterHwEditNums"></em></td>
								<td><em data-bind="text:afterHwRate"></em></td>
								<td><em data-bind="text:afterWkUpNums"></em></td>
								<td><em data-bind="text:afterWkQuoteNums"></em></td>
								<td><em data-bind="text:afterWkRate"></em></td>
								<!-- /ko -->
								<td><a class="f-csp s-c-turquoise" href="javascript:void(0);" data-bind="click:$parent.opendetail">详情</a></td>

							</tr>
						</tbody>
					</table>
					<!-- ko if: listdata().length == 0 -->
					<div class="eval-rate-pictab m-tips f-block">
						<i></i> <span>对不起，没有您要查询的数据</span>
					</div>
					<!-- /ko -->
				</div>
			</div>
		</div>

	</div>
</div>

<script>
	seajs.use('monitor/pages/common/beike/beikeStatisView');
</script>

</body>
</html>