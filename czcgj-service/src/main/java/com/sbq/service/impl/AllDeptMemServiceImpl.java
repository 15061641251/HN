package com.sbq.service.impl;


import com.sbq.dao.IDeptDao;
import com.sbq.dao.IUserDao;
import com.sbq.entity.Dept;
import com.sbq.entity.dto.DeptMemberDto;
import com.sbq.entity.dto.DeptOrUserDto;
import com.sbq.entity.dto.UserDto;
import com.sbq.service.IAllDeptMemService;
import com.sbq.tools.DeptMemberTreeUtil;
import com.sbq.tools.NullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AllDeptMemServiceImpl implements IAllDeptMemService {

    @Autowired
    private IUserDao memberDao = null;
    @Autowired
    private IDeptDao deptDao = null;

    private static final long parent_id = 0L;

    private static final long dept_idL = 1L;

    private Dept out = null;
    private long patent_dept_id = 0L;

    /**
     * 获取通讯录
     */
    @Override
    public String getDeptMemListTree() throws Exception {

        List<Dept> depts = this.getALLDeptAndUserCache();

        StringBuffer sb = new StringBuffer().append("[");
        return JsonString(sb, depts) + "]";
    }

    @Override
    public List<DeptMemberDto> getDeptMemListTree2(long deptId) throws Exception {

        List<DeptMemberDto> deptMemberList = deptDao.getAllDeptListDeptMemberDto();

        //再查出所有用户
        List<DeptMemberDto> memberList = memberDao.getAllUserListDeptMemberDto();

        List<DeptMemberDto> deptMemberTree = DeptMemberTreeUtil.getChildTreeObjects(deptMemberList, (int) deptId);

        //进行处理,子部门
        if (deptMemberTree != null && deptMemberTree.size() > 0)
            for (int i = 0; i < deptMemberTree.size(); i++)
                doDeptMember(deptMemberTree.get(i), memberList, (int) deptId);

        ///////自身部门加入该部门下的人员
        for (int j = 0; j < memberList.size(); j++) {
            DeptMemberDto member = memberList.get(j);
            if (member.getDept_id() == deptId) {
                //加入子节点
                deptMemberTree.add(member);
            }
        }

        return deptMemberTree;
    }

    private void doDeptMember(DeptMemberDto deptMember, List<DeptMemberDto> memberList, int deptId) {


        long dept_id = deptMember.getInt_id();

        if (deptMember.getType().equals("dept")) {

            for (int j = 0; j < memberList.size(); j++) {
                DeptMemberDto member = memberList.get(j);
                if (member.getDept_id() == dept_id) {
                    //加入子节点
                    deptMember.getChildren().add(member);
                }
            }
            for (int i = 0; i < deptMember.getChildren().size(); i++)
                doDeptMember(deptMember.getChildren().get(i), memberList, deptId);

        }

    }

    /**
     * 获取部门列表
     */
    @Override
    public String getDeptListTree() throws Exception {

        List<Dept> depts = this.getALLDeptCache();

        StringBuffer sb = new StringBuffer().append("[");
        return JsonString(sb, depts) + "]";
    }

    @Override
    public Map getLastDeptAndUserList(String time) throws Exception {

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("time_stamp", time);

        List<DeptOrUserDto> deptList = deptDao.getLastDeptList(paramMap);
        List<DeptOrUserDto> userList = memberDao.getLastUserList(paramMap);

        paramMap.clear();
        paramMap.put("deptList", deptList);
        paramMap.put("userList", userList);

        return paramMap;
    }

    /**
     * app获取全部可用的部门list和人员list
     *
     * @return
     * @throws Exception
     */
    @Override
    public Map getAllDeptAndUserList() throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userList", memberDao.getAllUserList());
        resultMap.put("deptList", deptDao.getAllDeptList());

        return resultMap;
    }


    /**
     * 缓存中获取全部部门及人员，若没有则加入缓存并返回
     *
     * @throws Exception
     */
    @Override
    public List<Dept> getALLDeptAndUserCache() throws Exception {
        List<UserDto> user = memberDao.selectAllMembers();
        List<Dept> deptList = deptDao.selectAllDepts();

        deptList = searchAllDept(deptList, user);

        return deptList;
    }

    /**
     * 缓存中获取全部部门，若没有则加入缓存并返回
     *
     * @throws Exception
     */
    @Override
    public List<Dept> getALLDeptCache() throws Exception {

        List<UserDto> user = new ArrayList<>();
        List<Dept> deptList = deptDao.selectAllDepts();

        return searchAllDept(deptList, user);
    }

    @Override
    public List<String> getDeptIdsByParentId(long parent_id) throws Exception {

        List<Dept> depts = this.getALLDeptCache();

        out = null;
        depts.set(0, getThisDept(depts.get(0), parent_id));

        StringBuffer sb = new StringBuffer();
        return Arrays.asList(getDept_ids(sb, depts).split(","));
    }


    //获取指定id的部门列表
    private Dept getThisDept(Dept dept, long patent_dept_id) {
        if (patent_dept_id == dept.getInt_id()) {
            out = dept;
        } else if (!NullUtil.isNull(dept.getChildDepts())) {
            for (int i = 0; i < dept.getChildDepts().size(); i++) {
                if (NullUtil.isNull(out)) {
                    getThisDept(dept.getChildDepts().get(i), patent_dept_id);
                } else {
                    break;
                }
            }
        }
        return out;
    }

    private List<Dept> searchAllDept(List<Dept> listAllObject, List<UserDto> memList) throws Exception {
        List<Dept> listAllDept = new ArrayList<Dept>();

        for (Dept row : listAllObject) {
            if (row.getParent_id() == parent_id) {
                listAllDept.add(row);
                fomateDept(listAllObject, row, memList);
            }
        }
        return listAllDept;
    }

    private void fomateDept(List<Dept> tmp, Dept dept, List<UserDto> memList) {

        for (Dept row : tmp) {
            if (row.getParent_id() == dept.getInt_id()) {
                List<Dept> list = dept.getChildDepts();
                if (null == list) {
                    list = new ArrayList<Dept>();
                }
                if (row.getInt_id() != dept_idL) {
                    list.add(row);
                    dept.setChildDepts(list);
                }
                fomateDept(tmp, row, memList);
            }
        }
        List<UserDto> deptMemList = getMemberByDeptId(dept, memList);
        dept.setMemList(deptMemList);
    }

    private List<UserDto> getMemberByDeptId(Dept d, List<UserDto> memList) {
        List<UserDto> deptMemList = null;
        for (UserDto mem : memList) {

            if (Objects.equals(d.getInt_id(), mem.getDept_id())) {

                if (null == deptMemList) {
                    deptMemList = new ArrayList<UserDto>();
                }
                deptMemList.add(mem);
            }
        }
        if (null != deptMemList) {
            memList.removeAll(deptMemList);
        }
        return deptMemList;
    }

    public Boolean isexist(String ids, String id) {
        Boolean flag = false;
        if (null != ids && !"".equals(ids)) {
            String[] array = ids.split(",");
            for (String anArray : array) {
                if (anArray.equals(id)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    private String getDept_ids(StringBuffer sb, List<Dept> deptList) throws Exception {

        if (deptList.size() > 0) {
            for (Dept dept : deptList) {
                sb.append(dept.getInt_id()).append(",");
                if (null != dept.getChildDepts()) {
                    getDept_ids(sb, dept.getChildDepts());
                }
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    public String JsonString(StringBuffer sb, List<Dept> deptList) throws Exception {

        if (deptList.size() > 0) {
            for (int i = 0; i < deptList.size(); i++) {
                Dept dept = deptList.get(i);

                if (!NullUtil.isNull(dept.getChildDepts())) {
                    sb.append("{\"name\":\"").append(dept.getDept_name()).append("\",\"type\":\"").append("dept").append("\",\"id\":\"").append(dept.getInt_id()).append("\",\"mobile\":\"").append("");
                    sb.append("\",\"mobile2\":\"").append("");
                    sb.append("\",\"parent_id\":\"").append(dept.getParent_id()).append("\",\"email\":\"").append("").append("\",\"sex\":\"").append("").append("\",\"address\":\"").append("");
                    sb.append("\",\"tel\":\"").append(dept.getTel()).append("\",\"account\":\"").append("").append("\",\"dept_id\":\"").append("").append("\",\"order_index\":\"").append(dept.getOrder_index());
                    sb.append("\",\"dept_short\":\"").append(dept.getDept_short()).append("\",\"fax\":\"").append(dept.getFax());
                    sb.append("\",\"head_url\":\"").append("");
                    sb.append("\"");
                    sb.append(",\"children\":[");
                    if (!NullUtil.isNull(dept.getMemList())) {
                        List<UserDto> memList = dept.getMemList();
                        for (UserDto mem : memList) {
                            sb.append("{\"name\":\"").append(mem.getUsername()).append("\",\"type\":\"").append("member").append("\",\"id\":\"").append(mem.getInt_id()).append("\",\"mobile\":\"").append(mem.getMobile());
                            sb.append("\",\"mobile2\":\"").append(mem.getMobile2()).append("\",\"parent_id\":\"").append(mem.getDept_id());
                            sb.append("\",\"email\":\"").append(mem.getEmail()).append("\",\"sex\":\"").append(mem.getSex()).append("\",\"address\":\"").append(mem.getAddress());
                            sb.append("\",\"tel\":\"").append(mem.getTel()).append("\",\"dept_short\":\"").append("").append("\",\"fax\":\"").append("").append("\",\"order_index\":\"").append(mem.getOrder_index());
                            sb.append("\",\"account\":\"").append(mem.getAccount()).append("\",\"dept_id\":\"").append(mem.getDept_id()).append("\",\"head_url\":\"").append(mem.getHead_url());
                            sb.append("\"");
                            sb.append("},");
                        }
                    }
                    JsonString(sb, dept.getChildDepts());
                    sb.deleteCharAt(sb.toString().length() - 1);
                    sb.append("]},");
                } else {
                    sb.append("{\"name\":\"").append(dept.getDept_name()).append("\",\"type\":\"").append("dept").append("\",\"id\":\"").append(dept.getInt_id()).append("\",\"mobile\":\"").append("");
                    sb.append("\",\"mobile2\":\"").append("");
                    sb.append("\",\"parent_id\":\"").append(dept.getParent_id()).append("\",\"email\":\"").append("").append("\",\"sex\":\"").append("").append("\",\"address\":\"").append("");
                    sb.append("\",\"tel\":\"").append(dept.getTel()).append("\",\"account\":\"").append("").append("\",\"dept_id\":\"").append("");
                    sb.append("\",\"dept_short\":\"").append(dept.getDept_short()).append("\",\"fax\":\"").append(dept.getFax()).append("\",\"order_index\":\"").append(dept.getOrder_index());
                    sb.append("\",\"head_url\":\"").append("");
                    sb.append("\"");
                    sb.append(",\"children\":[");
                    if (!NullUtil.isNull(dept.getMemList())) {
                        List<UserDto> memList = dept.getMemList();
                        for (UserDto mem : memList) {
                            sb.append("{\"name\":\"").append(mem.getUsername()).append("\",\"type\":\"").append("member").append("\",\"id\":\"").append(mem.getInt_id()).append("\",\"mobile\":\"").append(mem.getMobile());
                            sb.append("\",\"mobile2\":\"").append(mem.getMobile2()).append("\",\"parent_id\":\"").append(mem.getDept_id());
                            sb.append("\",\"email\":\"").append(mem.getEmail()).append("\",\"sex\":\"").append(mem.getSex()).append("\",\"address\":\"").append(mem.getAddress());
                            sb.append("\",\"tel\":\"").append(mem.getTel()).append("\",\"dept_short\":\"").append("").append("\",\"fax\":\"").append("").append("\",\"order_index\":\"").append(mem.getOrder_index());
                            sb.append("\",\"account\":\"").append(mem.getAccount()).append("\",\"dept_id\":\"").append(mem.getDept_id()).append("\",\"head_url\":\"").append(mem.getHead_url());
                            sb.append("\"");
                            sb.append("},");
                        }
                        sb.deleteCharAt(sb.toString().length() - 1);
                    }
                    sb.append("]");
                    sb.append("},");
                }
            }
            return sb.toString().substring(0, sb.toString().length() - 1);
        } else {
            return sb.toString();
        }
    }


}
