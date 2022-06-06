package com.uqierp.shiro;

import com.uqierp.bean.Account;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 扩展具有Account对象 Token
 * 
 */
public class AccountLoginToken extends UsernamePasswordToken {
    private static final long serialVersionUID = 1L;
    private Account account;

    public AccountLoginToken(String username, char[] password, boolean rememberMe, String host, Account account) {
        super(username, password, rememberMe, host);
        this.account = account;
    }

    public AccountLoginToken(String username, String password, Account account) {
        super(username, password);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
