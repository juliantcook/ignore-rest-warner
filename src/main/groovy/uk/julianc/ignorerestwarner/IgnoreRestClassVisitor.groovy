package uk.julianc.ignorerestwarner

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.control.Janitor
import org.codehaus.groovy.control.SourceUnit

class IgnoreRestClassVisitor implements GroovyClassVisitor {

    SourceUnit sourceUnit

    IgnoreRestClassVisitor(SourceUnit sourceUnit) {
        this.sourceUnit = sourceUnit
    }

    @Override
    void visitMethod(MethodNode node) {
        if (node.annotations.find { it.classNode.name == "spock.lang.IgnoreRest" }) {
            warn "@IgnoreRest detected. Don't forget to remove!", node
        }
    }

    private void warn(String message, MethodNode node) {
        String sample = sourceUnit.getSample(node.lineNumber, node.columnNumber, new Janitor())
        System.err.println "WARNING: $message\n\n$sample\n\n" +
                "\tat ${node.declaringClass.name}:${node.lineNumber}"
    }

    @Override
    void visitClass(ClassNode node) {}

    @Override
    void visitConstructor(ConstructorNode node) {}

    @Override
    void visitField(FieldNode node) {}

    @Override
    void visitProperty(PropertyNode node) {}
}
