package smartdao;

import java.io.Serializable;

public class Condition {

    Condition where(String columnName){

        return this;
    }

    Condition equal(Serializable value){
        return this;
    }

    Condition like(Serializable value){
        return this;
    }

    Condition and(){
        return this;
    }

    Condition or(){
        return this;
    }

    Condition limit(int a, int b){
        return this;
    }

    Condition orderBy(String columnName){
        return this;
    }

    Condition with(String columnName, Serializable value){
        return this;
    }

}
