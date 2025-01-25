package com.congyan.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.congyan.entity.RestBean;
import com.congyan.entity.vo.request.UserReDataVO;
import com.congyan.service.DysarthriaTestService;
import com.congyan.service.UserDataService;
import com.congyan.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/userData")
@Tag(name = "处理用户数据",description = "保存、分析数据")
public class UserDataController {

    @Resource
    UserDataService UserDataService;
    @Resource
    JwtUtils utils;

    /**
     * 保存用户测评数据
     * @param request 请求
     * @param userReDataVO 测评数据
     * @return 成功保存
     */
    @PostMapping("/saveUserTrainDta")
    @Operation(summary = "保存用户的测评数据")
    public RestBean<Void> SaveUserTrainData(HttpServletRequest request, @RequestBody
                                                @Valid UserReDataVO userReDataVO){
        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        Integer id = null;
        try {
            id = utils.toId(jwt);
        } catch (Exception e) {
            return RestBean.failure(2002,"令牌错误");
        }
        try {
            UserDataService.saveUserTrainData(id,userReDataVO);
        } catch (Exception e) {
            return RestBean.failure(1002,"数据库问题");
        }
        return RestBean.success();
    }
}
