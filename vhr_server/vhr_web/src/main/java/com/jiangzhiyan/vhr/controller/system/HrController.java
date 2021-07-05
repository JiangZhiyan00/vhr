package com.jiangzhiyan.vhr.controller.system;

import com.jiangzhiyan.vhr.model.Hr;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.service.HrService;
import com.jiangzhiyan.vhr.service.system.basic.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("系统管理-操作员管理")
@RestController
@RequestMapping("/system/hr")
public class HrController {

    @Autowired
    private HrService hrService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    @ApiOperation("查询hr信息,包括其拥有的角色")
    public ResponseBean selectHrsByKeywordExceptCurrent(String keyword) {
        return ResponseBean.success(hrService.selectHrsByKeywordExceptCurrent(keyword));
    }

    @ApiOperation("修改hr信息")
    @PutMapping("/")
    public ResponseBean updateHr(@RequestBody Hr hr) {
        hrService.updateHr(hr);
        return ResponseBean.success("更新成功");
    }

    @ApiOperation("查询所有的角色")
    @GetMapping("/allRoles")
    public ResponseBean selectAllRoles() {
        return roleService.selectAll();
    }

    @ApiOperation("更新hr的角色")
    @PutMapping("/updateRoles")
    public ResponseBean updateHrRoles(Integer hrId, Integer[] roleIds) {
        hrService.updateHrRoles(hrId, roleIds);
        return ResponseBean.success("角色更新成功");
    }

    @ApiOperation("删除hr及其与角色关联的记录")
    @DeleteMapping("/{id}")
    public ResponseBean deleteHr(@PathVariable Integer id) {
        hrService.deleteHr(id);
        return ResponseBean.success("删除成功");
    }
}
