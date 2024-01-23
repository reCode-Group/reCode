package com.dev.reCode

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

class VbaJsConverterTest {
    private lateinit var v: Vba2JsConverter

    @BeforeEach
    fun initialization() {
        v = Vba2JsConverter()
    }

    @Test
    fun `First test`() {
        assertEquals(
            v.vbaToJs(
                """Sыввыub Example()
    Dim myRrrrange
    Dim result
    Dim Run As Long
    For Run = 1 To 3
        Select Case Run
        Case 1
            result = "=SUM(A1:A100)"
        Case 2
            result = "=SUM(A1:A300)"
        Case 3
            result = "=SUM(A1:A25)"
        End Select
        ActiveSheet.range("B" & Run) = result
    Next Run
End Sub"""
            ), """(function(){
    var myRrrrange, result, Run;
    for (Run=1; Run<=3; Run++){
        switch( Run){
            case 1:
                result = "=SUM(A1:A100)";
                break;
            case 2:
                result = "=SUM(A1:A300)";
                break;
            case 3:
                result = "=SUM(A1:A25)";
        }
        Api.GetActiveSheet().GetRange("B" + Run).Value = result;
    }
})();"""
        )
    }

    @Test
    fun `Second test`() {
        assertEquals(
            v.vbaToJs(
                """Sub Example()
    Cells(3, 4)="Hello world"
End Sub"""
            ), """(function(){
    Api.GetActiveSheet().GetRange("C4").SetValue("Hello world");
})();"""
        )
    }


}