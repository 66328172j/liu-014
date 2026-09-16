package com.fc.v2.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fc.v2.model.auto.TCssdReturn;

import java.util.List;

/**
 * 器械归还单 Service接口
 *
 * @author fuce
 * @date 2026-09-12
 */
public interface ITCssdReturnService {

    /** 按主键查询 */
    TCssdReturn selectTCssdReturnById(Long id);

    /** 按条件查询列表（分页由调用方统一处理） */
    List<TCssdReturn> selectTCssdReturnList(Wrapper<TCssdReturn> queryWrapper);

    /** 新增 */
    int insertTCssdReturn(TCssdReturn record);

    /** 修改 */
    int updateTCssdReturn(TCssdReturn record);

    /** 批量删除 */
    int deleteTCssdReturnByIds(String ids);

    /** 按主键删除 */
    int deleteTCssdReturnById(Long id);
}
