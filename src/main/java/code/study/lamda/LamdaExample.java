package code.study.lamda;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class LamdaExample {

    public static void main(String[] args) {

                                                                      /*인터페이스명/메소드/반환타입/설명*/
        Supplier<Integer> s = () -> (int) (Math.random() * 100) + 1; //Supplier<T>, get(), T, 매개변수는 없고 반환 값만 있음
        Consumer<Integer> c = i -> System.out.println(i + ",");      //Consumer<T>, accept(T t), void, Suplier와 반대로 매개변수만 있고, 반환값이 없음
        Predicate<Integer> p = i -> i % 2 == 0;                      //Predicate<T>, test(T t), boolean, 조건식을 표현하는데 사용되며 매개변수는 하나, 반환타입은 boolean
        Function<Integer, Integer> f = i -> i / 10 * 10;             //Function<T,R>, apply(T t), R, 일반적인 함수이며 하나의 매개변수를 받아서 결과를 반환함

        List<Integer> list = new ArrayList<>();
        makeRandomList(s, list);
        System.out.println(list);
        printEvenNum(p, c, list);
        List<Integer> newList = doSomething(f, list);
        System.out.println(newList);
    }
    static <T> List<T> doSomething(Function<T, T> f, List<T> list) {
        List<T> newList = new ArrayList<T>(list.size());

        for(T i : list) {
            newList.add(f.apply(i));
        }

        return newList;
    }

    static <T> void printEvenNum(Predicate<T> p, Consumer<T> c, List<T> list) {
        System.out.print("[");
        for(T i : list) {
            if(p.test(i))
                c.accept(i);
        }
        System.out.println("]");
    }

    static <T> void makeRandomList(Supplier<T> s, List<T> list) {
        for(int i=0;i<10;i++) {
            list.add(s.get());
        }
    }
}


