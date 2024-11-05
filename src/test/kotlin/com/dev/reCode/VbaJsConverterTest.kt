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
                """Sub Example()
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
    for(Run = 1; Run <= 3; Run++){
        switch(Run){
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
                """Sub example()
    Cells(4, 3) = "dsds world"
End Sub"""
            ), """(function(){
    Api.GetActiveSheet().GetRange("D3").SetValue("dsds world");
})();""")
    }

    @Test
    fun Test3() {
        assertEquals(
            v.vbaToJs(
                """Sub example()
    Range("B4").Interior.Color = RGB(255, 0, 0)
End Sub"""
            ), """(function(){
    Api.GetActiveSheet().GetRange("B4").SetFillColor(Api.CreateColorFromRGB(255, 0, 0));
})();""")
    }

    @Test
    fun Test4() {
        assertEquals(
            v.vbaToJs(
                """ Sub example()
    Range("B4").Font.Color = RGB(255, 0, 0)
End Sub"""
            ), """(function(){
    Api.GetActiveSheet().GetRange("B4").SetFontColor(Api.CreateColorFromRGB(255, 0, 0));
})();"""
        )
    }

    @Test
    fun Test5() {
        assertEquals(
            v.vbaToJs(
                """ Sub example()
    Range("A2").Font.Bold = True
End Sub"""
            ), """(function(){
    Api.GetActiveSheet().GetRange("A2").SetBold( true);
})();"""
        )
    }

    @Test
    fun Test6() {
        assertEquals(
            v.vbaToJs(
                """Sub example()
Range("A1:B3").Merge
End Sub"""
            ), """(function(){
    Api.GetActiveSheet().GetRange("A1:B3").Merge(true);
})();"""
        )
    }

    @Test
    fun Test7() {
        assertEquals(
            v.vbaToJs(
                """Sub example()
Range("C5:D10").UnMerge
End Sub"""
            ), """(function(){
    Api.GetActiveSheet().GetRange("C5:D10").UnMerge();
})();"""
        )
    }
    @Test
    fun Test8() {
        assertEquals(
            v.vbaToJs(
                """Sub example()
Columns("B").ColumnWidth = 25
End Sub"""
            ), """(function(){
    Api.GetActiveSheet().SetColumnWidth(1, 25);
})();"""
        )
    }
    @Test
    fun Test9() {
        assertEquals(
            v.vbaToJs(
                """Sub example()
    Selection.TypeText Text:="Hello world!"
End Sub"""
            ), """(function(){
    var oDocument = Api.GetDocument();
    var oParagraph = Api.CreateParagraph();
    oParagraph.AddText("Hello world!");
    oDocument.InsertContent([oParagraph]);
})();"""
        )
    }
}