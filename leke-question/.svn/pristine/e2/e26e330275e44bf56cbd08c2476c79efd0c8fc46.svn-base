define(function(require, exports, module) {
	var _ = require('common/base/helper');
	var QueTypeSelect = require('question/widgets/queTypeSelect');
	var DiffLevelSelect = require('question/widgets/diffLevelSelect');
	var TagSelect = require('question/widgets/tagSelect');
	
	var Pages = require('common/base/pages');
	
	require('core-business/widget/jquery.leke.matNodeSelect');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var Bar = {
		init: function() {
			var self = this;
			var param = self.attrs();
			
			var $ss = self.$('[name=stageSubject]');
			if($ss.length) {
				$ss.stgGrdSbjSelect({
					type: 'stg_sbj',
					onChange: function(item) {
						self.attr('schoolStageId', item.schoolStageId);
						self.attr('subjectId', item.subjectId);
					}
				});
			}
			
			var $sct = self.$('[name=section]');
			if($sct.length) {
				$sct.on('click', function() {
					var data = self.attrs({
						keys: ['schoolStageId', 'subjectId']
					});
					$sct.matNodeSelect({
						type: 'section',
						data: data,
						multi: false,
						onSelect: function(section) {
							self.attr('materialNodeId', section.materialNodeId);
							$sct.val(section.path);
						}
					});
				});
				self.$el.on('click', '.j-btn-del-section', function() {
					self.attr('materialNodeId', null);
					$sct.val('');
				});
			}
			
			var $type = self.$('[name=questionTypeId]');
			if($type.length) {
				var _type = new QueTypeSelect({
					data: param,
					tpl: $type
				});
				_type.attrLink(self, ['subjectId']);
				self.attrLink(_type, ['questionTypeId']);
			}
			
			var $diff = self.$('[name=diffLevel]');
			if($diff.length) {
				var _diff = new DiffLevelSelect({
					data: param,
					tpl: $diff
				});
				self.attrLink(_diff, ['diffLevel']);
			}
			
			var $tag = self.$('[name=officialTagId]');
			if($tag.length) {
				var _tag = new TagSelect({
					data: param,
					tpl: $tag
				});
				self.attrLink(_tag, ['officialTagId']);
			}
			
			var $qid = self.$('[name=questionId]');
			if($qid.length) {
				self.attrBind($qid);
			}
			
			var $cont = self.$('[name=content]');
			if($cont.length) {
				self.attrBind($cont);
			}
			
			Pages.bind(self.$el, {
				ns: '.quePage',
				load: function(page) {
					self.attr('curPage', page);
					self.load();
				}
			});
		}
	};
	
	module.exports = Bar;
});