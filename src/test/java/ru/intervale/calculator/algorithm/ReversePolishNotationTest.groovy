package ru.intervale.calculator.algorithm

import spock.lang.Specification

class ReversePolishNotationTest extends Specification {

    def "Reverse polish notation simple case"() {
        expect:
        new ReversePolishNotation(input).rpn() == rpn

        where:
        input    || rpn
        "2+3"    || "2 3 +"
        "0-1"    || "0 1 -"
        "10/2"   || "10 2 /"
        "10^2"   || "10 2 ^"
        "100%10" || "100 10 %"
        "3++3"   || "3  +3 +"
        "-6+6"   || " -6 6 +"

    }
}
