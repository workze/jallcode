package ormstaff;

public interface EntityManagerFactory {
    <T> EntityManager getEntityManeger(Class<T> cls);
}
