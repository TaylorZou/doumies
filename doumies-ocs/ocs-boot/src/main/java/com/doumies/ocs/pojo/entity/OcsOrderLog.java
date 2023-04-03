package com.doumies.ocs.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.doumies.common.base.BaseEntity;
import lombok.Data;

/**
 * 订单操作历史记录
 *
 * @author Taylor
 * @date 2021-12-13
 */
@Data
public class OcsOrderLog extends BaseEntity {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 订单ID
	 */
	private Long orderId;
	/**
	 * 操作人[用户；系统；后台管理员]
	 */
	private String user;
	/**
	 * 操作详情
	 */
	private String detail;
	/**
	 * 操作时订单状态
	 */
	private Integer orderStatus;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 逻辑删除【0->正常；1->已删除】
	 */
	private Integer deleted;

}
