<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>寒暑假作业管理 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${assets}/styles/pay/common/new.css" />
<link rel="stylesheet" type="text/css"
	href="${assets}/styles/union/union.css">
<link type="text/css" rel="stylesheet"
	href="${assets}/styles/pay/common/layout.css?t=${_t}">
<style type="text/css">

.m-form ul .form-item .title{
width:210px;
}
.m-form .submit{
padding-left:180px;
}

</style>

</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<div class="z-pay-coupon">

				<div class="m-form">
					<form>
						<ul>
							<li class="form-item "><label for="" class="title"><span
									class="require">*</span>年份：</label>
								<div class="con">
									<input value="" id="year" name="year" data-des="年份" readonly="readonly" class="cknull"
										type="text">
								</div></li>
							<li class="form-item "><label for="" class="title"><span
									class="require">*</span>类型：</label>
								<div class="con">
									<select class="u-select u-select-mn j-select-condition" data-des="类型" class="cknull"
										id="type" name="type">
										<option value="1">寒假</option>
										<option value="2">暑假</option>
									</select>
								</div></li>
							<li class="form-item"><label for="" class="title"><span
									class="require">*</span>寒/暑假作业截止时间：</label>
								<div class="con">
									<!-- <input value="" id="work_begintime" name="work_begintime" data-des="寒/暑假作业的开始时间" class="cknull"
										readonly="readonly" type="text" class="f-mr5"> ~  --><input value="" id="work_endtime" name="work_endtime" data-des="寒/暑假作业的结束时间" class="cknull"
										readonly="readonly" type="text" class="f-mr5">
								</div></li>

							<li class="form-item"><label for="" class="title"><span
									class="require">*</span>寒/暑假作业推送周期开始时间：</label>
								<div class="con">
									<input value="" id="cycle_begintime" name="cycle_begintime" data-des="寒/暑假作业推送周期的开始时间" class="cknull"
										readonly="readonly" type="text" class="f-mr5"> 
								</div></li>
							
							<li class="form-item"><label for="" class="title"><span
									class="require">*</span>寒/暑假作业推送周期结束时间：</label>
								<div class="con">
									<input data-des="寒/暑假作业推送周期的结束时间" class="cknull"
										value="" id="cycle_endtime" name="cycle_endtime"
										readonly="readonly" type="text" class="f-mr5">
								</div></li>	
						</ul>
						<div class="submit">
							<input value="确定" id="add"
								class="u-btn u-btn-nm u-btn-bg-turquoise" type="button">
							<input value="返回" id="rtn" class="u-btn u-btn-nm u-btn-bg-gray"
								type="button">
						</div>

					</form>
				</div>
			</div>

		</div>
	</div>


	<script>
	seajs.use("homework/homework/platformAdmin/AddHomeworkConfig");
</script>

</body>
</html>