package com.jiangzhiyan.vhr.service;

import com.jiangzhiyan.vhr.mapper.HrMapper;
import com.jiangzhiyan.vhr.mapper.HrRoleMapper;
import com.jiangzhiyan.vhr.model.Hr;
import com.jiangzhiyan.vhr.model.Role;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.utils.AssertExceptionUtil;
import com.jiangzhiyan.vhr.utils.FastDFSUtil;
import com.jiangzhiyan.vhr.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HrService implements UserDetailsService {

    @Resource
    private HrMapper hrMapper;

    @Resource
    private HrRoleMapper hrRoleMapper;

    @Value("${fastdfs.nginx.host}")
    private String nginxHost;

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

    /**
     * 更新非当前登录hr的信息
     */
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

    @Transactional(rollbackFor = Exception.class)
    public void updateCurrentHrInfo(Hr hr,Authentication authentication){
        AssertExceptionUtil.isTrue(hr == null || hr.getId() == null,"无效的请求");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(hr.getName()),"修改的名称不能为空");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(hr.getPhone()),"修改的手机号码不能为空");
        AssertExceptionUtil.isTrue(!PhoneUtil.isValidMobile(hr.getPhone()),"修改的手机号码格式不正确");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(hr.getTelephone()),"修改的电话号码不能为空");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(hr.getAddress()),"修改的家庭住址不能为空");
        AssertExceptionUtil.isTrue(hrMapper.updateByPrimaryKeySelective(hr) != 1);
        //更新security中存储的Authentication对象
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
                (hr, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Map<String, Object> passwordInfo) {
        String oldPassword = (String) passwordInfo.get("oldPassword");
        String newPassword = (String) passwordInfo.get("newPassword");
        String confirmPassword = (String) passwordInfo.get("confirmPassword");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(oldPassword), "原始密码不能为空");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(newPassword), "新密码不能为空");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(confirmPassword), "确认密码不能为空");
        AssertExceptionUtil.isTrue(oldPassword.equals(newPassword),"新密码不能与原始密码一致");
        AssertExceptionUtil.isTrue(!newPassword.equals(confirmPassword),"两次输入的新密码不一致");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Integer hrId = ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Hr hr = hrMapper.selectByPrimaryKey(hrId);
        AssertExceptionUtil.isTrue(!passwordEncoder.matches(oldPassword,hr.getPassword()),"原始密码错误,请重新输入");
        AssertExceptionUtil.isTrue(hrMapper.updateHrPassword(hrId,passwordEncoder.encode(newPassword)) != 1);
    }

    /**
     * 更新头像
     * @param file 头像图片文件
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserFace(MultipartFile file,Authentication authentication) {
        String filePath = FastDFSUtil.upload(file);
        AssertExceptionUtil.isTrue(filePath == null,"文件上传失败");
        String url = nginxHost + filePath;
        Hr hr = (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        hr.setUserFace(url);
        AssertExceptionUtil.isTrue(hrMapper.updateByPrimaryKeySelective(hr) != 1);
        //同时需要将security中的hr信息更新
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
                (hr, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
