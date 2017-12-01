package com.tgnet.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class CollectionUtil {

    public static <T, E extends T> List<T> convert(List<E> list){
        if(list == null)
            return null;
        if(list.size() == 0)
            return Collections.emptyList();
        List<T> result = new ArrayList<>();
        for (E e : list) {
            result.add(e);
        }
        return result;
    }

    public static <T> T firstOrDefault(Collection<T> collection){
        if(collection == null || collection.isEmpty())
            return null;
        else{
            return collection.iterator().next();
        }
    }

    public static <T> boolean isNull(Collection<T> collection){
        return collection == null ;
    }


    public static <T> boolean isNullOrEmpty(T[] collection){
        return collection == null || collection.length == 0;
    }

    public static boolean isNullOrEmpty(long[] collection){
        return collection == null || collection.length == 0;
    }

    public static boolean isNullOrEmpty(int[] collection){
        return collection == null || collection.length == 0;
    }

    public static <T> boolean isNullOrEmpty(Collection<T> collection){
        return collection == null || collection.size() == 0;
    }

    public static <K,V> boolean isNullOrEmpty(Map<K,V> map){
        return map == null || map.size() == 0;
    }

    public  static  String getStringByAllList(List<String> list){
        String string = null;
        for (int i = 0; i < list.size(); i++) {
            if (i ==0){
                string =  list.get(0);
            }else {
                string = string + "、" +  list.get(i);
            }
        }
        return  string;
    }

    public  static <T> void clearAdd(Collection<T> collectionParent,Collection<T> collectionSub){
        if (!isNull(collectionParent)  && !isNullOrEmpty(collectionSub)){
            collectionParent.clear();
            collectionParent.addAll(collectionSub);
        }

    }

    public static <T> List<T> ensureNotNullList(List<T> list) {
        return list == null ? Collections.<T>emptyList() : list;
    }
}
