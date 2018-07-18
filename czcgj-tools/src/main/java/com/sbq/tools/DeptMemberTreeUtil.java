package com.sbq.tools;


import com.sbq.entity.dto.DeptMemberDto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 把一个list集合,里面的bean含有 parentId 转为树形式
 */
public class DeptMemberTreeUtil {

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public static List<DeptMemberDto> getChildTreeObjects(List<DeptMemberDto> list, int parentId) {
        List<DeptMemberDto> returnList = new ArrayList<DeptMemberDto>();
        for (Iterator<DeptMemberDto> iterator = list.iterator(); iterator.hasNext(); ) {
            DeptMemberDto t = (DeptMemberDto) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParent_id() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     * @author lanyuan Email: mmm333zzz520@163.com
     * @date 2013-12-4 下午7:27:30
     */
    private static void recursionFn(List<DeptMemberDto> list, DeptMemberDto t) {
        List<DeptMemberDto> childList = getChildList(list, t);// 得到子节点列表
        t.setChildren(childList);
        for (DeptMemberDto tChild : childList) {
            if (hasChild(list, tChild)) {// 判断是否有子节点
                // returnList.add(TreeObject);
                Iterator<DeptMemberDto> it = childList.iterator();
                while (it.hasNext()) {
                    DeptMemberDto n = (DeptMemberDto) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    // 得到子节点列表
    private static List<DeptMemberDto> getChildList(List<DeptMemberDto> list, DeptMemberDto t) {

        List<DeptMemberDto> tlist = new ArrayList<DeptMemberDto>();
        Iterator<DeptMemberDto> it = list.iterator();
        while (it.hasNext()) {
            DeptMemberDto n = (DeptMemberDto) it.next();
            if (n.getParent_id() == t.getInt_id()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    private static List<DeptMemberDto> returnList = new ArrayList<DeptMemberDto>();

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list   分类表
     * @param typeId 传入的父节点ID
     * @param prefix 子节点前缀
     */
    public static List<DeptMemberDto> getChildTreeObjects(List<DeptMemberDto> list, int typeId, String prefix) {
        if (list == null)
            return null;
        for (Iterator<DeptMemberDto> iterator = list.iterator(); iterator.hasNext(); ) {
            DeptMemberDto node = (DeptMemberDto) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (node.getParent_id() == typeId) {
                recursionFn(list, node, prefix);
            }
            // 二、遍历所有的父节点下的所有子节点
            /*
             * if (node.getParentId()==0) { recursionFn(list, node); }
			 */
        }
        return returnList;
    }

    private static void recursionFn(List<DeptMemberDto> list, DeptMemberDto node, String p) {
        List<DeptMemberDto> childList = getChildList(list, node);// 得到子节点列表
        if (hasChild(list, node)) {// 判断是否有子节点
            returnList.add(node);
            Iterator<DeptMemberDto> it = childList.iterator();
            while (it.hasNext()) {
                DeptMemberDto n = (DeptMemberDto) it.next();
                recursionFn(list, n, p + p);
            }
        } else {
            returnList.add(node);
        }
    }

    // 判断是否有子节点
    private static boolean hasChild(List<DeptMemberDto> list, DeptMemberDto t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    // 本地模拟数据测试
    public void main(String[] args) {

    }

}
