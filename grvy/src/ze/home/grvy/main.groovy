package ze.home.grvy

import java.util.regex.Matcher
import java.util.regex.Pattern
import static java.util.Calendar.getInstance as now

class Person{
    String name;
    Integer age;
    def testMethod(){
        'test'
    }
}
println(Person.class.declaredMethods*.name.grep(~/.*Method/)) //[invokeMethod, testMethod]


//// 闭包
//def clo = { v -> ++v }
//println clo(1)

//def badRead() throws FileNotFoundException{
//    new File('doesNotExist.txt').text
//}
//badRead()


//class Person{
//    String name;
//    Integer age;
//}
//
//def person1 = new Person(name: '', age: 18);  // ok
//def person2 = ['', 20] as Person  // ok
//Person person3 = ['', 22]  //Caught: org.codehaus.groovy.runtime.typehandling.GroovyCastException




//def range = 1..5
//println(range.collect())  // [1, 2, 3, 4, 5]
//println(1 in range)  // true


//def ss = ['a','b','c']
//def s2 = ss*.toUpperCase()
//println(s2)  // [A, B, C]


//// 正则表达式
//def p = ~/[0-9]+/
//assert p instanceof Pattern
//println p.matcher('123').matches() // true
//def matcher = '123' =~ /[0-9]+/
//println matcher.matches()  // true
//println(!matcher)  //
//println(matcher)   // java.util.regex.Matcher[pattern=[0-9]+ region=0,3 lastmatch=]
//def matcher2 = 'abc' =~ /[0-9]+/
//println(!matcher2) // true
//println( 'abc' ==~ /[a-z]+/ )  // true
//println( 'abc' ==~ /[0-9]+/ )  // false


//class User {
//    String name
//    User(String name) { this.name = name}
//    String getName() { "getterName" }
//}
//def user = new User('innerName')
//assert user.name == 'getterName'
//assert user.@name == "innerName"



////对象操作符
//def nullobj = null
//def nullobjname = nullobj?.name
//println(nullobjname)  // null


//// 数组
//def arr = [0,1,2,3,4]
//println(arr.size())  // 5
//println(arr[1..2])   //[1, 2]
//println(arr[-2..-1])  // [3, 4]
//arr.add(5)
//arr << 6
//println(arr) //[0, 1, 2, 3, 4, 5, 6]


//def links = [1, 2, 3] as LinkedList
//println(links.class)  //class java.util.LinkedList
//
//def ints = [1, 2, 3] as int[]
//println(ints.class) //class [I
//
//def strings = [1, 2, 3] as String[]
//println(strings.class)  // class [Ljava.lang.String;

//println 'hello world'
//println 1 + 2  // 3
//println "1+2 is ${1 + 2}"
//println "express ${def a = 1; println('a is ' + a)}" // a is 1 // express null
//
//println "express ${def a = 1; println('a is ' + a); a + 1}" //a is 1  //express 2
//
//
//def map = [:]
//map.'name' = 'name'
//println map.name
//
//def person = [name: 'wang', age: 18]
//println "name is $person.name"    // name is wang
//
//def template = '''this is
//multiline
//text
//'''
//println template
//
//def pattern = / i am raw string /
//println(pattern)  //  i am raw string
//
//def numbers = [1, 2, 3]
//println(numbers.class)  // class java.util.ArrayList
//assert numbers instanceof List





