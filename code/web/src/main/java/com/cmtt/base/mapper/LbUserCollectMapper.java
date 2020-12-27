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
    @Select("select id,periodical_id,periodical_title,title,share_title,share_content,quote_title,quote_desc,description,post_subject_id,post_subject,post_catalog_id,tcolor,post_catalog,show_type,img_url,preimg_url,link_url,theme_info,author,post_order,is_free,published_at,praise_count,record_count,read_count,recommend,status,update_time,create_time from lb_post where id in (select post_id from lb_user_collect where user_id =#{id})")
    IPage<LbPost> getMyCollectPostByUserId(IPage<LbPost> page,Integer id);
}
