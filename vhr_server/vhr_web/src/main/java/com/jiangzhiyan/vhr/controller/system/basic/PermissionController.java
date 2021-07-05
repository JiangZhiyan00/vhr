package com.jiangzhiyan.vhr.controller.system.basic;

import com.jiangzhiyan.vhr.model.Role;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.service.MenuService;
import com.jiangzhiyan.vhr.service.system.basic.PermissionService;
import com.jiangzhiyan.vhr.service.system.basic.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JiangZhiyan
 */
@RestController
@Api("系统管理-基础信息设置-权限组")
@RequestMapping("/system/basic/permission")
public class PermissionController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @ApiOperation("查询所有角色信息")
    @GetMapping("/")
    public ResponseBean selectAll(){
        return roleService.selectAll();
    }

    @ApiOperation("查询所有菜单并分级")
    @GetMapping("/menus")
    public ResponseBean queryAllMenusWithChildren(){
        return ResponseBean.success(menuService.queryAllMenusWithChildren());
    }

    @ApiOperation("查询当前角色拥有的菜单id")
    @GetMapping("/getMenuIds/{roleId}")
    public ResponseBean getMenuIdsByRoleId(@PathVariable Integer roleId){
        return ResponseBean.success(permissionService.getMenuIdsByRoleId(roleId));
    }

    @ApiOperation("添加角色")
    @PostMapping("/")
    public ResponseBean addRole(@RequestBody Role role){
        roleService.insertSelective(role);
        return ResponseBean.success("角色添加成功");
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/{roleId}")
    public ResponseBean deleteRole(@PathVariable Integer roleId){
        roleService.deleteByPrimaryKey(roleId);
        return ResponseBean.success("角色删除成功");
    }

    @ApiOperation("更新角色拥有的菜单")
    @PutMapping("/")
    public ResponseBean editRole(Integer roleId,Integer[] menuIds){
        permissionService.updatePermission(roleId,menuIds);
        return ResponseBean.success("权限更新成功");
    }
}
