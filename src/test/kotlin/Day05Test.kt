import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day05Test {

    @Test
    fun `should react polymer`() {
        Polymer("dabAcCaCBAcCcaDA").react().stringRepresentation shouldBe "dabAaCBAcCcaDA"
        Polymer("dabAaCBAcCcaDA").react().stringRepresentation shouldBe "dabCBAcCcaDA"
        Polymer("dabCBAcCcaDA").react().stringRepresentation shouldBe "dabCBAcaDA"
    }

    @Test
    fun `should fully react polymer`() {
        Polymer("dabAcCaCBAcCcaDA").reactFully().stringRepresentation shouldBe "dabCBAcaDA"
    }

    @Test
    fun `should react`() {
        ('C' reactsWith 'C') shouldBe false
        ('C' reactsWith 'D') shouldBe false
        ('C' reactsWith 'd') shouldBe false
        ('C' reactsWith 'c') shouldBe true
        ('c' reactsWith 'C') shouldBe true
        ('A' reactsWith 'a') shouldBe true
    }
}
