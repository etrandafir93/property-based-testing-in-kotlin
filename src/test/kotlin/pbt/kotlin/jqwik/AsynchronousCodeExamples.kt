package pbt.kotlin.jqwik

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.kotlin.api.runBlockingProperty
import org.assertj.core.api.Assertions.assertThat
import kotlin.coroutines.EmptyCoroutineContext

class AsynchronousCodeExamples {

    suspend fun echo(string: String): String {
        delay(100)
        return string
    }

    @Property(tries = 10)
    fun `use runBlockingProperty`(@ForAll s: String) =
        runBlockingProperty {
            assertThat(echo(s)).isEqualTo(s)
        }

    @Property(tries = 10)
    fun `use runBlockingProperty with context`(@ForAll s: String) =
        runBlockingProperty(EmptyCoroutineContext) {
            assertThat(echo(s)).isEqualTo(s)
        }

    @Property(tries = 10)
    suspend fun `use suspend function`(@ForAll s: String) {
        assertThat(echo(s)).isEqualTo(s)
    }

    @Property(tries = 10)
    fun `use coroutines testing support`(@ForAll s: String) = runBlocking {
        launch {
            assertThat(echo(s)).isEqualTo(s)
        }
    }
}