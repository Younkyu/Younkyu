package com.nobrain.rx_study.step1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest2 {
    @Test
    public void addition_isCorrect() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
//      List<String> list = Arrays.asList("A","B","C","D");
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            if(s.charAt(0)%2 == 0) {
                continue;
            }

            String str = s.toLowerCase();
            if(str.equals("a")) {
                builder.append(str);
            }
        }
        System.out.println(builder.toString());
    }

    @Test
    public void test() throws Exception {
        final StringBuilder builder = new StringBuilder();
        List<String> list = Arrays.asList("A", "B", "C", "D");
        Observable.fromIterable(list)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        return s.toLowerCase();
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        builder.append(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //nothing
                    }

                    @Override
                    public void onComplete() {
                        System.out.println(builder.toString());
                    }
                });
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        builder.append(s);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        //nothing
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        System.out.println(builder.toString());
//                    }
//                });

    }
    @Test
    public void test2() throws Exception {
        final StringBuilder builder = new StringBuilder();
        List<String> list = Arrays.asList("A","B","C","D");
        Observable.fromIterable(list)
                .filter(s -> s.charAt(0)%2 != 0)
                .map(s -> s.toLowerCase())
                .filter(s->s.equals("a"))
                .subscribe(s -> builder.append(s), throwable -> {
                    //nothing
                }, () -> System.out.println(builder.toString()));
    }

    @Test
    public void test3() throws Exception {
        final StringBuilder builder = new StringBuilder();
        List<String> list = Arrays.asList("A","B","C","D");
        Observable.fromIterable(list)
                .map(s -> s.toLowerCase())
                .subscribe(s -> builder.append(s), throwable -> {
                    //nothing
                }, () -> System.out.println(builder.toString()));
    }

    @Test
    public void test4() throws Exception {
        final StringBuilder builder = new StringBuilder();
        List<String> list = Arrays.asList("A","B","C","D");

        PublishSubject<String> subject = PublishSubject.create();

 //       Observable.fromIterable(list)
            subject
                .map(s -> s.toLowerCase())
                .subscribe(s -> System.out.print(s));

        subject.onNext("A");
        subject.onNext("B");
        subject.onNext("C");
        subject.onNext("D");
        subject.onComplete();
    }

}