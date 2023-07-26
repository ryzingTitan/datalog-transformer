package com.ryzingtitan.datalogtransformer.domain.transformer.services.configuration

import lombok.Generated
import org.springframework.boot.context.properties.ConfigurationProperties

@Generated
@ConfigurationProperties(prefix = "track-info")
data class TrackInfoConfiguration(
    val name: String,
    val latitude: Double,
    val longitude: Double,
)
