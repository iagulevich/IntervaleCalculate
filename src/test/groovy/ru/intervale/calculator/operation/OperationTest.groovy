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
        stack         || priority
        new Stack<>() || Operation.DEFAULT_PRIORITY
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
        Operation.PLUS.perform(d1 as double, d2 as double) == result
        where:
        d1     | d2  || result
        2      | 3   || 5
        2      | -3  || -1
        2.00   | -13 || -11
        2.0001 | 1.4 || 3.4001
    }

    def "Perform operation minus"() {
        expect:
        Operation.MINUS.perform(d1 as double, d2 as double) == result
        where:
        d1   | d2  || result
        2    | 3   || -1
        2    | -3  || 5
        2.00 | -13 || 15
    }

    def "Perform operation division"() {
        expect:
        Operation.DIV.perform(d1 as Double, d2) == result
        where:
        d1   | d2  || result
        2    | 3   || 0.6666666666666666
        2    | -3  || -0.6666666666666666
        2.00 | -13 || -0.15384615384615385
    }

    def "Division by zero"() {
        when:
        Operation.DIV.perform(1, 0)
        then:
        DivisionByZero ex = thrown()
        ex.message == "Division by zero"
    }

    def "Perform operation multiply"() {
        expect:
        Operation.MUL.perform(d1 as Double, d2) == result
        where:
        d1   | d2  || result
        2    | 3   || 6
        2    | -3  || -6
        2.00 | -13 || -26
    }

    def "Perform operation percent"() {
        expect:
        Operation.PER.perform(d1 as Double, d2) == result
        where:
        d1  | d2 || result
        100 | 10 || 10
        200 | 20 || 40
        200 | 50 || 100
    }

    def "Perform operation power"() {
        expect:
        Operation.POW.perform(d1 as Double, d2) == result
        where:
        d1 | d2 || result
        10 | 2  || 100
        2  | 10 || 1024
        3  | 4  || 81
    }

    def "Perform operation left bracket"(){
        when:
        Operation.LEFT_BRACKET.perform(1, 1)
        then:
        OperationUnrealized ex = thrown()
        ex.message == "Operation unrealized"
    }

    def "Perform operation right bracket"(){
        when:
        Operation.RIGHT_BRACKET.perform(1, 1)
        then:
        OperationUnrealized ex = thrown()
        ex.message == "Operation unrealized"
    }
}
