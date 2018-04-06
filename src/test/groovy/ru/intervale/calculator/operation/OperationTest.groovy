package ru.intervale.calculator.operation

import spock.lang.Specification

class OperationTest extends Specification {

    def "Find operation. Success."() {
        expect:
        Operation.find(symbol as char) == operation
        where:
        symbol || operation
        '+'    || Operation.PLUS
        '/'    || Operation.DIV
        '*'    || Operation.MUL
        '-'    || Operation.MINUS
        '%'    || Operation.PER
        '^'    || Operation.POW
        '('    || Operation.LEFT_BRACKET
        ')'    || Operation.RIGHT_BRACKET
    }

    def "Find operation. Operation not find."() {
        setup:
        def symbol = 'a'
        when:
        Operation.find(symbol as char)
        then:
        OperationNotFound ex = thrown()
        ex.message == "Operation not found for: $symbol"
    }

    def "Check symbol is operation."() {
        expect:
        Operation.isOperation(symbol) == has
        where:
        symbol || has
        '+'    || true
        '/'    || true
        '*'    || true
        '-'    || true
        '%'    || true
        '^'    || true
        '('    || true
        ')'    || true
        'a'    || false
        ''     || false
        'ads'  || false
    }

    def "Get priority of operation"() {
        expect:
        Operation.getPriority(symbol as char) == priority
        where:
        symbol || priority
        '+'    || 2
        '-'    || 2
        '/'    || 3
        '*'    || 3
        '%'    || 3
        '^'    || 4
        '('    || 1
        ')'    || 1
        'a'    || Operation.DEFAULT_PRIORITY
        'z'    || Operation.DEFAULT_PRIORITY
    }

    def "Get priority constructor is stack"() {
        expect:
        Operation.getPriority(stack as Stack) == priority
        where:
        stack             || priority
        new Stack<>()     || Operation.DEFAULT_PRIORITY
    }

    def "Operation is unary"() {
        expect:
        Operation.isUnary(symbol as char) == isUnary
        where:
        symbol || isUnary
        '+'    || true
        '-'    || true
        'a'    || false
    }

    def "Operation is bracket"() {
        expect:
        Operation.isBracket(symbol as char) == isBracket
        where:
        symbol || isBracket
        '('    || true
        ')'    || true
        '+'    || false
        '-'    || false

    }

    def "Operation is left bracket"() {
        expect:
        Operation.isLeftBracket(symbol as char) == isLeftBracket
        where:
        symbol || isLeftBracket
        '('    || true
        ')'    || false
        'a'    || false
    }

    def "Opearation is right bracket"() {
        expect:
        Operation.isRightBracket(symbol as char) == isRightBracket
        where:
        symbol || isRightBracket
        ')'    || true
        '('    || false
        'a'    || false
    }

    def "Get symbol of operation"() {
        expect:
        Operation.PLUS.getSymbol() == '+' as char
        Operation.MINUS.getSymbol() == '-' as char
        Operation.MUL.getSymbol() == '*' as char
        Operation.DIV.getSymbol() == '/' as char
        Operation.PER.getSymbol() == '%' as char
        Operation.POW.getSymbol() == '^' as char
        Operation.LEFT_BRACKET.getSymbol() == '(' as char
        Operation.RIGHT_BRACKET.getSymbol() == ')' as char
    }

    def "Perform operation plus"() {
        expect:
        Operation.PLUS.perform(2,3) == 5
        Operation.PLUS.perform(2,-3) == -1
        Operation.PLUS.perform(2.00,-13) == -11
        Operation.PLUS.perform(2.001,1.4) == 3.401
    }

    def "Perform operation minus"() {
        expect:
        Operation.MINUS.perform(2,3) == -1
        Operation.MINUS.perform(2,-3) == 5
        Operation.MINUS.perform(2.00,-13) == 15
        Operation.MINUS.perform(2.001,1.4) == 0.601
    }
}
