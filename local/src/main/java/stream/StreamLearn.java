package stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wangguize
 * date 2019-08-08
 */
public class StreamLearn {
    public static void main(String[] args) {
        Stream.of(new Subject("1", "key1"), new Subject("2", "key2"))
                .peek(System.out::println).map(v -> v.key).peek(System.out::println).forEach(v -> System.out.println(v));  // .collect(Collectors.toList());

//        List<Integer> collect = Stream.of(1, 2).peek(System.out::println).collect(Collectors.toList());
//        System.out.println(collect);
    }

    @Data
    public static class Subject {
        String id;
        String key;
        public Subject(String id,String key){
            this.id = id;
            this.key = key;
        }
    }
}
