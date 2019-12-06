
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
        val task = Task(id = "A", status = PENDING, offset = 60)
        task.advance30Times()
        task.status shouldBe PENDING
        task.advance30Times()
        task.status shouldBe PENDING
        task.advance()
        task.status shouldBe DONE

        val taskB = Task(id = "D", status = PENDING, offset = 60)
        taskB.advance30Times()
        taskB.status shouldBe PENDING
        taskB.advance30Times()
        taskB.status shouldBe PENDING
        taskB.advance()
        taskB.advance()
        taskB.advance()
        taskB.advance()
        taskB.status shouldBe DONE
    }

    private fun Task.advance30Times() {
        for (i in 0 until 30) {
            advance()
        }
    }

    @Test
    fun `should return completed tasks`() {
        val taskZ = Task("Z", status = CLOSED)
        val taskA = Task("A")
        val taskB = Task("B", mutableListOf(taskA))
        val taskC = Task("C", mutableListOf(taskZ))

        mapOf("A" to taskA).getAvailableTasks() shouldBe listOf(taskA)

        mapOf(
            "A" to taskA,
            "B" to taskB
        ).getAvailableTasks() shouldBe listOf(taskA)

        mapOf(
            "A" to taskA,
            "C" to taskC
        ).getAvailableTasks() shouldBe listOf(taskA, taskC)
    }
}


