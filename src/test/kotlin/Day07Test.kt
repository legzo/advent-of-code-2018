import Status.CLOSED
import Status.DONE
import Status.PENDING
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day07Test {

    private val input = """
            Step C must be finished before step A can begin.
            Step C must be finished before step F can begin.
            Step A must be finished before step B can begin.
            Step A must be finished before step D can begin.
            Step B must be finished before step E can begin.
            Step D must be finished before step E can begin.
            Step F must be finished before step E can begin.
        """.trimIndent()
        .split("\n")

    @Test
    fun `should get step sequence with single worker`() {
        input.toStepSequence(
            taskDurationOffset = 0,
            numberOfWorkers = 1
        ).sequence shouldBe "CABDFE"
    }

    @Test
    fun `should get step sequence with multiple workers`() {
        val stepSequence = input.toStepSequence(
            taskDurationOffset = 0,
            numberOfWorkers = 2
        )

        stepSequence.numberOfSteps shouldBe 15
        stepSequence.sequence shouldBe "CABFDE"
    }

    @Test
    fun `should advancing task be done`() {
        val task = Task(id = "A", initialStatus = PENDING, offset = 60)
        repeat(30) { task.advance() }
        task.status shouldBe PENDING
        repeat(30) { task.advance() }
        task.status shouldBe PENDING
        task.advance()
        task.status shouldBe DONE

        val taskB = Task(id = "D", initialStatus = PENDING, offset = 60)
        repeat(30) { taskB.advance() }
        taskB.status shouldBe PENDING
        repeat(30) { taskB.advance() }
        taskB.status shouldBe PENDING
        taskB.advance()
        taskB.advance()
        taskB.advance()
        taskB.advance()
        taskB.status shouldBe DONE
    }

    @Test
    fun `should return completed tasks`() {
        val taskZ = Task("Z", initialStatus = CLOSED)
        val taskA = Task("A")
        val taskB = Task("B", listOf("A"))
        val taskC = Task("C", listOf("Z"))

        listOf(taskA).getAvailableTasks() shouldBe listOf(taskA)

        listOf(taskA, taskB).getAvailableTasks() shouldBe listOf(taskA)

        listOf(taskA, taskC, taskZ).getAvailableTasks() shouldBe listOf(taskA, taskC)
    }
}


