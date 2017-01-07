package lsd.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lsd.pageModel.DataGrid;
import lsd.pageModel.Json;
import lsd.pageModel.PageHelper;
import lsd.pageModel.SessionInfo;
import lsd.pageModel.User;
import lsd.service.ResourceServiceI;
import lsd.service.RoleServiceI;
import lsd.service.UserServiceI;
import lsd.util.ConfigUtil;
import lsd.util.IpUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {

	@Autowired
	private UserServiceI userService;

	@Autowired
	private RoleServiceI roleService;

	@Autowired
	private ResourceServiceI resourceService;

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Json login(User user, HttpSession session, HttpServletRequest request) {
		Json j = new Json();
		User u = userService.login(user);
		if (u != null) {
			j.setSuccess(true);
			j.setMsg("登录成功！");

			SessionInfo sessionInfo = new SessionInfo();
			BeanUtils.copyProperties(u, sessionInfo);// __________
			sessionInfo.setIp(IpUtil.getIpAddr(request));
			sessionInfo.setResourceList(userService.resourceList(u.getId()));
			session.setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);

			j.setObj(sessionInfo);
		} else {
			j.setMsg("用户名或密码错误！");
		}
		return j;
	}

	/**
	 * 用户登录时的autocomplete
	 * 
	 * @param q
	 *            参数
	 * @return
	 */
	@RequestMapping("/loginCombobox")
	@ResponseBody
	public List<User> loginCombobox(String q) {
		return userService.loginCombobox(q);
	}

	/**
	 * 用户登录时的combogrid
	 * 
	 * @param q
	 * @param ph
	 * @return
	 */
	@RequestMapping("/loginCombogrid")
	@ResponseBody
	public DataGrid loginCombogrid(String q, PageHelper ph) {
		return userService.loginCombogrid(q, ph);
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reg")
	public Json reg(User user) {
		Json j = new Json();
		try {
			userService.reg(user);
			j.setSuccess(true);
			j.setMsg("注册成功！新注册的用户没有任何权限，请让管理员赋予权限后再使用本系统");
			j.setObj(user);
		} catch (Exception e) {
			// TODO: handle exception
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public Json logout(HttpSession session) {
		Json j = new Json();
		if (session != null) {
			session.invalidate();
		}
		j.setSuccess(true);
		j.setMsg("注销成功！");
		return j;
	}

	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager() {
		return "/admin/user";
	}

	/**
	 * 获取用户数据表格
	 * 
	 * @param user
	 * @param ph
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(User user, PageHelper ph) {
		return userService.dataGrid(user, ph);
	}

	/**
	 * 跳转到添加用户页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		User u = new User();
		u.setId(UUID.randomUUID().toString());
		request.setAttribute("user", u);
		return "/admin/userAdd";
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(User user) {
		Json j = new Json();
		try {
			userService.add(user);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(user);
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 跳转到用户修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		User u = userService.get(id);
		request.setAttribute("user", u);
		return "/admin/userEdit";
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(User user) {
		Json j = new Json();
		try {
			userService.edit(user);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(user);
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		if (id != null && !id.equalsIgnoreCase(sessionInfo.getId())) {// 不能删除自己
			userService.delete(id);
		}
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 批量删除用户
	 * 
	 * @param ids
	 *            ('0','1','2')
	 * @return
	 */
	@RequestMapping("/batchDelete")
	@ResponseBody
	public Json batchDelete(String ids, HttpSession session) {
		Json j = new Json();
		if (ids != null && ids.length() > 0) {
			for (String id : ids.split(",")) {
				if (id != null) {
					this.delete(id, session);
				}
			}
		}
		j.setMsg("批量删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 跳转到用户授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/grantPage")
	public String grantPage(String ids, HttpServletRequest request) {
		request.setAttribute("ids", ids);
		if (ids != null && !ids.equalsIgnoreCase("") && ids.indexOf(",") == -1) {
			User u = userService.get(ids);
			request.setAttribute("user", u);
		}
		return "/admin/userGrant";
	}

	/**
	 * 用户授权
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/grant")
	@ResponseBody
	public Json grant(String ids, User user) {
		Json j = new Json();
		userService.grant(ids, user);
		j.setSuccess(true);
		j.setMsg("授权成功！");
		return j;
	}

	/**
	 * 跳转到编辑用户密码页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/editPwdPage")
	public String editPwdPage(String id, HttpServletRequest request) {
		User u = userService.get(id);
		request.setAttribute("user", u);
		return "/admin/userEditPwd";
	}

	/**
	 * 编辑用户密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/editPwd")
	@ResponseBody
	public Json editPwd(User user) {
		Json j = new Json();
		userService.editPwd(user);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

	/**
	 * 跳转到编辑自己的密码页面
	 * 
	 * @return
	 */
	@RequestMapping("/editCurrentUserPwdPage")
	public String editCurrentUserPwdPage() {
		return "/user/userEditPwd";
	}

	/**
	 * 修改自己的密码
	 * 
	 * @param session
	 * @param pwd
	 * @return
	 */
	@RequestMapping("/editCurrentUserPwd")
	@ResponseBody
	public Json editCurrentUserPwd(HttpSession session, String oldPwd, String pwd) {
		Json j = new Json();
		if (session != null) {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
			if (sessionInfo != null) {
				if (userService.editCurrentUserPwd(sessionInfo, oldPwd, pwd)) {
					j.setSuccess(true);
					j.setMsg("编辑密码成功，下次登录生效！");
				} else {
					j.setMsg("原密码错误！");
				}
			} else {
				j.setMsg("登录超时，请重新登录！");
			}
		} else {
			j.setMsg("登录超时，请重新登录！");
		}
		return j;
	}

	/**
	 * 跳转到显示用户角色页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/currentUserRolePage")
	public String currentUserRolePage(HttpServletRequest request, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		request.setAttribute("userRoles", JSON.toJSONString(roleService.tree(sessionInfo)));
		return "/user/userRole";
	}

	/**
	 * 跳转到显示用户权限页面
	 * 
	 * @return
	 */
	@RequestMapping("/currentUserResourcePage")
	public String currentUserResourcePage(HttpServletRequest request, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		request.setAttribute("userResources", JSON.toJSONString(resourceService.allTree(sessionInfo)));
		return "/user/userResource";
	}

}
