import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day02Test {

    @Test
    fun `caculate checksum`() {
        listOf(
            "abcdef",
            "bababc",
            "abbcde",
            "abcccd",
            "aabcdd",
            "abcdee",
            "ababab"
        ).calculateChecksum() shouldBe 12
    }

    @Test
    fun `find common letters for correct boxes`() {
        listOf(
            "abcde",
            "fghij",
            "klmno",
            "pqrst",
            "fguij",
            "axcye",
            "wvxyz"
        ).findCommonLetters() shouldBe "fgij"
    }

    @Test
    fun `tell if two strings differ for only one char`() {
        "a".differByOnlyOneCharFrom("a") shouldBe false
        "a".differByOnlyOneCharFrom("b") shouldBe true
        "ab".differByOnlyOneCharFrom("ba") shouldBe false
        "ab".differByOnlyOneCharFrom("aa") shouldBe true
        "abcde".differByOnlyOneCharFrom("fghij") shouldBe false
        "fguij".differByOnlyOneCharFrom("fghij") shouldBe true
    }

     @Test
     fun `get common chars only`() {
         "fghij".commonCharsWith("fguij") shouldBe "fgij"
     }
}


