package com.bitech.joke.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * <p>Rxjava实现bus
 * 实现类似于广播的功能
 * </p>
 * Created on 2016/4/11 10:38.
 *
 * @author Lucy
 */
public class Rxbus {

    private volatile static Rxbus instance;

    private Rxbus() {
    }

    public static Rxbus getInstance() {
        if (instance == null) {
            instance = new Rxbus();
        }
        return instance;
    }

    //线程安全的 存储Subject,Subject实现了观察者与被观察者接口
    private ConcurrentHashMap<Object, List<Subject>> subjectHashMap = new ConcurrentHashMap<>();

    //注册
    public <T> Observable<T> register(@NonNull Object tag) {
        List<Subject> list = subjectHashMap.get(tag);
        if (list == null) {
            list = new ArrayList<>();
            subjectHashMap.put(tag, list);
        }

        Subject<T, T> subject = PublishSubject.create();
        list.add(subject);
        return subject;
    }
    //取消注册
    public void unregister(@NonNull Object tag, @NonNull Observable observable) {

        List<Subject> list = subjectHashMap.get(tag);
        if (list != null) {
            list.remove(observable);
            if (list.isEmpty()) {
                subjectHashMap.remove(tag);
            }
        }

    }
    //传递数据
    //Subject的使用方式就是onNext
    public void post(@NonNull Object tag, @NonNull Object content) {
        List<Subject> list = subjectHashMap.get(tag);
        if (list != null && !list.isEmpty()) {
            for (Subject subject : list) {
                subject.onNext(content);
            }
        }
    }
}
