package com.ryzingtitan.datalogtransformer.data.datalog.repositories

import com.ryzingtitan.datalogtransformer.data.datalog.entities.Datalog
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface DatalogRepository : CoroutineCrudRepository<Datalog, Long>
