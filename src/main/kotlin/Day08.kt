object MyTreeNode {

    data class MetadataCounter(val currentIndex: Int, val totalMetadataSum: Int)

    fun decodeMeta(input: List<Int>): MetadataCounter {

        val childNumber = input[0]
        val metaDataNumber = input[1]
        var currentIndex = 2
        var sum = 0
        for (child in 0 until childNumber) {
            val metadataCounter = decodeMeta(input.subList(currentIndex, input.size))
            currentIndex += metadataCounter.currentIndex
            sum += metadataCounter.totalMetadataSum
        }
        for (metaData in 0 until metaDataNumber) {
            sum += input[currentIndex]
            currentIndex++
        }

        return MetadataCounter(currentIndex, sum)
    }

    data class IndexedNode(val currentIndex: Int, val node: Node)

    fun parseNode(input: List<Int>): IndexedNode {
        val childNumber = input[0]
        val metaDataNumber = input[1]
        var currentIndex = 2

        val nodeBuilder = NodeBuilder()

        for (child in 0 until childNumber) {
            val indexedNode = parseNode(input.subList(currentIndex, input.size))
            currentIndex += indexedNode.currentIndex
            nodeBuilder.children += indexedNode.node
        }
        for (metaData in 0 until metaDataNumber) {
            nodeBuilder.metadata += input[currentIndex]
            currentIndex++
        }

        return IndexedNode(currentIndex, nodeBuilder.build())
    }

}

data class NodeBuilder(
    val children: MutableList<Node> = mutableListOf(),
    val metadata: MutableList<Int> = mutableListOf()
) {
    fun build() = Node(children, metadata)
}

data class Node(val children: List<Node>, val metadata: List<Int>) {
    val value: Int
        get() = when {
            children.isEmpty() -> metadata.sum()
            else -> metadata
                .map { it - 1 } // decalage parce que les indices commencent à 1 dans l'énoncé
                .filter { it in children.indices }
                .sumBy { children[it].value }
        }
}
