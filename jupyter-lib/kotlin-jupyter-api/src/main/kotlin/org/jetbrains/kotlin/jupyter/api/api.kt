package org.jetbrains.kotlin.jupyter.api

/**
 * Single evaluated notebook cell representation
 */
interface CodeCell {
    /**
     * Reference to the notebook instance
     */
    val notebook: Notebook<*>

    /**
     * Displayed cell ID
     */
    val id: Int

    /**
     * Internal cell ID which is used to generate internal class names and result fields
     */
    val internalId: Int

    /**
     * Cell code
     */
    val code: String

    /**
     * Cell code after magic preprocessing
     */
    val preprocessedCode: String

    /**
     * Cell result value
     */
    val result: Any?

    /**
     * Cell standard output
     */
    val streamOutput: String

    /**
     * Cell displays
     */
    val displays: DisplayContainer

    /**
     * Previously evaluated cell
     */
    val prevCell: CodeCell?
}

interface KotlinKernelHost {
    fun display(value: Any)

    fun updateDisplay(value: Any, id: String? = null)

    fun scheduleExecution(code: Code)

    fun execute(code: Code): Any?

    fun executeInit(codes: List<Code>)
}

interface ResultsAccessor {
    operator fun get(i: Int): Any?
}

interface RuntimeUtils: JavaVersionHelper

interface Notebook<CellT: CodeCell> {
    val cells: Map<Int, CellT>
    val results: ResultsAccessor
    val displays: DisplayContainer
    val host: KotlinKernelHost

    fun history(before: Int): CellT?
    val currentCell: CellT?
        get() = history(0)
    val lastCell: CellT?
        get() = history(1)

    val kernelVersion: KotlinKernelVersion
    val runtimeUtils: RuntimeUtils
}
