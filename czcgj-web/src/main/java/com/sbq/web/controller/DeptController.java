package com.sbq.web.controller;

import com.sbq.annotation.RequestLog;
import com.sbq.entity.Dept;
import com.sbq.entity.dto.UserDto;
import com.sbq.service.IAllDeptMemService;
import com.sbq.service.IDeptService;
import com.sbq.tools.Constant;
import com.sbq.tools.JsonUtil;
import com.sbq.tools.NullUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/dept")
public class DeptController extends BaseController {

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IAllDeptMemService iAllDeptMemService = null;

    @Autowired
    private IAllDeptMemService allDeptMemService;

    /**
     * 进入部门管理界面
     */
    @RequestMapping(value = "deptManager")
    public String deptManager() {
        return "dept/manager";
    }

    /**
     * 进入部门选择页
     * 使用页面：通知公告
     */
    @RequestMapping(value = "/showDeptUser")
    public String showDeptUser(HttpServletRequest request, HttpServletResponse response, Model model, @Param("ids") String ids) {
        model.addAttribute("ids", ids);
        return "common/deptUserList";
    }

    /**
     * 人员单选
     */
    @RequestMapping(value = "/chooseOneUser")
    public String chooseOneUser(HttpServletRequest request, HttpServletResponse response, Model model, @Param("ids") String ids) {
        model.addAttribute("ids", ids);
        String formtype=request.getParameter("formtype");
        request.setAttribute("formtype", formtype);
        return "common/chooseOneUser";
    }

    /**
     * 人员列表
     */
    @RequestMapping(value = "/chooseDeptAndUser")
    public String chooseDeptAndUser(HttpServletRequest request, HttpServletResponse response, Model model, @Param("ids_id") String ids_id, @Param("flag") String flag) {
        return "common/chooseDeptAndUser";
    }

    /**
     * 单选部门
     * ztree 单选
     */
    @RequestMapping(value = "/chooseOneDept")
    public String chooseOneDept(HttpServletRequest request, HttpServletResponse response, Model model, @Param("id") String id, @Param("flag") String flag) {
        model.addAttribute("id", id);
        model.addAttribute("flag", flag);
        return "common/chooseOneDept";
    }


