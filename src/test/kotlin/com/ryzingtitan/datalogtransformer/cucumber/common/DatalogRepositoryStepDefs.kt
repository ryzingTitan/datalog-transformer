package com.ryzingtitan.datalogtransformer.cucumber.common

import com.ryzingtitan.datalogtransformer.data.datalog.entities.Data
import com.ryzingtitan.datalogtransformer.data.datalog.entities.Datalog
import com.ryzingtitan.datalogtransformer.data.datalog.entities.TrackInfo
import com.ryzingtitan.datalogtransformer.data.datalog.entities.User
import com.ryzingtitan.datalogtransformer.data.datalog.repositories.DatalogRepository
import com.ryzingtitan.datalogtransformer.data.datalogrecord.entities.DatalogRecord
import com.ryzingtitan.datalogtransformer.data.datalogrecord.repositories.DatalogRecordRepository
import io.cucumber.datatable.DataTable
import io.cucumber.java.Before
import io.cucumber.java.DataTableType
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.*

class DatalogRepositoryStepDefs(
    private val datalogRepository: DatalogRepository,
    private val datalogRecordRepository: DatalogRecordRepository,
) {
    @Given("the following old datalogs exist:")
    fun givenTheFollowingOldDatalogsExist(table: DataTable) {
        val existingDatalogs =
            table.tableConverter.toList<DatalogRecord>(table, DatalogRecord::class.java)

        runBlocking {
            existingDatalogs.forEach { existingDatalog ->
                datalogRecordRepository.save(existingDatalog)
            }
        }
    }

    @Then("the following new datalogs will exist:")
    fun thenTheFollowingNewDatalogsWillExist(table: DataTable) {
        val expectedDatalogs =
            table.tableConverter.toList<Datalog>(table, Datalog::class.java)
                .sortedBy { it.epochMilliseconds }

        val actualDatalogs = mutableListOf<Datalog>()
        runBlocking {
            datalogRepository.findAll().collect { datalog ->
                actualDatalogs.add(datalog)
            }
        }

        assertEquals(
            expectedDatalogs.sortedBy { it.epochMilliseconds },
            actualDatalogs.sortedBy { it.epochMilliseconds },
        )
    }

    @Before
    fun setup() {
        runBlocking {
            datalogRepository.deleteAll()
            datalogRecordRepository.deleteAll()
        }
    }

    @DataTableType
    fun mapDatalog(tableRow: Map<String, String>): Datalog {
        return Datalog(
            sessionId = UUID.fromString(tableRow["sessionId"]),
            epochMilliseconds = tableRow["epochMilliseconds"].toString().toLong(),
            data = Data(
                longitude = tableRow["longitude"].toString().toDouble(),
                latitude = tableRow["latitude"].toString().toDouble(),
                altitude = tableRow["altitude"].toString().toFloat(),
                intakeAirTemperature = tableRow["intakeAirTemperature"].toString().toIntOrNull(),
                boostPressure = tableRow["boostPressure"].toString().toFloatOrNull(),
                coolantTemperature = tableRow["coolantTemperature"].toString().toIntOrNull(),
                engineRpm = tableRow["engineRpm"].toString().toIntOrNull(),
                speed = tableRow["speed"].toString().toIntOrNull(),
                throttlePosition = tableRow["throttlePosition"].toString().toFloatOrNull(),
                airFuelRatio = tableRow["airFuelRatio"].toString().toFloatOrNull(),
            ),
            trackInfo = TrackInfo(
                name = tableRow["trackName"].toString(),
                latitude = tableRow["trackLatitude"].toString().toDouble(),
                longitude = tableRow["trackLongitude"].toString().toDouble(),
            ),
            user = User(
                firstName = tableRow["firstName"].toString(),
                lastName = tableRow["lastName"].toString(),
                email = tableRow["email"].toString(),
            ),
        )
    }

    @DataTableType
    fun mapDatalogRecord(tableRow: Map<String, String>): DatalogRecord {
        return DatalogRecord(
            sessionId = UUID.fromString(tableRow["sessionId"]),
            epochMilliseconds = tableRow["epochMilliseconds"].toString().toLong(),
            longitude = tableRow["longitude"].toString().toDouble(),
            latitude = tableRow["latitude"].toString().toDouble(),
            altitude = tableRow["altitude"].toString().toFloat(),
            intakeAirTemperature = tableRow["intakeAirTemperature"].toString().toIntOrNull(),
            boostPressure = tableRow["boostPressure"].toString().toFloatOrNull(),
            coolantTemperature = tableRow["coolantTemperature"].toString().toIntOrNull(),
            engineRpm = tableRow["engineRpm"].toString().toIntOrNull(),
            speed = tableRow["speed"].toString().toIntOrNull(),
            throttlePosition = tableRow["throttlePosition"].toString().toFloatOrNull(),
            airFuelRatio = tableRow["airFuelRatio"].toString().toFloatOrNull(),
        )
    }
}
