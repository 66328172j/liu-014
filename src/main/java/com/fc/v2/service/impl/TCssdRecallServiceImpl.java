package com.fc.v2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.mapper.auto.TCssdRecallMapper;
import com.fc.v2.model.auto.TCssdRecall;
import com.fc.v2.service.ITCssdRecallService;

/**
 * 器械召回处置单 Service业务层处理（state-machine 形状：单据流转）
 *
 * @author fuce
 * @date 2026-09-14
 */
@Service
public class TCssdRecallServiceImpl implements ITCssdRecallService {

    private static final int MAX_STAGE = 3;
    private static final int STATUS_ACTIVE = 1;
    private static final int STATUS_TERMINAL = 2;

    @javax.annotation.Resource
    private TCssdRecallMapper cssdRecallMapper;

    @Override
    public TCssdRecall selectTCssdRecallById(Long id) {
        return this.cssdRecallMapper.selectById(id);
    }

    @Override
    public List<TCssdRecall> selectTCssdRecallList(QueryWrapper<TCssdRecall> queryWrapper) {
        return this.cssdRecallMapper.selectList(queryWrapper);
    }

    @Override
    public TCssdRecall advance(Long id, String remark) {
        TCssdRecall r = this.cssdRecallMapper.selectById(id);
        if (r == null) {
            return null;
        }
        int st = r.getStage() == null ? 0 : r.getStage();
        r.setStage(Math.min(st + 2, MAX_STAGE));
        r.setStatus(STATUS_ACTIVE);
        r.setLastAction(remark);
        this.cssdRecallMapper.updateById(r);
        return r;
    }

    @Override
    public TCssdRecall rollback(Long id, String remark) {
        TCssdRecall r = this.cssdRecallMapper.selectById(id);
        if (r == null) {
            return null;
        }
        r.setStage(0);
        r.setStatus(STATUS_ACTIVE);
        r.setLastAction(remark);
        this.cssdRecallMapper.updateById(r);
        return r;
    }

    @Override
    public boolean updateContent(Long id, String remark) {
        TCssdRecall r = this.cssdRecallMapper.selectById(id);
        if (r == null) {
            return false;
        }
        r.setContent(remark);
        return this.cssdRecallMapper.updateById(r) > 0;
    }

    @Override
    public boolean remove(Long id) {
        TCssdRecall r = this.cssdRecallMapper.selectById(id);
        if (r == null) {
            return false;
        }
        return this.cssdRecallMapper.deleteById(id) > 0;
    }

}
