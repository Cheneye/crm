package com.cl.base;


import com.cl.crm.modal.ResultInfo;
import com.cl.crm.modal.UserModal;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public class BaseController {


    @ModelAttribute
    public void preHandler(HttpServletRequest request){
        request.setAttribute("ctx", request.getContextPath());
    }

    public ResultInfo success(String msg){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(msg);
        return resultInfo;
    }
    public ResultInfo success(String msg,UserModal userModal){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setResult(userModal);
        resultInfo.setMsg(msg);
        return resultInfo;
    }


}
