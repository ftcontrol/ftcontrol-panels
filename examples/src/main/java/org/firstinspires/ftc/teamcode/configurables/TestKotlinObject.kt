package org.firstinspires.ftc.teamcode.configurables

import com.bylazar.configurables.annotations.Configurable
import com.bylazar.configurables.annotations.GenericValue

@Configurable
object TestKotlinObject {
    @JvmField
    var testInt: Int = 1

    @JvmField
    var testLong: Long = 1L

    @JvmField
    var testDouble: Double = 1.0

    @JvmField
    var testFloat: Float = 1.0f

    @JvmField
    var testString: String = "test!"

    @JvmField
    var testBoolean: Boolean = false

    enum class TestEnum {
        TEST1,
        TEST2,
        TEST3
    }

    @JvmField
    var testEnum: TestEnum = TestEnum.TEST1

    @JvmField
    var testArray: Array<Int> = arrayOf(1, 2, 3)

    @JvmField
    var testList: List<Int> = listOf(1, 2, 3)

    @JvmField
    var testMap: Map<String, Int> = mapOf("one" to 1, "two" to 2, "three" to 3)

    class CustomType(
        val testInt: Int,
        val testString: String
    )

    @JvmField
    var testCustomType: CustomType = CustomType(1, "test!")

    class NestedType(
        val testInt: Int,
        val testString: String,
        val testCustomType: CustomType
    )

    @JvmField
    var testNestedType: NestedType = NestedType(1, "test!", CustomType(2, "test2!"))

    class UnknownType(
        val testInt: Int,
    )

    @JvmField
    var testUnknownType: UnknownType = UnknownType(1)

    @JvmField
    var testRandomArray = arrayOf(
        1,
        1.0,
        1.0f,
        "test!",
        true,
        CustomType(1, "test!"),
        NestedType(1, "test!", CustomType(2, "test2!")),
        UnknownType(1),
        arrayOf(
            1,
            2,
            3
        ),
        mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3
        )
    )

    class TParamClass<T>(
        val test: T
    )

    @JvmField
    @field:GenericValue(Int::class)
    var testTParamClass = TParamClass(1)
}