package com.jiangzhiyan.vhr.base;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JiangZhiyan
 */
@Repository
public interface BaseMapper<M,ID> {
    
    Integer insertSelective(M model) throws DataAccessException;

    Integer insertBatch(@Param("models") List<M> models) throws DataAccessException;

    M selectByPrimaryKey(ID id) throws DataAccessException;

    Integer updateByPrimaryKeySelective(M model) throws DataAccessException;

    Integer updateBatch(List<M> models) throws DataAccessException;

    Integer deleteByPrimaryKey(ID id) throws DataAccessException;

    Integer deleteBatch(@Param("ids") ID[] ids) throws DataAccessException;

    List<M> selectAll() throws DataAccessException;

    M selectByName(String name);

    List<M> queryAndPaged(BaseQuery baseQuery) throws DataAccessException;
}

