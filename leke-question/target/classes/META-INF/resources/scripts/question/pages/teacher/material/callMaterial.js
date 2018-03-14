
var DIR = Leke.ctx + '/auth/common/basicData';
var SAVE_DIR = Leke.ctx + '/auth/teacher/material/saveTeacherMaterial.htm';

function Query(params){
	var self = this;
	self.schoolStageId = params.schoolStageId;
	self.subjectId = params.subjectId;
	self.pressId = params.pressId;
	self.materialId = params.materialId;
	self.materialFileId = params.materialFileId;
};

var callMaterial = {
		init: function(){
			var self = this;
			self.bindEvent();
			self.optgroups = [];
			self._initStgSbj();
			self.query = new Query(window.materialCtx.material || {});
			self.loadMaterials();
		},
		bindEvent: function(){
			var self = this;
			$('.elect-seclect-nav').on('click','.selected-stg-sbj',function(){
				var $this = $(this);
				$('.select-stg-sbj').show();
				$('.select-press').hide();
				$('.elect-seclect-item').removeClass('elect-seclect-item-on');
				$this.addClass('elect-seclect-item-on');
				$('.elect-cover').show().css('opacity','0.6');
			});
			$('.elect-seclect-nav').on('click','.selected-press',function(){
				var $this = $(this);
				$('.select-stg-sbj').hide();
				$('.select-press').show();
				$('.elect-seclect-item').removeClass('elect-seclect-item-on');
				$this.addClass('elect-seclect-item-on');
				$('.elect-cover').show().css('opacity','0.6');
			});
			
			//选择学段学科
			$('.select-stg-sbj').on('click','.elect-seclect-section-li',function(){
				hidecover();
				var $this = $(this);
				var $context = $('.select-stg-sbj');
				$this.addClass('elect-seclect-section-li-on').siblings().removeClass('elect-seclect-section-li-on');
				self.query.schoolStageId = $this.data('schoolStageId');
				self.query.subjectId = $this.data('subjectId');
				var schoolStageName =  $this.data('schoolStageName');
				var subjectName =  $this.data('subjectName');
				$('.con-stg-sbj').html(schoolStageName + subjectName);
				$('#material').html('');
				$('.con-press').html('请选择出版社');
				self.query.pressId = '';
				self.query.materialId = '';
				self.loadPress();
			});
			//出版社选择
			$('.select-press').on('click','.elect-seclect-section-li',function(){
				hidecover();
				var $this = $(this);
				var $context = $('.select-press');
				$('.elect-seclect-item').removeClass('elect-seclect-item-on');
				$context.addClass('elect-seclect-item-on');
				$context.find('.elect-seclect-section-li').removeClass('elect-seclect-section-li-on')
				$this.addClass('elect-seclect-section-li-on');
				var pressId = $this.data('pressId');
				var pressName = $this.data('pressName');
				$('.con-press').html(pressName);
				self.query.pressId = pressId;
				self.query.materialId = '';
				self.loadMaterials();
			});
		
			$('.elect-cover').click(function(){
				hidecover();
				$('.elect-seclect-item').removeClass('elect-seclect-item-on')
			})
			
			//教材版本选择
			$('.elect-seclect-article').on('click','.elec-course-name',function(){
				var $this = $(this);
				var $context = $('.elect-seclect-article');
				$this.addClass('elec-course-name-on').siblings().removeClass('elec-course-name-on');
				var materialId = $this.data('materialId');
				var materialFileId = $this.data('materialFileId')
				self.query.materialId = materialId;
				self.query.materialFileId = materialFileId
			});
			
			//确定加入
			$('.j-save').on('click',function(){
				var query = self.query;
				var $on = $('.elect-seclect-article').find('.elec-course-name-on');
				query.materialFileId = $on.data('materialFileId');
				if(!query.materialId || !query.materialFileId){
					Utils.Notice.print('请选择教材！',3000);
					return false;
				}
				var req = $.post(SAVE_DIR,{materialFileId: query.materialFileId,curPage: $on.data('curPage')});
				req.then(function(datas){
					var result = {materialId: self.query.materialId}
					window.LeKeBridge.sendMessage2Native('bind', JSON.stringify(result));
				},function(msg){
					Utils.Notice.print(msg || '加入失败！',3000);
				});
			});
		},
		_initStgSbj: function(){
			var self = this;
			var schoolStages = window.materialCtx.schoolStages || [];
			for(var i = 0; i < schoolStages.length; i++ ){
				var stg = schoolStages[i];
				var subjects = stg.subjects || [];
				for(var j = 0; j < subjects.length; j++){
					var sbj = subjects[j];
					self.optgroups.push({
						schoolStageName: stg.schoolStageName,
						schoolStageId: stg.schoolStageId,
						subjectId: sbj.subjectId,
						subjectName: sbj.subjectName
					});
				}
			}
			var output = Mustache.render($("#j_stg_sbj").html(), self);
			$('#stg_sbj').html(output);
		},
		loadPress: function(){
			var self = this;
			var query = self.query;
			var schoolStageId = query.schoolStageId;
			var subjectId = query.subjectId;
			var req = $.post(DIR + '/presses.htm',{schoolStageId: schoolStageId,subjectId: subjectId});
			req.then(function(datas){
				var output = Mustache.render($("#j_press").html(), datas.datas);
				$('#press').html(output);
			},function(msg){
				Utils.Notice.alert(msg || '加载出版社出错！');
			});
		},
		loadMaterials: function(){
			var self = this;
			var query = self.query;
			var schoolStageId = query.schoolStageId;
			var subjectId = query.subjectId;
			var pressId = query.pressId
			var req = $.post(Leke.ctx + '/auth/teacher/material/findTeacherMaterial.htm',{schoolStageId: schoolStageId,subjectId: subjectId,pressId: pressId});
			req.then(function(datas){
				var output = Mustache.render($("#j_material").html(), datas.datas);
				if(!datas.datas.materials || !datas.datas.materials.length){
					output = '<div class="null">暂无电子教材!</div>';
				}
				$('#material').html(output);
				$('#material').find('.elec-course-name').each(function(){
					var $this = $(this);
					if($this.data('materialId') == self.query.materialId){
						$this.addClass('elec-course-name-on');
					}
				});
			},function(msg){
				Utils.Notice.alert(msg || '加载教材版本出错！');
			});
		}
};
	function hidecover(){
		$('.elect-seclect-section').hide();
		$('.elect-cover').hide();
	}
	callMaterial.init();