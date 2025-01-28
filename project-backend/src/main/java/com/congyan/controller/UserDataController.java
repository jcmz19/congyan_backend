package com.congyan.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.congyan.entity.RestBean;
import com.congyan.entity.vo.request.UserReDataVO;
import com.congyan.entity.vo.response.UserTrainSummaryAnalysisVO;
import com.congyan.service.UserDataService;
import com.congyan.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
        Integer id = null;
        try {
           id = getUserId(request);
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

    /**
     * 查询用户历史数据，进行总体分析
     * @param request 请求
     * @return 返回一个根据时间指数衰减得到的分析数据结构
     */
    @GetMapping("/getSummaryAnalysis")
    @Operation(summary = "用户总体数据分析（时间衰减）")
    public RestBean<UserTrainSummaryAnalysisVO> getSummaryAnalysis(HttpServletRequest request){
        Integer id = null;
        try {
            id = getUserId(request);
        } catch (Exception e) {
            return RestBean.failure(2002,"令牌错误");
        }

        try {
            return RestBean.success(UserDataService.userTrainDataSummaryAnalysis(id));
        } catch (Exception e) {
            return RestBean.failure(2009,"数据库格式问题，请联系管理员");
        }

    }


    private Integer getUserId(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        return utils.toId(jwt);
    }
}
