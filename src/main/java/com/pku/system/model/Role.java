package com.pku.system.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Role {
    private int r_id;
    private String r_name;
    private List<Integer> p_ids;
    private String p_idsStr;

    public List<Integer> getP_ids() {
        if(p_ids == null) {
            p_ids = new ArrayList<Integer>();
        }
        return p_ids;
    }

    public void setP_ids(List<Integer> p_ids) {
        this.p_ids = p_ids;
    }

    public String getP_idsStr() {
        if(CollectionUtils.isEmpty(p_ids)) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for(Integer p_id : p_ids) {
            s.append(p_id);
            s.append(",");
        }
        p_idsStr = s.toString();
        return p_idsStr;
    }

    public void setP_idsStr(String p_idsStr) {
        if(StringUtils.isEmpty(p_idsStr)) {
            return;
        }
        String[] p_idStrs = p_idsStr.split(",");
        for(String p_idStr : p_idStrs) {
            if(StringUtils.isEmpty(p_idStr)) {
                continue;
            }
            getP_ids().add(Integer.valueOf(p_idStr));
        }
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }
}
