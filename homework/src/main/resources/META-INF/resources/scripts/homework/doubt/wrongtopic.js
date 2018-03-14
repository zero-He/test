define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require('common/handlebars');
	var tmp = ['<div class="f-clearfix" style="margin-left:-8px">',
					'<span class="title f-fl f-w60 f-fs14">【答题记录】</span>',
					'<div class="f-bfc f-fs14 f-lwb">',
						'<div class="detail">',
							'<label for="" class="answer">回答：</label>',
							'<div class="con">{{answerContent}}</div>',
						'</div>',
						'<div class="tips">',
							'<em>题目来源：{{sourceName}}</em> <em>答题时间：{{fmt createdOn "yyyy-MM-dd HH:mm:ss"}}</em><p></p>',
						'</div>',
					'</div>',
				'</div>'].join("");
	Handlebars.handlers["tmp"] = Handlebars.compile(tmp);
	function getWrongTopic(questionId,createdBy,replaceElement){
		if(questionId&&createdBy){
			$.get(Leke.domain.wrongtopicServerName+'/auth/common/wrong/getWrongLog.htm?jsoncallback=?&questionId='+questionId+'&createdBy='+createdBy,function(json){
				console.log(json);
				if(json.success&&json.datas&&json.datas.wrongLog){
					$(replaceElement).replaceWith(Handlebars.render('tmp', json.datas.wrongLog));
				}
			});
		}
	}
	module.exports = getWrongTopic;
});