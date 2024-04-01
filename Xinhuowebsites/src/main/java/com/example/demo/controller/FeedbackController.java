package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.Utils.R;
import com.example.demo.Utils.TokenUtil;
import com.example.demo.mapper.FeedbackMapper;
import com.example.demo.pojo.Feedback;
import com.example.demo.pojo.Show;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class FeedbackController {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @RequestMapping(value="ListAll/{start}/{pageSize}/{isHandle}/{isDelete}",method = {RequestMethod.GET,RequestMethod.POST})
    public R<?>ListAll(@PathVariable int start,@PathVariable int pageSize, @PathVariable boolean isHandle,@PathVariable boolean isDelete){
        Page<Feedback> page=new Page<>(start,pageSize);//查询第start页，显示pageSize条数据

        QueryWrapper<Feedback> wrapper= new QueryWrapper<>();
        wrapper.eq("is_handle",isHandle);
        wrapper.eq("is_delete",isDelete);
        //查询条件
        IPage<Feedback> iPage = feedbackMapper.selectPage(page, wrapper);
        System.out.println("数据总条数："+iPage.getTotal());
        System.out.println("数据总页数："+iPage.getPages());
        System.out.println("当前的页数："+iPage.getCurrent());
        //获取该页数的数据
        List<Feedback> records = iPage.getRecords();
        Show<Feedback,Long> feedbackShow = new Show<>(records, iPage.getTotal());
        return R.ok(feedbackShow);
    }

    @RequestMapping(value="addFeedback",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public R<?> addFeedback(HttpServletRequest request,@RequestBody Feedback feedback) {
        String token = request.getHeader("token");
        User user = TokenUtil.getUser(token);
        feedback.setContact(user.getContact());
        System.out.println(feedback.getContent());
        int i = feedbackMapper.insert(feedback);
        if (i == 1) {
            return R.ok("反馈成功");
        } else {
            return R.error().message("反馈失败");
        }


    }
    @RequestMapping(value="handleFeedback/{id}/{handle}",method = {RequestMethod.GET,RequestMethod.POST})
    public R<?> handleFeedback(@PathVariable int id, @PathVariable String handle) {
        UpdateWrapper<Feedback> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        Feedback feedback = feedbackMapper.selectById(id);
        if (!feedback.getisHandle()) {
            feedback.setHandle(handle);
            feedback.setisHandle(true);
            int update = feedbackMapper.update(feedback, updateWrapper);
            if(update==1&& feedback.getisHandle()){
                return R.ok(feedback);
            }
            else{
                return R.error().message("反馈失败");
            }
        } else {

            return R.error().message("已处理");
        }

    }
    @RequestMapping(value="deleteFeedback/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public R<?> deleteFeedback(@PathVariable int id) {
        Feedback feedback = feedbackMapper.selectById(id);
        UpdateWrapper<Feedback> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        feedback.setisDelete(true);
        int i = feedbackMapper.update(feedback,updateWrapper);
        feedback.setisDelete(true);
        if (feedback.getisDelete() && i == 1) {
            return R.ok(feedback);
        } else {
            return R.error().message("出现错误");
        }

    }
    @RequestMapping(value="vague/{keyword}/{start}/{pageSize}",method = {RequestMethod.GET,RequestMethod.POST})
    public R<?>List(@PathVariable int pageSize, @PathVariable String keyword,@PathVariable int start){
        Page<Feedback> page=new Page<>(start,5);//查询第start页，显示5条数据
        QueryWrapper<Feedback> wrapper= new QueryWrapper<>();
        wrapper.like("content",keyword);
        Page<Feedback> articlePage = this.feedbackMapper.selectPage(page, wrapper);
        System.out.println("数据总条数："+articlePage.getTotal());
        System.out.println("数据总页数："+articlePage.getPages());
        System.out.println("当前的页数："+articlePage.getCurrent());
        //获取该页数的数据
        List<Feedback> records = articlePage.getRecords();
        Show<Feedback,Long> feedbackShow = new Show<>(records, articlePage.getTotal());
        System.out.println(feedbackShow);
        return R.ok(feedbackShow);
    }


}
