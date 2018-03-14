package cn.strong.leke.question.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.incentive.DynamicHelper;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.IncentiveTypes;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.dao.mybatis.workbook.ITeacherWorkbookDao;
import cn.strong.leke.question.model.workbook.TeacherWorkbook;
import cn.strong.leke.question.service.ITeacherWorkbookService;
import cn.strong.leke.question.service.IWorkbookService;

@Service
public class TeacherWorkbookService implements ITeacherWorkbookService {

	@Resource
	private ITeacherWorkbookDao teacherWorkbookDao;
	@Resource
	private IWorkbookService workbookService;

	@Override
	public Award addTeacherWorkbook(TeacherWorkbook fav, User user) {
		if (fav == null || fav.getTeacherId() == null || fav.getWorkbookId() == null) {
			throw new ValidateException("pap.teaworkbook.info.incomplete");
		}
		Long workbookId = fav.getWorkbookId();
		Long teacherId = fav.getTeacherId();
		Workbook workbook = workbookService.getWorkbook(workbookId);
		if (workbook == null) {
			throw new ValidateException("que.workbook.not.exist");
		}
		if (workbook.getCreatedBy().equals(teacherId)) {
			throw new ValidateException("pap.teaworkbook.info.own");
		}
		if (teacherWorkbookDao.countFavorite(workbookId, teacherId) > 0) {
			throw new ValidateException("pap.teaworkbook.already.exist");
		}
		workbookService.updateIncFavCount(workbookId);
		teacherWorkbookDao.addFavorite(workbookId, teacherId);
		Long roleId = user.getCurrentRole().getId();
		if (!roleId.equals(RoleCst.TEACHER)) {
			return Award.ignore();
		}
		DynamicInfo dynamicInfo = createdynamicForAddFavWb(workbook, user);
		return DynamicHelper.publish(dynamicInfo);
	}

	@Override
	public Award addBatchTeacherWorkbook(Long[] workbookIds, User user) {
		if (CollectionUtils.isEmpty(workbookIds)) {
			throw new ValidateException("pap.teaworkbook.info.incomplete");
		}
		Long teacherId = user.getId();
		List<TeacherWorkbook> teacherWorkbooks = new ArrayList<TeacherWorkbook>();
		List<DynamicInfo> dynamicInfos = new ArrayList<DynamicInfo>();
		List<Long> addWorkbookIds = new ArrayList<Long>();
		for (Long workbookId : workbookIds) {
			Workbook workbook = workbookService.getWorkbook(workbookId);
			if (workbook == null) {
				throw new ValidateException("que.workbook.not.exist");
			}
			if (workbook.getCreatedBy().equals(teacherId)) {
				continue;
			}
			if (teacherWorkbookDao.countFavorite(workbookId, teacherId) > 0) {
				continue;
			}
			dynamicInfos.add(createdynamicForAddFavWb(workbook, user));
			TeacherWorkbook tw = new TeacherWorkbook();
			tw.setTeacherId(teacherId);
			tw.setWorkbookId(workbookId);
			tw.setCreatedBy(teacherId);
			teacherWorkbooks.add(tw);
			addWorkbookIds.add(workbookId);
		}
		if (CollectionUtils.isNotEmpty(teacherWorkbooks)) {
			teacherWorkbooks.forEach(fav -> {
				teacherWorkbookDao.addFavorite(fav.getWorkbookId(), fav.getTeacherId());
			});
			workbookService.updateBatchIncFavCount(addWorkbookIds);
		}
		Long roleId = user.getCurrentRole().getId();
		if (!roleId.equals(RoleCst.TEACHER)) {
			return Award.ignore();
		}
		return DynamicHelper.publish(dynamicInfos);
	}

	private DynamicInfo createdynamicForAddFavWb(Workbook workbook, User user) {
		DynamicInfo dynamicInfo = new DynamicInfo();
		dynamicInfo.setTypeId(IncentiveTypes.TEACHER.REP_FAVORITE);
		dynamicInfo.setTitle(workbook.getWorkbookName());
		dynamicInfo.setUserId(user.getId());
		dynamicInfo.setUserName(user.getUserName());
		dynamicInfo.setRoleId(RoleCst.TEACHER);
		dynamicInfo.setSchoolId(user.getCurrentSchool().getId());
		return dynamicInfo;
	}

	@Override
	public void deleteTeacherWorkbook(TeacherWorkbook fav) {
		if (fav == null || fav.getTeacherId() == null || fav.getWorkbookId() == null) {
			throw new ValidateException("pap.teaworkbook.info.incomplete");
		}
		teacherWorkbookDao.deleteFavorites(new Long[] { fav.getWorkbookId() }, fav.getTeacherId());
	}

	@Override
	public void deleteBatchTeacherWorkbook(Long[] workbookIds, Long teacherId) {
		if (CollectionUtils.isNotEmpty(workbookIds)) {
			teacherWorkbookDao.deleteFavorites(workbookIds, teacherId);
		}
	}

}
