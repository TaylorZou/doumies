package com.doumies.ocs.tcc.service;

import com.doumies.ocs.pojo.dto.OrderSubmitDTO;
import com.doumies.ocs.pojo.entity.OcsOrder;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface SeataTccOrderService {

    @TwoPhaseBusinessAction(name = "prepareSubmitOrder", commitMethod = "commitSubmitOrder", rollbackMethod = "rollbackSubmitOrder")
    OcsOrder prepareSubmitOrder(BusinessActionContext businessActionContext,
                                @BusinessActionContextParameter(paramName = "orderSubmitDTO") OrderSubmitDTO orderSubmitDTO);

    boolean commitSubmitOrder(BusinessActionContext businessActionContext);

    boolean rollbackSubmitOrder(BusinessActionContext businessActionContext);
}
