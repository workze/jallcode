package domaindesign01.service;

import domaindesign01.model.DomainRegistry;
import domaindesign01.model.UserDescriptor;

public class AuthService {
    public UserDescriptor authUser(String userName, String pwd){
        if(userName == null){
            throw new IllegalArgumentException("user name must not be null");
        }
        DomainRegistry.userRepo().userFromAuthCredential(userName, pwd);

        return new UserDescriptor();
    }
}
