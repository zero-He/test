/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.doc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.strong.leke.common.utils.ImageUtils;
import cn.strong.leke.common.utils.ImageUtils.Dimension;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.question.word.model.Img;
import cn.strong.leke.question.word.model.ImgDim;

/**
 * 
 *
 * @author  liulongbiao
 * @created 2014年12月8日 下午1:35:47
 * @since   v3.2
 */
public class LekeWordImgConverter implements WordImgConverter {

	private static final Logger log = LoggerFactory
			.getLogger(LekeWordImgConverter.class);

	@Override
	public Img convertImage(XWPFPictureData data, ImgDim dim) {
		if (data == null) {
			return null;
		}
		Img img = null;
		InputStream is = null;
		try {
			is = data.getPackagePart().getInputStream();
			byte[] bytes = IOUtils.toByteArray(is);
			String suffix = data.suggestFileExtension();

			String[] urls = FileUtils.upload(bytes, suffix);
			if (StringUtils.isEmpty(urls[0])) {
				throw new LekeRuntimeException("que.word.doc.img.upload.failed");
			}
			img = new Img(urls[1]);
			setImgDim(dim, img, bytes, suffix);
		} catch (Exception e) {
			log.info("unable to convert image in word file.", e);
			img = null;
		} finally {
			IOUtils.closeQuietly(is);
		}
		return img;
	}

	/**
	 * 设置图片宽高
	 * 
	 * @author liulongbiao
	 * @created 2015年1月11日 下午4:01:38
	 * @since v3.2.2
	 * @param dim
	 * @param img
	 * @param bytes
	 * @param suffix
	 */
	private void setImgDim(ImgDim dim, Img img, byte[] bytes, String suffix) {
		if (dim != null) {
			img.setDim(dim);
		} else {
			try {
				InputStream imgStream = new ByteArrayInputStream(bytes);
				Dimension d = ImageUtils.getImageDimension(imgStream,
						suffix);
				img.setDim(new ImgDim(d.width, d.height));
			} catch (Exception ex) {
				log.info("unable to read image dimension.", ex);
			}
		}
	}
}
