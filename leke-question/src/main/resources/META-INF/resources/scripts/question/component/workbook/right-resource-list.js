	define(function(require, exports, module) {
		var ko = require('knockout');
		var $ = require('jquery');
		var _ = require('underscore');
		var utils = require('utils');
		var dialog = require('dialog');
		var http = require('common/base/http');
		var RepoService = require('repository/service/RepoService');
		var DM = Leke.domain;
		var BK_DM = DM.beikeServerName;
		var workbookService = require('question/pages/common/workbookService');
		var Roles = require('core-business/roles');
		require('core-business/widget/jquery.leke.stgGrdSbjSelect');
		require('core-business/widget/jquery.leke.matSelect');
		require('common/knockout/component/leke-page');
		var PERSONAL = 1;
		var FAVORITE = 4;
		
		function Query(options) {
			var ops = _.extend({}, options);
			var self = this;
			self.resType = ops.resType;//资源类型
			self.shareScope = ops.shareScope;//个人资源或收藏
			self.schoolStageId = ko.observable( );//课件名称
			self.subjectId = ko.observable();
			self.pressId = ko.observable();
			self.materialId = ko.observable();
			self.sections = ko.observableArray();
			self.name = ko.observable(ops.name || '');//课件名称
			self.content = ko.observable(ops.content || '');
			self.userResGroupId = ops.userResGroupId;
			self.curPage = ko.observable(ops.curPage || 1);
		}
		
		Query.prototype.toJS = function() {
			var self = this;
			var result = {};
			result.content = self.content.peek();
			result.userResGroupId = self.userResGroupId.peek();
			result.shareScope = self.shareScope.peek();
			result.curPage = self.curPage.peek();
			if (self.schoolStageId){
				result.schoolStageId = self.schoolStageId.peek();
			}
			
			if (self.subjectId){
				result.subjectId = self.subjectId.peek();
			}
			
			if (self.materialId){
				result.materialId = self.materialId.peek();
			}
			
			if (self.pressId){
				result.pressId = self.pressId.peek();
			}
			
			return result;
		};
		
		function SearchVM(params, element) {
			var self = this;
			self.$el = $(element);
			self.options = _.extend({}, SearchVM.defaults, params);
			self.query = new Query(params);
			self.isPersonal = ko.observable(true);
			self.isFavorite = ko.observable(false);
			self.records = ko.observableArray();
			self.selectAll = ko.observable(false);
			self.totalSize = ko.observable(0);
			self.disposables = [];
			var query = self.query;
			
			self.disposables.push(ko.computed(function() {
				var val = self.selectAll();
				self.$el.find('.c-resource-list').find('input[name="resId"]').prop('checked', val);
			}));
			
			self.disposables.push(ko.computed(function() {
				var curPage = self.query.curPage();
			}));
			
			self.disposables.push(ko.computed(function() {
				var shareScope = self.query.shareScope();
				self.query.curPage(1);
				self.isPersonal(shareScope == PERSONAL);
				self.isFavorite(shareScope == FAVORITE);
			}));
			
			self.disposables.push(ko.computed(function() {
				var userResGroupId = self.query.userResGroupId();
				self.query.curPage(1);
			}));
			
			self.disposables.push(ko.computed(function() {
				var curPage = self.query.curPage();
				var userResGroupId = self.query.userResGroupId();
				var shareScope = self.query.shareScope();
			
				self.load();
			}));
			
			self._initStgSbjSelect();
			self.sectionPath = ko.observable('请选择教材章节');
			self.tagContent = ko.observable();
			
			//没必要把microcourse选中的传进来，编辑才需要
			self.disposables.push(ko.computed(function(){
				var content = '';
				_.each(self.query.sections() ||{},function(n){
					content += n.path;
				});
				self.sectionPath(content);
			})); 
			
		};
		
		SearchVM.prototype._initStgSbjSelect = function() {
			var self = this;
			var query = self.query;
			var $ss = self.$ss = self.$el.find('[name=stageSubject]');
			$ss.stgGrdSbjSelect({
				data: {
					schoolStageId: query.schoolStageId(),
					subjectId: query.subjectId()
				},
				model: 'nocache',
				onChange: function(item) {
					_.each(['schoolStageId', 'subjectId'], function(k) {
						query[k](item[k]);
					});
				}
			});
			var _syncStgSbj = _.debounce(function() {
				var data = {};
				_.each(['schoolStageId', 'subjectId'], function(k) {
					data[k] = query[k].peek();
				});
				$ss.stgGrdSbjSelect('setData', data);
			}, 100);
			self._d_ss = ko.computed(function() {
				var stgid = query.schoolStageId();
				var sbjid = query.subjectId();
				_syncStgSbj();
			});
		};

		SearchVM.prototype.openSectionSelect = function(vm, evt) {
			var self = this, query = self.query;
			evt = $.event.fix(evt);
			require.async(['core-business/widget/jquery.leke.matSelect'], function() {
				$(evt.target).matSelect({
					type: 'section',
					multi: false,
					model: 'persearch',
					data: {
						schoolStageId: query.schoolStageId(),
						subjectId: query.subjectId()
					},
					onSelect: function(section) {
						var sec =  {
							//materialNodeId: section.materialNodeId,
							materialId: section.materialId,
							/*path: section.path,
							materialNodePath: section.path,*/
							path:  section.pressName + ' ' +section.materialName,
							queMaterialId: section.materialId
						};
						query.materialId(section.materialId);
						query.pressId(section.pressId);
						query.sections([sec]);
					}
				});
			});
		};

		SearchVM.prototype.delSection = function(){
			var self = this;
			self.query.sections([]);
			self.query.materialId({});
			self.query.pressId({});
		};
		
		/*
		 * 实际查询操作
		 */
		SearchVM.prototype.load = _.throttle(function() {
			var self = this;
			var query = self.query.toJS();
			self.selectAll(false);
			var postUrl = self.query.shareScope() == PERSONAL ? self.options.urlPersonalDetails : self.options.urlFavoriteDetails;
			http.post(postUrl, query).then(function(datas) {
				var records = RepoService.processRecords(datas.records);
				self.records(records);
				self.totalSize(datas.page.totalSize || 0);
			}, function() {
				utils.Notice.alert('试卷列表加载失败!');
				self.records([]);
			});
		}, 300);
		
		SearchVM.prototype.doSearch = function() {
			var self = this;
			self.curPage(1);
			self.load();
		};
		
		//移动
		SearchVM.prototype.move = function($data){
			var self = this;
			var workbookId = $data.workbookId;
			self.doMove([workbookId]);
		};
		
		SearchVM.prototype.batchMove = function(){
			var self = this;
			var workbookIds = self._selectedResIds();
			if(!workbookIds.length){
				utils.Notice.alert('请选择习题册!');
				return;
			}
			self.doMove(workbookIds);
		};
		
		SearchVM.prototype.doMove = function(workbookIds){
			var self = this;
			var resType = self.query.resType;
			var shareScope = self.query.shareScope.peek();
			var userResGroupId = self.query.userResGroupId.peek();
			document.domain = 'leke.cn';
			if(!workbookIds.length){
				utils.Notice.alert('请选择习题册!');
				return;
			}
			var ops = {
				id : 'userResGroup',
				title : '选择资源分组',
				url: BK_DM + '/auth/common/resgroup/choose.htm?crossDomain=true&resType=' + resType + '&shareScope=' + shareScope,
				size : 'lg',
				onClose : function(newUserResGroupId){
					if(!newUserResGroupId){
						return;
					}
					var req;
					if(userResGroupId){
						var model = {
							oldUserResGroupId: userResGroupId,
							newUserResGroupId: newUserResGroupId
						};
						req = workbookService.moveBatchWorkbookUserGroup(JSON.stringify(model),workbookIds);
					}else{
						req = workbookService.addBatchWorkbookUserGroup(newUserResGroupId,workbookIds);
					}
					req.then(function(data){
						utils.Notice.alert('移动成功');
						self.selectAll(false);
						self.load();
					},function(msg){
						utils.Notice.alert(msg || '移动失败');
						
					});
				}
			};
			dialog.open(ops);
		};
		
		
		//移出资源分组
		SearchVM.prototype.remove = function(record){
			var self = this;
			var workbookId = record.workbookId;
			var userResGroupId = self.query.userResGroupId();
			if(userResGroupId){
				self.delWorkbookUserResGroup(userResGroupId,[workbookId]);
			}
		};
		
		SearchVM.prototype.batchRemove = function(){
			var self = this;
			var workbookIds = self._selectedResIds();
			var userResGroupId = self.query.userResGroupId();
			if(!workbookIds.length){
				utils.Notice.alert('请选择习题册!');
				return;
			}
			if(userResGroupId){
				self.delWorkbookUserResGroup(userResGroupId,workbookIds);
			}
		};
		
		SearchVM.prototype.delWorkbookUserResGroup = function(userResGroupId, workbookIds){
			var self = this;
			if(!workbookIds.length){
				utils.Notice.alert('请选择要移出的习题册！');
				return;
			}
			dialog.confirm('确认要移出分组吗？').done(function(sure){
				if(sure){
					var req = workbookService.delBatchWorkbookUserResGroup(userResGroupId,workbookIds);
					req.then(function(data){
						utils.Notice.alert('习题册移出操作成功!');
						self.load();
					},function(msg){
						utils.Notice.alert(msg || '习题册移出操作失败!');
					});
				}
			});
		};
		
		var URL_ADD = Leke.ctx + '/auth/common/workbook/workbookAdd.htm?crossDomain=true';
		
		SearchVM.prototype.add = function(){
			var self = this;
			var url = URL_ADD;
			var roleId = Leke.user.currentRoleId;
			document.domain = 'leke.cn';
			if(roleId == Roles.RESEARCHER) {
				url += '&sharePlatform=true';
			} else if(roleId == Roles.PROVOST) {
				url += '&shareSchool=true&status=1';
			} else {
				url += '&status=1';
			}
			dialog.open({
				id: 'workbookAdd',
				title: '创建习题册',
				url: url,
				size: 'nm',
				onClose: function(){
					self.load();
				}
			});
		};
		
		SearchVM.prototype.del = function(data){
			var self = this;
			dialog.confirm('删除后将无法恢复，确定要删除吗？').done(function(sure){
				if(!sure) {
					return;
				}
				http.jsonp(Leke.ctx + '/auth/common/workbook/jsonp/doRemove.htm',{
					workbookId: data.workbookId
				}).then(function(){
					utils.Notice.alert('习题册删除成功');
					self.load();
				}, function(msg){
					utils.Notice.alert(msg || '习题册删除失败');
				});
			});
		};
		
		SearchVM.prototype.rename = function($data){
			var self = this;
			document.domain = 'leke.cn';
			dialog.open({
				id: 'workbookEdit',
				title: '习题册重命名',
				url: Leke.ctx + '/auth/common/workbook/workbookEdit.htm?crossDomain=true&workbookId=' + $data.workbookId,
				size: 'nm',
				onClose: function(){
					self.load();
				}
			});
		};
		
		SearchVM.prototype.doUnfavorite = function(record) {
			var self = this;
			dialog.confirm("你确定要取消收藏吗？").done(function(sure) {
				if(sure){
					RepoService.doUnFavoriteWorkbook(record.resId).then(function(data) {
						utils.Notice.alert('取消收藏操作成功!');
						self.load();
					}, function(msg) {
						utils.Notice.alert(msg || '取消收藏操作失败!');
					});
				}
			});
		};
		
		SearchVM.prototype.doBatchUnfavorite = function(){
			var self = this;
			var workbookIds = self._selectedResIds();
			if(!workbookIds.length){
				utils.Notice.alert('请选择习题册!');
				return false;
			}
			dialog.confirm("你确定要取消收藏吗？").done(function(sure) {
				if(sure){
					RepoService.doBatchUnFavoriteWorkbook(workbookIds).then(function(datas){
						utils.Notice.alert('取消收藏成功!');
						self.load();
					},function(msg){
						utils.Notice.alert('取消收藏失败!' || msg);
					});
				}
			});
		};
		
		SearchVM.prototype._selectedResIds = function() {
			var self = this;
			var resIds = [];
			self.$el.find('.c-resource-list').find('input[name="resId"]:checked').each(function() {
				resIds.push($(this).val());
			});
			return resIds;
		};
		
		SearchVM.prototype.dispose = function() {
			var self = this;
			_.each(self.disposables, function(d) {
				d.dispose();
			});
		};
		
		var DIR = Leke.ctx + '/auth/common/workbook/';
		SearchVM.defaults = {
				urlPersonalDetails: DIR + 'personal/details.htm',
				urlFavoriteDetails: DIR + 'favorite/details.htm'
			};
		
		ko.components.register('right-resource-list', {
			template: require('./right-resource-list.html'),
			viewModel: {
				createViewModel: function(params, componentInfo) {
					return new SearchVM(params, componentInfo.element);
				}
			}
		});
	});