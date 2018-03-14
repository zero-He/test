package cn.strong.leke.homework.dao.mybatis;

import cn.strong.leke.homework.model.HanWang;

public interface HanWangDao {

	/**
	 * 保存汉王脚本。
	 * @param hanWang
	 */
	public void saveHanWang(HanWang hanWang);

	/**
	 * 根据ID查询汉王脚本
	 * @param id
	 * @return
	 */
	public HanWang getHanWangById(Long id);
}
