package tcc.random.services

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period
import java.util.*

@Service
class CustomerService {
    companion object {
        fun calculateCustomerAge(birthDate: Date): Int {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Brazil"))
            calendar.time = birthDate
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            return Period.between(
                LocalDate.of(year, month, day),
                    LocalDate.now()
            ).years
        }
    }
}