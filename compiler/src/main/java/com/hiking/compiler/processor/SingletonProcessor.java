package com.hiking.compiler.processor;

import com.google.auto.service.AutoService;
import com.hiking.annotation.singleton.Singleton;
import com.hiking.compiler.base.SingleAnnotationProcessor;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;

import java.util.List;

import javax.annotation.processing.Processor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class SingletonProcessor extends SingleAnnotationProcessor {

    @Override
    public Class[] getSupportedClazz() {
        return new Class[]{Singleton.class};
    }

    @Override
    public void translate(Element e, JCTree jcTree) {
        if (!(e instanceof TypeElement)) {
            return;
        }
        ExecutableElement executableElement = null;
        List<? extends Element> es = e.getEnclosedElements();
        for (Element element : es) {
            if (element instanceof ExecutableElement
                    && element.getAnnotation(Singleton.Constructor.class) != null
                    && !((ExecutableElement) element).getParameters().isEmpty()) {
                executableElement = (ExecutableElement) element;
                break;
            }
        }
        if (executableElement != null) {
            jcTree.accept(new DCLTreeTranslator(e.getSimpleName(), (Symbol.MethodSymbol) executableElement));
        } else {
            jcTree.accept(new InstanceHolderTreeTranslator(e.getSimpleName()));
        }
    }

    @Override
    public void generateJavaFile(Element e, JCTree jcTree) {

    }

    private class InstanceHolderTreeTranslator extends TreeTranslator {
        Name mRootClazzName;
        TreeMaker treeMaker = SingletonProcessor.this.mTreeMaker;
        Names names = SingletonProcessor.this.mNames;

        public InstanceHolderTreeTranslator(Name name) {
            mRootClazzName = name;
        }

        @Override
        public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
            if (jcClassDecl.getSimpleName().equals(mRootClazzName)) {
                SingletonProcessor.this.info("InstanceHolderTreeTranslator开始生成" + jcClassDecl.name);
                JCTree.JCMethodDecl getInstanceMethod = makeGetInstanceMethodDecl(jcClassDecl);
                if (!jcClassDecl.defs.contains(getInstanceMethod)) {
                    try {
                        jcClassDecl.defs = jcClassDecl.defs
                                .prepend(getInstanceMethod)
                                .prepend(makeInstanceHolderDecl(jcClassDecl));
                    } catch (Exception e) {
                        info(e.getMessage());
                    }

                }
            }
            super.visitClassDef(jcClassDecl);
        }

        private JCTree.JCMethodDecl makeGetInstanceMethodDecl(JCTree.JCClassDecl jcClassDecl) {

            com.sun.tools.javac.util.List<JCTree.JCStatement> jcStatements = new ListBuffer<JCTree.JCStatement>()
                    .append(treeMaker.Return(treeMaker.Select(treeMaker.Ident(names.fromString("_InstanceHolder")), names.fromString("_sInstance"))))
                    .toList();
            JCTree.JCBlock body = treeMaker.Block(0, jcStatements);

            return treeMaker.MethodDef(
                    treeMaker.Modifiers(Flags.PUBLIC | Flags.STATIC),
                    names.fromString("getInstance"),
                    treeMaker.Ident(jcClassDecl.name),
                    com.sun.tools.javac.util.List.<JCTree.JCTypeParameter>nil(),
                    com.sun.tools.javac.util.List.<JCTree.JCVariableDecl>nil(),
                    com.sun.tools.javac.util.List.<JCTree.JCExpression>nil(),
                    body, null);
        }


        /**
         * private static class _InstanceHolder {
         * private static final SingleTest _sInstance = new SingleTest();
         * }
         */
        private JCTree makeInstanceHolderDecl(JCTree.JCClassDecl jcClassDecl) {
            return treeMaker.ClassDef(
                    treeMaker.Modifiers(Flags.PRIVATE | Flags.STATIC),
                    names.fromString("_InstanceHolder"),
                    com.sun.tools.javac.util.List.<JCTree.JCTypeParameter>nil(),
                    null,
                    com.sun.tools.javac.util.List.<JCTree.JCExpression>nil(),
                    com.sun.tools.javac.util.List.of(makeInstanceFieldDecl(jcClassDecl)));
        }

        /**
         * private static volatile SingleTest _sInstance = new SingleTest();
         */
        private JCTree makeInstanceFieldDecl(JCTree.JCClassDecl jcClassDecl) {
            return treeMaker.VarDef(
                    treeMaker.Modifiers(Flags.PRIVATE | Flags.STATIC | Flags.FINAL),
                    names.fromString("_sInstance"),
                    treeMaker.Ident(jcClassDecl.name),
                    treeMaker.NewClass(null,
                            com.sun.tools.javac.util.List.<JCTree.JCExpression>nil(),
                            treeMaker.Ident(jcClassDecl.name),
                            com.sun.tools.javac.util.List.<JCTree.JCExpression>nil(),
                            null));
        }
    }

    private class DCLTreeTranslator extends TreeTranslator {
        Name mRootClazzName;
        Symbol.MethodSymbol mConstructor;
        TreeMaker treeMaker = SingletonProcessor.this.mTreeMaker;
        Names names = SingletonProcessor.this.mNames;

        public DCLTreeTranslator(Name name, Symbol.MethodSymbol executableElement) {
            mConstructor = executableElement;
            mRootClazzName = name;
        }

        @Override
        public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {

            if (jcClassDecl.getSimpleName().equals(mRootClazzName)) {
                SingletonProcessor.this.info("DCLTreeTranslator开始生成" + jcClassDecl.name);
                if (mConstructor == null) {
                    return;
                }
                treeMaker.at(jcClassDecl.pos);
                JCTree.JCMethodDecl getInstanceMethod = makeGetInstanceMethodDecl(jcClassDecl);


                if (!jcClassDecl.defs.contains(getInstanceMethod)) {
                    try {
                        jcClassDecl.defs = jcClassDecl.defs
                                .prepend(getInstanceMethod)
                                .prepend(makeInstanceFieldDecl(jcClassDecl));
                    } catch (Exception e) {
                        info(e.getMessage());
                    }

                }
            }
            super.visitClassDef(jcClassDecl);
        }


        /**
         * private static volatile SingleTest _sInstance;
         */
        private JCTree makeInstanceFieldDecl(JCTree.JCClassDecl jcClassDecl) {
            return treeMaker.VarDef(
                    treeMaker.Modifiers(Flags.PRIVATE | Flags.STATIC | Flags.VOLATILE),
                    names.fromString("_sInstance"),
                    treeMaker.Ident(jcClassDecl.name),
                    null);
        }


        /**
         * public static SingleTest getInstance(String context) {
         * if (_sInstance == null) {
         * Class var1 = SingleTest.class;
         * synchronized(SingleTest.class) {
         * if (_sInstance == null) {
         * _sInstance = new SingleTest(context);
         * }
         * }
         * }
         * return _sInstance;}
         */
        private JCTree.JCMethodDecl makeGetInstanceMethodDecl(JCTree.JCClassDecl jcClassDecl) {

            ListBuffer body = new ListBuffer<JCTree.JCStatement>();
            com.sun.tools.javac.util.List list = body.append(makeCheckNullIfDecl(jcClassDecl))
                    .append(treeMaker.Return(treeMaker.Ident(names.fromString("_sInstance"))))
                    .toList();
            JCTree.JCBlock var7 = treeMaker.Block(0, list);

            //
            com.sun.tools.javac.util.List<JCTree.JCVariableDecl> var5 = com.sun.tools.javac.util.List.nil();
            com.sun.tools.javac.util.List<Symbol.VarSymbol> paramSyms = mConstructor.getParameters();
            for (Symbol.VarSymbol paramSym : paramSyms) {
                var5 = var5.append(treeMaker.VarDef(paramSym, null));
            }
            return treeMaker.MethodDef(
                    treeMaker.Modifiers(Flags.PUBLIC | Flags.STATIC),
                    names.fromString("getInstance"),
                    treeMaker.Ident(jcClassDecl.name),
                    com.sun.tools.javac.util.List.<JCTree.JCTypeParameter>nil(),
                    var5,
                    com.sun.tools.javac.util.List.<JCTree.JCExpression>nil(),
                    var7,
                    null);
        }

        private JCTree.JCIf makeCheckNullIfDecl(JCTree.JCClassDecl jcClassDecl) {
            ListBuffer body = new ListBuffer<JCTree.JCStatement>();
            com.sun.tools.javac.util.List list = body.append(makeSyncDecl(jcClassDecl)).toList();
            JCTree.JCBlock var2 = treeMaker.Block(0, list);
            return treeMaker.If(treeMaker.Parens(treeMaker.Binary(JCTree.Tag.EQ, treeMaker.Ident(names.fromString("_sInstance")), treeMaker.Literal(TypeTag.BOT, null))),
                    var2,
                    null);

        }


        private JCTree.JCSynchronized makeSyncDecl(JCTree.JCClassDecl jcClassDecl) {
            ListBuffer body = new ListBuffer<JCTree.JCStatement>();
            com.sun.tools.javac.util.List list = body.append(makeDoubleCheckNullIfDecl(jcClassDecl)).toList();
            JCTree.JCBlock var2 = treeMaker.Block(0, list);
            return treeMaker.Synchronized(treeMaker.Parens(treeMaker.Select(treeMaker.Ident(jcClassDecl.name), names._class)),
                    var2
            );

        }

        private JCTree.JCIf makeDoubleCheckNullIfDecl(JCTree.JCClassDecl jcClassDecl) {
            ListBuffer body = new ListBuffer<JCTree.JCStatement>();
            com.sun.tools.javac.util.List list = body.append(makeAssignDecl(jcClassDecl)).toList();
            JCTree.JCBlock var2 = treeMaker.Block(0, list);
            return treeMaker.If(treeMaker.Parens(treeMaker.Binary(JCTree.Tag.EQ,
                    treeMaker.Ident(names.fromString("_sInstance")),
                    treeMaker.Literal(TypeTag.BOT, null))),
                    var2,
                    null);

        }


        private JCTree.JCExpressionStatement makeAssignDecl(JCTree.JCClassDecl jcClassDecl) {
            com.sun.tools.javac.util.List<JCTree.JCExpression> var4 = com.sun.tools.javac.util.List.nil();
            com.sun.tools.javac.util.List<Symbol.VarSymbol> paramSyms = mConstructor.getParameters();
            for (Symbol.VarSymbol paramSym : paramSyms) {
                var4 = var4.append(treeMaker.Ident(paramSym.name));
            }

            return treeMaker.Exec(treeMaker.Assign(
                    treeMaker.Ident(names.fromString("_sInstance")),
                    treeMaker.NewClass(null,
                            com.sun.tools.javac.util.List.<JCTree.JCExpression>nil(),
                            treeMaker.Ident(jcClassDecl.name),
                            var4, null)
            ));
        }
    }


}
