package com.uqierp.shiro;

import com.uqierp.bean.Account;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.*;

public class MybatisRealm extends AuthorizingRealm {
    
    /*
     * Authorization 授权(权限操作验证)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        Account account = (Account) principals.fromRealm(getName()).iterator().next();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();
        // 用户角色和对应权限获取设置
        /*Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("yhbh", account.getPk());
        // 检测当前登录用户的角色
        List<Map<String, Object>> roleList = userInfoService.queryRoleListByYhbh(paramMap);
        for (Map<String, Object> roleMap : roleList) {
            roles.add((String) roleMap.get("JSDM"));
            Map<String, String> parMap = new HashMap<String, String>();
            parMap.put("jsbh", (String) roleMap.get("JSBH"));
            // 检测当前登录用户的操作权限(当前是'一对多'关系,一个角色对应多个权限)
            List<Map<String, Object>> permitList = userInfoService.queryPermissionListByJsbh(parMap);
            for (Map<String, Object> permitMap : permitList) {
                permissions.add((String) permitMap.get("CODE"));
            }
        }*/
        // 添加角色
        info.addRoles(roles);
        // 添加权限
        info.addStringPermissions(permissions);
        return info;
    }
    
    /*
     * Authentication 验证(登录认证)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        AccountLoginToken userInfoToken = (AccountLoginToken) authcToken;
        String username = userInfoToken.getUsername();
        SimpleAuthenticationInfo info = null;
        try {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("jh", username);
            m.put("yxx", "1");
           /* Map<String, Object> p = (Map<String, Object>) userInfoService.queryInfoByJh(m).get(0);
            Account userinfo = new Account();
            userinfo.setUsername(p.get("username")==null?"":p.get("username").toString());
            userinfo.setPassword(p.get("password")==null?"":p.get("password").toString());
            String password = userinfo.getPassword();
            info = new SimpleAuthenticationInfo(userinfo, password.toCharArray(), getName());
            if(userinfo.getSalt() != null && !"".equals(userinfo.getSalt())){
                info.setCredentialsSalt(ByteSource.Util.bytes(userinfo.getSalt()));
            }*/
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage(), e);
        }
        return info;
    }

}
