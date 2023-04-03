package com.doumies.ocs.pojo.dto;

import com.doumies.ocs.pojo.entity.OcsOrder;
import com.doumies.ocs.pojo.entity.OcsOrderItem;
import com.doumies.ucs.pojo.dto.MemberDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @desc
 * @author Taylor
 * @date 2021-12-13
 */
@Data
@Accessors(chain = true)
public class OrderDTO {

    private OcsOrder order;

    private List<OcsOrderItem> orderItems;

    private MemberDTO member;

}
