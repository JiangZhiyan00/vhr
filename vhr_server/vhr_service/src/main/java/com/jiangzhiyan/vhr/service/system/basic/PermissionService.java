package com.jiangzhiyan.vhr.service.system.basic;

import com.jiangzhiyan.vhr.base.BaseService;
import com.jiangzhiyan.vhr.mapper.MenuRoleMapper;
import com.jiangzhiyan.vhr.model.MenuRole;
import com.jiangzhiyan.vhr.utils.AssertExceptionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JiangZhiyan
 */
@Service
public class PermissionService extends BaseService<MenuRole,Integer> {

    @Resource
    private MenuRoleMapper menuRoleMapper;

    public List<Integer> getMenuIdsByRoleId(Integer roleId) {
        return menuRoleMapper.getMenuIdsByRoleId(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(Integer roleId,Integer[] menuIds){
        AssertExceptionUtil.isTrue(roleId == null,invalidRequest);
        List<Integer> existMenuIds = menuRoleMapper.getMenuIdsByRoleId(roleId);
        //如果roleId当前存在对应的menuIds,执行删除
        if (existMenuIds != null && existMenuIds.size() > 0){
            AssertExceptionUtil.isTrue(menuRoleMapper.deleteByRoleId(roleId) != existMenuIds.size());
        }
        if (menuIds == null || menuIds.length < 1){
            return;
        }
        //如果传入的menuIds参数有值,执行添加操作
        AssertExceptionUtil.isTrue(menuRoleMapper.insertMenuAndRoles(roleId,menuIds) != menuIds.length);
    }

    @Override
    protected void checkParams(MenuRole model) {
    }

    @Override
    protected void initParams(MenuRole model) {
    }
}
