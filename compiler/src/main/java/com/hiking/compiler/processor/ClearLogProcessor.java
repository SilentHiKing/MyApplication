package com.hiking.compiler.processor;

import com.google.auto.service.AutoService;
import com.hiking.compiler.base.BaseProcessor;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;

import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;


@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class ClearLogProcessor extends BaseProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (!roundEnvironment.processingOver() && mTrees != null) {
            Set<? extends Element> es = roundEnvironment.getRootElements();
            for (Element e : es) {
                if (e.getKind() == ElementKind.CLASS) {
                    ((JCTree) mTrees.getTree(e)).accept(new LogClearTranslator());
                }

            }
            /*roundEnvironment.getRootElements().stream()
                    .filter(it -> it.getKind() == ElementKind.CLASS)
                    .forEach(it -> ((JCTree) mTrees.getTree(it)).accept(new LogClearTranslator()));*/
        }
        return false;
    }

    class LogClearTranslator extends TreeTranslator {

        public static final String LOG_TAG = "MLog.";

        @Override
        public void visitBlock(JCTree.JCBlock jcBlock) {
            super.visitBlock(jcBlock);
            List<JCTree.JCStatement> jcStatementList = jcBlock.getStatements();
            if (jcStatementList == null || jcStatementList.isEmpty()) {
                return;
            }
            List<JCTree.JCStatement> out = List.nil();
            for (JCTree.JCStatement jcStatement : jcStatementList) {
                if (isLogStatements(jcStatement)) {
                    info("jcStatement.pos=%s,jcStatement.type=%s",jcStatement.pos,jcStatement.type);
                    mMessager.printMessage(Diagnostic.Kind.WARNING, this.getClass().getCanonicalName() + "LogClear:" + jcStatement.toString());
                } else {
                    out = out.append(jcStatement);
                }
            }
            jcBlock.stats = out;
        }


        private boolean isLogStatements(JCTree.JCStatement jcStatement) {
            return jcStatement.toString().contains(LOG_TAG);
        }
    }
}

