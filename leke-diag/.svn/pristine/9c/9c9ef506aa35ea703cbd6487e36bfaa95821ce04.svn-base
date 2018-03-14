<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>IScroll 测试</title>
<%@ include file="/pages/common/mobile-meta.jsp"%>
<style type="text/css">
* {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}

html {
  -ms-touch-action: none;
}

body,ul,li {
  padding: 0;
  margin: 0;
  border: 0;
}

body {
  font-size: 12px;
  font-family: ubuntu, helvetica, arial;
  overflow: hidden; /* this is important to prevent the whole page to bounce */
}

#header, #footer {
  position: absolute;
  left: 0;
  z-index: 2;

  width: 100%;
  height: 45px;

  padding: 0;

  line-height: 45px;
  color: #eee;
  font-size: 20px;
  text-align: center;
  font-weight: bold;
}

#header {
  top: 0;
  background: #CD235C;
  border-bottom: 1px solid #444;
}

#footer {
  bottom: 0;
  background: #444;
  border-top: 1px solid #444;
}

#wrapper {
  left: 0;
  width: 100%;
  background: #ccc;
  /**
  position: absolute;
  z-index: 1;
  top: 45px;
  bottom: 45px;
  overflow: hidden;
  **/
}

#scroller {
  position: absolute;
  z-index: 1;
  -webkit-tap-highlight-color: rgba(0,0,0,0);
  width: 100%;
  -webkit-transform: translateZ(0);
  -moz-transform: translateZ(0);
  -ms-transform: translateZ(0);
  -o-transform: translateZ(0);
  transform: translateZ(0);
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  -webkit-text-size-adjust: none;
  -moz-text-size-adjust: none;
  -ms-text-size-adjust: none;
  -o-text-size-adjust: none;
  text-size-adjust: none;
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
  width: 100%;
  text-align: left;
}

li {
  padding: 0 10px;
  height: 40px;
  line-height: 40px;
  border-bottom: 1px solid #ccc;
  border-top: 1px solid #fff;
  background-color: #fafafa;
  font-size: 14px;

}

.beyond {
  float: right;
}

.buttons {
  position:absolute;
  z-index:3;
  left:10px;
  font-size: 16px;
}

</style>
</head>
<body>
	<div id="app"></div>
	<script type="text/javascript" src="${assets}/scripts/common/mobile/react.min.js"></script>
	<script type="text/javascript" src="${assets}/scripts/common/mobile/react-dom.min.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/m/diag/report/testing-iscroll.bundle.js"></script>
</body>
</html>