package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.LbPost;
import com.cmtt.base.entity.LbSubject;
import com.cmtt.base.entity.LbUserCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmtt.base.entity.SysPermission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户收藏文章表 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
public interface LbUserCollectMapper extends BaseMapper<LbUserCollect> {

    /**
     * 根据用户id获取当前用户收藏的文章
     * @param id
     * @return
     */
    @Select("select * from lb_post where id in (select user_id from lb_user_collect where user_id =#{id})")
    IPage<LbPost> getMyCollectPostByUserId(IPage<LbPost> page,Integer id);
}
