package com.ryzingtitan.datalogtransformer.data.datalogrecord.repositories

import com.ryzingtitan.datalogtransformer.data.datalogrecord.entities.DatalogRecord
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface DatalogRecordRepository : CoroutineCrudRepository<DatalogRecord, Any>
