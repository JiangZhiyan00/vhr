package com.jiangzhiyan.vhr.service.system.basic;

import com.jiangzhiyan.vhr.base.BaseService;
import com.jiangzhiyan.vhr.mapper.MenuRoleMapper;
import com.jiangzhiyan.vhr.mapper.RoleMapper;
import com.jiangzhiyan.vhr.model.Role;
import com.jiangzhiyan.vhr.utils.AssertExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService extends BaseService<Role, Integer> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private MenuRoleMapper menuRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByPrimaryKey(Integer roleId) {
        AssertExceptionUtil.isTrue(roleId == null,invalidRequest);
        List<Integer> existMenuIds = menuRoleMapper.getMenuIdsByRoleId(roleId);
        if (existMenuIds != null && existMenuIds.size() > 0) {
            AssertExceptionUtil.isTrue(menuRoleMapper.deleteByRoleId(roleId) != existMenuIds.size());
        }
        AssertExceptionUtil.isTrue(roleMapper.deleteByPrimaryKey(roleId) != 1);
    }

    @Override
    protected void checkParams(Role model) {
        AssertExceptionUtil.isTrue(model == null,invalidRequest);
        AssertExceptionUtil.isTrue(StringUtils.isBlank(model.getName()),"添加的角色英文名称不能为空");
        if (!model.getName().startsWith("ROLE_")){
            model.setName("ROLE_"+model.getName());
        }
        AssertExceptionUtil.isTrue(StringUtils.isBlank(model.getNameZh()),"添加的角色中文名称不能为空");
        Role existRole1 = roleMapper.selectByName(model.getName());
        Role existRole2 = roleMapper.selectByNameZh(model.getNameZh());
        if (model.getId() == null){
            AssertExceptionUtil.isTrue(existRole1 != null,"添加的角色英文名称已存在");
            AssertExceptionUtil.isTrue(existRole2 != null,"添加的角色中文名称已存在");
        }else {
            AssertExceptionUtil.isTrue(existRole1 != null && !model.getId().equals(existRole1.getId()),
                    "更改的角色英文名称已存在");
            AssertExceptionUtil.isTrue(existRole2 != null && !model.getId().equals(existRole2.getId()),
                    "更改的角色中文名称已存在");
        }
    }

    @Override
    protected void initParams(Role model) {

    }
}
