package uk.julianc.ignorerestwarner

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class IgnoreRestWarner implements ASTTransformation {
    @Override
    void visit(ASTNode[] nodes, SourceUnit source) {
        source.AST.classes.findAll { it.superClass.name == "spock.lang.Specification" }.each {
            it.visitContents(new IgnoreRestClassVisitor(source))
        }
    }
}

