package org.hrds.rducm.gitlab.infra.util;


import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zmf
 */
public class ConvertUtils {
    private ConvertUtils() {
    }

    /**
     * This is a method to convert an object to destination object.
     * It's only for the simple object that can use
     * {@link BeanUtils#copyProperties(Object, Object)} method.
     * And the destination class should have the non-parameter constructor.
     * {@link Class#newInstance()} method is used.
     *
     * @param source           the source object
     * @param destinationClass the destination class
     * @param <S>              the source type
     * @param <D>              the destination type
     * @return destination object
     */
    public static <S, D> D convertObject(S source, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }
        try {
            D destination = destinationClass.newInstance();
            BeanUtils.copyProperties(source, destination);
            return destination;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CommonException("Can not instantiate the class type: {} with no-arg constructor.", destinationClass.getName());
        }
    }

    /**
     * convert page with default beanUtils
     *
     * @param source    source page
     * @param converter converter for list content of page
     * @param <S>       the source content type
     * @param <D>       the destination content type
     * @return destination page
     */
    public static <S, D> Page<D> convertPage(Page<S> source, Function<S, D> converter) {
        if (source == null) {
            return null;
        }
        Page<D> destination = new Page<>();
        BeanUtils.copyProperties(source, destination, "content");
        if (source.getContent() != null) {
            destination.setContent(source.getContent().stream().map(converter).collect(Collectors.toList()));
        }
        return destination;
    }

    /**
     * convert page with default beanUtils
     *
     * @param source source page
     * @param <S>    the source content type
     * @param <D>    the destination content type
     * @return destination page
     */
    public static <S, D> Page<D> convertPage(Page<S> source, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }
        Page<D> destination = new Page<>();
        BeanUtils.copyProperties(source, destination, "content");
        if (source.getContent() != null) {
            destination.setContent(source.getContent().stream().map(s -> convertObject(s, destinationClass)).collect(Collectors.toList()));
        }
        return destination;
    }

    /**
     * convert List with default beanUtils
     *
     * @param source source page
     * @param <S>    the source content type
     * @param <D>    the destination content type
     * @return destination page
     */
    public static <S, D> List<D> convertList(List<S> source, Class<D> destinationClass) {
        if (source == null) {
            return new ArrayList<>();
        }
        if (source.isEmpty()) {
            return new ArrayList<>();
        }
        return source.stream().map(s -> convertObject(s, destinationClass)).collect(Collectors.toList());
    }


    /**
     * convert List with special converter
     *
     * @param source source page
     * @param <S>    the source content type
     * @param <D>    the destination content type
     * @return destination page
     */
    public static <S, D> List<D> convertList(List<S> source, Function<S, D> converter) {
        if (source == null) {
            return new ArrayList<>();
        }
        if (source.isEmpty()) {
            return new ArrayList<>();
        }
        return source.stream().map(converter).collect(Collectors.toList());
    }
}
