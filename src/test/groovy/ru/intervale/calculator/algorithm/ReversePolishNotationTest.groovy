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
        "(5 - 3) * 12 + 8" || "5 3 - 12 * 8 +"
        "(5 - 3) * (12 + 8)" || "5 3 - 12 8 + *"
        "50/4 + (100 - 5^3)"||"50 4 / 100 5 3 ^ - +"
        "3^2^2^2/1000 - 40000.021"||"3 2 ^ 2 ^ 2 ^ 1000 / 40000.021 -"
        "5 + 2 * 3 ^ 2 + 2 ^ 3 * 5 / 10"||"5 2 3 2 ^ * + 2 3 ^ 5 * 10 / +"
        "5+2*3^2+2^3*5/10"||"5 2 3 2 ^ * + 2 3 ^ 5 * 10 / +"

    }
}
