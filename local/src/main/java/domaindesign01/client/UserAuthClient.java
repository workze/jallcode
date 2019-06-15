package domaindesign01.client;

import domaindesign01.model.DomainRegistry;
import domaindesign01.model.User;
import domaindesign01.model.UserId;

public class UserAuthClient {

    public static void main(String[] args) {
        // old method
        boolean authed = false;
        User user = DomainRegistry.userRepo()
                .getUser(new UserId());
        if(user != null){
            authed = user.isAuth("123");
        }

        // new method

    }
}
