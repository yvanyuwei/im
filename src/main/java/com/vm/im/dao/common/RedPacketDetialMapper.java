package com.vm.im.dao.common;

import com.vm.im.entity.common.RedPacketDetial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * <p>
 * 红包明细 Mapper 接口
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Repository
public interface RedPacketDetialMapper extends BaseMapper<RedPacketDetial> {

    /**
     * 根据红包Id 查询红包明细
     *
     * @param id
     */
    RedPacketDetial selectByRedPacketId(String id);

    /**
     * 获取指定红包的总金额
     *
     * @param id
     * @return
     */
    BigDecimal sumAmountByRedPacketId(String id);
}
