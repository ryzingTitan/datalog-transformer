package com.ryzingtitan.datalogtransformer.domain.transformer.services

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import com.ryzingtitan.datalogtransformer.data.datalog.entities.Data
import com.ryzingtitan.datalogtransformer.data.datalog.entities.Datalog
import com.ryzingtitan.datalogtransformer.data.datalog.entities.TrackInfo
import com.ryzingtitan.datalogtransformer.data.datalog.entities.User
import com.ryzingtitan.datalogtransformer.data.datalog.repositories.DatalogRepository
import com.ryzingtitan.datalogtransformer.data.datalogrecord.entities.DatalogRecord
import com.ryzingtitan.datalogtransformer.data.datalogrecord.repositories.DatalogRecordRepository
import com.ryzingtitan.datalogtransformer.domain.transformer.services.configuration.TrackInfoConfiguration
import com.ryzingtitan.datalogtransformer.domain.transformer.services.configuration.UserConfiguration
import com.ryzingtitan.datalogtransformer.domain.transformer.services.services.RecordTransformationService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.*

@ExperimentalCoroutinesApi
class RecordTransformationServiceTests {
    @Nested
    inner class Transform {
        @Test
        fun `transforms all datalogs from version 2 to version 3`() = runTest {
            recordTransformationService.transform()

            verify(mockDatalogRecordRepository, times(1)).findAll()
            verify(mockDatalogRepository, times(1)).save(firstDatalog)
            verify(mockDatalogRepository, times(1)).save(secondDatalog)
        }
    }

    @BeforeEach
    fun setup() {
        recordTransformationService = RecordTransformationService(
            mockDatalogRecordRepository,
            mockUserConfiguration,
            mockTrackInfoConfiguration,
            mockDatalogRepository,
        )

        whenever(mockUserConfiguration.firstName).thenReturn("test")
        whenever(mockUserConfiguration.lastName).thenReturn("tester")
        whenever(mockUserConfiguration.email).thenReturn("test@test.com")

        whenever(mockTrackInfoConfiguration.name).thenReturn("Test Track")
        whenever(mockTrackInfoConfiguration.latitude).thenReturn(40.0)
        whenever(mockTrackInfoConfiguration.longitude).thenReturn(-80.0)

        whenever(mockDatalogRecordRepository.findAll()).thenReturn(flowOf(firstDatalogRecord, secondDatalogRecord))

        logger = LoggerFactory.getLogger(RecordTransformationService::class.java) as Logger
        appender = ListAppender()
        appender.context = LoggerContext()
        logger.addAppender(appender)
        appender.start()
    }

    private lateinit var recordTransformationService: RecordTransformationService
    private lateinit var logger: Logger
    private lateinit var appender: ListAppender<ILoggingEvent>

    private val mockDatalogRecordRepository = mock<DatalogRecordRepository>()
    private val mockDatalogRepository = mock<DatalogRepository>()
    private val mockUserConfiguration = mock<UserConfiguration>()
    private val mockTrackInfoConfiguration = mock<TrackInfoConfiguration>()

    private val sessionId = UUID.randomUUID()
    private val firstRecordTimestamp = Instant.now().toEpochMilli()
    private val secondRecordTimestamp = Instant.now().toEpochMilli() + 5

    private val firstDatalogRecord = DatalogRecord(
        sessionId = sessionId,
        epochMilliseconds = firstRecordTimestamp,
        longitude = -86.14162,
        latitude = 42.406800000000004,
        altitude = 188.4f,
        intakeAirTemperature = 138,
        boostPressure = 16.5f,
        coolantTemperature = 155,
        engineRpm = 3500,
        speed = 79,
        throttlePosition = 83.2f,
        airFuelRatio = 17.5f,
    )

    private val firstDatalog = Datalog(
        sessionId = sessionId,
        epochMilliseconds = firstRecordTimestamp,
        data = Data(
            longitude = -86.14162,
            latitude = 42.406800000000004,
            altitude = 188.4f,
            intakeAirTemperature = 138,
            boostPressure = 16.5f,
            coolantTemperature = 155,
            engineRpm = 3500,
            speed = 79,
            throttlePosition = 83.2f,
            airFuelRatio = 17.5f,
        ),
        trackInfo = TrackInfo(
            name = "Test Track",
            latitude = 40.0,
            longitude = -80.0,
        ),
        user = User(
            email = "test@test.com",
            firstName = "test",
            lastName = "tester",
        ),
    )

    private val secondDatalogRecord = DatalogRecord(
        sessionId = sessionId,
        epochMilliseconds = secondRecordTimestamp,
        longitude = -86.14162,
        latitude = 42.406800000000004,
        altitude = 190.4f,
        intakeAirTemperature = 158,
        boostPressure = 16.5f,
        coolantTemperature = 155,
        engineRpm = 5500,
        speed = 85,
        throttlePosition = 83.2f,
        airFuelRatio = null,
    )

    private val secondDatalog = Datalog(
        sessionId = sessionId,
        epochMilliseconds = secondRecordTimestamp,
        data = Data(
            longitude = -86.14162,
            latitude = 42.406800000000004,
            altitude = 190.4f,
            intakeAirTemperature = 158,
            boostPressure = 16.5f,
            coolantTemperature = 155,
            engineRpm = 5500,
            speed = 85,
            throttlePosition = 83.2f,
            airFuelRatio = null,
        ),
        trackInfo = TrackInfo(
            name = "Test Track",
            latitude = 40.0,
            longitude = -80.0,
        ),
        user = User(
            email = "test@test.com",
            firstName = "test",
            lastName = "tester",
        ),
    )
}
