package tcc.random.models

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.mongodb.core.mapping.Document
import java.text.SimpleDateFormat
import java.util.*


@Document
data class CreditCard(
    val flag: String?,
    val holderName: String?,
    val number: String?,

    @JsonFormat(pattern = "yyyy-MM")
    val validThru: String?,

    val cvv: String?
) {
}