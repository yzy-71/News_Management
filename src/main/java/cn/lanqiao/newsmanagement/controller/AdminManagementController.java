package cn.lanqiao.newsmanagement.controller;

import cn.lanqiao.newsmanagement.model.dto.PageHelperQuery;
import cn.lanqiao.newsmanagement.model.dto.TAdminDelete;
import cn.lanqiao.newsmanagement.model.dto.TNewsDelete;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.service.AdminManagementService;
import cn.lanqiao.newsmanagement.service.NewsManagementService;
import cn.lanqiao.newsmanagement.utils.PageHelper;
import cn.lanqiao.newsmanagement.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminManagementController {
    @Autowired
    private AdminManagementService adminManagementService;
    // 获取新闻列表, 分页查询
    @RequestMapping("/list")
    public ResponseUtils list(@RequestBody PageHelperQuery pageHelperQuery) {
        try {
            PageHelper<TAdmin> pageHelper = adminManagementService.pageList(pageHelperQuery.getUsername(), pageHelperQuery.getId(), pageHelperQuery.getPassword(), pageHelperQuery.getEmail(), pageHelperQuery.getPhone(), pageHelperQuery.getDate(), pageHelperQuery.getPageNum(), pageHelperQuery.getPageSize());
            return new ResponseUtils<>(200,"分页查询成功",pageHelper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //模糊查询新闻, 分页查询
    @RequestMapping("/search")
    public ResponseUtils fuzzyQueryAdmin(@RequestBody PageHelperQuery pageHelperQuery) {
        try {
            PageHelper<TAdmin> pageHelper = adminManagementService.fuzzyQueryAdmin(pageHelperQuery.getUsername(), pageHelperQuery.getId(), pageHelperQuery.getPassword(), pageHelperQuery.getEmail(), pageHelperQuery.getPhone(), pageHelperQuery.getDate(), pageHelperQuery.getPageNum(), pageHelperQuery.getPageSize());
            return new ResponseUtils<>(200,"分页查询成功",pageHelper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //删除功能
    @RequestMapping("/delete")
    public ResponseUtils delete(@RequestBody TAdminDelete tAdminDelete){
        try {
            int result = adminManagementService.deleteAdmin(tAdminDelete.getId());
            if (result==1){
                return new ResponseUtils(200,"删除成功");
            }else {
                return new ResponseUtils(300,"删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //编辑用户
    @RequestMapping("/update")
    public ResponseUtils update(@RequestBody TAdmin tAdmin) {
        try {
            int result = adminManagementService.updateAdmin(tAdmin);
            if (result == 1) {
                return new ResponseUtils(200, "更新成功");
            } else {
                return new ResponseUtils(300, "更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
