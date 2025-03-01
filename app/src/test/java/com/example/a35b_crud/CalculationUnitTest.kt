package com.example.a35b_crud

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class CalculationUnitTest {

    @Test
    fun sum_check(){
        val calculations = Calculations()
        val result = calculations.add(5,5) //10

        assertEquals(10,result)

    }

    //using mockito
//    Given Calculation Class
//    When 1+1 passed in add function
//    Then return 2
    @Test
    fun sum_check_mockito(){
        val calculations = mock(Calculations::class.java)

        `when`(calculations.add(1,1)).thenReturn(2)

        assertEquals(calculations.add(1,1),3)


    }
}