package com.cl.crm;

import com.alibaba.fastjson.JSON;
import com.cl.crm.exceptions.AuthFailedException;
import com.cl.crm.exceptions.NoLoginException;
import com.cl.crm.exceptions.ParamsException;
import com.cl.crm.modal.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

        ModelAndView mv = new ModelAndView();
        if(e instanceof NoLoginException){
            mv.setViewName("no_login");
            mv.addObject("code",((NoLoginException) e).getCode());
            mv.addObject("msg",((NoLoginException) e).getMsg());
            mv.addObject("ctx",request.getContextPath());
            return mv;
        }

        /**方法返回值类型判断:
         *    如果方法级别存在@ResponseBody 方法响应内容为json  否则视图
         *    handler 参数类型为HandlerMethod
         * 返回值
         *    视图:默认错误页面
         *
         *    json:错误的json信息
         */

        mv.setViewName("error");
        mv.addObject("code",500);
        mv.addObject("msg","系统异常！");
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            if(null==responseBody){
                /**
                 * 返回视图
                 */
                if(e instanceof ParamsException){
                    mv.addObject("code",((ParamsException) e).getCode());
                    mv.addObject("msg",((ParamsException) e).getMsg());
                }
                if(e instanceof AuthFailedException){
                    mv.addObject("code",((AuthFailedException) e).getCode());
                    mv.addObject("msg",((AuthFailedException) e).getMsg());
                }
                return mv;
            }else{
                /**
                 * 返回json
                 */
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(500);
                resultInfo.setMsg("系统异常！");
                if(e instanceof ParamsException){
                    resultInfo.setCode(((ParamsException) e).getCode());
                    resultInfo.setMsg(((ParamsException) e).getMsg());
                }
                if(e instanceof AuthFailedException){
                    resultInfo.setCode(((AuthFailedException) e).getCode());
                    resultInfo.setMsg(((AuthFailedException) e).getMsg());
                }
                //通过流的形式输出json
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter pw =null;
                try {
                    pw=response.getWriter();
                    pw.write(JSON.toJSONString(resultInfo));
                    pw.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }finally {
                   if(null!=pw){
                       pw.close();
                   }
                }
                return null;
            }
        }else {
            return mv;
        }

    }
}
