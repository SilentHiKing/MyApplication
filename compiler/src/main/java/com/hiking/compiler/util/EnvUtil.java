package com.hiking.compiler.util;

import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Types;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;

public class EnvUtil {
    public static ProcessingEnvironment mProcessingEnvironment;
    // apt 相关类
    public static Filer mFiler;//文件相关的辅助类
    public static Elements mElements;//元素相关的辅助类
    public static Messager mMessager;//日志相关的辅助类
    // javac 编译器相关类
    public static Trees mTrees;
    public static TreeMaker mTreeMaker;
    public static Names mNames;
    public static Types mTypes;

    public static void init(ProcessingEnvironment env) {
        if (mProcessingEnvironment != null) {
            return;
        }
        mProcessingEnvironment = env;
        mFiler = env.getFiler();
        mElements = env.getElementUtils();
        mMessager = env.getMessager();

        mTrees = Trees.instance(env);
        Context c = ((JavacProcessingEnvironment) env).getContext();
        mTreeMaker = TreeMaker.instance(c);
        mNames = Names.instance(c);
        mTypes = Types.instance(c);
    }


}
