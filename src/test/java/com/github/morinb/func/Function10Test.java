package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Function10Test
{

    @Test
    void applyShouldApplyFunctionToAllArguments()
    {

        Function10<String, String, String, String, String, String, String, String, String, String, String> concatenator =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var expected = "12345678910";
        var actual = concatenator.apply("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        assertEquals(expected, actual);

    }

    @Test
    void andThenShouldApplySecondFunction()
    {

        Function10<String, String, String, String, String, String, String, String, String, String, Integer> lengthCalculator =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        (param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10).length();

        var myLength = lengthCalculator.andThen(length -> length * 2);

        Integer expected = 22;
        var actual = myLength.apply("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        assertEquals(expected, actual);

    }

    @Test
    void curriedShouldApplyFunctionToAllArguments()
    {

        Function10<String, String, String, String, String, String, String, String, String, String, String> concatenator =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var expected = "12345678910";
        var actual = concatenator.curried()
                .apply("1")
                .apply("2")
                .apply("3")
                .apply("4")
                .apply("5")
                .apply("6")
                .apply("7")
                .apply("8")
                .apply("9")
                .apply("10");

        assertEquals(expected, actual);
    }
}