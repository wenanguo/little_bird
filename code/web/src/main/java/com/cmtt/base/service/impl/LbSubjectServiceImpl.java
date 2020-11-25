package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.LbSubject;
import com.cmtt.base.mapper.LbSubjectMapper;
import com.cmtt.base.service.ILbSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 栏目表 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@Service
public class LbSubjectServiceImpl extends ServiceImpl<LbSubjectMapper, LbSubject> implements ILbSubjectService {

    /**
     * 获取栏目及栏目下文章数据
     * @param page
     * @return
     */
    public IPage<LbSubject> getLbSubjectPostList(IPage<LbSubject> page){
        return this.baseMapper.getLbSubjectPostList(page);
    }
}
