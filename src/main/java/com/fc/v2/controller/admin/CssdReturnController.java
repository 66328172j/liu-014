package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TCssdReturn;
import com.fc.v2.service.ITCssdReturnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 器械归还单 Controller
 *
 * @author fuce
 * @date 2026-09-12
 */
@Api(value = "器械归还单")
@Controller
@RequestMapping("/CssdReturnController")
public class CssdReturnController extends BaseController {

    private final String prefix = "admin/cssdReturn";

    @Autowired
    private ITCssdReturnService cssdReturnService;

    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("cssd:cssdReturn:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    @Log(title = "器械归还单集合查询", action = "list")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("cssd:cssdReturn:list")
    @ResponseBody
    public ResultTable list(TCssdReturn record) {
        QueryWrapper<TCssdReturn> queryWrapper = new QueryWrapper<TCssdReturn>();
        startPage();
        com.github.pagehelper.PageInfo<TCssdReturn> page =
                new com.github.pagehelper.PageInfo<TCssdReturn>(cssdReturnService.selectTCssdReturnList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }

    @Log(title = "器械归还单新增", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RequiresPermissions("cssd:cssdReturn:add")
    @ResponseBody
    public AjaxResult add(TCssdReturn record) {
        return toAjax(cssdReturnService.insertTCssdReturn(record));
    }

    @Log(title = "器械归还单修改", action = "edit")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    @PostMapping("/edit")
    @RequiresPermissions("cssd:cssdReturn:edit")
    @ResponseBody
    public AjaxResult editSave(TCssdReturn record) {
        return toAjax(cssdReturnService.updateTCssdReturn(record));
    }

    @Log(title = "器械归还单删除", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("cssd:cssdReturn:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(cssdReturnService.deleteTCssdReturnByIds(ids));
    }
}
