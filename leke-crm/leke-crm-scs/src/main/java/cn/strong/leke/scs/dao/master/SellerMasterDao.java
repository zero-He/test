package cn.strong.leke.scs.dao.master;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

public interface SellerMasterDao {

	/**
	 * 创建默认的销售销售累计信息
	 * @param sellerId 销售ID
	 * @param sellerName 销售姓名
	 * @return
	 */
	public int insertSeller(@Param("sellerId") Long sellerId, @Param("sellerName") String sellerName);

	/**
	 * 更新销售佣金累计。
	 * @param sellerId 销售ID
	 * @param commAmount 佣金金额
	 */
	public int updateSellerCommAmount(@Param("sellerId") Long sellerId, @Param("commAmount") BigDecimal commAmount);

	/**
	 * up=true 递增客户数量（客户数量+1）
	 * 移除客户（客户数量+1）     v1.9.0新增  2015-12-18
	 * @param sellerId 销售ID
	 * @return
	 */
	public int updateIncrementCustomerNum(@Param("sellerId") Long sellerId ,@Param("up") boolean up);

	/**
	 * 重置月销售累计金额。
	 */
	public int resetSellerMonthCommAmount();

	/**
	 * 重置年和月销售累计金额。
	 */
	public int resetSellerYearCommAmount();
}
