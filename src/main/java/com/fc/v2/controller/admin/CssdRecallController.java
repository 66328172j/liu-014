package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TCssdRecall;
import com.fc.v2.service.ITCssdRecallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 器械召回处置单 Controller（state-machine 形状：流转入口）
 *
 * @author fuce
 * @date 2026-09-14
 */
@Api(value = "器械召回处置单")
@Controller
@RequestMapping("/cssdRecall")
public class CssdRecallController extends BaseController {

    private final String prefix = "admin/cssdRecall";

    @Autowired
    private ITCssdRecallService cssdRecallService;

    @ApiOperation(value = "流转台账跳转", notes = "流转台账跳转")
    @GetMapping("/view")
    @RequiresPermissions("cssdRecall:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    @Log(title = "器械召回处置单流转台账", action = "list")
    @ApiOperation(value = "流转台账", notes = "流转台账")
    @GetMapping("/list")
    @RequiresPermissions("cssdRecall:list")
    @ResponseBody
    public ResultTable list(TCssdRecall record) {
        QueryWrapper<TCssdRecall> queryWrapper = new QueryWrapper<TCssdRecall>();
        startPage();
        com.github.pagehelper.PageInfo<TCssdRecall> page =
                new com.github.pagehelper.PageInfo<TCssdRecall>(cssdRecallService.selectTCssdRecallList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }

    @Log(title = "器械召回处置单推进", action = "advance")
    @ApiOperation(value = "推进一档", notes = "推进一档")
    @PostMapping("/advance")
    @RequiresPermissions("cssdRecall:advance")
    @ResponseBody
    public AjaxResult advance(Long id, String remark) {
        return toAjax(cssdRecallService.advance(id, remark) != null ? 1 : 0);
    }

    @Log(title = "器械召回处置单回退", action = "rollback")
    @ApiOperation(value = "回退一档", notes = "回退一档")
    @PostMapping("/rollback")
    @RequiresPermissions("cssdRecall:rollback")
    @ResponseBody
    public AjaxResult rollback(Long id, String remark) {
        return toAjax(cssdRecallService.rollback(id, remark) != null ? 1 : 0);
    }
}
