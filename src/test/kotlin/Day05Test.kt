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
        Polymer("dabAcCaCBAcCcaDA").fullyReact().stringRepresentation shouldBe "dabCBAcaDA"
    }
}
