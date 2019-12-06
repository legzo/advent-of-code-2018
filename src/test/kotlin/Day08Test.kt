import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day08Test {

    private val expectedNode = Node(
        children = listOf(
            Node(
                children = listOf(),
                metadata = listOf(10, 11, 12)
            ),
            Node(
                children = listOf(
                    element = Node(
                        children = listOf(),
                        metadata = listOf(99)
                    )
                ),
                metadata = listOf(2)
            )
        ),
        metadata = listOf(1, 1, 2)
    )

    @Test
    fun `should get step sequence`() {
        val input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"
            .split(" ")
            .map { it.toInt() }

        MyTreeNode.decodeMeta(input).totalMetadataSum shouldBe 138
    }

    @Test
    fun `should parse node from string`() {
        val input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"
            .split(" ")
            .map { it.toInt() }

        MyTreeNode.parseNode(input).node shouldBe expectedNode
    }

    @Test
    fun `should get node value`() {
        expectedNode.value shouldBe 66
    }
}


