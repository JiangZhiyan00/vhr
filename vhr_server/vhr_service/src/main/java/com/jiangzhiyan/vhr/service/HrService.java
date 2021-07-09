package com.jiangzhiyan.vhr.service;

import com.jiangzhiyan.vhr.mapper.HrMapper;
import com.jiangzhiyan.vhr.mapper.HrRoleMapper;
import com.jiangzhiyan.vhr.model.Hr;
import com.jiangzhiyan.vhr.model.Role;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.utils.AssertExceptionUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class HrService implements UserDetailsService {

    @Resource
    private HrMapper hrMapper;

    @Resource
    private HrRoleMapper hrRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        if (hr == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<Role> roles = hrMapper.selectRolesByHrId(hr.getId());
        if (roles == null || roles.size() == 0){
            Role role = new Role();
            role.setName("ROLE_LOGIN");
            roles = new ArrayList<>(1);
            roles.add(role);
        }
        hr.setRoles(roles);
        return hr;
    }

    public List<Hr> selectHrsByKeywordExceptCurrent(String keyword) {
        Hr currentHr = (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return hrMapper.selectHrsByKeywordExceptCurrent(currentHr.getId(),keyword);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateHr(Hr hr) {
        AssertExceptionUtil.isTrue(hr == null || hr.getId() == null,"无效的请求");
        AssertExceptionUtil.isTrue(hrMapper.updateByPrimaryKeySelective(hr) != 1);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateHrRoles(Integer hrId, Integer[] roleIds) {
        AssertExceptionUtil.isTrue(hrId == null,"无效的请求");
        List<Integer> existRoleIds = hrRoleMapper.getRoleIdsByHrId(hrId);
        //如果hrId当前存在对应的roleIds,执行删除
        if (existRoleIds != null && existRoleIds.size() > 0){
            AssertExceptionUtil.isTrue(hrRoleMapper.deleteByHrId(hrId) != existRoleIds.size());
        }
        if (roleIds == null || roleIds.length < 1){
            return;
        }
        //如果传入的menuIds参数有值,执行添加操作
        AssertExceptionUtil.isTrue(hrRoleMapper.insertHrAndRoles(hrId,roleIds) != roleIds.length);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteHr(Integer id) {
        AssertExceptionUtil.isTrue(id == null,"无效的请求");
        List<Integer> existRoleIds = hrRoleMapper.getRoleIdsByHrId(id);
        if (existRoleIds != null && existRoleIds.size() > 0){
            AssertExceptionUtil.isTrue(hrRoleMapper.deleteByHrId(id) != existRoleIds.size());
        }
        AssertExceptionUtil.isTrue(hrMapper.deleteByPrimaryKey(id) != 1);
    }

    /**
     * 查询除了当前hr之外的所有hr信息
     */
    public ResponseBean getAllHrsExceptCurrent() {
        Hr currentHr = (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseBean.success(hrMapper.getAllHrsExceptCurrent(currentHr.getId()));
    }
}
