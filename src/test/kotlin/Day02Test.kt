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
}