define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = require('utils');
	var Service = require('../../service/knowledgeService');
	var Mustache = require('mustache');
	var Widget = require('common/base/widget');
	var StageSelect = require('question/widgets/stageSelect');
	var SubjectSelect = require('question/widgets/subjectSelect');
	
	var KLView = Widget.extend({
		tpl: '.detail',
		init: function() {
			var self = this;
			var $f = self.$('#formSearch');
			self._stage = new StageSelect({
				tpl: $f.find('[name=schoolStageId]')
			});
			self._subject = new SubjectSelect({
				tpl: $f.find('[name=subjectId]')
			});
			self.$tb = self.$('#tbRoots');
			self._rtpl = $('#row_tpl').val();
			
			self._subject.attrLink(self._stage, ['schoolStageId']);
			self.doSearch();
		},
		events: {
			'click .btnSearch': 'doSearch',
			'click .btnAdd': 'doAdd',
			'click .btnDel': 'doDel'
		},
		doSearch: function() {
			var self = this;
			self.$tb.empty();
			var params = $.extend({}, self._stage.attrs(), self._subject.attrs());
			Service.getRoots(params).done(function(datas) {
				var contents = Mustache.render(self._rtpl, datas);
				self.$tb.html(contents);
			});
		},
		doAdd: function() {
			var self = this;
			var params = $.extend({}, self._stage.attrs(), self._subject.attrs());
			var stageId = params.schoolStageId;
			if(!stageId || stageId === '') {
				utils.Notice.alert('请选择学段！');
				return false;
			}
			var subjectId = params.subjectId;
			if(!subjectId || subjectId === '') {
				utils.Notice.alert('请选择学科！');
				return false;
			}
			var name = '' + params.schoolStageName + params.subjectName;
			Service.addRoot({
				schoolStageId: stageId,
				subjectId: subjectId,
				knowledgeName: name
			}).then(function() {
				utils.Notice.alert('根知识点添加成功！');
				self.doSearch();
			}, function(msg) {
				utils.Notice.alert(msg || '根知识点添加失败！');
			});
		},
		doDel: function(evt) {
			var self = this;
			var kid = $(evt.target).data('knowledge-id');
			Service.del({
				knowledgeId: kid
			}).then(function() {
				utils.Notice.alert('根知识点删除成功！');
				self.doSearch();
			}, function(msg) {
				utils.Notice.alert(msg || '根知识点删除失败！');
			});
		}
	});
	
	new KLView();
});