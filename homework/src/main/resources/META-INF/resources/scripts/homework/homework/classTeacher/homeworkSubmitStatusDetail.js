define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Page = require('page');
	var Utils = require("utils");
	var Clazzes = require('tutor/clazz/find');
	Clazzes.fInit();
	var options = {};
	options.type = $("#jClassType").val();
	Clazzes.fLoad(options);
	$("#jClassType").change(function(){
		var options = {};
		options.type = $("#jClassType").val();
		Clazzes.fLoad(options);
	});
	$("#bHomeworkList").click(function(){
		var classId = $("#jClazzId").val();
		if(!classId){
			Utils.Notice.alert('请选择班级');
			return;
		}
		var mask = Utils.Notice.mask;
		mask.create("正在查询数据请稍等");
		$.post("detail.htm",{classId:classId},function(data){
			mask.close();
			if(data.success){
				$("#jThead").html("");
				$("#jTbody").html("");
				var headers = data.datas.headers;
				var exp = data.datas["export"];
				var fieldNames = data.datas.fieldNames;
				var thStringArry = [];
				for(var i=0;i<headers.length;i++){
					thStringArry.push("<th><span title='"+headers[i]+"'>");
					thStringArry.push(headers[i]);
					thStringArry.push("</span></th>");
				}
				$("#jThead").append(thStringArry.join(""));
				var trStringArry = [];
				for(var m=0;m<exp.length;m++){
					var e = exp[m];
					trStringArry.push("<tr>");
					for(var n=0;n<fieldNames.length;n++){
						trStringArry.push("<td><span>")
						trStringArry.push(e[fieldNames[n]]);
						trStringArry.push("</span></td>");
					}
					trStringArry.push("</tr>");
				}
				$("#jTbody").html(trStringArry.join(""));
			}
		})
	});
	$("#jExportData").click(function(){
		var classId = $("#jClazzId").val();
		if(!classId){
			Utils.Notice.alert('请选择班级');
			return;
		}
		window.location.href = "export.htm?classId="+classId;
	});

});
