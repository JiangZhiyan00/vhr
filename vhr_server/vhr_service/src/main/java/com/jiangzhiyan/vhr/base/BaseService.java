package com.jiangzhiyan.vhr.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.utils.AssertExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseService<M,ID> {

    @Autowired
    protected BaseMapper<M,ID> baseMapper;

    protected final String invalidRequest = "无效的请求";

    /**
     * 单个添加操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertSelective(M model) {
        checkParams(model);
        initParams(model);
        AssertExceptionUtil.isTrue(baseMapper.insertSelective(model) != 1);
    }

    /**
     * 批量添加操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(List<M> models) {
        AssertExceptionUtil.isTrue(models == null || models.size() < 1,invalidRequest);
        assert models != null;
        for (M model : models){
            checkParams(model);
            initParams(model);
        }
        AssertExceptionUtil.isTrue(baseMapper.insertBatch(models) != models.size());
    }

    @Transactional(readOnly = true)
    public M selectByPrimaryKey(ID id) {
        AssertExceptionUtil.isTrue(id == null,invalidRequest);
        return baseMapper.selectByPrimaryKey(id);
    }

    //List<M> selectByParams(BaseQuery baseQuery) throws DataAccessException;

    /**
     * 单个更新
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateByPrimaryKeySelective(M model) {
        checkParams(model);
        initParams(model);
        AssertExceptionUtil.isTrue(baseMapper.updateByPrimaryKeySelective(model) != 1);
    }

    /**
     * 批量更新
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(List<M> models) {
        AssertExceptionUtil.isTrue(models == null || models.size() < 1,invalidRequest);
        assert models != null;
        for (M model : models){
            checkParams(model);
            initParams(model);
        }
        AssertExceptionUtil.isTrue(baseMapper.updateBatch(models) != models.size());
    }

    /**
     * 删除单项
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByPrimaryKey(ID id) {
        AssertExceptionUtil.isTrue(id == null,invalidRequest);
        AssertExceptionUtil.isTrue(baseMapper.deleteByPrimaryKey(id) != 1);
    }

    /**
     * 批量删除
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(ID[] ids) {
        AssertExceptionUtil.isTrue(ids == null || ids.length < 1,invalidRequest);
        assert ids != null;
        for (ID id : ids){
            AssertExceptionUtil.isTrue(id == null,invalidRequest);
        }
        AssertExceptionUtil.isTrue(baseMapper.deleteBatch(ids) != ids.length);
    }

    /**
     * 查询所有
     */
    @Transactional(readOnly = true)
    public ResponseBean selectAll(){
        List<M> models = baseMapper.selectAll();
        return ResponseBean.success(models);
    }

    /**
     * 分页查询
     */
    @Transactional(readOnly = true)
    public ResponseBean queryAndPaged(BaseQuery baseQuery){
        Map<String,Object> map = new HashMap<>(2);
        PageHelper.startPage(baseQuery.pageNum,baseQuery.pageSize);
        List<M> models = baseMapper.queryAndPaged(baseQuery);
        PageInfo<M> pageInfo = new PageInfo<>(models);
        map.put("count",pageInfo.getTotal());
        map.put("list",pageInfo.getList());
        return ResponseBean.success(map);
    }

    /**
     * 检查对象参数合法性(通常是添加或更新操作)
     * @param model 待检查的对象
     */
    protected abstract void checkParams(M model);

    /**
     * 初始化对象参数(通常是添加或更新操作)
     * @param model 待初始化的对象
     */
    protected abstract void initParams(M model);
}
