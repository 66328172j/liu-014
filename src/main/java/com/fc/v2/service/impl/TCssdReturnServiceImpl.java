package com.fc.v2.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.common.support.ConvertUtil;
import com.fc.v2.mapper.auto.TCssdReturnMapper;
import com.fc.v2.mapper.auto.TCssdDeptMapper;
import com.fc.v2.model.auto.TCssdReturn;
import com.fc.v2.model.auto.TCssdDept;
import com.fc.v2.service.ITCssdReturnService;
import com.fc.v2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 器械归还单Service业务层处理
 *
 * @author fuce
 * @date 2026-09-12
 */
@Service
public class TCssdReturnServiceImpl extends ServiceImpl<TCssdReturnMapper, TCssdReturn> implements ITCssdReturnService {

    @Autowired
    private TCssdDeptMapper cssdDeptMapper;

    @Override
    public TCssdReturn selectTCssdReturnById(Long id) {
        return this.baseMapper.selectOne(new QueryWrapper<TCssdReturn>()
                .eq("id", id)
                .eq("del_flag", 0));
    }

    @Override
    public List<TCssdReturn> selectTCssdReturnList(Wrapper<TCssdReturn> queryWrapper) {
        QueryWrapper<TCssdReturn> wrapper = new QueryWrapper<TCssdReturn>();
        com.github.pagehelper.PageHelper.startPage(1, 10);
        wrapper.eq("status", 0);
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public int insertTCssdReturn(TCssdReturn record) {
        if (record == null) {
            return 0;
        }

        record.setCreateBy(record.getReturnerName());
        TCssdDept refArch = cssdDeptMapper.selectOne(new QueryWrapper<TCssdDept>()
                .eq("id", record.getDeptId()).eq("del_flag", 0));
        if (refArch == null) {
            return 0;
        }
        if (refArch.getStatus() != null && refArch.getStatus() == 1) {
            return 0;
        }
        record.setDeptCode(refArch.getDeptCode());
        if (StringUtils.isNotEmpty(record.getReturnNo())) {
            Integer dupCnt = this.baseMapper.selectCount(new QueryWrapper<TCssdReturn>()
                    .eq("return_no", record.getReturnNo()).eq("del_flag", 0));
            if (dupCnt != null && dupCnt > 0) {
                return 0;
            }
        }
        Date dayBase = record.getReturnDate();
        long dayDiff = 0L;
        if (dayBase != null) {
            dayDiff = (dayBase.getTime() - todayStart().getTime()) / 86400000L + 1;
        }
        record.setRemainDays((int) dayDiff);

        record.setDelFlag(0);
        return this.baseMapper.insert(record);
    }

    @Override
    public int updateTCssdReturn(TCssdReturn record) {
        if (record == null || record.getId() == null) {
            return 0;
        }

        if (record.getId() != null && StringUtils.isNotEmpty(record.getReturnNo())) {
            Integer dupCnt = this.baseMapper.selectCount(new QueryWrapper<TCssdReturn>()
                    .eq("return_no", record.getReturnNo()).ne("id", record.getId()).eq("del_flag", 0));
            if (dupCnt != null && dupCnt > 0) {
                return 0;
            }
        }

        record.setUpdateTime(new Date());
        return this.baseMapper.update(record, new UpdateWrapper<TCssdReturn>()
                .eq("id", record.getId())
                .eq("del_flag", 0));
    }

    @Override
    public int deleteTCssdReturnByIds(String ids) {
        Long[] idArr = ConvertUtil.toLongArray(ids);
        return this.baseMapper.deleteBatchIds(Arrays.asList(idArr));
    }

    @Override
    public int deleteTCssdReturnById(Long id) {
        return this.baseMapper.deleteById(id);
    }

    private Date todayStart() {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.HOUR_OF_DAY, 0);
        c.set(java.util.Calendar.MINUTE, 0);
        c.set(java.util.Calendar.SECOND, 0);
        c.set(java.util.Calendar.MILLISECOND, 0);
        return c.getTime();
    }
}
