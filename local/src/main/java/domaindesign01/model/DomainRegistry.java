package domaindesign01.model;

public class DomainRegistry {
    public static UserRepository userRepo(){
        return new UserRepository();
    }
}
