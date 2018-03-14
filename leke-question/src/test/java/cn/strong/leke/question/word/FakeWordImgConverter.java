/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word;

import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.springframework.stereotype.Component;

import cn.strong.leke.question.word.doc.WordImgConverter;
import cn.strong.leke.question.word.model.Img;
import cn.strong.leke.question.word.model.ImgDim;

/**
 * 
 *
 * @author  liulongbiao
 * @created 2014年12月8日 下午1:35:47
 * @since   v3.2
 */
@Component
public class FakeWordImgConverter implements WordImgConverter {

	@Override
	public Img convertImage(XWPFPictureData data, ImgDim dim) {
		return new Img(data.getFileName(), dim);
	}

}
