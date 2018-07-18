package com.sbq.service;


import com.sbq.entity.Dept;
import com.sbq.entity.dto.DeptMemberDto;

import java.util.List;
import java.util.Map;

public interface IAllDeptMemService {

    /**
     * 从数据库获得数据，封装成List<Dept> key:sql中的列名（小写） value：值
     *
     * @return 查不到数据返回空的list
     * @throws Exception
     */
    public String getDeptMemListTree() throws Exception;

    /**
     * 通过设置父子关系
     *
     * @return
     * @throws Exception
     */
    public List<DeptMemberDto> getDeptMemListTree2(long deptId) throws Exception;

    /**
     * 获取部门列表
     *
     * @return 查不到数据返回空的list
     * @throws Exception
     */
    public String getDeptListTree() throws Exception;

    /**
     * app获取最后更新的部门和人员信息
     *
     * @param time 格式 2016-06-20 21:30:00
     * @return
     * @throws Exception
     */
    public Map getLastDeptAndUserList(String time) throws Exception;

    /**
     * app获取全部可用的部门list和人员list
     *
     * @return
     * @throws Exception
     */
    public Map getAllDeptAndUserList() throws Exception;

    /**
     * 缓存中获取全部部门及人员，若没有则加入缓存并返回
     *
     * @return
     * @throws Exception
     */
    public List<Dept> getALLDeptAndUserCache() throws Exception;

    /**
     * 缓存中获取全部部门，若没有则加入缓存并返回
     *
     * @return
     * @throws Exception
     */
    public List<Dept> getALLDeptCache() throws Exception;

    /**
     * 获取所有下级部门ids
     *
     * @param parent_id
     * @return
     */
    public List<String> getDeptIdsByParentId(long parent_id) throws Exception;

}
