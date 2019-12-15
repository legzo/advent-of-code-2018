import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day10Test {

    private val myParticles = Particle.loadList(
        """
            position=< 9,  1> velocity=< 0,  2>
            position=< 7,  0> velocity=<-1,  0>
            position=< 3, -2> velocity=<-1,  1>
            position=< 6, 10> velocity=<-2, -1>
            position=< 2, -4> velocity=< 2,  2>
            position=<-6, 10> velocity=< 2, -2>
            position=< 1,  8> velocity=< 1, -1>
            position=< 1,  7> velocity=< 1,  0>
            position=<-3, 11> velocity=< 1, -2>
            position=< 7,  6> velocity=<-1, -1>
            position=<-2,  3> velocity=< 1,  0>
            position=<-4,  3> velocity=< 2,  0>
            position=<10, -3> velocity=<-1,  1>
            position=< 5, 11> velocity=< 1, -2>
            position=< 4,  7> velocity=< 0, -1>
            position=< 8, -2> velocity=< 0,  1>
            position=<15,  0> velocity=<-2,  0>
            position=< 1,  6> velocity=< 1,  0>
            position=< 8,  9> velocity=< 0, -1>
            position=< 3,  3> velocity=<-1,  1>
            position=< 0,  5> velocity=< 0, -1>
            position=<-2,  2> velocity=< 2,  0>
            position=< 5, -2> velocity=< 1,  2>
            position=< 1,  4> velocity=< 2,  1>
            position=<-2,  7> velocity=< 2, -2>
            position=< 3,  6> velocity=<-1, -1>
            position=< 5,  0> velocity=< 1,  0>
            position=<-6,  0> velocity=< 2,  0>
            position=< 5,  9> velocity=< 1, -2>
            position=<14,  7> velocity=<-2,  0>
            position=<-3,  6> velocity=< 2, -1>
        """.trimIndent()
            .split("\n")
    )

    @Test
    fun `should load particles`() {
        Particle.loadList(
            listOf(
                "position=< 9,  1> velocity=< 0,  2>",
                "position=< 7,  0> velocity=<-1,  0>"
            )
        ) shouldBe listOf(
            Particle(`XYPosition`(9, 1), Velocity(0, 2)),
            Particle(`XYPosition`(7, 0), Velocity(-1, 0))
        )
    }

    @Test
    fun `should move according to velocity`() {
        Particle(`XYPosition`(3, 9), Velocity(1, -2))
            .move()
            .move()
            .move() shouldBe Particle(`XYPosition`(6, 3), Velocity(1, -2))
    }

    @Test
    fun `should draw the list of particles`() {
        myParticles.draw() shouldBe """
            ........#.............
            ................#.....
            .........#.#..#.......
            ......................
            #..........#.#.......#
            ...............#......
            ....#.................
            ..#.#....#............
            .......#..............
            ......#...............
            ...#...#.#...#........
            ....#..#..#.........#.
            .......#..............
            ...........#..#.......
            #...........#.........
            ...#.......#..........
        """.trimIndent()
    }

    @Test
    fun `should get the state containing the message`() {
        val particlesState = myParticles.getStateWithMessage()

        particlesState.steps shouldBe 3
        particlesState.particles.draw() shouldBe """
            #...#..###
            #...#...#.
            #...#...#.
            #####...#.
            #...#...#.
            #...#...#.
            #...#...#.
            #...#..###
            """.trimIndent()
    }
}
