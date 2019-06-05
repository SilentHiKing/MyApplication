package com.hiking.compiler.base;

import com.sun.source.util.TreePath;
import com.sun.tools.javac.tree.JCTree;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public abstract class SingleAnnotationProcessor extends BaseProcessor {


    boolean mIsFirstRound = true;

    JCTree.JCCompilationUnit mRootTree;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        Class[] clazzs = getSupportedClazz();
        for (Class clazz : clazzs) {
            annotations.add(clazz.getCanonicalName());
        }
        return annotations;
    }

    public abstract Class[] getSupportedClazz();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (!mIsFirstRound) {
            return false;
        }
        mIsFirstRound = false;
        info("%s:process begin>>>>>", this.getClass().getSimpleName());
        Class[] clazzs = getSupportedClazz();
        for (Class clazz : clazzs) {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(clazz);
            for (Element e : elements) {
                if (!(e instanceof TypeElement)) {
                    continue;
                }
                TreePath treePath = mTrees.getPath(e);
                mRootTree = (JCTree.JCCompilationUnit) treePath.getCompilationUnit();
                JCTree jcTree = (JCTree) mTrees.getTree(e);
                translate(e, jcTree);
                generateJavaFile(e, jcTree);
            }
        }
        info("%s:process end<<<<<", this.getClass().getSimpleName());
        return true;
    }

    public abstract void translate(Element e, JCTree jcTree);

    public abstract void generateJavaFile(Element e, JCTree jcTree);
}
