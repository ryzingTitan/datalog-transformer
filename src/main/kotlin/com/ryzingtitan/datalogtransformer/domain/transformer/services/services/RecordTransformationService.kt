package com.ryzingtitan.datalogtransformer.domain.transformer.services.services

import com.ryzingtitan.datalogtransformer.data.datalog.entities.Data
import com.ryzingtitan.datalogtransformer.data.datalog.entities.Datalog
import com.ryzingtitan.datalogtransformer.data.datalog.entities.TrackInfo
import com.ryzingtitan.datalogtransformer.data.datalog.entities.User
import com.ryzingtitan.datalogtransformer.data.datalog.repositories.DatalogRepository
import com.ryzingtitan.datalogtransformer.data.datalogrecord.repositories.DatalogRecordRepository
import com.ryzingtitan.datalogtransformer.domain.transformer.services.configuration.TrackInfoConfiguration
import com.ryzingtitan.datalogtransformer.domain.transformer.services.configuration.UserConfiguration
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RecordTransformationService(
    private val datalogRecordRepository: DatalogRecordRepository,
    private val userConfiguration: UserConfiguration,
    private val trackInfoConfiguration: TrackInfoConfiguration,
    private val datalogRepository: DatalogRepository,
) {
    private val logger: Logger = LoggerFactory.getLogger(RecordTransformationService::class.java)
    fun transform() {
        logger.info("Beginning to transform records")

        runBlocking {
            datalogRecordRepository.findAll().map { datalogRecord ->
                val transformedDatalog = Datalog(
                    datalogRecord.sessionId,
                    datalogRecord.epochMilliseconds,
                    Data(
                        datalogRecord.longitude,
                        datalogRecord.latitude,
                        datalogRecord.altitude,
                        datalogRecord.intakeAirTemperature,
                        datalogRecord.boostPressure,
                        datalogRecord.coolantTemperature,
                        datalogRecord.engineRpm,
                        datalogRecord.speed,
                        datalogRecord.throttlePosition,
                        datalogRecord.airFuelRatio,
                    ),
                    TrackInfo(
                        trackInfoConfiguration.name,
                        trackInfoConfiguration.latitude,
                        trackInfoConfiguration.longitude,
                    ),
                    User(
                        userConfiguration.firstName,
                        userConfiguration.lastName,
                        userConfiguration.email,
                    ),
                )

                datalogRepository.save(transformedDatalog)
            }.collect()
        }

        logger.info("Record transformation completed")
    }
}