    /**
     * 呈现部门树
     *
     * @throws Exception
     */
    @RequestMapping(value = "/getDeptTree")
    public void getDeptTree(HttpServletRequest request, HttpServletResponse response) {
        try {
            String selectIds = request.getParameter("selectIds");

            List<String> selectIdsList = new ArrayList<String>();
            if (!NullUtil.isNull(selectIds)) {
                selectIdsList = Arrays.asList(selectIds.split(","));
            }
            String json = "";

            List<Dept> deptList = iAllDeptMemService.getALLDeptCache();
            if (deptList.size() > 0) {
                StringBuffer sb = new StringBuffer().append("[");
                String msg = toJsonString(sb, deptList, selectIdsList);
                json = msg + "]";
            }
            JsonUtil.printJson(response, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String toJsonString(StringBuffer sb, List<Dept> deptList, List<String> list) throws Exception {
        if (deptList.size() > 0) {
            for (int i = 0; i < deptList.size(); i++) {
                Dept dept = deptList.get(i);

                if (!NullUtil.isNull(dept.getChildDepts())) {
                    sb.append("{\"name\":").append("\"").append(dept.getDept_name().trim()).append("\",\"id\":\"").append(dept.getInt_id()).append("\",\"parentid\":\"").append(dept.getParent_id());
                    sb.append("\",\"flag\":\"0\"");
                    if (exist(list, dept.getInt_id())) {
                        sb.append(",checked:true");
                    }
                    sb.append(",\"children\":[");
                    toJsonString(sb, dept.getChildDepts(), list);
                    sb.deleteCharAt(sb.toString().length() - 1);
                    sb.append("]},");
                } else {
                    sb.append("{\"name\":\"").append(dept.getDept_name().trim()).append("\",\"id\":\"").append(dept.getInt_id()).append("\",\"parentid\":\"").append(dept.getParent_id()).append("\"");
                    sb.append(",\"flag\":\"0\"");
                    if (exist(list, dept.getInt_id())) {
                        sb.append(",checked:true");
                    }
                    sb.append("},");
                }
            }
            return sb.toString().substring(0, sb.toString().length() - 1);
        } else {
            return sb.toString();
        }
    }

    /**
     * 获得部门人员树json
     *
     * @throws Exception
     */
    @RequestMapping(value = "/getDeptMemListTree", method = RequestMethod.POST)
    public void getDeptMemListTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String selectIds = request.getParameter("selectIds");

        List<Long> selectIdsList = new ArrayList<Long>();
        if (selectIds != "" && null != selectIds) {
            String[] id = selectIds.split(",");
            for (String anId : id) {
                selectIdsList.add(Long.valueOf(anId));
            }
        }

        List<Dept> deptList = iAllDeptMemService.getALLDeptAndUserCache();

        StringBuffer sb = new StringBuffer().append("[");
        String msg = toJsonString2(sb, deptList, selectIdsList);
        String json = msg + "]";
        JsonUtil.printJson(response, json);
    }


    private String toJsonString2(StringBuffer sb, List<Dept> deptList, List<Long> list) {
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);

            if (!NullUtil.isNull(dept.getChildDepts())) {
                sb.append("{\"name\":" + "\"").append(dept.getDept_name()).append("\",\"id").append("\":\"").append(dept.getInt_id()).append("\",\"flag\":\"0\",open:false");
                sb.append(",\"children\":[");
                if (!NullUtil.isNull(dept.getMemList())) {
                    List<UserDto> memList = dept.getMemList();
                    for (UserDto mem : memList) {
                        boolean exist = existMember(list, mem.getInt_id());
                        if (exist) {
                            sb.append("{\"name\":" + "\"").append(mem.getUsername()).append("\",\"id\":\"").append(mem.getInt_id()).append("\",\"flag\":\"1\",\"iconSkin\":\"icon03\",checked:true},");
                        } else {
                            sb.append("{\"name\":" + "\"").append(mem.getUsername()).append("\",\"id\":\"").append(mem.getInt_id()).append("\",\"flag\":\"1\",\"iconSkin\":\"icon03\"},");
                        }
                    }
                }
                toJsonString2(sb, dept.getChildDepts(), list);
                sb.deleteCharAt(sb.toString().length() - 1);
                sb.append("]},");
            } else {
                sb.append("{\"name\":" + "\"" + dept.getDept_name() + "\",\"id" + "\":\"" + dept.getInt_id() + "\",\"flag\":\"0\",open:false");
                if (!NullUtil.isNull(dept.getMemList())) {
                    sb.append(",\"children\":[");
                    List<UserDto> memList = dept.getMemList();
                    for (UserDto mem : memList) {
                        boolean exist = existMember(list, mem.getInt_id());
                        if (exist) {
                            sb.append("{\"name\":" + "\"").append(mem.getUsername()).append("\",\"id\":\"").append(mem.getInt_id()).append("\",\"flag\":\"1\",\"iconSkin\":\"icon03\",checked:true},");
                        } else {
                            sb.append("{\"name\":" + "\"").append(mem.getUsername()).append("\",\"id\":\"").append(mem.getInt_id()).append("\",\"flag\":\"1\",\"iconSkin\":\"icon03\"},");
                        }
                    }
                    sb.deleteCharAt(sb.toString().length() - 1);
                    sb.append("]");
                }
                sb.append("},");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    private static boolean exist(List<String> list, long x) {
        boolean flag = false;
        String xSt = String.valueOf(x);
        for (String aList : list) {
            if (aList.equals(xSt)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private static boolean existMember(List<Long> list, long x) {
        boolean flag = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == x) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @RequestLog(moduleName = "获取部门信息")
    @RequestMapping(value = "/getDeptDetail")
    public
    @ResponseBody
    Map<String, Object> getDeptDetail(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> p = new HashMap<String, Object>();

        String int_id = request.getParameter("int_id");
        if (!NullUtil.isBlank(int_id)) {
            Dept dept = deptService.selectByPrimaryKey(Long.parseLong(int_id));

            p.put("returnCode", Constant.CODE_SUCCESS);
            p.put("description", Constant.CODE_SUCCESS_MSG);
            p.put("result", dept);
        } else {
            p.put("returnCode", Constant.CODE_REQUEST_ERROR);
            p.put("description", Constant.CODE_REQUEST_ERROR_MSG);
        }
        return p;
    }

    @RequestLog(moduleName = "修改部门")
    @RequestMapping(value = "/editDept")
    public
    @ResponseBody
    Map<String, Object> editDept(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> p = new HashMap<String, Object>();

        String int_id = request.getParameter("int_id");
        String dept_name = request.getParameter("dept_name");
        String tel = request.getParameter("tel");
        String stateflag = request.getParameter("stateflag");

        if (!NullUtil.isBlank(int_id)) {
            Dept dept = deptService.selectByPrimaryKey(Long.parseLong(int_id));
            dept.setTime_stamp(new Date());
            if (!NullUtil.isBlank(dept_name)) {
                dept.setDept_name(dept_name);
            }
            if (!NullUtil.isBlank(tel)) {
                dept.setTel(tel);
            }
            if (!NullUtil.isBlank(stateflag)) {
                dept.setStateflag(1);
            }
            this.deptService.updateByPrimaryKeySelective(dept);

            p.put("returnCode", Constant.CODE_SUCCESS);
            p.put("description", Constant.CODE_SUCCESS_MSG);
        } else {
            p.put("returnCode", Constant.CODE_REQUEST_ERROR);
            p.put("description", Constant.CODE_REQUEST_ERROR_MSG);
        }
        return p;
    }

    @RequestLog(moduleName = "删除部门")
    @RequestMapping(value = "/delDept")
    public
    @ResponseBody
    Map<String, Object> delDept(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> p = new HashMap<String, Object>();

        String int_id = request.getParameter("int_id");

        if (!NullUtil.isBlank(int_id)) {
            Dept dept = deptService.selectByPrimaryKey(Long.parseLong(int_id));
            dept.setTime_stamp(new Date());

            dept.setStateflag(1);
            this.deptService.updateByPrimaryKeySelective(dept);

            p.put("returnCode", Constant.CODE_SUCCESS);
            p.put("description", Constant.CODE_SUCCESS_MSG);
        } else {
            p.put("returnCode", Constant.CODE_REQUEST_ERROR);
            p.put("description", Constant.CODE_REQUEST_ERROR_MSG);
        }
        return p;
    }

    @RequestLog(moduleName = "添加部门")
    @RequestMapping(value = "/addDept")
    public
    @ResponseBody
    Map<String, Object> addDept(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> p = new HashMap<String, Object>();

        String dept_name = request.getParameter("dept_name");
        String tel = request.getParameter("tel");
        String parentId = request.getParameter("parentId");

        if (!NullUtil.isBlank(dept_name, parentId)) {
            Dept dept = new Dept();
            dept.setCreate_time(new Date());
            dept.setTime_stamp(new Date());
            dept.setDept_name(dept_name);
            dept.setParent_id(Long.parseLong(parentId));
            if (!NullUtil.isBlank(tel)) {
                dept.setTel(tel);
            }
            int int_id = this.deptService.insertSelective(dept);

            p.put("returnCode", Constant.CODE_SUCCESS);
            p.put("description", Constant.CODE_SUCCESS_MSG);
            p.put("int_id", int_id);
        } else {
            p.put("returnCode", Constant.CODE_REQUEST_ERROR);
            p.put("description", Constant.CODE_REQUEST_ERROR_MSG);
        }
        return p;
    }
}
