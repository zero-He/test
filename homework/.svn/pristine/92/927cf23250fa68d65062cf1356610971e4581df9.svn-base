package cn.strong.leke.homework.util;

import javax.servlet.http.HttpServletRequest;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.model.user.UserBase;

public class PhotoUtils {

	public static final String FILE_SERVERNAME = "fileServerName";
	public static final String STATIC_SERVERNAME = "staticServerName";

	/**
	 * 根据性别获取默认头像
	 * @param sex
	 * @return
	 */
	public static String getAbsolutePhoto(UserBase user, HttpServletRequest request) {
		if (user.getPhoto() == null) {
			String photo = null;
			if (user.getSex() == null) {
				user.setSex(3);
			}
			switch (user.getSex()) {
			case 1:
				photo = "/images/home/photo-man.png";
				break;
			case 2:
				photo = "/images/home/photo-female.png";
				break;
			default:
				photo = "/images/home/photo.png";
			}
			return request.getServletContext().getInitParameter(STATIC_SERVERNAME) + photo;
		} else {
			return request.getServletContext().getInitParameter(FILE_SERVERNAME) + "/" + user.getPhoto();
		}
	}

	/**
	 * 根据性别获取默认头像
	 * @param sex
	 * @return
	 */
	public static String getAbsoluteResizePhoto(String photo, Integer sex, HttpServletRequest request) {
		if (StringUtils.isEmpty(photo)) {
			if (sex == null) {
				sex = 3;
			}
			switch (sex) {
			case 1:
				photo = "/images/home/photo-man.png";
				break;
			case 2:
				photo = "/images/home/photo-female.png";
				break;
			default:
				photo = "/images/home/photo.png";
			}
			return request.getServletContext().getInitParameter(STATIC_SERVERNAME) + photo;
		} else {
			String resize = "/resize_50x50/";
			return request.getServletContext().getInitParameter(FILE_SERVERNAME) + resize + photo;
		}
	}
}
