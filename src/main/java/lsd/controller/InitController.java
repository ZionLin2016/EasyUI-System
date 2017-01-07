package lsd.controller;

import javax.servlet.http.HttpSession;

import lsd.service.InitServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 初始化数据库控制器
 * 
 * @author LSD
 * 
 */
@Controller
@RequestMapping("/initController")
public class InitController {

	@Autowired
	private InitServiceI initService;

	/**
	 * 初始化数据库后转向首页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/init")
	public String init(HttpSession session) {
		if (session != null) {
			session.invalidate();// 清除当前session的所有相关信息
		}
		initService.init();
		return "redirect:/";
	}
}
