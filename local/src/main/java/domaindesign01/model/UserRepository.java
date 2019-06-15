package domaindesign01.model;

public class UserRepository {
    public User getUser(UserId userId){
        return new User();
    }

    // new auth method
    public User userFromAuthCredential(String userName, String pwd){
        return new User();
    }
}
