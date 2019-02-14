package com.htkfood.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.htkfood.entity.CommentEntity;
import com.htkfood.exception.CommonException;
import com.htkfood.interceptor.PrivilegeConstant;
import com.htkfood.interceptor.annotation.Privileges;
import com.htkfood.service.CommentService;
import com.htkfood.util.ResultHelper;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@Privileges(PrivilegeConstant.OPEN_PAGE)
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public Map<String, Object> addComment(@RequestParam Long expressId,@RequestParam String t,@RequestParam String comment,@RequestParam Integer score1,@RequestParam Integer score2,@RequestParam Integer score3,@RequestParam String imgs) throws CommonException {
		Map<String,Object>result=ResultHelper.success();
		commentService.insertComment(expressId, t, comment, score1, score2, score3,imgs);
		return result;
	}
	@Privileges(PrivilegeConstant.OPEN_PAGE)
	@RequestMapping(value = "/getComment", method = RequestMethod.POST)
	public Map<String, Object> getComment(@RequestParam Long expressId,@RequestParam String t) throws CommonException {
		Map<String,Object>result=ResultHelper.success();
		CommentEntity commentEntity=commentService.getComment(expressId, t);
		if(commentEntity!=null) {
			result.put("comment", commentEntity);
		}
		return result;
	}
}
