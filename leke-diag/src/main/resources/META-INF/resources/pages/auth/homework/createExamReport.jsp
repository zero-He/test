<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${assets}/styles/common/global.css">
    <title>创建成绩单</title>
    <%@ include file="/pages/common/meta.jsp"%>
  </head>
  <body>
  <%@ include file="/pages/header/header.jsp"%>
    <div class="m-bg"></div>
    <div class="g-bd">
      <%@ include file="/pages/navigate/navigate.jsp"%>

      <div class="g-mn">
        <div class="m-form">
          <form id="form" method="post" enctype="multipart/form-data">
            <ul>
              <li class="form-item">
                <label for="" class="title"><span class="require">*</span>成绩单名称：</label>
                <div class="con">
                  <input type="text" class="u-ipt u-ipt-lg" id="examReportName" name="examReportName" maxlength="20">
                </div>
              </li>
              <li class="form-item">
                <label for="" class="title">寄语：</label>
                <div class="con">
                  <textarea rows="3" cols="80" id="greatings" name="greatings" maxlength="200" placeholder="经过一个学期的辛勤努力，该是收获成绩的时候啦！"></textarea>
                </div>
              </li>
              <li class="form-item">
                <label for="" class="title"><span class="require">*</span>上传成绩单：</label>
                <div class="con">
					<button id="jUpload">Upload</button>
					<span id="progressExcelUpload">
	                    <a href="/auth/teacher/examReport/downloadTemplate.htm" class="s-c-turquoise f-ml50">下载模板</a>
					</span>
					<input type="hidden" name="filePath" id="filePath">
                </div>
              </li>
            </ul>
            <br/>
            <div class="f-tac f-mt50">
            	<button type="button" id="add" name="add" class="u-btn u-btn-nm u-btn-bg-turquoise">创建</button>
          	</div>
          </form>
          
        </div>
      </div>
    </div>
    <script>
		seajs.use('diag/homework/createExamReport');
	</script>
  </body>
</html>
