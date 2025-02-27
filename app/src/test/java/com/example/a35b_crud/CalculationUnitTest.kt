package com.example.a35b_crud

import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class CalculationUnitTest {

    @Test
    fun testCalc(){
        var test = Calculations()
        val res = test.add(5,5)

        assertEquals(5,5)
    }

    @Test
    fun check_sum_using_mockito(){
        var calculations = mock(Calculations::class.java)

        `when`(calculations.add(5,5)).thenReturn(10)

        Assert.assertEquals(calculations.add(5, 5), 10)
    }
}